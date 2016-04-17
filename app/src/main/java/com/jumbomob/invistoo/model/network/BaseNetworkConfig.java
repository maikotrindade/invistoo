package com.jumbomob.invistoo.model.network;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * @author maiko.trindade
 * @since 04/02/2016
 */
public class BaseNetworkConfig {

    //This is the emulator base ul
    public static final String BASE_URL = "http://10.0.3.2:8080/";

    //This is the local url
    //public static final String BASE_URL = "http://127.0.0.1:8080/";

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL);

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        //TODO remove Retrofit logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.setConnectTimeout(5, TimeUnit.SECONDS);
//        okHttpClient.setReadTimeout(5, TimeUnit.SECONDS);
//        okHttpClient.setWriteTimeout(5, TimeUnit.SECONDS);
        okHttpClient.interceptors().add(interceptor);

        final Retrofit retrofit = builder.baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit.create(serviceClass);
    }
}
