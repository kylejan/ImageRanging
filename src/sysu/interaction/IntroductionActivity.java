package sysu.interaction;

import sysu.interaction.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class IntroductionActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.introduction);
		playAnimation();
	}
	
	private void playAnimation() {
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(500);
					Intent intent = new Intent();
					intent.setClass(IntroductionActivity.this,
							CameraOrPicturesActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
					IntroductionActivity.this.finish();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
}
