package com.zdn.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesUtil {
	private Context context;
	private final String preferenceFileName = "zdnPreference";
	//���췽���д��������Ķ���
	public PreferencesUtil(Context context) {
		super();
		this.context = context;
	}

	/**
	 * �������
	 * @param name ����
	 * @param age ����
	 */
	public void saveFriendListVersion(int friendListVersion ){
		SharedPreferences sharedPreferences = context.getSharedPreferences( preferenceFileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt("friendListVersion", friendListVersion);
		editor.commit();	//�����ύ��xml�ļ���
	}
	
	/**
	 * ��ȡ�������ò���
	 * @return params
	 */
	public int getFriendListVersion(){
		SharedPreferences sharedPreferences = context.getSharedPreferences( preferenceFileName , Context.MODE_PRIVATE);
		return ( sharedPreferences.getInt("friendListVersion", -1));
	}
}
