package sysu.interaction;

import android.app.Activity;
import sysu.interaction.R;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SetHeightActivity extends Activity{
	private SetHeightImageView setHeightImageView;
	private TextView heightTextView1,heightTextView2;
	private Button okButton;
	private Button homeButton;
	private Spinner objSpinner;
	public static float height1,height2;
	private ArrayAdapter<String> adapter;
	private static final String[] objs = {"参照物", "银行卡长", "银行卡宽","A4纸长", "A4纸宽", "宿舍瓷砖边长"};
	private static final double[] data = {0, 8.56, 5.40, 29.7, 21.0, 30.0};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_height);
		setHeightImageView = (SetHeightImageView)findViewById(R.id.setHeightimageView);
		setHeightImageView.setImageBitmap(CameraOrPicturesActivity.bitmap);
		heightTextView1 = (TextView)findViewById(R.id.heightText1);
		heightTextView2 = (TextView)findViewById(R.id.heightText2);
		okButton = (Button)findViewById(R.id.ok_but);
		homeButton = (Button)findViewById(R.id.home_but);
		objSpinner = (Spinner) findViewById(R.id.hspinner);
		
		okButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				height1 =Float.valueOf( heightTextView1.getText().toString() );
				height2 =Float.valueOf( heightTextView2.getText().toString() );
				Intent intent = new Intent();
				intent.setClass(SetHeightActivity.this,ResultActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});
		
		homeButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(SetHeightActivity.this,
						CameraOrPicturesActivity.class);
				startActivity(intent);
			}
		});
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, objs);
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		objSpinner.setAdapter(adapter);
		
		objSpinner.setOnItemSelectedListener(new OnItemSelectedListener() 
				{

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						if (arg2 == 0)
						{
							heightTextView1.setText("高度");
							heightTextView2.setText("高度");
							heightTextView1.setTextColor(Color.GRAY);
							heightTextView2.setTextColor(Color.GRAY);
						}
						else
						{
							heightTextView1.setText(data[arg2]+"");
							heightTextView2.setText(data[arg2]+"");
							heightTextView1.setTextColor(Color.BLACK);
							heightTextView2.setTextColor(Color.BLACK);
						}
						arg0.setVisibility(View.VISIBLE);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
					}
					
				});
	}
}
