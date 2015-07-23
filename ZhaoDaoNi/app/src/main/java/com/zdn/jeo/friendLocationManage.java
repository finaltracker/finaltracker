package com.zdn.jeo;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.zdn.logic.MainControl;

/**
 * Created by wanghp1 on 2015/7/22.
 */
public class friendLocationManage {
    static private int requiredPeriod = 1000;
    static private boolean stop = false;
    static Handler handler =  new Handler() {
        public void handleMessage(Message msg) {
            if( msg.what == 1 )
            {
                MainControl.locationGet( null , false );
            }
        }};

    static public void periodRequiredStart()
    {
        stop = false;

        if (Looper.myLooper()== null)
        {
            Looper.prepare();
        }

        handler.postDelayed( myRunnable , requiredPeriod );
    }

    static public void stopRequireGeo()
    {
        stop = true;
    }

    static private Runnable myRunnable = new Runnable() {
        public void run() {
            if( stop )
            {
                handler.getLooper().quit();
                return;
            }
            else
            {
                Message m = handler.obtainMessage();
                m.what = 1;
                handler.sendMessage( m );

                handler.postDelayed( myRunnable , requiredPeriod );
            }

        }
    };
}
