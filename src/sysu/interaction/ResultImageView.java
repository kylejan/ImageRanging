package sysu.interaction;

import javax.security.auth.PrivateCredentialPermission;

import sysu.graphic.DrawablePoint;
import sysu.graphic.GraphicDraw;

import android.R.bool;
import android.R.string;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

public class ResultImageView extends ImageView {
	public static boolean hasAim;
	public static DrawablePoint[] aimDrawablePoint = new DrawablePoint[3];
	private float x1, y1, x2, y2;
	//放大镜的外边框
	public Drawable circleDrawable = (Drawable)this.getResources().getDrawable(sysu.interaction.R.drawable.circle);
	//用于存储当前view被转换成为的bitmap
	private Bitmap biggerBitmap;

	public ResultImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		for (int i = 0; i < 3; i++)
			aimDrawablePoint[i] = new DrawablePoint();
		x1 = y1 = x2 = y2 = 0;
		hasAim = false;
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
			hasAim = true;
			//放大镜消失
			GraphicDraw.NOBIGGER = true;
		}
		return true;
	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		GraphicDraw.drawRectangle(canvas);
		if (SetHeightImageView.hasHeightLines)
			GraphicDraw.drawHeightLines(canvas);
		setAim();
		if (((ResultActivity) getContext()).getSpinnerPosition() == 1) 
			GraphicDraw.drawAim(canvas,3);
		else  GraphicDraw.drawAim(canvas,2);
		GraphicDraw.drawBigger(circleDrawable,canvas,x2,y2,getWidth(),getHeight(),biggerBitmap);
	}

	private void setAim() {
		if (!hasAim)
			initializeAim();
		else
			modifyAim();
	}

	private void initializeAim() {
		aimDrawablePoint[0].x = x1;
		aimDrawablePoint[0].y = y1;
		aimDrawablePoint[1].x = x2;
		aimDrawablePoint[1].y = y2;
		aimDrawablePoint[2].x = x2;
		aimDrawablePoint[2].y = y1;
	}

	private void modifyAim() {
		DrawablePoint toBeModifiedDrawablePoint;
		if(((ResultActivity) getContext()).getSpinnerPosition() == 1)
			toBeModifiedDrawablePoint = findClosestAimPoint(3);
		else toBeModifiedDrawablePoint = findClosestAimPoint(2);
		toBeModifiedDrawablePoint.x = x2;
		toBeModifiedDrawablePoint.y = y2;
	}

	private DrawablePoint findClosestAimPoint(int num) {
		float min = 1000000000;
		int index = 0;
		for(int i=0;i<num;i++){
			if (aimDrawablePoint[i].dis(x2, y2) < min){
				min = aimDrawablePoint[i].dis(x2, y2) ;
				index = i;
			}
		}	
		return aimDrawablePoint[index];
	}

	public void clearAim() {
		for (int i = 0; i < 3; i++)
			aimDrawablePoint[i].x = aimDrawablePoint[i].y = 0;
		hasAim = false;
		x1=y1=x2=y2=0;
		invalidate();
	}
}
