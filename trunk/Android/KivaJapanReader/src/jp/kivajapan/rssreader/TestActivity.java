package jp.kivajapan.rssreader;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.test);
		
	}
	public void create(View v){
        //データベースヘルパーのインスタンスを作成する（まだデータベースはできない）  
        DatabaseHelper dbHelper = new DatabaseHelper(this);  
        //データベースオブジェクトを取得する（データベースにアクセスすると作成される。）  
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //データベースを閉じる  
        db.close();  
	}
	public void insert(View v){
        //データベースヘルパーのインスタンスを作成する（まだデータベースはできない）  
        DatabaseHelper dbHelper = new DatabaseHelper(this);  
        //データベースオブジェクトを取得する（データベースにアクセスすると作成される。）  
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();  
        values.put("Title", "Abebe");  
        values.put("Link", "http://www.kivajapan.jp/");  
        values.put("Summary", "0000-1234-5678");  
        values.put("Content", "0000--5678");  
        values.put("Author", "Yuji DOI");  
        values.put("Image", "jifejiaf.jpg");  

        long ret;  
        try {  
        	ret = db.insert("MyTable", null, values);  
        } finally {  
            //データベースを閉じる  
            db.close();  
        }  
        if (ret == -1) {  
        	Toast.makeText(this, "Insert失敗", Toast.LENGTH_SHORT).show();  
        } else {   
        	Toast.makeText(this, "Insert成功", Toast.LENGTH_SHORT).show();  
        }  
	}
	public void update(View v){
        ContentValues values = new ContentValues();  
        values.put("Title","Hiro");  
        String whereClause = "No = ?";  
        String whereArgs[] = new String[1];  
        whereArgs[0] = "1";  
    
        DatabaseHelper dbHelper = new DatabaseHelper(this);  
        SQLiteDatabase db = dbHelper.getWritableDatabase();   
        int ret;  
        try {  
            ret = db.update("MyTable", values, whereClause, whereArgs);  
        } finally {  
            db.close();  
        }  
        if (ret == -1){  
            Toast.makeText(this, "Update失敗", Toast.LENGTH_SHORT).show();  
        } else {   
            Toast.makeText(this, "Update成功", Toast.LENGTH_SHORT).show();  
        } 
	}
	public void delete(View v){
		 String whereClause = "No > ?";  
	        String whereArgs[] = new String[1];  
	        whereArgs[0] = "0";  
	    
	        DatabaseHelper dbHelper = new DatabaseHelper(this);  
	        SQLiteDatabase db = dbHelper.getWritableDatabase();   
	        int ret;  
	        try {  
	            ret = db.delete("MyTable", whereClause, whereArgs);  
	        } finally {  
	            db.close();  
	        }  
	        if (ret == -1){  
	            Toast.makeText(this, "Delete失敗", Toast.LENGTH_SHORT).show();  
	        } else {   
	            Toast.makeText(this, "Delete成功", Toast.LENGTH_SHORT).show();  
	        }  
	}
	public void rawQuery(View v){
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
        sql.append(" FROM MyTable;");  
        //rawQueryメソッドでデータを取得  
        DatabaseHelper dbHelper = new DatabaseHelper(this);  
        SQLiteDatabase db=dbHelper.getReadableDatabase();  
        StringBuilder text = new StringBuilder();  
        try{  
            Cursor cursor = db.rawQuery(sql.toString(), null);   
            //TextViewに表示  
            while (cursor.moveToNext()){  
                text.append(cursor.getInt(0));  
                text.append("," + cursor.getString(1));  
                text.append("," + cursor.getString(2));  
                text.append("," + cursor.getString(3));  
                text.append("\n");  
            }  
        }finally{  
            db.close();  
        }  
        TextView lblList = (TextView)this.findViewById(R.id.textView1);  
        lblList.setText(text);
	}
    public void query(View v){  
        //queryメソッドでデータを取得  
        String[] cols = {"No","Title","Link","Summary","Content","Author","Image"};  
        String selection = "No = ?";  
        String[] selectionArgs = {"1"};  
        String groupBy = null;  
        String having = null;  
        String orderBy = null;  
        DatabaseHelper dbHelper = new DatabaseHelper(this);  
        SQLiteDatabase db=dbHelper.getReadableDatabase();  
        StringBuilder text = new StringBuilder();  
        try{  
            Cursor cursor = db.query("MyTable", cols, selection, selectionArgs, groupBy, having, orderBy);  
            //TextViewに表示  
            while (cursor.moveToNext()){  
                text.append(cursor.getInt(0));  
                text.append("," + cursor.getString(1));  
                text.append("," + cursor.getString(2));  
                text.append("," + cursor.getString(3));  
                text.append("\n");  
            }  
        }finally{  
            db.close();  
        }  
        TextView lblList = (TextView)this.findViewById(R.id.textView1);  
        lblList.setText(text);  
    }  
}
