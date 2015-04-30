package com.zdn.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class MyPageAdapter extends PagerAdapter {
	
	private List<View> allViews;
	
	public MyPageAdapter(List<View> allViews) {
		this.allViews = allViews;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return allViews.size();
	}



	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeViewAt(position);
	}


	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		container.addView(allViews.get(position));
		return allViews.get(position);

	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}
	

}
