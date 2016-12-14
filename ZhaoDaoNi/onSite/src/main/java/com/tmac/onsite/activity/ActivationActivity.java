/**
 * 
 */
package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.R.drawable;
import com.tmac.onsite.utils.StatusBarUtil;
import com.tmac.onsite.view.PhoneEditText;

import android.app.Activity;
import android.content.Intent;
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
public class ActivationActivity extends Activity{
	
	private PhoneEditText phone;
	private ImageView clear;
	private Button activate;
	private LinearLayout back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activation);
		StatusBarUtil.setTranslucent(this, 0);
		
		initView();
		setEditChanged();
		setListener();
	}

	
	public void initView() {
		phone = (PhoneEditText) findViewById(R.id.home_phone);
		clear = (ImageView) findViewById(R.id.home_clear);
		activate = (Button) findViewById(R.id.home_activate);
		back = (LinearLayout) findViewById(R.id.home_back);
	}

	public void setEditChanged() {

		phone.addTextChangedListener(new TextWatcher() {

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
				int i = phone.getText().toString().trim().length();
				if (i > 0) {
					clear.setVisibility(0);
					if (i == 11) {
						activate.setBackgroundResource(drawable.btn_orange_bg);
						activate.setEnabled(true);
					} else {
						activate.setBackgroundResource(drawable.btn_gray_bg);
						activate.setEnabled(false);
					}
				} else {
					clear.setVisibility(8);

				}

			}
		});

	}

	public void setListener() {

		clear.setOnClickListener(ls);
		activate.setOnClickListener(ls);
		back.setOnClickListener(ls);

	}

	private OnClickListener ls = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.home_clear:
				phone.setText("");
				break;
			case R.id.home_activate:
				char one = phone.getText().toString().charAt(0);
				char two = phone.getText().toString().charAt(1);
				String on = one + "";
				String tw = two + "";
				// Toast.makeText(getApplicationContext(), one+"", 1).show();
				if (on.contains("1") && "358".contains(tw)) {
					Toast.makeText(getApplicationContext(), "请注意查收短信！",
							Toast.LENGTH_SHORT).show();

					startActivity(new Intent(ActivationActivity.this,
							IdentifyActivity.class));
				} else {
					Toast.makeText(getApplicationContext(), "请正确输入手机号码！",
							Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.home_back:

				finish();
				break;

			}

		}
	};
	
}
