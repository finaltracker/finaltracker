package com.adn.db;


import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	static private String TAG = "DBManager";
    
    static private Map< String, DBHelper > registedDbMap = null;
    static  Context context = null;
    
    public DBManager(Context context  ){
    	
    	this.context = context;
    	registedDbMap = new HashMap< String, DBHelper >();
  	
    }

    //success return DbHelper ,else return false
    static public DBHelper  GetDbHelper( Class c )
    {


    	
    	Log.d(TAG,"GetDbHelper " + c.getSimpleName() );
    	DBHelper helperFmdb = new DBHelper(context , c );

    	registedDbMap.put(c.getSimpleName(), helperFmdb);
    	
    	return helperFmdb;
    }
   
  
}
