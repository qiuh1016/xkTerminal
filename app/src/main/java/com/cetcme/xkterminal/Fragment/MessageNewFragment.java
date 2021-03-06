package com.cetcme.xkterminal.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cetcme.xkterminal.ActionBar.TitleBar;
import com.cetcme.xkterminal.MainActivity;
import com.cetcme.xkterminal.MyApplication;
import com.cetcme.xkterminal.MyClass.Constant;
import com.cetcme.xkterminal.MyClass.PreferencesUtils;
import com.cetcme.xkterminal.R;
import com.cetcme.xkterminal.Sqlite.Bean.FriendBean;
import com.cetcme.xkterminal.Sqlite.Proxy.FriendProxy;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import org.xutils.DbManager;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiuhong on 11/01/2018.
 */

@SuppressLint("ValidFragment")
public class MessageNewFragment extends Fragment{

    public MainActivity mainActivity;

    private String tg;
    private String receive;
    private String content;
    private String time;
    private int messageId;

    private TitleBar titleBar;

    private EditText receiver_editText;
    private EditText content_editText;

    private TextView text_count_textView;
    private TextView last_send_textView;
    private TextView sender_or_receiver_textView;

    private Button sms_temp_btn;
    private Button friend_btn;

    private DbManager db;

    public MessageNewFragment(String tg, String receive, String content, String time, int id) {
        this.tg = tg;
        this.receive = receive;
        this.content = content;
        this.time = time;
        this.messageId = id;
        Log.e("Main", "MessageFragment: " + tg );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_message_new,container,false);

        db = ((MyApplication) mainActivity.getApplication()).db;

        initView(view);
        setTg(tg);

