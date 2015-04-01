package com.zdn.chat;

import java.util.ArrayList;
import java.util.Map;

import com.zdn.view.PagerSlidingTabStrip.IconTabProvider;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * 表情种类对应�?pagerAdapter
 * @author 仇加�? *
 */
public class FaceCategroyAdapter extends FragmentPagerAdapter implements IconTabProvider{
	
	private OnOperationListener onOperationListener;
	
	private Map<Integer, ArrayList<String>> data;

	public FaceCategroyAdapter(FragmentManager fm) {
		super(fm);
		
	}

	
	@Override
	public int getPageIconResId(int position) {
		
//		return ICONS[position];
		return (Integer) (data.keySet().toArray()[position]);
	}

	@Override
	public int getCount() {
//		return ICONS.length;
		return data == null ? 0 : data.size();
	}
	
	@Override
	public Fragment getItem(int position) {
		FacePageFragment f = new FacePageFragment();
		f.setOnOperationListener(onOperationListener);
		Bundle b = new Bundle();
		b.putInt(FacePageFragment.ARG_POSITION, position);
		b.putStringArrayList(FacePageFragment.ARG_FACE_DATA, data.get(data.keySet().toArray()[position]));
//		b.putSerializable(FacePageFragment.ARG_LISTTENER,onOperationListener);
		f.setArguments(b);
		return f;
	}


	public OnOperationListener getOnOperationListener() {
		return onOperationListener;
	}


	public void setOnOperationListener(OnOperationListener onOperationListener) {
		this.onOperationListener = onOperationListener;
	}


	public Map<Integer, ArrayList<String>> getData() {
		return data;
	}


	public void setData(Map<Integer, ArrayList<String>> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	
	
}