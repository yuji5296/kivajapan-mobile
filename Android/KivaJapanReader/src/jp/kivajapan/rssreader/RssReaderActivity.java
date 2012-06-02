package jp.kivajapan.rssreader;


import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
import android.widget.AbsListView;
import android.widget.ListView;
//import android.widget.Toast;
import android.widget.Toast;

public class RssReaderActivity extends ListActivity implements ListView.OnScrollListener{
	//public static final String RSS_FEED_URL = "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
	public static final String RSS_FEED_URL = "http://kivajapan.jp/atom.xml";
//	public static final int MENU_ITEM_RELOAD = Menu.FIRST; 
	private ArrayList<Item> mItems;
	private RssListAdapter mAdapter;
//	private ProgressDialog mProgressDialog;
//	private ListView listview;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();

    	// アニメーションするイメージを取得
//    	ImageView animWindow = (ImageView)findViewById(R.id.ImageView01);

    	// アニメーションリソースをロード
//    	Animation anim = AnimationUtils.loadAnimation(this, R.anim.progresscircle);
//    	anim.setRepeatMode(Animation.RESTART);

    	// アニメーション開始
//    	animWindow.startAnimation(anim);
        
		// Itemオブジェクトを保持するためのリストを生成し、アダプタに追加する
		mItems = new ArrayList<Item>();
		mAdapter = new RssListAdapter(this, mItems);
		mAdapter = readAdapter();
		
		// アダプタをリストビューにセットする
		setListAdapter(mAdapter);
		
		//更新
		update();

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
//		intent.putExtra("DESCRIPTION", item.getDescription());
		intent.putExtra("CONTENT", item.getContent());
		intent.putExtra("SUMMARY", item.getSummary());
		intent.putExtra("AUTHOR", item.getAuthor());
		intent.putExtra("LINK", item.getLink());
		intent.putExtra("IMAGE", item.getImage());
		startActivity(intent);
//		Log.v("KivaJapanReader",(String)item.getTitle());
//		Log.v("KivaJapanReader",(String)item.getImage());
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
    	inflater.inflate(R.menu.menu_list, menu);
    	//できたらtrueを返す
		return result;
	}

	// MENUの項目を押したときの処理
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			// 更新
			case R.id.menu_update:
				update();
				break;
			// ホーム
			case R.id.menu_home:
				goHome();
				break;
			// 設定
			case R.id.menu_preference:
				Intent intent;
				intent = new Intent(this, KivaJapanPreferenceActivity.class);
				startActivity(intent);
				break;
			// 削除
			case R.id.menu_delete:
				delete();
				break;
			// 検索
			case R.id.menu_search:
//				search("");
				onSearchRequested();
				break;
			// ソート
			case R.id.menu_sort:
				sort();
				break;
			// フィルタ
			case R.id.menu_filter:
				filter();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void goHome(){
		Intent intent;
		intent = new Intent(this, TopActivity.class);
		startActivity(intent);
	}
	private void update(){
		//ListViewの表示をemptyに変更する
		// アダプタを初期化し、タスクを起動する
//		mItems.clear();
//		mAdapter.clear();
//		mItems = new ArrayList<Item>();
//		mAdapter = new RssListAdapter(this, mItems);
//		//ListViewの更新
//		listview = getListView();
//		listview.invalidateViews();
//		listview.invalidate();
		
		// タスクはその都度生成する
		RssParserTask task = new RssParserTask(this, mAdapter);
		task.execute(RSS_FEED_URL);
//		//Adapterの更新
//		mAdapter.clear();
//		mAdapter.notifyDataSetChanged();

		//表示後リストのスタイルが適用されない
		//list_row_background.xmlを修正で解決
		//android:state_window_focused="false"の場合に背景色を透過にする設定を無効化
		//更新時にandroid:state_window_focusedをtrueにすれば良い？
		
	}
	private void search(String query){
    	Toast.makeText(this, query+"を検索中", Toast.LENGTH_SHORT).show();	
	}
	private void sort(){
    	Toast.makeText(this, "ソート機能追加予定", Toast.LENGTH_SHORT).show();	
	}
	private void filter(){
    	Toast.makeText(this, "フィルタ機能追加予定", Toast.LENGTH_SHORT).show();	
	}
	public RssListAdapter readAdapter(){
		RssDatabase db = new RssDatabase(this);
		db.create("read");
		List<Item> items = new ArrayList<Item>();
		items = db.getItem();
		mAdapter.clear();
		for ( int i = 0; i < items.size(); ++i ) {
			mAdapter.add(items.get(i));
		}
		mAdapter.notifyDataSetChanged();
		db.close();
		return mAdapter;

	}
	private void delete(){
		//データベースを作成
		RssDatabase db = new RssDatabase(this);
		db.create("write");
		db.delete();
		db.close();
		mAdapter.clear();
		mAdapter = new RssListAdapter(this, mItems);
		mAdapter.notifyDataSetChanged();
		//最終更新日を初期化
		PreferenceManager.getDefaultSharedPreferences(this).edit().putString("rssLastUpdate", "No update").commit();
    	Toast.makeText(this, "Delete all items.", Toast.LENGTH_SHORT).show();	

	}

	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		// タスクはその都度生成する
		mAdapter.clear();
		mAdapter = new RssListAdapter(this, mItems);
		RssParserTask task = new RssParserTask(this, mAdapter);
		task.execute(RSS_FEED_URL);
		mAdapter.notifyDataSetChanged();
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
	  if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
	    String query = intent.getStringExtra(SearchManager.QUERY);
	    // 検索処理を実行 今回はToastを表示
//	    Toast.makeText(this, query, Toast.LENGTH_LONG).show();
	    // 自分がやった実装ではGeoCoderで地名検索させて、Addressに変換させた。
	    search(query);
	  }
	}
}