package com.tmac.onsite.activity;

import com.tmac.onsite.R;
import com.tmac.onsite.utils.StatusBarUtil;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class BaseActivity extends Activity {
	
	protected TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
//		tv = (TextView) findViewById(R.id.title_tv);
		StatusBarUtil.setTranslucent(this, 0);
	}

}
