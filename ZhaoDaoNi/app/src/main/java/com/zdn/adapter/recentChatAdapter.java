package com.zdn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.zdn.R;
import com.zdn.basicStruct.friendMemberData;
import com.zdn.basicStruct.friendTeamData;
import com.zdn.chat.ZdnMessage;
import com.zdn.data.dataManager;
import com.zdn.internet.InternetComponent;
import com.zdn.util.FileUtil;

import java.util.List;


public class recentChatAdapter extends BaseAdapter
{

	private static final String TAG = "recentChatAdapter";
	private List<friendMemberData>	ftd;   // recentChatList;
	private  Context mContext;
	private BitmapUtils bitmapUtils = null;
	private String friendsAvatorDir ;


	public recentChatAdapter(Context context,List<friendMemberData>	ftd)
	{
		super();
		this.mContext = context;
		this.ftd = ftd;
		friendsAvatorDir = FileUtil.makePath( FileUtil.getBaseDirector() , context.getString(R.string.friendsAvator) );
		bitmapUtils = new BitmapUtils(mContext , friendsAvatorDir );

	}

	 @Override
	 public int getCount() {
		 if( ftd != null )
		 {
			 return ftd.size();
		 }
		 else
		 {
			 return 0;
		 }

	 }

	 @Override
	 public Object getItem(int position) {
		 return ftd.get(position);
	 }

	 @Override
	 public long getItemId(int position) {
		 return position;
	 }

	 @Override
	 public View getView(int position, View convertView, ViewGroup parent) {
		 ChildHolder holder = null;
		 if (convertView == null) {
			 convertView = LayoutInflater.from(mContext).inflate(
					 R.layout.fragment_constact_child, null);
			 //convertView.getBackground().setAlpha(20);
			 holder = new ChildHolder();
			 holder.nameView = (TextView) convertView
					 .findViewById(R.id.contact_list_item_name);
			 holder.feelView = (TextView) convertView
					 .findViewById(R.id.cpntact_list_item_state);
			 holder.iconView = (ImageView) convertView.findViewById(R.id.icon);
			 RelativeLayout.LayoutParams paramTest = (RelativeLayout.LayoutParams) holder.iconView.getLayoutParams();
			 paramTest.leftMargin = 0;;
			 holder.iconView.setLayoutParams(paramTest);
			 holder.acceptButton = (ImageView) convertView.findViewById(R.id.accept_button);
			 holder.acceptButton.setVisibility(View.GONE);
			 convertView.setTag(holder);
		 } else {
			 holder = (ChildHolder) convertView.getTag();
		 }

		 friendMemberData md = (friendMemberData)getItem(position );


		 bitmapUtils.display( holder.iconView, InternetComponent.WEBSITE_ADDRESS_BASE_NO_SEPARATOR + md.basic.getPictureAddress());



		 holder.nameView.setText( md.basic.getNickName() );
		 List<ZdnMessage> mList = md.getMessageList();
		 if( mList.size() > 0 )
		 {
			 String theLastMessage = mList.get(mList.size()-1).getContent();
			 holder.feelView.setText(theLastMessage);
		 }
		 else
		 {
			 holder.feelView.setText("");
		 }

		 convertView.setTag(R.id.INDEX_IN_ALL_FRIEND_LIST, dataManager.getFrilendList().findAfriendTeam( md.basic.getTeamName() ));
		 convertView.setTag(R.id.INDEX_IN_ONE_FRIEND_TEAM, dataManager.getFrilendList().getFriendTeamData( md.basic.getTeamName()).findFriendMember( ftd.get(position).basic.getPhoneNumber() ) );

		 return convertView;
	 }


class ChildHolder {
		TextView nameView;
		TextView feelView;
		ImageView iconView;
		ImageView acceptButton;

	}

}

