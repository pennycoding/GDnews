package com.gdnews.ui;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import com.gdnews.bean.User_imformation;
import com.gdnews.news.MainActivity;
import com.gdnews.news.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Landing extends Activity {

	ImageView titlereturn, my_image;
	Button cancellation;
	User_imformation ui;
	Intent intent = new Intent();
	String userid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landsuccess);

		titlereturn = (ImageView) this.findViewById(R.id.titlereturn);
		my_image = (ImageView) this.findViewById(R.id.my_image);
		cancellation = (Button) this.findViewById(R.id.Cancellation);
		ui = (User_imformation) getApplication();
		userid = ui.getuserid();
		new MyDownloadTask(my_image).execute("http://1.mygdmecapp.sinaapp.com/myapp_landing.php");

		titlereturn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent.setClass(Landing.this, MainActivity.class);
				startActivity(intent);
				finish();

			}
		});

		cancellation.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				ui.setuserid("");
				intent.setClass(Landing.this, User_land.class);
				startActivity(intent);
				finish();
			}
		});

	}

	// ͼƬ���غ���
	public Bitmap downloadImage(String url) {
		// Post�������ͱ���������NameValuePair[]���д���
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("id", userid));

		Bitmap bitmap = null;

		// ����post����
		HttpPost httpRequest = new HttpPost(url);

		HttpClient httpClient = new DefaultHttpClient();
		try {
			// ����HTTP����
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			// ȡ��HttpResponse
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			// �ж��Ƿ���ȷִ��
			if (HttpStatus.SC_OK == httpResponse.getStatusLine()
					.getStatusCode()) {
				// ����������ת��Ϊbitmap
				HttpEntity entity = httpResponse.getEntity();
				InputStream in = entity.getContent();
				bitmap = BitmapFactory.decodeStream(in);

				return bitmap;

			}

		} catch (Exception e) {
			e.printStackTrace();
			// setTitle(e.getMessage());
		} finally {
			httpClient.getConnectionManager().shutdown();
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
