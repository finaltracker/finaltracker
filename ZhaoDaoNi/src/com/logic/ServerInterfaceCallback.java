package com.logic;

public interface ServerInterfaceCallback {

	public void registReqCallBack( String phoneNumber  );
	public void addA_ContractorCallBack( String phoneNumber  );
	/* String message : �������ѵ�ʱ����ӵ���Ϣ*/
	public void remoteAddMe( String callerPhoneNumber , String callerNickName , int validPeriod , String message );
	public void remoteAddMeResponseCallBack( int result );
/* �б��е����ѵ�GPS λ�� */
	public void friendsGpsUpdate( String phoneNumber , int longitude, int latitude );

}
