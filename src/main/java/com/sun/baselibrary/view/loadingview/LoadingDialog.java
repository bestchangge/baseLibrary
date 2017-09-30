package com.sun.baselibrary.view.loadingview;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.sun.baselibrary.R;


public class LoadingDialog extends Dialog{
	public LoadingDialog(Context context) {
		super(context);
	}
	
	public LoadingDialog(Context context, int theme) {
		super(context, theme);
	}

	public LoadingDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public static LoadingDialog createProgressDialog(Activity context){
		LoadingDialog progress = new LoadingDialog(context, R.style.dialog);
        progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
		progress.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_loading_view, null);
        progress.setContentView(view);
        perfectDialog(context, progress);
        return progress;
    }
	
	public static void perfectDialog(Activity context,LoadingDialog progress){
		WindowManager windowManager = context.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = progress.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()-120); //设置宽度
        progress.getWindow().setAttributes(lp);
	}
}
