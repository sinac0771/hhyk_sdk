package com.hhyk_sdk.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;



@SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
public class DWebView extends WebView {
    private Context mContext;
    /**
     * 默认背景白色
     */
    private String mTitleBg = "ffffff";
    /**
     * 默认字体黑色
     */
    private String mTitleTextColor = "FF000000";
    private String mTitle;
    /**
     * 默认是显示
     */
    private int mIsCloseVisible = 0;
    private boolean reviceHtmlError;

    public DWebView(Context context, int width, int height) {
        super(context);
        mContext = context;
        setLayoutParams(new ViewGroup.LayoutParams(width*2/3,height*2/3));
        setWebChromeClient(new WebChromeClient());
        WebSettings settings = getSettings();

        // 不让左右滑动
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(PluginState.ON);
        settings.setAllowFileAccess(true);
//		settings.setPluginsEnabled(true);
        settings.setLoadWithOverviewMode(true);


        setWebViewClient(new HelloWebViewClient());

    }




    public class HelloWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (reviceHtmlError) {
                return;
            }
            if (mContext instanceof Activity) {
                Activity activity = (Activity) mContext;
                if (activity != null && activity.isFinishing()) {
                    return;
                }
            }
            super.onPageFinished(view, url);

//            if (null != mAdData && mAdData.getShowType() == 6){
//                view.loadUrl("javascript:startPlay()");
//            }

        }

        /*
         * (non-Javadoc)
         *
         * @see
         * android.webkit.WebViewClient#onReceivedError(android.webkit.WebView,
         * int, java.lang.String, java.lang.String)
         */
        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {

            super.onReceivedError(view, errorCode, description, failingUrl);
        }

    }


    /*
     * (non-Javadoc)
     *
     * @see android.webkit.WebView#loadUrl(java.lang.String)
     */
    @Override
    public void loadUrl(String url) {
        reviceHtmlError = false;
        super.loadUrl(url);
    }


    public void setTitleBg(int color) {
        mTitleBg = getColor(color);

    }

    public void setCloseBtVisible(int visible) {
        mIsCloseVisible = visible;
    }

    public void setTitleTextColor(int color) {
        mTitleTextColor = getColor(color);
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitleBg() {
        return mTitleBg;
    }

    public int getCloseBtVisible() {
        return mIsCloseVisible;
    }

    public String getTitleTextColor() {
        return mTitleTextColor;
    }

    public String getTitle() {
        return mTitle;
    }

    private String getColor(int color) {
        StringBuffer s = new StringBuffer();
        String r = Integer.toHexString((color >> 16) & 0xFF);
        String g = Integer.toHexString((color >> 8) & 0xFF);
        String b = Integer.toHexString(color & 0xFF);
        // TODO 添加透明度设置 没弄成功
        // float a = color >>> 24;
        // float alpha = a / 255;
        // alpha = (float) (Math.round(alpha * 10)) / 10;
        if (r.equals("0")) {
            s.append("00");
        } else {
            if (r.length() == 1) {
                s.append("0" + r);
            } else if (r.length() == 2) {
                s.append(r);
            }
        }
        if (g.equals("0")) {
            s.append("00");
        } else {
            if (g.length() == 1) {
                s.append("0" + g);
            } else if (g.length() == 2) {
                s.append(g);
            }
        }
        if (b.equals("0")) {
            s.append("00");
        } else {
            if (b.length() == 1) {
                s.append("0" + b);
            } else if (b.length() == 2) {
                s.append(b);
            }
        }

        return s.toString();
    }
}
