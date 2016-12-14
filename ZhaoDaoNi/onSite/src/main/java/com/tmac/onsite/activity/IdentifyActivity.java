/**
 * 
 */
package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.R.drawable;
import com.tmac.onsite.utils.StatusBarUtil;
import com.tmac.onsite.utils.TimeCount;
import com.tmac.onsite.view.PhoneEditText;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * @author linsen
 * @date 2016年6月6日
 */
public class IdentifyActivity extends Activity {

	private PhoneEditText code;
	private Button agin, primit;
	private ImageView clear;
	private TimeCount timeCount;
	private LinearLayout back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_identify);
		StatusBarUtil.setTranslucent(this, 0);
//		StatusBarUtil.setColor(this, Color.BLUE, 122);
		initView();
		timeCount = new TimeCount(60000, 1000, agin);
		setEditChanged();
		setListener();
		timeCount.start();
	}
	
	// 初始化View
	private void initView() {
		code = (PhoneEditText) findViewById(R.id.identify_code);
		agin = (Button) findViewById(R.id.identify_agin);
		primit = (Button) findViewById(R.id.identify_primit);
		clear = (ImageView) findViewById(R.id.identify_clear);
		back=(LinearLayout)findViewById(R.id.identify_back);

	}

	// ���������
	public void setEditChanged() {

		code.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				int i = code.getText().toString().trim().length();
				if (i > 0) {
					clear.setVisibility(0);
					if (i == 6) {
						primit.setBackgroundResource(drawable.btn_orange_bg);
						primit.setEnabled(true);
					} else {
						primit.setBackgroundResource(drawable.btn_gray_bg);
						primit.setEnabled(false);
					}
				} else {
					clear.setVisibility(8);

				}

			}
		});

	}

	/** �����¼� */
	public void setListener() {

		clear.setOnClickListener(ls);
		agin.setOnClickListener(ls);
		primit.setOnClickListener(ls);
		back.setOnClickListener(ls);

	}

	private OnClickListener ls = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			// 清除验证码
			case R.id.identify_clear:
				code.setText("");
				break;
			// 跳转
			case R.id.identify_primit:
				Toast.makeText(getApplicationContext(), "验证码输入正确！",
						Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(IdentifyActivity.this, LoginActivity.class);
				startActivity(intent);
				break;
			case R.id.identify_agin:
				timeCount.start();

				break;
			case R.id.identify_back:
				
				finish();
				
				break;
			}

		}
	};
	
}
