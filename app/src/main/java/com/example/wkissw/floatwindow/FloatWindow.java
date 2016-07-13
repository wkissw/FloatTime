package com.example.wkissw.floatwindow;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;



public class FloatWindow {
    public WindowManager mWindowManager;
    public WindowManager.LayoutParams mLayout;
    public DesktopLayout mDesktopLayout;
    private Context context;
    private static final String TAG = "FloatWindow";

    FloatWindow(Context cont){
        this.context =cont;
    }


    public void createWindowManger(){
        //取得系统窗体
        mWindowManager = (WindowManager)context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //窗口的布局样式
        mLayout = new WindowManager.LayoutParams();
        //设置窗体显示的类型为系统提示
        mLayout.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        //设置窗体焦点和触摸
        //不能获取案件输入焦点
        mLayout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        //默认位置
        mLayout.gravity = Gravity.START|Gravity.TOP;

        //设置显示模式
        mLayout.format = PixelFormat.RGBA_8888;

        //设置窗体高度和宽度
        mLayout.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayout.height = WindowManager.LayoutParams.WRAP_CONTENT;

    }

    public void createDesktopLayout(){
        mDesktopLayout = new DesktopLayout(context);
        mDesktopLayout.setOnTouchListener(new View.OnTouchListener() {
            private float lastX; //上一次位置的X.Y坐标
            private float lastY;
            private float nowX;  //当前移动位置的X.Y坐标
            private float nowY;
            private float tranX; //悬浮窗移动位置的相对值
            private float tranY;

            //触摸事件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = false;
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "onTouch: 你按下了悬浮框");
                        lastX = event.getRawX();
                        lastY = event.getRawY();
                        ret =true;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "onTouch: 你在移动");
                        nowX = event.getRawX();
                        nowY =event.getRawY();

                        tranX = nowX - lastX;
                        tranY = nowY - lastY;
                        mLayout.x+=tranX;
                        mLayout.y+=tranY;

                        mWindowManager.updateViewLayout(mDesktopLayout,mLayout);

                        lastX = nowX;
                        lastY = nowY;
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                }
                return ret;
            }
        });
    }

    //显示
    public void showDesk() {
        mWindowManager.addView(mDesktopLayout,mLayout);
    }

}
