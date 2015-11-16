package com.zdn.basicStruct;

/**
 * Created by wanghp1 on 2015/9/28.
 */
public class timeSpaceBallDetail extends timeSpaceBallBase
{
    private String startTime;
    private String endTime;
    private String ballStatus;
    private String catcher;



    public timeSpaceBallDetail(String mobile, String ballId, String lat, String lng, String type, String content, String startTime, String endTime, String ballStatus  , String catcher )
    {
        super( mobile,ballId,lat,lng,type,content );
        this.startTime = startTime;
        this.endTime = endTime;
        this.ballStatus = ballStatus;
        this.catcher = catcher;
    }
    public void setStartTime( String startTime ) { this.startTime = startTime ; }
    public String getStartTime() { return startTime; }

    public void setEndTime( String endTime ) { this.endTime = endTime ; }
    public String getEndTime() { return endTime; }

    public void setBallStatus( String ball_status ) { this.ballStatus = ball_status ; }
    public String getBallStatus() { return ballStatus; }
}
