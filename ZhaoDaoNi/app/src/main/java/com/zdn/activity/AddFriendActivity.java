package com.zdn.activity;

import java.util.ArrayList;
import java.util.List;

import com.zdn.R;
import com.zdn.adapter.MyPageAdapter;
import com.zdn.logic.MainControl;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFriendActivity extends zdnBasicActivity  {
	
	private ViewPager pager;
	private int pageNo = 1;

	private Button[] bottomBtns = new Button[2];

	// ׼����ť��ѡ�е�ͼƬ����
	private int[] allBottomsImgs = { R.drawable.movie, R.drawable.video_store };
	// ѡ�е�ͼƬ����
	private int[] allBottomsImgsSelected = { R.drawable.movie_press,
			R.drawable.video_store_press };
	// ���ڰ���ͼƬ����
	private int[] allBottomsImgsSelecting = { R.drawable.movie,
			R.drawable.video_store };

	private List<View> allViews = new ArrayList<View>();
	
	EditText searchContext = null ;
	Button	 searchButton = null;
	
	public AddFriendActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.add_friend);
		setupViews();
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	private void setupViews() {
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setOffscreenPageLimit(2);
		
		pageNo = pager.getCurrentItem();
		
		allViews.clear();


        View VideoView = LayoutInflater.from(this).inflate(
                R.layout.add_friend_detail, null);
        //initVideoView(VideoView);
        allViews.add(VideoView);
        
        searchContext = (EditText) VideoView.findViewById(R.id.add_friend_detail_serach_context);
		searchButton = (Button) VideoView.findViewById(R.id.AddFriendDetailSearch);
		searchButton.setOnClickListener( new View.OnClickListener() {
        	public void onClick(View v) {
        		String text = searchContext.getText().toString() ;
        		if(!text.isEmpty())
        		{
        			startActivity( new Intent("com.zdn.activity.searchFriendResultForAddActivity.ACTION") );
        			MainControl.searchFirendOrCircle( text );
        		}
        		
        	}
        	});
        
        View detailItemView =  VideoView.findViewById(R.id.add_friend_detail_add_contract );

		
        detailItemView.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {

        		startActivity( new Intent("com.zdn.activity.ContactActivity.ACTION") );
        	}
        	});
        
        detailItemView =  VideoView.findViewById(R.id.add_friend_detail_add_webchat );
        detailItemView.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText( AddFriendActivity.this, "���΢�ź���",
        			     Toast.LENGTH_SHORT).show();
        	}
        	});
        
        detailItemView =  VideoView.findViewById(R.id.add_friend_detail_add_qq );
        detailItemView.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText( AddFriendActivity.this, "���QQ����",
        			     Toast.LENGTH_SHORT).show();
        	}
        	});
        
        detailItemView =  VideoView.findViewById(R.id.create_circle );
        detailItemView.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Toast.makeText( AddFriendActivity.this, "����Ȧ��",
        			     Toast.LENGTH_SHORT).show();
        	}
        	});
        
        //View AppView = LayoutInflater.from(context).inflate(
        //        R.layout.add_friend_detail, null);
        //initAppView(AppView);
       // allViews.add(AppView);

       

        MyPageAdapter myPagerAdapter = new MyPageAdapter(allViews);
        pager.setAdapter(myPagerAdapter);
		Intent intent = getIntent();
		initPager(0);
		pager.setCurrentItem(intent.getIntExtra("currPage", 0), true);
	}

	public void initPager(int selectedBtn) {
		// �ֱ�ȡ����Щ���
		bottomBtns[0] = (Button) findViewById(R.id.title_movie_bt);
		bottomBtns[1] = (Button) findViewById(R.id.title_video_bt);

		// ѭ��Ϊ�ײ���ť�������
		for (int i = 0; i < bottomBtns.length; i++) {
			// ��ʱ���弸������, ��ֹiһֱ����
			final int temp = i;
			if (i == selectedBtn) {
				// ѡ�е�
				bottomBtns[i].setBackgroundResource(allBottomsImgsSelected[i]);
			} else {
				// û��ѡ�е�
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
							pageNo = temp;
							if(pageNo == 0 )
							{
								searchContext.setHint("��������Ѻ���/�ֻ���");
							}
							else
							{
								searchContext.setHint("����Ȧ�Ӻ���");
							}
							
							for( int i = 0 ; i < 2 ; i++ )
							{
								if( i == pageNo)
								{
									bottomBtns[i]
												.setBackgroundResource(allBottomsImgsSelected[i]);
										
								}
								else
								{
									bottomBtns[i]
												.setBackgroundResource(allBottomsImgs[i]);
										
								}
							}
							//Do not real do viewpage change , cause: here only on view 
							//pager.setCurrentItem(temp, true); 
						}
					}
					return false;
				}
			});
		}
	}

}
