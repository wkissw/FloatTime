package com.example.wkissw.floatwindow;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends Activity {
    static boolean  ESITWINDOWS = false;
    FloatWindow floatWindow;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        floatWindow = new FloatWindow(MainActivity.this);   //new出一个悬浮窗类
        floatWindow.createWindowManger();                   //创建一个窗口类
        floatWindow.createDesktopLayout();                  //创建布局参数

        //显示按钮的监听事件
        findViewById(R.id.buttonShow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: 显示窗体");
                if (!ESITWINDOWS) {
                    floatWindow.showDesk();
                    TimeListener();
                    ESITWINDOWS = true;
                    finish();
                }else {
                    Toast.makeText(MainActivity.this, "亲你已经有一个悬浮窗了", Toast.LENGTH_SHORT).show();
                }

            }
        });


        //关闭按钮的监听事件
        findViewById(R.id.buttonClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了测试", Toast.LENGTH_SHORT).show();
                SystemDateTime timeClock = new SystemDateTime();
                try {
                    Log.d(TAG, "onClick: 开始修改时间");
                    timeClock.setDate(2020,12,11);
                    floatWindow.mDesktopLayout.updateTime();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //消息处理 改变现实内容
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
                    floatWindow.mDesktopLayout.setTextTime(sysTimeStr);
                    floatWindow.mWindowManager.updateViewLayout(floatWindow.mDesktopLayout,floatWindow.mLayout);
                    break;
                default:break;
            }
        }
    };

    //新建一个线程 每秒发送一次消息更新显示的时间
    private void TimeListener(){
        new Thread(){
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                        Message msg = new Message();
                        msg.what = 1;
                        mhandler.sendMessage(msg);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }.start();
    }
}
