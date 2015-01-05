package com.zdn.interf;

public interface ServerInterfaceCallBack {
	/* ServerInterface Call back interface */
	/* String message : 增加朋友的时候，添加的信息*/
	public void friendAddMe( String callerPhoneNumber , String callerNickName , int validPeriod , String message );

	public void addA_FriendAnswer( int result );
}
