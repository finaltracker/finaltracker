/**
 * 
 */
package com.tmac.onsite.adapter;

import java.util.List;

import com.tmac.onsite.R;
import com.tmac.onsite.bean.NoBeginBean;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author tmac
 */
public class NoBeginAdapter extends CommonAdapter<NoBeginBean> {

	public NoBeginAdapter(Context context, List<NoBeginBean> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.nobegin_list_item, parent, false);
			holder.imageView = (ImageView) convertView.findViewById(R.id.img);
			holder.timeRemin = (TextView) convertView.findViewById(R.id.tv_time);
			holder.number = (TextView) convertView.findViewById(R.id.tv_number);
			holder.area = (TextView) convertView.findViewById(R.id.tv_workarea);
			holder.finishTime = (TextView) convertView.findViewById(R.id.tv_finishtime);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(list.get(position).isRob()){
			holder.imageView.setVisibility(View.VISIBLE);
		}else {
			holder.imageView.setVisibility(View.INVISIBLE);
		}
		holder.timeRemin.setText(list.get(position).getTimeRemin());
		holder.number.setText(list.get(position).getNumber());
		holder.area.setText(list.get(position).getWorkArea());
		holder.finishTime.setText(list.get(position).getFinishTime());
		return convertView;
	}
	
	static class ViewHolder{
		public ImageView imageView;
        public TextView timeRemin;
        public TextView number;
        public TextView area;
        public TextView finishTime; 
	}
	
	/**
     * 解决ViewPager嵌套ListView，退出App报IllegalArgumentException:The observer is
     * null错的问题。
     */
    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

}
