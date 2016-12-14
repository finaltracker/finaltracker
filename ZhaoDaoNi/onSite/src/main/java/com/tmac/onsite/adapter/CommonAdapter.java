/**
 * 
 */
package com.tmac.onsite.adapter;

import java.util.List;
import java.util.zip.Inflater;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author tmac
 * @param <T>
 */
public abstract class CommonAdapter<T> extends BaseAdapter {

	protected Context mContext;
	protected LayoutInflater inflater; 
	protected List<T> list;
	
	public CommonAdapter(Context context, List<T> list) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.list = list;
		this.inflater = (LayoutInflater) mContext.
				getSystemService(Service.LAYOUT_INFLATER_SERVICE);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list.size() > 0 ? this.list.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	

}
