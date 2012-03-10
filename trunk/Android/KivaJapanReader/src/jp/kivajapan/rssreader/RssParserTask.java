package jp.kivajapan.rssreader;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

//import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Xml;
import android.widget.TextView;
import android.widget.Toast;

public class RssParserTask extends AsyncTask<String, Integer, RssListAdapter> {
	private RssReaderActivity mActivity;
	private RssListAdapter mAdapter;
	private int return_code = 0;
	final private int NONETWORK = 1;
	final private int NOUPDATE = 2;
	
	
//	private ProgressDialog mProgressDialog;

	// コンストラクタ
	public RssParserTask(RssReaderActivity activity, RssListAdapter adapter) {
		mActivity = activity;
		mAdapter = adapter;
	}

//	public RssParserTask(Context context, RssListAdapter adapter) {
//		// TODO Auto-generated constructor stub
//		mActivity = (RssReaderActivity) context;
//		mAdapter = adapter;
//	}

	// タスクを実行した直後にコールされる
	@Override
	protected void onPreExecute() {
		// プログレスバーを表示する
//		mProgressDialog = new ProgressDialog(mActivity);
////		mProgressDialog.setTitle(R.string.update);
//		mProgressDialog.setMessage("Now loding...");
//		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		mProgressDialog.show();
		//DBからItemを読み込みAdapterにセット
//		RssListAdapter result = null;
//		mActivity.setListAdapter(result);
	}

	// バックグラウンドにおける処理を担う。タスク実行時に渡された値を引数とする
	@Override
	protected RssListAdapter doInBackground(String... params) {
		RssListAdapter result = null;
		try {
			// HTTP経由でアクセスし、InputStreamを取得する
			URL url = new URL(params[0]);
			InputStream is = url.openConnection().getInputStream();
			result = parseXml(is);
		} catch (Exception e) {
			return_code = NONETWORK;
			e.printStackTrace();
			return null;
		}
		// ここで返した値は、onPostExecuteメソッドの引数として渡される
		return result;
	}

	// メインスレッド上で実行される
	@Override
	protected void onPostExecute(RssListAdapter result) {
//		mProgressDialog.dismiss();
    	if (result == null){
    		switch (return_code){
    		case NONETWORK:
            	Toast.makeText(mActivity, "Network error", Toast.LENGTH_SHORT).show();
    			break;
    		case NOUPDATE:
            	Toast.makeText(mActivity, "No update", Toast.LENGTH_SHORT).show();
    			break;
    		}
    	}else{
        	Toast.makeText(mActivity, "Updated", Toast.LENGTH_SHORT).show();
        	result = mActivity.readAdapter();
    		mActivity.setListAdapter(result);
    	}
	}

	// XMLをパースする
	public RssListAdapter parseXml(InputStream is) throws IOException, XmlPullParserException {
		//データベースを作成
		RssDatabase db = new RssDatabase(mActivity);
		db.create("write");
		
		//パーサー作成
		XmlPullParser parser = Xml.newPullParser();
		//parser.setProperty(XmlPullParser.FEATURE_VALIDATION , true);　//すぐにパースエラーになってしまう
		try {
//			parser.setInput(is, null);
			// Kiva JapanのフィードはUTF-8だが、ゴミ(0x3E)が含まれる為、not well-formedとなりパースに失敗する
			// ISO-8859-1としてパースすれば成功するが文字化けする為、getBytesでUTF-8に変換する
			parser.setInput(is, "ISO-8859-1");
			int eventType = parser.getEventType();
			Item currentItem = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tag = null;
				switch (eventType) {
					case XmlPullParser.START_TAG:
						tag = parser.getName();
						if (tag.equals("entry")) {
							currentItem = new Item();
						} else if (currentItem != null) {
							if (tag.equals("title")) {
//								currentItem.setTitle(parser.nextText());
								// ISO-8859-1としてパースすれば成功するが文字化けする為、getBytesでUTF-8に変換する
								String str = new String(parser.nextText().getBytes("ISO-8859-1"),"UTF-8");
								currentItem.setTitle(str);
							} else if (tag.equals("link")) {
								//<link>要素のhref属性を取得
								currentItem.setLink(parser.getAttributeValue(null,"href"));
//							} else if (tag.equals("description")) {
//								currentItem.setDescription(parser.nextText());
							} else if (tag.equals("summary")) {
								// ISO-8859-1としてパースすれば成功するが文字化けする為、getBytesでUTF-8に変換する
								String str = new String(parser.nextText().getBytes("ISO-8859-1"),"UTF-8");
								currentItem.setSummary(str);
							} else if (tag.equals("name")) {
								// ISO-8859-1としてパースすれば成功するが文字化けする為、getBytesでUTF-8に変換する
								String str = new String(parser.nextText().getBytes("ISO-8859-1"),"UTF-8");
								currentItem.setAuthor(str);
							} else if (tag.equals("content")) {
								// ISO-8859-1としてパースすれば成功するが文字化けする為、getBytesでUTF-8に変換する
								String str = new String(parser.nextText().getBytes("ISO-8859-1"),"UTF-8");
								currentItem.setContent(str);
								
								// imgタグから画像のURLを取得する
								String img = getImage(str);
								currentItem.setImage(img);
								
								//画像データを取得
								Logout.d(img);
								byte[] bmp = getBitmap(img);
								currentItem.setBmp(bmp);
							}
						}else if (tag.equals("updated")) {
							String str = new String(parser.nextText().getBytes("ISO-8859-1"),"UTF-8");
							Logout.d(str);
							//最終更新日を取得
							String lastUpdate = PreferenceManager.getDefaultSharedPreferences(mActivity).getString("rssLastUpdate", str);
							if (str.equals(lastUpdate)){
								//同じRSSなので処理を中断
								return_code = NOUPDATE;
								return null;
							}
							//更新日を最終更新日に設定
							PreferenceManager.getDefaultSharedPreferences(mActivity).edit().putString("rssLastUpdate", str).commit();
						}
						break;
					case XmlPullParser.END_TAG:
						tag = parser.getName();
//						if (tag.equals("item")) {
						if (tag.equals("entry")) {
//							mAdapter.add(currentItem);
							db.insert(currentItem);
						}
						break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		db.close();
		return mAdapter;
	}

	//HTMLをパースして起業家の画像のURIを取得
	public String getImage(String html){
		String linkHref = null;
		Document doc = Jsoup.parse(html);
	
		//http://jsoup.org/cookbook/input/load-document-from-url
		//Document doc = Jsoup.connect("http://example.com/").get();
	
		Elements links = doc.getElementsByTag("img");
		for (Element link : links) {
			linkHref = link.attr("src");
		}
		return linkHref;
	}
	//urlの画像を取得
    protected byte[] getBitmap(String url) {  

    	// ピックアップ起業家の写真を表示する
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(new HttpGet(url));
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		byte[] bitm = null;
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
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			OutputStream os = new BufferedOutputStream(b);
			int c;
			try {
				while ((c = in.read()) != -1) {
					os.write(c);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					try {
						os.flush();
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			// 書き込み先はByteArrayOutputStreamクラス内部となる。
			// この書き込まれたバイトデータをbyte型配列として取り出す場合には、
			// toByteArray()メソッドを呼び出す。 
			bitm = b.toByteArray();
		}

		return bitm;  
    }  

}
