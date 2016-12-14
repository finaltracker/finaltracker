/**
 * 
 */
package com.tmac.onsite.view;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import com.tmac.onsite.R;
import com.tmac.onsite.utils.AudioManager;
import com.tmac.onsite.utils.AudioManager.OnPauseResumeListener;

import android.R.integer;
import android.content.Context;
import android.content.pm.ApplicationInfo.DisplayNameComparator;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Telephony.Sms.Conversations;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author tmac
 */
public class AudioPopupWindow extends PopupWindow implements OnClickListener, OnPauseResumeListener{

	private AudioManager audioManager; 
	private Context mContext;
	private View contentView;
	private LinearLayout popup_layout_bottom;
	private Button btn_replay_record;
	private Button btn_post_record;
	private Chronometer chronometer;
	private TextView tv_cancle_audio;
	private TextView tv_time;
	private TextView tv_hint;
	private ImageView iv_nobegin_audio;
	private ImageView iv_begin_audio;
	private ImageView iv_play_audio;
	private ImageView iv_pause_audio;
	private TimerTask task;
	private Timer timer;
	private int reco_time_Num = 0;
	private int play_time_Num = 0;
	private String record_time;
	private String play_time;
	private boolean isFirstPlay = true;
	private boolean isPlayOver = false;
	private int time_state;
	private static final int RECORD = 0;
	private static final int PLAY = 1;
	private OnUpLoadClickListener mListener;
	private final String TAG = "LC-AudioPopupWindow";
	
	
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			tv_time.setText(msg.obj + "");
		};
	};
	
	
	public AudioPopupWindow(Context context, OnUpLoadClickListener listener) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mListener = listener;
		calWindthAndHeight();
		init();
		
	}
	

	private void init() {
		// TODO Auto-generated method stub
		contentView = LayoutInflater.from(mContext).inflate(R.layout.audio_popupwindow, null);
		setContentView(contentView);
		setTouchable(true);
		setFocusable(true);
		setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        setBackgroundDrawable(new BitmapDrawable());
        
        tv_cancle_audio = findVIewById(R.id.cancle_audio);
        tv_time = findVIewById(R.id.tv_time);
        tv_hint = findVIewById(R.id.tv_hint);
        iv_nobegin_audio = findVIewById(R.id.iv_nobegin_audio);
        iv_begin_audio = findVIewById(R.id.iv_begin_audio);
        iv_pause_audio = findVIewById(R.id.iv_pause_audio);
        iv_play_audio = findVIewById(R.id.iv_play_audio);
        popup_layout_bottom = findVIewById(R.id.popup_layout_bottom);
        btn_post_record = findVIewById(R.id.upload_audio_btn);
        btn_replay_record = findVIewById(R.id.re_audio_btn);
        chronometer = findVIewById(R.id.chronometer);
        
        
        tv_cancle_audio.setOnClickListener(this);
        iv_nobegin_audio.setOnClickListener(this);
        iv_begin_audio.setOnClickListener(this);
        iv_pause_audio.setOnClickListener(this);
        iv_play_audio.setOnClickListener(this);
        btn_post_record.setOnClickListener(this);
        btn_replay_record.setOnClickListener(this);
        
	}

	private <T extends View> T findVIewById(int id){
		return (T)contentView.findViewById(id);
	}
	
	private void calWindthAndHeight() {
		// TODO Auto-generated method stub
		
		WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		manager.getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
				mContext.getResources().getDimension(R.dimen.audio_popup_height),
				mContext.getResources().getDisplayMetrics());
		setWidth(width);
		setHeight(height);
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.cancle_audio:
			dismiss();
			audioManager.releaseRecorder();
			break;
			
		case R.id.iv_nobegin_audio:
			//time_state = RECORD;
			chronometer.setVisibility(View.VISIBLE);
			chronometer.start();
			chronometer.setBase(SystemClock.elapsedRealtime());
			iv_nobegin_audio.setVisibility(View.GONE);
			tv_hint.setVisibility(View.GONE);
			iv_begin_audio.setVisibility(View.VISIBLE);
			//tv_time.setVisibility(View.VISIBLE);
			//timer.schedule(task, 0, 1000);
			
			// 录制音频
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				String dir = Environment.getExternalStorageDirectory() + "/" + mContext.getPackageName();
				audioManager = AudioManager.getInstance(dir);
				audioManager.setOnPauseResume(this);
				audioManager.prepareAudio();
			}else {
				Toast.makeText(mContext, "当前手机没有SD卡", Toast.LENGTH_SHORT).show();
			}
			
			break;
			
		case R.id.iv_begin_audio:
			audioManager.releaseRecorder();
			record_time = chronometer.getText().toString();
			chronometer.stop();
			chronometer.setBase(SystemClock.elapsedRealtime());
			iv_begin_audio.setVisibility(View.GONE);
			tv_cancle_audio.setVisibility(View.GONE);
			chronometer.setVisibility(View.GONE);
			iv_play_audio.setVisibility(View.VISIBLE);
			popup_layout_bottom.setVisibility(View.VISIBLE);
			tv_time.setVisibility(View.VISIBLE);
			tv_time.setText("00:00/" + record_time);
			
			break;
			
		case R.id.iv_play_audio:
			iv_play_audio.setVisibility(View.GONE);
			iv_pause_audio.setVisibility(View.VISIBLE);
			if(isFirstPlay){
				initTimerTask();
				playAudio(true);
				/*AudioManager.playAudio(audioManager.getAudioPath(), new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						resetPlay();
					}
				});*/
			}else {
				AudioManager.resume();
				iv_pause_audio.setVisibility(View.VISIBLE);
				iv_play_audio.setVisibility(View.GONE);
			}
			break;
			
		case R.id.iv_pause_audio:
			AudioManager.pause();
			iv_pause_audio.setVisibility(View.GONE);
			iv_play_audio.setVisibility(View.VISIBLE);
			break;
			
		case R.id.upload_audio_btn:
			// 上传录音
			dismiss();
			mListener.onUpload(record_time);
			
			break;
			
		case R.id.re_audio_btn:
			resetRecord();
			break;
			
			
		default:
			break;
		}
	}

	/**
	 * 初始化录音计时器的操作
	 */
	private void initTimerTask() {
		// TODO Auto-generated method stub
		timer = new Timer();	
		task = new TimerTask() {
					

			@Override
			public void run() {
				// TODO Auto-generated method stub
				switch (time_state) {
				case RECORD:
					reco_time_Num++;
					Message reco_msg = new Message();
					if(reco_time_Num < 10){
						record_time = "00:0" + reco_time_Num;
						reco_msg.obj = record_time;
						handler.sendMessage(reco_msg);
					}else {
						record_time = "00:" + reco_time_Num;
						reco_msg.obj = record_time;
						handler.sendMessage(reco_msg);
					}
					break;
					
				case PLAY:
					if(!AudioManager.isPause){
						play_time_Num++;
						Message play_msg = new Message();
						if(play_time_Num < 10){
							play_time = "00:0" + play_time_Num + "/" + record_time;
							play_msg.obj = play_time;
							handler.sendMessage(play_msg);
						}else {
							play_time = "00:" + play_time_Num + "/" + record_time;
							play_msg.obj = play_time;
							handler.sendMessage(play_msg);
						}
					}
					break;

				default:
					break;
				}
				
			}
		};
		AudioManager.isPause = false;
		isPlayOver = false;
		time_state = PLAY;
		isFirstPlay = false;
		play_time_Num = 0;
		timer.schedule(task, 500, 1000);
	}


	@Override
	public void playPauseResume(int state) {
		// TODO Auto-generated method stub
		switch (state) {
		case AudioManager.PLAY:
			iv_play_audio.setVisibility(View.GONE);
			iv_pause_audio.setVisibility(View.VISIBLE);
			timer.schedule(task, 0, 1000);
			break;
		case AudioManager.PAUSE:
			iv_pause_audio.setVisibility(View.GONE);
			iv_play_audio.setVisibility(View.VISIBLE);
			
			break;
		case AudioManager.RESUME:
			iv_pause_audio.setVisibility(View.VISIBLE);
			iv_play_audio.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}
	
	
	/**
	 * 播放录音结束后重置
	 */
	private void resetPlay(){
		Log.d(TAG, "Audio Duration " + AudioManager.mMediaPlayer.getDuration());
		iv_play_audio.setVisibility(View.VISIBLE);
		iv_pause_audio.setVisibility(View.GONE);
		AudioManager.releasePlayer();
		isFirstPlay = true;
		timer.cancel();
		task.cancel();
		timer = null;
		task = null;
		isPlayOver = true;
	}
	
	
	/**
	 * 重置录音
	 */
	private void resetRecord(){
		if(!isPlayOver){
			resetPlay();
		}
		
		AudioManager.isPause = true;
		popup_layout_bottom.setVisibility(View.GONE);
		tv_time.setVisibility(View.GONE);
		iv_play_audio.setVisibility(View.GONE);
		iv_pause_audio.setVisibility(View.GONE);
		tv_cancle_audio.setVisibility(View.VISIBLE);
		tv_hint.setVisibility(View.VISIBLE);
		iv_nobegin_audio.setVisibility(View.VISIBLE);
		
		// 删除录音文件
		File file = new File(audioManager.getAudioPath());
		if(file.exists()){
			file.delete();
		}
		
	}
	
	public void playAudio(final boolean isReset){
		AudioManager.playAudio(audioManager.getAudioPath(), new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				if(isReset)
					resetPlay();
			}
		});
	}
	
	public interface OnUpLoadClickListener{
		void onUpload(String time);
	}
}
