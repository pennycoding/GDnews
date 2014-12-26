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
					Toast.makeText(DemoCommentData.this, "�������������ݣ�����", 3000).show();
				} else {
					new MyDownloadJSONTask()
							.execute("http://1.mygdmecapp.sinaapp.com/myapp_mycomment.php");
					Toast.makeText(DemoCommentData.this, "���۳ɹ�", 3000).show();
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

	// ʵ��һ�����ʷ������ķ���
	public String getJSONFromServer(String url) {

		String result = "";

		// Post�������ͱ���������NameValuePair[]���д���
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("n", n));
		params.add(new BasicNameValuePair("id", id));
		params.add(new BasicNameValuePair("comment", comment));
		System.out.println("ssss" + n + id + comment);
		// ����post����
		HttpPost httpRequest = new HttpPost(url);

		try {
			// ����HTTP����
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// ȡ��Ĭ�ϵ�HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// ȡ��HttpResponse
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

	// JSON����
	public void DisplayJSON(String jsonString) {
		try {

			JSONArray array = new JSONArray(jsonString);

			System.out.println("������������" + array.length() + "������");

			// �������غ�����ݴ����һ��ȫ�ֱ������棬�����������
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

		// ���캯����Ŀ����Ϊ�˰�����Դ�����������ڲ�
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

		// ÿһ�е���ʽ�ɸ÷�������
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			LayoutInflater inflater = DemoCommentData.this.getLayoutInflater();

			View temp = null;

			temp = inflater.inflate(R.layout.comment_item_layout, null);
			// ��web�ϵı������newlayout�е�title��
			TextView comment_content = (TextView) temp
					.findViewById(R.id.tv_friendcomment);
			comment_content
					.setText(webcomment.list.get(position).comment_content);
			// ��web�ϵĸ�Ҫ����newlayout�е�summary��
			TextView user_name = (TextView) temp
					.findViewById(R.id.tv_fiendname);
			user_name.setText(webcomment.list.get(position).user_name);
			// ��web�ϵ��޷���newlayout�е�comment��
			TextView comment_date = (TextView) temp
					.findViewById(R.id.tv_comment_time);
			comment_date.setText(""
					+ webcomment.list.get(position).comment_date);
			// ��web�ϵ�ͼƬ����newlayout�е�image��
			ImageView iv = (ImageView) temp.findViewById(R.id.imageView1);
			new MyDownloadTask(iv)
					.execute(webcomment.list.get(position).user_image);

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

}
