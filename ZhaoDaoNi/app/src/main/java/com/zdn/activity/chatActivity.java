package com.zdn.activity;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.Toast;

import com.zdn.R;
import com.zdn.CommandParser.CommandE;
import com.zdn.basicStruct.SendMessageRspEvent;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.basicStruct.getMessageRspEvent;
import com.zdn.chat.ZdnMessage;
import com.zdn.chat.MessageAdapter;
import com.zdn.chat.MessageInputToolBox;
import com.zdn.chat.OnOperationListener;
import com.zdn.chat.Option;
import com.zdn.data.dataManager;
import com.zdn.event.EventDefine;
import com.zdn.logic.InternetComponent;
import com.zdn.logic.MainControl;
import com.zdn.util.ObjectConvertTool;
import de.greenrobot.event.EventBus;

public class chatActivity extends FragmentActivity {
	
	private int 				INIT_SHOW_MESSAGE_MAX	= 50;
	private MessageInputToolBox box;
	private ListView 			listView;
	private MessageAdapter 		adapter;
	private String              targetTo; // the message will sent to whom
	int teamPosition = 0;
	int memberPosition = 0;
	friendMemberData fdm = null;
	@SuppressLint("UseSparseArrays")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle=this.getIntent().getExtras(); 
		targetTo = bundle.getString("targetTo");
		teamPosition = bundle.getInt("teamPosition");
		memberPosition = bundle.getInt("memberPosition");
		fdm = dataManager.getFrilendList().getMemberData(teamPosition, memberPosition);
		setContentView(R.layout.chat_activity);
		
