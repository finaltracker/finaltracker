package com.zdn.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.zdn.R;

public class mainActivityFragmentBase extends Fragment {
    int fragmentIndex = - 1;
    static final public int MAP_FRAGMENT   =   0;
    static final public int PERSION_INFORMATION_FRAGMENT   =   1;

    public void setFragmentIndex( int fragmentIndex )
    {
        this.fragmentIndex = fragmentIndex;
    }

    public int getFragmentIndex()
    {
        return fragmentIndex;
    }
}
