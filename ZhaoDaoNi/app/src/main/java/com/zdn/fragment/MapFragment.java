package com.zdn.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.zdn.R;
import com.zdn.activity.MainActivity;
import com.zdn.activity.StartTimeBallDialog;
import com.zdn.activity.chatActivity;
import com.zdn.adapter.ContainFragmentAdapter;
import com.zdn.adapter.recentChatAdapter;
import com.zdn.basicStruct.areaScanRspEvent;
import com.zdn.basicStruct.coordinate;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.basicStruct.friendTeamDataManager;
import com.zdn.basicStruct.getBallAllRspEvent;
import com.zdn.basicStruct.robot;
import com.zdn.basicStruct.timeSpaceBallBase;
import com.zdn.basicStruct.timeSpaceBallDetail;
import com.zdn.basicStruct.timeSpaceBallManager;
import com.zdn.com.headerCtrl;
import com.zdn.data.dataManager;
import com.zdn.internet.InternetComponent;
import com.zdn.jeo.friendLocationManage;
import com.zdn.logic.MainControl;
import com.zdn.util.OSUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdn.ext.SatelliteMenu;
import com.zdn.ext.SatelliteMenuItem;
import com.zdn.view.AnimationView;

import de.greenrobot.event.EventBus;


public class MapFragment extends mainActivityFragmentBase implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mMapView = null;
    ListView recentChatFriend = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int recentChatFriendViewWidth = 0;

    private recentChatAdapter m_recentChatAdapt;
    MainActivity.MyOnTouchListener myOnTouchListener;
    private GestureDetector mGestureDetector;
    friendTeamDataManager.friendsMemberChange fmc = null;
    friendMemberData.gpsChange gpsc = null;

    private WindowManager.LayoutParams mWindowLayoutParams;
    private WindowManager mWindowManager;
    private SatelliteMenu m_SatelliteMenu = null;
    private AnimationView bombExplodeAnimation = null;
    private final int sateliteMenuOverAnimationPeriod = 500;
    static private BaiduMap mBaidumap = null;
    /*              <phone  overlay> */
    private Map< String,Overlay> friendOverlayMap = new HashMap();
    /*              <Id    overlay> */
    private Map<String,Overlay> timeSpaceBallMap = new HashMap<String,Overlay>();

    private Map<String,Overlay> robotMap = new HashMap<>();

    private Point lastLongPressPosition = new Point( 0 , 0 );

    private timeSpaceBallManager.ballStateChanged ballStateChangedListener = null;



    private double initLat = 31.10;
    private double initLng  = 121.006983;
    private static double centerLat = 0;
    private static double centerLng = 0;

    private static List<BaiduMap.OnMapStatusChangeListener> msclList = new ArrayList<BaiduMap.OnMapStatusChangeListener>();
    private static List<BaiduMap.OnMapLoadedCallback> mlcbList = new ArrayList<BaiduMap.OnMapLoadedCallback>();

    public MapFragment()
    {
        super( null , mainActivityFragmentBase.MAP_FRAGMENT );
    }
    public MapFragment( headerCtrl.menuStateChange msc )
    {
        //SatelliteMenu menu = (SatelliteMenu) findViewById(R.id.menu);

        super(msc, mainActivityFragmentBase.MAP_FRAGMENT);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mGestureDetector = new GestureDetector(this.getActivity(), new LearnGestureListener());

        //setHasOptionsMenu(true);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        ballStateChangedListener = new timeSpaceBallManager.ballStateChanged(){

            @Override
            public void addA_Ball(timeSpaceBallBase add) {

                BitmapDescriptor mBitMap = BitmapDescriptorFactory.fromResource(R.drawable.timespaceball );

//构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position( new LatLng(Double.valueOf(add.getLat()) , Double.valueOf(add.getLng()) ))
                        .icon(mBitMap);
//在地图上添加Marker，并显示

                Overlay ov = timeSpaceBallMap.get( add.getBallId() );

                if( ov != null )
                {
                    ov.remove();
                }

                BaiduMap mBaidumap = mMapView.getMap();

                Overlay mOverLay = null;
                try {
                    mOverLay = mBaidumap.addOverlay(option);
                } catch (Exception e) {
                    Log.d(this.getClass().getSimpleName(), "overlay.put error");
                }

                timeSpaceBallMap.put( add.getBallId()  , mOverLay );

            }

            @Override
            public void removeA_Ball(timeSpaceBallBase add) {
                Overlay ov = timeSpaceBallMap.get( add.getBallId() );

                if( ov != null )
                {
                    ov.remove();
                }
                timeSpaceBallMap.remove(  add.getBallId() );
            }

            @Override
            public void BallPositionMove(timeSpaceBallBase ball) {
                removeA_Ball( ball );
                addA_Ball( ball );
            }

            @Override
            public void removeAll() {
                for ( Overlay ov : timeSpaceBallMap.values() )
                {
                    if( ov != null )
                    {
                        ov.remove();
                    }
                }
                timeSpaceBallMap.clear();

            }

            @Override
            public void centerChanged( coordinate newCenter ) {
            MainControl.getBallLocation( "3", Double.toString( newCenter.getLatitude()),Double.toString( newCenter.getLongitude()), Long.toString(timeSpaceBallManager.ALL_BALL_RANGER));
            }

            @Override
            public void BallBomb(String ballId, float latitude ,float longtitude ) {
                Overlay ov = timeSpaceBallMap.get( ballId );

                if( ov != null )
                {
                    ov.remove();
                }
                timeSpaceBallMap.remove(ballId);


                Point point = mBaidumap.getProjection().toScreenLocation( new LatLng(latitude,longtitude ));

                //animate bomb expore
                bombExplodeAnimation.setVisibility(View.VISIBLE);
                bombExplodeAnimation.reStart();

                ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) bombExplodeAnimation.getLayoutParams();

                lp.leftMargin = (int)(point.x) - bombExplodeAnimation.getWidth()/2;
                lp.bottomMargin = OSUtils.getScreenHeight() - (int)point.y - bombExplodeAnimation.getHeight()/2;

                bombExplodeAnimation.setLayoutParams(lp);
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d( this.getClass().getSimpleName() ,"onCreateView" );
        SDKInitializer.initialize( this.getActivity().getApplicationContext());
        final View rootView =  inflater.inflate(R.layout.fragment_map, container, false);

        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));

        //获取地图控件引用
        mMapView = (MapView) rootView.findViewById(R.id.bmapView);
        View back_button =   rootView.findViewById(R.id.back_button);
        if( back_button != null )
        {
            back_button.setVisibility(View.INVISIBLE );
        }

        mBaidumap = mMapView.getMap();
        mBaidumap.setTrafficEnabled(false);
        dataManager.getAllBallsListManager().registBallStateChangedListener(ballStateChangedListener);


