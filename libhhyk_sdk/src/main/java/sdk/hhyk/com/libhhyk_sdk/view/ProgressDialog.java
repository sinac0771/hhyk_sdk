package sdk.hhyk.com.libhhyk_sdk.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class ProgressDialog extends Dialog {
	
	public Context context;
	public boolean payViewUp;

	public ProgressDialog(Context context, boolean payViewUp) {
		super(context);
		this.context = context;
		this.payViewUp = payViewUp;
		init();
	}

	private void init() {
		ProgressBar progressBar = new ProgressBar(context);
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		this.setContentView(progressBar);
		this.setCancelable(false);
	}
	
	

}
