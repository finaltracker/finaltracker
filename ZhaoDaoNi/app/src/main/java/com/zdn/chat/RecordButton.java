package com.zdn.chat;

import java.io.File;
import java.io.IOException;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zdn.R;
import com.zdn.util.AudioUtil;
import com.zdn.util.FileUtil;

public class RecordButton extends Button
{
	
	private final static String TAG="RecordButton";
	private static final int MIN_INTERVAL_TIME = 2000;// 2s
	public final static int VOLUME_MAX=8;
	
	
	private VolumeViewer mVolumeViewer;
    private RelativeLayout mVolumeLay,mExitLay;
	private boolean mIsCancel=false;
	
	private String mFileName = null;
	private OnFinishedRecordListener mFinishedListerer;
	private long mStartTime;
	private Dialog mRecordDialog;

	private AudioUtil mAudioUtil;
	private ObtainDecibelThread mThread;
	private Handler mVolumeHandler;
	private int mYpositon=0;
	private Context context = null ;
	
	
	public RecordButton(Context context) 
	{
		super(context);
		this.context = context;
		init();
	}

	public RecordButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	public RecordButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
		init();
	}

	public void setSavePath( )
	{

		String FileName = context.getString(R.string.RecordVoice)+"/"+System.currentTimeMillis()+".amr";
		mFileName = FileUtil.getAppRelatePath( FileName );
	}

	public void setOnFinishedRecordListener(OnFinishedRecordListener listener)
	{
		mFinishedListerer = listener;
	}


	
	private void init() 
	{
		mVolumeHandler = new ShowVolumeHandler();
		mAudioUtil=new AudioUtil();
		int[] location = new int[2];  
        getLocationOnScreen(location);  
        mYpositon= location[1];
		setSavePath();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) 
	{

		if (mFileName == null)
			return false;

		switch (event.getAction()) 
		{
		case MotionEvent.ACTION_DOWN:
			initDialogAndStartRecord();
			mVolumeLay.setVisibility(View.VISIBLE);
			mExitLay.setVisibility(View.GONE);
			break;
			
		case MotionEvent.ACTION_UP:
			if(!mIsCancel) 
				finishRecord();
			else   
				cancelRecord();
			mIsCancel=false;
			break;
			
		case MotionEvent.ACTION_MOVE:// 当手指移动到view外面，会cancel
			
			if(event.getY()<mYpositon)
			{
				mIsCancel=false;
				mExitLay.setVisibility(View.VISIBLE);
				mVolumeLay.setVisibility(View.GONE);
			}
			
			break;
		}

		return true;
	}

	
	//创建 dialog 的contentView
	private View createContentView()
	{   
		Context context=getContext();
		LinearLayout layout=new LinearLayout(context);
		layout.setBackgroundResource(R.drawable.toolbar_up); // temp
      	int px=PxTodpUtil.dip2px(context, 25);
		
		
		//volumn 界面
		mVolumeLay=new RelativeLayout(context);
		layout.addView(mVolumeLay);
		mVolumeLay.setPadding(0, px, 0, px);
		RelativeLayout.LayoutParams rParams=new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		rParams.setMargins(px, 0, 0, 0);
		
		ImageView  phoneImg=new ImageView(context);
		phoneImg.setImageResource(R.drawable.bottom_bg);
		phoneImg.setId(getResources().getInteger(R.integer.RECORD_PHONE_IMG));
		mVolumeLay.addView(phoneImg,rParams);
		
		mVolumeViewer=new VolumeViewer(context);
	    rParams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		rParams.addRule(RelativeLayout.ALIGN_BOTTOM, getResources().getInteger(R.integer.RECORD_PHONE_IMG) );
		rParams.addRule(RelativeLayout.RIGHT_OF, getResources().getInteger(R.integer.RECORD_PHONE_IMG) );
		mVolumeLay.addView(mVolumeViewer,rParams);
		
		TextView recordHint=new TextView(context);
		recordHint.setTextColor(Color.WHITE);
		recordHint.setText("点击我");
		recordHint.setGravity(Gravity.CENTER);
		recordHint.setTextSize(16);
		rParams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		rParams.addRule(RelativeLayout.BELOW,  getResources().getInteger(R.integer.RECORD_PHONE_IMG) );
		rParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		rParams.setMargins(0,  PxTodpUtil.dip2px(context, 10),0,0);
		mVolumeLay.addView(recordHint,rParams);
		
		//录音取消提示界面
		mExitLay=new RelativeLayout(context);
		layout.addView(mExitLay,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		mExitLay.setVisibility(View.GONE);
		//mExitLay.setGravity(Gravity.CENTER_HORIZONTAL);
		px=PxTodpUtil.dip2px(context, 20);
		mExitLay.setPadding(0, px, 0,px);
		
		ImageView exitImg=new ImageView(context);
		exitImg.setImageResource(R.drawable.toolbar_up);//temp
		rParams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		rParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		rParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		mExitLay.addView(exitImg,rParams);
		
		TextView exitTxt=new TextView(context);
		exitTxt.setText("点击退出");
		exitTxt.setTextColor(Color.WHITE);
		exitTxt.setGravity(Gravity.CENTER);
		exitTxt.setTextSize(18);
		exitTxt.setBackgroundResource(R.drawable.toolbar_up); //temp
		px=PxTodpUtil.dip2px(context, 10);
		//exitTxt.setPadding(px, px, px, px);
		rParams=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		rParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		mExitLay.addView(exitTxt,rParams);
		
		
		return layout;
	}
	
	//初始化dialog 和录音器
	private void initDialogAndStartRecord() 
	{

		mStartTime = System.currentTimeMillis();
		if(mRecordDialog==null)
		{
			mRecordDialog = new Dialog(getContext(),R.style.like_toast_dialog_style);
			mRecordDialog.setContentView(createContentView());
			LayoutParams params =mRecordDialog.getWindow().getAttributes();
			int px=PxTodpUtil.dip2px(getContext(), 200);
	        params.width = px;    
	        params.height = px;  
	        mRecordDialog.getWindow().setAttributes(params);  
			
			mRecordDialog.setOnDismissListener(onDismiss);	
		}
		

		startRecording();
		mRecordDialog.show();
	}

	
	//结束录音
	private void finishRecord()
	{
		stopRecording();
		mRecordDialog.dismiss();

		long intervalTime = System.currentTimeMillis() - mStartTime;
		if (intervalTime < MIN_INTERVAL_TIME)
		{
			Toast.makeText(getContext(), "时间太短！", Toast.LENGTH_SHORT).show();
			File file = new File(mFileName);
			file.delete();
			return;
		}

		if (mFinishedListerer != null)
			mFinishedListerer.onFinishedRecord(mFileName,(int) ((System.currentTimeMillis()-mStartTime)/1000));
	}

	//取消录音
	private void cancelRecord() 
	{
		stopRecording();
		mRecordDialog.dismiss();

		Toast.makeText(getContext(), "取消录音！", Toast.LENGTH_SHORT).show();
		File file = new File(mFileName);
		file.delete();
	}

	//开始录音
	private void startRecording() 
	{
		mAudioUtil.setAudioPath(mFileName);
		mAudioUtil.recordAudio();
		mThread = new ObtainDecibelThread();
		mThread.start();

	}

	
	private void stopRecording()
	{
		if (mThread != null) 
		{
			mThread.exit();
			mThread = null;
		}
		if (mAudioUtil != null) 
		{
			mAudioUtil.stopRecord();
		}
	}

	private class ObtainDecibelThread extends Thread
	{

		private volatile boolean running = true;

		public void exit() 
		{
			running = false;
		}

		@Override
		public void run() 
		{
			while (running)
			{
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) 
				{
					e.printStackTrace();
				}
				if (mAudioUtil == null || !running)
				{
					break;
				}
				
				int volumn = mAudioUtil.getVolumn();
				
				if (volumn != 0)	mVolumeHandler.sendEmptyMessage(volumn);
					
			}
		}

	}

	private OnDismissListener onDismiss = new OnDismissListener()
	{

		@Override
		public void onDismiss(DialogInterface dialog) 
		{
			stopRecording();
		}
	};

	 class ShowVolumeHandler extends Handler 
	{
		@Override
		public void handleMessage(Message msg)
		{
			mVolumeViewer.setVolumeValue(msg.what);
		}
	}

	public interface OnFinishedRecordListener 
	{
		public void onFinishedRecord(String audioPath, int recordTime);
	}

}
