/**
 * 
 */
package com.tmac.onsite.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.R.integer;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * @author tmac
 */
public class AudioManager {

	private static final String TAG = AudioManager.class.getSimpleName();
	private MediaRecorder mMediaRecorder;
	public static MediaPlayer mMediaPlayer;
	private static AudioManager mInstance;
	private String mDir;
	private String mCurrentFilePath;
	public static boolean isPause = false;
	private static OnPauseResumeListener mListener;
	public static final int PLAY = 0;
	public static final int PAUSE = 1;
	public static final int RESUME = 2;
	
	private AudioManager(String dir){
		this.mDir = dir;
	}
	
	public static AudioManager getInstance(String dir){
		if(mInstance == null){
			synchronized (AudioManager.class) {
				mInstance = new AudioManager(dir);
			}
		}
		return mInstance;
	}
	
	/**
	 * 录制音频
	 */
	public void prepareAudio(){
		
		try {
			File dir = new File(mDir);
			if(!dir.exists())
				dir.mkdirs();
			
			String fileName = generateFileName();
			File file = new File(dir, fileName);
			
			mCurrentFilePath = file.getAbsolutePath();
			mMediaRecorder = new MediaRecorder();
			// 设置输出文件
			mMediaRecorder.setOutputFile(file.getAbsolutePath());
			// 设置MediaRecorder的音频源为麦克风
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			// 设置音频的格式
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			// 设置音频的编码
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mMediaRecorder.prepare();
			mMediaRecorder.start();
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 随机生成音频的后缀名
	 * @return
	 */
	private String generateFileName() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString() + ".amr";
	}
	
	/**
	 * 录制结束，释放资源
	 */
	public void releaseRecorder(){
		mMediaRecorder.stop();
		mMediaRecorder.release();
		mMediaRecorder = null;
		Log.d(TAG, "音频录制结束");
	}
	
	
	
	/**
	 * 播放音频
	 */
	public static void playAudio(String filePath, OnCompletionListener listener){
		
		if(mMediaPlayer == null){
			mMediaPlayer = new MediaPlayer();
			mMediaPlayer.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					mMediaPlayer.reset();
					return false;
				}
			});
		}else {
			mMediaPlayer.reset();
		}
		
		try {
			mMediaPlayer.setAudioStreamType(android.media.AudioManager.STREAM_MUSIC);
			// 设置播放完毕回调接口
			mMediaPlayer.setOnCompletionListener(listener);
			// 设置播放源
			mMediaPlayer.setDataSource(filePath);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (IllegalArgumentException | SecurityException
				| IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "play error : " + e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 暂停播放
	 */
	public static void pause(){
		if(mMediaPlayer != null && mMediaPlayer.isPlaying()){
			mMediaPlayer.pause();
			isPause = true;
		}
		
	}
	
	/**
	 * 恢复播放
	 */
	public static void resume(){
		if(mMediaPlayer != null && isPause){
			mMediaPlayer.start();
			isPause = false;
		}
	}
	
	/**
	 * 释放资源
	 */
	public static void releasePlayer(){
		if(mMediaPlayer != null){
			isPause = true;
			mMediaPlayer.stop();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}
	
	
	public void setOnPauseResume(OnPauseResumeListener mListener){
		this.mListener = mListener;
	}
	

	public interface OnPauseResumeListener{
		void playPauseResume(int state);
	}
	
	
	public String getAudioPath(){
		return mCurrentFilePath;
	}
	
	
	
	
}
