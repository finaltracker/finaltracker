package com.zdn.activity;


import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.qq.test.SDManager;
import com.zdn.R;

import com.zdn.adapter.navigationListDrawerItemAdapter;
import com.zdn.basicStruct.networkStatusEvent;
import com.zdn.com.headerCtrl;
import com.zdn.data.dataManager;
import com.zdn.fragment.MapFragment;
import com.zdn.fragment.PeopleFragment;
import com.zdn.fragment.mainActivityFragmentBase;
import com.zdn.fragment.myInfomationFragment;
import com.zdn.fragment.navigationFragment;
import com.zdn.jpush.ExampleUtil;
import com.zdn.logic.MainControl;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.greenrobot.event.EventBus;

public class MainActivity extends FragmentActivity implements navigationFragment.navigationChanged ,headerCtrl.menuStateChange {

	//for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;

	public static final String MESSAGE_RECEIVED_ACTION = "com.spirit.zdn.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	public static MainActivity me = null;
	public static final String TAG = "MainControl";
	public static final int EVENT_UI_LOG_IN_START		=	1;
	public static final int EVENT_UI_REGIST_RESULT		=	EVENT_UI_LOG_IN_START+1;
	public static final int MSG_SET_TAGS				=  EVENT_UI_REGIST_RESULT +1 ;


	private static final int INBOX	=	0;
	private static final int STARRED	=	INBOX+1;
	private static final int SENT_MAIL	=	STARRED+1;
	private static final int DRAFTS	=	SENT_MAIL+1;
	private static final int MORE_MARKERS	=	DRAFTS+1;
	private static final int TRASH	=	MORE_MARKERS+1;
	private static final int SPAM	=	TRASH+1;
	public static final int PERSONAL_INFORMATION	=	SPAM+1;
	public static final int MAIN_MAP = PERSONAL_INFORMATION+1;
    public static final int FRIEND_LIST = MAIN_MAP+1;

	public static boolean isForeground = false;
	MainControl control ;
	List<navigationListDrawerItemAdapter.navigationListContextHolder> nlch;
	private int selectFragmentIndex = 0;
	navigationFragment navigationf = null; // 记录导航fragment
	public MainActivity()
	{
		me = this; // 
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.main_activity);
		init();

		super.onCreate(savedInstanceState);

		nlch = new ArrayList<navigationListDrawerItemAdapter.navigationListContextHolder>();
		nlch.add(new navigationListDrawerItemAdapter.navigationListContextHolder("inbox", R.drawable.ic_inbox_black_24dp));
		nlch.add(new navigationListDrawerItemAdapter.navigationListContextHolder("starred", R.drawable.ic_star_black_24dp));
		nlch.add(new navigationListDrawerItemAdapter.navigationListContextHolder("sent_mail", R.drawable.ic_send_black_24dp));
		nlch.add(new navigationListDrawerItemAdapter.navigationListContextHolder("drafts", R.drawable.ic_drafts_black_24dp));
		nlch.add(new navigationListDrawerItemAdapter.navigationListContextHolder("trash", R.drawable.ic_delete_black_24dp));
		nlch.add(new navigationListDrawerItemAdapter.navigationListContextHolder("spam", R.drawable.ic_report_black_24dp));

