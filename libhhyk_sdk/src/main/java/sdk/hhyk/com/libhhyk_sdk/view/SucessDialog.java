package sdk.hhyk.com.libhhyk_sdk.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.view.WindowManager;

public class SucessDialog extends Dialog {
	
	public Context context;
	public int type;

	public SucessDialog(Context context, int type) {
		super(context);
		this.context = context;
		this.type = type;
		init();
	}

	private void init() {
		this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		Activity activity = (Activity)context;
		WindowManager wm = activity.getWindowManager();
	     int width = wm.getDefaultDisplay().getWidth();
//	     int height = wm.getDefaultDisplay().getHeight();
		//2MM 3沃商店 4爱游戏
//		switch (type) {
//		case 2:
//			this.setContentView(new MmSucView(this, context), new LayoutParams(width, -1));
//			break;
//
//		case 3:
//			this.setContentView(new WoSucView(this, context), new LayoutParams(width, -1));
//			break;
//
//		case 4:
//			this.setContentView(new AiSucView(this, context), new LayoutParams(width, -1));
//			break;
//
//		default:
//			this.setContentView(new MmSucView(this, context), new LayoutParams(width, -1));
//			break;
//		}
		this.setCancelable(false);
	}
}
