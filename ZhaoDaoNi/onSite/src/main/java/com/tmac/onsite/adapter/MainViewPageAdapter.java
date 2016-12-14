/**
 * 
 */
package com.tmac.onsite.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author tmac
 */
public class MainViewPageAdapter extends FragmentPagerAdapter {

	private List<Fragment> list;
	
	/**
	 * @param fm
	 */
	public MainViewPageAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		if(list != null){
			this.list = list;
		}
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		return this.list.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size();
	}

}
