package com.hhyk_sdk;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hhyk_sdk.entity.OrderModel;
import com.hhyk_sdk.view.DtDialog;

public class MainActivity extends AppCompatActivity {

  DtDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDialog=new DtDialog(this,new OrderModel());
        findViewById(R.id.tv_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.show();
                    }
                });
            }
        });
    }
}
