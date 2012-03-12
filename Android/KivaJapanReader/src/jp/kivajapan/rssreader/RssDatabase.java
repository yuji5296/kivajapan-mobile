/**
 * 
 */
package jp.kivajapan.rssreader;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.TextView;

/**
 * @author yuji
 *
 */
public class RssDatabase {
	Activity activity;
	Context context;
	DatabaseHelper dbHelper;  
    SQLiteDatabase db;
	
	RssDatabase(Activity activity) {
//		super();
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

	public void create(String mode){
        //データベースヘルパーのインスタンスを作成する（まだデータベースはできない）  
        dbHelper = new DatabaseHelper(activity);  
        //データベースオブジェクトを取得する（データベースにアクセスすると作成される。）
        if (mode == "write"){
        	db = dbHelper.getWritableDatabase();
        	Logout.v("Create writable database.");
        }else{
        	db = dbHelper.getReadableDatabase();
        	Logout.v("Create readable database.");
        }
	}
	public void close(){
        //データベースを閉じる  
        db.close();  
    	Logout.v("Close database.");
	}
	
	public void insert(Item item){
		//ItemをDBに挿入
		
		if(getItemById(item.getId()) != null){
			//同じID有り
	    	Logout.v("Same id in database.");
			return ;
		}else{
		//DBからupdatedを取得
//		String updated = ""; 
//		//更新日を比較
//		if(item.getUpdated().equals(updated)){
//			//更新無し
//			return ;
//		}
		//新しい場合は更新
		
			//同じものがない場合はinsert
	        ContentValues values = new ContentValues();  
	        values.put("Title", item.getTitle());  
	        values.put("Link", item.getLink());  
	        values.put("Id", item.getId());  
	        values.put("Published", item.getPublished());  
	        values.put("Updated", item.getUpdated());  
	        values.put("Summary", item.getSummary());  
	        values.put("Content", item.getContent());  
	        values.put("Author", item.getAuthor());
	        values.put("Image", item.getImage());
	        values.put("Bmp", item.getBmp());
	    	db.insert("MyTable", null, values);  
	    	Logout.v("Insert item to database.");
		}
	}

	public Item getItemById(String id) {
		// queryメソッドでデータを取得
		String[] cols = { "No", "Title", "Link", "Id", "Published", "Updated",
				"Summary", "Content", "Author", "Image", "Bmp" };
		String selection = "Id = ?";
		String[] selectionArgs = { id };
		String groupBy = null;
		String having = null;
		String orderBy = null;

		Item item = new Item();
		try {
			Cursor cursor = db.query("MyTable", cols, selection, selectionArgs,
					groupBy, having, orderBy);
			// Itemにセット
			if (cursor.moveToFirst()==false){
				//レコードがない場合はnullを返す
		    	Logout.v("No item in database. Id="+id);
				return null;
			}
			while (cursor.moveToNext()) {
				item.setTitle(cursor.getString(1));
				item.setLink(cursor.getString(2));
				item.setId(cursor.getString(3));
				item.setPublished(cursor.getString(4));
				item.setUpdated(cursor.getString(5));
				item.setSummary(cursor.getString(6));
				item.setContent(cursor.getString(7));
				item.setAuthor(cursor.getString(8));
				item.setImage(cursor.getString(9));
				item.setBmp(cursor.getBlob(10));
			}
		} finally {
		}
		return item;
	}

	public List<Item> getItem(){
		List<Item> items = new ArrayList<Item>();
        //SQL作成  
        StringBuilder sql = new StringBuilder();  
        sql.append(" SELECT");  
        sql.append(" No");
        sql.append(" ,Title");  
        sql.append(" ,Link");
        sql.append(" ,Id");
        sql.append(" ,Published");
        sql.append(" ,Updated");
        sql.append(" ,Summary");
        sql.append(" ,Content");  
        sql.append(" ,Author");  
        sql.append(" ,Image");
        sql.append(" ,Bmp");
        sql.append(" FROM MyTable;");  
        //rawQueryメソッドでデータを取得  
            Cursor cursor = db.rawQuery(sql.toString(), null);   
            //TextViewに表示  
            while (cursor.moveToNext()){
            	Item item = new Item();
                item.setTitle(cursor.getString(1));
                item.setLink(cursor.getString(2));
                item.setId(cursor.getString(3));
                item.setPublished(cursor.getString(4));
                item.setUpdated(cursor.getString(5));
                item.setSummary(cursor.getString(6));
                item.setContent(cursor.getString(7));
                item.setAuthor(cursor.getString(8));
                item.setImage(cursor.getString(9));
                item.setBmp(cursor.getBlob(10));
                items.add(item);
            }  
	    	Logout.v("Get " + String.valueOf(items.size()) + " items.");

        return items;
	}

	public void delete(){
		String whereClause = "No > ?";
		String whereArgs[] = new String[1];
		whereArgs[0] = "0";

		db.delete("MyTable", whereClause, whereArgs);
		Logout.v("Delete all items in database.");

	}
}
