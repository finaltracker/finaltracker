package com.zdn.basicStruct;

/**
 * Created by wanghp1 on 2015/6/19.
 */
public class coordinate {
    private double lng;   // 经度
    private double lat;   // 纬度


    public coordinate()
    {
        lng = 0;
        lat = 0;
    }
    public coordinate( double lng  , double lat )
    {
        this.lng = lng;
        this.lat = lat;
    }

    public void setCoordinate (  coordinate coor )
    {
        this.lng = coor.lng;
        this.lat = coor.lat;
    }

    public double getLongitude()
    {
        return  lng;
    }

    public double getLatitude()
    {
        return lat;
    }
}
