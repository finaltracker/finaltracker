package com.zdn.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.zdn.AsyncTaskBase;
import com.zdn.R;
import com.zdn.adapter.PhoneConstactSortAdapter;
import com.zdn.sort.CharacterParser;
import com.zdn.sort.PinyinComparator;
import com.zdn.sort.SideBar;
import com.zdn.sort.SideBar.OnTouchingLetterChangedListener;
import com.zdn.sort.SortModel;
import com.zdn.util.ConstactUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PhoneConstactView extends RelativeLayout {

	
	private Context mContext;
	private View mBaseView;
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private PhoneConstactSortAdapter adapter;
	private Map<String, String> callRecords;
	private LoadingView mLoadingView;

	/**
	 * 濮瑰鐡ф潪顒佸床閹存劖瀚鹃棅宕囨畱
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 閺嶈宓侀幏濂哥叾閺夈儲甯撻崚妗砳stView闁插矂娼伴惃鍕殶閹诡喚琚�
	 */
	private PinyinComparator pinyinComparator;

	public PhoneConstactView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		
		mBaseView= LayoutInflater.from(context).inflate(R.layout.fragment_phone_constact, this);
		findView();
		init();
	}


	private void findView() {
		mLoadingView=(LoadingView) mBaseView.findViewById(R.id.loading);
		sideBar = (SideBar) mBaseView.findViewById(R.id.sidrbar);
		dialog = (TextView) mBaseView.findViewById(R.id.dialog);

		sortListView = (ListView) mBaseView
				.findViewById(R.id.country_lvcountry);
	}

	private void init() {
		// 鐎圭偘绶ラ崠鏍ㄧ溄鐎涙娴嗛幏濂哥叾閿燂拷
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar.setTextView(dialog);

		// 鐠佸墽鐤嗛崣鍏呮櫠鐟欙附鎳滈惄鎴濇儔
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@SuppressLint("NewApi")
			@Override
			public void onTouchingLetterChanged(String s) {
				// 鐠囥儱鐡уВ宥夘浕濞嗏�鍤悳鎵畱娴ｅ秶鐤�
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}
			}
		});

		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 鏉╂瑩鍣风憰浣稿焺閻⑩暆dapter.getItem(position)閺夈儴骞忛崣鏍х秼閸撳潮osition閿燂拷閿熸枻鎷锋惔鏃傛畱鐎电钖�
				// Toast.makeText(getApplication(),
				// ((SortModel)adapter.getItem(position)).getName(),
				// Toast.LENGTH_SHORT).show();
				String number = callRecords.get(((SortModel) adapter
						.getItem(position)).getName());

			}
		});

		new AsyncTaskConstact(mLoadingView).execute(0);
	}

	private class AsyncTaskConstact extends
			AsyncTaskBase {
		public AsyncTaskConstact(LoadingView loadingView) {
			super(loadingView);
		}

		@Override
		protected Integer doInBackground(Integer... params) {
			int result = -1;
			//Get All phone record from contract
			callRecords = ConstactUtil.getAllCallRecords(mContext);
			result = 1;
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 1) {
				
				SourceDateList = filledData( callRecords );

				// 閺嶈宓乤-z鏉╂稖顢戦幒鎺戠碍濠ф劖鏆熼敓锟�				
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new PhoneConstactSortAdapter(mContext, SourceDateList);
				sortListView.setAdapter(adapter);


			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	/**
	 * 娑撶瘱istView婵夘偄鍘栭弫鐗堝祦
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(Map<String, String> date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (Iterator<String> keys = date.keySet().iterator(); keys
		.hasNext();) {
		String key = keys.next();
		
		//for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(key);
			sortModel.setPhoneNumber(date.get(key)  );
			// 濮瑰鐡ф潪顒佸床閹存劖瀚鹃敓锟�			
			String pinyin = characterParser.getSelling(key);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 濮濓絽鍨悰銊ㄦ彧瀵骏绱濋崚銈嗘焽妫ｆ牕鐡уВ宥嗘Ц閸氾附妲搁懟杈ㄦ瀮鐎涙鐦�
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;

	}

	/**
	 * 閺嶈宓佹潏鎾冲弳濡楀棔鑵戦惃鍕舵嫹?閺夈儴绻冨銈嗘殶閹诡喖鑻熼弴瀛樻煀ListView
	 * 
	 * @param filterStr
	 */
	public void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())
						|| characterParser.getFristLetterSpell(name).startsWith(
								filterStr.toString())
				) 
				{
					filterDateList.add(sortModel);
				}
			}
		}

		// 閺嶈宓乤-z鏉╂稖顢戦幒鎺戠碍
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

}
