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
        OrderModel orderModel=new OrderModel();
        orderModel.setSubject("1111");
        orderModel.setMerchantid("111111");
        orderModel.setOuttradeno("123455678877");
        orderModel.setAmount("0.2");
        orderModel.setCurrency("HKD");
        orderModel.setNotifyurl("http://www.baodu.com");
        orderModel.setCustomerid(123456+"");


        mDialog=new DtDialog(this,orderModel);
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
