package com.zdn.basicStruct;

/**
 * Created by wanghp1 on 2015/6/19.
 */
public class coordinate {
    private float lng;   // 经度
    private float lat;   // 纬度


    public coordinate()
    {
        lng = 0;
        lat = 0;
    }
    public coordinate( float lng  , float lat )
    {
        this.lng = lng;
        this.lat = lat;
    }

    public void setCoordinate (  coordinate coor )
    {
        this.lng = coor.lng;
        this.lat = coor.lat;
    }

    public float getLongitude()
    {
        return  lng;
    }

    public float getLatitude()
    {
        return lat;
    }
}
