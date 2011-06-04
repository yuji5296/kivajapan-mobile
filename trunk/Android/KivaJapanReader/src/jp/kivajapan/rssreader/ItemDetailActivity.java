package jp.kivajapan.rssreader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemDetailActivity extends Activity {
	private TextView mTitle;
	private TextView mAuthor;
//	private TextView mDescr;
	private WebView  mContent;
	private String mLink;
	private String title;
	private String author;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.item_detail);
		
		Intent intent = getIntent();

		//�����ȥ�򥻥å�
		title = intent.getStringExtra("TITLE");
		mTitle = (TextView) findViewById(R.id.item_detail_title);
		mTitle.setText(title);

		//�����Ԥ򥻥å�
		author = intent.getStringExtra("AUTHOR");
		mAuthor = (TextView) findViewById(R.id.item_detail_author);
		mAuthor.setText("�����ԡ�" + author);

		//��ʸ�򥻥å�
//		String descr = intent.getStringExtra("DESCRIPTION");
//		mDescr = (TextView) findViewById(R.id.item_detail_descr);
//		mDescr.setText(descr);		
		String descr = intent.getStringExtra("CONTENT");
		mContent = (WebView) findViewById(R.id.WebView01);
		//CharSequence sHtml = Html.fromHtml(descr);
//		mContent.loadDataWithBaseURL(null, descr, "text/html", "UTF-8", null);
		mContent.loadData(descr, "text/html", "UTF-8");
		//webViewLoadData(mContent, descr);

		//URL�򥻥å�
		mLink = intent.getStringExtra("LINK");
		
	}
	
    public final static void webViewLoadData(WebView web, String html) {
        StringBuilder buf = new StringBuilder(html.length());
        for (char c : html.toCharArray()) {
            switch (c) {
              case '#':  buf.append("%23"); break;
              case '%':  buf.append("%25"); break;
              case '\'': buf.append("%27"); break;
              case '?':  buf.append("%3f"); break;                
              default:
                buf.append(c);
                break;
            }
        }
        web.loadData(buf.toString(), "text/html", "utf-8");
    }

    // MENU�ܥ���򲡤����Ȥ��ν���
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		// �ǥե���ȤǤϥ����ƥ���ɲä��������̤��ɽ������
//		menu.add(0, MENU_ITEM_RELOAD, 0, R.string.update);
//		menu.add(0, MENU_ITEM_HELP,0,R.string.help).setIcon(android.R.drawable.ic_menu_help);
    	//��˥塼����ե졼���������
    	MenuInflater inflater = getMenuInflater();
    	//xml�Υ꥽�����ե��������Ѥ��ƥ�˥塼�˥����ƥ���ɲ�
    	inflater.inflate(R.menu.menu_detail, menu);
    	//�Ǥ�����true���֤�
		return result;
	}

	// MENU�ι��ܤ򲡤����Ȥ��ν���
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		switch (item.getItemId()) {
			// ͻ���Web�����Ȥ���³��
			case R.id.menu_loan:
				//Toast.makeText(this, mLink, Toast.LENGTH_LONG).show();

				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("ͻ��");
				alert.setMessage("Kiva�Υ����Ȥ��顢���ε��ȲȤ�ͻ�񤷤ޤ�����");
				alert.setPositiveButton("�Ϥ�",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// Yes�ܥ��󤬲����줿���ν���
								// Browser��ư
								Uri uri = Uri.parse(mLink);
								Intent intent = new Intent(Intent.ACTION_VIEW,uri);
								startActivity(intent);
							}
						});
				alert.setNegativeButton("������",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								// No�ܥ��󤬲����줿���ν���
								Toast.makeText(ItemDetailActivity.this, "��ǰ�Ǥ����ޤ��ε�����Ԥ����Ƥ���ޤ���",
										Toast.LENGTH_LONG).show();
							}
						});
				alert.show();
				break;
				// ��ͭ
			case R.id.menu_share:
			    intent = new Intent(Intent.ACTION_SEND);
			    intent.setType("text/plain");  
			    intent.putExtra(Intent.EXTRA_TEXT, mLink);
//			    intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("http://kivajapan.jp/"));
//			    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"yuji5296@gmail.com"});  
//			    intent.putExtra(Intent.EXTRA_CC, "yuji5296@gmail.com");  
//			    intent.putExtra(Intent.EXTRA_BCC, "yuji5296@gmail.com");  
			    intent.putExtra(Intent.EXTRA_SUBJECT, title);  
			    
			   try{  
			      startActivityForResult(intent, 0);  
			    }  
			    catch (android.content.ActivityNotFoundException ex) {  
			      Toast.makeText(this, "client not found", Toast.LENGTH_LONG).show();
			    }
				break;
			// ����
			case R.id.menu_search:
				intent = new Intent(Intent.ACTION_SEARCH);
//				intent.getStringExtra(SearchManager.QUERY);
			    intent.putExtra("query", author);
			    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			    try{  
			      startActivityForResult(intent, 0);  
			    }  
			    catch (android.content.ActivityNotFoundException ex) {  
			      Toast.makeText(this, "client not found", Toast.LENGTH_LONG).show();
			    }
				break;
		}
		return super.onOptionsItemSelected(item);
	}

}
