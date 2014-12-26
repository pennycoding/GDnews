package com.gdnews.ui;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import com.gdnews.bean.User_imformation;
import com.gdnews.model.WebComment;
import com.gdnews.news.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DemoCommentData extends Activity {

	Button btn_comment;
	EditText et_comment;
	String comment;
	ImageView titlereturn;
	WebComment webcomment = null;
	ListView lv;
	User_imformation ui;
	String n = "";
	String id = "";
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.news_comment_layout);

		btn_comment = (Button) this.findViewById(R.id.btn_comment);
		et_comment = (EditText) this.findViewById(R.id.et_comment);
		titlereturn = (ImageView) this.findViewById(R.id.titlereturn);
		lv = (ListView) this.findViewById(R.id.lv_comment);
		ui = (User_imformation) getApplication();
		intent = new Intent();
		n = ui.getnewid();
		id = ui.getuserid();

		new MyDownloadJSONTask()
				.execute("http://1.mygdmecapp.sinaapp.com/myapp_comment.php");

		titlereturn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				finish();

			}
		});

		btn_comment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				comment = et_comment.getText().toString().trim();
				String comment_id = ui.getuserid();
				if (comment_id.equals("")) {
					intent.setClass(DemoCommentData.this, User_land.class);
					startActivity(intent);
				} else if (comment.equals("")) {
					Toast.makeText(DemoCommentData.this, "请输入评论内容！！！", 3000).show();
				} else {
					new MyDownloadJSONTask()
							.execute("http://1.mygdmecapp.sinaapp.com/myapp_mycomment.php");
					Toast.makeText(DemoCommentData.this, "评论成功", 3000).show();
					/*intent.setClass(DemoCommentData.this, DemoCommentData.class);
					startActivity(intent);
					finish();*/
					et_comment.setText("");
				}
				

				new MyDownloadJSONTask()
				.execute("http://1.mygdmecapp.sinaapp.com/myapp_comment.php");

			}
		});

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

			// et_json.setText(result);

		}

	}

	// 实现一个访问服务器的方法
	public String getJSONFromServer(String url) {

		String result = "";

		// Post运作传送变数必须用NameValuePair[]阵列储存
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("n", n));
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("comment", comment));
		System.out.println("ssss" + n + id + comment);
		// 发送post请求
		HttpPost httpRequest = new HttpPost(url);

		try {
			// 发出HTTP请求
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// 取得默认的HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// 取得HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils
						.toString(httpResponse.getEntity(), "UTF-8");

				return result;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			System.out.println("服务器返回了" + array.length() + "条评论");

			// 网络下载后的数据存放在一个全局变量里面，方便后面索引
			webcomment = new WebComment(array);

			MyAdapter adapter = new MyAdapter(webcomment);

			lv.setAdapter(adapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class MyAdapter extends BaseAdapter {

		WebComment webcomment;

		// 构造函数的目的是为了把数据源传到适配器内部
		public MyAdapter(WebComment webcomment) {
			this.webcomment = webcomment;

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return webcomment.list.size();
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

			LayoutInflater inflater = DemoCommentData.this.getLayoutInflater();

			View temp = null;

			temp = inflater.inflate(R.layout.comment_item_layout, null);
			// 把web上的标题放入newlayout中的title中
			TextView comment_content = (TextView) temp
					.findViewById(R.id.tv_friendcomment);
			comment_content
					.setText(webcomment.list.get(position).comment_content);
			// 把web上的概要放入newlayout中的summary中
			TextView user_name = (TextView) temp
					.findViewById(R.id.tv_fiendname);
			user_name.setText(webcomment.list.get(position).user_name);
			// 把web上的赞放入newlayout中的comment中
			TextView comment_date = (TextView) temp
					.findViewById(R.id.tv_comment_time);
			comment_date.setText(""
					+ webcomment.list.get(position).comment_date);
			// 把web上的图片放入newlayout中的image中
			ImageView iv = (ImageView) temp.findViewById(R.id.imageView1);
			new MyDownloadTask(iv)
					.execute(webcomment.list.get(position).user_image);

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

}
