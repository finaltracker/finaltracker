package com.zdn;

import com.adn.db.DBManager;
import com.zdn.util.FileUtil;

import cn.jpush.android.api.JPushInterface;
import android.app.Application;
import android.util.Log;

public class ZhaoDaoNiApplication extends Application {
	DBManager dbManager = null;
	
	@Override
    public void onCreate() {    	     
    	 Log.d( "ZhaoDaoNiApplication", "ZhaoDaoNiApplication onCreate");
         super.onCreate();
         
         
         //make local dir
         FileUtil.createBasePath();
         FileUtil.makeFile(getString(R.string.friendsDir) );
         FileUtil.makeFile(getString(R.string.friendsAvator) );
         //create db manager
         
         dbManager = new DBManager(this);
         
         JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
         JPushInterface.init(this);     		// 初始化 JPush
    }
}
