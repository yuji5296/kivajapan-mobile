package jp.kivajapan.rssreader;

import android.app.AlertDialog;
import android.content.Context;

public class KivaJapanAlertDialog extends AlertDialog {
	protected KivaJapanAlertDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}		   
	public KivaJapanAlertDialog(Context context, int theme) {  
		  super(context, theme);  
	}
	public void setPositiveButton(String string, OnClickListener onClickListener) {
		// TODO Auto-generated method stub
		super.setButton(string, onClickListener);	
	}
	public void setNegativeButton(String string, OnClickListener onClickListener) {
		// TODO Auto-generated method stub
		super.setButton2(string, onClickListener);	
	}
	
}
