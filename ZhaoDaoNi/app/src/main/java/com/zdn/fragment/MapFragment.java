package com.zdn.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.zdn.R;
import com.zdn.adapter.recentChatAdapter;
import com.zdn.com.headerCtrl;
import com.zdn.data.dataManager;

public class MapFragment extends mainActivityFragmentBase {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mMapView = null;
    ListView recentChatFriend = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private recentChatAdapter m_recentChatAdapt;



    public MapFragment()
    {
        super( null , mainActivityFragmentBase.MAP_FRAGMENT );
    }
    public MapFragment( headerCtrl.menuStateChange msc )
    {
        super(msc , mainActivityFragmentBase.MAP_FRAGMENT );

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        m_recentChatAdapt = new recentChatAdapter( this.getActivity() , dataManager.getFrilendList().getFriendTeamData("我的好友") );
        recentChatFriend.setAdapter( m_recentChatAdapt );
        m_recentChatAdapt.notifyDataSetChanged();

        initCommonView(rootView);

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
}