		onItemClickNavigation(MainActivity.MAIN_MAP);

	}

	private void init()
	{

		SDManager manager = new SDManager(this);
		manager.moveUserIcon();


		control = new MainControl("MainControl" , this );
		this.setTag(dataManager.self.getImsi());
		control.start();

		registerMessageReceiver();  // used for receive msg

		EventBus.getDefault().register(this);


	}
	static public MainActivity getInstance() { return me; }



	public void updateTilteAccrodingToNetworkState( boolean connect )
	{

		View toolbar = this.findViewById(R.id.header);
		if( toolbar != null )
		{
			if(connect )
			{
				//
				toolbar.setBackgroundColor(Color.parseColor("#00BFFF"));
				//TODO setTitle(getResources().getString(R.string.titleName));

			}
			else
			{
				toolbar.setBackgroundColor(Color.parseColor("#FF0000"));
				//TODO setTitle(getResources().getString(R.string.networkNoConnect));
			}
		}
		else
		{
			Log.d( this.getClass().getSimpleName() , "updateTilteAccrodingToNetworkState toolbar = null" );
		}

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		
		FragmentManager fragmentManager = this.getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		
		int id = item.getItemId();
		switch(id)
		{
		case R.id.action_settings:
			
			break;
		case R.id.action_add:
			startActivity( new Intent("com.zdn.activity.AddFriendActivity.ACTION") );
			break;
			
		case R.id.contact_friend:
			
			startActivity( new Intent("com.zdn.activity.PeopleActivity.ACTION") );
			
			break;
		default:
			
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	public void showNavigation()
	{
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		if( navigationf == null ) {
			navigationf = new navigationFragment(this, nlch, this, selectFragmentIndex);
		}

		ft.add(R.id.simple_fragment,navigationf ).commit();


	}
	public boolean hideNavigation()
	{
		if( navigationf != null  && navigationf.isVisible() )
		{
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction ft = fragmentManager.beginTransaction();

			ft.remove(navigationf).commit();
			//navigationf = null;
			return true;
		}

		return false;
	}
	//@Override
	public void onItemClickNavigation(int position ) {
		//Toast.makeText(this, "onItemClickNavigation", Toast.LENGTH_SHORT).show();
		selectFragmentIndex = position;
		FragmentManager fragmentManager = getFragmentManager();
		hideNavigation();
		switch( position )
		{

			case MainActivity.PERSONAL_INFORMATION:
				fragmentManager
						.beginTransaction()
						.replace(R.id.simple_fragment,
								myInfomationFragment.newInstance("","") ).commit();

				break;
			case MainActivity.MAIN_MAP:
				fragmentManager
						.beginTransaction()
						.replace(R.id.simple_fragment,
								new MapFragment( this)).commit();
                break;
            case MainActivity.FRIEND_LIST:
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.simple_fragment,
                                new PeopleFragment( this )).commit();
                break;
			default:
				break;
		}

	}


	@Override
	protected void onDestroy() {

		EventBus.getDefault().unregister(this);
		unregisterReceiver(mMessageReceiver);
		if(control!= null)
		{
			control.quit();
		}
		
		super.onDestroy();
	}

	public void onEvent(Object event)
	{

		if( event instanceof networkStatusEvent )
		{
			networkStatusEvent e = (networkStatusEvent)event;

			updateTilteAccrodingToNetworkState( e.getwifiConnect() || e.getGprsConnect()  );
		}


	}
	
	HandlerThread uIhandlerThread = null ;

	
	private void setTag( String tag ){

		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
				Toast.makeText(MainActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
		}

		handler.sendMessage(handler.obtainMessage(MSG_SET_TAGS, tagSet));

	} 
	// UI update handler
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if ( msg.what == EVENT_UI_LOG_IN_START ) {
				//	
				Intent intent = new Intent(me, Zhuce.class);
				//activityVolues.isLoadOK = true;
				me.startActivity(intent);
			} 
			else if (msg.what == EVENT_UI_REGIST_RESULT) 
			{ 
				if( Zhuce.getInstance() != null )
				{ // ???????洦???
					Zhuce.getInstance().registFeedback(msg.arg1 , (String)msg.obj );
				}
			} 
			else if (msg.what == MSG_SET_TAGS )
			 {
	             Log.d("MainActivity", "Set tags in handler.");
	             JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);

			 }

		};
	};

	@Override
	protected void onResume() {

		isForeground = true;
		super.onResume();
		JPushInterface.onResume(this);
	}


	@Override
	protected void onPause() {

		isForeground = false;
		super.onPause();
		JPushInterface.onPause(this);
	}
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	@Override
	public void onMenuClick(int menuId) {
		switch( menuId )
		{
			case R.id.navigationButton:
				showNavigation();
				break;
			case R.id.back_button:
                onBackPressed();
				break;
            case R.id.friendList:
                onItemClickNavigation( MainActivity.FRIEND_LIST );
                break;
			default:
				break;
		}
	}

	@Override
	public void menuFragmentClick() {
		hideNavigation();
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
              String messge = intent.getStringExtra(KEY_MESSAGE);
              String extras = intent.getStringExtra(KEY_EXTRAS);
              StringBuilder showMsg = new StringBuilder();
              showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
              
              //if (!ExampleUtil.isEmpty(extras)) {
            	  showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
              //}

			}
		}
	}

	
	private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i(TAG, logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i(TAG, logs);
                if (ExampleUtil.isConnected(getApplicationContext())) {
                	handler.sendMessageDelayed(handler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                } else {
                	Log.i(TAG, "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                Log.e(TAG, logs);
            }
            
            ExampleUtil.showToast(logs, getApplicationContext());
        }
        
    };

	long waitTime = 2000;
	long touchTime = 0;
	@Override
	public void onBackPressed()
	{
		FragmentManager fragmentManager = this.getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		if( hideNavigation() )
		{	//试图先隐藏导航栏
			return;
		}
		else if( selectFragmentIndex != MainActivity.MAIN_MAP )
		{
			onItemClickNavigation( MainActivity.MAIN_MAP );
		}
		else
		{
			long currentTime = System.currentTimeMillis();
			if ((currentTime - touchTime) >= waitTime)
			{
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				touchTime = currentTime;
			}
			else
			{
				finish();
			}
		}

	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch(ev.getAction()){
			case MotionEvent.ACTION_DOWN: {
				if( navigationf != null &&  navigationf.isVisible()) {
					float x = ev.getRawX();

					Log.d(this.getClass().getSimpleName(), "dispatchTouchEvent getRawX" + x);

					if (x > navigationf.getFragmentWidth() ) {
						hideNavigation();
					}
				}
				break;
			}
			case MotionEvent.ACTION_UP:
				break;
			default:
				break;
			}

		return super.dispatchTouchEvent(ev);


	}
}
