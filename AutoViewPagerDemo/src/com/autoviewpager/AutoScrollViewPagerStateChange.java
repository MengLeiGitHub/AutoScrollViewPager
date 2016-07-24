package com.autoviewpager;


 
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
/**
 * viewpager 底部下标指示器
 * @author ML  2015-05-29
 *
 */
public class AutoScrollViewPagerStateChange extends View {
	public AutoScrollViewPagerStateChange(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	private int size, current;

	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setAlpha(1);
 		int item_width = 30;
 		int item_height=5;
 		int jiange=20;
		int center_x = (getWidth() - item_width * size-jiange*(size)) / 2;
        int lastright = 0;
 		for (int i = 0; i < size; i++) {
			p.setColor(Color.parseColor("#ff00ff"));
			p.setStyle(Style.FILL);
			if (i == current) {
				p.setStyle(Style.FILL);
				p.setColor(Color.WHITE);
			}
			/*canvas.drawCircle(center_x + item_width / 2 + i * item_width,
					getHeight() / 2, 10, p);*/
			int left=center_x + item_width / 2 + i * item_width;
			if(i!=0)left=lastright+jiange;
			
			int right=left+item_width;
              lastright=right;

  			int top=0;
			int bottom=item_height;
 			Rect r=new Rect(left,top,right,bottom);
			canvas.drawRect(r, p);
		
			
			
 		}
		canvas.save();
 	}

	public void drawCicle(int size, int current) {
		this.size = size;
		this.current = current;
		invalidate();
	}
}

