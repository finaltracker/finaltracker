/**
 * 
 */
package com.tmac.onsite.fragment;

import java.util.ArrayList;
import java.util.List;

import com.tmac.onsite.R;
import com.tmac.onsite.activity.DetailNoBeginActivity;
import com.tmac.onsite.adapter.NoBeginAdapter;
import com.tmac.onsite.bean.NoBeginBean;
import com.tmac.onsite.utils.RefreshUtils;
import com.tmac.onsite.view.PullToRefreshLayout;
import com.tmac.onsite.view.PullToRefreshLayout.OnRefreshListener;
import com.tmac.onsite.view.PullableListView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author tmac
 */
public class NoBeginFragment extends Fragment {
	
	private PullToRefreshLayout ptrl;
	private PullableListView plv;
	private int state;
	private List<NoBeginBean> allList;
	private NoBeginAdapter adapter;
	private Handler myHandler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {	
			case RefreshUtils.LOAD_SUCCESS:
				if(state == 0){
					allList.clear();	
				}
				allList.addAll((List<NoBeginBean>)msg.obj);
				adapter.notifyDataSetChanged();
				RefreshUtils.getResultByState(state, ptrl, true);
				break;
			case RefreshUtils.LOAD_FAIL:
				RefreshUtils.getResultByState(state, ptrl, false);
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
		return inflater.inflate(R.layout.send_task_pull_layout, null);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initViews(view);
		initEvents();
	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		ptrl = (PullToRefreshLayout) view.findViewById(R.id.send_pull_layout);
		plv = (PullableListView) view.findViewById(R.id.send_pull_listview);
		allList = new ArrayList<NoBeginBean>();
		allList.add(new NoBeginBean("03", "CGH9867", "宝山区共康路124号万达", "2015-09-08", true));
		adapter = new NoBeginAdapter(getActivity(), allList);
	}
	
	private void initEvents() {
		// TODO Auto-generated method stub
		plv.setAdapter(adapter);
		ptrl.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				state = 0;	
				List<NoBeginBean> result = new ArrayList<NoBeginBean>();
				result.add(new NoBeginBean("02", "ABGh675", "宝山区共康路124号万达", "2016-03-12", true));
				result.add(new NoBeginBean("01", "JHK6758", "宝山区共康路125号万达", "2015-12-12", false));
				result.add(new NoBeginBean("22", "LKHIH67", "宝山区共康路126号万达", "2016-04-26", true));
				result.add(new NoBeginBean("16", "23YUIPK", "宝山区共康路127号万达", "2016-05-19", false));
				RefreshUtils.loadSucceed(result, myHandler);						
			}
			
			@Override
			public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
				// TODO Auto-generated method stub
				state = 1;
				RefreshUtils.loadFailed(myHandler);
			}
		});
		
		plv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(), DetailNoBeginActivity.class));
			}
		});
	}

	
}
