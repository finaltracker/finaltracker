package com.adn.db;


import java.util.ArrayList;
import java.util.List;

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

    public DBManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public class MemberInfo {

        public int    _id;
        public String teamName;
        public String memberName;
        public String pictureAddress;

        public MemberInfo(){}
        public MemberInfo(int _id,String teamName , String name,String pictureAddress ){
            this._id = _id;
            this.teamName = teamName;
            this.memberName = memberName;
            this.pictureAddress = pictureAddress;
        }

    }
    
    /**
     * ���info������һ����Ա��Ϣ
     * 
     * @param memberInfo
     */
    public void add(List<MemberInfo> memberInfo) {
        db.beginTransaction();// ��ʼ����
        try {
            for (MemberInfo info : memberInfo) {
                Log.i(TAG, "------add memberInfo----------");
                Log.i(TAG, info.teamName + "/" + info.memberName + "/" + info.pictureAddress );
                // ���info�в�������
                db.execSQL("INSERT INTO info VALUES(null,?,?,?)", new Object[] { info.teamName, info.memberName, info.pictureAddress });
            }
            db.setTransactionSuccessful();// ����ɹ�
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
    public void add(int _id, String teamName , String memberName, String pictureAddress ) {
        Log.i(TAG, "------add data----------");
        ContentValues cv = new ContentValues();
        // cv.put("_id", _id);
        cv.put("teamName", teamName );
        cv.put("memberName", memberName);
        cv.put("pictureAddress", pictureAddress);
        db.insert(DBHelper.DB_TABLE_NAME, null, cv);
        Log.i(TAG, teamName + "/" + memberName + "/" + pictureAddress  );
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
        ExecSQL("DELETE * FROM info");
        Log.i(TAG, "clear data");
    }

    /**
     * ͨ�����ֲ�ѯ��Ϣ,�������е�����
     * 
     * @param name
     */
    public ArrayList<MemberInfo> searchData(final String name) {
        String sql = "SELECT * FROM info WHERE name =" + "'" + name + "'";
        return ExecSQLForMemberInfo(sql);
    }

    public ArrayList<MemberInfo> searchAllData() {
        String sql = "SELECT  FROM info";
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
    private ArrayList<MemberInfo> ExecSQLForMemberInfo(String sql) {
        ArrayList<MemberInfo> list = new ArrayList<MemberInfo>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            MemberInfo info = new MemberInfo();
            info._id = c.getInt(c.getColumnIndex("_id"));
            info.teamName = c.getString(c.getColumnIndex("teamName"));
            info.memberName = c.getString(c.getColumnIndex("memberName"));
            info.pictureAddress = c.getString(c.getColumnIndex("pictureAddress"));
            list.add(info);
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
