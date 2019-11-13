package com.example.moviedb.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceHelper {

	private SharedPreferences sharedPreference;

	private static String SHARE_PREFRENCE = "showtimePref";

	private static String SESSION_ID_KEY = "sessionId";

	private static String USER_NAME_KEY = "username";

		
	public SharePreferenceHelper(Context context)
	{
		sharedPreference = context.getSharedPreferences(SHARE_PREFRENCE, Context.MODE_PRIVATE);
	}

	public void setLogin(String sessionId)
	{
		SharedPreferences.Editor editor = sharedPreference.edit();
		editor.putString(SESSION_ID_KEY, sessionId);
		editor.commit();
	}

	public String getSessionId() {
		return sharedPreference.getString(SESSION_ID_KEY, "");
	}

	public String getUserName() {
		return sharedPreference.getString(USER_NAME_KEY,"");
	}

	public void logoutSharePreference()
	{
		SharedPreferences.Editor editor = sharedPreference.edit();
		editor.clear();
		editor.commit();
	}

	public boolean isLogin()
	{
		if(sharedPreference.contains(SESSION_ID_KEY))
		{
			return true;
		}
		else
		{
			return false;
		}
	}


}
