package com.zdn.basicStruct;

import java.util.List;

/**
 * Created by wanghp1 on 2015/5/8.
 */
public class getBallAllRspEvent {

    List<timeSpaceBallDetail> ballList;


    public getBallAllRspEvent( List<timeSpaceBallDetail> ballList )
    {
        this.ballList = ballList;
    }

    public void setBallList( List<timeSpaceBallDetail> ballList ) {  this.ballList = ballList;}

    public List<timeSpaceBallDetail> getBallList() { return ballList ; }

}
