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
        }else{
        	db = dbHelper.getReadableDatabase();
        }
	}
	public void close(){
        //データベースを閉じる  
        db.close();  
	}
	
	public void insert(Item item){
		//ItemをDBに挿入
		//同じIDがある場合
			//更新日は比較
			//新しい場合は更新
		//同じものがない場合はinsert
        ContentValues values = new ContentValues();  
        values.put("Title", item.getTitle());  
        values.put("Link", item.getLink());  
        values.put("Summary", item.getSummary());  
        values.put("Content", item.getContent());  
        values.put("Author", item.getAuthor());
        values.put("Image", item.getImage());
        values.put("Bmp", item.getBmp());
    	long ret = db.insert("MyTable", null, values);  

	}
	public List<Item> getItem(){
		List<Item> items = new ArrayList<Item>();
        //SQL作成  
        StringBuilder sql = new StringBuilder();  
        sql.append(" SELECT");  
        sql.append(" No");
        sql.append(" ,Title");  
        sql.append(" ,Link");
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
                item.setSummary(cursor.getString(3));
                item.setContent(cursor.getString(4));
                item.setAuthor(cursor.getString(5));
                item.setImage(cursor.getString(6));
                item.setBmp(cursor.getBlob(7));
                items.add(item);
            }  
        
        return items;
	}
	public void delete(){
		String whereClause = "No > ?";
		String whereArgs[] = new String[1];
		whereArgs[0] = "0";

		db.delete("MyTable", whereClause, whereArgs);

	}
}
