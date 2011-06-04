package jp.kivajapan.rssreader;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class TopActivity extends Activity {
	String kivaUrl = "http://kivajapan.jp/";
	String kivaRss = "http://kivajapan.jp/atom.xml";
	String testUrl="file:///android_asset/test.html";
	String imageUrl = "http://www.kiva.org/img/w450h360/776940.jpg";
	String html = "<img src='http://www.kiva.org/img/w450h360/776940.jpg' width='100%'>";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.top);

    	//�ԥå����å׵��ȲȤμ̿����������ɽ��
    	getPickupWithJsoup();
  
	}
	
	//Kiva Japan Top�ڡ����Υԥå����å׵��ȲȲ�����ɽ��
	public void getPickupWithJsoup() {	
    	ImageView animWindow = (ImageView)this.findViewById(R.id.imageView1);
    	DownloadPickupTask task = new DownloadPickupTask(animWindow);  
		task.execute(kivaUrl);
   	}	
	
	public void onClick_Pickup(View v) {
		String url = (String) v.getTag();
		Uri uri = Uri.parse("http://kivajapan.jp" + url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	
	public void onClickButton1(View v) {
		// RSS�꡼��ɽ��
		Intent intent = new Intent(this, RssReaderActivity.class);
		startActivity(intent);
	}

	public void onClickButton2(View v) {
		// Kiva Japan�ˤĤ�������
		Uri uri = Uri
				.parse("http://kivajapan.jp/?page=Bureau&action=beginners");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	public void onClickButton3(View v) {
		// �����ڡ����˥�����
		Uri uri = Uri
				.parse("http://kivajapan.jp/?page=Bureau&action=about_translator");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

	// YouTube�Ǥθ���
	public void onClickButton4(View v) {
		Intent intent = new Intent(Intent.ACTION_SEARCH);
		intent.setPackage("com.google.android.youtube");
		intent.putExtra("query", "Kiva Japan");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	// MENU�ܥ���򲡤����Ȥ��ν���
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		// �ǥե���ȤǤϥ����ƥ���ɲä��������̤��ɽ������
		// menu.add(0, MENU_ITEM_RELOAD, 0, R.string.update);
		// menu.add(0,
		// MENU_ITEM_HELP,0,R.string.help).setIcon(android.R.drawable.ic_menu_help);
		// ��˥塼����ե졼���������
		MenuInflater inflater = getMenuInflater();
		// xml�Υ꥽�����ե��������Ѥ��ƥ�˥塼�˥����ƥ���ɲ�
		inflater.inflate(R.menu.menu, menu);
		// �Ǥ�����true���֤�
		return result;
	}

	// MENU�ι��ܤ򲡤����Ȥ��ν���
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		switch (item.getItemId()) {
		// ����
		case R.id.menu_update:
			getPickupWithJsoup();
			break;
		// ����
		case R.id.menu_info:
			intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			break;

		// �إ��
		case R.id.menu_help:
			intent = new Intent(this, HelpActivity.class);
			startActivity(intent);
			break;

		// ��ͭ
		case R.id.menu_share:
			intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			// intent.putExtra(Intent.EXTRA_TEXT,
			// "�����ͤ�ˤǤ���Ҳ�׸���ư�������ˤ���ޤ���Kiva Japan�ϥ��󥿡��ͥåȤ��̤���ȯŸ�Ӿ��ε��ȲȤ�ͻ��Ǥ��륵���ȤǤ���");
//			intent.putExtra(Intent.EXTRA_TEXT, "http://kivajapan.jp/");
			intent.putExtra(Intent.EXTRA_TEXT, "https://sites.google.com/site/kivajapanrssreader/");
			// intent.putExtra(Intent.EXTRA_STREAM,
			// Uri.parse("http://kivajapan.jp/"));
			// intent.putExtra(Intent.EXTRA_EMAIL, new
			// String[]{"yuji5296@gmail.com"});
			// intent.putExtra(Intent.EXTRA_CC, "yuji5296@gmail.com");
			// intent.putExtra(Intent.EXTRA_BCC, "yuji5296@gmail.com");
			intent.putExtra(Intent.EXTRA_SUBJECT, "Kiva Japan");

			try {
				startActivityForResult(intent, 0);
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(this, "client not found", Toast.LENGTH_LONG)
						.show();
			}
			break;
		// ����
		case R.id.menu_search:
			intent = new Intent(Intent.ACTION_SEARCH);
			// intent.getStringExtra(SearchManager.QUERY);
			intent.putExtra("query", "Kiva");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			try {
				startActivityForResult(intent, 0);
			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(this, "client not found", Toast.LENGTH_LONG)
						.show();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}

