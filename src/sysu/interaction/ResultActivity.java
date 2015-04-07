package sysu.interaction;
import sysu.interaction.R;
import sysu.algorithm.Function;
import sysu.algorithm.Node;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ResultActivity extends Activity {
	private ResultImageView imageView;
	private Button okButton;
	private Button homeButton;
	private Spinner aimSpinner;
	private double result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		imageView = (ResultImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(CameraOrPicturesActivity.bitmap);
		okButton = (Button) findViewById(R.id.ok_but);
		homeButton = (Button)findViewById(R.id.home_but);
		aimSpinner = (Spinner) findViewById(R.id.spinner1);
		setSpinner();

		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int a=aimSpinner.getSelectedItemPosition();
				switch (aimSpinner.getSelectedItemPosition())
				{
				case 0:
					result = calculatePlaneDis();
					break;
				case 1:
					result = calculateAngle();
					break;
				case 2:
					result = calculateHeight();
					break;
				}
				//弹出计算結果对话框
				new AlertDialog.Builder(ResultActivity.this).setTitle("计算结果")
				.setMessage(Double.toString(result))
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				})
				.setNegativeButton("保存结果", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				})
				.show();
			}
		});
		
		homeButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(ResultActivity.this,
						CameraOrPicturesActivity.class);
				startActivity(intent);
			}
		});
	}
	
	public int getSpinnerPosition(){
		return aimSpinner.getSelectedItemPosition();
	}
	
	private double calculatePlaneDis() {
		Node e = new Node(ResultImageView.aimDrawablePoint[0].x,
				ResultImageView.aimDrawablePoint[0].y);
		Node m = new Node(ResultImageView.aimDrawablePoint[1].x,
				ResultImageView.aimDrawablePoint[1].y);
		Node a = new Node(SetRectangleImageView.leftTopDrawablePoint.x,
				SetRectangleImageView.leftTopDrawablePoint.y);
		Node b = new Node(
				SetRectangleImageView.rightTopDrawablePoint.x,
				SetRectangleImageView.rightTopDrawablePoint.y);
		Node c = new Node(
				SetRectangleImageView.rightBottomDrawablePoint.x,
				SetRectangleImageView.rightBottomDrawablePoint.y);
		Node d = new Node(
				SetRectangleImageView.leftBottomDrawablePoint.x,
				SetRectangleImageView.leftBottomDrawablePoint.y);
		Node rectPos[] = { a, b, c, d };
		double rectLen[] = { SetRectangleActivity.horizentalLength,
				SetRectangleActivity.verticalLength };
		double lengthEM = Function.getRealDist(e, m, rectPos, rectLen);
//		TextView textView = (TextView) findViewById(R.id.result);
//		textView.setText("测量结果为：  " + lengthEM);
		return lengthEM;
	}
	
	private double calculateAngle(){
		//public static double getRealAngle(Node G, Node E, Node M, Node rectPos[], double rectLen[])  
		 
		//ectPos和rectLen和上面一样  
		//调用，返回角度度数，非弧度
		//```
		Node g = new Node(ResultImageView.aimDrawablePoint[0].x,ResultImageView.aimDrawablePoint[0].y);
		Node e = new Node(ResultImageView.aimDrawablePoint[1].x,ResultImageView.aimDrawablePoint[1].y);
		Node m = new Node(ResultImageView.aimDrawablePoint[2].x,ResultImageView.aimDrawablePoint[2].y);
		Node a = new Node(SetRectangleImageView.leftTopDrawablePoint.x,
				SetRectangleImageView.leftTopDrawablePoint.y);
		Node b = new Node(
				SetRectangleImageView.rightTopDrawablePoint.x,
				SetRectangleImageView.rightTopDrawablePoint.y);
		Node c = new Node(
				SetRectangleImageView.rightBottomDrawablePoint.x,
				SetRectangleImageView.rightBottomDrawablePoint.y);
		Node d = new Node(
				SetRectangleImageView.leftBottomDrawablePoint.x,
				SetRectangleImageView.leftBottomDrawablePoint.y);
		Node rectPos[] = { a, b, c, d };
		double rectLen[] = { SetRectangleActivity.horizentalLength,
				SetRectangleActivity.verticalLength };
		double angleGEM = Function.getRealAngle(g, e, m, rectPos, rectLen);
//		TextView textView = (TextView) findViewById(R.id.result);
//		textView.setText("测量结果为：  " + angleGEM);
		return angleGEM;
	}

	private double calculateHeight() {
		Node n = new Node(ResultImageView.aimDrawablePoint[0].x,
				ResultImageView.aimDrawablePoint[0].y);
		Node m = new Node(ResultImageView.aimDrawablePoint[1].x,
				ResultImageView.aimDrawablePoint[1].y);
		Node f = new Node(
				SetHeightImageView.heightLineDrawablePoints[0].x,
				SetHeightImageView.heightLineDrawablePoints[0].y);
		Node e = new Node(
				SetHeightImageView.heightLineDrawablePoints[1].x,
				SetHeightImageView.heightLineDrawablePoints[1].y);
		Node h = new Node(
				SetHeightImageView.heightLineDrawablePoints[2].x,
				SetHeightImageView.heightLineDrawablePoints[2].y);
		Node g = new Node(
				SetHeightImageView.heightLineDrawablePoints[3].x,
				SetHeightImageView.heightLineDrawablePoints[3].y);
		Node a = new Node(SetRectangleImageView.leftTopDrawablePoint.x,
				SetRectangleImageView.leftTopDrawablePoint.y);
		Node b = new Node(
				SetRectangleImageView.rightTopDrawablePoint.x,
				SetRectangleImageView.rightTopDrawablePoint.y);
		Node c = new Node(
				SetRectangleImageView.rightBottomDrawablePoint.x,
				SetRectangleImageView.rightBottomDrawablePoint.y);
		Node d = new Node(
				SetRectangleImageView.leftBottomDrawablePoint.x,
				SetRectangleImageView.leftBottomDrawablePoint.y);
		Node rectPos[] = { a, b, c, d };
		double heightMN = Function.getRealHeight(m, n, e, f, g, h,
				SetHeightActivity.height1, rectPos);
//		TextView textView = (TextView) findViewById(R.id.result);
//		textView.setText("测量结果为：  " + heightMN);
		return heightMN;
	}

	private void setSpinner() {
		ArrayAdapter<CharSequence> adapter;
		if (SetHeightImageView.hasHeightLines)
			adapter = ArrayAdapter.createFromResource(this,
					R.array.threeAims,
					android.R.layout.simple_spinner_item);
		else
			adapter = ArrayAdapter.createFromResource(this,
					R.array.twoAims,
					android.R.layout.simple_spinner_item);

		adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		aimSpinner.setAdapter(adapter);
		aimSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(
							AdapterView<?> parent,
							View view,
							int position, long id) {
						imageView.clearAim();
					}
					public void onNothingSelected(
							AdapterView<?> parent) {
						
					}
				});
	}
}
