package com.jumbomob.invistoo.model.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.jumbomob.invistoo.util.ConstantsUtil;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * @author maiko.trindade
 * @since 04/02/2016
 */
public class BaseNetworkConfig {

    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(ConstantsUtil.BASE_URL);

    public static <S> S createService(Class<S> serviceClass, String baseUrl) {

        //TODO remove Retrofit logging
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient();
//        okHttpClient.setConnectTimeout(5, TimeUnit.SECONDS);
//        okHttpClient.setReadTimeout(5, TimeUnit.SECONDS);
//        okHttpClient.setWriteTimeout(5, TimeUnit.SECONDS);
        okHttpClient.interceptors().add(interceptor);
        GsonBuilder gsonBuilder = new GsonBuilder();

// Register an adapter to manage the date types as long values
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = gsonBuilder.create();

        final Retrofit retrofit = builder.baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        return retrofit.create(serviceClass);
    }

}
