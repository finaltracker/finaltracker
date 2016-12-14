package com.tmac.onsite.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.tmac.onsite.R;
import com.tmac.onsite.adapter.SelectImgAdapter;
import com.tmac.onsite.utils.StatusBarUtil;

import android.R.bool;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;

public class SelectImgActivity extends Activity implements OnClickListener{
	
	private static final boolean DBG = true;
	private static final String TAG = "LC-SelectImgActivity";
	private static final int REQUEST_CODE = 1;
	private static final int DATA_LOADED = 2;
	private String fileName;
	
	private ProgressDialog mProgressDialog;
	private GridView mGridView;
	private ImageView camView;
	private ImageView selectView;
	private ImageView cancelView;
	private SelectImgAdapter mAdapter;
	private List<File> mFiles;
	private List<String> dirList;
	private List<String> mImgList;
	private List<String> mAllList;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what == DATA_LOADED){
				mProgressDialog.dismiss();
				if(DBG) Log.d(TAG, "display image view");
				// 绑定数据到gridview中
				dataToView();
			}
		};
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_img);
		StatusBarUtil.setColor(this, getResources().getColor(R.color.layout_title_bg),0);
		
		initView();
		initDatas();
		initEvent();
		
	}
	
	

	private void dataToView() {
		// TODO Auto-generated method stub
		
		for(int i = 0; i < mFiles.size(); i++){
			mImgList = new ArrayList<String>();
			mImgList = Arrays.asList(mFiles.get(i).list());
			for (int j = 0; j < mImgList.size(); j++) {
				mAllList.add(mFiles.get(i).getAbsolutePath() + "/" + mImgList.get(j));
			}
		}
		if(DBG) Log.i(TAG, "扫描到的图片张数：" + mAllList.size());
		mAdapter = new SelectImgAdapter(this, mAllList);
		mAdapter.setOnTakePhotoListener(onTakePhotoListener);
		mGridView.setAdapter(mAdapter);
	}



	private void initView() {
		// TODO Auto-generated method stub
		mGridView = (GridView) findViewById(R.id.select_gridview);
		camView = (ImageView) findViewById(R.id.camera_iv);
		selectView = (ImageView) findViewById(R.id.select_iv);
		cancelView = (ImageView) findViewById(R.id.cancel_iv);
	}

	/**
	 * 利用ContentProvider扫描手机中的所有图片
	 * 
	 */
	private void initDatas() {
		// TODO Auto-generated method stub
		
		if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			if(DBG) Log.d(TAG, "当前存储卡不可用!");
			return;
		}
		
		mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
		
		new Thread(){
			public void run() {
				dirList = new ArrayList<String>();
				mFiles = new ArrayList<File>();
				Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				ContentResolver cr = SelectImgActivity.this.getContentResolver();
				Cursor cursor = cr.query(mImageUri, null, MediaStore.Images.Media.MIME_TYPE
						+ " = ? or " + MediaStore.Images.Media.MIME_TYPE 
						+ " = ?", new String[]
					{"image/jpeg", "image/png"},
						MediaStore.Images.Media.DATE_MODIFIED);
				
				
				while (cursor.moveToNext()) {
					String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
					File parentFile = new File(path).getParentFile();
					if(parentFile == null)
						continue;
					
					//String dirPath = parentFile.getAbsolutePath();
					
					if(parentFile.list() == null)
						continue;
					
					if(mFiles.contains(parentFile)){
						continue;
					}else {
						//dirList.add(dirPath);
						mFiles.add(parentFile);
					}
				}
				
				cursor.close();
				if(DBG) Log.d(TAG, "图片数据扫描结束");
				mHandler.sendEmptyMessage(DATA_LOADED);
			};
		}.start();
		
	}


	private void initEvent() {
		// TODO Auto-generated method stub
		selectView.setOnClickListener(this);
		cancelView.setOnClickListener(this);
		mAllList = new ArrayList<String>();
		// 防止由于第一张照片导致Adapter getCount不对
		mAllList.add("null");
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.select_iv:
			
			break;
		case R.id.cancel_iv:
			// 删除当前显示的照片
			
			break;

		default:
			break;
		}
	}
	
	private SelectImgAdapter.OnTakePhotoListener onTakePhotoListener = new SelectImgAdapter.OnTakePhotoListener() {
		
		@Override
		public void onTakePhoto() {
			// TODO Auto-generated method stub
			if(DBG) Log.d(TAG, "onTakePhoto");
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
			File file = createFileIfNeed(fileName);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);
			startActivityForResult(intent, REQUEST_CODE);
		}
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){
			if(DBG) Log.d(TAG, "camera return data");
			
			if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
				return;
			// 显示原图到ImageView
			camView.setImageBitmap(readPic());
			selectView.setVisibility(View.VISIBLE);
			cancelView.setVisibility(View.VISIBLE);
			
		}
		
	};
	
	/**
	 * 从保存原图的地址中读取原图
	 * @return
	 */
	private Bitmap readPic(){
		String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/hanwei/" + fileName;
		Bitmap bitmap = BitmapFactory.decodeFile(filePath);
		return bitmap;
	}
	
	/**
	 * 创建拍照图片存储的路径
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private File createFileIfNeed(String fileName){
		
		String dirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/hanwei";
		if(DBG) Log.i(TAG, "dirPath = " + dirPath);
		File fileDir = new File(dirPath);
		if(!fileDir.exists()){
			fileDir.mkdirs();
		}
		
		File file = new File(fileDir, fileName);
		
		try {
			if(!file.exists()){
				file.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	
}
