package com.zdn.adapter;

import java.util.HashMap;
import java.util.List;

import com.zdn.R;
import com.zdn.view.FriendListView;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendListAdapter extends BaseExpandableListAdapter 
		 {

	private static final String TAG = "FriendListAdapter";
	private Context mContext;
	
	private List<teamData> teams = null;

	

	public FriendListAdapter(Context context, 
			FriendListView mIphoneTreeView, View searchView) {
		this.mContext = context;


	}

	public Object getChild(int groupPosition, int childPosition) {
		 Object ret = null;
		 
		 if( teams == null )
		 {
			 return null;
		 }
		 else
		 {
			 if( groupPosition >(teams.size() -1)   )
			 {
				 return null;
			 }
			 if( childPosition > (teams.get(groupPosition).member.size()-1) )
			 {
				 return null;
			 }
			 
			 return teams.get(groupPosition).member.get(childPosition);
		 }
		 
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
		if( groupPosition >(teams.size() -1)   )
		{
			return 0;
		}
		else
		{
			return teams.get(groupPosition).member.size();
		}
	}

	public Object getGroup(int groupPosition) {
		if( groupPosition >(teams.size() -1) )
		{
			return null;
		}
		else
		{
			return teams.get(groupPosition);
		}
	}

	public int getGroupCount() {
		return teams.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		GroupHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.fragment_constact_child, null);
			holder = new GroupHolder();
			holder.nameView = (TextView) convertView
					.findViewById(R.id.contact_list_item_name);
			holder.feelView = (TextView) convertView
					.findViewById(R.id.cpntact_list_item_state);
			holder.iconView = (ImageView) convertView.findViewById(R.id.icon);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}

		memberData md = (memberData)getChild(groupPosition,childPosition);

		holder.iconView.setImageBitmap(md.picture);
		 
		holder.nameView.setText( md.memberName );
		holder.feelView.setText("爱生�?..爱Android...");
		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		ChildHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.fragment_constact_group, null);
			holder = new ChildHolder();
			holder.nameView = (TextView) convertView
					.findViewById(R.id.group_name);
			holder.onLineView = (TextView) convertView
					.findViewById(R.id.online_count);
			holder.iconView = (ImageView) convertView
					.findViewById(R.id.group_indicator);
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		holder.nameView.setText( ((teamData)getGroup(groupPosition)).teamName );
		holder.onLineView.setText(getChildrenCount(groupPosition) + "/"
				+ getChildrenCount(groupPosition));
		if (isExpanded) {
			holder.iconView.setImageResource(R.drawable.qb_down);
		} else {
			holder.iconView.setImageResource(R.drawable.qb_right);
		}
		return convertView;
	}

	public void updateListView( List<teamData> updateTeams )
	{
		this.teams = updateTeams;
	}

	class GroupHolder {
		TextView nameView;
		TextView feelView;
		ImageView iconView;
	}

	class ChildHolder {
		TextView nameView;
		TextView onLineView;
		ImageView iconView;
	}
	//define member struct

	public class memberData
	{
		public memberData() {}
		
		public String 	memberName;
		public String   pictureAddress;
		public Bitmap	picture;
	}
	//define team struct
	public class teamData{
		public	teamData(){}
		public String   teamName;
		public List<memberData>	member;
	}
}

