package com.tmac.onsite.activity;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeRadioButton;
import cn.bingoogolapple.badgeview.BGABadgeable;
import cn.bingoogolapple.badgeview.BGADragDismissDelegate;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.tmac.onsite.R;
import com.tmac.onsite.adapter.MainViewPageAdapter;
import com.tmac.onsite.fragment.LeftMenuFragment;
import com.tmac.onsite.fragment.RobTaskFragment;
import com.tmac.onsite.fragment.SendTaskFragment;
import com.tmac.onsite.utils.FindViewById;
import com.tmac.onsite.utils.StatusBarUtil;
import com.tmac.onsite.view.DraggableFlagView;
import com.tmac.onsite.view.DraggableFlagView.OnDraggableFlagViewListener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.FeatureInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends SlidingFragmentActivity{
	
	private static final String TAG = MainActivity.class.getSimpleName();
	private ViewPager vp;
	private List<Fragment> fragmentList;
	private RadioGroup rg;
	private BGABadgeRadioButton rb_rob_task;
	private BGABadgeRadioButton rb_send_task;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*//透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);*/
		setContentView(R.layout.activity_main);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.layout_title_bg),0);
		
		// 添加SlidingMenu
		addLeftMenu();
		// 初始化View
		initViews();
		// 初始化事件
		initEvents();
		
	}

	
	private void initViews() {
		// TODO Auto-generated method stub
		rg = FindViewById.getView(this, R.id.rg);
		rb_rob_task = FindViewById.getView(this, R.id.rb_get_task);
		rb_send_task = FindViewById.getView(this, R.id.rb_send_task);
		
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new RobTaskFragment());
        fragmentList.add(new SendTaskFragment());
        
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(new MainViewPageAdapter(getSupportFragmentManager(), fragmentList));
        vp.setCurrentItem(0);
        
	}
	
	private void initEvents() {
		
		// 设置底部RadioButton中气泡提示的数量	
		rb_rob_task.showTextBadge("10");

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb_get_task:
					vp.setCurrentItem(0);
					// 隐藏提示				
					rb_rob_task.showTextBadge("12");
					break;
				case R.id.rb_send_task:
					vp.setCurrentItem(1);
					rb_rob_task.hiddenBadge();
					break;
				default:
					break;
				}
			}
		});
		
		// viewpager的PageChange事件
		vp.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// 处理与RadioGroup的关联
				BGABadgeRadioButton rb = (BGABadgeRadioButton) rg.getChildAt(position);
				rb.setChecked(true);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int position) {
				
			}
		});
		
	}

	/**
	 * 添加左边的SlidingMenu
	 */
	private void addLeftMenu() {
		// TODO Auto-generated method stub
		FrameLayout left = new FrameLayout(this);
		left.setId("LEFT".hashCode());
		setBehindContentView(left);
		getSupportFragmentManager().
		beginTransaction().
		replace("LEFT".hashCode(), new LeftMenuFragment())
		.commit();
					
		// 设置SlidingMenu的常用属性
		SlidingMenu sm = getSlidingMenu();
		// 1.设置阴影的宽度
		sm.setShadowWidth(20);
		// 2.设置阴影图片
		//sm.setShadowDrawable(R.drawable.showder);
		//3.滑动过程中透明度的控制(淡入淡出的效果)
		sm.setFadeEnabled(true);
		//4.淡入淡出的透明度效果的调整(调整透明度最小的值)
		sm.setFadeDegree(0.1f);
		//5.设置SlidingMenu距离右侧的距离大小
        sm.setBehindOffset(150);
		
	}
	
}
