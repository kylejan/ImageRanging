package sysu.interaction;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ConfigMenuActivity extends Activity {

	private ListView listView;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        listView = new ListView(this);
        String[] itemList = {"处理XML数据文件", "软件使用说明", "关于本软件"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, itemList);
        
        listView.setAdapter(adapter);
        setContentView(listView);
        
        OnItemClickListener listener = new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0)
				{
					Intent intent = new Intent();
					intent.setClass(ConfigMenuActivity.this, ProcessXMLActivity.class);
					startActivity(intent);
				}
				else if (arg2 == 1)
					Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
			}	
        };
        listView.setOnItemClickListener(listener);
    }
}
