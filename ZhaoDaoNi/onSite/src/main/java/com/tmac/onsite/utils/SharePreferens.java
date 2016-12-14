/**
 * 
 */
package com.tmac.onsite.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 存储工具类
 * @author tmac
 */
public class SharePreferens {
	
	private Context context;
	private SharedPreferences sp;
	
	public SharePreferens(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		sp = context.getSharedPreferences("onsite", Context.MODE_PRIVATE);
	}

	/**
	 * 第一次安装此应用进入之后，存储值为以进入
	 * @param isfirstin
	 */
	public void isFirstIN(boolean isfirstin){
		sp.edit().putBoolean("isfirstin", isfirstin)
		.commit();
	}
	
	/**
	 * 判断是不是第一次进入此应用
	 * @return
	 */
	public boolean isFirstIN(){
		return sp.getBoolean("isfirstin", true);
	}
	
}
