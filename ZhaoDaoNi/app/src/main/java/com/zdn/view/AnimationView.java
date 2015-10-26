package com.zdn.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.zdn.R;

public class AnimationView extends ImageView{

	private Bitmap resourceBip;
	private int duration;
	private boolean positive;
	private int repeatCount;
	private int playAfter;
	private AnimationDrawable animationDrawable;

	public AnimationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.animationView);
		int resId = array.getResourceId(R.styleable.animationView_img, 0);
		resourceBip = BitmapFactory.decodeResource(context.getResources(), resId);
		duration = array.getInt(R.styleable.animationView_duration, 500);
		positive = array.getBoolean(R.styleable.animationView_positive, true);
		repeatCount = array.getInt(R.styleable.animationView_repeat_count, 0);
		playAfter = array.getInt(R.styleable.animationView_play_after, 0);
		int column = array.getInt(R.styleable.animationView_column_number, 1);
		int row = array.getInt(R.styleable.animationView_row_number, 1);
		array.recycle();

		animationDrawable = new AnimationDrawable();
		initAnimation(column, row);
	}

	public void initAnimation(int column, int row) {
		int w = resourceBip.getWidth()/column;
		int h = resourceBip.getHeight()/row;

		if(positive) {
			for (int i = 0; i < row; i++) {
				int y = h*i;
				for (int j = 0; j < column; j++) {
					int x = w*j;
					Drawable drawable = new BitmapDrawable(getResources(), Bitmap.createBitmap(resourceBip, x, y, w, h));
					animationDrawable.addFrame(drawable, duration);
				}
			}
		} else {
			for (int i = row-1; i >= 0; i--) {
				int y = h*i;
				for (int j = column-1; j >= 0; j--) {
					int x = w*j;
					Drawable drawable = new BitmapDrawable(getResources(), Bitmap.createBitmap(resourceBip, x, y, w, h));
					animationDrawable.addFrame(drawable, duration);
				}
			}
		}
		setImageDrawable(animationDrawable);
		animationDrawable.setOneShot(false);
		animationDrawable.setRepeatCount(repeatCount);
		animationDrawable.setAnimationEndListener(new AnimationDrawable.AnimationEndListener() {

			@Override
			public void onEnd() {
				// TODO Auto-generated method stub
				switch (playAfter) {
					case 0:

						break;
					case 1:
						setVisibility(View.INVISIBLE);
						break;
					case 2:
						setVisibility(View.GONE);
						break;
					default:
						break;
				}
			}
		});
		animationDrawable.start();
	}

	public Drawable getCurrentDrawable() {
		return animationDrawable.getCurrent();
	}

	public Drawable getIndexDrawable(int index) {
		return animationDrawable.getFrame(index);
	}

	public void reStart()
	{
		if( animationDrawable.isRunning() )
		{
			animationDrawable.stop();
		}
		animationDrawable.setRepeatCount(repeatCount);
		animationDrawable.start();
	}
}
