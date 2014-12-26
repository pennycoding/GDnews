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
		//载入图片资源
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
		
		//根据nowX设置背景，开或者关状态
		if (nowX < (ic_on.getWidth()/2)){
			canvas.drawBitmap(ic_off, matrix, paint);//画出关闭时的背景
		}else{
			canvas.drawBitmap(ic_on, matrix, paint);//画出打开时的背景 
		}
		
		if (onSlip) {//是否是在滑动状态,  
			if(nowX >= ic_on.getWidth())//是否划出指定范围,不能让滑块跑到外头,必须做这个判断
				x = ic_on.getWidth() - ic_btn.getWidth()/2;//减去滑块1/2的长度
			else
				x = nowX - ic_btn.getWidth()/2;
		}else {
			if(nowStatus){//根据当前的状态设置滑块的x值
				x = ic_on.getWidth() - ic_btn.getWidth();
			}else{
				x = 0;
			}
		}
		
		//对滑块滑动进行异常处理，不能让滑块出界
		if (x < 0 ){
			x = 0;
		}
		else if(x > ic_on.getWidth() - ic_btn.getWidth()){
			x = ic_on.getWidth() - ic_btn.getWidth();
		}
		
		//画出滑块
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
		//刷新界面
		invalidate();
		return true;
	}
	
	
	
	/**
	 * 为WiperSwitch设置一个监听，供外部调用的方法
	 * @param listener
	 */
	public void setOnChangedListener(OnChangedListener listener){
		this.listener = listener;
	}
	
	
	/**
	 * 设置滑动开关的初始状态，供外部调用
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
     * 回调接口
     * @author len
     *
     */
	public interface OnChangedListener {
		public void OnChanged(WiperSwitch wiperSwitch, boolean checkState);
	}


}

