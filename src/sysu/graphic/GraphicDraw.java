package sysu.graphic;

import javax.security.auth.PrivateCredentialPermission;

import sysu.interaction.CameraOrPicturesActivity;
import sysu.interaction.R;
import sysu.interaction.ResultImageView;
import sysu.interaction.SetHeightImageView;
import sysu.interaction.SetRectangleActivity;
import sysu.interaction.SetRectangleImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.ShapeDrawable.ShaderFactory;
import android.graphics.drawable.shapes.OvalShape;

public class GraphicDraw {
	// 设置是否出现放大镜
	public static boolean NOBIGGER = true;

	// 放大镜的半径
	private static final int RADIUS = 80;
	// 放大倍数
	private static final int FACTOR = 2;
	// 放大镜中图像的偏移，用于设置放大镜中的图像
	private static Matrix matrix = new Matrix();
	// 放大镜的图像
	private static ShapeDrawable biggerDrawable;
	// 放大镜中心位置
	private static int biggerX = RADIUS, biggerY = RADIUS;
	
	private static Paint paint = new Paint();

	public static void drawRectangle(Canvas canvas) {
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawLine(SetRectangleImageView.leftTopDrawablePoint.x,
				SetRectangleImageView.leftTopDrawablePoint.y,
				SetRectangleImageView.leftBottomDrawablePoint.x,
				SetRectangleImageView.leftBottomDrawablePoint.y, paint);
		canvas.drawLine(SetRectangleImageView.leftTopDrawablePoint.x,
				SetRectangleImageView.leftTopDrawablePoint.y,
				SetRectangleImageView.rightTopDrawablePoint.x,
				SetRectangleImageView.rightTopDrawablePoint.y, paint);
		canvas.drawLine(SetRectangleImageView.rightTopDrawablePoint.x,
				SetRectangleImageView.rightTopDrawablePoint.y,
				SetRectangleImageView.rightBottomDrawablePoint.x,
				SetRectangleImageView.rightBottomDrawablePoint.y, paint);
		canvas.drawLine(SetRectangleImageView.leftBottomDrawablePoint.x,
				SetRectangleImageView.leftBottomDrawablePoint.y,
				SetRectangleImageView.rightBottomDrawablePoint.x,
				SetRectangleImageView.rightBottomDrawablePoint.y, paint);
	}

	public static void drawHeightLines(Canvas canvas) {
		paint.setAntiAlias(true);
		paint.setColor(Color.BLUE);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		for (int i = 0; i < 4; i += 2)
			canvas.drawLine(SetHeightImageView.heightLineDrawablePoints[i].x,
					SetHeightImageView.heightLineDrawablePoints[i].y,
					SetHeightImageView.heightLineDrawablePoints[i + 1].x,
					SetHeightImageView.heightLineDrawablePoints[i + 1].y, paint);
	}

	public static void drawAim(Canvas canvas, int num) {
		paint.setAntiAlias(true);
		paint.setColor(Color.GREEN);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.STROKE);
		for (int i = 0; i < num - 1; i++)
			canvas.drawLine(ResultImageView.aimDrawablePoint[i].x,
					ResultImageView.aimDrawablePoint[i].y,
					ResultImageView.aimDrawablePoint[i + 1].x,
					ResultImageView.aimDrawablePoint[i + 1].y, paint);
	}

	public static void drawBigger(Drawable circleDrawable, Canvas canvas,
			float x, float y, int w, int h, Bitmap viewBitmap) {
		if (viewBitmap == null || NOBIGGER)
			return;
		setPositionOfBigger(x, y, w, h);
		
		//放大镜的块
		BitmapShader shader = new BitmapShader(viewBitmap, TileMode.CLAMP, TileMode.CLAMP);
		Paint shaderPaint = new Paint();
		shaderPaint.setShader(shader);
		
		matrix.reset();
		//设置放大倍数
		matrix.postScale(FACTOR, FACTOR);
		//放大的位置
		matrix.postTranslate(biggerX - x * FACTOR, biggerY - y * FACTOR);
		shader.setLocalMatrix(matrix);
		canvas.drawCircle(biggerX, biggerY, RADIUS, shaderPaint);
		
		// 放大镜外边框的位置
		circleDrawable.setBounds(biggerX - RADIUS, biggerY - RADIUS, biggerX
				+ RADIUS, biggerY + RADIUS);
		circleDrawable.draw(canvas);
	}

	private static void setPositionOfBigger(float x, float y, int w, int h) {
		if (x < 2 * RADIUS && y < 2 * RADIUS) {
			biggerX = w - RADIUS;
		} else
			biggerX = RADIUS;
	}
}
