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

    	// アニメーションするイメージを取得
    	ImageView animWindow = (ImageView)findViewById(R.id.ImageView01);

    	// アニメーションリソースをロード
    	Animation anim = AnimationUtils.loadAnimation(this, R.anim.progresscircle);
    	anim.setRepeatMode(Animation.RESTART);

    	// アニメーション開始
    	animWindow.startAnimation(anim);
        
		// Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
		mItems = new ArrayList<Item>();
		mAdapter = new RssListAdapter(this, mItems);

		// タスクを起動する
		RssParserTask task = new RssParserTask(this, mAdapter);
		task.execute(RSS_FEED_URL);

		// アダプタをリストビューにセットする
		//setListAdapter(mAdapter);

		// サンプル用に空のItemオブジェクトをセットする
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
    
	// リストの項目を選択した時の処理
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
	
	// MENUボタンを押したときの処理
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean result = super.onCreateOptionsMenu(menu);
		// デフォルトではアイテムを追加した順番通りに表示する
//		menu.add(0, MENU_ITEM_RELOAD, 0, R.string.update);
//		menu.add(0, MENU_ITEM_HELP,0,R.string.help).setIcon(android.R.drawable.ic_menu_help);
    	//メニューインフレーターを取得
    	MenuInflater inflater = getMenuInflater();
    	//xmlのリソースファイルを使用してメニューにアイテムを追加
    	inflater.inflate(R.menu.menu, menu);
    	//できたらtrueを返す
		return result;
	}

	// MENUの項目を押したときの処理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;

		switch (item.getItemId()) {
			// 更新
			case R.id.menu01:

				//ListViewの表示をemptyに変更する
				// アダプタを初期化し、タスクを起動する
//				mItems.clear();
				mAdapter.clear();
//				mItems = new ArrayList<Item>();
				mAdapter = new RssListAdapter(this, mItems);
//				//Adapterの更新
//				mAdapter.notifyDataSetChanged();
//				//ListViewの更新
//				listview = getListView();
//				listview.invalidateViews();
//				listview.invalidate();
				
				// タスクはその都度生成する
				RssParserTask task = new RssParserTask(this, mAdapter);
				task.execute(RSS_FEED_URL);

				//表示後リストのスタイルが適用されない
				//list_row_background.xmlを修正で解決
				//android:state_window_focused="false"の場合に背景色を透過にする設定を無効化
				//更新時にandroid:state_window_focusedをtrueにすれば良い？
				
				break;
//				return true;
			// 情報
			case R.id.menu02:
				intent = new Intent(this, AboutActivity.class);
				startActivity(intent);
				break;
//				return true;
				
			// ヘルプ
			case R.id.menu03:
				intent = new Intent(this, HelpActivity.class);
				startActivity(intent);
				break;
//				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}