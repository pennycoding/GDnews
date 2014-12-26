package com.gdnews.ui;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.gdnews.model.WebData;
import com.gdnews.news.R;
import com.gdnews.utils.RefreshableView;
import com.gdnews.utils.RefreshableView.PullToRefreshListener;

public class MyFragmentOne extends Fragment {
	
	ListView lv;

	WebData webdata = null;
	
	RefreshableView refresh_hotnews;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		

		View myView = inflater.inflate(R.layout.fragment_layout_1, container,
				false);
		
		
		

		lv = (ListView) myView.findViewById(R.id.lv);
		
		
		refresh_hotnews =  (RefreshableView)myView.findViewById(R.id.refreshable_view);

		new MyDownloadJSONTask()
				.execute("http://1.mygdmecapp.sinaapp.com/myapp_news.php");
		
		

		
		

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				if (webdata != null) {
					String news_URL = webdata.list.get(position).news_url;

					Intent intent = new Intent();

					intent.setClass(getActivity(), news_webView.class);

					String setid = String.valueOf(position+1);
					
					intent.putExtra("newsURL", news_URL);
					intent.putExtra("newsid", setid);

					startActivity(intent);
					getActivity().finish();
				}

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

			System.out.println("������������" + array.length() + "������");

			// �������غ�����ݴ����һ��ȫ�ֱ������棬�����������
			webdata = new WebData(array);

			MyAdapter adapter = new MyAdapter(webdata);

			lv.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class MyAdapter extends BaseAdapter {

		WebData webdata;

		// ���캯����Ŀ����Ϊ�˰�����Դ�����������ڲ�
		public MyAdapter(WebData webdata) {
			this.webdata = webdata;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return webdata.list.size();
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

			temp = inflater.inflate(R.layout.newslayout, null);
			// ��web�ϵı������newlayout�е�title��
			TextView tv_title = (TextView) temp
					.findViewById(R.id.tv_style_title);
			tv_title.setText(webdata.list.get(position).news_title);
			// ��web�ϵĸ�Ҫ����newlayout�е�summary��
			TextView tv_summary = (TextView) temp
					.findViewById(R.id.tv_style_summary);
			tv_summary.setText(webdata.list.get(position).news_summary);
			// ��web�ϵ��޷���newlayout�е�comment��
			TextView tv_goods = (TextView) temp
					.findViewById(R.id.tv_style_goods);
			tv_goods.setText("" + webdata.list.get(position).news_time);
			// ��web�ϵ����۷���newlayout�е�comment��
			TextView tv_comment = (TextView) temp
					.findViewById(R.id.tv_style_comment);
			tv_comment.setText("" + webdata.list.get(position).news_comment);
			// ��web�ϵ�ͼƬ����newlayout�е�image��
			ImageView iv = (ImageView) temp.findViewById(R.id.iv_style_image);
			new MyDownloadTask(iv)
					.execute(webdata.list.get(position).news_images);

			return temp;
		}

	}

	// ͼƬ���غ���
	public Bitmap downloadImage(String url) {
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url);

		Bitmap bitmap = null;

		try {
			HttpResponse resp = httpclient.execute(httpget);
			// �ж��Ƿ���ȷִ��
			if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
				// ����������ת��Ϊbitmap
				HttpEntity entity = resp.getEntity();
				InputStream in = entity.getContent();
				bitmap = BitmapFactory.decodeStream(in);

				return bitmap;

			}

		} catch (Exception e) {
			e.printStackTrace();
			// setTitle(e.getMessage());
		} finally {
			httpclient.getConnectionManager().shutdown();
		}

		return null;
	}

	// �첽������
	public class MyDownloadTask extends AsyncTask<String, String, String> {

		// ����������ͼƬ�����������
		private Bitmap bmp;

		ImageView imageView;

		int width;
		int height;

		// ���һ���ⲿImageView������
		public MyDownloadTask(ImageView imageView) {
			this.imageView = imageView;
			height = imageView.getHeight();
			width = imageView.getWidth();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result.equals("OK")) {
				imageView.setImageBitmap(bmp);
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			imageView.setImageResource(R.drawable.titleimage);
		}

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			
			
			

			bmp = downloadImage(arg0[0]);

			if (bmp != null)
				return "OK";
			else
				return "FAIL";
		}

	}
	
	
	
	public void myRefresh(){
		refresh_hotnews.setOnRefreshListener(new PullToRefreshListener(){
			public void onRefresh() {
				try {
					
					Thread.sleep(1000);
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refresh_hotnews.finishRefreshing();
			}
		}, 0);
		
		
		
	}

}
