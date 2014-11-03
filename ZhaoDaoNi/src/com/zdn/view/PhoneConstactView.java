package com.zdn.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.zdn.AsyncTaskBase;
import com.zdn.R;
import com.zdn.sort.CharacterParser;
import com.zdn.sort.ClearEditText;
import com.zdn.sort.PinyinComparator;
import com.zdn.sort.SideBar;
import com.zdn.sort.SideBar.OnTouchingLetterChangedListener;
import com.zdn.sort.SortAdapter;
import com.zdn.sort.SortModel;
import com.zdn.util.ConstactUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
	private SortAdapter adapter;
	private ClearEditText mClearEditText;
	private Map<String, String> callRecords;
	private LoadingView mLoadingView;

	/**
	 * 姹夊瓧杞崲鎴愭嫾闊崇殑
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 鏍规嵁鎷奸煶鏉ユ帓鍒桳istView閲岄潰鐨勬暟鎹被
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
		// 瀹炰緥鍖栨眽瀛楄浆鎷奸煶锟�
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar.setTextView(dialog);

		// 璁剧疆鍙充晶瑙︽懜鐩戝惉
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@SuppressLint("NewApi")
			@Override
			public void onTouchingLetterChanged(String s) {
				// 璇ュ瓧姣嶉娆″嚭鐜扮殑浣嶇疆
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
				// 杩欓噷瑕佸埄鐢╝dapter.getItem(position)鏉ヨ幏鍙栧綋鍓峱osition锟�锟斤拷搴旂殑瀵硅薄
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
			callRecords = ConstactUtil.getAllCallRecords(mContext);
			result = 1;
			return result;
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 1) {
				List<String> constact = new ArrayList<String>();
				for (Iterator<String> keys = callRecords.keySet().iterator(); keys
						.hasNext();) {
					String key = keys.next();
					constact.add(key);
				}
				String[] names = new String[] {};
				names = constact.toArray(names);
				SourceDateList = filledData(names);

				// 鏍规嵁a-z杩涜鎺掑簭婧愭暟锟�
				Collections.sort(SourceDateList, pinyinComparator);
				adapter = new SortAdapter(mContext, SourceDateList);
				sortListView.setAdapter(adapter);

				mClearEditText = (ClearEditText) mBaseView
						.findViewById(R.id.filter_edit);

				// 鏍规嵁杈撳叆妗嗚緭鍏ワ拷?鐨勬敼鍙樻潵杩囨护鎼滅储
				mClearEditText.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
							int before, int count) {
						// 褰撹緭鍏ユ閲岄潰鐨勶拷?涓虹┖锛屾洿鏂颁负鍘熸潵鐨勫垪琛紝鍚﹀垯涓鸿繃婊ゆ暟鎹垪锟�
						filterData(s.toString());
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
							int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
					}
				});
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

	}

	/**
	 * 涓篖istView濉厖鏁版嵁
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(String[] date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++) {
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			// 姹夊瓧杞崲鎴愭嫾锟�
			String pinyin = characterParser.getSelling(date[i]);
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// 姝ｅ垯琛ㄨ揪寮忥紝鍒ゆ柇棣栧瓧姣嶆槸鍚︽槸鑻辨枃瀛楁瘝
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
	 * 鏍规嵁杈撳叆妗嗕腑鐨勶拷?鏉ヨ繃婊ゆ暟鎹苟鏇存柊ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 鏍规嵁a-z杩涜鎺掑簭
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

}
