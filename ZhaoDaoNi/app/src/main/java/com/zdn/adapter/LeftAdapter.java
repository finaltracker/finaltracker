package com.zdn.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zdn.R;

public abstract class LeftAdapter extends BaseAdapter {

	private int currentPosition = -1;
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView != null) {
			if(currentPosition != -1 && position == currentPosition) {
				convertView.setBackgroundResource(R.drawable.item_click_bg);
			} else convertView.setBackgroundResource(0);
		}
		return convertView;
	}
	
	public boolean setCurrentPosition(int position) {
		if(currentPosition == position) return true;
		else {
			currentPosition = position;
			return false;
		}
	}
}
