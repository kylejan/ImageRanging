package sysu.interaction;

import java.io.FileNotFoundException;

import sysu.graphic.GraphicDraw;
import sysu.interaction.R;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class CameraOrPicturesActivity extends Activity {
	public static Bitmap bitmap;
	private Button picButton;
	private Button cameraButton;
	private Button configButton;
	private Button historyButton;
	private Button homeButton;
	private static int TAKE_PICTURE = 1;
	private static int GET_PICTURE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camer_or_pictures);
		picButton = (Button) findViewById(R.id.pic_but);
		cameraButton = (Button) findViewById(R.id.camera_but);
		configButton = (Button) findViewById(R.id.config_but);
		historyButton = (Button) findViewById(R.id.history_but);
		homeButton = (Button) findViewById(R.id.home_but);
		
		picButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, GET_PICTURE);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});

		cameraButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, TAKE_PICTURE);
				overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
			}
		});
		configButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CameraOrPicturesActivity.this,
						ConfigMenuActivity.class);
				startActivity(intent);
			}
		});
		
		homeButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				
			}
		});
		
		historyButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(CameraOrPicturesActivity.this,
						HistoryActivity.class);
				startActivity(intent);
			}
		});

	}

	// 处理获取的相片
	public void onActivityResult(int requestCode, int resultCode,
			Intent data) {
		// 从相册获取的
		if (requestCode == GET_PICTURE && resultCode == RESULT_OK) {
			handlePictureFromMobile(data);
		} // 从照相机获取的
		else if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK) {
			handlePictureFromCamera(data);
		} else
			return;
		super.onActivityResult(requestCode, resultCode, data);
		Intent intent = new Intent();
		intent.setClass(CameraOrPicturesActivity.this,
				SetRectangleActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}

	private void handlePictureFromMobile(Intent data) {
		Uri uri = data.getData();
		ContentResolver cr = this.getContentResolver();
		try {
			bitmap = BitmapFactory.decodeStream(cr
					.openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void handlePictureFromCamera(Intent data) {
		Uri imageUri = null;
		// Check if the result includes a thumbnail Bitmap
		if (data != null) {
			if (data.hasExtra("data")) {
				bitmap = data.getParcelableExtra("data");
			}
		}
	}
}
