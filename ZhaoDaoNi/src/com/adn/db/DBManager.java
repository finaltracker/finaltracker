package com.adn.db;


import java.util.ArrayList;
import java.util.List;

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
     * 向表info中增加一个成员信息
     * 
     * @param memberInfo
     */
    public void add(List<MemberInfo> memberInfo) {
        db.beginTransaction();// 开始事务
        try {
            for (MemberInfo info : memberInfo) {
                Log.i(TAG, "------add memberInfo----------");
                Log.i(TAG, info.teamName + "/" + info.memberName + "/" + info.pictureAddress );
                // 向表info中插入数据
                db.execSQL("INSERT INTO info VALUES(null,?,?,?)", new Object[] { info.teamName, info.memberName, info.pictureAddress });
            }
            db.setTransactionSuccessful();// 事务成功
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
        ExecSQL("DELETE * FROM info");
        Log.i(TAG, "clear data");
    }

    /**
     * 通过名字查询信息,返回所有的数据
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
