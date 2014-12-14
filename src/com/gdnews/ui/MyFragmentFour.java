package com.gdnews.ui;

import java.util.ArrayList;

import com.gdnews.news.R;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class MyFragmentFour extends Fragment {

	//容器类控件
	private ViewPager viewPager;
	
	//数据源
	private ArrayList<View> viewList;
	
	// private PagerAdapter 
	MyPagerAdapter pagerAdapter;
	
	DemoCommentData democommentdata=new DemoCommentData() ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View myView=inflater.inflate(R.layout.fragment_layout_4, container, false);
		
		viewPager=(ViewPager)myView.findViewById(R.id.viewPager);
		
		
		
		//数据源加载
		loadView();
		
		pagerAdapter=new MyPagerAdapter();
		
		viewPager.setAdapter(pagerAdapter);
		
		return myView;
	}
	
	public void loadView()
	{
		viewList=new ArrayList<View>();
		
		//导入wevView
		View temp=getActivity().getLayoutInflater().inflate(R.layout.news_content_layout, null);
		
	
		viewList.add(temp);
		
        View temp2=getActivity().getLayoutInflater().inflate(R.layout.news_comment_layout, null);
		
		viewList.add(temp2);
	
		
	}
	
	
	//适配器
	public class MyPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return viewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
		
			View target=viewList.get(position);
            if(position==0)
           {
			 WebView webView=(WebView)target.findViewById(R.id.news_content);
			
		   	webView.loadUrl("http://192.168.0.200/jidiannews2.html");
           }
            else
            {
              ListView lv=(ListView)target.findViewById(R.id.lv_comment);	
            
              SimpleAdapter adapter=new SimpleAdapter(
            		  getActivity(),
            		  democommentdata.list,
            		  R.layout.comment_item_layout,
            		  new String[]{"friendName","comment","staus","timestamp"},
            		  new int[]{R.id.tv_fiendname,R.id.tv_friendcomment,R.id.tv_commentstatus,R.id.tv_comment_time}
           		  
            		  );
              
              lv.setAdapter(adapter); 
              
            }
            
			((ViewPager) container).addView(target);
			return viewList.get(position);

		}
	
		
	}
	
}
