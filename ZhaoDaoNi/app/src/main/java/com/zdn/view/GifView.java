package com.zdn.view;

import com.zdn.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class GifView extends ImageView {
private long movieStart;
private Movie movie;

int gifWidth;
int gifHeight;
private AnimationDrawable animDown;
    //此处必须重写该构造方法
public GifView(Context context,AttributeSet attributeSet) {
	super(context,attributeSet);
		//以文件流（InputStream）读取进gif图片资源

		animDown = new AnimationDrawable();

		TypedArray a = context.obtainStyledAttributes(attributeSet,
				R.styleable.GifView);

		int r = a.getResourceId(R.styleable.GifView_gif, 0);

		setBackgroundResource(r);
		animDown = (AnimationDrawable) getBackground();
		//animDown.start();
		a.recycle();
	}
	public void start()
	{
		animDown.start();
	}

	public void stop()
	{
		animDown.stop();
	}

	public boolean isRunning()
	{
		return animDown.isRunning();
	}
}