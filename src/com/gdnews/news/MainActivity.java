package com.gdnews.news;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.gdnews.bean.User_imformation;
import com.gdnews.news.WiperSwitch.OnChangedListener;
import com.gdnews.ui.Landing;
//import com.gdnews.ui.MyFragmentFour;
import com.gdnews.ui.MyFragmentOne;
import com.gdnews.ui.MyFragmentThree;
import com.gdnews.ui.MyFragmentTwo;
import com.gdnews.ui.User_land;

public class MainActivity extends Activity implements OnClickListener,
		OnChangedListener {

	private LinearLayout ll_news, ll_Website, ll_service, ll_config;
	private ImageView iv_land;
	private MyFragmentOne fragmentOne;
	private MyFragmentTwo fragmentTwo;
	private MyFragmentThree fragmentThree;
	//private MyFragmentFour fragmentFour;
	private FragmentManager fragmentManager;
	User_imformation ui;
	String userid;
	Intent intent = new Intent();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ll_news = (LinearLayout) this.findViewById(R.id.ll_news);
		//ll_Website = (LinearLayout) this.findViewById(R.id.ll_Website);
		ll_service = (LinearLayout) this.findViewById(R.id.ll_service);
		ll_config = (LinearLayout) this.findViewById(R.id.ll_config);

		iv_land = (ImageView) this.findViewById(R.id.iv_land);

		ll_news.setOnClickListener(this);
		//ll_Website.setOnClickListener(this);
		ll_service.setOnClickListener(this);
		ll_config.setOnClickListener(this);
		fragmentManager = getFragmentManager();
		
		ui = (User_imformation) getApplication();
		
		userid = ui.getuserid();
		
		setTabSelection(0);

		iv_land.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println(userid);
				if(userid.equals("")) {
					intent.setClass(MainActivity.this,User_land.class);
					startActivity(intent);
					finish();
				}else{
					intent.setClass(MainActivity.this,Landing.class);
					startActivity(intent);
					finish();
				}
				
			}
		});
		
	}

	public void OnChanged(WiperSwitch wiperSwitch, boolean checkState) {
		Log.e("log", "" + checkState);
	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		int id = arg0.getId();

		switch (id) {
		case R.id.ll_news:
			setTabSelection(0);
			System.out.println("ll_news");
			break;
		/*case R.id.ll_Website:
			setTabSelection(3);
			System.out.println("ll_Website");
			break;*/
		case R.id.ll_service:
			setTabSelection(2);
			System.out.println("ll_service");
			break;
		default:
			setTabSelection(1);
			System.out.println("ll_config");
			break;
		}

	}

	private void setTabSelection(int index) {

		// FragmentTransaction 专门负责Fragment切换
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		hideFragments(transaction);

		switch (index) {

		case 0:
			if (fragmentOne == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				fragmentOne = new MyFragmentOne();
				transaction.add(R.id.content_frame, fragmentOne);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fragmentOne);
			}
			System.out.println("fragmentOne");
			break;
		case 1:
			if (fragmentTwo == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				fragmentTwo = new MyFragmentTwo();
				transaction.add(R.id.content_frame, fragmentTwo);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fragmentTwo);			
			}
			System.out.println("fragmentTwo");
			break;
		default:
			if (fragmentThree == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				fragmentThree = new MyFragmentThree();
				transaction.add(R.id.content_frame, fragmentThree);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fragmentThree);
			}
			System.out.println("fragmentThree");
			break;
			/*default:
			if (fragmentFour == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				fragmentFour = new MyFragmentFour();
				transaction.add(R.id.content_frame, fragmentFour);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(fragmentFour);
			}
			System.out.println("fragmentFour");
			break;*/

		}

		// 生效
		transaction.commit();

	}

	private void hideFragments(FragmentTransaction transaction) {
		if (fragmentOne != null) {
			transaction.hide(fragmentOne);
		}
		if (fragmentTwo != null) {
			transaction.hide(fragmentTwo);
		}
		if (fragmentThree != null) {
			transaction.hide(fragmentThree);
		}
		/*if (fragmentFour != null) {
			transaction.hide(fragmentFour);
		}*/
	}

}
