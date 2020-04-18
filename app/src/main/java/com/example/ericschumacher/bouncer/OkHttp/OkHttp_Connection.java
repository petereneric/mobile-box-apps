package com.example.ericschumacher.bouncer.OkHttp;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ericschumacher.bouncer.Interfaces.Interface_HttpOkCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp_Connection {

    static Call get(String url, String json, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static void getResponse(final Context context, final AppCompatActivity activity, String url, final Interface_HttpOkCallback iCallback) {
        get(url, "", new Callback() {
            Handler mainHandler = new Handler(context.getMainLooper());

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.i("Hier ist", "doch was");
                mainHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if (!response.isSuccessful()) {
                            try {
                                iCallback.onResult(response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                    }
                });
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Handle UI here
                        try {
                            iCallback.onResult(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                /*
                if (response.isSuccessful()) {
                    Log.i("Da kommt", "was2)");
                    iCallback.onResult(response.body().string());
                } else {
                }
                */

            }
        });
    }
}
