package jp.kivajapan.rssreader;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

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
}