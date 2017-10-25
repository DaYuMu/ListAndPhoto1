package com.nlwl.listandphoto.photo;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.nlwl.listandphoto.R;


/**
 * 描述：加载框</br>
 * @author iceforg</br>
 * @version 2017-01-13 下午 8:05
 */
public class LoadingDialog {
	private Dialog alertDialog;
	public LoadingDialog(Context context) {
		alertDialog = new Dialog(context, R.style.circle_dialog);
		View view = LayoutInflater.from(context)
				.inflate(R.layout.circle_progress, null);
		alertDialog.setContentView(view);
		alertDialog.setCanceledOnTouchOutside(false);
	}
	public LoadingDialog(Context context, int themeResId) {

	}

	public void dismiss() {
		alertDialog.dismiss();
	}

	public void show() {
		alertDialog.show();
	}

}
