/**
 * 
 */
package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.utils.StatusBarUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * @author tmac
 */
public class DetailTaskActivity extends Activity {
	
	private Button rob_Btn;
	private ImageView iv_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail_task);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.layout_title_bg),0);
		
		initViews();
		initEvents();
	}
	
	private void initViews() {
		// TODO Auto-generated method stub
		rob_Btn = (Button) findViewById(R.id.main_button);
		iv_back = (ImageView) findViewById(R.id.iv_back_detail);
	}
	
	private void initEvents() {
		// TODO Auto-generated method stub
		rob_Btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(DetailTaskActivity.this);
				builder.setMessage(R.string.detail_dialog_message)
						.setPositiveButton(R.string.ensure, new DialogInterface.OnClickListener() {
								
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Toast.makeText(DetailTaskActivity.this, "你的抢单要求已发送", Toast.LENGTH_SHORT).show();
							}
							
						})
						.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
								
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
										
							}
						})
						.create()
						.show();
				
				
			}
		});
		
		iv_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
}
