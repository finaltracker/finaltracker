package com.zdn.cropimage;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;


import com.zdn.util.BitmapUtils;
import com.zdn.util.FileUtil;

import java.io.File;

public class CropHelper {
	// SD卡不存在
	public static final String SDCARD_NOT_EXISTS = "SD卡不存在，无法拍照";

	// 设置头像
	public static final int HEAD_FROM_ALBUM = 2106;
	public static final int HEAD_FROM_CAMERA = 2107;
	public static final int HEAD_SAVE_PHOTO = 2108;
	public static final int COMMON_INPUT_REQUEST_ID = 2019;

	// 头像设置提示
	public static final String HEAD_SET_CANCEL = "取消拍照";
	public static final String HEAD_UPLOAD_SUCCESS = "图片上传成功";
	public static final String HEAD_IMAGE_INVALID = "无效图片";
	public static final String UPLOAD_HEAD = "上传图片";

	private Fragment mFragment = null;
	private Activity m_ac = null;

	// 照片保存路径
	private String mTempPhotoPath;

	public CropHelper(Fragment act, Activity ac , String photoPath) {
		mFragment = act;
		m_ac = ac;
		mTempPhotoPath = photoPath;
	}

	public Bitmap getBitmap(Intent data) {
		if (data == null) {
			return null;
		}
		return data.getParcelableExtra("data");
	}

	/**
	 * 开始裁剪
	 * 
	 */
	public void startPhotoCrop(Uri uri, String duplicatePath, int reqCode, boolean mBWPhoto) {

		Intent intent = new Intent();
		if( mFragment != null ) {
			intent.setClass(mFragment.getActivity(), CropImage.class);
		}
		else
		{
			intent.setClass( m_ac ,CropImage.class);
		}
		intent.setDataAndType(uri, "image/*");

		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("scale", true);
		intent.putExtra("scaleUpIfNeeded", true);
		intent.putExtra("BWPhoto", mBWPhoto);
		if( mFragment != null )
		{
			mFragment.startActivityForResult(intent, reqCode);
		}
		else
		{
			m_ac.startActivityForResult(intent, reqCode );
		}
		
	}

	public void startCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 下面这句指定调用相机拍照后的照片存储的路径
		File temp = new File(mTempPhotoPath);
		if (temp.exists()) {
			temp.delete();
		}
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(temp));
		if (mFragment != null) {
			mFragment.startActivityForResult(intent, HEAD_FROM_CAMERA);
		}
		else
		{
			m_ac.startActivityForResult(intent, HEAD_FROM_CAMERA);
		}
	}

	public void startAlbum() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		if (mFragment != null) {
		mFragment.startActivityForResult(intent, HEAD_FROM_ALBUM);
		}
		else
		{
			m_ac.startActivityForResult(intent, HEAD_FROM_ALBUM);
		}
	}

	public void showToast(String msg) {
		if (mFragment != null) {
			Toast.makeText(mFragment.getActivity(), msg, Toast.LENGTH_SHORT).show();

		}
		else
		{
			Toast.makeText(m_ac, msg, Toast.LENGTH_SHORT).show();
		}

	}

	public void getDataFromCamera(Intent data) {
		File temp = new File(mTempPhotoPath);
		if (!temp.exists()) {
			showToast(HEAD_SET_CANCEL);
		} else {
			Uri uri = null;
			try {
				uri = Uri.parse(mTempPhotoPath);
				startPhotoCrop(uri, null, HEAD_SAVE_PHOTO ,false);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * 调用相机拍照
	 * @param data
	 * @param mBWphoto 是否为黑白照片
	 */
	public void getDataFromCamera(Intent data,boolean mBWphoto) {
		File temp = new File(mTempPhotoPath);
		if (!temp.exists()) {
			showToast(HEAD_SET_CANCEL);
		} else {
			Uri uri = null;
			try {
				uri = Uri.parse(mTempPhotoPath);
				startPhotoCrop(uri, null, HEAD_SAVE_PHOTO, mBWphoto );
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public void getDataFromAlbum(Intent data) {
		if (data == null) {
			showToast(HEAD_SET_CANCEL);
		} else {
			Uri uri = null;
			uri = data.getData();
			if (uri != null) {
				startPhotoCrop(uri, null, CropHelper.HEAD_SAVE_PHOTO,false);
			}
		}
	}

	/**
	 * 截图保存为临时文件
	 * 
	 * @param data
	 * @return
	 */
	public boolean saveTempPhoto(Intent data) {
		if (data != null) {
			return BitmapUtils.saveFile(getBitmap(data), mTempPhotoPath);
		} else {
			showToast(HEAD_IMAGE_INVALID);
			return false;
		}
	}

	/**
	 * 截图保存到指定目录
	 * 
	 * @param data
	 * @return
	 */
	public boolean savePhoto(Intent data, String filePath) {
		if (data != null) {
			return BitmapUtils.saveFile(getBitmap(data), filePath);
		} else {
			showToast(HEAD_IMAGE_INVALID);
			return false;
		}
	}
	
	/**
	 * 复制临时路径下的截图文件到指定路径
	 * 
	 * @param filePath
	 * @return
	 */
	public boolean copyTempPhoto(String filePath) {
		return FileUtil.copyFile(mTempPhotoPath, filePath);
	}

	/**
	 * 获取截图存放的临时路径
	 * 
	 * @return
	 */
	public String getTempPath() {
		return mTempPhotoPath;
	}
}
