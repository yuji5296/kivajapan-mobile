package jp.kivajapan.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
//import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
//import android.net.Uri;
import android.preference.PreferenceManager;
//import android.view.View;
//import android.widget.ImageView;
import android.widget.RemoteViews;
//import android.widget.Toast;


public class KivaJapanWidgetProvider extends AppWidgetProvider {
	public static final String RSS_FEED_URL = "http://kivajapan.jp/atom.xml";
//	private ArrayList<Item> mItems;
//	private RssListAdapter mAdapter;

	@Override
	public void onEnabled(Context context) {
//		Log.v("KivaJapanReaderWidget", "onEnabled");
		super.onEnabled(context);
		
		//クリック時に実行するIntentの設定
//		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
//		Intent clickIntent = new Intent(context, AboutActivity.class);
//		clickIntent.setAction("jp.kivajapan.rssreader.ACTION_MY_CLICK");
//		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, clickIntent, 0);
//		remoteViews.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
		
//		// Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
//		mItems = new ArrayList<Item>();
//		mAdapter = new RssListAdapter(context, mItems);
//
//		// タスクを起動する
//		RssParserTask task = new RssParserTask(context, mAdapter);
//		task.execute(RSS_FEED_URL);
	}
	
	@Override
	public void onUpdate(final Context context, final AppWidgetManager appWidgetManager,
			final int[] appWidgetIds) {
//		Log.v("KivaJapanReaderWidget", "onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);

		int widgetRefreshInterval = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context).getString("widgetRefreshInterval", "60"));
		
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {		
			@Override
			public void run() {
				refreshWidgetView(context, appWidgetManager, appWidgetIds);
			}
		}, 1, widgetRefreshInterval * 60L * 1000L);
	}
	private void refreshWidgetView(Context context,AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
                  
        final int N = appWidgetIds.length;

        // このプロバイダーに所属するAppWidgetのそれぞれについて処理を行う
        for (int i=0; i<N ;i++){
                int appWidgetId = appWidgetIds[i];

                // ExampleActivityを起動するIntentを生成
                Intent intent = new Intent(context, TopActivity.class);
                
                // それをPendingIntentに変換する
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

                // AppWidgetのレイアウト・リソースを取得
                RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
                
                // 第1引数にClickリスナーを設定したいViewのID、
                // 第2引数にClickされた際に発行されるPendingIntentを指定
                views.setOnClickPendingIntent(R.id.imageView1, pendingIntent);
                views.setOnClickPendingIntent(R.id.linearLayout1, pendingIntent);

                // 更新時の時刻を取得して表示
                Date update = new Date();
//        		Log.v("KivaJapanReaderWidget", update.toLocaleString());
                views.setTextViewText(R.id.textView_small, update.toLocaleString());

                // KivaJapanのピックアップ起業家情報を取得

        		// HTMLを取得してピックアップ起業家の写真のURLを取得する。
        		try {
//            		String linkHref;
            		String imgSrc;
            		String kivaUrl = "http://kivajapan.jp/";
            		String name;
            		String country = "国";
            		String category = "業種";
            		String translator = "翻訳家";
        			URL url = new URL(kivaUrl);
        			Document doc = Jsoup.parse(url,0);
        			//http://jsoup.org/cookbook/input/load-document-from-url
        			//Document doc = Jsoup.connect("http://example.com/").get();
        			
        		
        			//写真
        			Element content = doc.getElementById("pthumb");
//        			Elements links = content.getElementsByTag("a");
//        			for (Element link : links) {
//        			  linkHref = link.attr("href");
//        			}
        			Elements imgs = content.getElementsByTag("img");
        			for (Element img : imgs) {
        				  imgSrc = img.attr("src");
        				  //http://www.kiva.org/img/w450h360/852894.jpg
        				  //http://www.kiva.org/img/w160h120/852894.jpg
//        				  Log.v("KivaJapanReaderWidget", imgSrc);
        				  String[] urls = imgSrc.split("/");
//        				  Log.v("KivaJapanReaderWidget", "url.length="+String.valueOf(urls.length));
//        				  Log.v("KivaJapanReaderWidget", "url[0]="+urls[0]);
//        				  Log.v("KivaJapanReaderWidget", "url[1]="+urls[1]);
//        				  Log.v("KivaJapanReaderWidget", "url[2]="+urls[2]);
//        				  Log.v("KivaJapanReaderWidget", "url[3]="+urls[3]);
//        				  Log.v("KivaJapanReaderWidget", "url[4]="+urls[4]);
//        				  Log.v("KivaJapanReaderWidget", "url[5]="+urls[5]);
        				  imgSrc = "http://www.kiva.org/img/w160h120/" + urls[5];
//        				  Log.v("KivaJapanReaderWidget", imgSrc);
        				  Bitmap bmp = getBitmap(imgSrc);
        				  if(bmp != null){
        					  views.setImageViewBitmap(R.id.imageView1, bmp);
        				  }
        			}
        			
        			//名前
        			//	<div class="main-pickup-name"> 
        			//	<a href="/entrepreneurs/?k_guid=328860">Maristela Osorio さん</a>
        			//	</div> 
        			Elements elements = doc.getElementsByClass("main-pickup-name");
        			for (Element element : elements) {
        				Elements links1 = element.getElementsByTag("a");
        				for (Element link1 : links1) {
        					name = link1.text();
//        					Log.v("KivaJapanReaderWidget", name);
        	                views.setTextViewText(R.id.textView_large, name);
        				}
        			}
        			
        			//国旗
        			//<div class="main-pickup-flag">
        			//<img src="/img/webpage/national_flags/VN.jpg" height="26" alt="" />
        			//</div>
        			Elements elements3 = doc.getElementsByClass("main-pickup-flag");
        			for (Element element : elements3) {
        				Elements flags = element.getElementsByTag("img");
	        			for (Element img : flags) {
	        				  imgSrc = img.attr("src");
	        				  // /img/webpage/national_flags/VN.jpg
	        				  // http://kivajapan.jp/img/webpage/national_flags/VN.jpg
	        				  imgSrc = kivaUrl + imgSrc;
//	        				  Log.v("KivaJapanReaderWidget", imgSrc);
	        				  Bitmap bmp = getBitmap(imgSrc);
	        				  if(bmp != null){
	        					  views.setImageViewBitmap(R.id.imageView2, bmp);
	        				  }
	        			}
        			}

        			//国取得
        			//<span class="main-pickup-country">フィリピン</span> 			
        			Elements elements2 = doc.getElementsByClass("main-pickup-country");
        			for (Element element : elements2) {
        				Elements spans = element.getElementsByTag("span");
        				for (Element span : spans) {
        					country = span.text();
//        					Log.v("KivaJapanReaderWidget", country);
        				}
        			}
        			
        			//業種取得
        			//  <dl class="main-pickup-status-data">
        			//  <dt>業種：</dt>
        			//  <dd>一般商店</dd>
        			Elements elements4 = doc.getElementsByClass("main-pickup-status-data");
        			for (Element element : elements4) {
        				Elements dds = element.getElementsByTag("dd");
        				for (Element dd : dds) {
        					category = dd.text();
//        					Log.v("KivaJapanReaderWidget", category);
        					break;
        				}
        			}
        			
        			// 国/業種を表示
	                views.setTextViewText(R.id.textView_medium, country + "/" + category);

	                //　翻訳者取得
//	                <!-- 翻訳家詳細 -->
//	                <div class="main-pickup-translator">
//	                <a href="../translators/?page=Profile&tr_id=497"><img src="../translators/prof_img/0.jpg" height="20" /></a>
//	                翻訳者 ： <a href="../translators/?page=Profile&tr_id=497">Yukie</a>
//	                </div>
        			Elements elements5 = doc.getElementsByClass("main-pickup-translator");
        			for (Element element : elements5) {
        				Elements anchors = element.getElementsByTag("a");
        				for (Element anchor : anchors) {
        					translator = anchor.text();
//        					Log.v("KivaJapanReaderWidget", translator);
        	                views.setTextViewText(R.id.textView_small, "翻訳者：" + translator);
        				}
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
//		Log.v("KivaJapanReaderWidget", "onDeleted");
		super.onDeleted(context, appWidgetIds);
	}
	
	@Override
	public void onDisabled(Context context) {
//		Log.v("KivaJapanReaderWidget", "onDisabled");
		super.onDisabled(context);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
//		Log.v("KivaJapanReaderWidget", "onReceive");
		super.onReceive(context, intent);
	}
	
    // KivaJapanのピックアップ起業家情報を取得
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
