package com.logic;

public interface ServerInterfaceCallback {

	public void registReqCallBack( String phoneNumber  );
	public void addA_ContractorCallBack( String phoneNumber  );
	/* String message : 增加朋友的时候，添加的信息*/
	public void remoteAddMe( String callerPhoneNumber , String callerNickName , int validPeriod , String message );
	public void remoteAddMeResponseCallBack( int result );
/* 列表中的朋友的GPS 位置 */
	public void friendsGpsUpdate( String phoneNumber , int longitude, int latitude );

}
