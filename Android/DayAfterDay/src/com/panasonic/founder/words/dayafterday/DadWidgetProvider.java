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

        // ���̃v���o�C�_�[�ɏ�������AppWidget�̂��ꂼ��ɂ��ď������s��
        for (int i=0; i<N ;i++){
                int appWidgetId = appWidgetIds[i];

                // ExampleActivity���N������Intent�𐶐�
                Intent intent = new Intent(context, DadMainActivity.class);
                
                // �����PendingIntent�ɕϊ�����
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                // AppWidget�̃��C�A�E�g�E���\�[�X���擾
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                
                // ��1������Click���X�i�[��ݒ肵����View��ID�A
                // ��2������Click���ꂽ�ۂɔ��s�����PendingIntent���w��
                views.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
                views.setOnClickPendingIntent(R.id.textView1, pendingIntent);

                // �����b�擾
                try {

        	    	// �����K�V���̎ʐ^�������_���ɕ\��
        	    	// �摜�t�@�C����URL��"img/01.jpg"�@�` "img/20.jpg"
        	    	Random random = new Random();  
        	    	String imgSrc = baseurl + String.format("img/%02d.jpg", random.nextInt(19)+1);
        	    	Log.v("DayAfterDay",imgSrc);
  				  	Bitmap bmp = getBitmap(imgSrc);
				  	views.setImageViewBitmap(R.id.imageView1, bmp);
        	    	
        	    	//�@�����̈ꌾ���Z�b�g
        	    	// URL��1��2���ł���΁A"resource/DS0102.HTML"
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

                // ID�Ŏw�肵��AppWidget�̕\�����A��قǎ擾���A���X�i�[���Z�b�g����
                // RemoteViews�ōX�V����^����������悤AppWidgetManager�Ɏw��
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
	
    // �ʐ^���_�E�����[�h
    protected Bitmap getBitmap(String imgSrc) {

//    	this.imageView.setTag(linkHref);

    	// �s�b�N�A�b�v�N�ƉƂ̎ʐ^��\������
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
