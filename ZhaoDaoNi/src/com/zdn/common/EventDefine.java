package com.zdn.common;

public class EventDefine {
	public static final int IS_ACCOUNT_QUEUE_REQ	= 1;
	public static final int IS_ACCOUNT_QUEUE_RSP	= 2;
	public static final int IS_ACCOUNT_REQ			= 3;
	public static final int IS_ACCOUNT_RSP			= 4;
	
	
	
	/* UI <-> CONTROL */
	public static final int UI_CTRL_EVENT_BEGIN			= 100;
	public static final int UI_TO_CTRL_ACCOUNT_REQUEST	= UI_CTRL_EVENT_BEGIN+1;
	public static final int ADD_A_FRIEND	= UI_TO_CTRL_ACCOUNT_REQUEST+1;
	public static final int ADD_A_FRIEND_RSP	= ADD_A_FRIEND+1;
	public static final int ADD_A_FRIEND_ANSWER	= ADD_A_FRIEND_RSP+1;
	public static final int ADD_A_FRIEND_CONFIRM_RSP	= ADD_A_FRIEND_ANSWER+1;
	
	
	/*jpush*/
	
	public static final int JPUSH_SERVER_COMMAND			= 200;
	
	
	
	
	
	
	
	
	
	
	
	public static final int IS_REQIST_RSP_NO_REGIST			= 503;
	public static final int IS_REQIST_RSP_HAS_REGIST		= 0;
}
