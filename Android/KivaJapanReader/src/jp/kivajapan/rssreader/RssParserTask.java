package jp.kivajapan.rssreader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Xml;

public class RssParserTask extends AsyncTask<String, Integer, RssListAdapter> {
	private RssReaderActivity mActivity;
	private RssListAdapter mAdapter;
	private ProgressDialog mProgressDialog;

	// コンストラクタ
	public RssParserTask(RssReaderActivity activity, RssListAdapter adapter) {
		mActivity = activity;
		mAdapter = adapter;
	}

	// タスクを実行した直後にコールされる
	@Override
	protected void onPreExecute() {
		// プログレスバーを表示する
//		mProgressDialog = new ProgressDialog(mActivity);
////		mProgressDialog.setTitle(R.string.update);
//		mProgressDialog.setMessage("Now loding...");
//		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//		mProgressDialog.show();
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
			e.printStackTrace();
		}
		// ここで返した値は、onPostExecuteメソッドの引数として渡される
		return result;
	}

	// メインスレッド上で実行される
	@Override
	protected void onPostExecute(RssListAdapter result) {
//		mProgressDialog.dismiss();
		mActivity.setListAdapter(result);
	}

	// XMLをパースする
	public RssListAdapter parseXml(InputStream is) throws IOException, XmlPullParserException {
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
//						if (tag.equals("item")) {
						if (tag.equals("entry")) {
							currentItem = new Item();
						} else if (currentItem != null) {
							if (tag.equals("title")) {
//								currentItem.setTitle(parser.nextText());
								// ISO-8859-1としてパースすれば成功するが文字化けする為、getBytesでUTF-8に変換する
								String str = new String(parser.nextText().getBytes("ISO-8859-1"),"UTF-8");
								currentItem.setTitle(str);
							} else if (tag.equals("description")) {
								currentItem.setDescription(parser.nextText());
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
								// htmlの為、パーサでタグが取得できない？
//								XmlPullParser parser2 = Xml.newPullParser();
//								parser2.setInput(new StringReader(str));
//								int eventType2 = parser2.getEventType();
//								while (eventType2 != XmlPullParser.END_DOCUMENT) {
//									String tag2 = null;
//									switch (eventType2) {
//										case XmlPullParser.START_TAG:
//											tag2 = parser2.getName();
//											if (tag2.equals("img")) {
//												String src = parser2.getAttributeValue(null,"src");
//												currentItem.setImage(src);
//											}
//									}
//									eventType2 = parser2.next();
//								}
							}
						}
						break;
					case XmlPullParser.END_TAG:
						tag = parser.getName();
//						if (tag.equals("item")) {
						if (tag.equals("entry")) {
							mAdapter.add(currentItem);
						}
						break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mAdapter;
	}

}
