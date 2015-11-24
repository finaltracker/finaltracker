package com.zdn.basicStruct;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.zdn.fragment.MapFragment;
import com.zdn.logic.MainControl;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by wanghp1 on 2015/9/28.
 */
public class timeSpaceBallManager {
    static private int requiredPeriod = 1000 * 60 ; // 1 MINUTE

    private List<timeSpaceBallBase> tsbList = new ArrayList<timeSpaceBallBase>();
    static private boolean stop = false;
    public coordinate centerOfBalls = null;
    private List<ballStateChanged> ballStateChangedListener = new ArrayList<ballStateChanged>();
    static public final long  ALL_BALL_RANGER = 100000; //100 KM
    private final long CENTER_OF_BALLS_UPDATE_DISTANCE = 100 ; // 移动超过 CENTER_OF_BALLS_UPDATE_DISTANCE 则需要更新附近的球
    private BaiduMap.OnMapStatusChangeListener mapStatusChangeListener =null;
    private BaiduMap.OnMapLoadedCallback mapLoaderCallBack = null;
    static Handler handler =  new Handler() {
        public void handleMessage(Message msg) {
            if( msg.what == 1 )
            {
                MainControl.getBallLocationDefault("3");
            }
        }};
      public timeSpaceBallManager()
    {
        mapLoaderCallBack = new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                periodRequiredStart();
            }
        };
        MapFragment.registOnMapLoadedCallback(mapLoaderCallBack );

        mapStatusChangeListener = new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                periodRequireStop();


            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                periodRequiredStart();
            }
        };

        MapFragment.registOnMapStatusChangeListener(mapStatusChangeListener);
    }

    public List<timeSpaceBallBase> getAllTimeSpaceBallList()
    {
        return this.tsbList;
    }

    public void addA_TimeSpaceBall( timeSpaceBallBase add )
    {
        tsbList.add( add );
        for ( ballStateChanged bsc : ballStateChangedListener )
        {
            bsc.addA_Ball( add );
        }
    }

    public void ballPositionMove( String BallId , String lat , String lng )
    {
        for ( timeSpaceBallBase tsb : tsbList
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
            for ( timeSpaceBallBase tsb: tsbList
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
    public void BallBomb(String ballId, float latitude ,float longtitude )
    {
        //removeA_TimeSpaceBall( ballId );
        for ( ballStateChanged bsc : ballStateChangedListener )
        {
            bsc.BallBomb(ballId,latitude,longtitude );
        }


    }

    public void registBallStateChangedListener(ballStateChanged bsc )
    {
        ballStateChangedListener.add( bsc ) ;
        //现有的朋友发送给listener
        for( timeSpaceBallBase tsb : tsbList )
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
    static public void periodRequireStop()
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
        MainControl.getBallLocationDefault("3");
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
        public void addA_Ball( timeSpaceBallBase add );
        public void removeA_Ball( timeSpaceBallBase add );
        public void BallPositionMove( timeSpaceBallBase add );
        public void removeAll();
        public void centerChanged (coordinate newCenter );
        public void BallBomb( String ballId ,float longtitude , float latitude );
    }

}
