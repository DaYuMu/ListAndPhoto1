package com.nlwl.listandphoto.list;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.nlwl.listandphoto.R;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

	TextView tv_list;

	ImageView back;

	private ListView faultStyleListView;//用于弹出popwindow的数据加载
	private ArrayList<String> mFaultTypeData = new ArrayList<>();//故障类型数据
	private PopupWindow mFaultStylePopupWindow;//popwindow
	private EditText faultDescription;

	private int clickFaultTypePos =  0;//点击故障类型的位置



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		initview();

	}


	/**
	 * 知识库中的搜索功能
	 */
	public void initview() {

		mFaultTypeData.add("第一个");
		mFaultTypeData.add("第二个");
		mFaultTypeData.add("第三个");
		mFaultTypeData.add("第四个");
		mFaultTypeData.add("第五个");
		mFaultTypeData.add("第六个");
		mFaultTypeData.add("第七个");


		tv_list = (TextView) findViewById(R.id.tv_list);
		tv_list.setText("全部");
		tv_list.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showFaultStylePopWindow();
			}
		});

		back = (ImageView) findViewById(R.id.list_back);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	/**
	 * 点击faultStyle组件弹出popwindow
	 * <p>
	 * created at 2016-12-20 上午 11:16
	 */
	private void showFaultStylePopWindow() {
		initFaultStyleListView();
		DisplayMetrics dm = new DisplayMetrics();// 获取屏幕密度（方法2）
		dm = getResources().getDisplayMetrics();
		float screenHeight = dm.heightPixels;
		mFaultStylePopupWindow = new PopupWindow(
				faultStyleListView, tv_list.getWidth(), (int) screenHeight / 6);
		mFaultStylePopupWindow.setOutsideTouchable(true);//设置点击外部区域自动消失
		mFaultStylePopupWindow.setBackgroundDrawable(new BitmapDrawable());//设置空的背景点击响应事件
		mFaultStylePopupWindow.setFocusable(true);//设置可获取焦点
		mFaultStylePopupWindow.showAsDropDown(tv_list, 0, 3);
	}

	/**
	 * 初始化listview组件，在加载popwindow布局中
	 * <p>
	 * created at 2016-12-20 上午 11:17
	 */
	private void initFaultStyleListView() {
		faultStyleListView = new ListView(this);
		faultStyleListView.setDivider(new ColorDrawable(0xffd4d5d6));
		faultStyleListView.setDividerHeight(2);
		faultStyleListView.setBackgroundResource(R.drawable.listview_background);
		faultStyleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				tv_list.setText(mFaultTypeData.get(position));
				clickFaultTypePos = position;
				mFaultStylePopupWindow.dismiss();

				Toast.makeText(ListActivity.this, "点击的是"+mFaultTypeData.get(position)+"下标为"+position, Toast.LENGTH_SHORT).show();

			}
		});


		faultTypeAdapter = new FaultStyleAdapter(this, mFaultTypeData);
		faultStyleListView.setAdapter(faultTypeAdapter);

	}

	private FaultStyleAdapter faultTypeAdapter;//diaog中listview适配器

}
