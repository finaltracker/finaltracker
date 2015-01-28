package com.zdn.jpush;

import org.json.JSONException;
import org.json.JSONObject;

import com.zdn.CommandParser.CommandE;
import com.zdn.CommandParser.Property;
import com.zdn.activity.MainActivity;
import com.zdn.activity.MainControl;
import com.zdn.event.EventDefine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * 鑷畾涔夋帴鏀跺櫒
 * 
 * 濡傛灉涓嶅畾涔夎繖锟�Receiver锛屽垯锟�
 * 1) 榛樿鐢ㄦ埛浼氭墦锟�锟斤拷鐣岄潰
 * 2) 鎺ユ敹涓嶅埌鑷畾涔夋秷锟�
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] ACTION_REGISTRATION_ID : " + regId);
            //send the Registration Id to your server...
                        
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] ACTION_MESSAGE_RECEIVED EXTRA_MESSAGE : " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	processCustomMessage(context, bundle);
        
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] ACTION_NOTIFICATION_RECEIVED");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] ACTION_NOTIFICATION_RECEIVED  notifactionId: " + notifactionId);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] ACTION_NOTIFICATION_OPENED ");
            
        	//鎵撳紑鑷畾涔夌殑Activity
        	Intent i = new Intent(context, TestActivity.class);
        	i.putExtras(bundle);
        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        	context.startActivity(i);
        	
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] ACTION_RICHPUSH_CALLBACK  EXTRA_EXTRA: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //鍦ㄨ繖閲屾牴JPushInterface.EXTRA_EXTRA 鐨勫唴瀹瑰鐞嗕唬鐮侊紝姣斿鎵撳紑鏂扮殑Activity锟�鎵撳紑锟�锟斤拷缃戦〉锟�.
        	
        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.e(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
	}

	// 鎵撳嵃 intent extra 鏁版嵁
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} 
			else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}
	
	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}

			}
			context.sendBroadcast(msgIntent);
		}
		else
		{
			
			String ExtraStr = "";
			StringBuilder sb = new StringBuilder();
			for (String key : bundle.keySet()) {
			
				if( key.equals(JPushInterface.EXTRA_EXTRA ))
				{
					ExtraStr =  bundle.getString(key);
				}
			}
				
			Message msg_cmd = MainControl.getInstance().obtainMessage(); 
			msg_cmd.what = EventDefine.JPUSH_SERVER_COMMAND;
			
			CommandE e_c = new CommandE("JPUSH_SERVER_COMMAND");
			
			e_c.AddAProperty( new Property("EventDefine", Integer.toString( EventDefine.JPUSH_SERVER_COMMAND ) ));
			e_c.AddAProperty( new Property("Command",  bundle.getString(JPushInterface.EXTRA_MESSAGE) ));
			e_c.AddAProperty( new Property("Extra",  ExtraStr  ));

			msg_cmd.obj = e_c;   
	        
			MainControl.getInstance().sendMessage(msg_cmd);
		}
	}
}
