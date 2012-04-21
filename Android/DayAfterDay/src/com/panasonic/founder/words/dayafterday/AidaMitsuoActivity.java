package com.panasonic.founder.words.dayafterday;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

public class AidaMitsuoActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        
        WebView  mContent01;
    	mContent01 = (WebView) findViewById(R.id.WebViewRoot);
    	String url = "http://www.google.co.jp/search?hl=ja&q=%E7%9B%B8%E7%94%B0%E3%81%BF%E3%81%A4%E3%82%92&um=1&ie=UTF-8&tbm=isch&source=og&sa=N&tab=wi&biw=1024&bih=675&sei=%20dv-zTs3AFoWimQXa6KHkAw";
    	mContent01.loadUrl(url);
    	 
    }

}
