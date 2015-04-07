package sysu.interaction;

import java.util.ArrayList;
import java.util.HashMap;

import sysu.graphic.DrawablePoint;
import sysu.graphic.GraphicDraw;

import android.R;
import android.R.drawable;
import android.R.integer;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.SlidingDrawer.OnDrawerScrollListener;

public class SetRectangleImageView extends ImageView {
	public static boolean hasRectangle;
	public static DrawablePoint leftTopDrawablePoint = new DrawablePoint();
	public static DrawablePoint rightTopDrawablePoint = new DrawablePoint();
	public static DrawablePoint leftBottomDrawablePoint = new DrawablePoint();
	public static DrawablePoint rightBottomDrawablePoint = new DrawablePoint();
	private float x1, x2, y1, y2;
	
	private ArrayList<DrawablePoint> retanglePointList = new ArrayList<DrawablePoint>();
	//放大镜的外边框
	public Drawable circleDrawable = (Drawable)this.getResources().getDrawable(sysu.interaction.R.drawable.circle);
	//用于存储当前view被转换成为的bitmap
	private Bitmap biggerBitmap;
	
	public SetRectangleImageView(Context context) {
		super(context);
	}

	public SetRectangleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		hasRectangle = false;
		x1 = x2 = y1 = y2 = 0;
		retanglePointList.add(leftTopDrawablePoint);
		retanglePointList.add(rightTopDrawablePoint);
		retanglePointList.add(rightBottomDrawablePoint);
		retanglePointList.add(leftBottomDrawablePoint);
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
		if (event.getAction() == MotionEvent.ACTION_UP) {
			hasRectangle = true;
			//放大镜消失
			GraphicDraw.NOBIGGER = true;
		}
		return true;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		setRectangle();
		GraphicDraw.drawRectangle(canvas);	
		GraphicDraw.drawBigger(circleDrawable,canvas,x2,y2,getWidth(),getHeight(),biggerBitmap);
	}

	private void setRectangle() {
		if (!hasRectangle)
			initializeRectangle();
		else
			modifyRectangle();
	}

	private void initializeRectangle() {
		float minX = x1 < x2 ? x1 : x2;
		float maxX = x1 > x2 ? x1 : x2;
		float minY = y1 < y2 ? y1 : y2;
		float maxY = y1 > y2 ? y1 : y2;
		leftTopDrawablePoint.x = leftBottomDrawablePoint.x = minX;
		leftTopDrawablePoint.y = rightTopDrawablePoint.y = minY;
		rightBottomDrawablePoint.x = rightTopDrawablePoint.x = maxX;
		rightBottomDrawablePoint.y = leftBottomDrawablePoint.y = maxY;
	}

	private void modifyRectangle() {
		DrawablePoint toBeChangeDrawablePoint = findClosest();
		toBeChangeDrawablePoint.x = x2;
		toBeChangeDrawablePoint.y = y2;
	}

	private DrawablePoint findClosest() {
		float min = 1000000000;
		int index = 0;
		for (int i = 0; i < 4; i++) {
			if (min > retanglePointList.get(i).dis(x2, y2)) {
				min = retanglePointList.get(i).dis(x2, y2);
				index = i;
			}
		}
		return retanglePointList.get(index);
	}
}

// 123123123s
// A 306��186
// D 212,457
// B 515,187
// C 570,464
// E 345,482
// M 480 272

