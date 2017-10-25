package com.nlwl.listandphoto.photo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nlwl.listandphoto.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class PhotoActivity extends AppCompatActivity {

	private ImageView iv_photo;
	ImageView back;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);

		initView();

		setListener();


	}

	private Dialog dialog;

	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;//调用系统相机的请求码
	private Uri fileUri;//图片保存的路径
	public static final int MEDIA_TYPE_IMAGE = 1;//xiangji

	private void setListener() {

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		iv_photo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Log.e("photoActivity", "点击事件被点击");
//				Toast.makeText(PhotoActivity.this, "点击事件被点击", Toast.LENGTH_SHORT).show();

				dialog = new Dialog(PhotoActivity.this, R.style.dialog);
				dialog.setCanceledOnTouchOutside(true);

				View v = LayoutInflater.from(PhotoActivity.this)
						.inflate(R.layout.select_photos, null);

//				Toast.makeText(PhotoActivity.this, "v", Toast.LENGTH_SHORT).show();
				Button camera = (Button) v.findViewById(R.id.camera);
				Button picture = (Button) v.findViewById(R.id.picture);
				camera.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						// create a file to save the image
						fileUri = CameraUtil.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
						// 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null
						// ，如果此处指定，则后来的data为null
						// set the image file name
						intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
						startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

						dialog.dismiss();
					}
				});
				picture.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						Bimp.bmp.clear();
						Intent intent =
								new Intent(PhotoActivity.this,
										ChoosePhotosActivity.class);
						startActivityForResult(intent, 10);

						dialog.dismiss();
					}
				});


				//设置它的ContentView
				{
					dialog.setContentView(v);
				}
				dialog.show();

			}
		});

	}

	private void initView() {
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		back = (ImageView) findViewById(R.id.photo_back);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);


		/*进行选择照片之后的操作*/
		if (requestCode == 10) {
			if (Bimp.bmp.size() != 0) {
				iv_photo.setImageBitmap(Bimp.bmp.get(0));
			}
		}


		/*进行拍照之后的操作*/
		if (CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE == requestCode) {

			if (RESULT_OK == resultCode) {

				// Check if the result includes a thumbnail Bitmap
				if (data != null) {
					// 没有指定特定存储路径的时候

					// 指定了存储路径的时候（intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);）
					// Image captured and saved to fileUri specified in the
					// Intent

					if (data.hasExtra("data")) {
						Bitmap thumbnail = data.getParcelableExtra("data");

						String fileName = "" + System.currentTimeMillis();
						Bitmap compressbitmap = compressImage(thumbnail, fileName);// 将图片进行压缩
						Log.e("camera.bitmap", "放入了照片");
						Bimp.bmp.add(0, compressbitmap);
						Bimp.path.add(0, imageDir_camera + ALBUM_PATH_camera + "/"
								+ fileName + ".jpg");
						Log.e("camera.bitmap", "照片"+Bimp.bmp.toString());
						Log.e("camera.bitmap", "照片"+Bimp.path.toString());

						Bimp.bmp.add(0, thumbnail);
						Bimp.path.add(0, fileUri.getPath());
//						imageRecyclerAdapter.add(Bimp.bmp);
//						imageRecyclerAdapter.notifyDataSetChanged();

						iv_photo.setImageBitmap(Bimp.bmp.get(0));
					}
				} else {
					/*有指定路径的时候，现在有指定路径，所以程序进入else中*/

					// If there is no thumbnail image data, the image
					// will have been stored in the target output URI.

					// Resize the full image to fit in out image view.
					/*int width = imageRecyclerAdapter.getWidth();
					int height = imageRecyclerAdapter.getHeight();*/

					Log.e("width_height", "得到宽和高");

					BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

					factoryOptions.inJustDecodeBounds = true;

					/*没有注释掉之前，不论拍几张照片都只显示第一张照片，-_-*/
//					BitmapFactory.decodeFile(fileUri.getPath(), factoryOptions);

					/*int imageWidth = factoryOptions.outWidth;
					int imageHeight = factoryOptions.outHeight;*/

					// Determine how much to scale down the image
					/*int scaleFactor = Math.min(imageWidth / width, imageHeight
							/ height);*/

//					factoryOptions.inSampleSize = scaleFactor;
					factoryOptions.inSampleSize =10 ;


					// Decode the image file into a Bitmap sized to fill the
					// View
					factoryOptions.inJustDecodeBounds = false;
					factoryOptions.inPurgeable = true;

					Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
							factoryOptions);
					Log.e("bitmappppppbitmap", bitmap.toString());

					String fileName = "" + System.currentTimeMillis();
					Log.e("bitmappppppfilename", fileName);
					Bitmap compressbitmap = compressImage(bitmap, fileName);//  将图片进行压缩

					Log.e("bitmappcompressbitmap", compressbitmap.toString());
//					Bimp.bmp.clear();
//					Bimp.path.clear();

					Bimp.bmp.add(0, compressbitmap);
					Bimp.path.add(0, imageDir_camera + ALBUM_PATH_camera + "/"
							+ fileName + ".jpg");
					Log.e("camera_compress", "图片压缩完成，已经放入集合");
					Log.e("camera_compress", "有"+Bimp.bmp.size()+"张图片，图片路径："+Bimp.path.get(0));


					/*为什么注释掉？？要进行图片的压缩*/
//					Bimp.bmp.add(0, bitmap);
//					Bimp.path.add(0, fileUri.getPath());

					iv_photo.setImageBitmap(Bimp.bmp.get(0));

//					imageRecyclerAdapter.add(Bimp.bmp);
//					imageRecyclerAdapter.notifyDataSetChanged();
				}
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}

	}



	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	/*得到系統時間*/
//	long thistime = System.currentTimeMillis() / 100000;

	final static String ALBUM_PATH_camera = "/Bitmap/Picture/camera";
	final static String imageDir_camera = CopyFileTools.getSDPath();
	BufferedOutputStream bos;

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
		File file = new File(imageDir_camera + ALBUM_PATH_camera);
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			bos =
					new BufferedOutputStream(
							new FileOutputStream(new File(imageDir_camera + ALBUM_PATH_camera + "/"
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
}
