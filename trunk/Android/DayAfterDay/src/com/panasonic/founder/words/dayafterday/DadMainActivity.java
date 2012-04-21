package com.panasonic.founder.words.dayafterday;

import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.DatePicker;
import android.widget.Toast;

public class DadMainActivity extends Activity {
    /** Called when the activity is first created. */
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
	String baseurl = "http://panasonic.co.jp/founder/words/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
//        WebView  mContent01;
//    	mContent01 = (WebView) findViewById(R.id.WebView01);
//    	
//    	// 松下幸之助の写真をランダムに表示
//    	// 画像ファイルのURLは"img/01.jpg"　～ "img/20.jpg"
//    	Random random = new Random();  
//    	String img = baseurl + String.format("img/%02d.jpg", random.nextInt(19)+1);
//    	Log.v("DayAfterDay",img);
//    	mContent01.loadUrl(img);

        onReload();
    }

    public void onReload(){
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        WebView  mContent02;
    	mContent02 = (WebView) findViewById(R.id.WebView02);
   	
    	// 今日の一言を表示
    	// URLは1月2日であれば、"resource/DS0102.HTML"
    	String word = baseurl + "resource/DS" + String.format("%02d%02d", month+1, day) + ".HTML";
    	Log.v("DayAfterDay",word);
    	mContent02.loadUrl(word);
    	
    }
    
    
    // MENUボタンを押したときの処理
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
    	//メニューインフレーターを取得
    	MenuInflater inflater = getMenuInflater();
    	//xmlのリソースファイルを使用してメニューにアイテムを追加
    	inflater.inflate(R.menu.menu_main, menu);
    	//できたらtrueを返す
		return result;
	}

	// MENUの項目を押したときの処理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Uri uri;
		Intent intent;
		switch (item.getItemId()) {
			case R.id.today:
				calendar = Calendar.getInstance();
				onReload();
				break;
			case R.id.tomorrow:
				calendar.add(Calendar.DAY_OF_MONTH,1);
				onReload();
				break;
			case R.id.yesterday:
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				onReload();
				break;
			case R.id.nextmonth:
				calendar.add(Calendar.MONTH,1);
				onReload();
				break;
			case R.id.lastmonth:
				calendar.add(Calendar.MONTH, -1);
				onReload();
				break;
			case R.id.datepicker:
				//日付選択ダイアログを表示
		        final DatePickerDialog datePickerDialog = new DatePickerDialog(
		        		this,
		        		new DatePickerDialog.OnDateSetListener() {
//		        			@Override
		        			public void onDateSet(DatePicker view, int y, int m, int d) {
		        				//設定された日付をトースト表示
		        				Toast.makeText(DadMainActivity.this,
		        						String.valueOf(y) + "/" +
		        						String.valueOf(m + 1) + "/" +
		        						String.valueOf(d),
		        						Toast.LENGTH_SHORT).show();
		        				//日付を上書き
		        				calendar.set(Calendar.YEAR, y);
		        				calendar.set(Calendar.MONTH, m);
		        				calendar.set(Calendar.DAY_OF_MONTH, d);
		        		        onReload();

		                    }
		        		},
		        		year, month, day);
		        datePickerDialog.show();
		        break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onClick_tomorrow(View v){
		calendar.add(Calendar.DAY_OF_MONTH,1);
		onReload();
	}
	public void onClick_yesterday(View v){
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		onReload();
	}
	public void onClick_datepicker(View v){
		//日付選択ダイアログを表示
        final DatePickerDialog datePickerDialog = new DatePickerDialog(
        		this,
        		new DatePickerDialog.OnDateSetListener() {
//        			@Override
        			public void onDateSet(DatePicker view, int y, int m, int d) {
        				//設定された日付をトースト表示
        				Toast.makeText(DadMainActivity.this,
        						String.valueOf(y) + "/" +
        						String.valueOf(m + 1) + "/" +
        						String.valueOf(d),
        						Toast.LENGTH_SHORT).show();
        				//日付を上書き
        				calendar.set(Calendar.YEAR, y);
        				calendar.set(Calendar.MONTH, m);
        				calendar.set(Calendar.DAY_OF_MONTH, d);
        		        onReload();
                    }
        		},
        		year, month, day);
        datePickerDialog.show();
	}
	
}