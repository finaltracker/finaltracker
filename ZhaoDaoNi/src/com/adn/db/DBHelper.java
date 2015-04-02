package com.adn.db;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DBHelper�̳���SQLiteOpenHelper����Ϊά���͹������ݿ�Ļ���
 * @author bixiaopeng 2013-2-16 ����3:05:52
 */
public class DBHelper  extends SQLiteOpenHelper{
	
	public static final String DB_NAME = "zdn.db";
	public String table = "null";
	private static final int DB_VERSION=1;
	private static final String TAG = "DBHelper";
	private Field[]ClassFs = null;
	private SQLiteDatabase dbFmdb;
	private Class dbClass = null ;
	
	public DBHelper(Context context,Class c) {
		//Context context, String name, CursorFactory factory, int version
		//factory����null,ʹ��Ĭ��ֵ
		super(context, DB_NAME, null, DB_VERSION);
		table = c.getSimpleName();
		this.dbClass = c;
		this.ClassFs = c.getFields();
		dbFmdb = getWritableDatabase();
	}
	//���ݵ�һ�δ�����ʱ������onCreate
	@Override
	public void onCreate(SQLiteDatabase db) {		
		//������
		
		 String variableCombin = "(_id INTEGER PRIMARY KEY AUTOINCREMENT";
		 
		 for (Field field : ClassFs )
		 {
			 variableCombin +="," + field.getName() + " STRING";
		 }
		 variableCombin +=")";
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS " + table +  variableCombin );
		 Log.i(TAG, "create table");
	}
	//���ݿ��һ�δ���ʱonCreate�����ᱻ���ã����ǿ���ִ�д��������䣬��ϵͳ���ְ汾�仯֮�󣬻����onUpgrade���������ǿ���ִ���޸ı�ṹ�����
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//�ڱ�info������һ��other
		//db.execSQL("ALTER TABLE info ADD COLUMN other STRING");  
	    Log.i("WIRELESSQA", "update sqlite "+oldVersion+"---->"+newVersion);
	}
	
	 public void add(List<Object> memberInfo ) {
	        dbFmdb.beginTransaction();// ��ʼ����
	        try {
	        	String execSqlStrBegin = "INSERT INTO "+table+" VALUES(null";
	        	String execSqlStrEnd = ")";
	        	
	        	String execSqlStrAll = execSqlStrBegin;
	        	
	        	int paraNumber = ClassFs.length;
	        	
	        	for( int i = 0 ; i < paraNumber ; i++ )
	        	{
	        		execSqlStrAll += ",?";
	        	}
	        	
	        	execSqlStrAll += execSqlStrEnd;
	        	
	        	Object[] newObjArray = new Object[paraNumber] ;
	        	
	            for (Object mi : memberInfo) {
	                Log.i(TAG, "------add memberInfo----------");
	                //Log.i(TAG, info.teamName + "/" + info.memberName + "/" +info.phoneNumber + "/" +info.nickName+ "/" +info.comment+"/" + info.pictureAddress );
	                // ���info�в�������
	                for( int i = 0 ; i < paraNumber ; i++ )
	                //for (Field field : friendMemberDataBasicClassFs )
	            	{
	                	Object object = ClassFs[i].get(mi);

	                	newObjArray[i] = (String)object ;
	            	}
	                //db.execSQL("INSERT INTO info VALUES(null,?,?,?,?)", new Object[] { info.teamName, info.memberName, info.phoneNumber, info.nickName , info.comment , info.pictureAddress });
	                dbFmdb.execSQL( execSqlStrAll , newObjArray);
	            }
	            dbFmdb.setTransactionSuccessful();// ����ɹ�
	        } catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            dbFmdb.endTransaction();// ��������
	        }
	    }
	 
	 public void add( Object mi ) {
	        dbFmdb.beginTransaction();// ��ʼ����
	        try {
	        	String execSqlStrBegin = "INSERT INTO "+table+" VALUES(null";
	        	String execSqlStrEnd = ")";
	        	
	        	String execSqlStrAll = execSqlStrBegin;
	        	
	        	int paraNumber = ClassFs.length;
	        	
	        	for( int i = 0 ; i < paraNumber ; i++ )
	        	{
	        		execSqlStrAll += ",?";
	        	}
	        	
	        	execSqlStrAll += execSqlStrEnd;
	        	
	        	Object[] newObjArray = new Object[paraNumber] ;
	        	
	            //for (Object mi : memberInfo) 
	        	{
	                Log.i(TAG, "------add memberInfo----------");
	                //Log.i(TAG, info.teamName + "/" + info.memberName + "/" +info.phoneNumber + "/" +info.nickName+ "/" +info.comment+"/" + info.pictureAddress );
	                // ���info�в�������
	                for( int i = 0 ; i < paraNumber ; i++ )
	                //for (Field field : friendMemberDataBasicClassFs )
	            	{
	                	Object object = ClassFs[i].get(mi);

	                	newObjArray[i] = (String)object ;
	            	}
	                //db.execSQL("INSERT INTO info VALUES(null,?,?,?,?)", new Object[] { info.teamName, info.memberName, info.phoneNumber, info.nickName , info.comment , info.pictureAddress });
	                dbFmdb.execSQL( execSqlStrAll , newObjArray);
	            }
	            dbFmdb.setTransactionSuccessful();// ����ɹ�
	        } catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            dbFmdb.endTransaction();// ��������
	        }
	    }

	    public void clearData( ) {
	        ExecSQL("DELETE FROM "+table );
	        Log.i(TAG, "clear data");
	    }
	    
	    /**
	     * ͨ�����ֲ�ѯ��Ϣ,�������е�����
	     * 
	     * @param name
	     */
	    public ArrayList<Object> searchData(final String name   ) {
	        String sql = "SELECT * FROM "+table+" WHERE name =" + "'" + name + "'";
	        return ExecSQLForMemberInfo(sql);
	    }

	    public ArrayList<Object> searchAllData(  ) {
	        String sql = "SELECT * FROM " + table;
	        return ExecSQLForMemberInfo(sql);
	    }

	    /**
	     * ͨ���������޸�ֵ
	     * 
	     * @param raw
	     * @param rawValue
	     * @param whereName
	     */
	    public void updateData(String raw, String rawValue, String whereName  ) {
	        String sql = "UPDATE "+table+" SET " + raw + " =" + " " + "'" + rawValue + "'" + " WHERE name =" + "'" + whereName
	                     + "'";
	        ExecSQL(sql);
	        Log.i(TAG, sql);
	    }

	    /**
	     * ִ��SQL�����list
	     * 
	     * @param sql
	     * @return
	     */
	    private ArrayList<Object> ExecSQLForMemberInfo(String sql) {
	        ArrayList<Object> list = new ArrayList<Object>();
	        Cursor c = ExecSQLForCursor(sql);
	        while (c.moveToNext()) {
	        	 Object fmd = null;
				try {
					fmd = dbClass.newInstance();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	 
	        	 for (Field field : ClassFs ) {
	             	String value = null;
	             	
	             	try {
						field.set( fmd, c.getString(c.getColumnIndex( field.getName())) );
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	 }
	            
	            list.add(fmd);
	        }
	        c.close();
	        return list;
	    }

	    /**
	     * ִ��һ��SQL���
	     * 
	     * @param sql
	     */
	    private void ExecSQL(String sql) {
	        try {
	            dbFmdb.execSQL(sql);
	            Log.i("execSql: ", sql);
	        } catch (Exception e) {
	            Log.e("ExecSQL Exception", e.getMessage());
	            e.printStackTrace();
	        }
	    }

	    /**
	     * ִ��SQL������һ���α�
	     * 
	     * @param sql
	     * @return
	     */
	    private Cursor ExecSQLForCursor(String sql) {
	        Cursor c = dbFmdb.rawQuery(sql, null);
	        return c;
	    }

	    public void closeDB() {
	    	
	    	if(dbFmdb.isOpen())
	        {
	    		dbFmdb.close();
	        }
	    }

}
