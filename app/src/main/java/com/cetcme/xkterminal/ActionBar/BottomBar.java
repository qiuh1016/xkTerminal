package com.cetcme.xkterminal.ActionBar;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cetcme.xkterminal.MainActivity;
import com.cetcme.xkterminal.MyClass.ScreenUtil;
import com.cetcme.xkterminal.Navigation.NavigationActivity;
import com.cetcme.xkterminal.Navigation.NavigationMainActivity;
import com.cetcme.xkterminal.R;

import java.util.ArrayList;

/**
 * Created by qiuhong on 10/01/2018.
 */

public class BottomBar extends RelativeLayout implements View.OnClickListener {

    public MainActivity mainActivity;

    private Button button_receive;
    private Button button_send;
    private Button button_sign;
    private Button button_alert;
    private Button button_setting;
    private Button button_navigate;
    private Button button_post;
    private Button button_about;
    private Button button_other_ship;
  
    private Button button_pin;

    private ArrayList<Button> buttons = new ArrayList<>();

    public BottomBar(Context context) {
        super(context);
    }

    public BottomBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 最先的板子分辨率为1024*552
        View view = LayoutInflater.from(context).inflate(R.layout.bar_bottom_scroll_view, this, true);
        bindView(view);
    }

    private void bindView(View view) {
        button_receive  = view.findViewById(R.id.button_receive);
        button_send     = view.findViewById(R.id.button_send);
        button_sign     = view.findViewById(R.id.button_sign);
        button_alert    = view.findViewById(R.id.button_alert);
        button_setting  = view.findViewById(R.id.button_setting);
        button_navigate = view.findViewById(R.id.button_navigate);
        button_post     = view.findViewById(R.id.button_post);
        button_about    = view.findViewById(R.id.button_about);
        button_other_ship = view.findViewById(R.id.button_other_ship);
        button_pin      = view.findViewById(R.id.button_pin);

        buttons.add(button_receive);
        buttons.add(button_send);
        buttons.add(button_sign);
        buttons.add(button_alert);
        buttons.add(button_setting);
        buttons.add(button_navigate);
        buttons.add(button_post);
        buttons.add(button_about);
        buttons.add(button_other_ship);
        buttons.add(button_pin);

        for (Button button: buttons) {
            button.setTextColor(0xFFFFFFFF);
            button.setBackgroundResource(R.drawable.button_bg_selector);
            button.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_receive:
                mainActivity.initMessageFragment("send");
                break;
            case R.id.button_send:
                mainActivity.initMessageFragment("receive");
                break;
            case R.id.button_sign:
                mainActivity.initLogFragment("sign");
                break;
            case R.id.button_alert:
                mainActivity.initLogFragment("alert");
                break;
            case R.id.button_setting:
                mainActivity.initSettingFragment();
                break;
            case R.id.button_navigate:
                mainActivity.startActivity(new Intent(mainActivity, NavigationMainActivity.class));
                break;
            case R.id.button_post:
                mainActivity.initLogFragment("inout");
                break;
            case R.id.button_about:
                mainActivity.initAboutFragment();
                break;
            case R.id.button_other_ship:
                mainActivity.openOtherShips();
                break;
            case R.id.button_pin:
                mainActivity.openPinList();
                break;
            default:
                break;
        }
    }
}