        return view;
    }

    private void initView(View view) {
        titleBar = view.findViewById(R.id.titleBar);
        receiver_editText = view.findViewById(R.id.receiver_editText);
        content_editText = view.findViewById(R.id.content_editText);

        text_count_textView = view.findViewById(R.id.text_count_textView);
        last_send_textView = view.findViewById(R.id.last_send_textView);

        sender_or_receiver_textView = view.findViewById(R.id.sender_or_receiver_textView);

        last_send_textView.setText(PreferencesUtils.getString(getActivity(),"lastSendTime", ""));

        content_editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                modifyContentIntoLength();

                if (tg.equals("new") || tg.equals("relay") || tg.equals("resend") || tg.equals("reply")) {
                    text_count_textView.setText(getRemainContentLength() + "");
                }
            }
        });

        // sms temp
        sms_temp_btn = view.findViewById(R.id.sms_temp_btn);
        sms_temp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] items = getSmsTempList();
                final String[] showItems = new String[items.length];
                // 显示序号
                if (Constant.SHOW_NUMBER_MSG_TEMP_LIST) {
                    for (int i = 0; i < showItems.length; i++) {
                        showItems[i] = (i + 1) + ". " + items[i];
                    }
                }

                new QMUIDialog.CheckableDialogBuilder(getActivity())
                        .addItems(showItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                content_editText.setText(items[which]);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });


        // friend
        friend_btn = view.findViewById(R.id.friend_btn);
        friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] builtInFriendNames = mainActivity.getResources().getStringArray(R.array.friendName);
                final String[] builtInFriendNumbers = mainActivity.getResources().getStringArray(R.array.friendNumber);

                //TODO: 防止friends为空 后续改进
                try {
                    FriendBean friendBean = new FriendBean();
                    friendBean.setName("123");
                    db.saveBindingId(friendBean);
                    db.delete(friendBean);
                } catch (Exception e) {

                }

                final List<FriendBean> friends = FriendProxy.getAll(db);
                // 显示序号
                final String[] showItems = new String[builtInFriendNames.length + friends.size()];
                for (int i = 0; i < showItems.length; i++) {
                    if (i < friends.size()) {
                        showItems[i] = (i + 1) + ". " + friends.get(i).getName() + "(" + friends.get(i).getNumber() + ")";
                    } else {
                        showItems[i] = (i + 1) + ". " + builtInFriendNames[i - friends.size()] + "(" + builtInFriendNumbers[i - friends.size()] + ")";

                    }
                }

                new QMUIDialog.CheckableDialogBuilder(getActivity())
                        .addItems(showItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                receiver_editText.setText(which < friends.size() ? friends.get(which).getNumber() : builtInFriendNumbers[which - friends.size()]);
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    public void setTg(String tg) {
        if (tg.equals("new")) {
            titleBar.setTitle("新建短信");
            receiver_editText.setText("");
            content_editText.setText("");
            text_count_textView.setText(Constant.MESSAGE_CONTENT_MAX_LENGTH + "");
            receiver_editText.setEnabled(true);
            content_editText.setEnabled(true);
        }

        if (tg.equals("reply")) {
            titleBar.setTitle("回复短信");
            receiver_editText.setText(receive);
            content_editText.setText("");
            text_count_textView.setText(getRemainContentLength() + "");
            receiver_editText.setEnabled(true);
            content_editText.setEnabled(true);
        }

        if (tg.equals("relay")) {
            titleBar.setTitle("转发短信");
            receiver_editText.setText("");
            content_editText.setText(content);
            text_count_textView.setText(getRemainContentLength() + "");
            receiver_editText.setEnabled(true);
            content_editText.setEnabled(true);
        }

        if (tg.equals("resend")) {
            titleBar.setTitle("重新发送");
            receiver_editText.setText(receive);
            content_editText.setText(content);
            text_count_textView.setText(getRemainContentLength() + "");
            receiver_editText.setEnabled(true);
            content_editText.setEnabled(true);
        }

        if (tg.equals("detail")) {
            titleBar.setTitle("短信详情");
            receiver_editText.setText(receive);
            content_editText.setText(content);
            receiver_editText.setEnabled(false);
            receiver_editText.setTextColor(0xFF000000);
            content_editText.setEnabled(false);
            content_editText.setTextColor(0xFF000000);
            text_count_textView.setText(getRemainContentLength() + "");

            if (mainActivity.messageListStatus.equals("receive")) sender_or_receiver_textView.setText("发件人：");
        }

        sms_temp_btn.setVisibility(tg.equals("detail") ? View.GONE : View.VISIBLE);
        friend_btn.setVisibility(tg.equals("detail") ? View.GONE : View.VISIBLE);
    }

    public String getReceiver() {
        return receiver_editText.getText().toString();
    }

    public String getContent() {
        return content_editText.getText().toString();
    }

    private int getRemainContentLength() {
        try {
            int length = Constant.MESSAGE_CONTENT_MAX_LENGTH - content_editText.getText().toString().getBytes("GB2312").length;
            return length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Constant.MESSAGE_CONTENT_MAX_LENGTH;
        }
    }

    private int getCurrentContentLength() {
        try {
            return content_editText.getText().toString().getBytes("GB2312").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private String[] getSmsTempList() {
        final String[] items = getActivity().getResources().getStringArray(R.array.smsTemplate);
        String smsTempStr = PreferencesUtils.getString(getActivity(), "smsTemplate", "");
        if (smsTempStr.isEmpty()) {
            return items;
        }

        for (String s : items) {
            smsTempStr += getString(R.string.smsTemplateSeparate);
            smsTempStr += s;
        }
        return  smsTempStr.split(getString(R.string.smsTemplateSeparate));
    }

    private void modifyContentIntoLength() {
        String content = content_editText.getText().toString();
        if (getCurrentContentLength() > Constant.MESSAGE_CONTENT_MAX_LENGTH) {
            content_editText.setText(content.subSequence(0, content.length() - 1));
            content_editText.setSelection(content_editText.getText().toString().length());
            modifyContentIntoLength();
        }
    }
}
