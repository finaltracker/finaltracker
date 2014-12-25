package com.zdn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
/*
	expandGroup (int groupPos) ;//在分组列表视图中 展开一组，
	setSelectedGroup (int groupPosition) ;//设置选择指定的组。
	setSelectedChild (int groupPosition, int childPosition, boolean shouldExpandGroup);//设置选择指定的子项。
	getPackedPositionGroup (long packedPosition);//返回所选择的组
	getPackedPositionForChild (int groupPosition, int childPosition) ;//返回所选择的子项
	getPackedPositionType (long packedPosition);//返回所选择项的类型（Child,Group）
	isGroupExpanded (int groupPosition);//判断此组是否展开
*/

public class FriendListView extends ExpandableListView implements OnGroupClickListener {
	public FriendListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		registerListener();
	}

	public FriendListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		registerListener();
	}

	public FriendListView(Context context) {
		super(context);
		registerListener();
	}
	
	/**
	 * Adapter 鎺ュ彛 . 鍒楄〃蹇呴』瀹炵幇姝ゆ帴鍙?.
	 */

	private static final int MAX_ALPHA = 255;

	private ExpandableListAdapter mAdapter;

	/**
	 * 鐢ㄤ簬鍦ㄥ垪琛ㄥご鏄剧ず鐨?View,mHeaderViewVisible 涓?true 鎵嶅彲瑙?
	 */

	private void registerListener() {
		setOnGroupClickListener(this);
	}

	@Override
	public void setAdapter(ExpandableListAdapter adapter) {
		super.setAdapter(adapter);
		mAdapter = (ExpandableListAdapter) adapter;
	}
	/*

	/**
	 * 
	 * 鐐瑰嚮浜?Group 瑙﹀彂鐨勪簨浠?, 瑕佹牴鎹牴鎹綋鍓嶇偣鍑?Group 鐨勭姸鎬佹潵
	 */
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		if(parent.isGroupExpanded(groupPosition)){
            parent.collapseGroup(groupPosition);
        }
		else
		{
//第二个参数false表示展开时是否触发默认滚动动画
            parent.expandGroup(groupPosition,false);
            //parent.smoothScrollToPosition (groupPosition);


		}
        //telling the listView we have handled the group click, and don't want the default actions.
        return true;

	
	}


}
