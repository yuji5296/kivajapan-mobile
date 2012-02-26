package jp.kivajapan.rssreader;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
//import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class AboutActivity extends Activity {	
	@Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);				
		setContentView(R.layout.about);		
		
		//Manifestからバージョン名を取得して表示
		try {
		    String packegeName = getPackageName();
		    PackageInfo packageInfo;
			packageInfo = getPackageManager().getPackageInfo(packegeName, PackageManager.GET_META_DATA);
			TextView version = (TextView)findViewById(R.id.Version);
			version.setText("Version."+ packageInfo.versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// MENUボタンを押したときの処理
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
    	//メニューインフレーターを取得
    	MenuInflater inflater = getMenuInflater();
    	//xmlのリソースファイルを使用してメニューにアイテムを追加
    	inflater.inflate(R.menu.menu_about, menu);
    	//できたらtrueを返す
		return result;
	}

	// MENUの項目を押したときの処理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Uri uri;
		Intent intent;
		switch (item.getItemId()) {
			case R.id.menu_mail:
				
				uri=Uri.parse("mailto:" + getString(R.string.mail));
//				uri=Uri.parse("mailto:yuji5296@gmail.com");
				intent=new Intent(Intent.ACTION_SENDTO,uri);
//				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback about Kiva Japan Reader");
				intent.putExtra(Intent.EXTRA_TEXT,"Input your comment, request, etc...");
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				try {
					startActivityForResult(intent, 0);
				} catch (android.content.ActivityNotFoundException ex) {
					Toast.makeText(this, "client not found", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case R.id.menu_goto_site:
				uri = Uri.parse(getString(R.string.url_site));
				intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				break;
				
			case R.id.menu_goto_kiva:
				uri = Uri.parse(getString(R.string.url_kiva));
				intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				break;

			case R.id.menu_goto_kivajapan:
				uri = Uri.parse(getString(R.string.url_kivajapan));
				intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				break;
			case R.id.menu_goto_kivadroid:
				PackageManager pm = getPackageManager();
				try {
					pm.getApplicationInfo("com.chimpler.kivadroid", 0);
					//アプリが存在しない場合、例外発生。
					intent = new Intent(Intent.ACTION_MAIN);
					intent.setClassName("com.chimpler.kivadroid", "com.chimpler.kivadroid.HomeActivity");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} catch (ActivityNotFoundException e) {
					uri=Uri.parse("market://search?q=com.chimpler.kivadroid");
					intent=new Intent(Intent.ACTION_VIEW,uri);
					startActivity(intent); 
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					uri=Uri.parse("market://search?q=com.chimpler.kivadroid");
					intent=new Intent(Intent.ACTION_VIEW,uri);
					startActivity(intent); 
					e.printStackTrace();
				}
				break;
			case R.id.menu_goto_facebook:
				uri = Uri.parse("http://www.facebook.com/pages/KivaJapan-Reader/274413395920688");
				intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				break;
			case R.id.menu_goto_twitter:
				uri = Uri.parse("http://twitter.com/#!/KivaJapanReader");
				intent = new Intent(Intent.ACTION_VIEW,uri);
				startActivity(intent);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}