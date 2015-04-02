package com.zdn;

import com.adn.db.DBManager;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;
import android.util.Log;

public class ZhaoDaoNiApplication extends Application {
	DBManager dbManager = null;
	
	@Override
    public void onCreate() {    	     
    	 Log.d( "ZhaoDaoNiApplication", "ZhaoDaoNiApplication onCreate");
         super.onCreate();
         
         //create db manager
         
         dbManager = new DBManager(this);
         
         JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
         JPushInterface.init(this);     		// 初始化 JPush
    }
}
