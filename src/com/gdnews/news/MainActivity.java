package com.gdnews.news;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
//import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gdnews.ui.MyDialog;
import com.gdnews.ui.MyFragmentFour;
import com.gdnews.ui.MyFragmentOne;
import com.gdnews.ui.MyFragmentThree;
import com.gdnews.ui.MyFragmentTwo;
public class MainActivity extends Activity implements OnClickListener {

	private LinearLayout ll_news, ll_Website, ll_service, ll_config;
	private ImageView iv_land;

	private MyFragmentOne fragmentOne;
	private MyFragmentTwo fragmentTwo;
	private MyFragmentThree fragmentThree;
	private MyFragmentFour fragmentFour;

	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ll_news = (LinearLayout) this.findViewById(R.id.ll_news);
		ll_Website = (LinearLayout) this.findViewById(R.id.ll_Website);
		ll_service = (LinearLayout) this.findViewById(R.id.ll_service);
		ll_config = (LinearLayout) this.findViewById(R.id.ll_config);

		iv_land = (ImageView) this.findViewById(R.id.iv_land);

		ll_news.setOnClickListener(this);
		ll_Website.setOnClickListener(this);
		ll_service.setOnClickListener(this);
		ll_config.setOnClickListener(this);

		fragmentManager = getFragmentManager();

		iv_land.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				final Dialog dialog = new MyDialog(MainActivity.this,
						R.style.dialog);
				LayoutInflater layoutInflator = (LayoutInflater) MainActivity.this
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View temp = layoutInflator.inflate(R.layout.style_land, null);

				Button btn_confirm = (Button) temp
						.findViewById(R.id.btn_confirm);

				final EditText et_user = (EditText) temp
						.findViewById(R.id.et_user);
				final EditText et_password = (EditText) temp
						.findViewById(R.id.et_password);

				ImageView iv_close = (ImageView) temp
						.findViewById(R.id.iv_close);

				btn_confirm.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						String user = et_user.getText().toString();
						String password = et_password.getText().toString();

						if (dialog != null) {
							dialog.dismiss();
						}
					}
				});
				iv_close.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if (dialog != null) {
							dialog.dismiss();
						}
					}
				});

				dialog.setContentView(temp);

				dialog.setCanceledOnTouchOutside(true);

				dialog.show();

			}
		});

	}

	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		int id = arg0.getId();

		switch (id) {
		case R.id.ll_news:
			setTabSelection(0);
			System.out.println("ll_news");
			break;
		case R.id.ll_Website:
			setTabSelection(1);
			System.out.println("ll_Website");
			break;
		case R.id.ll_service:
			setTabSelection(2);
			System.out.println("ll_service");
			break;
		default:
			setTabSelection(3);
			System.out.println("ll_config");
			break;
		}

	}

	private void setTabSelection(int index) {

		// FragmentTransaction ר�Ÿ���Fragment�л�
		FragmentTransaction transaction = fragmentManager.beginTransaction();

		hideFragments(transaction);

		switch (index) {

		case 0:
			if (fragmentOne == null) {
				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				fragmentOne = new MyFragmentOne();
				transaction.add(R.id.content_frame, fragmentOne);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(fragmentOne);
			}
			System.out.println("fragmentOne");
			break;
		case 1:
			if (fragmentTwo == null) {
				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				fragmentTwo = new MyFragmentTwo();
				transaction.add(R.id.content_frame, fragmentTwo);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(fragmentTwo);
			}
			System.out.println("fragmentTwo");
			break;
		case 2:
			if (fragmentThree == null) {
				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				fragmentThree = new MyFragmentThree();
				transaction.add(R.id.content_frame, fragmentThree);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(fragmentThree);
			}
			System.out.println("fragmentThree");
			break;
		default:
			if (fragmentFour == null) {
				// ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
				fragmentFour = new MyFragmentFour();
				transaction.add(R.id.content_frame, fragmentFour);
			} else {
				// ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
				transaction.show(fragmentFour);
			}
			System.out.println("fragmentFour");
			break;

		}

		// ��Ч
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
		if (fragmentFour != null) {
			transaction.hide(fragmentFour);
		}
	}

}
