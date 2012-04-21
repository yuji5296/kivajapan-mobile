package com.panasonic.founder.words.dayafterday;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RemoteViews;

public class DadWidgetProvider extends AppWidgetProvider {
	String baseurl = "http://panasonic.co.jp/founder/words/";

	@Override
	public void onEnabled(Context context) {
		Log.v("DayAfterDay", "onEnabled");
		super.onEnabled(context);
	}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.v("DayAfterDay", "onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
        final int N = appWidgetIds.length;

        // このプロバイダーに所属するAppWidgetのそれぞれについて処理を行う
        for (int i=0; i<N ;i++){
                int appWidgetId = appWidgetIds[i];

                // ExampleActivityを起動するIntentを生成
                Intent intent = new Intent(context, DadMainActivity.class);
                
                // それをPendingIntentに変換する
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                // AppWidgetのレイアウト・リソースを取得
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                
                // 第1引数にClickリスナーを設定したいViewのID、
                // 第2引数にClickされた際に発行されるPendingIntentを指定
                views.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
                views.setOnClickPendingIntent(R.id.textView1, pendingIntent);

                // 一日一話取得
                try {

        	    	// 松下幸之助の写真をランダムに表示
        	    	// 画像ファイルのURLは"img/01.jpg"　～ "img/20.jpg"
        	    	Random random = new Random();  
        	    	String imgSrc = baseurl + String.format("img/%02d.jpg", random.nextInt(19)+1);
        	    	Log.v("DayAfterDay",imgSrc);
  				  	Bitmap bmp = getBitmap(imgSrc);
				  	views.setImageViewBitmap(R.id.imageView1, bmp);
        	    	
        	    	//　今日の一言をセット
        	    	// URLは1月2日であれば、"resource/DS0102.HTML"
        	        final Calendar calendar = Calendar.getInstance();
        	        final int month = calendar.get(Calendar.MONTH);
        	        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        	    	String wordSrc = baseurl + "resource/DS" + String.format("%02d%02d", month+1, day) + ".HTML";
        	    	Log.v("DayAfterDay",wordSrc);
//        	    	views.setTextViewText(R.id.textView1,  + translator);
        			URL url = new URL(wordSrc);
        			Document doc = Jsoup.parse(url,0);
        			//http://jsoup.org/cookbook/input/load-document-from-url
        			//Document doc = Jsoup.connect("http://example.com/").get();
        			Elements elements = doc.getElementsByTag("h1");
        			for (Element element : elements) {
        					String word;
        					word = element.text();
        					Log.v("DayAfterDay", word);
        	                views.setTextViewText(R.id.textView1, word);
        			}
                } catch (Exception e) {
        			e.printStackTrace();
        		}

                // IDで指定したAppWidgetの表示を、先ほど取得し、リスナーをセットした
                // RemoteViewsで更新する／初期化するようAppWidgetManagerに指示
                appWidgetManager.updateAppWidget(appWidgetId, views);
        }

	}
	
	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.v("DayAfterDay", "onDeleted");
		super.onDeleted(context, appWidgetIds);
	}
	
	@Override
	public void onDisabled(Context context) {
		Log.v("DayAfterDay", "onDisabled");
		super.onDisabled(context);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.v("DayAfterDay", "onReceive");
		super.onReceive(context, intent);
	}
	
    // 写真をダウンロード
    protected Bitmap getBitmap(String imgSrc) {

//    	this.imageView.setTag(linkHref);

    	// ピックアップ起業家の写真を表示する
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(new HttpGet(imgSrc));
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Bitmap bitm = null;
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream in = null;
			try {
				in = httpEntity.getContent();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bitm = BitmapFactory.decodeStream(in);
		}

		return bitm;  
    }  

}
