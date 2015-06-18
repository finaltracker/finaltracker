package com.zdn.fragment;

import android.app.Activity;
import android.content.Intent;
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
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.zdn.R;
import com.zdn.activity.MainActivity;
import com.zdn.activity.chatActivity;
import com.zdn.adapter.recentChatAdapter;
import com.zdn.basicStruct.friendTeamData;
import com.zdn.com.headerCtrl;
import com.zdn.data.dataManager;
import com.zdn.util.OSUtils;

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

        SDKInitializer.initialize( this.getActivity().getApplicationContext());
        View rootView =  inflater.inflate(R.layout.fragment_map, container, false);

        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ));

        //获取地图控件引用
        mMapView = (MapView) rootView.findViewById(R.id.bmapView);
        View back_button =   rootView.findViewById(R.id.back_button);
        if( back_button != null )
        {
            back_button.setVisibility(View.INVISIBLE );
        }

        BaiduMap mBaidumap = mMapView.getMap();
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
        m_recentChatAdapt = new recentChatAdapter( this.getActivity() , dataManager.getFrilendList().constructRecentChatTeamAccordingTime( 100 ));
        recentChatFriend.setAdapter( m_recentChatAdapt );
        m_recentChatAdapt.notifyDataSetChanged();

        RelativeLayout.LayoutParams paramTest = (RelativeLayout.LayoutParams) recentChatFriend.getLayoutParams();
        recentChatFriendViewWidth = paramTest.width;


        initCommonView(rootView);

        myOnTouchListener = new MainActivity.MyOnTouchListener() {

            @Override
            public boolean onTouch(MotionEvent ev) {
                if( MapFragment.this.isVisible() ) {
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

        super.onCreateView( inflater , container , savedInstanceState );
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
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

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

}
