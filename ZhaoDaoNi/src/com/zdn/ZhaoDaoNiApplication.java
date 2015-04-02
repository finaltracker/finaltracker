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
         
         JPushInterface.setDebugMode(true); 	// ���ÿ�����־,����ʱ��ر���־
         JPushInterface.init(this);     		// ��ʼ�� JPush
    }
}
