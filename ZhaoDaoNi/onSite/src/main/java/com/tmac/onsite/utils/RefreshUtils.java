package com.tmac.onsite.utils;

import android.os.Handler;



import java.util.List;

import com.tmac.onsite.view.PullToRefreshLayout;

public class RefreshUtils {
	
	public static final int LOAD_SUCCESS = 0;
	public static final int LOAD_FAIL = 1;
	
	/**
	 * 通知用户数据加载成功
	 * 
	 * @param result
	 */
	public static void loadSucceed(List<?> result,Handler handler) {
		if (result != null && !result.isEmpty()) {
			handler.sendMessageDelayed(handler.obtainMessage(LOAD_SUCCESS, result), 3000);
			//handler.sendMessage(handler.obtainMessage(10, result));
		}else {
			loadFailed(handler);
		}
	}

	/**
	 * 通知用户数据加载失败
	 */
	public static void loadFailed(Handler handler) {
		handler.sendMessageDelayed(handler.obtainMessage(LOAD_FAIL), 3000);
		//handler.sendMessage(handler.obtainMessage(20));
		return;
	}

	/**
	 * 根据是否有数据来获得刷新状态
	 * 
	 * @param state
	 *            标识是刷新0还是加载1
	 * @param ptrl
	 *            PullToRefreshLayout控件
	 * @param hasMoreData
	 *            标识是否加载或者刷新成功
	 */
	public static void getResultByState(int state, PullToRefreshLayout ptrl,
			boolean hasMoreData) {
		if (hasMoreData) {
			if (state == 0) {
				ptrl.refreshFinish(PullToRefreshLayout.SUCCEED);
			} else if(state==1){
				ptrl.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			}
		} else {
			if (state == 0) {
				ptrl.refreshFinish(PullToRefreshLayout.FAIL);
			} else if(state==1){
				ptrl.loadmoreFinish(PullToRefreshLayout.FAIL);
			}
		}
	}
}