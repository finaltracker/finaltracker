package com.zdn.db;


import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

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
