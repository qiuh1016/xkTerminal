package com.cetcme.xkterminal.ActionBar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cetcme.xkterminal.MainActivity;
import com.cetcme.xkterminal.R;

/**
 * Created by qiuhong on 10/01/2018.
 */

public class BackBar extends RelativeLayout implements View.OnClickListener{

    public MainActivity mainActivity;

    public Button button_back;

    public BackBar(Context context) {
        super(context);
    }

    public BackBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.bar_back_view, this, true);

        bindView(view);

    }

    private void bindView(View view) {
        button_back  = view.findViewById(R.id.button_back);

        button_back.setTextColor(0xFFFFFFFF);
        button_back.setBackgroundResource(R.drawable.button_bg_selector);
        button_back.setOnClickListener(this);

//        button_back.setTextSize(10); //16

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_back:
                    mainActivity.initMainFragment();
//                if (mainActivity.backButtonStatus.equals("backToMain")) {
//                } else if (mainActivity.backButtonStatus.equals("backToMessageList")) {
//                    mainActivity.backToMessageFragment();
//                }

                break;
            default:
                break;
        }
    }

}
