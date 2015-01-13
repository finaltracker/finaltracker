package com.adn.db;


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
	
	public DBHelper(Context context) {
		//Context context, String name, CursorFactory factory, int version
		//factory����null,ʹ��Ĭ��ֵ
		super(context, DB_NAME, null, DB_VERSION);
	}
	//���ݵ�һ�δ�����ʱ������onCreate
	@Override
	public void onCreate(SQLiteDatabase db) {		
		//������
		  db.execSQL("CREATE TABLE IF NOT EXISTS info" +  
	                "(_id INTEGER PRIMARY KEY AUTOINCREMENT,teamName STRING ,  memberName VARCHAR, phoneNumber STRING ,pictureAddress STRING )");
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
