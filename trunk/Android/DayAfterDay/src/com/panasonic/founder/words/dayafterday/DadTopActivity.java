package com.panasonic.founder.words.dayafterday;

import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class DadTopActivity extends Activity{
    /** Called when the activity is first created. */
//    final Calendar calendar = Calendar.getInstance();
//    final int year = calendar.get(Calendar.YEAR);
//    final int month = calendar.get(Calendar.MONTH);
//    final int day = calendar.get(Calendar.DAY_OF_MONTH);
	String baseurl = "http://panasonic.co.jp/founder/words/";

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top);
        
        WebView  mContent01;
    	mContent01 = (WebView) findViewById(R.id.WebViewTopImage);
    	
    	// 松下幸之助の写真をランダムに表示
    	// 画像ファイルのURLは"img/01.jpg"　～ "img/20.jpg"
    	Random random = new Random();  
    	String img = baseurl + String.format("img/%02d.jpg", random.nextInt(19)+1);
    	Log.v("DayAfterDay",img);
    	mContent01.loadUrl(img);
    	
//        Log.d("DayAfterDay", "Year" + year + "Month" + month + "Day" + day);
//        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker1);
//    	datePicker.init(year,month+1,day, this);
    	 
    }
    
    //写真をタップしたら今日の一話を表示
	public void onClickPicture(View v) {
    	Log.v("DayAfterDay","onClickPicture()");
		Intent intent = new Intent(this, DadMainActivity.class);
		startActivity(intent);
	}

//	public void onDateChanged(DatePicker view, int year, int monthOfYear,
//	            int dayOfMonth) {
//	        // TODO Auto-generated method stub
//	        view.updateDate(year, monthOfYear, dayOfMonth);
//	        Log.d("DayAfterDay", "Year" + year + "Month" + dayOfMonth + "Day" + dayOfMonth);
//		// TODO Auto-generated method stub
//		
//	}

	public void onClick_Michi(View v) {
    	Log.v("DayAfterDay","onClick_Michi()");
		Intent intent = new Intent(this, MichiActivity.class);
		startActivity(intent);
	}
	public void onClick_Aida(View v) {
    	Log.v("DayAfterDay","onClick_Aida()");
		Intent intent = new Intent(this, AidaMitsuoActivity.class);
		startActivity(intent);
	}

}
