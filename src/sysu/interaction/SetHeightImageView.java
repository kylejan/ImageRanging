package sysu.interaction;

import sysu.graphic.DrawablePoint;
import sysu.graphic.GraphicDraw;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

public class SetHeightImageView extends ImageView {
	public static boolean hasHeightLines;
	// 点数组中，前两个对应第一条直线，后两个对应第二条直线
	public static DrawablePoint heightLineDrawablePoints[] = new DrawablePoint[4];
	private float x1, x2, y1, y2;
	
	//放大镜的外边框
	public Drawable circleDrawable = (Drawable)this.getResources().getDrawable(sysu.interaction.R.drawable.circle);
	//用于存储当前view被转换成为的bitmap
	private Bitmap biggerBitmap;
	
	public SetHeightImageView(Context context) {
		super(context);
	}

	public SetHeightImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		hasHeightLines = false;
		x1 = x2 = y1 = y2 = 0;
		for (int i = 0; i < 4; i++)
			heightLineDrawablePoints[i] = new DrawablePoint();
	}

	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		//将view 转成bitmap
		this.setDrawingCacheEnabled(true);
		biggerBitmap = Bitmap.createBitmap(this.getDrawingCache());
		this.setDrawingCacheEnabled(false);
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x1 = x2 = x;
			y1 = y2 = y;
			//放大镜出现
			GraphicDraw.NOBIGGER = false;
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
			x2 = x;
			y2 = y;
			invalidate();
			break;
		}
		if (event.getAction() == MotionEvent.ACTION_UP){
			hasHeightLines = true;
			//放大镜消失
			GraphicDraw.NOBIGGER = true;
		}
		return true;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		GraphicDraw.drawRectangle(canvas);
		setHeightLines();
		GraphicDraw.drawHeightLines(canvas);
		GraphicDraw.drawBigger(circleDrawable,canvas,x2,y2,getWidth(),getHeight(),biggerBitmap);
	}

	private void setHeightLines() {
		if (!hasHeightLines)
			initializeHeightLines();
		else
			modifyHeightLines();
	}

	private void initializeHeightLines() {
		heightLineDrawablePoints[0].x = heightLineDrawablePoints[1].x = x1;
		heightLineDrawablePoints[0].y = heightLineDrawablePoints[2].y = y1;
		heightLineDrawablePoints[2].x = heightLineDrawablePoints[3].x = x2;
		heightLineDrawablePoints[1].y = heightLineDrawablePoints[3].y = y2;
	}

	private void modifyHeightLines() {
		DrawablePoint toBeModifiedDrawablePoint = findClosestAimPoint();
		toBeModifiedDrawablePoint.x = x2;
		toBeModifiedDrawablePoint.y = y2;
	}

	private DrawablePoint findClosestAimPoint() {
		float min = 1000000000;
		int index = 0;
		for (int i = 0; i < 4; i++) {
			if (min > heightLineDrawablePoints[i].dis(x2, y2)) {
				min = heightLineDrawablePoints[i].dis(x2, y2);
				index = i;
			}
		}
		return heightLineDrawablePoints[index];
	}
}
