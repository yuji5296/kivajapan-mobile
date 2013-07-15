package com.yuji5296.multipleapktest;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;

public class TopActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top);

		int versionCode = 0;
        String versionName = "";
        PackageManager packageManager = this.getPackageManager();
 
        try {
               PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
               versionCode = packageInfo.versionCode;
               versionName = packageInfo.versionName;
               
          } catch (NameNotFoundException e) {
               e.printStackTrace();
          }
 
		TextView TextView_versionCode = (TextView)findViewById(R.id.versionCode);
		TextView_versionCode.setText("versionCode = " + versionCode);
		TextView TextView_versionName = (TextView)findViewById(R.id.versionName);
		TextView_versionName.setText("versionName = " + versionName);

	}
}
