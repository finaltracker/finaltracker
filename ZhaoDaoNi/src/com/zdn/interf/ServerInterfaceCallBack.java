package com.zdn.interf;

public interface ServerInterfaceCallBack {
	/* ServerInterface Call back interface */
	/* String message : �������ѵ�ʱ����ӵ���Ϣ*/
	public void friendAddMe( String callerPhoneNumber , String callerNickName , int validPeriod , String message );

	public void addA_FriendAnswer( int result );
}