//设定中心点坐标

        LatLng initShowCentre = new LatLng(initLat,initLng);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(initShowCentre)
                .zoom(10)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化


        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaidumap.setMapStatus(mMapStatusUpdate);

        //recent chat view handle
        recentChatFriend = (ListView)rootView.findViewById( R.id.recentChatFriend  );
        recentChatFriend.setOnItemClickListener(this);
        m_recentChatAdapt = new recentChatAdapter( this.getActivity() , dataManager.getFrilendList().getAllRecnetChatList(  ));
        recentChatFriend.setAdapter(m_recentChatAdapt);
        m_recentChatAdapt.notifyDataSetChanged();

        RelativeLayout.LayoutParams paramTest = (RelativeLayout.LayoutParams) recentChatFriend.getLayoutParams();
        recentChatFriendViewWidth = paramTest.width;


        initCommonView(rootView);

        mWindowManager = (WindowManager) this.getActivity()
                .getSystemService(Context.WINDOW_SERVICE);

        //createSatelliteImage( 0 ,2560/4  );

        myOnTouchListener = new MainActivity.MyOnTouchListener() {

            @Override
            public boolean onTouch(MotionEvent ev) {


                MainActivity ma =  (MainActivity)MapFragment.this.getActivity();
                if( ( ma != null ) &&( ma.isMainMapInFront()) ) {
                    if (mGestureDetector.onTouchEvent(ev))
                        return true;
                    else
                        return false;
                }

                return false;

            }

        };

        ((MainActivity) getActivity())
                .registerMyOnTouchListener(myOnTouchListener);

        fmc = new friendTeamDataManager.friendsMemberChange() {

            @Override
            public void addA_Friend(final friendMemberData fmd) {
                //定义Maker坐标


                //添加位置移动对应的回调
                gpsc = new friendMemberData.gpsChange()
                {

                    @Override
                    public void updateGps(friendMemberData ufmd, coordinate gps) {
                        if(ufmd == dataManager.self.selfInfo )
                        {
                            //是自己，发送位置信息到server

                            MainControl.locationUpdate(ufmd.getLatitude(), ufmd.getLongitude());
                            dataManager.getAllBallsListManager().updateCenterOfBalls(gps);
                        }
                        if( MapFragment.this.getActivity() != null )
                        {
                            BitmapUtils mbug = new BitmapUtils(MapFragment.this.getActivity());
                            MapFragmentBitmapLoadCallBack overLayBitmapLoadCallBack = new MapFragmentBitmapLoadCallBack( ufmd);

                            mbug.display(MapFragment.this.rootView, InternetComponent.WEBSITE_ADDRESS_BASE_NO_SEPARATOR + ufmd.basic.getPictureAddress(), null, overLayBitmapLoadCallBack);
                        }
                    }
                };
                fmd.registgpsChangeListener(gpsc);

                friendLocationManage.periodRequiredStart();


            }


            @Override
            public void removeA_Friend(friendMemberData fmd) {

                Overlay ov = friendOverlayMap.remove(fmd.basic.getPhoneNumber() );
                fmd.unRegistgpsChangeListener( gpsc );
                if(ov!=null)
                {
                    ov.remove();
                }
            }
        };
        dataManager.getFrilendList().registFriendMemberChangeListener(fmc);


        m_SatelliteMenu  = (SatelliteMenu) rootView.findViewById(R.id.popMenu);
        m_SatelliteMenu.setMainImageHaveAnimation(false);

        bombExplodeAnimation = ( AnimationView ) rootView.findViewById((R.id.bomb_explode ));

        List<SatelliteMenuItem> items = new ArrayList<SatelliteMenuItem>();
        items.add(new SatelliteMenuItem(1, R.drawable.time_space_ball));
        items.add(new SatelliteMenuItem(2, R.drawable.time_space_ball));
        items.add(new SatelliteMenuItem(3, R.drawable.time_space_ball));
        m_SatelliteMenu.addItems(items);

        m_SatelliteMenu.setOnItemClickedListener(new SatelliteMenu.SateliteClickedListener() {

            public void eventOccured(int id) {
                Log.i("sat", "Clicked on " + id);

                if (id == 3) // 退出
                {
                    m_SatelliteMenu.setVisibility(View.INVISIBLE);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            m_SatelliteMenu.setVisibility(View.INVISIBLE);

                            Intent intent = new Intent();
                            intent.setClass(MapFragment.this.getActivity(), StartTimeBallDialog.class);
                            Bundle bundle = new Bundle();
                            LatLng screenPoint = mBaidumap.getProjection().fromScreenLocation(lastLongPressPosition);

                            bundle.putString("TargetLat", Double.toString(screenPoint.latitude));
                            bundle.putString("TargetLng", Double.toString(screenPoint.longitude));
                            intent.putExtras(bundle);
                            MapFragment.this.startActivityForResult(intent, 0);//这里采用startActivityForResult来做跳转，此处的0为一个依据，可以写其他的值，但一定要>=0
                        }
                    }, sateliteMenuOverAnimationPeriod);
                }
            }
        });

        mBaidumap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                centerLat = initLat;
                centerLng = initLng;

                for (BaiduMap.OnMapLoadedCallback mlcb : mlcbList
                        ) {
                    mlcb.onMapLoaded();
                }

            }
        });
        mBaidumap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            /**
             * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
             * @param status 地图状态改变开始时的地图状态
             */
            public void onMapStatusChangeStart(MapStatus status) {
                for (BaiduMap.OnMapStatusChangeListener msc : msclList
                        ) {
                    msc.onMapStatusChangeStart(status);
                }
            }

            /**
             * 地图状态变化中
             * @param status 当前地图状态
             */
            public void onMapStatusChange(MapStatus status) {
                for (BaiduMap.OnMapStatusChangeListener msc : msclList
                        ) {
                    msc.onMapStatusChange(status);
                }
            }

            /**
             * 地图状态改变结束
             * @param status 地图状态改变结束后的地图状态
             */
            public void onMapStatusChangeFinish(MapStatus status) {
                LatLng ll = status.target;
                centerLat = ll.latitude;
                centerLng = ll.longitude;
                for (BaiduMap.OnMapStatusChangeListener msc : msclList
                        ) {
                    msc.onMapStatusChangeFinish(status);
                }
                Log.d("map change", "sts ch fs:" + centerLat + "," + centerLng + "");
            }
        });
        leftBallListViewonCreate(rootView);

        // Do work to refresh the list here.

        super.onCreateView(inflater, container, savedInstanceState);


        return rootView;
    }


    private void updateRobot( areaScanRspEvent robotList )
    {
        //清除所有的robot
        for ( Overlay ov : robotMap.values()) {
            {
                if (ov != null) {
                    ov.remove();
                }
            }
        }
        robotMap.clear();


        for (int i = 0; i < robotList.getRobotList().size(); i++) {

            BitmapUtils mbug = new BitmapUtils(MapFragment.this.getActivity());
            robotBitmapLoadCallBack rblcb = new robotBitmapLoadCallBack(robotList.getRobotList().get(i));

            mbug.display(MapFragment.this.rootView, InternetComponent.WEBSITE_ADDRESS_BASE_NO_SEPARATOR + robotList.getRobotList().get(i).pictureUrl, null, rblcb);
        }
    }

    public void onEvent(Object event) {

        if( event instanceof areaScanRspEvent)
        {
            updateRobot( (areaScanRspEvent) (event));
        }


    }
    public void leftBallListViewonCreate( View rootView )
    {


    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(this.getClass().getSimpleName(), "onDestroyView");
        friendLocationManage.stopRequireGeo();
        timeSpaceBallManager.periodRequireStop();

        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        friendOverlayMap.clear();
        timeSpaceBallMap.clear();
        dataManager.getFrilendList().unRegistFriendMemberChangeListener(fmc);
        dataManager.getAllBallsListManager().ungistBallStateChangedListener(ballStateChangedListener);

        mMapView.onDestroy();
        mMapView = null;
    }


    /* 右侧好友列表的处理*/
    private void expandRecentChatView()
    {

        RelativeLayout.LayoutParams paramTest = (RelativeLayout.LayoutParams) recentChatFriend.getLayoutParams();
        paramTest.width = (int)(OSUtils.getScreenWidth() * 2 /3);
        recentChatFriend.setLayoutParams(paramTest);
    }

    private void shrinkRecentChatView()
    {
        RelativeLayout.LayoutParams paramTest = (RelativeLayout.LayoutParams) recentChatFriend.getLayoutParams();
        paramTest.width = recentChatFriendViewWidth;
        recentChatFriend.setLayoutParams(paramTest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this.getActivity(), chatActivity.class);

        // 创建Bundle对象用来存放数据,Bundle对象可以理解为数据的载体

        Bundle b = new Bundle();
        int groupPosition = (int) view.getTag( R.id.INDEX_IN_ALL_FRIEND_LIST );
        int childPosition = (int) view.getTag( R.id.INDEX_IN_ONE_FRIEND_TEAM );
        b.putString("targetTo", dataManager.getFrilendList().getMemberData(groupPosition, childPosition).basic.getPhoneNumber() );
        b.putInt("teamPosition", groupPosition );
        b.putInt("memberPosition", childPosition);

        intent.putExtras(b);

        startActivity(intent);
    }


    class LearnGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent ev) {
            Log.d("onSingleTapUp", ev.toString());

            return false;
        }

        @Override
        public void onShowPress(MotionEvent ev) {
            Log.d("onShowPress", ev.toString());
        }
        @Override
        public void onLongPress(MotionEvent ev) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) m_SatelliteMenu.getLayoutParams();
            lastLongPressPosition.x = (int)(ev.getRawX()) ;
            lastLongPressPosition.y = (int)ev.getRawY() ;
            lp.leftMargin = (int)(ev.getRawX()) - m_SatelliteMenu.getMainViewWidth()/2;
            lp.bottomMargin = OSUtils.getScreenHeight() - (int)ev.getRawY() - m_SatelliteMenu.getMainViewHight()/2;


            m_SatelliteMenu.setLayoutParams(lp);
            m_SatelliteMenu.setVisibility(View.VISIBLE);
            m_SatelliteMenu.click();


            Log.d("onLongPress",ev.toString());
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("onScroll", e1.toString());
            return false;
        }
        @Override
        public boolean onDown(MotionEvent ev) {
            Log.d("onDownd",ev.toString());
            return false;
        }
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d("d",e1.toString());
            Log.d("e2",e2.toString());
            //TODO....
            RelativeLayout.LayoutParams paramTest = (RelativeLayout.LayoutParams) recentChatFriend.getLayoutParams();

           if(( e1.getRawX() > ( OSUtils.getScreenWidth() - paramTest.width )) && (e2.getRawX() < ( OSUtils.getScreenWidth() - paramTest.width )  ))
           {
               expandRecentChatView();
           }
           else if( ( e1.getRawX() < e2.getRawX()  ) &&  (e2.getRawX() > ( OSUtils.getScreenWidth() - paramTest.width )))
            {
                shrinkRecentChatView();
            }

            return false;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        mGestureDetector.onTouchEvent(event);


        return super.onTouch(v,event);
    }

    /**
     * 浠庣晫闈笂闈㈢Щ鍔ㄦ嫋鍔ㄩ暅鍍�
     */
    private void removeDragImage() {
        if (m_SatelliteMenu != null) {
            mWindowManager.removeView(m_SatelliteMenu);
            m_SatelliteMenu = null;
        }
    }



    private class MapFragmentBitmapLoadCallBack extends BitmapLoadCallBack
    {
       // LatLng          mLatLng = null;

       friendMemberData localFmd = null;


        public MapFragmentBitmapLoadCallBack( friendMemberData fmd  )
        {
           // mBaidumap  = baidumap ;
            localFmd = fmd;
        }
        @Override
        public void onLoadCompleted(View container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {

            BitmapDescriptor mBitMap = BitmapDescriptorFactory.fromBitmap(bitmap);

//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position( new LatLng( localFmd.getLatitude() , localFmd.getLongitude() ))
                .icon(mBitMap);
//在地图上添加Marker，并显示

        Overlay ov = friendOverlayMap.get( localFmd.basic.getPhoneNumber() );

        if( ov != null )
        {
            ov.remove();

        }
        MapFragment mf = MapFragment.this;
        if( ( mf != null )&& (mf.isVisible()) ) {

            Overlay mOverLay = null;

            BaiduMap mBaidumap = mMapView.getMap();

            try {
                mOverLay = mBaidumap.addOverlay(option);
            } catch (Exception e) {
                Log.d(this.getClass().getSimpleName(), "overlay.put error");
            }
            Bundle mBundle = new Bundle();
            mBundle.putString("TeamName", localFmd.basic.getTeamName());
            mBundle.putString("PhoneNumber", localFmd.basic.getPhoneNumber());
            mOverLay.setExtraInfo(mBundle);


            friendOverlayMap.put(localFmd.basic.getPhoneNumber(), mOverLay);


        }
    }

        @Override
        public void onLoadFailed(View container, String uri, Drawable drawable) {

    }
    };


    private class robotBitmapLoadCallBack extends BitmapLoadCallBack
    {
        robot r = null;

        public robotBitmapLoadCallBack( robot  r  )
        {

            this.r = r;
        }
        @Override
        public void onLoadCompleted(View container, String uri, Bitmap bitmap, BitmapDisplayConfig config, BitmapLoadFrom from) {

            BitmapDescriptor mBitMap = BitmapDescriptorFactory.fromBitmap(bitmap);

//构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(new LatLng( r.address.getLatitude(), r.address.getLongitude()))
                    .icon(mBitMap);
//在地图上添加Marker，并显示

            Overlay ov = robotMap.get( r.name );

            if( ov != null )
            {
                ov.remove();

            }
            MapFragment mf = MapFragment.this;
            if( ( mf != null )&& (mf.isVisible()) ) {

                Overlay mOverLay = null;

                BaiduMap mBaidumap = mMapView.getMap();

                try {
                    mOverLay = mBaidumap.addOverlay(option);
                } catch (Exception e) {
                    Log.d(this.getClass().getSimpleName(), "overlay.put error");
                }
                Bundle mBundle = new Bundle();
                mBundle.putString("name", r.name);
                mBundle.putString("blood", "100%");
                mOverLay.setExtraInfo(mBundle);


                robotMap.put( r.name, mOverLay);


            }
        }

        @Override
        public void onLoadFailed(View container, String uri, Drawable drawable) {

        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 0: // StartTimeBallDialog return
                if( resultCode == 1 )
                { // make a time ball
                    Bundle targetBundle = data.getExtras();

                     MainControl.startBallGame( 0 , targetBundle.getString("content"),targetBundle.getString("duration"),targetBundle.getString("TargetLat"), targetBundle.getString("TargetLng") );

                }
                else
                {
                    //abort start a time ball
                }
                break;
            default:
                break;
        }
    }
    static public LatLng getScreenCentreLatLng() {
        return new LatLng( centerLat , centerLng );
    }

    static public void registOnMapStatusChangeListener( BaiduMap.OnMapStatusChangeListener mscl )
    {
        msclList.add( mscl );
    }
    static public void unregistOnMapStatusChangeListener( BaiduMap.OnMapStatusChangeListener mscl )
    {
        msclList.remove(mscl);
    }

    static public void registOnMapLoadedCallback( BaiduMap.OnMapLoadedCallback mlcb )
    {
        mlcbList.add( mlcb );
    }
    static public void unregisOnMapLoadedCallback( BaiduMap.OnMapLoadedCallback mlcb )
    {
        mlcbList.remove(mlcb);
    }

}
