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
 * DBHelper继承了SQLiteOpenHelper，作为维护和管理数据库的基类
 * @author bixiaopeng 2013-2-16 下午3:05:52
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
		//factory输入null,使用默认值
		super(context, DB_NAME, null, DB_VERSION);
		table = c.getSimpleName();
		this.dbClass = c;
		this.ClassFs = c.getFields();
		dbFmdb = getWritableDatabase();
	}
	//数据第一次创建的时候会调用onCreate
	@Override
	public void onCreate(SQLiteDatabase db) {		
		//创建表
		
		 String variableCombin = "(_id INTEGER PRIMARY KEY AUTOINCREMENT";
		 
		 for (Field field : ClassFs )
		 {
			 variableCombin +="," + field.getName() + " STRING";
		 }
		 variableCombin +=")";
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS " + table +  variableCombin );
		 Log.i(TAG, "create table");
	}
	//数据库第一次创建时onCreate方法会被调用，我们可以执行创建表的语句，当系统发现版本变化之后，会调用onUpgrade方法，我们可以执行修改表结构等语句
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//在表info中增加一列other
		//db.execSQL("ALTER TABLE info ADD COLUMN other STRING");  
	    Log.i("WIRELESSQA", "update sqlite "+oldVersion+"---->"+newVersion);
	}
	
	 public void add(List<Object> memberInfo ) {
	        dbFmdb.beginTransaction();// 开始事务
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
	                // 向表info中插入数据
	                for( int i = 0 ; i < paraNumber ; i++ )
	                //for (Field field : friendMemberDataBasicClassFs )
	            	{
	                	Object object = ClassFs[i].get(mi);

	                	newObjArray[i] = (String)object ;
	            	}
	                //db.execSQL("INSERT INTO info VALUES(null,?,?,?,?)", new Object[] { info.teamName, info.memberName, info.phoneNumber, info.nickName , info.comment , info.pictureAddress });
	                dbFmdb.execSQL( execSqlStrAll , newObjArray);
	            }
	            dbFmdb.setTransactionSuccessful();// 事务成功
	        } catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            dbFmdb.endTransaction();// 结束事务
	        }
	    }
	 
	 public void add( Object mi ) {
	        dbFmdb.beginTransaction();// 开始事务
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
	                // 向表info中插入数据
	                for( int i = 0 ; i < paraNumber ; i++ )
	                //for (Field field : friendMemberDataBasicClassFs )
	            	{
	                	Object object = ClassFs[i].get(mi);

	                	newObjArray[i] = (String)object ;
	            	}
	                //db.execSQL("INSERT INTO info VALUES(null,?,?,?,?)", new Object[] { info.teamName, info.memberName, info.phoneNumber, info.nickName , info.comment , info.pictureAddress });
	                dbFmdb.execSQL( execSqlStrAll , newObjArray);
	            }
	            dbFmdb.setTransactionSuccessful();// 事务成功
	        } catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            dbFmdb.endTransaction();// 结束事务
	        }
	    }

	    public void clearData( ) {
	        ExecSQL("DELETE FROM "+table );
	        Log.i(TAG, "clear data");
	    }
	    
	    /**
	     * 通过名字查询信息,返回所有的数据
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
	     * 通过名字来修改值
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
	     * 执行SQL命令返回list
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
	     * 执行一个SQL语句
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
	     * 执行SQL，返回一个游标
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
