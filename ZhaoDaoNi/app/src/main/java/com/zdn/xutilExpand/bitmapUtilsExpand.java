package com.zdn.xutilExpand;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;


import java.io.File;

/**
 * Created by wanghp1 on 2015/6/24.
 */
public class bitmapUtilsExpand extends BitmapUtils {
    public bitmapUtilsExpand(Context context) {
        super(context);
    }



    public void baiduOverlayDisplay( View container, String uri ,BitmapLoadCallBack cb ) {


        super.display( container , uri , null , cb );


    }

}
