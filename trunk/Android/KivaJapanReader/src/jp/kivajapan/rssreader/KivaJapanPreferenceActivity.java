package jp.kivajapan.rssreader;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;

public class KivaJapanPreferenceActivity extends android.preference.PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
		//設定されている値をsummaryに表示
		EditTextPreference edittext_preference = (EditTextPreference)getPreferenceScreen().findPreference("kiva_id");
		edittext_preference.setSummary(edittext_preference.getText());
		edittext_preference = (EditTextPreference)getPreferenceScreen().findPreference("kivajapan_id");
		edittext_preference.setSummary(edittext_preference.getText());
		edittext_preference = (EditTextPreference)getPreferenceScreen().findPreference("rssLastUpdate");
		edittext_preference.setSummary(edittext_preference.getText());
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
