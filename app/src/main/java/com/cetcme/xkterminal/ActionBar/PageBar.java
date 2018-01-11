package com.cetcme.xkterminal.ActionBar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.cetcme.xkterminal.MainActivity;
import com.cetcme.xkterminal.R;

import java.util.ArrayList;

/**
 * Created by qiuhong on 10/01/2018.
 */

public class PageBar extends RelativeLayout implements View.OnClickListener {

    public MainActivity mainActivity;

    private Button button_prev;
    private Button button_next;
    private Button button_back;

    private ArrayList<Button> buttons = new ArrayList<>();

    public PageBar(Context context) {
        super(context);
    }

    public PageBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.bar_page_view, this, true);

        bindView(view);

    }

    private void bindView(View view) {
        button_prev = view.findViewById(R.id.button_prev);
        button_next = view.findViewById(R.id.button_next);
        button_back = view.findViewById(R.id.button_back);

        buttons.add(button_prev);
        buttons.add(button_next);
        buttons.add(button_back);

        for (Button button: buttons) {
            button.setTextColor(0xFF000000);
            button.setBackgroundResource(R.drawable.button_bg_selector);
            button.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_prev:
                mainActivity.prevPage();
                break;
            case R.id.button_next:
                mainActivity.nextPage();
                break;
            case R.id.button_back:
                mainActivity.initMainFragment();
                break;
            default:
                break;
        }
    }

    public void setNextButtonEnable(boolean enable) {
        button_next.setEnabled(enable);
        if (enable) {
            button_next.setTextColor(0xFF000000);
        } else {
            button_next.setTextColor(0xFFA8A8A8);
        }
    }

    public void setPrevButtonEnable(boolean enable) {
        button_prev.setEnabled(enable);
        if (enable) {
            button_prev.setTextColor(0xFF000000);
        } else {
            button_prev.setTextColor(0xFFA8A8A8);
        }
    }

}
