package com.example.okhttp;

import android.app.Application;

import com.example.okhttp.data.remote.HerokuApi;
import com.example.okhttp.data.remote.RetrofitClient;

public class App extends Application {

    private static RetrofitClient retrofitClient;
    public static HerokuApi api;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitClient = new RetrofitClient();
        api = retrofitClient.provideApi();
    }
}
