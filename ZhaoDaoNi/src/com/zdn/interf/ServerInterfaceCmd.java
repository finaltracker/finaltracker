package com.zdn.interf;

import com.zdn.CommandParser.CommandE;

public interface ServerInterfaceCmd {

	/* ���̹��� */
	public int registReq( String phoneNumber ,  String passWord ,String imsi );
	
	//�Ƿ��Ѿ�ע����
	public void isRegist( String imsi );
	
	//����һ������
	public int addA_Friend( CommandE e );
	//���Ѽ��ҵĻظ�
	public void friendAddMeAnswer( CommandE e );
	
	public CommandE requestFriendList();
	
	//������������Լ���GPS��Ϣ
	public void updateGpsInfo( String phoneNumber , int longitude, int latitude );
	
	//�����������friend��GPS��Ϣ
	public void requestFriendGpsInfo( String phoneNumber , int longitude, int latitude );

	
}
