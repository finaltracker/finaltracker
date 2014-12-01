package com.zdn.adapter;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zdn.R;
import com.zdn.bean.RecentChat;
import com.zdn.util.FileUtil;
import com.zdn.util.ImgUtil;
import com.zdn.util.SystemMethod;
import com.zdn.util.ImgUtil.OnLoadBitmapListener;
import com.zdn.view.IphoneTreeView;
import com.zdn.view.IphoneTreeView.IphoneTreeHeaderAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendListAdapter extends BaseExpandableListAdapter implements
		IphoneTreeHeaderAdapter {

	private static final String TAG = "ExpAdapter";
	private Context mContext;
	private HashMap<String, List<RecentChat>> maps;
	private IphoneTreeView mIphoneTreeView;
	private View mSearchView;
	private HashMap<String, SoftReference<Bitmap>> hashMaps = new HashMap<String, SoftReference<Bitmap>>();
	private String dir = FileUtil.getRecentChatPath();

	private List<teamData> teams = null;
	// 伪数�?
	private HashMap<Integer, Integer> groupStatusMap;
	

	public FriendListAdapter(Context context, HashMap<String, List<RecentChat>> maps,
			IphoneTreeView mIphoneTreeView, View searchView) {
		this.mContext = context;
		this.maps = maps;
		this.mIphoneTreeView = mIphoneTreeView;
		groupStatusMap = new HashMap<Integer, Integer>();
		dir = FileUtil.getRecentChatPath();
		mSearchView = searchView;
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

	@Override
	
	public int getTreeHeaderState(int groupPosition, int childPosition) {
		final int childCount = getChildrenCount(groupPosition);
		if (childPosition == childCount - 1) {
			//mSearchView.setVisibility(View.GONE);
			return PINNED_HEADER_PUSHED_UP;
		} else if (childPosition == -1
				&& !mIphoneTreeView.isGroupExpanded(groupPosition)) {
			//mSearchView.setVisibility(View.VISIBLE);
			return PINNED_HEADER_GONE;
		} else {
			//mSearchView.setVisibility(View.GONE);
			return PINNED_HEADER_VISIBLE;
		}
	}

	@Override
	public void configureTreeHeader(View header, int groupPosition,
			int childPosition, int alpha) {
		((TextView) header.findViewById(R.id.group_name))
				.setText(((teamData)getGroup(groupPosition)).teamName);
		((TextView) header.findViewById(R.id.online_count))
				.setText(getChildrenCount(groupPosition) + "/"
						+ getChildrenCount(groupPosition));
	}

	@Override
	public void onHeadViewClick(int groupPosition, int status) {
		groupStatusMap.put(groupPosition, status);
	}

	@Override
	public int getHeadViewClickStatus(int groupPosition) {
		if (groupStatusMap.containsKey(groupPosition)) {
			return groupStatusMap.get(groupPosition);
		} else {
			return 0;
		}
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
		public Bitmap	picture;
	}
	//define team struct
	public class teamData{
		public	teamData(){}
		public String   teamName;
		public List<memberData>	member;
	}
}

