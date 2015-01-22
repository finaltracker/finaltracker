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
 *DBManager�ǽ�����DBHelper֮�ϣ���װ�˳��õ�ҵ�񷽷�
 * @author bixiaopeng 2013-2-16 ����3:06:26
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
     * ���info������һ����Ա��Ϣ
     * 
     * @param memberInfo
     */
    public void add(List<friendMemberData> memberInfo) {
        db.beginTransaction();// ��ʼ����
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
                // ���info�в�������
                for( int i = 0 ; i < paraNumber ; i++ )
                //for (Field field : friendMemberDataBasicClassFs )
            	{
                	newObjArray[i] = (String) friendMemberDataBasicClassFs[i].get(info.basic) ;
            	}
                //db.execSQL("INSERT INTO info VALUES(null,?,?,?,?)", new Object[] { info.teamName, info.memberName, info.phoneNumber, info.nickName , info.comment , info.pictureAddress });
                db.execSQL( execSqlStrAll , newObjArray);
            }
            db.setTransactionSuccessful();// ����ɹ�
        } catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            db.endTransaction();// ��������
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
     * ͨ��name��ɾ������
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
     * �������
     */
    public void clearData() {
        ExecSQL("DELETE FROM info");
        Log.i(TAG, "clear data");
    }

    /**
     * ͨ�����ֲ�ѯ��Ϣ,�������е�����
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
     * ͨ���������޸�ֵ
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
     * ִ��SQL�����list
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
     * ִ��һ��SQL���
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
     * ִ��SQL������һ���α�
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
