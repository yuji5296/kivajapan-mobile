package jp.kivajapan.rssreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailActivity extends Activity {
	private TextView mTitle;
	private TextView mAuthor;
//	private TextView mDescr;
	private WebView  mContent;
	private String mLink;
	private String title;
	private String author;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);
		
		Intent intent = getIntent();

		//タイトルをセット
		title = intent.getStringExtra("TITLE");
		mTitle = (TextView) findViewById(R.id.item_detail_title);
		mTitle.setText(title);

		//翻訳者をセット
		author = intent.getStringExtra("AUTHOR");
		mAuthor = (TextView) findViewById(R.id.item_detail_author);
		mAuthor.setText("翻訳者：" + author);

		//本文をセット
//		String descr = intent.getStringExtra("DESCRIPTION");
//		mDescr = (TextView) findViewById(R.id.item_detail_descr);
//		mDescr.setText(descr);		
		String descr = intent.getStringExtra("CONTENT");
		mContent = (WebView) findViewById(R.id.WebView01);
		//CharSequence sHtml = Html.fromHtml(descr);
//		mContent.loadDataWithBaseURL(null, descr, "text/html", "UTF-8", null);
		mContent.loadData(descr, "text/html", "UTF-8");
		//webViewLoadData(mContent, descr);

		//URLをセット
		mLink = intent.getStringExtra("LINK");
		
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

    // MENUボタンを押したときの処理
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		// デフォルトではアイテムを追加した順番通りに表示する
//		menu.add(0, MENU_ITEM_RELOAD, 0, R.string.update);
//		menu.add(0, MENU_ITEM_HELP,0,R.string.help).setIcon(android.R.drawable.ic_menu_help);
    	//メニューインフレーターを取得
    	MenuInflater inflater = getMenuInflater();
    	//xmlのリソースファイルを使用してメニューにアイテムを追加
    	inflater.inflate(R.menu.menu_detail, menu);
    	//できたらtrueを返す
		return result;
	}

	// MENUの項目を押したときの処理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		switch (item.getItemId()) {
			// 融資（Webサイトに接続）
			case R.id.menu_loan:
				//Toast.makeText(this, mLink, Toast.LENGTH_LONG).show();

				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("融資");
				alert.setMessage("Kivaのサイトから、この起業家に融資しますか？");
				alert.setPositiveButton("はい",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// Yesボタンが押された時の処理
								// Browserを起動
								Uri uri = Uri.parse(mLink);
								Intent intent = new Intent(Intent.ACTION_VIEW,uri);
								startActivity(intent);
							}
						});
				alert.setNegativeButton("いいえ",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// Noボタンが押された時の処理
								Toast.makeText(ItemDetailActivity.this, "残念です。またの機会をお待ちしております。",
										Toast.LENGTH_LONG).show();
							}
						});
				alert.show();
				break;
				// 共有
			case R.id.menu_share:
			    intent = new Intent(Intent.ACTION_SEND);
			    intent.setType("text/plain");  
			    intent.putExtra(Intent.EXTRA_TEXT, mLink);
//			    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("http://kivajapan.jp/"));
//			    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"yuji5296@gmail.com"});  
//			    intent.putExtra(Intent.EXTRA_CC, "yuji5296@gmail.com");  
//			    intent.putExtra(Intent.EXTRA_BCC, "yuji5296@gmail.com");  
			    intent.putExtra(Intent.EXTRA_SUBJECT, title);  
			    
			   try{  
			      startActivityForResult(intent, 0);  
			    }  
			    catch (android.content.ActivityNotFoundException ex) {  
			      Toast.makeText(this, "client not found", Toast.LENGTH_LONG).show();
			    }
				break;
			// 検索
			case R.id.menu_search:
				intent = new Intent(Intent.ACTION_SEARCH);
//				intent.getStringExtra(SearchManager.QUERY);
			    intent.putExtra("query", author);
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    try{  
			      startActivityForResult(intent, 0);  
			    }  
			    catch (android.content.ActivityNotFoundException ex) {  
			      Toast.makeText(this, "client not found", Toast.LENGTH_LONG).show();
			    }
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}
