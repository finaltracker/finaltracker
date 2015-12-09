package com.zdn.basicStruct;

import java.util.List;

/**
 * Created by wanghp1 on 2015/5/8.
 */
public class areaScanRspEvent {

    List<robot> robotsList;


    public areaScanRspEvent(List<robot> robotsList)
    {
        this.robotsList = robotsList;
    }

    public void setRobotList( List<robot> robotsList ) {  this.robotsList = robotsList;;}

    public List<robot> getRobotList() { return robotsList ; }

}
