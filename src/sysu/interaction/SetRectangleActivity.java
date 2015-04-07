package sysu.interaction;

import sysu.algorithm.Node;
import sysu.interaction.R;
import java.security.PublicKey;

import android.R.anim;
import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class SetRectangleActivity extends Activity {
	public static float verticalLength;
	public static float horizentalLength;
	private SetRectangleImageView imageView;
	private Button planeButton;
	private Button heightButton;
	private Button helpButton;
	private Button homeButton;
	private Button exchgButton;
	private Spinner objSpinner;
	private TextView verticalLengthTextView;
	private TextView horizentalLengthTextView;
	private ArrayAdapter<String> adapter;

	private static final String[] objs = { "参照物", "银行卡", "A4纸", "Iphone4",
			"宿舍瓷砖", "10元人民币" };
	private static final Node[] data = { new Node(0, 0), new Node(8.56, 5.40),
			new Node(21.0, 29.7), new Node(11.52, 5.86), new Node(30, 30),
			new Node(14.0, 7.0) };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_rectangle);
		planeButton = (Button) findViewById(R.id.plane_but);
		heightButton = (Button) findViewById(R.id.height_but);
		helpButton = (Button) findViewById(R.id.help_but);
		homeButton = (Button) findViewById(R.id.home_but);
		exchgButton = (Button)findViewById(R.id.exchange_but);
		objSpinner = (Spinner) findViewById(R.id.objspinner);
		verticalLengthTextView = (TextView) findViewById(R.id.verticalText);
		horizentalLengthTextView = (TextView) findViewById(R.id.horizentalText);
		imageView = (SetRectangleImageView) findViewById(R.id.imageView1);
		imageView.setImageBitmap(CameraOrPicturesActivity.bitmap);

		OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				EditText clickEditText = (EditText) v;
				if (hasFocus) {
					clickEditText.setText("");
					clickEditText.setTextColor(Color.BLACK);
				}
			}
		};

		verticalLengthTextView.setOnFocusChangeListener(focusChangeListener);
		horizentalLengthTextView.setOnFocusChangeListener(focusChangeListener);

		planeButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				horizentalLength = Float.valueOf(horizentalLengthTextView
						.getText().toString());
				verticalLength = Float.valueOf(verticalLengthTextView.getText()
						.toString());
				SetHeightImageView.hasHeightLines = false;
				Intent intent = new Intent();
				intent.setClass(SetRectangleActivity.this, ResultActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});

		heightButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				horizentalLength = Float.valueOf(horizentalLengthTextView
						.getText().toString());
				verticalLength = Float.valueOf(verticalLengthTextView.getText()
						.toString());
				SetHeightImageView.hasHeightLines = false;
				Intent intent = new Intent();
				intent.setClass(SetRectangleActivity.this,
						SetHeightActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});

		helpButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {

			}
		});

		homeButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(SetRectangleActivity.this,
						CameraOrPicturesActivity.class);
				startActivity(intent);
			}
		});
		
		exchgButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				String tmpString = horizentalLengthTextView.getText().toString();
				horizentalLengthTextView.setText(verticalLengthTextView.getText().toString());
				verticalLengthTextView.setText(tmpString);
			}
		});
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, objs);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		objSpinner.setAdapter(adapter);

		objSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0)
				{
					horizentalLengthTextView.setText("横向");
					verticalLengthTextView.setText("纵向");
					horizentalLengthTextView.setTextColor(Color.GRAY);
					verticalLengthTextView.setTextColor(Color.GRAY);
				}
				else
				{
					horizentalLengthTextView.setText(data[arg2].x + "");
					verticalLengthTextView.setText(data[arg2].y + "");
					horizentalLengthTextView.setTextColor(Color.BLACK);
					verticalLengthTextView.setTextColor(Color.BLACK);
				}
				arg0.setVisibility(View.VISIBLE);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				horizentalLengthTextView.setText("横向距离");
				horizentalLengthTextView.setText("纵向距离");
			}

		});
	}
}
