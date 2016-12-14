/**
 * 
 */
package com.tmac.onsite.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * @author linsen
 * @date 2016年6月6日
 */
public class PhoneEditText extends EditText {

	public PhoneEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public PhoneEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public PhoneEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		

		Paint paint=new Paint();
		paint.setStrokeWidth(10);
		paint.setColor(Color.GRAY);
		canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
		
		super.onDraw(canvas);
		
		
	}
	
}
