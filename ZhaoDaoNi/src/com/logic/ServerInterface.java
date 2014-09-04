package com.logic;

public class ServerInterface implements ServerInterfaceCallback{

	/* 流程管理 */
	public void registReq( String phoneNumber , String nickName  )
	{
		
	}
	
	public void addA_Contractor( String PhoneNumber , String comment )
	{
		
	}
	
	public void remoteAddMeResponse( int result )
	{
		
	}
	

	@Override
	public void registReqCallBack(String phoneNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addA_ContractorCallBack(String phoneNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remoteAddMe(String callerPhoneNumber, String callerNickName,
			int validPeriod, String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remoteAddMeResponseCallBack(int result) {
		// TODO Auto-generated method stub
		
	}

	/* 位置信息管理 */
	
	/*phoneNumber ： 标识自己，
	 *  经度 ， 纬度
	 *   */
	public void updateGpsInfo( String phoneNumber , int longitude, int latitude )
	{
		
	}
	
	@Override
	public void friendsGpsUpdate(String phoneNumber, int longitude, int latitude) {
		// TODO Auto-generated method stub
		
	}
	

}