		EventBus.getDefault().register(this );  
		initMessageInputToolBox();
				initListView();
		
	}
	@Override
	protected void onDestroy() {
		EventBus.getDefault().unregister(this); 
		super.onDestroy();
		
	}
	
	/**
	 * init MessageInputToolBox
	 */
	@SuppressLint("ShowToast")
	private void initMessageInputToolBox(){
		box = (MessageInputToolBox) findViewById(R.id.messageInputToolBox);
		box.setOnOperationListener(new OnOperationListener() {
			
			@Override
			public void send(String content) {
				
				System.out.println("===============" + content);
				
				ZdnMessage message = new ZdnMessage( targetTo ,0, 1, "Tom", "avatar", targetTo, "avatar", content, true, true, new Date());
				
				MainControl.sendMessageToServer( message,targetTo);
				adapter.getData().add(message);
				listView.setSelection(listView.getBottom()); 
				
				//Just demo
				//createReplayMsg(message);
			}
			
			@Override
			public void selectedFace(String content) {
				
				System.out.println("===============" + content);
				ZdnMessage message = new ZdnMessage(targetTo,ZdnMessage.MSG_TYPE_FACE, ZdnMessage.MSG_STATE_SUCCESS, "Tomcat", "avatar", targetTo, "avatar", content, true, true, new Date());
				adapter.getData().add(message);
				listView.setSelection(listView.getBottom());
				
				//Just demo
				//createReplayMsg(message);
			}
			
			
			@Override
			public void selectedFuncation(int index) {
				
				System.out.println("===============" + index);
				
				switch (index) {
				case 0:
					//do some thing
					break;
				case 1:
					//do some thing
					break;
					
				default:
					break;
				}
				Toast.makeText(chatActivity.this, "Do some thing here, index :" +index, 1000).show();
				
			}
			
		});
		
		ArrayList<String> faceNameList = new ArrayList<String>();
		for(int x = 1; x <= 10; x++){
			faceNameList.add("big"+x);
		}
		for(int x = 1; x <= 10; x++){
			faceNameList.add("big"+x);
		}
		
		ArrayList<String> faceNameList1 = new ArrayList<String>();
		for(int x = 1; x <= 7; x++){
			faceNameList1.add("cig"+x);
		}
		
		
		ArrayList<String> faceNameList2 = new ArrayList<String>();
		for(int x = 1; x <= 24; x++){
			faceNameList2.add("dig"+x);
		}
		
		Map<Integer, ArrayList<String>> faceData = new HashMap<Integer, ArrayList<String>>();
		faceData.put(R.drawable.em_cate_magic, faceNameList2);
		faceData.put(R.drawable.em_cate_rib, faceNameList1);
		faceData.put(R.drawable.em_cate_duck, faceNameList);
		box.setFaceData(faceData);
		
		
		List<Option> functionData = new ArrayList<Option>();
		for(int x = 0; x < 5; x++){
			Option takePhotoOption = new Option(this, "Take", R.drawable.take_photo);
			Option galleryOption = new Option(this, "Gallery", R.drawable.gallery);
			functionData.add(galleryOption);
			functionData.add(takePhotoOption);
		}
		box.setFunctionData(functionData);
	}
	
	 
	
	private void initListView(){
		listView = (ListView) findViewById(R.id.messageListview);
		List<ZdnMessage> messages = new ArrayList<ZdnMessage>();
		
		/*
		//create Data
		ZdnMessage message = new ZdnMessage("Jerry",ZdnMessage.MSG_TYPE_TEXT, ZdnMessage.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar", "Hi", false, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 8));
		ZdnMessage message1 = new ZdnMessage("Jerry",ZdnMessage.MSG_TYPE_TEXT, ZdnMessage.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar", "Hello World", true, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24)* 8));
		ZdnMessage message2 = new ZdnMessage("Jerry",ZdnMessage.MSG_TYPE_PHOTO, ZdnMessage.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar", "device_2014_08_21_215311", false, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 7));
		ZdnMessage message3 = new ZdnMessage("Jerry",ZdnMessage.MSG_TYPE_TEXT, ZdnMessage.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar", "Haha", true, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 7));
		ZdnMessage message4 = new ZdnMessage("Jerry",ZdnMessage.MSG_TYPE_FACE, ZdnMessage.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar", "big3", false, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 7));
		ZdnMessage message5 = new ZdnMessage("Jerry",ZdnMessage.MSG_TYPE_FACE, ZdnMessage.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar", "big2", true, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 6));
		ZdnMessage message6 = new ZdnMessage("Jerry",ZdnMessage.MSG_TYPE_TEXT, ZdnMessage.MSG_STATE_FAIL, "Tom", "avatar", "Jerry", "avatar", "test send fail", true, false, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 6));
		ZdnMessage message7 = new ZdnMessage("Jerry",ZdnMessage.MSG_TYPE_TEXT, ZdnMessage.MSG_STATE_SENDING, "Tom", "avatar", "Jerry", "avatar", "test sending", true, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 6));
		
		messages.add(message);
		messages.add(message1);
		messages.add(message2);
		messages.add(message3);
		messages.add(message4);
		messages.add(message5);
		messages.add(message6);
		messages.add(message7);
		*/
		
		int messageListSize = fdm.getMessageList().size();
		
		int startIndex = 0;
		
		if( messageListSize > INIT_SHOW_MESSAGE_MAX ) 
		{
			startIndex = messageListSize - INIT_SHOW_MESSAGE_MAX;
		}
		for( int i = startIndex ; i<messageListSize  ;i++  )
		{
			messages.add(fdm.getMessageList().get( i ) );
		}
		
		adapter = new MessageAdapter(this, messages);
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		//setSelection work ok require that listView load data finished
		new Handler().postDelayed(new Runnable()  {
			@Override
			public void run() {
				listView.setSelection(listView.getBottom());
				
			}
			}, 100);
		
		
		listView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				box.hide();
				return false;
			}
		});
		
	}
	
	
	private void createReplayMsg(ZdnMessage message){
		
		final ZdnMessage reMessage = new ZdnMessage("Jerry",message.getType(), 1, "Tom", "avatar", "Jerry", "avatar", 
											message.getType() == 0 ? "Re:" + message.getContent() : message.getContent(),
											false, true, new Date()
											);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000 * (new Random().nextInt(3) +1));
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							adapter.getData().add(reMessage);
							listView.setSelection(listView.getBottom()); 
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	public void onEvent(Object event)
	{
		
		if( event instanceof SendMessageRspEvent )
		{
			SendMessageRspEvent e = (SendMessageRspEvent)event;
			
			adapter.notifyDataSetChanged();
		}
		else if( event instanceof getMessageRspEvent )
		{
			friendMemberData  fmd = dataManager.getFrilendList().getMemberData(teamPosition, memberPosition);
			getMessageRspEvent e = (getMessageRspEvent) event;
			if( fmd.basic.getPhoneNumber().equals(e.m.getBelogTag() ))
			{
				adapter.getData().add(e.m);
				listView.setSelection(listView.getBottom());
			}
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())//得到被点击的item的itemId
		{
			case R.id.guyDetail: //
				Intent intent = new Intent(this, friendInformationDetailActivity.class);

				intent.putExtra("teamPosition", teamPosition);
				intent.putExtra("memberPosition", memberPosition);
				// 调用startActivityForResult方法
				startActivity(intent);
				break;

		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.chat_activity_menu, menu);
		return true;
		
	}
	@Override
	protected void onResume() {
		if( null == dataManager.getFrilendList().getMemberData(teamPosition, memberPosition) )
		{ // no this friend/circle in friend list
			finish(); 
		}
		super.onResume();
	}
}
