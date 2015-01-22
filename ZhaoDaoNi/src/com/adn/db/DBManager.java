package com.adn.db;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.zdn.basicStruct.friendMemberData;
import com.zdn.basicStruct.friendMemberDataBasic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 *DBManager是建立在DBHelper之上，封装了常用的业务方法
 * @author bixiaopeng 2013-2-16 下午3:06:26
 */
public class DBManager {

    private DBHelper       helper;
    private SQLiteDatabase db;
    private String TAG = "DBManager";
    
    Field[]friendMemberDataBasicClassFs = null;

    public DBManager(Context context  ){
         Class c = null;
		c = friendMemberDataBasic.class;
		friendMemberDataBasicClassFs = c.getFields(); 
		
		helper = new DBHelper(context , friendMemberDataBasicClassFs );
        db = helper.getWritableDatabase();
       
    }

    
    /**
     * 向表info中增加一个成员信息
     * 
     * @param memberInfo
     */
    public void add(List<friendMemberData> memberInfo) {
        db.beginTransaction();// 开始事务
        try {
        	String execSqlStrBegin = "INSERT INTO info VALUES(null";
        	String execSqlStrEnd = ")";
        	
        	String execSqlStrAll = execSqlStrBegin;
        	
        	int paraNumber = friendMemberDataBasicClassFs.length;
        	
        	for( int i = 0 ; i < paraNumber ; i++ )
        	{
        		execSqlStrAll += ",?";
        	}
        	
        	execSqlStrAll += execSqlStrEnd;
        	
        	Object[] newObjArray = new Object[paraNumber] ;
        	
            for (friendMemberData info : memberInfo) {
                Log.i(TAG, "------add memberInfo----------");
                //Log.i(TAG, info.teamName + "/" + info.memberName + "/" +info.phoneNumber + "/" +info.nickName+ "/" +info.comment+"/" + info.pictureAddress );
                // 向表info中插入数据
                for( int i = 0 ; i < paraNumber ; i++ )
                //for (Field field : friendMemberDataBasicClassFs )
            	{
                	newObjArray[i] = (String) friendMemberDataBasicClassFs[i].get(info.basic) ;
            	}
                //db.execSQL("INSERT INTO info VALUES(null,?,?,?,?)", new Object[] { info.teamName, info.memberName, info.phoneNumber, info.nickName , info.comment , info.pictureAddress });
                db.execSQL( execSqlStrAll , newObjArray);
            }
            db.setTransactionSuccessful();// 事务成功
        } catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            db.endTransaction();// 结束事务
        }
    }

    /**
     * @param _id
     * @param name
     * @param age
     * @param website
     * @param weibo
     */
    //classPacketName object
    public void add(  Object o ) 
    {
        Log.i(TAG, "------add data----------");
        ContentValues cv = new ContentValues();
        // cv.put("_id", _id);
        for (Field field : friendMemberDataBasicClassFs ) {
        	String value = null;
        	try {
				value = (String) field.get(o);
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	cv.put(field.getName(),value );

        	Log.i(TAG, field.getName() +" = " +  value );
        }
       
        db.insert(DBHelper.DB_TABLE_NAME, null, cv);
    }

    /**
     * 通过name来删除数据
     * 
     * @param name
     */
    public void delData(String name) {
        // ExecSQL("DELETE FROM info WHERE name ="+"'"+name+"'");
        String[] args = { name };
        db.delete(DBHelper.DB_TABLE_NAME, "name=?", args);
        Log.i(TAG, "delete data by " + name);

    }

    /**
     * 清空数据
     */
    public void clearData() {
        ExecSQL("DELETE FROM info");
        Log.i(TAG, "clear data");
    }

    /**
     * 通过名字查询信息,返回所有的数据
     * 
     * @param name
     */
    public ArrayList<friendMemberData> searchData(final String name) {
        String sql = "SELECT * FROM info WHERE name =" + "'" + name + "'";
        return ExecSQLForMemberInfo(sql);
    }

    public ArrayList<friendMemberData> searchAllData() {
        String sql = "SELECT * FROM info";
        return ExecSQLForMemberInfo(sql);
    }

    /**
     * 通过名字来修改值
     * 
     * @param raw
     * @param rawValue
     * @param whereName
     */
    public void updateData(String raw, String rawValue, String whereName) {
        String sql = "UPDATE info SET " + raw + " =" + " " + "'" + rawValue + "'" + " WHERE name =" + "'" + whereName
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
    private ArrayList<friendMemberData> ExecSQLForMemberInfo(String sql) {
        ArrayList<friendMemberData> list = new ArrayList<friendMemberData>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
        	 friendMemberData fmd = new friendMemberData();
        	 
        	 for (Field field : friendMemberDataBasicClassFs ) {
             	String value = null;
             	
             	try {
					field.set( fmd.basic, c.getString(c.getColumnIndex( field.getName())) );
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
            db.execSQL(sql);
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
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void closeDB() {
        db.close();
    }

}
