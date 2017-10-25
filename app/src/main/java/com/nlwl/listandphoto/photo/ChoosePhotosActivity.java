package com.nlwl.listandphoto.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.nlwl.listandphoto.R;

import java.io.Serializable;
import java.util.List;

/**
 * 选择图片，，这里是图片的文件夹，然后点击进入图片选择
 */
public class ChoosePhotosActivity extends Activity {
	List<ImageBucket> dataList;
	GridView gridView;
	ImageBucketAdapter adapter;// 自定义的适配器
	AlbumHelper helper;
	public static final String EXTRA_IMAGE_LIST = "imagelist";
	public static Bitmap bimap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_image_bucket);
		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());
		initData();
		initView();
	}

	public void iv_action_left(View v){
		finish();
	}
	/**
	 * 初始化数据
	 */
	private void initData() {
		dataList = helper.getImagesBucketList(false);	
		bimap=BitmapFactory.decodeResource(
				getResources(),
				R.drawable.icon_addpic_unfocused);
	}

	/**
	 * 初始化view视图
	 */
	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		if (dataList == null) {
//			Toast.makeText(this, "暂时没有图片，建议拍照", Toast.LENGTH_SHORT).show();
			return;
		} else {
//			Toast.makeText(this, "进入有图片的判断", Toast.LENGTH_SHORT).show();
			/*添加文件夹是否有"ElevatorManager"的判断*/
			for (int i = 0; i < dataList.size(); i++) {
				if (dataList.get(i).equals("ElevatorManager")) {
					dataList.remove(i);
				}
			}
			adapter = new ImageBucketAdapter(ChoosePhotosActivity.this, dataList);

			gridView.setAdapter(adapter);

			gridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
					Intent intent = new Intent(ChoosePhotosActivity.this,
							ImageGridActivity.class);
					intent.putExtra(ChoosePhotosActivity.EXTRA_IMAGE_LIST,
							(Serializable) dataList.get(position).imageList);
					startActivityForResult(intent, 100);
					finish();
				}

			});
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode!=Activity.RESULT_OK){
			return;
		}
		
		switch (requestCode) {
		
		case 100:
			setResult(Activity.RESULT_OK);
			finish();
			break;

		default:
			break;
		}
	}
}
