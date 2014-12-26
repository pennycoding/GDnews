package com.gdnews.ui;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import com.gdnews.model.WebIssue;
import com.gdnews.news.R;
import com.gdnews.utils.RefreshableView;
import com.gdnews.utils.RefreshableView.PullToRefreshListener;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MyFragmentThree extends Fragment {

	WebIssue webIssue = null;
	ListView lists; 
	RefreshableView refresh_showlove;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		
		View myView=inflater.inflate(R.layout.my_confession, container, false);
		
		ImageView issue_img = (ImageView) myView.findViewById(R.id.issue_img);
		
		lists = (ListView) myView.findViewById(R.id.clist);

		refresh_showlove =  (RefreshableView)myView.findViewById(R.id.refreshable_view);
		
		new MyDownloadJSONTask()
		.execute("http://1.mygdmecapp.sinaapp.com/myapp_confession.php");
		
		issue_img.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.setClass(getActivity(), IssueConfession.class);
				startActivity(intent);
				
			}
		});
		
		myRefresh();
		
		return myView;
	}
	
	public class MyDownloadJSONTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub

			return getJSONFromServer(arg0[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			DisplayJSON(result);

			//et_json.setText(result);

		}

	}

	// ʵ��һ�����ʷ������ķ���
	public String getJSONFromServer(String url) {

		String result = "";

		// ��������κβ�������£�������������get����
		HttpGet httpget = new HttpGet(url);

		HttpClient client = new DefaultHttpClient();

		try {

			HttpResponse response = client.execute(httpget);

			result = EntityUtils.toString(response.getEntity(), "utf-8");// ��ȡ��������Ӧ����

			return result;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	// JSON����
	public void DisplayJSON(String jsonString) {
		try {

			JSONArray array = new JSONArray(jsonString);

			System.out.println("������������" + array.length() + "�����");

			// �������غ�����ݴ����һ��ȫ�ֱ������棬�����������
			webIssue = new WebIssue(array);

			MyAdapter adapter = new MyAdapter(webIssue);

			lists.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class MyAdapter extends BaseAdapter {

		WebIssue webIssue;

		// ���캯����Ŀ����Ϊ�˰�����Դ�����������ڲ�
		public MyAdapter(WebIssue webIssue) {
			this.webIssue = webIssue;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return webIssue.lists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		// ÿһ�е���ʽ�ɸ÷�������
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			LayoutInflater inflater = getActivity().getLayoutInflater();

			View temp = null;

			temp = inflater.inflate(R.layout.confessionlayout, null);
			// ��web�ϵ�to����confessionlayout�е�to��
			TextView ic_to = (TextView) temp
					.findViewById(R.id.ic_tv_to);
			ic_to.setText(webIssue.lists.get(position).confession_to);
			// ��web�ϵ����ݷ���confessionlayout�е�content��
			TextView ic_content = (TextView) temp
					.findViewById(R.id.ic_tv_content);
			ic_content.setText(webIssue.lists.get(position).confession_content);
			// ��web�ϵ�from����confessionlayout�е�from��
			TextView ic_from = (TextView) temp
					.findViewById(R.id.ic_tv_from);
			ic_from.setText(webIssue.lists.get(position).confession_from);
			// ��web�ϵ�date����confessionlayout�е�date��
			TextView ic_date = (TextView) temp
					.findViewById(R.id.ic_tv_date);
			ic_date.setText(""+webIssue.lists.get(position).confession_date);

			return temp;
		}
	}
	
	
	
	public void myRefresh(){
		refresh_showlove.setOnRefreshListener(new PullToRefreshListener(){
			public void onRefresh() {
				try {
					
					Thread.sleep(1000);

					new MyDownloadJSONTask()
					.execute("http://1.mygdmecapp.sinaapp.com/myapp_confession.php");
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refresh_showlove.finishRefreshing();
			}
		}, 0);
		
		
		
	}
}
