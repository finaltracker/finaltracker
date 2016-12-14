package com.tmac.onsite.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;
import com.tmac.onsite.R;
import com.tmac.onsite.utils.LoadLocalImageUtil;
import com.tmac.onsite.utils.ImageLoader.OnNotifyListener;
import com.tmac.onsite.utils.ImageLoader.Type;

public class SelectImgAdapter extends CommonAdapter<String>{
	
	private static final boolean DBG = true;
	private static final String TAG = "LC-SelectImgAdapter";
	private static Set<String> mSelectImg = new HashSet<String>();
	private OnTakePhotoListener mListener;
	private int mScreenWidth;
	private ImageLoader mImageLoader;
	private DisplayImageOptions options;

	public SelectImgAdapter(Context context, List<String> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
		if(DBG) Log.d(TAG, "init SelectImgAdapter construct");
		// 获取屏幕的宽度
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;
		mImageLoader = ImageLoader.getInstance();
		// 一定要调init方法,否则会出错
		mImageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
		options = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.ic_stub)
					.showImageForEmptyUri(R.drawable.ic_empty)
					.showImageOnFail(R.drawable.ic_error)
					.cacheInMemory(true)
					.cacheOnDisc(true)
					.bitmapConfig(Bitmap.Config.RGB_565)
					.build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//if(DBG) Log.d(TAG, "enter getView");
		final ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.select_gridview_item, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.gridview_img);
			holder.checkBox = (ImageButton) convertView.findViewById(R.id.checkbox_img);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(position == 0){
			holder.iv.setImageResource(R.drawable.take_photo);
			holder.checkBox.setVisibility(View.GONE);
			holder.iv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(DBG)Log.i(TAG, "start take photo");
					mListener.onTakePhoto();
				}
			});
		}else {
			//重置状态
			holder.iv.setImageResource(R.drawable.albums_bg);
			holder.checkBox.setImageResource(R.drawable.checkbox_unselect_press);
			holder.iv.setColorFilter(null);
			/**
			 * 此处设置IamgeView的宽度设为了ImageLoad类中压缩测量图片的时候准备的（gridView的numColum为3）
			 * 节省内存的使用
			 * */
			holder.iv.setMaxWidth(mScreenWidth/3);
			
			final String filePath = list.get(position);
			mImageLoader.displayImage("file://" + filePath, holder.iv, options, new ImageLoadingListener() {
				
				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					// TODO Auto-generated method stub
					
				}
			});
			holder.iv.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//已经被选择
					if(mSelectImg.contains(filePath)){
						mSelectImg.remove(filePath);
						holder.iv.setColorFilter(null);
						holder.checkBox.setImageResource(R.drawable.checkbox_unselect_press);
					}else { //未被选中
						mSelectImg.add(filePath);
						holder.iv.setColorFilter(Color.parseColor("#77000000"));
						holder.checkBox.setImageResource(R.drawable.checkbox_selected_press);
					}
					//notifyDataSetChanged();
				}
			});
			
			if(mSelectImg.contains(filePath)){
				holder.iv.setColorFilter(Color.parseColor("#77000000"));
				holder.checkBox.setImageResource(R.drawable.checkbox_selected_press);
			}
		}
		
		return convertView;
	}

	private class ViewHolder{
		
		private ImageView iv;
		private ImageButton checkBox;
		
	}
	
	public void setOnTakePhotoListener(OnTakePhotoListener listener){
		this.mListener = listener;
	}
	
	public interface OnTakePhotoListener{
		void onTakePhoto();
	}
	
	
	private static boolean isNotify = false;
	
}
