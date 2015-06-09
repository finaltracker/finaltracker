package com.zdn.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zdn.R;
import com.zdn.fragment.navigationFragment;

import java.util.List;


public class navigationListDrawerItemAdapter extends BaseAdapter{

	private Context mContext;
	List< navigationListContextHolder > mListContext;
	navigationFragment.navigationChanged nc;
	int selectIndex;

	public navigationListDrawerItemAdapter(Context mContext  , List< navigationListContextHolder > listContext ,navigationFragment.navigationChanged nc , int selectIndex ) {
		this.mContext = mContext;
		mListContext = listContext;
		this.nc = nc;
		this.selectIndex = selectIndex;
	}


	public int getCount() {
		
		return mListContext.size();
	}

	public Object getItem(int position) {
		
		
		return 	mListContext.get(position);
	}

	public long getItemId(int position) {
		return position;
	}


	public View getView(final int position, View view, ViewGroup arg2) {
		final ViewHolder viewHolder ;
		
		if (view == null) {
			viewHolder = new ViewHolder();

			view = LayoutInflater.from(mContext).inflate(R.layout.navigation_list_drawer_item, null);
			viewHolder.navigationIconText = (TextView) view.findViewById(R.id.navigationIconText);
			viewHolder.navigationIcon = (ImageView) view.findViewById(R.id.navigationIcon);
			viewHolder.itemDrawer = (RelativeLayout) view.findViewById(R.id.itemDrawer);
			if(selectIndex == position )
			{
				//设置选中项
				viewHolder.itemDrawer.setBackgroundColor(mContext.getResources().getColor(R.color.gray));

			}
			viewHolder.itemDrawer.setOnClickListener(new View.OnClickListener() {
	        	public void onClick(View v) {
					nc.onItemClickNavigation( position );
	        	}
	        	});
			viewHolder.itemDrawer.setOnTouchListener(new View.OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					viewHolder.itemDrawer.setBackgroundColor(mContext.getResources().getColor(R.color.gray));
					nc.onItemClickNavigation( position );
					return false;
				}
			});
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		

		viewHolder.navigationIconText.setText(mListContext.get(position).itemName);
		viewHolder.navigationIcon.setImageDrawable(mContext.getResources().getDrawable(mListContext.get(position).itemIConId ));
		
		return view;

	}
	
	


	final static class ViewHolder {
		ImageView navigationIcon;
		TextView  navigationIconText;
		RelativeLayout itemDrawer;

		
		
	}

	static public class navigationListContextHolder{
		String 		itemName;
		Integer  	itemIConId;

		public navigationListContextHolder( String 		itemName , Integer  	itemIConId )
		{
			this.itemName = itemName;
			this.itemIConId = itemIConId;
		}
	}

}