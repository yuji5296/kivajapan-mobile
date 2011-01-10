package jp.kivajapan.rssreader;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class HelpActivity extends Activity {
	private WebView  mContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.help);
		mContent = (WebView) findViewById(R.id.WebView02);
		mContent.loadUrl("file:///android_asset/help.html");

	}

}
