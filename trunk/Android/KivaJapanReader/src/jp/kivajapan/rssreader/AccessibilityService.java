package jp.kivajapan.rssreader;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class AccessibilityService extends
		android.accessibilityservice.AccessibilityService {

    /** Minimal timeout between accessibility events we want to receive. */
    private static final int NOTIFICATION_TIMEOUT_MILLIS = 80;

    public static final String SHARED_ACCESS_PREFS="str_access";
    public static final String ACCESS_PREFS_KEY="access_key";
 
    private SharedPreferences mPrefAccess;
    public static final boolean DEBUG=true;    // 通知のToast表示フラグ
 
    @Override
    public void onCreate() {
        super.onCreate();
    }
 
    @Override
    public void onServiceConnected() {
        mPrefAccess = getSharedPreferences(SHARED_ACCESS_PREFS, MODE_PRIVATE);
 
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED;
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        info.notificationTimeout = NOTIFICATION_TIMEOUT_MILLIS;
        info.flags = AccessibilityServiceInfo.DEFAULT;
        setServiceInfo(info);
    }
 
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
        case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED :
            if(DEBUG) {
                Log.d("StrAccessibilityService", "Access NOTIFIFY:"+event.getClassName());
                Log.d("StrAccessibilityService", "Access NOTIFIFY:"+event.getPackageName());
                Log.d("StrAccessibilityService", "Access NOTIFIFY:"+event.getEventTime());
                Log.d("StrAccessibilityService", "Access NOTIFIFY:"+event.getText());
                Log.d("StrAccessibilityService", "Access NOTIFIFY:"+event.getParcelableData());
            }
            putPreferences(event);
            break;
        }
    }
 
    private void putPreferences(AccessibilityEvent event){// Preferenceを変更
        SharedPreferences.Editor editor = mPrefAccess.edit();
        editor.putString(ACCESS_PREFS_KEY, ""+event.getText());
        editor.commit();
    }
    

	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub

	}

}
