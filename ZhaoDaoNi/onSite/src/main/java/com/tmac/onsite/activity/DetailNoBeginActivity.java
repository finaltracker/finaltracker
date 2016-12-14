package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.utils.LightControl;
import com.tmac.onsite.utils.MyDialog;
import com.tmac.onsite.utils.StatusBarUtil;
import com.tmac.onsite.utils.MyDialog.OnDialogClickListener;
import com.tmac.onsite.view.AudioPopupWindow;
import com.tmac.onsite.view.AudioPopupWindow.OnUpLoadClickListener;

import android.R.integer;
import android.R.layout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * @author tmac
 */
public class DetailNoBeginActivity extends Activity implements OnClickListener, OnUpLoadClickListener, OnDialogClickListener{
	
	private static boolean DBG = true;
	private static final String TAG = "LC-DetailNoBeginActivity";
	private Button btn_upload_pre_img;
	private Button btn_upload_end_img;
	private Button btn_construct_finish;
	private Button btn_construct_no_finish;
	private Button btn_construct_img;
	private Button btn_record_time;
	private Button btn_check_reason;
	private AudioPopupWindow popupWindow;
	private RelativeLayout layout;
	private RelativeLayout layout_bottom_1;
	private RelativeLayout layout_bottom_2;
	private ImageView iv_back;
	private static String reasonStr;
	public static final int DEFAULT_VALUE = -1;
	private static final int REQUEST_CODE = 0; 
	public static final String SHOW_REASON = "show_reason";
	public static final String RETURN_STATE = "state";
	public static final String CANNOT_REASON = "reason";
	public static final int UPLOAD_PRE = 1;
	public static final int UPLOAD_END = 2; 
	public static final int EDIT_REASON = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_nobegin);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.layout_title_bg),0);
			
		initViews();
		initEvents();
			
	}

	private void initViews() {
		// TODO Auto-generated method stub
		btn_upload_pre_img = (Button) findViewById(R.id.upload_pre_image);
		btn_upload_end_img = (Button) findViewById(R.id.upload_end_image);
		layout = (RelativeLayout) findViewById(R.id.nobegin_layout_bottom);
		layout_bottom_1 = (RelativeLayout) findViewById(R.id.nobegin_layout_bottom_1);
		layout_bottom_2 = (RelativeLayout) findViewById(R.id.nobegin_layout_bottom_2);
		btn_construct_finish = (Button) findViewById(R.id.contrust_finish);
		btn_construct_no_finish = (Button) findViewById(R.id.contrust_no_finish);
		btn_construct_img = (Button) findViewById(R.id.contrust_img_btn);
		btn_record_time = (Button) findViewById(R.id.record_time_btn);
		btn_check_reason = (Button) findViewById(R.id.check_reason_btn);
		
		iv_back = (ImageView) findViewById(R.id.iv_back_detail);
		popupWindow = new AudioPopupWindow(this, this);
	}

	private void initEvents() {
		// TODO Auto-generated method stub
		
		btn_upload_pre_img.setOnClickListener(this);
		btn_upload_end_img.setOnClickListener(this);
		btn_construct_finish.setOnClickListener(this);
		btn_construct_no_finish.setOnClickListener(this);
		btn_construct_img.setOnClickListener(this);
		btn_record_time.setOnClickListener(this);
		btn_check_reason.setOnClickListener(this);
		iv_back.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.upload_pre_image:
			Intent intent_pre = new Intent(DetailNoBeginActivity.this, UploadImgActivity.class);
			intent_pre.putExtra(RETURN_STATE, UPLOAD_PRE);
			startActivityForResult(intent_pre, REQUEST_CODE);
			break;
		case R.id.upload_end_image:
			Intent intent_end = new Intent(DetailNoBeginActivity.this, UploadImgActivity.class);
			intent_end.putExtra(RETURN_STATE, UPLOAD_END);
			startActivityForResult(intent_end, REQUEST_CODE);
			break;
		case R.id.contrust_finish:
			MyDialog.showDialog(this, R.string.ensure_construct_finish, R.string.waiting, R.string.ensure_finish,
				getWindow(), this, R.id.contrust_finish);
			break;
		case R.id.contrust_no_finish:
			MyDialog.showDialog(this, R.string.ensure_construct_nofinish, R.string.try_again, R.string.cannot_finish,
					getWindow(), this, R.id.contrust_no_finish);
			break;
		case R.id.contrust_img_btn:
			
			break;
		case R.id.record_time_btn:
			// 播放录音
			popupWindow.playAudio(false);
			
			break;
		case R.id.iv_back_detail:
			finish();
			break;
		case R.id.check_reason_btn:
			Intent intent = new Intent(DetailNoBeginActivity.this, CannotFinishActivity.class);
			intent.putExtra(SHOW_REASON, true);
			intent.putExtra(CANNOT_REASON, reasonStr);
			startActivity(intent);
			if(DBG) Log.i(TAG, "check_reason_btn");
			break;
		default:
			break;
		}
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		int state = data.getIntExtra(RETURN_STATE, DEFAULT_VALUE);
		if(DBG) Log.i(TAG, "onActivityResult");
		if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
			if(state == UPLOAD_PRE){
				// 显示上传施工后照片的Button
				btn_upload_end_img.setVisibility(View.VISIBLE);
				btn_upload_pre_img.setVisibility(View.GONE);
				layout_bottom_1.setVisibility(View.GONE);
			}else if(state == UPLOAD_END){
				btn_upload_end_img.setVisibility(View.GONE);
				btn_upload_pre_img.setVisibility(View.GONE);
				layout_bottom_1.setVisibility(View.VISIBLE);
			}else if(state == EDIT_REASON){
				layout_bottom_2.setVisibility(View.VISIBLE);
				btn_record_time.setVisibility(View.GONE);
				btn_construct_no_finish.setVisibility(View.GONE);
				btn_check_reason.setVisibility(View.VISIBLE);
				reasonStr = data.getStringExtra(CANNOT_REASON);
				if(DBG) Log.i(TAG, "reasonStr = " + reasonStr);
			}
		}
	}

	/**
	 * 上传录音成功的回调
	 */
	@Override
	public void onUpload(String time) {
		// TODO Auto-generated method stub
		Toast.makeText(this, getResources().getString(R.string.upload_record_success), Toast.LENGTH_SHORT).show();
		layout_bottom_1.setVisibility(View.GONE);
		layout_bottom_2.setVisibility(View.VISIBLE);
		
		btn_record_time.setText(time);
		
	}

	@Override
	public void onDialog(int type, int situation) {
		// TODO Auto-generated method stub
		switch (situation) {
		case R.id.contrust_finish:
			if(type == MyDialog.NEGATIVE){
				MyDialog.lightOff(getWindow());
				int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		        int height = getResources().getDimensionPixelSize(resourceId);
		        popupWindow.setAnimationStyle(R.style.dir_popupwindow_anim);
		        popupWindow.showAsDropDown(layout, 0, -layout.getMeasuredHeight()+height);
		        popupWindow.setOnDismissListener(new OnDismissListener() {
					
					@Override
					public void onDismiss() {
						// TODO Auto-generated method stub
						MyDialog.lightOn(getWindow());
					}
				});
			}
			break;
		case R.id.contrust_no_finish:
			if(type == MyDialog.NEGATIVE){
				startActivityForResult(new Intent(DetailNoBeginActivity.this, CannotFinishActivity.class), REQUEST_CODE);
			}
			break;
		default:
			break;
		}
	}
	
}
