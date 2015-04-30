package com.zdn.channel;

import com.zdn.CommandParser.ExpCommandE;
import com.zdn.CommandParser.Property;
import com.zdn.logic.InternetComponent;
import com.zdn.logic.MainControl;

import android.os.Message;
import android.util.Log;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


/**
 * Provides utility methods for communicating with the server.
 */
final public class xUtilsHttp {
    /** The tag used to log to adb console. */
    private static final String TAG = "NetworkUtilities";
   
    /** Timeout (in ms) we specify for each http request */
    public static final int HTTP_REQUEST_TIMEOUT_MS = 30 * 1000;
    

    private xUtilsHttp() {
    }


    /**
     * Connects to the server,
     * @return String request response (or null)
     */
    
    //CommandE 0 位置必须是URL 地址
    public static void httpReq( ExpCommandE command ) {

    	RequestParams params = new RequestParams();
    	
    	ExpCommandE e = command;
		final ExpCommandE reponse = InternetComponent.packA_CommonExpCommandE_ToMainControl( "SEND_MESSAGE_TO_SERVER_RSP",Integer.parseInt(command.GetExpPropertyContext("EventDefine")) + 1 );
	
		reponse.setUserData( e.getUserData() );
		
		final Message msg_rsp = MainControl.getInstance().obtainMessage(); 
		msg_rsp.what = MainControl.SEND_MESSAGE_TO_SERVER_RSP;
        //
		

		msg_rsp.obj = reponse;   
        
		
        
        String url = command.GetExpPropertyContext("URL");

        Log.d("HTTP", "httpReq : " );
        for( int i = 0 ; i < command.GetExpPropertyNum() ; i++ )
        {
        	Log.d("HTTP", command.GetExpProperty(i).GetPropertyName() + " " + command.GetExpProperty(i).GetPropertyContext() );
        }
        for( int i = 0 ; i < command.GetPropertyNum() ; i++ )
        {
        	Log.d("HTTP", command.GetProperty(i).GetPropertyName() + " " + command.GetProperty(i).GetPropertyContext() );
        }
        
        for( int i = 0 ; i < command.GetPropertyNum() ; i++ )
        {
        	params.addBodyParameter(command.GetProperty(i).GetPropertyName(), command.GetProperty(i).GetPropertyContext());
        }
        

        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, url , params,
            new RequestCallBack<String>() {

                @Override
                public void onStart() {
                    
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    if (isUploading) {
                    	
                    } else {
                        
                    }
                }

                @Override
                public void onSuccess(ResponseInfo<String> responseInfo) {
                    //ExpCommandE
                	Log.d("HTTP", "httpReqRsp  result: " + responseInfo.result );
                	reponse.AddAProperty( new Property("HTTP_REQ_RSP",responseInfo.result ) );
                	reponse.AddAProperty( new Property("STATUS", "0" ));
                	MainControl.getInstance().sendMessage(msg_rsp);
            		
                    
                }

                @Override
                public void onFailure(HttpException error, String msg) {
                	Log.d("HTTP", "httpReqRsp : error" );
                	reponse.AddAProperty( new Property("HTTP_REQ_RSP","error" ) );
                	reponse.AddAProperty( new Property("STATUS", "1" ));
                	MainControl.getInstance().sendMessage(msg_rsp);
                }
        });   
        
        
        
    }
    
   

}
