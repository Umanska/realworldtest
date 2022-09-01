package org.realworld.api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.realworld.api.interceptors.HttpCustomLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.fasterxml.jackson.core.JsonParser.Feature.STRICT_DUPLICATE_DETECTION;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.realworld.api.Constants.API_BASE_URL;

public class RetrofitFactory {

    private static final HttpCustomLoggingInterceptor httpLoggingInterceptor =
            new HttpCustomLoggingInterceptor();
    private static RetrofitFactory instance;

    private RetrofitFactory() {
    }

    public static RetrofitFactory getInstance() {
        if (instance == null) {
            instance = new RetrofitFactory();
        }
        return instance;
    }

    public <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit =
                getRetrofitDefaultBuilder()
                        .client(getDefaultHttpClient())
                        .build();
        return retrofit.create(serviceClass);
    }

    public <S> S createAuthorizedService(Class<S> serviceClass, final String token) {
        if (token == null) {
            throw new RuntimeException("There is no token for authorization");
        }
        Retrofit retrofit =
                getRetrofitDefaultBuilder()
                        .client(getAuthorizedHttpClient(token))
                        .build();
        return retrofit.create(serviceClass);
    }

    private Retrofit.Builder getRetrofitDefaultBuilder() {
        ObjectMapper defaultMapper = new ObjectMapper()
                .configure(STRICT_DUPLICATE_DETECTION, true)
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, true)
                .configure(FAIL_ON_NULL_FOR_PRIMITIVES, true);

        return new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(defaultMapper));
    }

    private OkHttpClient getDefaultHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    private OkHttpClient getAuthorizedHttpClient(String token) {
        return new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", token)
                            .build();
                    return chain.proceed(request);
                })
                .build();
    }
}
