package com.nlwl.listandphoto.photo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.nlwl.listandphoto.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * 选择图片，控制图片张数在9张之内
 */
public class ImageGridActivity extends Activity {
	public static final String EXTRA_IMAGE_LIST = "imagelist";


	private LoadingDialog loadingDialog;

	List<ImageItem> dataList;
	GridView gridView;
	ImageGridAdapter adapter;
	AlbumHelper helper;
	Button bt;

	private boolean isshow = false;

//	private LoadingDialog loadingDialog;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:
					Toast.makeText(ImageGridActivity.this, "只能选择1张图片", Toast.LENGTH_SHORT).show();
					break;

				case 1:
					loadingDialog.dismiss();
					break;
				default:
					break;
			}
			super.handleMessage(msg);
		}
	};

	private ByteArrayOutputStream baos = new ByteArrayOutputStream();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_image_grid);

		/*当点击完成时添加LoadingDialog对话框*/

		loadingDialog=new LoadingDialog(this);
//		loadingDialog = new LoadingDialog(this);

		/*测试一开始的时候是否会有dialog的显示----测试成功*/
//		loadingDialog.show();

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		/*递归删除文件和文件夹*/
		try {
			CopyFileTools.RecursionDeleteFile(file);
		} catch (Exception e) {

		}


		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_LIST);

		initView();


		/*选择图片完成的点击事件*/
		bt = (Button) findViewById(R.id.bt);
		bt.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {

				isshow = true;
				showdialog(isshow);

//				loadingDialog.show();

				/*选择图片完成的点击事件*/
//				loadingDialog.show();

				final ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext(); ) {
					list.add(it.next());
				}
				if (Bimp.act_bool) {
					setResult(Activity.RESULT_OK);
					Bimp.act_bool = false;
				}

				if (list == null) {
					return;
				}



				new Thread(new Runnable() {
					@Override
					public void run() {
						for (int i = 0; i < list.size(); i++) {
							loadingDialog.show();
							Log.e("loadingdialog_for", "loadingdialog----show");
							if (Bimp.bmp.size() < 10) {
								try {
							/*從相冊中獲得的圖片*/
									Bitmap bm = Bimp.revitionImageSize(list.get(i));
//							Bitmap bm=Bimp.bmp.get(i);

									String fileName = "" + System.currentTimeMillis();
							/*先进行尺寸压缩，再进行质量压缩，返回值是图片*/

//							Bitmap getimage = getimage(list.get(i), fileName);
//							Bitmap getimage = getimage(bm,fileName);
									Bitmap getimage = compressImage(bm, fileName);


									Bimp.bmp.add(0, getimage);
									Bimp.path.add(0, imageDir + ALBUM_PATH + "/"
											+ fileName + ".jpg");
								} catch (Exception e) {
									e.printStackTrace();
								}
							}

						}
						mHandler.sendEmptyMessage(1);
						finish();
					}
				}).start();


				/*if (index == list.size()) {
//					loadingDialog.dismiss();
					isshow = false;
					showdialog(isshow);

//					loadingDialog.dismiss();

				}*/
			}
		});


		/*得到当前手机的分辨率*/
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);

		width = metric.widthPixels;  // 宽度（PX）
		height = metric.heightPixels;  // 高度（PX）

		density = metric.density;  // 密度（0.75 / 1.0 / 1.5）
		densityDpi = metric.densityDpi;  // 密度DPI（120 / 160 / 240）
	}


	private static final int OVER = 1;



	private void showdialog(boolean isshow) {

		if (isshow == true) {
			loadingDialog.show();
			Log.e("loadingdialog", "loadingdialog----show");
		} else if (isshow == false) {
			loadingDialog.dismiss();
			Log.e("loadingdialog", "loadingdialog----dismiss");
		}
	}

	float width;  // 宽度（PX）
	float height;  // 高度（PX）

	float density;  // 密度（0.75 / 1.0 / 1.5）
	int densityDpi;  // 密度DPI（120 / 160 / 240）


	/*图片先进行尺寸压缩，再进行质量压缩*/
	private Bitmap getimage(String srcPath, String fileName) {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

		Log.e("compressImage", width + "-----" + height + "");

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
//		float hh = 800f;//这里设置高度为800f
//		float ww = 480f;//这里设置宽度为480f

		float hh = height;
		float ww = width;
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0) {
			be = 1;
		}
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		Log.e("compressImage", "已经进行了尺寸压缩，将要进行质量压缩");
		return compressImage(bitmap, fileName);//压缩好比例大小后再进行质量压缩
	}


	/*得到系統時間*/
//	long thistime = System.currentTimeMillis() / 100000;

	/*图片压缩并且返回压缩后的图片*/
	private Bitmap compressImage(Bitmap image, String srcpath) {
		image.compress(Bitmap.CompressFormat.JPEG, 80, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

		int options = 100;
		while (baos.toByteArray().length / 1024 > 50) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);
			//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
			if (options < 20) {
				options = 20;
			}
		}

		if (!file.exists()) {
			file.mkdirs();
		}

		try {
//			BufferedOutputStream bos =
//					new BufferedOutputStream(new FileOutputStream(new File(imageDir + ALBUM_PATH +
// "/"+srcpath.substring(srcpath.lastIndexOf("/")))));
			BufferedOutputStream bos =
					new BufferedOutputStream(
							new FileOutputStream(new File(imageDir + ALBUM_PATH + "/"
									+ srcpath + ".jpg")));


			bos.write(baos.toByteArray());
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ByteArrayInputStream isBm =
				new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中


		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}


	final static String ALBUM_PATH = "/Bitmap/Picture/Text";
	final static String imageDir = CopyFileTools.getSDPath();
	File file = new File(imageDir + ALBUM_PATH);


	/*质量压缩图片*/
	/*public static void compressImageToFile(Bitmap bmp,File file) {
		// 0-100 100为不压缩
		int options = 100;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		File myCaptureFile = new File(imageDir + ALBUM_PATH + "/" + file);
		// 把压缩后的数据存放到baos中
//		bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
		try {
			BufferedOutputStream bos =
					new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			bmp.compress(Bitmap.CompressFormat.JPEG, options, bos);
			bos.write(baos.toByteArray());
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/


	private void initView() {
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new ImageGridAdapter.TextCallback() {
			public void onListen(int count) {
				bt.setText("完 成" + "(" + count + ")");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				adapter.notifyDataSetChanged();
			}

		});

	}

	public void iv_action_left(View v) {
		finish();
	}

}
