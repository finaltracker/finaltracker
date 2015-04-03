package com.zdn.chat;

import com.adn.db.DBHelper;
import com.adn.db.DBManager;

public class ZdnMessageDbAdapt {


  public String type;   // 0-text | 1-photo | 2-face | more type ... TODO://
  public String state;    // 0-sending | 1-success | 2-fail
  public String fromUserName;
  public String fromUserAvatar;
  public String toUserName;
  public String toUserAvatar;
  public String content;

  public String isSend;
  public String sendSucces;
  public String time;
  
  public ZdnMessageDbAdapt(
                        String   type           ,
                        String   state          ,
                        String   fromUserName   ,
                        String   fromUserAvatar ,
                        String   toUserName     ,
                        String   toUserAvatar   ,
                        String   content        ,
                        String   isSend         ,
                        String   sendSucces     ,
                        String   time           
  )                                             
  {
    this.type             = type;
    this.state            = state;
    this.fromUserName     = fromUserName;
    this.fromUserAvatar   = fromUserAvatar;
    this.toUserName       = toUserName;
    this.toUserAvatar     = toUserAvatar;
    this.content          = content;
    this.isSend           = isSend;
    this.sendSucces       = sendSucces;
    this.time             = time;
  }
  
  
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

  public void SaveToDb( )
  {
    DBHelper getDbHelper = DBManager.GetDbHelper( ZdnMessageDbAdapt.class );
    	
    getDbHelper.add(  this );
    	
    getDbHelper.closeDB();
  }
  
}
