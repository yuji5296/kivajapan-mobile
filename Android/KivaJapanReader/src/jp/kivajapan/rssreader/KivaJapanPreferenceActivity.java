package jp.kivajapan.rssreader;

import java.util.Map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceManager;

public class KivaJapanPreferenceActivity extends android.preference.PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
		//設定されている値をsummaryに表示
//		以下のコードを使って全てのPreferenceを表示するように修正
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Map<String, ?> keys = sharedPreferences.getAll();
		if (keys.size() > 0) {
			for (String key : keys.keySet()) {
				// 設定されている値をsummaryに表示
//				if (key.equals("Save")) {
//					CheckBoxPreference mCheckBoxPreference = null;
//					mCheckBoxPreference = (CheckBoxPreference) getPreferenceScreen().findPreference(key);
//					mCheckBoxPreference.setSummary(sharedPreferences.getBoolean(key, false) ? "Disable" : "Enable");
				if (key.equals("rssLastUpdate")|key.equals("widgetLastUpdate")) {
					EditTextPreference mEditTextPreference = null;
					mEditTextPreference = (EditTextPreference) getPreferenceScreen().findPreference(key);
					mEditTextPreference.setSummary(sharedPreferences.getString(key, ""));
				}else if(key.equals("widgetRefreshInterval")){
					ListPreference mListPreference = null;
					mListPreference = (ListPreference)getPreferenceScreen().findPreference(key);
					mListPreference.setSummary(sharedPreferences.getString(key, ""));
				}

			}
		}

	}
	@Override  
	protected void onResume() {  
	    super.onResume();  
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);  
	}  
	   
	@Override  
	protected void onPause() {  
	    super.onPause();  
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);  
	}  
	  
	// ここで summary を動的に変更  
	private SharedPreferences.OnSharedPreferenceChangeListener listener =   
	    new SharedPreferences.OnSharedPreferenceChangeListener() {  
	       
	    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {  
	        findPreference(key).setSummary(sharedPreferences.getString(key, "00000000"));  
	    }  
	};  
}
