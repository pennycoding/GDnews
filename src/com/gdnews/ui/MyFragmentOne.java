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

	// 实现一个访问服务器的方法
	public String getJSONFromServer(String url) {

		String result = "";

		// 如果不带任何参数情况下，可以这样构造get请求
		HttpGet httpget = new HttpGet(url);

		HttpClient client = new DefaultHttpClient();

		try {

			HttpResponse response = client.execute(httpget);

			result = EntityUtils.toString(response.getEntity(), "utf-8");// 获取服务器响应内容

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

	// JSON解释
	public void DisplayJSON(String jsonString) {
		try {

			JSONArray array = new JSONArray(jsonString);

			System.out.println("服务器返回了" + array.length() + "条新闻");

			// 网络下载后的数据存放在一个全局变量里面，方便后面索引
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

		// 构造函数的目的是为了把数据源传到适配器内部
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

		// 每一行的样式由该方法决定
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			
			
			

			LayoutInflater inflater = getActivity().getLayoutInflater();

			View temp = null;

			temp = inflater.inflate(R.layout.newslayout, null);
			// 把web上的标题放入newlayout中的title中
			TextView tv_title = (TextView) temp
					.findViewById(R.id.tv_style_title);
			tv_title.setText(webdata.list.get(position).news_title);
			// 把web上的概要放入newlayout中的summary中
			TextView tv_summary = (TextView) temp
					.findViewById(R.id.tv_style_summary);
			tv_summary.setText(webdata.list.get(position).news_summary);
			// 把web上的赞放入newlayout中的comment中
			TextView tv_goods = (TextView) temp
					.findViewById(R.id.tv_style_goods);
			tv_goods.setText("" + webdata.list.get(position).news_time);
			// 把web上的评论放入newlayout中的comment中
			TextView tv_comment = (TextView) temp
					.findViewById(R.id.tv_style_comment);
			tv_comment.setText("" + webdata.list.get(position).news_comment);
			// 把web上的图片放入newlayout中的image中
			ImageView iv = (ImageView) temp.findViewById(R.id.iv_style_image);
			new MyDownloadTask(iv)
					.execute(webdata.list.get(position).news_images);

			return temp;
		}

	}

	// 图片下载函数
	public Bitmap downloadImage(String url) {
		HttpClient httpclient = new DefaultHttpClient();

		HttpGet httpget = new HttpGet(url);

		Bitmap bitmap = null;

		try {
			HttpResponse resp = httpclient.execute(httpget);
			// 判断是否正确执行
			if (HttpStatus.SC_OK == resp.getStatusLine().getStatusCode()) {
				// 将返回内容转换为bitmap
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

	// 异步任务类
	public class MyDownloadTask extends AsyncTask<String, String, String> {

		// 下载下来的图片，存放在这里
		private Bitmap bmp;

		ImageView imageView;

		int width;
		int height;

		// 获得一个外部ImageView的引用
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
