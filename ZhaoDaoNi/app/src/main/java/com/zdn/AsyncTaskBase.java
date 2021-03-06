package com.zdn;



import com.zdn.R;
import com.zdn.view.LoadingView;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

public class AsyncTaskBase extends AsyncTask<Integer, Integer, Integer> {
	private LoadingView mLoadingView;
	public AsyncTaskBase(LoadingView loadingView){
		this.mLoadingView=loadingView;
	}
	@Override
	protected Integer doInBackground(Integer... params) {

		return null;
	}

	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if(mLoadingView != null )
		{
			if(result==1){
		
			mLoadingView.setVisibility(View.GONE);
			}else{
				mLoadingView.setText(R.string.no_data);
			}
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(mLoadingView != null )
		{
			mLoadingView.setVisibility(View.VISIBLE);
		}
	}

}
