package org.realworld.utils;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import static okhttp3.internal.platform.Platform.INFO;

public class HttpCustomLoggingInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");

    public interface Logger {
        void log(String message);

        Logger DEFAULT = message -> Platform.get().log(INFO, message, null);
    }

    public HttpCustomLoggingInterceptor() {
        this.logger = Logger.DEFAULT;
    }

    private final Logger logger;
    private volatile Set<String> headersToRedact = Collections.emptySet();

    public void redactHeader(String name) {
        Set<String> newHeadersToRedact = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        newHeadersToRedact.addAll(headersToRedact);
        newHeadersToRedact.add(name);
        headersToRedact = newHeadersToRedact;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        StringBuilder requestStartMessage = new StringBuilder();
        requestStartMessage.append("--> ")
                .append(request.method())
                .append(' ')
                .append(request.url())
                .append((connection != null ? " " + connection.protocol() : ""));
        if (hasRequestBody) {
            requestStartMessage.append(" (")
                    .append(requestBody.contentLength())
                    .append("-byte body)");
        }

        if (hasRequestBody) {
            if (requestBody.contentType() != null) {
                requestStartMessage
                        .append(System.lineSeparator())
                        .append("Content-Type: ")
                        .append(requestBody.contentType());
            }
            if (requestBody.contentLength() != -1) {
                requestStartMessage
                        .append(System.lineSeparator())
                        .append("Content-Length: ")
                        .append(requestBody.contentLength());
            }

            logger.log(requestStartMessage.toString());
            Headers headers = request.headers();
            for (int i = 0, count = headers.size(); i < count; i++) {
                String name = headers.name(i);
                // Skip headers from the request body as they are explicitly logged above.
                if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                    logger.log(getHeadersAsString(headers, i, new StringBuilder()).toString());
                }
            }

            if (!hasRequestBody) {
                logger.log(requestStartMessage
                        .append(System.lineSeparator())
                        .append("--> END ")
                        .append(request.method())
                        .toString());
            } else if (bodyHasUnknownEncoding(request.headers())) {
                logger.log(requestStartMessage
                        .append(System.lineSeparator())
                        .append("--> END ").append(request.method())
                        .append(" (encoded body omitted)")
                        .toString());
            } else if (requestBody.isDuplex()) {
                logger.log(requestStartMessage
                        .append(System.lineSeparator())
                        .append("--> END ").append(request.method())
                        .append(" duplex request body omitted)")
                        .toString());
            } else {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);

                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }

                logger.log("");
                if (isPlaintext(buffer)) {
                    logger.log(buffer.readString(charset));
                    logger.log("--> END " + request.method()
                            + " (" + requestBody.contentLength() + "-byte body)");
                } else {
                    logger.log("--> END " + request.method() + " (binary "
                            + requestBody.contentLength() + "-byte body omitted)");
                }
            }
        }

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        StringBuilder responseMessage = new StringBuilder();
        logger.log(responseMessage
                .append("<-- ")
                .append(response.code())
                .append((response.message().isEmpty() ? "" : ' ' + response.message()))
                .append(' ')
                .append(response.request().url())
                .append(" (")
                .append(tookMs)
                .append("ms )")
                .toString());

        Headers headers = response.headers();
        StringBuilder headersMessage = new StringBuilder();
        for (int i = 0, count = headers.size(); i < count; i++) {
            getHeadersAsString(headers, i, headersMessage);
        }
        logger.log(headersMessage.toString());

        if (!HttpHeaders.hasBody(response)) {
            logger.log("<-- END HTTP");
        } else if (bodyHasUnknownEncoding(response.headers())) {
            logger.log("<-- END HTTP (encoded body omitted)");
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.getBuffer();

            Long gzippedLength = null;
            if ("gzip".equalsIgnoreCase(headers.get("Content-Encoding"))) {
                gzippedLength = buffer.size();
                try (GzipSource gzippedResponseBody = new GzipSource(buffer.clone())) {
                    buffer = new Buffer();
                    buffer.writeAll(gzippedResponseBody);
                }
            }

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (!isPlaintext(buffer)) {
                logger.log("<-- END HTTP (binary " + buffer.size() + "-byte body omitted)");
                return response;
            }

            if (contentLength != 0) {
                logger.log(buffer.clone().readString(charset));
            }

            if (gzippedLength != null) {
                logger.log("<-- END HTTP (" + buffer.size() + "-byte, "
                        + gzippedLength + "-gzipped-byte body)");
            } else {
                logger.log("<-- END HTTP (" + buffer.size() + "-byte body)");
            }
        }


        return response;
    }

    private StringBuilder getHeadersAsString(Headers headers, int i, StringBuilder headersMessage) {
        String value = headersToRedact.contains(headers.name(i)) ? "██" : headers.value(i);
        return headersMessage
                .append(System.lineSeparator())
                .append(headers.name(i))
                .append(": ")
                .append(value);
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private static boolean bodyHasUnknownEncoding(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null
                && !contentEncoding.equalsIgnoreCase("identity")
                && !contentEncoding.equalsIgnoreCase("gzip");
    }

}
