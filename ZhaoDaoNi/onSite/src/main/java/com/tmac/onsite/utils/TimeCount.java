/**
 * 
 */
package com.tmac.onsite.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * @author linsen
 * @date 2016年6月6日
 */
public class TimeCount extends CountDownTimer{

	private TextView getcode;
	
	/**
	 * @param millisInFuture
	 * @param countDownInterval
	 */
	public TimeCount(long millisInFuture, long countDownInterval, TextView getcode) {
		super(millisInFuture, countDownInterval);
		this.getcode=getcode;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onFinish() {
		getcode.setText("重新获取");
		getcode.setEnabled(true);
		getcode.setTextColor(Color.parseColor("#FF6633"));
	}

	@Override
	public void onTick(long millisUntilFinished) {
		getcode.setText("正在获取"+"("+millisUntilFinished / 1000 + ")");
		getcode.setEnabled(false);
		getcode.setTextColor(Color.parseColor("#ffffff"));
	}

}
