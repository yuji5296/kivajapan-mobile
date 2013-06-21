package jp.kivajapan.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

//URIで指定した画像を読み込みImageViewに表示する。
public class DownloadPickupTask extends AsyncTask<String, Void, Bitmap> {
	private TopActivity activity; 
    // アイコンを表示するビュー  
    private ImageView imageView;  
  
    // コンストラクタ  
    public DownloadPickupTask(TopActivity activity, ImageView imageView) {
    	this.activity = activity;
        this.imageView = imageView;
    	// プログレス画像を表示（更新時に表示する為にここで設定）
//        this.imageView.setImageResource(R.drawable.progresscircle_small);

    	// アニメーションリソースをロード
//        Animation anim = AnimationUtils.loadAnimation(this.imageView.getContext(), R.anim.progresscircle);
//    	anim.setRepeatMode(Animation.RESTART);

    	// アニメーション開始
//    	this.imageView.startAnimation(anim);

    }  
    @Override  
	protected void onPreExecute() {
    	
	}

    // バックグラウンドで実行する処理  
    @Override  
    protected Bitmap doInBackground(String... urls) {
		String linkHref = null;
		String imgSrc = null;

		// HTMLを取得してピックアップ起業家の写真のURLを取得する。
		try {
			URL url = new URL(urls[0]);
			Document doc = Jsoup.parse(url,0);
			//http://jsoup.org/cookbook/input/load-document-from-url
			//Document doc = Jsoup.connect("http://example.com/").get();
		
			Element content = doc.getElementById("pthumb");
			Elements links = content.getElementsByTag("a");
			for (Element link : links) {
			  linkHref = link.attr("href");
			}
			Elements imgs = content.getElementsByTag("img");
			for (Element img : imgs) {
				  imgSrc = img.attr("src");
			}
		} catch (Exception e) {
        	return null;
//			e.printStackTrace();
		}

    	this.imageView.setTag(linkHref);

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
  
    // メインスレッドで実行する処理  
    @Override  
    protected void onPostExecute(Bitmap result) {
    	if (result == null){
        	Toast.makeText(activity, "No network", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	this.imageView.clearAnimation();
    	this.imageView.setImageBitmap(result);
		this.imageView.invalidate();
    }

}  