package com.ghhwer.describeit.access;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ghhwer.describeit.access.ApiConstants.DATAMUSE_URL;

public class DatamuseRetrofit {
    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(DATAMUSE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static DatamuseGet getWordMapService(){
        return getRetrofitInstance().create(DatamuseGet.class);
    }
}