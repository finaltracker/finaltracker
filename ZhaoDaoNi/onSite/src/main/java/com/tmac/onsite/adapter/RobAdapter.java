/**
 * 
 */
package com.tmac.onsite.adapter;

import java.util.List;

import com.tmac.onsite.R;
import com.tmac.onsite.bean.RobBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 通用的文字列表适配器，便于扩展。
 * @author tmac
 */
public class RobAdapter extends CommonAdapter<RobBean>{

	
	public RobAdapter(Context context, List<RobBean> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.rob_listview_item, parent, false);
			holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
			holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_number.setText(list.get(position).getNumber());
		holder.tv_address.setText(list.get(position).getAddress());
		holder.tv_time.setText(list.get(position).getTime());
		return convertView;
	}

	static class ViewHolder{
		
		private TextView tv_number;
		private TextView tv_address;
		private TextView tv_time;
		
	}
	
}
