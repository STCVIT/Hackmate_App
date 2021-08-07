package com.example.hackmate.util;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.hackmate.util.Constants.BASE_URL;

public class RetrofitInstance {
    private static Retrofit retrofit;
//    NullOnEmptyConverterFactory nullOnEmptyConverterFactory = new NullOnEmptyConverterFactory();

    public static Retrofit getRetrofitInstance() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
