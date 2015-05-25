package com.zdn.chat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.zdn.db.DBHelper;
import com.zdn.db.DBManager;

public class ZdnMessage {
	public static int MSG_TYPE_TEXT 	= 0;
	public static int MSG_TYPE_PHOTO 	= 1;
	public static int MSG_TYPE_FACE 	= 2;
	
	public static int MSG_STATE_SENDING 	= 0;
	public static int MSG_STATE_SUCCESS 	= 1;
	public static int MSG_STATE_FAIL 		= 2;
	
	public int type;		// 0-text | 1-photo | 2-face | more type ... TODO://
	public int state; 		// 0-sending | 1-success | 2-fail
	public String fromUserName;
	public String fromUserAvatar;
	public String toUserName;
	public String toUserAvatar;
	public String content;
	public String belogTag;
	

	public Boolean isSend;
	public Boolean sendSucces;
	public String dateTime; // yyyy-MM-dd-HH-mm example:dateTime=(new SimpleDateFormat("yyyy-MM-dd")).format(ddate);  

    public ZdnMessage()
    {
    }
	public ZdnMessage(String groupTag , Integer type, Integer state, String fromUserName,
			String fromUserAvatar, String toUserName, String toUserAvatar,
			//     内容   是否是发送数据
			String content, Boolean isSend, Boolean sendSucces, Date time) {
		super();

		
		this.type = type;
		this.state = state;
		this.fromUserName = fromUserName;
		this.fromUserAvatar = fromUserAvatar;
		this.toUserName = toUserName;
		this.toUserAvatar = toUserAvatar;
		this.content = content;
		this.isSend = isSend;
		this.sendSucces = sendSucces;
		this.dateTime = (new SimpleDateFormat("yyyy-MM-dd-HH-mm")).format(time); 
		this.belogTag = groupTag;
	}
	public ZdnMessage(String belogTag , Integer type, Integer state, String fromUserName,
			String fromUserAvatar, String toUserName, String toUserAvatar,

			String content, Boolean isSend, Boolean sendSucces, String time) {
		super();

		
		this.type = type;
		this.state = state;
		this.fromUserName = fromUserName;
		this.fromUserAvatar = fromUserAvatar;
		this.toUserName = toUserName;
		this.toUserAvatar = toUserAvatar;
		this.content = content;
		this.isSend = isSend;
		this.sendSucces = sendSucces;
		this.dateTime = time; 
		this.belogTag = belogTag;
	}
	
	public String getBelogTag()
	{
		return this.belogTag;
	}
	/*
	public ZdnMessage( ZdnMessageDbAdapt mAda) 
	{
		type = Integer.parseInt(mAda.type);
		state = Integer.parseInt(mAda.state);
		fromUserName = mAda.fromUserName;
		fromUserAvatar = mAda.fromUserAvatar;
		toUserName = mAda.toUserName;
		toUserAvatar = mAda.toUserAvatar;
		content = mAda.content;
		isSend = Boolean.parseBoolean( mAda.isSend );
		sendSucces = Boolean.parseBoolean( mAda.sendSucces );
		SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd-HH-mm");
		try {
			dateTime = sdf.parse( mAda.time );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dateTime = new Date("2000-01-01");
		}
	}
*/
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getFromUserAvatar() {
		return fromUserAvatar;
	}

	public void setFromUserAvatar(String fromUserAvatar) {
		this.fromUserAvatar = fromUserAvatar;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getToUserAvatar() {
		return toUserAvatar;
	}

	public void setToUserAvatar(String toUserAvatar) {
		this.toUserAvatar = toUserAvatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsSend() {
		return isSend;
	}

	public void setIsSend(Boolean isSend) {
		this.isSend = isSend;
	}

	public Boolean getSendSucces() {
		return sendSucces;
	}

	public void setSendSucces(Boolean sendSucces) {
		this.sendSucces = sendSucces;
	}

	public Date getTime() {
		Date ret = null;
		try {
			ret =  new SimpleDateFormat("yyyy-MM-dd-HH-mm").parse(dateTime);
		} catch (ParseException e) {
			 try {
				ret =new SimpleDateFormat("yyyy-MM-dd-HH-mm").parse("2000-01-01-00-00");
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public String getTimeString() {
		
		return dateTime;
	}

	public void setTime(Date time) {
		this.dateTime = (new SimpleDateFormat("yyyy-MM-dd-HH-mm")).format(time); 
	}
	
	
	public void SaveToDb( )
	{
		//ZdnMessageDbAdapt zmda = new ZdnMessageDbAdapt(this);
		
		DBHelper getDbHelper = DBManager.GetDbHelper( ZdnMessage.class );
		
		getDbHelper.add(  this );
		
		getDbHelper.closeDB();
	}
}
