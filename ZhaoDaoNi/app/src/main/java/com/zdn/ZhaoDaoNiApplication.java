package com.zdn;

import com.zdn.db.DBManager;
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
         FileUtil.makeFile(getString(R.string.friendsDir));
         FileUtil.makeFile(getString(R.string.friendsAvator));
         FileUtil.makeFile(getString(R.string.SendPictureTofriends));
         FileUtil.makeFile(getString(R.string.ReceivedPictureFromfriends));
         FileUtil.makeFile(getString(R.string.RecordVoice));
         FileUtil.makeFile(getString(R.string.ReceivedAudioFromfriends));
        //create db manager
         
         dbManager = new DBManager(this);
         
         JPushInterface.setDebugMode(true);
         JPushInterface.init(this);
    }
}
