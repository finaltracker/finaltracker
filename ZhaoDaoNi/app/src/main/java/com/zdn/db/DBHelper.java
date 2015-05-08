package com.zdn.db;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * DBHelper driver SQLiteOpenHelper it is a database base class

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

		super(context, DB_NAME, null, DB_VERSION);
		table = c.getSimpleName();
		this.dbClass = c;
		this.ClassFs = c.getDeclaredFields();
		dbFmdb = getWritableDatabase();
		
		onCreate(dbFmdb); // create table
	}
	//call in the first run function onCreate
	@Override
	public void onCreate(SQLiteDatabase db) {		
		//create table
		
		 String variableCombin = "(_id INTEGER PRIMARY KEY AUTOINCREMENT";
		 
		 for (Field field : ClassFs )
		 {
			 //Log.d( this.getClass().getSimpleName(),field.getType().getSimpleName() );
			 variableCombin +="," + field.getName() + " " + "STRING";
		 }
		 variableCombin +=")";
		 
		 db.execSQL("CREATE TABLE IF NOT EXISTS " + table +  variableCombin );
		 Log.i(TAG, "create table:" + table );
	}
	
	private Object stringToType(Class<?> toWhatType , String in )
	{
		
		if( toWhatType == String.class )
		{
			return in;
		}
		else if( toWhatType == int.class )
		{
			return Integer.parseInt(in);
		}
		else if( toWhatType == long.class)
		{ // no support
			return null;
		}
		
		else if( toWhatType == Integer.class )
		{// no support
			return null;
		}
		else if( toWhatType == Boolean.class )
		{
			if( in.equals("1") )
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else if( toWhatType == Date.class )
		{
			
			 // no support
			return null;

		}
		
		return null;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//db.execSQL("ALTER TABLE info ADD COLUMN other STRING");
	    Log.i("WIRELESSQA", "update sqlite "+oldVersion+"---->"+newVersion);
	}
	
	 public void add(List<Object> memberInfo ) {
	        dbFmdb.beginTransaction();// start business
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
	                // insert data into table
	                for( int i = 0 ; i < paraNumber ; i++ )
	                //for (Field field : friendMemberDataBasicClassFs )
	            	{
	                	Object object = ClassFs[i].get(mi);

	                	newObjArray[i] = object ;
	            	}
	                //db.execSQL("INSERT INTO info VALUES(null,?,?,?,?)", new Object[] { info.teamName, info.memberName, info.phoneNumber, info.nickName , info.comment , info.pictureAddress });
	                dbFmdb.execSQL( execSqlStrAll , newObjArray);
	            }
	            dbFmdb.setTransactionSuccessful();// success
	        } catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            dbFmdb.endTransaction();//
	        }
	    }
	 
	 public void add( Object mi ) {
	        dbFmdb.beginTransaction();//
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
	                //
	                for( int i = 0 ; i < paraNumber ; i++ )
	                //for (Field field : friendMemberDataBasicClassFs )
	            	{
	                	Object object = ClassFs[i].get(mi);

	                	newObjArray[i] = object ;
	            	}
	                //db.execSQL("INSERT INTO info VALUES(null,?,?,?,?)", new Object[] { info.teamName, info.memberName, info.phoneNumber, info.nickName , info.comment , info.pictureAddress });
	                dbFmdb.execSQL( execSqlStrAll , newObjArray);
	            }
	            dbFmdb.setTransactionSuccessful();
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
	     *
	     * 
	     * @param name
	     */
	    public ArrayList<Object> searchData(final String what , String value ) {
	        String sql = "SELECT * FROM "+table+" WHERE "+what+" =" + "'" + value + "'";
	        return ExecSQLForMemberInfo(sql);
	    }

	    public ArrayList<Object> searchAllData(  ) {
	        String sql = "SELECT * FROM " + table;
	        return ExecSQLForMemberInfo(sql);
	    }

	    /**
	     *
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
	     *
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
					Log.d(this.getClass().getSimpleName() , fmd.toString() );
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
	             		
	             		Object middle = stringToType( field.getType() ,c.getString(c.getColumnIndex( field.getName())) );
						field.set( fmd, middle );
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
	     * do a SQL
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
	     *
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
