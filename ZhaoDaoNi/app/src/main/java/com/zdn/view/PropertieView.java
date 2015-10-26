package com.zdn.view;

import java.util.ArrayList;

import com.zdn.R;
import com.zdn.adapter.LeftAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class PropertieView extends LinearLayout implements OnItemClickListener {

	private static final String TAG = PropertieView.class.getSimpleName();
	private FragmentManager fragmentManager;
	private ListView leftList;
	private LeftAdapter leftAdapter;
	
	private LinearLayout rightShow;
	private ArrayList<Fragment> contentFragment = null;
	
	public PropertieView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}
	
	public void init(Context context) {
		setOrientation(LinearLayout.HORIZONTAL);
		int five = dip2px(5);
		
		leftList = new ListView(context);
		LayoutParams listParams = new LayoutParams(dip2px(120), LayoutParams.MATCH_PARENT);
		listParams.setMargins(five, five, five, five);
		leftList.setBackgroundResource(R.drawable.bg);
		leftList.setPadding(five, five, five, five);
		leftList.setDividerHeight(five);
		leftList.setDivider(null);
		leftList.setSelector(android.R.color.transparent);
		addView(leftList, listParams);
		
		View line = new View(context);
		LayoutParams lineParams = new LayoutParams(dip2px(1), LayoutParams.MATCH_PARENT);
		lineParams.setMargins(0, five, 0, five);
		line.setLayoutParams(lineParams);
		line.setBackgroundColor(getResources().getColor(R.color.gray));
		addView(line, lineParams);
		
		rightShow = new LinearLayout(context);
		rightShow.setId( 99 );
		LayoutParams showParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
		showParams.setMargins(five, five, five, five);
		rightShow.setLayoutParams(showParams);
		rightShow.setBackgroundResource(R.drawable.bg);
		addView(rightShow, showParams);
		rightShow.setVisibility(View.GONE);
	}
	
	public void showData(FragmentManager manager, LeftAdapter adapter, ArrayList<Fragment> contents) {
		fragmentManager = manager;
		contentFragment = contents;
		
		leftAdapter = adapter;
		leftList.setAdapter(leftAdapter);
		leftList.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		if(parent == leftList) {
			boolean isNow = leftAdapter.setCurrentPosition(position);
			if(isNow) {
				leftAdapter.setCurrentPosition(-1);
				rightShow.setVisibility(View.GONE);
				leftAdapter.notifyDataSetChanged();
				return;
			} else if (rightShow.getVisibility() == View.GONE) {
				rightShow.setVisibility(View.VISIBLE);
			}
			leftAdapter.notifyDataSetChanged();
			
			Log.i(TAG, "position:"+position);
			FragmentTransaction transaction = fragmentManager.beginTransaction();
			
			for (int i = 0; i < contentFragment.size(); i++) {
				Fragment fragment = contentFragment.get(i);
				if(fragment != null && fragment.isAdded()) transaction.hide(fragment);
			}
			Fragment fragment = contentFragment.get(position);
			if(fragment != null) {
				if(!fragment.isAdded())
					transaction.add(rightShow.getId(), fragment);
				else 
					transaction.show(fragment);
			}
			transaction.commit();
		}
	}
		
	public void setContentFragment(ArrayList<Fragment> contents) {
		contentFragment = contents;
	}
	
	private int dip2px(float dpValue) {  
        final float scale = getContext().getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }
}
