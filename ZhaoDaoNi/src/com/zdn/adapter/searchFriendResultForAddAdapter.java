package com.zdn.adapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zdn.R;

import com.zdn.activity.MainControl;
import com.zdn.activity.PeopleActivity;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.basicStruct.friendTeamDataManager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class searchFriendResultForAddAdapter extends BaseAdapter{
	private friendTeamDataManager teams = null;;
	private Context mContext;
	
	
	public searchFriendResultForAddAdapter(Context mContext ,friendTeamDataManager teams ) {
		this.mContext = mContext;
		this.teams =  teams;

	}
	
	/**
	 * 当ListView数据发生变化�?调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(friendTeamDataManager teams ){
		this.teams = teams;
		notifyDataSetChanged();
	}

	public int getCount() {
		int count = 0 ;
		for( int i = 0 ; i < teams.getTeamNum() ; i ++ )
		{
			count += teams.getMemberNumInTeam(i);
		}
		return count;
	}

	public Object getItem(int position) {
		
		Object ret = null;
		for( int i = 0 ; i < teams.getTeamNum() ; i ++ )
		{
			if( ( position - teams.getMemberNumInTeam(i))  < 0 )
			{
				ret = teams.getMemberData(i, position);
				break;
			}
			else
			{
				position -= teams.getMemberNumInTeam(i) ;
			}
			
		}
		
		return ret;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		final ViewHolder viewHolder ;
		
		int location = position;
		friendMemberData md = null;
		
		for( int i = 0 ; i < teams.getTeamNum( ); i ++ )
		{
			if( ( location - teams.getMemberNumInTeam(i) ) < 0 )
			{
				md = teams.getMemberData(i, location);
				break;
			}
			else
			{
				location -= teams.getMemberNumInTeam(i);
			}
			
		}
		
		assert(md != null );

		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.search_for_for_add_friend_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.majia = (ImageView) view.findViewById(R.id.PersonIcon);
			viewHolder.phoneNumner = (TextView) view.findViewById(R.id.phoneNumber);
			viewHolder.AddFriend = (ImageView) view.findViewById(R.id.AddFriend );
			
			viewHolder.AddFriend.setOnClickListener(new View.OnClickListener() {
	        	public void onClick(View v) {
	        		
	        		MainControl.addA_Friend( viewHolder.phoneNumner.getText().toString(),"some information!");
	        		   
	        	}
	        	});
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		if( md.phoneNumber == null ){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(md.memberName );
			viewHolder.tvTitle.setVisibility(View.GONE);
			viewHolder.phoneNumner.setVisibility(View.GONE);
			viewHolder.AddFriend.setVisibility(View.GONE);		
			viewHolder.majia.setVisibility(View.GONE);
			
			
		}else{

			viewHolder.tvLetter.setVisibility(View.GONE);
			
			viewHolder.tvTitle.setText(md.memberName );
			viewHolder.tvTitle.setText(View.GONE);
			viewHolder.phoneNumner.setText(View.GONE);
			//viewHolder.AddFriend.setImageBitmap()
			//viewHolder.AddFriend.setImageBitmap( );
		
		}
	
		return view;

	}
	
	


	final static class ViewHolder {
		TextView tvLetter;
		ImageView majia;
		TextView tvTitle;
		TextView phoneNumner;
		ImageView AddFriend;
		
	}



}