package com.zdn.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class mainActivityFragmentBase extends Fragment {
    int fragmentIndex = - 1;
    static final public int MAP_FRAGMENT   =   0;
    static final public int PERSION_INFORMATION_FRAGMENT   =   1;

    private View rootView = null;
    private LinearLayout zdnHeaderLayout = null;
    private ImageView navigationButton = null;
    private TextView headerTitle = null;

    private menuStateChange msc = null;

    public mainActivityFragmentBase()
    {

    }

    public mainActivityFragmentBase( menuStateChange msc , int fragmentIndex )
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
        navigationButton = (ImageView)rootView.findViewById(R.id.navigationButton);
        headerTitle = ( TextView )rootView.findViewById(R.id.headerTitle);

        navigationButton.setOnClickListener( new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                if( msc != null )
                {
                    msc.onMenuClick( R.id.navigationButton );
                }
            }
        });

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msc.menuFragmentClick();
            }
        });
    }

    public void  setBackGroundColor( int color )
    {
        if( zdnHeaderLayout != null )
        {
            zdnHeaderLayout.setBackgroundColor( color );
        }
    }

    public void setTitle( String title )
    {
        if( headerTitle != null )
        {
            headerTitle.setText(title);
        }
    }

    public void setNavigationImage( int srcId )
    {
        if( navigationButton != null )
        {
            navigationButton.setBackgroundResource( srcId);
        }


    }

    public interface menuStateChange
    {
        public void onMenuClick(  int menuId );

        public void menuFragmentClick();
    }

}
