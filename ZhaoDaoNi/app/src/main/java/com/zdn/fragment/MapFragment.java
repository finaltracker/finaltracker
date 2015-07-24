package com.zdn.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.zdn.activity.chatActivity;
import com.zdn.adapter.recentChatAdapter;
import com.zdn.basicStruct.coordinate;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.basicStruct.friendTeamData;
import com.zdn.basicStruct.friendTeamDataManager;
import com.zdn.com.headerCtrl;
import com.zdn.data.dataManager;
import com.zdn.internet.InternetComponent;
import com.zdn.logic.MainControl;
import com.zdn.util.OSUtils;
import com.zdn.xutilExpand.bitmapUtilsExpand;

import java.util.HashMap;
import java.util.Map;

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
    friendTeamData  recentChatTeamData = null;
    friendTeamDataManager.friendsMemberChange fmc = null;
    friendMemberData.gpsChange gpsc = null;

    private Map< String,Overlay> overlayMap = new HashMap();

    public MapFragment()
    {
        super( null , mainActivityFragmentBase.MAP_FRAGMENT );
    }
    public MapFragment( headerCtrl.menuStateChange msc )
    {
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

        final BaiduMap mBaidumap = mMapView.getMap();
        mBaidumap.setTrafficEnabled( false );

//设定中心点坐标

        LatLng cenpt = new LatLng(31.10,121.006983);
        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
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

            }


            @Override
            public void removeA_Friend(friendMemberData fmd) {

                Overlay ov = overlayMap.remove(fmd.basic.getPhoneNumber() );
                fmd.unRegistgpsChangeListener( gpsc );
                if(ov!=null)
                {
                    ov.remove();
                }
            }
        };
        dataManager.getFrilendList().registFriendMemberChangeListener( fmc );
        super.onCreateView(inflater, container, savedInstanceState );
        return rootView;
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
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        overlayMap.clear();
        dataManager.getFrilendList().unRegistFriendMemberChangeListener(fmc);
        mMapView.onDestroy();
        mMapView = null;
    }


    /* 右侧好友列表的处理*/
    private void expandRecentChatView()
    {

        RelativeLayout.LayoutParams paramTest = (RelativeLayout.LayoutParams) recentChatFriend.getLayoutParams();
        paramTest.width = (int)(OSUtils.getScreenWidth()*2/3);
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
        b.putInt("memberPosition", childPosition );

        intent.putExtras(b);

        startActivity( intent );
    }


    class LearnGestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent ev) {
            Log.d("onSingleTapUp", ev.toString());
            return true;
        }
        @Override
        public void onShowPress(MotionEvent ev) {
            Log.d("onShowPress",ev.toString());
        }
        @Override
        public void onLongPress(MotionEvent ev) {
            Log.d("onLongPress",ev.toString());
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d("onScroll",e1.toString());
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

        Overlay ov = overlayMap.get( localFmd.basic.getPhoneNumber() );

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
                Log.d(this.getClass().getSimpleName(), "overlayMap.put error");
            }
            Bundle mBundle = new Bundle();
            mBundle.putString("TeamName", localFmd.basic.getTeamName());
            mBundle.putString("PhoneNumber", localFmd.basic.getPhoneNumber());
            mOverLay.setExtraInfo(mBundle);


            overlayMap.put(localFmd.basic.getPhoneNumber(), mOverLay);


        }
    }

        @Override
        public void onLoadFailed(View container, String uri, Drawable drawable) {

    }
    };
}
