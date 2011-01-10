package jp.kivajapan.rssreader;


import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class RssReaderActivity extends ListActivity {
	//public static final String RSS_FEED_URL = "http://itpro.nikkeibp.co.jp/rss/ITpro.rdf";
	public static final String RSS_FEED_URL = "http://kivajapan.jp/atom.xml";
//	public static final int MENU_ITEM_RELOAD = Menu.FIRST; 
	private ArrayList<Item> mItems;
	private RssListAdapter mAdapter;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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
				// アダプタを初期化し、タスクを起動する
				mItems = new ArrayList<Item>();
				mAdapter = new RssListAdapter(this, mItems);
				// タスクはその都度生成する
				RssParserTask task = new RssParserTask(this, mAdapter);
				task.execute(RSS_FEED_URL);
				return true;
			// 情報
			case R.id.menu02:
				intent = new Intent(this, AboutActivity.class);
				startActivity(intent);
				return true;
				
			// ヘルプ
			case R.id.menu03:
				intent = new Intent(this, HelpActivity.class);
				startActivity(intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}