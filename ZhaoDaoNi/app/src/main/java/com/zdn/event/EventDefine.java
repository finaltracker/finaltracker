package com.zdn.event;

public class EventDefine {
	public static final int COMMAND_NETWORK_STATE	= 1;
	public static final int CHECK_REGIST_REQ = COMMAND_NETWORK_STATE+1;
	public static final int CHECK_REGIST_RSP	= CHECK_REGIST_REQ+1;
	public static final int REGIST_REQ			= CHECK_REGIST_RSP+1;
	public static final int REGIST_RSP			= REGIST_REQ+1;
	public static final int GET_FRIEND_LIST_REQ		= REGIST_RSP+1;
	public static final int GET_FRIEND_LIST_RSP		= GET_FRIEND_LIST_REQ+1;
	public static final int ADD_A_FRIEND_ANSWER_REQ	= GET_FRIEND_LIST_RSP+1;
	public static final int ADD_A_FRIEND_ANSWER_RSP	= ADD_A_FRIEND_ANSWER_REQ+1;
	public static final int ADD_A_FRIEND_REQ		= ADD_A_FRIEND_ANSWER_RSP+1;    //10
	public static final int ADD_A_FRIEND_RSP		= ADD_A_FRIEND_REQ+1;
	public static final int SEARCH_FRIEND_OR_CIRCLE_REQ		= ADD_A_FRIEND_RSP+1;
	public static final int SEARCH_FRIEND_OR_CIRCLE_RSP		= SEARCH_FRIEND_OR_CIRCLE_REQ+1;
	public static final int UPDATE_FRIEND_INFORMATION_REQ		= SEARCH_FRIEND_OR_CIRCLE_RSP+1;
	public static final int UPDATE_FRIEND_INFORMATION_RSP		= UPDATE_FRIEND_INFORMATION_REQ+1;
	public static final int DELETE_FRIEND_REQ		= UPDATE_FRIEND_INFORMATION_RSP+1;
	public static final int DELETE_FRIEND_RSP		= DELETE_FRIEND_REQ+1;
	public static final int SEND_MESSAGE_REQ		= DELETE_FRIEND_RSP+1;
	public static final int SEND_MESSAGE_RSP		= SEND_MESSAGE_REQ+1;
	public static final int GET_MESSAGE_REQ		= SEND_MESSAGE_RSP+1; //20
	public static final int GET_MESSAGE_RSP		= GET_MESSAGE_REQ+1;
	public static final int UPLOAD_FILE_REQ		= GET_MESSAGE_RSP+1;
	public static final int UPLOAD_FILE_RSP		= UPLOAD_FILE_REQ+1;
	public static final int LOCATION_UPLOAD_REQ		= UPLOAD_FILE_RSP+1;
	public static final int LOCATION_UPLOAD_RSP		= LOCATION_UPLOAD_REQ+1;
	public static final int LOCATION_GET_REQ		= LOCATION_UPLOAD_RSP+1;
	public static final int LOCATION_GET_RSP		= LOCATION_GET_REQ+1;
	public static final int DOWNLOAD_AUDIO_REQ		= LOCATION_GET_RSP+1;
	public static final int DOWNLOAD_AUDIO_RSP		= DOWNLOAD_AUDIO_REQ+1;
	public static final int START_BALL_GAME_REQ		= DOWNLOAD_AUDIO_RSP+1;
	public static final int START_BALL_GAME_RSP		= START_BALL_GAME_REQ+1;
	public static final int GET_BALL_LOCATION_REQ		= START_BALL_GAME_RSP+1;
	public static final int GET_BALL_LOCATION_RSP		= GET_BALL_LOCATION_REQ+1;
	public static final int GET_BALL_POSITION_REQ		= GET_BALL_LOCATION_RSP+1;
	public static final int GET_BALL_POSITION_RSP		= GET_BALL_POSITION_REQ+1;
	
	/*jpush*/
	
	public static final int JPUSH_SERVER_COMMAND			= 200;
	
	
	
	
	
	
	
	
	
	
	
	public static final int IS_REQIST_RSP_NO_REGIST			= 503;
	public static final int IS_REQIST_RSP_HAS_REGIST		= 0;
}
