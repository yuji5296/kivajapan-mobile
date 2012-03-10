package jp.kivajapan.rssreader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    /* データベース名 */  
    private final static String DB_NAME = "kivajapanrssdb";  
    /* データベースのバージョン */  
    private final static int DB_VER = 2;  
    
	public DatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VER);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
        String sql = "";  
        sql += "create table MyTable (";  
        sql += " No integer primary key autoincrement";  
        sql += ",Title text not null";  
        sql += ",Link text";  
        sql += ",Summary text";  
        sql += ",Content text";  
        sql += ",Author text";  
        sql += ",Image text";  
        sql += ",Bmp blob";  
        sql += ")";  
        db.execSQL(sql);  
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
