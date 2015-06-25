package com.zdn.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.zdn.R;
import com.zdn.basicStruct.networkStatusEvent;
import com.zdn.com.headerCtrl;

import de.greenrobot.event.EventBus;

public class mainActivityFragmentBase extends Fragment  implements View.OnTouchListener {
    int fragmentIndex = - 1;
    static final public int MAP_FRAGMENT   =   0;
    static final public int PERSION_INFORMATION_FRAGMENT   =   MAP_FRAGMENT + 1;
    static final public int FRIEND_LIST_FRAGMENT   =   PERSION_INFORMATION_FRAGMENT + 1;

    protected View rootView = null;
    private LinearLayout zdnHeaderLayout = null;
    private ImageView navigationButton = null;
    private TextView headerTitle = null;
    private ImageView ShowFriendListButton = null;
    private headerCtrl.menuStateChange msc = null;
    headerCtrl hc = null;

    public mainActivityFragmentBase()
    {

    }

    public mainActivityFragmentBase( headerCtrl.menuStateChange msc , int fragmentIndex )
    {
        this.msc = msc;
        this.fragmentIndex = fragmentIndex;
    }
    public void setFragmentIndex( int fragmentIndex )
    {
        this.fragmentIndex = fragmentIndex;
    }

    public int getFragmentIndex()
    {
        return fragmentIndex;
    }


    protected  void initCommonView(View rootView)
    {
        this.rootView = rootView;
        zdnHeaderLayout = (LinearLayout) rootView.findViewById(R.id.header);
        hc = new headerCtrl( zdnHeaderLayout , msc );


    }

    public void  setBackGroundColor( int color )
    {
        hc.setBackGroundColor(color);
    }

    public void setTitle( String title )
    {
        hc.setTitle( title );
    }

    public void setNavigationImage( int srcId )
    {
        hc.setNavigationImage(srcId);
    }



    public void onEvent(Object event)
    {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return null;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        view.setOnTouchListener(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return true;
    }
}
