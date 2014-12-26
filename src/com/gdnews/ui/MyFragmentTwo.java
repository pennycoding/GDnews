package com.gdnews.ui;

import com.gdnews.news.R;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MyFragmentTwo extends Fragment {

	RelativeLayout rl_switch_notification, layout_jie;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View myView = inflater
				.inflate(R.layout.style_sitting, container, false);

		rl_switch_notification = (RelativeLayout) myView
				.findViewById(R.id.rl_switch_notification);
		layout_jie = (RelativeLayout) myView.findViewById(R.id.layout_jie);
		
		rl_switch_notification.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();

				intent.setClass(getActivity(), MyConformation.class);

				startActivity(intent);

			}
		});

		layout_jie.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();

				intent.setClass(getActivity(), MySkin.class);

				startActivity(intent);

			}
		});

		return myView;
	}

}
