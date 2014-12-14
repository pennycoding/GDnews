package com.gdnews.ui;

import android.app.Dialog;
import android.content.Context;

public class MyDialog extends Dialog {
	Context context;
    public MyDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public MyDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
    }
}
