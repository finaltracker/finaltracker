package com.zdn.chat;

public class ZdnMessageDbAdapt {


	public String type;		// 0-text | 1-photo | 2-face | more type ... TODO://
	public String state; 		// 0-sending | 1-success | 2-fail
	public String fromUserName;
	public String fromUserAvatar;
	public String toUserName;
	public String toUserAvatar;
	public String content;

	public String isSend;
	public String sendSucces;
	public String time;
	
	
	
	public ZdnMessageDbAdapt( ZdnMessage m )
	{
		type = m.getType().toString();
		state = m.getState().toString();
		fromUserName = m.getFromUserName();
		fromUserAvatar = m.getFromUserAvatar();
		toUserName = m.getToUserName();
		toUserAvatar = m.getToUserAvatar();
		content = m.getContent();
		isSend = m.getIsSend().toString();
		sendSucces = m.getSendSucces().toString();
		time = m.getTime().toString();
	}
	
}
