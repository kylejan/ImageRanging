package sysu.interaction;

import java.io.File;

import org.w3c.dom.Text;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Environment;  
public class ProcessXMLActivity extends Activity{
	private Button processButton;
	private TextView hintText;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.process_xml);
		
		processButton = (Button)findViewById(R.id.process_but);
		hintText = (TextView)findViewById(R.id.hint_text);
		processButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File sdcardDir = Environment.getExternalStorageDirectory();
				
				String fromPath = sdcardDir + "/input.xml";
				String fname = sdcardDir + "/output.xml";
				if (ParseAndSaveXML.process(fromPath, fname))
				{
					Toast.makeText(getApplicationContext(), "数据处理成功！", Toast.LENGTH_SHORT).show();
					hintText.setText("已将output.xml文件放在内存卡根目录下");
				}
//				double[] data = ParseAndSaveXML.parseXML(fromPath);
//				Toast.makeText(getApplicationContext(), data[0] + " " + data[1] + " " + data[2], Toast.LENGTH_SHORT).show();
			}
		});
	}
}
