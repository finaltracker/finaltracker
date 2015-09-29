package com.zdn.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.zdn.R;
import com.zdn.util.DateTimePickDialogUtil;
import com.zdn.view.EditTextWithDel;

import org.w3c.dom.Text;

import java.util.Calendar;

public class StartTimeBallDialog extends Activity {
	//private MyDialog dialog;
	private LinearLayout layout;
	private Bundle TargetBundle = null;
	private TextView scheduleArrivedTimeTv = null;
	private TextView saySomthingEditText = null;
	private TextView scheduleArrivedT = null;
	private String initEndDateTime = "2500年1月1日 0:0"; // 初始化结束时间
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exit_dialog);

		Intent intent = this.getIntent();
		TargetBundle = intent.getExtras();

		layout=(LinearLayout)findViewById(R.id.exit_layout);

		saySomthingEditText = (TextView)findViewById(R.id.saySomeThing);
		scheduleArrivedT = (TextView)findViewById(R.id.scheduleArrivedTime);
		scheduleArrivedTimeTv = (TextView)findViewById(R.id.scheduleArrivedTime);
		scheduleArrivedTimeTv.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				//show time setting
				DateTimePickDialogUtil dtpdu = new DateTimePickDialogUtil(
						StartTimeBallDialog.this, null );

				dtpdu.dateTimePicKDialog( scheduleArrivedTimeTv );
			}
		});

	}

	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
	public void exitbutton1(View v) {
		//数据是使用Intent返回
		Intent intent = new Intent();

		TargetBundle.putString("content", saySomthingEditText.getText().toString());

		String sat = scheduleArrivedT.getText().toString();
		Calendar satCalendar = Calendar.getInstance();
		if (!(null == sat || "".equals(sat))) {
			satCalendar = DateTimePickDialogUtil.getCalendarByInintData( sat );
		}
		long diff = satCalendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
		TargetBundle.putString("duration",Long.toString(diff));

		intent.putExtras(TargetBundle); // start

		//设置返回数据
		this.setResult( 1 , intent); // start
		//关闭Activity
    	this.finish();    	
      }  
	public void exitbutton0(View v) {


		//数据是使用Intent返回
		Intent intent = new Intent();
		TargetBundle.putString("exitCause","abort");
		intent.putExtras(TargetBundle); // start
		//设置返回数据
		this.setResult( 2 , intent); // exit
		//关闭Activity

		this.finish();
    	//MainWeixin.instance.finish();//�ر�Main ���Activity
      }  
	
}
