package com.zdn.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

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
		return ( sharedPreferences.getInt("friendListVersion", -1));
	}
}
