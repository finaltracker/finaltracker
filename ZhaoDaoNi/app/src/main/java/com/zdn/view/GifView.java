package com.zdn.view;

import com.zdn.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;
 
public class GifView extends View{
private long movieStart;
private Movie movie;

int gifWidth;
int gifHeight;

    //此处必须重写该构造方法
public GifView(Context context,AttributeSet attributeSet) {
	super(context,attributeSet);
	//以文件流（InputStream）读取进gif图片资源

	TypedArray a = context.obtainStyledAttributes(attributeSet,
			R.styleable.GifView);

	int r = a.getResourceId(R.styleable.GifView_gif, 0);

	movie=Movie.decodeStream(getResources().openRawResource( r ));

	gifWidth  = movie.width();
	gifHeight = movie.height();


	a.recycle();
}

	public  void stop()
	{
		movie.
	}
	@Override
		protected void onDraw(Canvas canvas) {
		long curTime=android.os.SystemClock.uptimeMillis();
		//第一次播放
		if (movieStart == 0) {
		movieStart = curTime;
		}
		if (movie != null) {
		int duraction = movie.duration();
		int relTime = (int) ((curTime-movieStart)%duraction);
		movie.setTime(relTime);

		int draw_start_x;
		int draw_start_y;
		int width = getWidth();
		int height = getHeight();
		draw_start_x =  ((width - gifWidth) > 0 )?(width - gifWidth)/2 : 0;
		draw_start_y =  ((height - gifHeight) > 0 )?(height - gifHeight)/2 : 0;

		movie.draw(canvas,draw_start_x, draw_start_y);
		//强制重绘
		invalidate();
		}
		super.onDraw(canvas);
	}
}