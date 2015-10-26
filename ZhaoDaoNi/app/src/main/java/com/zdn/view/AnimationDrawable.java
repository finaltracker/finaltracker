package com.zdn.view;

public class AnimationDrawable extends android.graphics.drawable.AnimationDrawable {

	private int repeatCount = 0;
	private boolean isRepeat = true;
	private AnimationEndListener listener;
	
	public AnimationDrawable() {
		super();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		if(!isRepeat) {
			repeatCount --;
			if(repeatCount == 1) {
				super.unscheduleSelf(this);
				if(listener != null) listener.onEnd();
			}
		}
	}
	
	public void setRepeatCount(int num) {
		if(num == 0) isRepeat = true;
		else {
			isRepeat = false;
			repeatCount = num*getNumberOfFrames();
		}
	}
	
	public void setAnimationEndListener(AnimationEndListener ls) {
		listener = ls;
	}
	
	public interface AnimationEndListener {
		public void onEnd();
	}
}
