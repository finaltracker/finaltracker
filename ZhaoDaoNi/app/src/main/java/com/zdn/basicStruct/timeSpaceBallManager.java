package com.zdn.basicStruct;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.zdn.logic.MainControl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghp1 on 2015/9/28.
 */
public class timeSpaceBallManager {
    static private int requiredPeriod = 1000 * 60 ; // 1 MINUTE
    List<timeSpaceBall> tsbList = new ArrayList<timeSpaceBall>();
    static private boolean stop = false;
    public coordinate centerOfBalls = null;
    private List<ballStateChanged> ballStateChangedListener = new ArrayList<ballStateChanged>();
    static public final long  ALL_BALL_RANGER = 100000; //100 KM
    private final long CENTER_OF_BALLS_UPDATE_DISTANCE = 100 ; // 移动超过 CENTER_OF_BALLS_UPDATE_DISTANCE 则需要更新附近的球
    static Handler handler =  new Handler() {
        public void handleMessage(Message msg) {
            if( msg.what == 1 )
            {
                MainControl.getBallLocationDefault("3");
            }
        }};

    public void addA_TimeSpaceBall( timeSpaceBall add )
    {
        tsbList.add( add );
        for ( ballStateChanged bsc : ballStateChangedListener )
        {
            bsc.addA_Ball( add );
        }
    }

    public void ballPositionMove( String BallId , String lat , String lng )
    {
        for ( timeSpaceBall tsb : tsbList
             ) {
            if(tsb.getBallId().equals( BallId ))
            {
                tsb.setLat( lat);
                tsb.setLng( lng );

                for ( ballStateChanged bsc : ballStateChangedListener )
                {
                    bsc.BallPositionMove( tsb );
                }
            }

        }
    }
    public void removeA_TimeSpaceBall( String ballId )
    {
        if( ballId != null && (!ballId.isEmpty()) )
        {
            for ( timeSpaceBall tsb: tsbList
                 )
            {
                if( ballId.equals( tsb.getBallId() ))
                {
                    tsbList.remove(tsb);
                    for ( ballStateChanged bsc : ballStateChangedListener )
                    {
                        bsc.removeA_Ball( tsb );
                    }
                    return;
                }
                
            }
        }
    }


    public void registBallStateChangedListener(ballStateChanged bsc )
    {
        ballStateChangedListener.add( bsc ) ;
        //现有的朋友发送给listener
        for( timeSpaceBall tsb : tsbList )
        {
            bsc.addA_Ball( tsb );
        }
    }

    public void ungistBallStateChangedListener( ballStateChanged r )
    {
        ballStateChangedListener.remove(r);
    }

    public void updateCenterOfBalls(coordinate center)
    {
        if(( centerOfBalls == null ) || ( centerOfBalls.DistanceGreateThan( center, CENTER_OF_BALLS_UPDATE_DISTANCE ) ))
        {
            centerOfBalls = center;

            for ( ballStateChanged bsc : ballStateChangedListener )
            {
                bsc.centerChanged(center);
            }
        }

    }


    public void removeAllBalls()
    {
        tsbList.clear();
        for ( ballStateChanged bsc : ballStateChangedListener )
        {
            bsc.removeAll();
        }
    }
    static public void stopRequireTimeSpaceBallPosition()
    {
        stop = true;
    }
    static public void periodRequiredStart()
    {
        stop = false;

        if (Looper.myLooper()== null)
        {
            Looper.prepare();
        }

        handler.postDelayed(myRunnable, requiredPeriod);
    }
    static private Runnable myRunnable = new Runnable() {
        public void run() {
            if( stop )
            {
                return;
                //handler.getLooper().quit();

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
    //球的数目或者位置变化
    public interface ballStateChanged
    {
        public void addA_Ball( timeSpaceBall add );
        public void removeA_Ball( timeSpaceBall add );
        public void BallPositionMove( timeSpaceBall add );
        public void removeAll();
        public void centerChanged (coordinate newCenter );
    }

}
