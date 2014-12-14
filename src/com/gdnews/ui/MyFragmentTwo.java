package com.gdnews.ui;

import com.gdnews.news.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragmentTwo extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View myView=inflater.inflate(R.layout.style_sitting, container, false);
		
		return myView;
	}
	
}
