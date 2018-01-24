package com.cetcme.xkterminal;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;

import com.cetcme.xkterminal.MyClass.Constant;
public class IDCardActivity extends Activity {

    private TextView name_textView;
    private TextView sex_textView;
    private TextView nation_textView;
    private TextView year_textView;
    private TextView month_textView;
    private TextView day_textView;
    private TextView address_textView;
    private TextView idCard_textView;

    boolean needDismissActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard_new);
        bindView();
        setDate();


        if (Constant.IDCARD_REMAIN_TIME != 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (needDismissActivity) {
                        onBackPressed();
                    }
                }
            }, Constant.IDCARD_REMAIN_TIME);
        }

    }

    private void bindView() {
        name_textView       = findViewById(R.id.name_textView);
        sex_textView        = findViewById(R.id.sex_textView);
        nation_textView     = findViewById(R.id.nation_textView);
        year_textView       = findViewById(R.id.year_textView);
        month_textView      = findViewById(R.id.month_textView);
        day_textView        = findViewById(R.id.day_textView);
        address_textView    = findViewById(R.id.address_textView);
        idCard_textView     = findViewById(R.id.idCard_textView);

    }

    private void setDate() {

        String name = getIntent().getExtras().getString("name");
        String sex = getIntent().getExtras().getString("sex");
        String birthday = getIntent().getExtras().getString("birthday");
        String address = getIntent().getExtras().getString("address");
        String idCard = getIntent().getExtras().getString("idCard");
        String nation = getIntent().getExtras().getString("nation");

        name_textView.setText(name);
        sex_textView.setText(sex);
        nation_textView.setText(nation);
        year_textView.setText(birthday.substring(0,4));
        month_textView.setText(birthday.substring(5,7));
        day_textView.setText(birthday.substring(8,10));
        address_textView.setText(address);
        idCard_textView.setText(idCard);
    }


    /**
     * 点击视图外 关闭窗口
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        onBackPressed();
        return true;
    }

    protected void onDestroy() {
        needDismissActivity = false;
        super.onDestroy();
    }

}
