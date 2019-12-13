package com.example.moviedb.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharePreferenceHelper {

	private SharedPreferences sharedPreference;

	private static String SHARE_PREFRENCE = "showtimePref";

	private static String SESSION_ID_KEY = "sessionId";

	private static String USER_NAME_KEY = "username";

	private static String USER_ID_KEY = "id";

		
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

	public void setUserName_Id(String userName, int id) {
		SharedPreferences.Editor editor = sharedPreference.edit();
		editor.putString(USER_NAME_KEY, userName);
		editor.putInt(USER_ID_KEY, id);
		editor.commit();
	}
	public String getSessionId() {
		return sharedPreference.getString(SESSION_ID_KEY, "");
	}

	public String getUserName() {
		return sharedPreference.getString(USER_NAME_KEY,"");
	}

	public int getUserId() {
		return sharedPreference.getInt(USER_ID_KEY, -100);
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
