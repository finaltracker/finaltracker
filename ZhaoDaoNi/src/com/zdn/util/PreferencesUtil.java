package com.zdn.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

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
		Log.d("PreferencesUtil" , "save friendListVersion = " + friendListVersion );
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
		Log.d("PreferencesUtil" , "read getFriendListVersion = " + sharedPreferences.getInt("friendListVersion", -1) );
				

		return ( sharedPreferences.getInt("friendListVersion", -1));
	}
	
	public void savePhoneNumber( String phone ){
		Log.d("PreferencesUtil" , "save phone = " + phone );
		SharedPreferences sharedPreferences = context.getSharedPreferences( preferenceFileName, Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putString("phone", phone);
		editor.commit();	//�����ύ��xml�ļ���
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
		editor.commit();	//�����ύ��xml�ļ���
	}
	

	public String getPassWord(){
		SharedPreferences sharedPreferences = context.getSharedPreferences( preferenceFileName , Context.MODE_PRIVATE);
		return ( sharedPreferences.getString("PassWord", ""));
	}
}
