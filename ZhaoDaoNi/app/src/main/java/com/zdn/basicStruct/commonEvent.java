package com.zdn.basicStruct;

/**
 * Created by wanghp1 on 2015/5/8.
 */
public class commonEvent {

    public static final int EVENT_TYPE_UNKNOW                = 0;
    public static final int EVENT_TYPE_MY_AVATAR_UPDATE    = EVENT_TYPE_UNKNOW+1;
    public static final int UPDATE_FRIEND_LIST_VIEW_FROM_REMOT          = EVENT_TYPE_MY_AVATAR_UPDATE +1;

    private int type;


    public commonEvent(  int type )
    {
        this.type = type;
    }

    public int  getCommonType(  ) { return type; }
    public void setCommonType(  int type ) { this.type = type; }
}
