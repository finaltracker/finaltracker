package com.zdn.adapter;


import com.zdn.R;

import com.zdn.data.dataManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class friendInformationGroupAdapter extends BaseAdapter{
	
	private Context mContext;
	String     group ; // which item has been select
	
	
	public friendInformationGroupAdapter(Context mContext ,String group ) {
		this.mContext = mContext;
		this.group = group;
	}
	
	
	
	
	public String getSelectedGroup() { return group; }
	
	public int getCount() {
		
		return dataManager.getFrilendList().getTeamNum();
	}

	public Object getItem(int position) {
		
		
		return 	dataManager.getFrilendList().getTeamData( position );
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		final ViewHolder viewHolder ;
		
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.friend_information_group_item, null);
			viewHolder.groupName = (TextView) view.findViewById(R.id.groupName);
			viewHolder.select = (ImageView) view.findViewById(R.id.select);
			viewHolder.select.setOnClickListener(new View.OnClickListener() {
	        	public void onClick(View v) {
	        		
	        		//update the list
	        		if( !friendInformationGroupAdapter.this.group.equals( viewHolder.groupName.getText().toString() ))
	        		{
	        			friendInformationGroupAdapter.this.group = viewHolder.groupName.getText().toString();
	        			notifyDataSetChanged();
	        		}
	        	}
	        	});
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		if( group.equals( viewHolder.groupName.getText().toString() ))
		{
    		viewHolder.select.setVisibility(View.VISIBLE);
			
		}
		else{

			viewHolder.select.setVisibility(View.GONE);
			
		}
		
		viewHolder.groupName.setText( dataManager.getFrilendList().getTeamData( position).teamName );
		
		
		return view;

	}
	
	


	final static class ViewHolder {
		TextView  groupName;
		ImageView select;
		
		
	}



}