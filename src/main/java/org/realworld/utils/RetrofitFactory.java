package org.realworld.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitFactory {

    private static final String BASE_URL = PropertiesManager.getProperty("apiBaseUrl");
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
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create());
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
