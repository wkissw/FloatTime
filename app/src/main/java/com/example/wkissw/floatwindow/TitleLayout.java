package com.example.wkissw.floatwindow;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TitleLayout extends LinearLayout {
    private static final String TAG = "TitleLayout";

    public TitleLayout(Context context, AttributeSet attrs) {
        super(context,attrs);
        LayoutInflater.from(context).inflate(R.layout.title,this);

        final TextView textView;
        textView = (TextView) findViewById(R.id.TvBack);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        final TextView textViewTitle = (TextView) findViewById(R.id.TextViewTile);
        final TextView TextViewGo = (TextView) findViewById(R.id.Tvgo);

        textViewTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 你点击了Title");
                Log.d(TAG, "onClick: "+textViewTitle.getTextSize());
                if (textViewTitle.getTextSize()==48.0f) {
                    Log.d(TAG, "onClick:  == 16.0");
                    textViewTitle.setTextSize(18.0f);
                    Log.d(TAG, "onClick: success" );
                }else {
                    Log.d(TAG, "onClick: ==else");
                    textViewTitle.setTextSize(16.0f);
                }

            }
        });


        TextViewGo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 你点击了GO");
                Toast.makeText(getContext(), "你点了GO你要去哪", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void confirm() {
        Log.d(TAG, "confirm: runing");
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("提示信息");
        dialog.setMessage("是否退出？");
        dialog.setCancelable(true);
        dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity)getContext()).finish();
                Log.d(TAG, "onClick: 点击了确认，销毁程序");
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }


}
