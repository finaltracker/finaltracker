package com.zdn.fragment;

import java.util.ArrayList;
import java.util.List;

import com.zdn.R;
import com.zdn.adapter.MyPageAdapter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;

public class AddFriendfragment extends Fragment {
	private View mView;
	private ViewPager pager;
	private int pageNo = 1;

	private Button[] bottomBtns = new Button[2];

	// 准备按钮不选中的图片数组
	private int[] allBottomsImgs = { R.drawable.movie, R.drawable.video_store };
	// 选中的图片数组
	private int[] allBottomsImgsSelected = { R.drawable.movie_press,
			R.drawable.video_store_press };
	// 正在按的图片数组
	private int[] allBottomsImgsSelecting = { R.drawable.movie,
			R.drawable.video_store };

	private List<View> allViews = new ArrayList<View>();
	
	public AddFriendfragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mView = inflater.inflate(R.layout.add_friend, null);

		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupViews();
	}

	private void setupViews() {
		pager = (ViewPager) mView.findViewById(R.id.pager);
		pager.setOffscreenPageLimit(2);

		pageNo = pager.getCurrentItem();

		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				// 璋冩暣澶撮儴鐨勬枃瀛?
				// 璋冩暣搴曢儴鎸夐挳鐨勫浘鐗囨樉绀?
				pageNo = arg0;
				for (int i = 0; i < bottomBtns.length; i++) {
					if (i == arg0) {
						bottomBtns[i]
								.setBackgroundResource(allBottomsImgsSelected[i]);
					} else {
						bottomBtns[i].setBackgroundResource(allBottomsImgs[i]);
					}
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}
		});
		allViews.clear();

		Context context = getActivity();
        View VideoView = LayoutInflater.from(context).inflate(
                R.layout.add_friend_detail, null);
        //initVideoView(VideoView);
        allViews.add(VideoView);

        View AppView = LayoutInflater.from(context).inflate(
                R.layout.add_friend_detail, null);
        //initAppView(AppView);
        allViews.add(AppView);

       

        MyPageAdapter myPagerAdapter = new MyPageAdapter(allViews);
        pager.setAdapter(myPagerAdapter);
		Intent intent = getActivity().getIntent();
		initPager(0);
		pager.setCurrentItem(intent.getIntExtra("currPage", 0), true);
	}

	public void initPager(int selectedBtn) {
		// 分别取得这些组件
		bottomBtns[0] = (Button) mView.findViewById(R.id.title_movie_bt);
		bottomBtns[1] = (Button) mView.findViewById(R.id.title_video_bt);

		// 循环为底部按钮加入监听
		for (int i = 0; i < bottomBtns.length; i++) {
			// 临时定义几个变量, 防止i一直自增
			final int temp = i;
			if (i == selectedBtn) {
				// 选中的
				bottomBtns[i].setBackgroundResource(allBottomsImgsSelected[i]);
			} else {
				// 没有选中的
				bottomBtns[i].setBackgroundResource(allBottomsImgs[i]);
			}

			bottomBtns[i].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pageNo = temp;
					pager.setCurrentItem(temp, true);
				}
			});
			bottomBtns[temp].setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View arg0, MotionEvent event) {
					// TODO Auto-generated method stub
					{
						if (event.getAction() == MotionEvent.ACTION_DOWN) {
							bottomBtns[temp]
									.setBackgroundResource(allBottomsImgsSelecting[temp]);
						}
						if (event.getAction() == MotionEvent.ACTION_UP) {
							bottomBtns[temp]
									.setBackgroundResource(allBottomsImgsSelected[temp]);
							pageNo = temp;
							pager.setCurrentItem(temp, true);
						}
					}
					return false;
				}
			});
		}
	}

}
