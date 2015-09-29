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

    public boolean DistanceGreateThan( coordinate coor , long distance )
    {
        if( ( distance - (long)(Distance( coor.getLongitude() , coor.getLatitude() , this.getLongitude() ,this.getLatitude() )) ) > 0 )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param long1
     *            第一点经度
     * @param lat1
     *            第一点纬度
     * @param long2
     *            第二点经度
     * @param lat2
     *            第二点纬度
     * @return 返回距离 单位：米
     */
    public static double Distance(double long1, double lat1, double long2,double lat2)
    {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }
}
