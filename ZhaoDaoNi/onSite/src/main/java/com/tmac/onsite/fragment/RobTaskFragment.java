/**
 * 
 */
package com.tmac.onsite.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tmac.onsite.R;
import com.tmac.onsite.activity.DetailTaskActivity;
import com.tmac.onsite.adapter.RobAdapter;
import com.tmac.onsite.bean.RobBean;
import com.tmac.onsite.utils.FindViewById;
import com.tmac.onsite.utils.RefreshUtils;
import com.tmac.onsite.view.PullToRefreshLayout;
import com.tmac.onsite.view.PullableListView;
import com.tmac.onsite.view.PullToRefreshLayout.OnRefreshListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author tmac
 */
public class RobTaskFragment extends Fragment {
	
	private PullToRefreshLayout pull_layout;
	private PullableListView pull_listview;
	private List<RobBean> allList;
	private RobAdapter adapter;
	private int state;
	private Handler myHandler = new Handler(){
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch (msg.what) {
            //成功加载
            case RefreshUtils.LOAD_SUCCESS:
                if (state == 0) {
                    allList.clear();
                }
                allList.addAll((List<RobBean>) msg.obj);
                adapter.notifyDataSetChanged();
                RefreshUtils.getResultByState(state, pull_layout, true);
                break;
            //加载失败
            case RefreshUtils.LOAD_FAIL:
                RefreshUtils.getResultByState(state, pull_layout, false);
                break;
                	
            default:
                break;
        }
		};
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_rob_task, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initViews(view);
		initDatas();
		initEvents();
	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		pull_layout = (PullToRefreshLayout) view.findViewById(R.id.ptrl);
		pull_listview = (PullableListView) view.findViewById(R.id.plv);
		allList = new ArrayList<RobBean>();
		adapter = new RobAdapter(getActivity(), allList);
	}
	
	private void initDatas() {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy,MM,dd");
        String date = format.format(new Date());
		allList.add(new RobBean("CJG23423", "上海市宝山区", date));
		allList.add(new RobBean("CJG78787", "上海市宝山区", date));
		allList.add(new RobBean("CJG09090", "上海市宝山区", date));
	}
	
	private void initEvents() {
		// TODO Auto-generated method stub
		pull_listview.setAdapter(adapter);
		pull_layout.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				state=0;
				List<RobBean> result = new ArrayList<RobBean>();
                RobBean n = new RobBean("HGJ98989", "上海市浦东西区", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                result.add(n);
                RefreshUtils.loadSucceed(result, myHandler);
			}
			
			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				state = 1;
				RefreshUtils.loadFailed(myHandler);
			}
		});
		
		pull_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), DetailTaskActivity.class));
			}
		});
		
	}
	
}
