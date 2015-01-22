package com.zdn.adapter;

import java.util.List;

import com.zdn.R;
import com.zdn.activity.MainControl;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.basicStruct.friendMemberDataBasic;
import com.zdn.basicStruct.friendTeamData;
import com.zdn.basicStruct.friendTeamDataManager;
import com.zdn.view.FriendListView;
import android.content.Context;
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
	private static final String verifingFriend = "待验证好友";
	private friendTeamDataManager teams = null;

	

	public FriendListAdapter(Context context, 
			FriendListView mIphoneTreeView, View searchView) {
		this.mContext = context;


	}

	public Object getChild(int groupPosition, int childPosition) {

		return teams.getMemberData( groupPosition , childPosition );
		 
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public int getChildrenCount(int groupPosition) {
		return teams.getMemberNumInTeam(groupPosition);
		
	}

	public Object getGroup(int groupPosition) {
		
		return teams.getTeamData(groupPosition);
	}

	public int getGroupCount() {
		return teams.getTeamNum();
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
		ChildHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.fragment_constact_child, null);
			holder = new ChildHolder();
			holder.nameView = (TextView) convertView
					.findViewById(R.id.contact_list_item_name);
			holder.feelView = (TextView) convertView
					.findViewById(R.id.cpntact_list_item_state);
			holder.iconView = (ImageView) convertView.findViewById(R.id.icon);
			holder.acceptButton = (ImageView) convertView.findViewById(R.id.accept_button);
			
			convertView.setTag(holder);
		} else {
			holder = (ChildHolder) convertView.getTag();
		}
		
		friendTeamData ft = (friendTeamData)getGroup( groupPosition );
		if( ft.teamName.equals( verifingFriend ) )
		{
			holder.nameView.setCompoundDrawables(null,null,null,null);
			holder.acceptButton.setVisibility(View.VISIBLE );
    		final int groupIndex = groupPosition;
    		final int childIndex = childPosition;
			holder.acceptButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {


        		MainControl.addA_FriendConfirm( "1" , ((friendMemberDataBasic)getChild( groupIndex,childIndex)).phoneNumber  );
        	}
        	});


		}
		else
		{
			holder.acceptButton.setVisibility(View.GONE );
		}
		
		
		friendMemberData md = (friendMemberData)getChild(groupPosition,childPosition);

		holder.iconView.setImageBitmap(md.picture);
		 
		holder.nameView.setText( md.basic.memberName );
		holder.feelView.setText("爱生活..爱Android...");
		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.fragment_constact_group, null);
			holder = new GroupHolder();
			holder.nameView = (TextView) convertView
					.findViewById(R.id.group_name);
			holder.onLineView = (TextView) convertView
					.findViewById(R.id.online_count);
			holder.iconView = (ImageView) convertView
					.findViewById(R.id.group_indicator);
			convertView.setTag(holder);
		} else {
			holder = (GroupHolder) convertView.getTag();
		}
		holder.nameView.setText( ((friendTeamData)getGroup(groupPosition)).teamName );
		holder.onLineView.setText(getChildrenCount(groupPosition) + "/"
				+ getChildrenCount(groupPosition));
		if (isExpanded) {
			holder.iconView.setImageResource(R.drawable.qb_down);
		} else {
			holder.iconView.setImageResource(R.drawable.qb_right);
		}
		return convertView;
	}

	public void updateListView( friendTeamDataManager updateTeams )
	{
		this.teams = updateTeams;
	}

	class ChildHolder {
		TextView nameView;
		TextView feelView;
		ImageView iconView;
		ImageView acceptButton;

	}

	class GroupHolder {
		TextView nameView;
		TextView onLineView;
		ImageView iconView;

	}
	

	
}

