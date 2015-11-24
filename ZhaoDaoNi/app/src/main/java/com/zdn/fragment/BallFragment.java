package com.zdn.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.zdn.R;
import com.zdn.adapter.BallFragmentAdapter;
import com.zdn.basicStruct.commonEvent;
import com.zdn.basicStruct.getBallAllRspEvent;
import com.zdn.basicStruct.timeSpaceBallDetail;
import com.zdn.data.dataManager;
import com.zdn.internal.SoundPullEventListener;
import com.zdn.logic.MainControl;
import com.zdn.view.PullToRefreshBase;
import com.zdn.view.PullToRefreshListView;

import de.greenrobot.event.EventBus;

public class BallFragment extends Fragment{

	View view;
	PullToRefreshListView mPullRefreshListView;
	BallFragmentAdapter ballListAdapter;
	List<timeSpaceBallDetail> tsbList = new ArrayList<timeSpaceBallDetail>();
	String theLastTime = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.ball_fragment, null);
		mPullRefreshListView = (PullToRefreshListView)(view.findViewById(R.id.ballList)) ;

		ballListAdapter = new BallFragmentAdapter( this.getActivity() ,null);//init timeSpaceBallDetail list is null;update will new infors comming
		mPullRefreshListView.setAdapter(ballListAdapter);
		//ballListAdapter.notifyDataSetChanged();
		mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(getActivity().getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				MainControl.getBallAll("3", theLastTime);

			}
		});

		// Add an end-of-list listener
		mPullRefreshListView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {

			@Override
			public void onLastItemVisible() {
				Toast.makeText(getActivity(), "End of List!", Toast.LENGTH_SHORT).show();
			}
		});

		/**
		 * Add Sound Event Listener
		 */
		SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(getActivity());
		soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
		soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
		soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
		mPullRefreshListView.setOnPullEventListener(soundListener);


		// Do work to refresh the list here.
		EventBus.getDefault().register(this);

		MainControl.getBallAll("3", theLastTime);



		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

	}

	@Override
	public void onDestroyView() {
		tsbList.clear();
		theLastTime = null;
		EventBus.getDefault().unregister(this);
		super.onDestroyView();
	}

	public void onEvent(Object event) {

		if( event instanceof getBallAllRspEvent)
		{
			getBallAllRspEvent e = (getBallAllRspEvent)event;

			List<timeSpaceBallDetail> tsbdl = e.getBallList();
			for ( timeSpaceBallDetail tsb:tsbdl
				 ) {
				tsbList.add( 0 , tsb );
			}

			if( tsbList.size() >0 )
			{
				timeSpaceBallDetail first = tsbList.get(0);
				if(first.getMobile().equals( dataManager.self.preferencesPara.getPhoneNumber() ))
				{
					theLastTime = first.getStartTime();
				}
				else
				{
					theLastTime = first.getEndTime();
				}
			}
			ballListAdapter.updateListView( tsbList );
			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshListView.onRefreshComplete();

		}


	}


}
