package com.zdn.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zdn.R;
import com.zdn.adapter.BallFragmentAdapter;
import com.zdn.adapter.recentChatAdapter;
import com.zdn.basicStruct.getBallAllRspEvent;
import com.zdn.basicStruct.networkStatusEvent;
import com.zdn.basicStruct.timeSpaceBallDetail;
import com.zdn.data.dataManager;
import com.zdn.logic.MainControl;

import de.greenrobot.event.EventBus;

public class BallFragment extends Fragment{

	View view;
	ListView	ballList;
	BallFragmentAdapter ballListAdapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.ball_fragment, null);
		ballList = (ListView)(view.findViewById(R.id.ballList)) ;

		ballListAdapter = new BallFragmentAdapter( this.getActivity() ,null);//init timeSpaceBallDetail list is null;update will new infors comming
		ballList.setAdapter(ballListAdapter);
		//ballListAdapter.notifyDataSetChanged();
		EventBus.getDefault().register(this);
		MainControl.getBallAll("3", null);
		ballList.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
					// 当不滚动时
					case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
						// 判断滚动到底部
						if (view.getLastVisiblePosition() >= (view.getCount()-2 )) {
							//TODO
							MainControl.getBallAll( "3", null);
						}
						break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
			}
		});
		return view;
	}

	@Override
	public void onDestroyView() {
		EventBus.getDefault().unregister(this);
		super.onDestroyView();
	}

	public void onEvent(Object event) {

		if( event instanceof getBallAllRspEvent)
		{
			getBallAllRspEvent e = (getBallAllRspEvent)event;

			List<timeSpaceBallDetail> tsbdl = e.getBallList();

			ballListAdapter.updateListView( tsbdl );
		}


	}

}
