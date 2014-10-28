package com.zdn.activity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.zdn.R;
import com.zdn.CommandParser.CommandE;
import com.zdn.CommandParser.Property;
import com.zdn.common.EventDefine;
import com.zdn.logic.InternetComponent;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/***
 * 
 * 注册
 * 
 * @author toshiba
 * 
 */
public class Zhuce extends Activity {

	private Button btn_ok;
	String namePath;
	static public Zhuce me = null;
	
	private EditText et_name, et_pass;
	//private BaiduPCSAction action = new BaiduPCSAction();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.zhuce);

		viewInit();
		me = this;
	}
	public static Zhuce getInstance() { return me; }
	
	public void viewInit() {
		btn_ok = (Button) this.findViewById(R.id.btn_login_ok);
		btn_ok.setOnClickListener(onClickListener);
		et_name = (EditText) this.findViewById(R.id.edit_name);
		et_name.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		et_pass = (EditText) this.findViewById(R.id.edit_password);
	}

	public void datainit() {

		//activityVolues.loadName = et_name.getText().toString();

		String info = et_name.getText().toString() + "-"
				+ et_pass.getText().toString();
		namePath = this.getFilesDir() + "/name.txt";

		String saveFile = "name.txt";

		FileOutputStream outputStream;
		try {
			outputStream = this.openFileOutput(saveFile, MODE_PRIVATE);
			if (!info.equals("")) {
				// save file
				outputStream.write(info.getBytes());

			} else {
				byte bytes = 0;
				outputStream.write(bytes);
			}

			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	OnClickListener onClickListener = new OnClickListener() {

		public void onClick(View v) {
			int id = v.getId();
			if (id == R.id.btn_login_ok) {
				
				String Id = et_name.getText().toString() ;
				String passWord = et_pass.getText().toString();
				
				if(Id.isEmpty() || passWord.isEmpty() )
				{
					Toast.makeText(getApplicationContext(), "用户名或密码不能为空",Toast.LENGTH_SHORT).show();
					return ;
				}
				CommandE e = new  CommandE("ACCOUNT_REQUST");
				e.AddAProperty(new Property("EventDefine",Integer.toString( EventDefine.UI_TO_CTRL_ACCOUNT_REQUEST ) ) );
				e.AddAProperty(new Property("ID",Id ) );
				e.AddAProperty(new Property("PASS_WORD",passWord ) );
				Message m = MainControl.getInstance().handler.obtainMessage();
				m.obj = e;
				MainControl.getInstance().handler.sendMessage(m);
				
				//still stay this activity until response back
			}
			
		}

	};
	
	public void registFeedback( int status ,String error )
	{
		switch( status )
		{
			case 0: // success
				me = null;
				finish();
				break;
			default:
				Toast.makeText(getApplicationContext(), error ,Toast.LENGTH_SHORT).show();
				
				break;
		}
	}

}
