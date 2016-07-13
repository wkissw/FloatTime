package com.example.wkissw.floatwindow;

import android.content.Context;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by wkissw on 16-6-23.
 */
public class DesktopLayout extends LinearLayout {
    TextView time;
    TextView year;
    private static final String TAG = "DesktopLayout";

    public DesktopLayout(Context context) {
        super(context);
        setOrientation(LinearLayout.VERTICAL); //水平排列

        //设置宽高为包含内容
        this.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT));
        View view = LayoutInflater.from(context).inflate(R.layout.float_time,null);
        this.addView(view);
        time = (TextView) findViewById(R.id.textViewTime);
        year = (TextView) findViewById(R.id.textViewYear);
        updateTime();


        Button buttonAddtime = (Button) findViewById(R.id.buttonAddDay);

        buttonAddtime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "hhh", Toast.LENGTH_SHORT).show();
                addTime();
                updateTime();
            }
        });
    }

    //对外接口用来改变显示内容
    public void setTextTime(CharSequence sysTime){
        time.setText(sysTime);
    }

    //增加时间
    public void addTime() {
        Log.d(TAG, "addTime: 增加一天时间");
        Calendar c = Calendar.getInstance();
        Calendar bak = Calendar.getInstance();
        int day = bak.get(Calendar.DAY_OF_MONTH);

//        c.set(Calendar.YEAR, 2020);
//        c.set(Calendar.MONTH, 4-1);
        c.set(Calendar.DAY_OF_MONTH, day+1);
//        c.set(Calendar.HOUR_OF_DAY, 5);
//        c.set(Calendar.MINUTE, 56);
        long when = c.getTimeInMillis();

        if (when / 1000 < Integer.MAX_VALUE) {
            try {
                Runtime.getRuntime().exec("su");
                SystemClock.setCurrentTimeMillis(when);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //updatetime
    public  void updateTime(){
        long sysTime = System.currentTimeMillis();
        CharSequence sysTimeStr = DateFormat.format("MM-dd", sysTime);
        year.setText(sysTimeStr);
    }
}
