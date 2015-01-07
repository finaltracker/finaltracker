package com.zdn.event;

public class EventDefine {
	public static final int IS_ACCOUNT_QUEUE_REQ	= 1;
	public static final int IS_ACCOUNT_QUEUE_RSP	= IS_ACCOUNT_QUEUE_REQ+1;
	public static final int IS_ACCOUNT_REQ			= IS_ACCOUNT_QUEUE_RSP+1;
	public static final int IS_ACCOUNT_RSP			= IS_ACCOUNT_REQ+1;
	public static final int GET_FRIEND_LIST_REQ		= IS_ACCOUNT_RSP+1;
	public static final int GET_FRIEND_LIST_RSP		= GET_FRIEND_LIST_REQ+1;
	public static final int ADD_A_FRIEND_ANSWER_REQ	= GET_FRIEND_LIST_RSP+1;
	public static final int ADD_A_FRIEND_ANSWER_RSP	= ADD_A_FRIEND_ANSWER_REQ+1;
	public static final int ADD_A_FRIEND_REQ		= ADD_A_FRIEND_ANSWER_RSP+1;
	public static final int ADD_A_FRIEND_RSP		= ADD_A_FRIEND_REQ+1;
	
	/*jpush*/
	
	public static final int JPUSH_SERVER_COMMAND			= 200;
	
	
	
	
	
	
	
	
	
	
	
	public static final int IS_REQIST_RSP_NO_REGIST			= 503;
	public static final int IS_REQIST_RSP_HAS_REGIST		= 0;
}
