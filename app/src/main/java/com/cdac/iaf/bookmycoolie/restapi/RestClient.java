package com.cdac.iaf.bookmycoolie.restapi;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static final String BASE_URL = "http://10.208.22.104:8080";

    private static Retrofit retrofit = null;

    static Gson gson = new GsonBuilder().setLenient().create();

    public static Retrofit getRetrofitClient(/*Context context*/){

        //OkHttpClient okHttpClient = SSLPinning.getOkHttpClient(context);

        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //.client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}