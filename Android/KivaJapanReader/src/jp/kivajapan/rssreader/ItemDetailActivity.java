package jp.kivajapan.rssreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
//import android.text.Html;
import android.webkit.WebView;
import android.widget.TextView;

public class ItemDetailActivity extends Activity {
	private TextView mTitle;
//	private TextView mDescr;
	private WebView  mContent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);
		
		Intent intent = getIntent();

		//タイトルをセット
		String title = intent.getStringExtra("TITLE");
		mTitle = (TextView) findViewById(R.id.item_detail_title);
		mTitle.setText(title);

		//翻訳者をセット
		String author = "翻訳者：" + intent.getStringExtra("AUTHOR");
		mTitle = (TextView) findViewById(R.id.item_detail_author);
		mTitle.setText(author);

		//本文をセット
//		String descr = intent.getStringExtra("DESCRIPTION");
//		mDescr = (TextView) findViewById(R.id.item_detail_descr);
//		mDescr.setText(descr);		
		String descr = intent.getStringExtra("CONTENT");
		mContent = (WebView) findViewById(R.id.WebView01);
		//CharSequence sHtml = Html.fromHtml(descr);
		mContent.loadDataWithBaseURL(null, descr, "text/html", "UTF-8", null);
		//webViewLoadData(mContent, descr);
		
		
	}
	
    public final static void webViewLoadData(WebView web, String html) {
        StringBuilder buf = new StringBuilder(html.length());
        for (char c : html.toCharArray()) {
            switch (c) {
              case '#':  buf.append("%23"); break;
              case '%':  buf.append("%25"); break;
              case '\'': buf.append("%27"); break;
              case '?':  buf.append("%3f"); break;                
              default:
                buf.append(c);
                break;
            }
        }
        web.loadData(buf.toString(), "text/html", "utf-8");
    }
}
