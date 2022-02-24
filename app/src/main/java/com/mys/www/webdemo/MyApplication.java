package com.mys.www.webdemo;

import android.app.Application;
import android.util.Log;

import com.yanzhenjie.andserver.AndServer;
import com.yanzhenjie.andserver.Server;
import com.yanzhenjie.andserver.filter.HttpCacheFilter;
import com.yanzhenjie.andserver.website.AssetsWebsite;

import java.util.concurrent.TimeUnit;

public class MyApplication extends Application {

    private boolean started;

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(() -> {
            try {
                Server server = AndServer.serverBuilder()
                        .port(15788)
                        .timeout(10, TimeUnit.SECONDS)
                        .website(new AssetsWebsite(getAssets(), "test"))
                        .filter(new HttpCacheFilter())
                        .build();
                server.startup();
                started = true;
            } catch (Exception e) {
                Log.e("-----1", "e=" + e);
            }
        }).start();
    }
}
