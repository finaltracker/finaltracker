package com.adn.db;


import java.lang.reflect.Field;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DBHelper�̳���SQLiteOpenHelper����Ϊά���͹������ݿ�Ļ���
 * @author bixiaopeng 2013-2-16 ����3:05:52
 */
public class DBHelper  extends SQLiteOpenHelper{
	
	public static final String DB_NAME = "zdn.db";
	public static final String DB_TABLE_NAME = "info";
	private static final int DB_VERSION=1;
	private static final String TAG = "DBHelper";
	private Field[]friendMemberDataBasicClassFs = null;
	
	public DBHelper(Context context ,Field[]friendMemberDataBasicClassFs ) {
		//Context context, String name, CursorFactory factory, int version
		//factory����null,ʹ��Ĭ��ֵ
		super(context, DB_NAME, null, DB_VERSION);
		
		this.friendMemberDataBasicClassFs = friendMemberDataBasicClassFs;
	}
	//���ݵ�һ�δ�����ʱ������onCreate
	@Override
	public void onCreate(SQLiteDatabase db) {		
		//������
		
		 String variableCombin = "(_id INTEGER PRIMARY KEY AUTOINCREMENT";
		 
		 for (Field field : friendMemberDataBasicClassFs )
		 {
			 variableCombin +="," + field.getName() + " STRING";
		 }
		 variableCombin +=")";
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS info" +  variableCombin );
		 Log.i(TAG, "create table");
	}
	//���ݿ��һ�δ���ʱonCreate�����ᱻ���ã����ǿ���ִ�д��������䣬��ϵͳ���ְ汾�仯֮�󣬻����onUpgrade���������ǿ���ִ���޸ı�ṹ�����
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//�ڱ�info������һ��other
		//db.execSQL("ALTER TABLE info ADD COLUMN other STRING");  
	    Log.i("WIRELESSQA", "update sqlite "+oldVersion+"---->"+newVersion);
	}
	

}
