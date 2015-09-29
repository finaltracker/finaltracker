package com.zdn.basicStruct;

/**
 * Created by wanghp1 on 2015/9/28.
 */
public class timeSpaceBall
{
    private String mobile; /* the mobile that register */
    private String ballId;
    private String lat;
    private String lng;
    private String type;
    private String content;

    public timeSpaceBall( String type )
    {
        this.type = type;
    }

    public timeSpaceBall(  String mobile ,String ballId ,String lat ,String lng ,String type ,String content )
    {
        this.mobile = mobile;
        this.ballId = ballId;
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.content = content;
    }

    public void setMobile( String mobile ) { this.mobile = mobile ; }
    public String getMobile() { return mobile ; }


    public void setBallId( String ballId ) { this.ballId = ballId ; }
    public String getBallId() { return ballId ; }


    public void setLat( String lat ) { this.lat = lat ; }
    public String getLat() { return lat ; }


    public void setLng( String lng ) { this.lng = lng ; }
    public String getLng() { return lng ; }


    public void setType( String type ) { this.type = type ; }
    public String getType() { return type ; }

    public void setContent( String content ) { this.content = content ; }
    public String getContent() { return content ; }


}
