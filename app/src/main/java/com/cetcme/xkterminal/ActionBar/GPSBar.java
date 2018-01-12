package com.cetcme.xkterminal.ActionBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cetcme.xkterminal.MainActivity;
import com.cetcme.xkterminal.MyClass.Constant;
import com.cetcme.xkterminal.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by qiuhong on 10/01/2018.
 */

public class GPSBar extends RelativeLayout {

    public MainActivity mainActivity;

    private TextView textView_latitude;
    private TextView textView_longitude;
    private TextView textView_speed;
    private TextView textView_heading;
    private TextView textView_location_status;
    private TextView textView_message;
    private TextView textView_time;

    private TextView textView_message_number;

    private ArrayList<TextView> textViews = new ArrayList<>();

    public GPSBar(Context context) {
        super(context);
    }

    public GPSBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.bar_gps_view, this, true);

        bindView(view);
        setData();
        new TimeHandler().start();
    }

    private void bindView(View view) {
        textView_latitude           = view.findViewById(R.id.textView_latitude);
        textView_longitude          = view.findViewById(R.id.textView_longitude);
        textView_speed              = view.findViewById(R.id.textView_speed);
        textView_heading            = view.findViewById(R.id.textView_heading);
        textView_location_status    = view.findViewById(R.id.textView_location_status);
        textView_message            = view.findViewById(R.id.textView_message);
        textView_time               = view.findViewById(R.id.textView_time);

        textView_message_number     = view.findViewById(R.id.textView_message_number);

        textView_message.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mainActivity.fragmentName.equals("message")) mainActivity.initMessageFragment("receive");
            }
        });

        textView_message_number.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mainActivity.fragmentName.equals("message")) mainActivity.initMessageFragment("receive");
            }
        });

        textViews.add(textView_latitude);
        textViews.add(textView_longitude);
        textViews.add(textView_speed);
        textViews.add(textView_heading);
        textViews.add(textView_location_status);
        textViews.add(textView_message);
        textViews.add(textView_time);

        for (TextView textview: textViews) {
            textview.getPaint().setFakeBoldText(true);
            textview.setTextSize(22);
            textview.setTextColor(0xFF000000);
        }
    }

    private void setData() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("latitude", "N 30°46.225′");
            jsonObject.put("longitude", "E 120°39.510′");
            jsonObject.put("speed", "10.3Kt");
            jsonObject.put("heading", "85°");
            jsonObject.put("gps", false);
            jsonObject.put("messageNumber", 3);

            textView_latitude.setText(jsonObject.getString("latitude"));
            textView_longitude.setText(jsonObject.getString("longitude"));
            textView_speed.setText(jsonObject.getString("speed"));
            textView_heading.setText(jsonObject.getString("heading"));

            if (jsonObject.getBoolean("gps")) {
                textView_location_status.setTextColor(0xFF2657EC);
                textView_location_status.setText("已定位");
                noGps = false;
            } else {
                textView_location_status.setTextColor(0xFFD0021B);
                textView_location_status.setText("未定位");
                noGps = true;
                new HalfTimeHandler().start();
            }

            int messageNumber = jsonObject.getInt("messageNumber");
            if (messageNumber != 0) {
                textView_message.setText("短信");
                textView_message_number.setText(messageNumber + "");
                textView_message_number.setVisibility(VISIBLE);
            } else {
                textView_message.setText("无短信");
                textView_message_number.setText("-");
                textView_message_number.setVisibility(INVISIBLE);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private boolean noGps = true;

    class HalfTimeHandler extends Thread{
        @Override
        public void run() {
            super.run();
            while (noGps && Constant.NO_GPS_FLASH_TIME != 0) {
                try {
                    Message message = new Message();
                    message.what = 3;
                    handler.sendMessage(message);
                    Thread.sleep(Constant.NO_GPS_FLASH_TIME);
                }
                catch (Exception e) {

                }
            }
        }
    }

    class TimeHandler extends Thread{
        @Override
        public void run() {
            super.run();
            do {
                try {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                    Thread.sleep(1000);
                }
                catch (Exception e) {

                }
            } while (true);
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    long sysTime = System.currentTimeMillis();
                    CharSequence sysTimeStr = DateFormat.format("HH:mm:ss", sysTime);
                    textView_time.setText(sysTimeStr); //更新时间
                    break;
                case 3:
                    textView_location_status.setVisibility(textView_location_status.getVisibility() == VISIBLE ? INVISIBLE : VISIBLE);
                    break;
                default:
                    break;
            }
        }
    };


}
