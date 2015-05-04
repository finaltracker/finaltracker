package com.zdn.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

public class PreferencesUtil {
	private Context context;
	private final String preferenceFileName = "zdnPreference";
	//构造方法中传入上下文对象
	public PreferencesUtil(Context context) {
		super();
		this.context = context;
	}

	/**
	 * 保存参数
	 * @param name 姓名
	 * @param age 年龄
	 */
	public void saveFriendListVersion(int friendListVersion ){
		Log.d("PreferencesUtil" , "save friendListVersion = " + friendListVersion );
		SharedPreferences sharedPreferences = context.getSharedPreferences( preferenceFileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt("friendListVersion", friendListVersion);
		editor.commit();	//数据提交到xml文件中
	}
	
	/**
	 * 获取各项配置参数
	 * @return params
	 */
	public int getFriendListVersion(){
		SharedPreferences sharedPreferences = context.getSharedPreferences( preferenceFileName , Context.MODE_PRIVATE);
		Log.d("PreferencesUtil" , "read getFriendListVersion = " + sharedPreferences.getInt("friendListVersion", -1) );
				

		return ( sharedPreferences.getInt("friendListVersion", -1));
	}
	
	public void savePhoneNumber( String phone ){
		Log.d("PreferencesUtil" , "save phone = " + phone );
		SharedPreferences sharedPreferences = context.getSharedPreferences( preferenceFileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("phone", phone);
		editor.commit();	//数据提交到xml文件中
	}
	

	public String getPhoneNumber(){
		SharedPreferences sharedPreferences = context.getSharedPreferences( preferenceFileName , Context.MODE_PRIVATE);
		return ( sharedPreferences.getString("phone", ""));
	}
	
	public void savePassWord( String PassWord ){
		Log.d("PreferencesUtil" , "save PassWord = " + PassWord );
		SharedPreferences sharedPreferences = context.getSharedPreferences( preferenceFileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("PassWord", PassWord);
		editor.commit();	//数据提交到xml文件中
	}
	

	public String getPassWord(){
		SharedPreferences sharedPreferences = context.getSharedPreferences( preferenceFileName , Context.MODE_PRIVATE);
		return ( sharedPreferences.getString("PassWord", ""));
	}
}
