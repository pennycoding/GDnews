package com.gdnews.ui;

import com.gdnews.news.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MySkin extends Activity {

	ImageView titlereturn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_skin_sitting);

		titlereturn = (ImageView) this.findViewById(R.id.titlereturn);

		titlereturn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				finish();

			}
		});

	}

}
