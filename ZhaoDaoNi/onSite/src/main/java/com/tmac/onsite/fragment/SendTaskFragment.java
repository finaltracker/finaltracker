/**
 * 
 */
package com.tmac.onsite.fragment;

import java.util.ArrayList;
import java.util.List;

import com.tmac.onsite.R;
import com.tmac.onsite.utils.DimenConvert;
import com.tmac.onsite.utils.GetWH;
import com.tmac.onsite.view.PullToRefreshLayout;
import com.tmac.onsite.view.PullableListView;

import android.R.integer;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * @author tmac
 */
public class SendTaskFragment extends Fragment implements OnClickListener{

	private RadioGroup rg;
	private RadioButton rbNobegain, rbFinished, rbNofinish, rbCancle;
	private FragmentManager fm;
	private FragmentTransaction ft;
	private NoBeginFragment noBeginFragment;
	private FinishedFragment finishedFragment;
	private NoFinishedFragment noFinishedFragment;
	private CancleFragment cancleFragment;
	private List<Fragment> fragList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_send_task, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initViews(view);
		//initDatas();
		initEvents();
	}

	private void initViews(View view) {
		// TODO Auto-generated method stub
		rg = (RadioGroup) view.findViewById(R.id.send_rg);
		rbNobegain = (RadioButton) view.findViewById(R.id.no_begin_task);
		rbFinished = (RadioButton) view.findViewById(R.id.task_over);
		rbNofinish = (RadioButton) view.findViewById(R.id.task_fail);
		rbCancle = (RadioButton) view.findViewById(R.id.task_abondon);
		rbNobegain.setOnClickListener(this);
		rbFinished.setOnClickListener(this);
		rbNofinish.setOnClickListener(this);
		rbCancle.setOnClickListener(this);
		noBeginFragment = new NoBeginFragment();
		noFinishedFragment = new NoFinishedFragment();
		finishedFragment = new FinishedFragment();
		cancleFragment = new CancleFragment();
		fragList = new ArrayList<Fragment>();
	}

	private void initDatas() {
		// TODO Auto-generated method stub
		fragList.add(noBeginFragment);
		fragList.add(noFinishedFragment);
		fragList.add(finishedFragment);
		fragList.add(cancleFragment);
	}
	
	private void initEvents() {
		// TODO Auto-generated method stub
		setSelectedTab(rbNobegain);
		fragList.add(noBeginFragment);
		fm = getActivity().getSupportFragmentManager();
		ft = fm.beginTransaction();
		ft.add(R.id.fl_send_task, noBeginFragment);
		ft.commit();
	}

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.no_begin_task:
			setSelectedTab(rbNobegain);
			setFragment(noBeginFragment, 0);
			break;
		case R.id.task_over:
			setSelectedTab(rbFinished);
			setFragment(finishedFragment, 1);
			break;
		case R.id.task_fail:
			setSelectedTab(rbNofinish);
			setFragment(noFinishedFragment, 2);
			break;
		case R.id.task_abondon:
			setSelectedTab(rbCancle);
			setFragment(cancleFragment, 3);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 设置显示的Fragment
	 * @param fragment
	 * @param position
	 */
	private void setFragment(Fragment fragment, int position){
		ft = fm.beginTransaction();
		if (fragment.isAdded()) {
			ft.show(fragment);
			hideFragment(position);
		} else {
			fragList.add(position, fragment);
			ft.add(R.id.fl_send_task, fragment);
			hideFragment(position);
		}
		ft.commit();
	}
	
	/**
	 * 隐藏Fragment
	 * @param position
	 */
	private void hideFragment(int position){
		for(int i = 0; i < fragList.size(); i++){
			if(i != position){
				ft.hide(fragList.get(i));
			}
		}
	}
	
	/**
	 * 设置RadioButton选中状态和下划线的显示
	 * @param rb
	 */
	private void setSelectedTab(RadioButton rb){
		Drawable drawable = getActivity().getResources().getDrawable(R.drawable.rb_shape);
		int screenWidth = GetWH.getWindowWidthExcept(getActivity().getWindowManager());
		int width = screenWidth / 4;
		drawable.setBounds(0, 0, width, DimenConvert.dipToPx(getActivity(), 2));
		rb.setSelected(true);
		rb.setCompoundDrawables(null, null, null, drawable);
		int size = rg.getChildCount();
		for (int i = 0; i < size; i++) {
			if(rg.getChildAt(i).getId() != rb.getId()){
				rg.getChildAt(i).setSelected(false);
				((RadioButton)rg.getChildAt(i)).setCompoundDrawables(null, null, null, null);
			}
		}
	}
	
}
