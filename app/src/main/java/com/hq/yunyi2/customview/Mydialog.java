package com.hq.yunyi2.customview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.hq.yunyi2.R;

public class Mydialog extends Dialog {
    private int layoutRes;
    private Context context;

    public Mydialog(Context context) {
        super(context);
        this.context = context;
    }


    public Mydialog(Context context, int resLayout) {
        super(context);

        this.layoutRes = resLayout;
    }

    public Mydialog(Context context, int theme, int resLayout) {
        super(context, theme);

        this.layoutRes = resLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(layoutRes);
    }

    public void showAsMyStyle(int screenWidth,int screenHeight){
        Window window=getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.mystyle);
        show();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width =(int)(0.95*screenWidth);
        params.height=(int)(0.9*screenHeight);
        // 设置背景变暗的程度
        params.dimAmount = 0.4f;
        window.setAttributes(params);

    }


}
