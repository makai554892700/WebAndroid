package com.mys.www.webdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mys.www.webdemo.view.WebActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("title", "");
//        intent.putExtra("url", "https://h5.isosceles.fund");
//        intent.putExtra("url", "file:///android_asset/web/index.html");
        intent.putExtra("url", "http://localhost:15788");
//        intent.putExtra("url", "https://www.quicktrading.xyz");
        startActivity(intent);
    }

}