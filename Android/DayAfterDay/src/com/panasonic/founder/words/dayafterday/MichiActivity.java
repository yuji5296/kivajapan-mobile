package com.panasonic.founder.words.dayafterday;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MichiActivity extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        
        WebView  mContent01;
    	mContent01 = (WebView) findViewById(R.id.WebViewRoot);
		String url = "file:///android_asset/michi.html";
    	mContent01.loadUrl(url);    	
    	 
    }

}
