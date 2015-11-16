package com.zdn.adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zdn.R;
import com.zdn.basicStruct.timeSpaceBallDetail;

import java.util.List;


public class BallFragmentAdapter extends BaseAdapter{
	List<timeSpaceBallDetail> tsbList;
	private Context mContext;
	
	
	public BallFragmentAdapter(Context mContext ,List<timeSpaceBallDetail> tsbdList ) {
		this.mContext = mContext;
		this.tsbList =  tsbdList;

	}
	
	/**
	 * 当ListView数据发生变化�??调用此方法来更新ListView

	 */
	public void updateListView(List<timeSpaceBallDetail> tsbList ){

		this.tsbList = tsbList;
		notifyDataSetChanged();
	}

	public int getCount() {

		if( tsbList == null )
		{
			return 1;
		}
		else {
			return tsbList.size() + 1; // 包含一行标题行
		}
	}

	public Object getItem(int position) {
		
		if( position == 0 )
		{
			return null;
		}
		else
		{
			return tsbList.get(position-1);
		}

	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		final ViewHolder viewHolder ;


		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.ball_fragment_item, null);
			viewHolder.ball_owner = (TextView) view.findViewById(R.id.ball_owner);
			viewHolder.ball_send_time = (TextView) view.findViewById(R.id.ball_send_time);
			viewHolder.ball_dead_time= (TextView) view.findViewById(R.id.ball_dead_time);
			viewHolder.ball_result = (TextView) view.findViewById(R.id.ball_result);
			viewHolder.detail = (TextView) view.findViewById(R.id.detail);
			view.setTag(viewHolder);
		}
		else {
		viewHolder = (ViewHolder) view.getTag();
		}

		if( position == 0 )
		{
			viewHolder.ball_owner.setText("所有者");
			viewHolder.ball_send_time.setText("启动时间");
			viewHolder.ball_dead_time.setText("结束时间");
			viewHolder.ball_result.setText("状态");
			viewHolder.detail.setText("详情");
		}
		else
		{
			if( tsbList.size() >  ( position - 1 ))
			{
				timeSpaceBallDetail tsbd = tsbList.get(position-1);
				viewHolder.ball_owner.setText(tsbd.getMobile());
				viewHolder.ball_send_time.setText(tsbd.getStartTime());
				viewHolder.ball_dead_time.setText(tsbd.getEndTime());
				viewHolder.ball_result.setText(tsbd.getBallStatus());
			}

		}


	
		return view;

	}
	
	


	final static class ViewHolder {
		TextView ball_owner;
		TextView ball_send_time;
		TextView ball_dead_time;
		TextView ball_result;
		TextView detail;
		
	}



}