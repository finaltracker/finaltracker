package com.zdn.adapter;

import java.util.List;

import com.zdn.R;
import com.zdn.CommandParser.CommandE;
import com.zdn.CommandParser.Property;
import com.zdn.event.EventDefine;
import com.zdn.logic.MainControl;
import com.zdn.sort.SortModel;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

public class PhoneConstactSortAdapter extends BaseAdapter implements SectionIndexer{
	private List<SortModel> list = null;
	private Context mContext;
	
	public PhoneConstactSortAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * 当ListView数据发生变化?调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<SortModel> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	
	
	public View getView(final int position, View view, ViewGroup arg2) {
		final ViewHolder viewHolder ;
		final SortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.fragment_phone_constacts_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.majia = (ImageView) view.findViewById(R.id.PersonIcon);
			viewHolder.phoneNumner = (TextView) view.findViewById(R.id.phoneNumber);
			viewHolder.add = (ImageView) view.findViewById(R.id.AddFriend );
			
			viewHolder.add.setOnClickListener(new View.OnClickListener() {
	        	public void onClick(View v) {
	        		
	        		MainControl.addA_Friend( viewHolder.phoneNumner.getText().toString(),"some information!");
	   
	        	}
	        	});
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		//根据position获取分类的首字母的Char
		int section = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的 Char的位
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
	
		viewHolder.tvTitle.setText(this.list.get(position).getName());
		viewHolder.phoneNumner.setText(this.list.get(position).GetPhoneNumber() );
		//show majia 
		
		return view;

	}
	


	final static class ViewHolder {
		TextView tvLetter;
		ImageView majia;
		TextView tvTitle;
		TextView phoneNumner;
		ImageView add;
		
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii�?
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 提取英文的首字母，非英文字母�?代替�?
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}