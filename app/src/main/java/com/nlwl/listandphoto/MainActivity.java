package com.nlwl.listandphoto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nlwl.listandphoto.PostRequest.PostRequestActivity;
import com.nlwl.listandphoto.list.ListActivity;
import com.nlwl.listandphoto.photo.PhotoActivity;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void toList(View view) {

		Intent intent = new Intent(MainActivity.this, ListActivity.class);
		startActivity(intent);

	}

	public void toPhoto(View view) {

		Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
		startActivity(intent);

	}

	public void toWebView(View view) {

		Intent intent = new Intent(MainActivity.this, PostRequestActivity.class);
		startActivity(intent);
	}
}
