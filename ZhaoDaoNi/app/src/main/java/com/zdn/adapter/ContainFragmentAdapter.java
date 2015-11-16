package com.zdn.adapter;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zdn.R;

//需要继承LeftAdapter
public class ContainFragmentAdapter extends LeftAdapter{

	private LayoutInflater layoutInflater;
	private ArrayList<String> names;

	public ContainFragmentAdapter(Context context, ArrayList<String> list) {

		layoutInflater = LayoutInflater.from(context);
		names = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return names.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return names.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if(convertView == null) {
			holder = new ViewHolder();

			convertView = layoutInflater.inflate(R.layout.adapter_fragment_contain, null);
			holder.name = (TextView) convertView.findViewById(R.id.adapter_fragment_contain_name);
			convertView.setTag(holder);
		} else holder = (ViewHolder) convertView.getTag();

		String one = names.get(position);
		if(one != null) holder.name.setText(one);

		//需要调用此方法
		super.getView(position, convertView, parent);
		return convertView;
	}

	private class ViewHolder {

		private TextView name;
	}
}
