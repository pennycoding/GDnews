package com.gdnews.news;

import com.gdnews.news.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class WiperSwitch extends View implements OnTouchListener{
	private Bitmap ic_on, ic_off, ic_btn;

	private float downX, nowX;

	private boolean onSlip = false;

	private boolean nowStatus = false;

	private OnChangedListener listener;
	
	
	public WiperSwitch(Context context) {
		super(context);
		init();
	}

	public WiperSwitch(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	public void init(){
		//����ͼƬ��Դ
		ic_on = BitmapFactory.decodeResource(getResources(), R.drawable.ic_open);
		ic_off = BitmapFactory.decodeResource(getResources(), R.drawable.ic_close);
		ic_btn = BitmapFactory.decodeResource(getResources(), R.drawable.ic_button);
		
		setOnTouchListener(this);
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Matrix matrix = new Matrix();
		Paint paint = new Paint();
		float x = 0;
		
		//����nowX���ñ����������߹�״̬
		if (nowX < (ic_on.getWidth()/2)){
			canvas.drawBitmap(ic_off, matrix, paint);//�����ر�ʱ�ı���
		}else{
			canvas.drawBitmap(ic_on, matrix, paint);//������ʱ�ı��� 
		}
		
		if (onSlip) {//�Ƿ����ڻ���״̬,  
			if(nowX >= ic_on.getWidth())//�Ƿ񻮳�ָ����Χ,�����û����ܵ���ͷ,����������ж�
				x = ic_on.getWidth() - ic_btn.getWidth()/2;//��ȥ����1/2�ĳ���
			else
				x = nowX - ic_btn.getWidth()/2;
		}else {
			if(nowStatus){//���ݵ�ǰ��״̬���û����xֵ
				x = ic_on.getWidth() - ic_btn.getWidth();
			}else{
				x = 0;
			}
		}
		
		//�Ի��黬�������쳣���������û������
		if (x < 0 ){
			x = 0;
		}
		else if(x > ic_on.getWidth() - ic_btn.getWidth()){
			x = ic_on.getWidth() - ic_btn.getWidth();
		}
		
		//��������
		canvas.drawBitmap(ic_btn, x , 0, paint); 
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:{
			if (event.getX() > ic_off.getWidth() || event.getY() > ic_off.getHeight()){
				return false;
			}else{
				onSlip = true;
				downX = event.getX();
				nowX = downX;
			}
			break;
		}
		case MotionEvent.ACTION_MOVE:{
			nowX = event.getX();
			break;
		}
		case MotionEvent.ACTION_UP:{
			onSlip = false;
			if(event.getX() >= (ic_on.getWidth()/2)){
				nowStatus = true;
				nowX = ic_on.getWidth() - ic_btn.getWidth();
			}else{
				nowStatus = false;
				nowX = 0;
			}
			
			if(listener != null){
				listener.OnChanged(WiperSwitch.this, nowStatus);
			}
			break;
		}
		}
		//ˢ�½���
		invalidate();
		return true;
	}
	
	
	
	/**
	 * ΪWiperSwitch����һ�����������ⲿ���õķ���
	 * @param listener
	 */
	public void setOnChangedListener(OnChangedListener listener){
		this.listener = listener;
	}
	
	
	/**
	 * ���û������صĳ�ʼ״̬�����ⲿ����
	 * @param checked
	 */
	public void setChecked(boolean checked){
		if(checked){
			nowX = ic_off.getWidth();
		}else{
			nowX = 0;
		}
		nowStatus = checked;
	}

	
    /**
     * �ص��ӿ�
     * @author len
     *
     */
	public interface OnChangedListener {
		public void OnChanged(WiperSwitch wiperSwitch, boolean checkState);
	}


}

