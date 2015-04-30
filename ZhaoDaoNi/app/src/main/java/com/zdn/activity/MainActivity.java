package com.zdn.activity;


import java.util.LinkedHashSet;
import java.util.Set;

import com.qq.test.SDManager;
import com.zdn.R;
import com.zdn.CommandParser.CommandE;
import com.zdn.CommandParser.Property;
import com.zdn.data.dataManager;
import com.zdn.event.EventDefine;
import com.zdn.fragment.NavigationDrawerFragment;
import com.zdn.jpush.ExampleUtil;
import com.zdn.logic.MainControl;


import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	MainControl control ;
	

	private CharSequence mTitle;
	public static boolean isForeground = false;
	
	public MainActivity()
	{
		me = this; // 
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getActionBar().setDisplayShowTitleEnabled(false);
		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, 
				R.array.plannets_array, android.R.layout.simple_spinner_dropdown_item);
                getActionBar().setListNavigationCallbacks(mSpinnerAdapter, new DropDownListenser());
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		
		//for test
			SDManager manager = new SDManager(this);
			manager.moveUserIcon();

		control = new MainControl("MainControl" , this );
		this.setTag( dataManager.self.getImsi());
		control.start();

		registerMessageReceiver();  // used for receive msg
		
	}
	static public MainActivity getInstance() { return me; }
	@Override
	//list item clicked 
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		/*
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
		*/
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, 
				R.array.plannets_array, android.R.layout.simple_spinner_dropdown_item);
		actionBar.setListNavigationCallbacks(mSpinnerAdapter, new DropDownListenser());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
    public boolean onPrepareOptionsMenu(Menu menu){
     
     super.onPrepareOptionsMenu(menu);
     /*
     MenuItem removeItem=menu.findItem(REMOVE_TODO);
     removeItem.setTitle(removeTitle);
     
     //÷ª”–µ±‘⁄ÃÌº”µƒ◊¥Ã¨œ¬£®addingNew=true£©ªÚ’ﬂListView±ªselectedµƒ«Èøˆœ¬REMOVE_TODO≤Àµ•œÓ≤≈ø…º˚
     removeItem.setVisible(addingNew||idx>-1);
     */
     return true;
     
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}
	
	
	@Override
	protected void onDestroy() {

		unregisterReceiver(mMessageReceiver);
		if(control!= null)
		{
			control.quit();
		}
		
		super.onDestroy();
	}
	
	
	
	HandlerThread uIhandlerThread = null ;

	
	private void setTag( String tag ){
       
		// ","ÔøΩÔøΩÔøΩÔøΩƒ∂ÔøΩÔøΩ ◊™ÔøΩÔøΩÔøΩÔøΩ Set
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
				Toast.makeText(MainActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
				return;
			}
			tagSet.add(sTagItme);
		}
		
		//ÔøΩÔøΩÔøΩÔøΩJPush APIÔøΩÔøΩÔøΩÔøΩTag
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
				{ // ÔøΩÔøΩ◊¢ÔøΩÔøΩÔøΩÔøΩÊ¥¶ÔøΩÔø?
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
              
              setCostomMsg(showMsg.toString());
			}
		}
	}
	private void setCostomMsg(String msg){
		/* 
		if (null != msgText) {
			 msgText.setText(msg);
			 msgText.setVisibility(android.view.View.VISIBLE);
        }
        */
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
   
    /**
     * ??? ActionBar.OnNavigationListener???
     */


    class DropDownListenser implements OnNavigationListener
    {
        // ?????SpinnerAdapter????¶Ã????????
        String[] listNames = getResources().getStringArray(R.array.plannets_array);

        /* ????????????????????Activity?ß÷??????????????Fragment */
        public boolean onNavigationItemSelected(int itemPosition, long itemId)
        {
            // ?????????Fragment
        	/*
            StudentInfo student = new StudentInfo();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            // ??Activity?ß÷??????ùI????????Fragment
            transaction.replace(R.id.context, student, listNames[itemPosition]);
            transaction.commit();
            */
        	if(itemPosition != 0) {
        		startActivity(new Intent(MainActivity.this, CircleActivity.class));
        	}
            return true;
        }
    }
    
	
}
