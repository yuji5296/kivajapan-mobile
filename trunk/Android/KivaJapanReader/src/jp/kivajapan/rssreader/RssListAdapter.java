package jp.kivajapan.rssreader;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
//import android.widget.ImageView;
import android.widget.TextView;

public class RssListAdapter extends ArrayAdapter<Item> {
	private LayoutInflater mInflater;
	private TextView mTitle;
	private TextView mDescr;
	private ImageView mImage;
	//	private WebView mWebview;

	public RssListAdapter(Context context, List<Item> objects) {
		super(context, 0, objects);
//		activity = (Activity) context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// 1行ごとのビューを生成する
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;

		if (convertView == null) {
			view = mInflater.inflate(R.layout.item_row, null);
		}

		// 現在参照しているリストの位置からItemを取得する
		Item item = this.getItem(position);
		if (item != null) {
			// Itemから必要なデータを取り出し、それぞれTextViewにセットする
			// タイトルの取得
			String title = item.getTitle();
			mTitle = (TextView) view.findViewById(R.id.item_title);
			mTitle.setText(title);
			
			// 翻訳者の取得
			String author = "翻訳者：" + item.getAuthor();
			mTitle = (TextView) view.findViewById(R.id.item_author);
			mTitle.setText(author);
			
			// 要約の取得
//			String descr = item.getDescription();
			String summary = item.getSummary();
			mDescr = (TextView) view.findViewById(R.id.item_descr);
			mDescr.setText(summary);

			// 画像の取得
			// 翻訳者の写真をRSSから取得して表示しようとしたがimgタグのsrcを取得できない 
//			String image = item.getImage();
//			String image = "http://www.kiva.org/img/w80h80/674364.jpg";
//			Uri uri = Uri.parse(image);
			mImage = (ImageView) view.findViewById(R.id.item_image);
	    	Bitmap bitmap = BitmapFactory.decodeByteArray(item.getBmp(), 0, item.getBmp().length); // blobはbyte[]です
	    	mImage.setImageBitmap(bitmap);
//	    	DownloadTask task = new DownloadTask(activity, mImage);  
//			task.execute(image);
	    	
//			Log.v("KivaJapanReader", image);
//			String descr = "<img src='"+ image + "'>";
//			Log.v("KivaJapanReader", descr);
//			mWebview = (WebView) view.findViewById(R.id.webView1);
			//CharSequence sHtml = Html.fromHtml(descr);
//			mWebview.loadDataWithBaseURL(null, descr, "text/html", "utf-8", null);


		}
		return view;
	}
	
//    protected Bitmap getBitmap(String url) {  
//
//    	// ピックアップ起業家の写真を表示する
//		DefaultHttpClient httpClient = new DefaultHttpClient();
//		HttpResponse httpResponse = null;
//		try {
//			httpResponse = httpClient.execute(new HttpGet(url));
//		} catch (ClientProtocolException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		Bitmap bitm = null;
//		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//			HttpEntity httpEntity = httpResponse.getEntity();
//			InputStream in = null;
//			try {
//				in = httpEntity.getContent();
//			} catch (IllegalStateException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			bitm = BitmapFactory.decodeStream(in);
//		}
//
//		return bitm;  
//    }  

}
