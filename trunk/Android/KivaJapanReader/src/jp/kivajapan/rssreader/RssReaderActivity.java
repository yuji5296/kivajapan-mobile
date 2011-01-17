package jp.kivajapan.rssreader;


import java.util.ArrayList;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class RssReaderActivity extends ListActivity {
	//public static final String RSS_FEED_URL = "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
	public static final String RSS_FEED_URL = "http://kivajapan.jp/atom.xml";
//	public static final int MENU_ITEM_RELOAD = Menu.FIRST; 
	private ArrayList<Item> mItems;
	private RssListAdapter mAdapter;
	private ProgressDialog mProgressDialog;
	private ListView listview;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();

    	// ���˥᡼����󤹤륤�᡼�������
    	ImageView animWindow = (ImageView)findViewById(R.id.ImageView01);

    	// ���˥᡼�����꥽���������
    	Animation anim = AnimationUtils.loadAnimation(this, R.anim.progresscircle);
    	anim.setRepeatMode(Animation.RESTART);

    	// ���˥᡼����󳫻�
    	animWindow.startAnimation(anim);
        
		// Item���֥������Ȥ��ݻ����뤿��Υꥹ�Ȥ��������������ץ����ɲä���
		mItems = new ArrayList<Item>();
		mAdapter = new RssListAdapter(this, mItems);

		// ��������ư����
		RssParserTask task = new RssParserTask(this, mAdapter);
		task.execute(RSS_FEED_URL);

		// �����ץ���ꥹ�ȥӥ塼�˥��åȤ���
		//setListAdapter(mAdapter);

		// ����ץ��Ѥ˶���Item���֥������Ȥ򥻥åȤ���
		//for (int i = 0; i < 10; i++) {
			//mAdapter.add(new Item());
		//}
    }
    
//    @Override
//    public void onRestart(){
//        super.onRestart();
//        Toast.makeText(this, "onRestar()", Toast.LENGTH_SHORT).show();
//    }
//    
//    @Override
//    public void onStart(){
//        super.onStart();
//        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
//    }
//    
//    @Override
//    public void onResume(){
//        super.onResume();
//        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();   	
//    }
//
//    @Override
//    public void onPause(){
//        super.onPause();
//        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
//    }
//    @Override
//    public void onStop(){
//        super.onStop();
//        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
//    }
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
//    }
    
	// �ꥹ�Ȥι��ܤ����򤷤����ν���
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Item item = mItems.get(position);
		Intent intent = new Intent(this, ItemDetailActivity.class);
		intent.putExtra("TITLE", item.getTitle());
		intent.putExtra("DESCRIPTION", item.getDescription());
		intent.putExtra("CONTENT", item.getContent());
		intent.putExtra("AUTHOR", item.getAuthor());
		startActivity(intent);
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
    	inflater.inflate(R.menu.menu, menu);
    	//�Ǥ�����true���֤�
		return result;
	}

	// MENU�ι��ܤ򲡤����Ȥ��ν���
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		switch (item.getItemId()) {
			// ����
			case R.id.menu01:

				//ListView��ɽ����empty���ѹ�����
				// �����ץ�������������������ư����
//				mItems.clear();
				mAdapter.clear();
//				mItems = new ArrayList<Item>();
				mAdapter = new RssListAdapter(this, mItems);
//				//Adapter�ι���
//				mAdapter.notifyDataSetChanged();
//				//ListView�ι���
//				listview = getListView();
//				listview.invalidateViews();
//				listview.invalidate();
				
				// �������Ϥ���������������
				RssParserTask task = new RssParserTask(this, mAdapter);
				task.execute(RSS_FEED_URL);

				//ɽ����ꥹ�ȤΥ������뤬Ŭ�Ѥ���ʤ�
				//list_row_background.xml�����ǲ��
				//android:state_window_focused="false"�ξ����طʿ���Ʃ��ˤ��������̵����
				//��������android:state_window_focused��true�ˤ�����ɤ���
				
				break;
//				return true;
			// ����
			case R.id.menu02:
				intent = new Intent(this, AboutActivity.class);
				startActivity(intent);
				break;
//				return true;
				
			// �إ��
			case R.id.menu03:
				intent = new Intent(this, HelpActivity.class);
				startActivity(intent);
				break;
//				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}