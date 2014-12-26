package com.gdnews.ui;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.gdnews.news.R;

public class IssueConfession extends Activity {

	ImageView imageView2,imageView3;
	EditText editText1,editText2,editText3;
	String to,content,from;
	MyHandler myhandler = new MyHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.issue_confession);
		
		editText1 = (EditText) this.findViewById(R.id.editText1);
		editText2 = (EditText) this.findViewById(R.id.editText2);
		editText3 = (EditText) this.findViewById(R.id.editText3);
		imageView2 = (ImageView) this.findViewById(R.id.imageView2);
		imageView3 = (ImageView) this.findViewById(R.id.imageView3);

		imageView2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				to = editText1.getText().toString().trim();
				content = editText2.getText().toString().trim();
				from = editText3.getText().toString().trim();
				
				
				if(to.equals("")) {
					Toast.makeText(IssueConfession.this, "��������������Ŷ��~", 3000).show();
				}else if(content.equals("")) {
					Toast.makeText(IssueConfession.this, "���ٶ���������˵��ʲô�ɣ���", 3000).show();
				}else if(from.equals("")){
					from = "����";
					// ����һ���Զ�����̷߳�������
					MyThread mythread = new MyThread("http://1.mygdmecapp.sinaapp.com/myapp_myconfession.php");
					// �߳�����
					mythread.start();
				}else{
					// ����һ���Զ�����̷߳�������
					MyThread mythread = new MyThread("http://1.mygdmecapp.sinaapp.com/myapp_myconfession.php");
					// �߳�����
					mythread.start();
				}
				
			}
		});
		
		imageView3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				finish();
				
			}
		});
		
	}
	
	String getWebInfo(String url) {
		String result = "";
		// Post�������ͱ���������NameValuePair[]���д���
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("to", to));
		params.add(new BasicNameValuePair("content", content));
		params.add(new BasicNameValuePair("from", from));

		// ����post����
		HttpPost httpRequest = new HttpPost(url);

		try {
			// ����HTTP����
			httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			System.out.println("to:" + to);
			System.out.println("content:" + content);
			System.out.println("from:" + from);

			// ȡ��Ĭ�ϵ�HttpClient
			HttpClient httpclient = new DefaultHttpClient();

			// ȡ��HttpResponse
			HttpResponse httpResponse = httpclient.execute(httpRequest);

			// ��״̬��Ϊ200������ɹ���ȡ����������
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				// ȡ���ַ���
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

	public class MyThread extends Thread {

		public String url;

		public MyThread(String Url) {
			url = Url;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String result = getWebInfo(url);
			Message msg = new Message();
			Bundle bundle = new Bundle();
			bundle.putString("RESULT", result);

			msg.setData(bundle);

			myhandler.sendMessage(msg);

		}

	}// end of class MyThread

	public class MyHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// super.handleMessage(msg);
			String result = msg.getData().getString("RESULT");
			// �����û�id
			if (result.equals("success")) {
				Toast.makeText(IssueConfession.this, "���ѱ�׳ɹ�~~~", 1000).show();
			}
			System.out.println(result);
		}

	}
	
}
