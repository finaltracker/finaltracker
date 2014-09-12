package com.logic;

import CommandParser.CommandE;

public interface ServerInterface {

	/* ���̹��� */
	public int registReq( String phoneNumber , String nickName  );
	
	//�Ƿ��Ѿ�ע����
	public void isRegist( String imsi );
	
	//����һ������
	public int addA_Friend( String PhoneNumber , String comment );
	//���Ѽ��ҵĻظ�
	public void friendAddMeAnswer( int result );
	
	public CommandE requestFriendList();
	
	//������������Լ���GPS��Ϣ
	public void updateGpsInfo( String phoneNumber , int longitude, int latitude );
	
	//�����������friend��GPS��Ϣ
	public void requestFriendGpsInfo( String phoneNumber , int longitude, int latitude );

	
	/* ServerInterface Call back interface */
	/* String message : �������ѵ�ʱ����ӵ���Ϣ*/
	public void friendAddMe( String callerPhoneNumber , String callerNickName , int validPeriod , String message );

	public void addA_FriendAnswer( int result );

}