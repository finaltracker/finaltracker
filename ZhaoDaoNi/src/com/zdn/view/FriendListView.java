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
	expandGroup (int groupPos) ;//�ڷ����б���ͼ�� չ��һ�飬
	setSelectedGroup (int groupPosition) ;//����ѡ��ָ�����顣
	setSelectedChild (int groupPosition, int childPosition, boolean shouldExpandGroup);//����ѡ��ָ�������
	getPackedPositionGroup (long packedPosition);//������ѡ�����
	getPackedPositionForChild (int groupPosition, int childPosition) ;//������ѡ�������
	getPackedPositionType (long packedPosition);//������ѡ��������ͣ�Child,Group��
	isGroupExpanded (int groupPosition);//�жϴ����Ƿ�չ��
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
	 * Adapter 接口 . 列表必须实现此接�?.
	 */

	private static final int MAX_ALPHA = 255;

	private ExpandableListAdapter mAdapter;

	/**
	 * 用于在列表头显示�?View,mHeaderViewVisible �?true 才可�?
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
	 * 点击�?Group 触发的事�?, 要根据根据当前点�?Group 的状态来
	 */
	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		if(parent.isGroupExpanded(groupPosition)){
            parent.collapseGroup(groupPosition);
        }
		else
		{
//�ڶ�������false��ʾչ��ʱ�Ƿ񴥷�Ĭ�Ϲ�������
            parent.expandGroup(groupPosition,false);
            //parent.smoothScrollToPosition (groupPosition);


		}
        //telling the listView we have handled the group click, and don't want the default actions.
        return true;

	
	}


}
