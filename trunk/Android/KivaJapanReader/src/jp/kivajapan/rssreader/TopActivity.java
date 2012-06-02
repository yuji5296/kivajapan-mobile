package jp.kivajapan.rssreader;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class TopActivity extends Activity {
	String kivaUrl = "http://kivajapan.jp/";
	String kivaRss = "http://kivajapan.jp/atom.xml";
	String testUrl="file:///android_asset/test.html";
	String imageUrl = "http://www.kiva.org/img/w450h360/776940.jpg";
	String html = "<img src='http://www.kiva.org/img/w450h360/776940.jpg' width='100%'>";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top);
        
    	//ピックアップ起業家の写真を取得して表示
    	getPickupWithJsoup();
    	
    	//最終更新時間を更新
//    	PreferenceManager.setDefaultSharedPreferences(context).getString("widgetRefreshInterval", "60");
//    	PreferenceManager.setDefaultValues(this, resId, readAgain);
//    	Preference searchbooksPref;  

//    	SharedPreferences.getString(key, "rssLastUpdate");
	}
	
	//Kiva Japan Topページのピックアップ起業家画像を表示
	public void getPickupWithJsoup() {	
    	ImageView animWindow = (ImageView)this.findViewById(R.id.imageView1);
    	DownloadPickupTask task = new DownloadPickupTask(this,animWindow);  
		task.execute(kivaUrl);
   	}	
	
	public void onClick_Pickup(View v) {
		String url = (String) v.getTag();
		Uri uri = Uri.parse("http://kivajapan.jp" + url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	
	public void onClickButton1(View v) {
		// RSSリーダ表示
		Intent intent = new Intent(this, RssReaderActivity.class);
		startActivity(intent);
	}

	public void onClickButton2(View v) {
		// Kiva Japanについて説明
		Uri uri = Uri
				.parse("http://kivajapan.jp/?page=Bureau&action=beginners");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	public void onClickButton3(View v) {
		// 翻訳ページにジャンプ
		Uri uri = Uri
				.parse("http://kivajapan.jp/?page=Bureau&action=about_translator");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	// YouTubeでの検索
	public void onClickButton4(View v) {
		Intent intent = new Intent(Intent.ACTION_SEARCH);
		intent.setPackage("com.google.android.youtube");
		intent.putExtra("query", "Kiva Japan");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	// MENUボタンを押したときの処理
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		// デフォルトではアイテムを追加した順番通りに表示する
		// menu.add(0, MENU_ITEM_RELOAD, 0, R.string.update);
		// menu.add(0,
		// MENU_ITEM_HELP,0,R.string.help).setIcon(android.R.drawable.ic_menu_help);
		// メニューインフレーターを取得
		MenuInflater inflater = getMenuInflater();
		// xmlのリソースファイルを使用してメニューにアイテムを追加
		inflater.inflate(R.menu.menu, menu);
		// できたらtrueを返す
		return result;
	}

	// MENUの項目を押したときの処理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		switch (item.getItemId()) {
		// 更新
		case R.id.menu_update:
			getPickupWithJsoup();
			break;
		// 設定
		case R.id.menu_preference:
			intent = new Intent(this, KivaJapanPreferenceActivity.class);
			startActivity(intent);
			break;
		// 情報
		case R.id.menu_info:
			intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			break;

		// ヘルプ
		case R.id.menu_help:
			intent = new Intent(this, HelpActivity.class);
			startActivity(intent);
			break;

		// 共有
		case R.id.menu_share:
			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			// intent.putExtra(Intent.EXTRA_TEXT,
			// "今、僕らにできる社会貢献活動がここにあります。Kiva Japanはインターネットを通じて発展途上国の起業家に融資できるサイトです。");
//			intent.putExtra(Intent.EXTRA_TEXT, "http://kivajapan.jp/");
			intent.putExtra(Intent.EXTRA_TEXT, "https://sites.google.com/site/kivajapanrssreader/");
			// intent.putExtra(Intent.EXTRA_STREAM,
			// Uri.parse("http://kivajapan.jp/"));
			// intent.putExtra(Intent.EXTRA_EMAIL, new
			// String[]{"yuji5296@gmail.com"});
			// intent.putExtra(Intent.EXTRA_CC, "yuji5296@gmail.com");
			// intent.putExtra(Intent.EXTRA_BCC, "yuji5296@gmail.com");
			intent.putExtra(Intent.EXTRA_SUBJECT, "Kiva Japan");

			try {
				startActivityForResult(intent, 0);
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(this, "client not found", Toast.LENGTH_LONG)
						.show();
			}
			break;
		// 検索
		case R.id.menu_search:
//			onSearchRequested();
			intent = new Intent();
			intent.setAction("android.intent.action.SEARCH");
//			intent.putExtra(SearchManager.QUERY,"Kiva");
			intent.putExtra("query", "Kiva");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			try {
//				startActivityForResult(intent, 0);
				startActivity(Intent.createChooser(intent,getString(R.string.app_name)));
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(this, "client not found", Toast.LENGTH_LONG)
						.show();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}

