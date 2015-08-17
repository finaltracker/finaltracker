package com.zdn.chat;


import android.graphics.Bitmap;

public interface OnOperationListener {

	public void send(String content);
	
	public void selectedFace(String content);
	
	public void selectedFuncation(int index);

	public void selectPictureReq( );

	public void sendAudioReq(String voiceFileName);

}
