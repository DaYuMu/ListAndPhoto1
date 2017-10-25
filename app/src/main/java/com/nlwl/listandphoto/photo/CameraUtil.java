package com.nlwl.listandphoto.photo;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * 照相机工具类
 *
 * @author iceforg
 *
 * Created by Administrator on 2016-12-31.
 */

public class CameraUtil {
	/**
	 * Create a file Uri for saving an image or video
	 */
	public static Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/**
	 * Create a File for saving an image or video
	 */
	private static File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = null;
		try {
			// This location works best if you want the created images to be
			// shared
			// between applications and persist after your app has been
			// uninstalled.
			mediaStorageDir = new File(
					Environment
							.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
					"ElevatorManager");


		} catch (Exception e) {
			e.printStackTrace();
		}
		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				// 在SD卡上创建文件夹需要权限：
				// <uses-permission
				// android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
				return null;
			}
		}
		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		return mediaFile;
	}
}
