package jp.kivajapan.rssreader;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
//import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailActivity extends Activity {
	private TextView mTitle;
	private TextView mAuthor;
	private TextView mSummary;
//	private TextView mDescr;
	private WebView  mContent;
	private ImageView  mImage;
	private String mLink;
	private String title;
	private String author;
	private String image;
	private String summary;
	private String name;
	private String country;
	private String category;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);
		
		Intent intent = getIntent();

		//タイトルをセット
		title = intent.getStringExtra("TITLE");
		mTitle = (TextView) findViewById(R.id.item_detail_title);
		mTitle.setText(title);
		
		//タイトルを起業家名、国名、業種名に分割
		String[] strings1 = title.split("[\\[/\\]]");
		name = strings1[0];
		Log.v("KivaJapanReader", name);
//		String[] strings2 = title.split("/");
		country = strings1[1];
		Log.v("KivaJapanReader", country);
//		String[] strings3 = title.split("\\]");
		category = strings1[2];
		Log.v("KivaJapanReader", category);
		
		
		//翻訳者をセット
		author = intent.getStringExtra("AUTHOR");
		mAuthor = (TextView) findViewById(R.id.item_detail_author);
		mAuthor.setText("翻訳者：" + author);

		//本文をセット
//		String descr = intent.getStringExtra("DESCRIPTION");
//		mDescr = (TextView) findViewById(R.id.item_detail_descr);
//		mDescr.setText(descr);		
//		String descr = intent.getStringExtra("CONTENT");
//		mContent = (WebView) findViewById(R.id.WebView01);
		//CharSequence sHtml = Html.fromHtml(descr);
//		mContent.loadDataWithBaseURL(null, descr, "text/html", "utf-8", null);
		// loadDataだと1.6では良いが、2.3.3で文字化け
//		mContent.loadData(descr, "text/html", "utf-8");
//		webViewLoadData(mContent, descr);
		summary = intent.getStringExtra("SUMMARY");
		mSummary = (TextView) findViewById(R.id.textView1);
		mSummary.setText(summary);
		
		//URLをセット
		mLink = intent.getStringExtra("LINK");
		
		//画像をセット
		image = intent.getStringExtra("IMAGE");
		String[] urls = image.split("/");
		Log.v("KivaJapanReader", "url.length="+String.valueOf(urls.length));
		Log.v("KivaJapanReader", "url[0]="+urls[0]);
		Log.v("KivaJapanReader", "url[1]="+urls[1]);
		Log.v("KivaJapanReader", "url[2]="+urls[2]);
		Log.v("KivaJapanReader", "url[3]="+urls[3]);
		Log.v("KivaJapanReader", "url[4]="+urls[4]);
		Log.v("KivaJapanReader", "url[5]="+urls[5]);
		image = "http://www.kiva.org/img/w400/" + urls[5];
		Log.v("KivaJapanReader", image);
		mImage = (ImageView) findViewById(R.id.imageView1);
    	DownloadTask task = new DownloadTask(mImage);  
		task.execute(image);
		
		
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
		// 詳細情報(KivaJapanのサイトに接続)
		case R.id.menu_detail:
			// 融資（Webサイトに接続）
			// Kivaの起業家ページをブラウザで開く
			Uri uri = Uri.parse(mLink);
			intent = new Intent(Intent.ACTION_VIEW,uri);
			startActivity(intent);
			break;

		case R.id.menu_loan:
				//Toast.makeText(this, mLink, Toast.LENGTH_LONG).show();

				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle(getString(R.string.loan));
				alert.setMessage(getString(R.string.message_loan_confirm));
				alert.setPositiveButton(getString(R.string.yes),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// Yesボタンが押された時の処理
								// Browserを起動
//								Uri uri = Uri.parse(mLink);
								// URLから企業家IDを取得
								String[] str = mLink.split("=");
								String k_guid = str[1];
								// Kivaの起業家ページをブラウザで開く
								Uri uri = Uri.parse("http://www.kiva.org/lend/" + k_guid);
								Intent intent = new Intent(Intent.ACTION_VIEW,uri);
								startActivity(intent);
							}
						});
				alert.setNegativeButton(getString(R.string.no),
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// Noボタンが押された時の処理
								Toast.makeText(ItemDetailActivity.this, getString(R.string.message_loan_cancel),
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
			case R.id.menu_search_name:
				intent = new Intent(Intent.ACTION_SEARCH);
			    intent.putExtra("query", name);
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    try{  
			      startActivityForResult(intent, 0);  
			    }  
			    catch (android.content.ActivityNotFoundException ex) {  
			      Toast.makeText(this, "client not found", Toast.LENGTH_LONG).show();
			    }
				break;
			case R.id.menu_search_country:
				intent = new Intent(Intent.ACTION_SEARCH);
			    intent.putExtra("query", country);
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    try{  
			      startActivityForResult(intent, 0);  
			    }  
			    catch (android.content.ActivityNotFoundException ex) {  
			      Toast.makeText(this, "client not found", Toast.LENGTH_LONG).show();
			    }
				break;
			case R.id.menu_search_category:
				intent = new Intent(Intent.ACTION_SEARCH);
			    intent.putExtra("query", category);
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
