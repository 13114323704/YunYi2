package com.hq.yunyi2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hq.yunyi2.utils.RegisterHttpUtils;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends Activity {

    private EditText editText_nickname, editText_username, editText_pasword;
    private Button btn_submit;
    private String nickname, username, password;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string_result = (String) msg.obj;
            if (string_result.equals("success")) {
                Toast.makeText(RegisterActivity.this, "注册成功,返回主页登录吧！", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(RegisterActivity.this, "注册失败！可能是服务器未打开或者没有联网哦"+string_result, Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editText_nickname = (EditText) this.findViewById(R.id.editText_nickname);
        editText_username = (EditText) this.findViewById(R.id.editText_username);
        editText_pasword = (EditText) this.findViewById(R.id.editText_password);
        btn_submit = (Button) this.findViewById(R.id.btn_register);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nickname = editText_nickname.getText().toString();
                username = editText_username.getText().toString();
                password = editText_pasword.getText().toString();

                if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "不能输入空值！", Toast.LENGTH_SHORT).show();
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", username);
                        params.put("nickname", nickname);
                        params.put("password", password);

                        String result = RegisterHttpUtils.sendPostMessage(params, "utf-8");
                        Message message = Message.obtain();
                        message.obj = result;
                        handler.sendMessage(message);
                    }
                }).start();
            }
        });
    }

    public void image_back(View view) {
        finish();
    }


}
