package com.zdn.interf;

import com.zdn.CommandParser.CommandE;

public interface ServerInterfaceCmd {

	/* ���̹��� */
	public int registReq( CommandE e  );
	
	//�Ƿ��Ѿ�ע����
	public void isRegist( String imsi );
	
	public void login( CommandE e );
	//����һ������
	public int addA_Friend( CommandE e );
	//���Ѽ��ҵĻظ�
	public void friendAddMeAnswer( CommandE e );
	//��ȡ�����б�
	public void getFriendList( CommandE e );
	//���º�����Ϣ
	public void updateFriendInfomation( CommandE e);
	
	//ɾ��һ������
	public void deleteFriend( CommandE e );
	
	//������������Լ���GPS��Ϣ
	public void updateGpsInfo( String phoneNumber , int longitude, int latitude );
	
	//�����������friend��GPS��Ϣ
	public void requestFriendGpsInfo( String phoneNumber , int longitude, int latitude );
	
	public void searchFirendOrCircle(  CommandE e );

	
}
