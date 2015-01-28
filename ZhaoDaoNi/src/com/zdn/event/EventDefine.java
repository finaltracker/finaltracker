package com.zdn.event;

public class EventDefine {
	public static final int CHECK_REGIST_REQ	= 1;
	public static final int CHECK_REGIST_RSP	= CHECK_REGIST_REQ+1;
	public static final int REGIST_REQ			= CHECK_REGIST_RSP+1;
	public static final int REGIST_RSP			= REGIST_REQ+1;
	public static final int GET_FRIEND_LIST_REQ		= REGIST_RSP+1;
	public static final int GET_FRIEND_LIST_RSP		= GET_FRIEND_LIST_REQ+1;
	public static final int ADD_A_FRIEND_ANSWER_REQ	= GET_FRIEND_LIST_RSP+1;
	public static final int ADD_A_FRIEND_ANSWER_RSP	= ADD_A_FRIEND_ANSWER_REQ+1;
	public static final int ADD_A_FRIEND_REQ		= ADD_A_FRIEND_ANSWER_RSP+1;
	public static final int ADD_A_FRIEND_RSP		= ADD_A_FRIEND_REQ+1;
	public static final int SEARCH_FRIEND_OR_CIRCLE_REQ		= ADD_A_FRIEND_RSP+1;
	public static final int SEARCH_FRIEND_OR_CIRCLE_RSP		= SEARCH_FRIEND_OR_CIRCLE_REQ+1;
	public static final int UPDATE_FRIEND_INFORMATION_REQ		= SEARCH_FRIEND_OR_CIRCLE_RSP+1;
	public static final int UPDATE_FRIEND_INFORMATION_RSP		= UPDATE_FRIEND_INFORMATION_REQ+1;
	public static final int DELETE_FRIEND_REQ		= UPDATE_FRIEND_INFORMATION_RSP+1;
	public static final int DELETE_FRIEND_RSP		= DELETE_FRIEND_REQ+1;
	
	/*jpush*/
	
	public static final int JPUSH_SERVER_COMMAND			= 200;
	
	
	
	
	
	
	
	
	
	
	
	public static final int IS_REQIST_RSP_NO_REGIST			= 503;
	public static final int IS_REQIST_RSP_HAS_REGIST		= 0;
}
