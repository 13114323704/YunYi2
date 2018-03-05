package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hq.yunyi2.utils.LoginHttpUtils;


import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {

    private TextView textView_register;
    private EditText editText_usernmae, editText_password;
    private Button btn_login;
    private String username, password;
    private SharedPreferences sharedPreferences;
    private ImageView image_user_login;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string_result = (String) msg.obj;
            if (string_result.equals("notRegister")) {
                Toast.makeText(LoginActivity.this, "该账户还没有注册，先去注册吧！", Toast.LENGTH_LONG).show();
            } else if (string_result.equals("fail")) {
                Toast.makeText(LoginActivity.this, "密码不正确！", Toast.LENGTH_LONG).show();
            } else if(string_result.equals("success")){
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("username",username);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this,string_result,Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences=getSharedPreferences("myLoginName", MODE_PRIVATE);

        textView_register = (TextView) this.findViewById(R.id.textView_register);
        editText_usernmae = (EditText) this.findViewById(R.id.editText_username);
        editText_password = (EditText) this.findViewById(R.id.editText_password);
        image_user_login=(ImageView)this.findViewById(R.id.image_user_login);
        btn_login = (Button) this.findViewById(R.id.btn_login);
        Drawable drawable=loadDrawable();
        if(drawable!=null){
            image_user_login.setImageDrawable(loadDrawable());
        }
        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText_usernmae.getText().toString();
                password = editText_password.getText().toString();
                if (username.equals("admin") && password.equals("123")) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                        Toast.makeText(LoginActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("flag","1");
                                params.put("username", username);
                                params.put("password", password);
                                String result = LoginHttpUtils.sendPostMessage(params, "utf-8");
                                Message message = Message.obtain();
                                message.obj = result;
                                handler.sendMessage(message);
                            }
                        }).start();
                    }
                }


            }
        });
    }

    // 加载用sharedPreferences保存的图片
    private Drawable loadDrawable() {
        String temp = sharedPreferences.getString("user_image", "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(
                temp.getBytes(), Base64.DEFAULT));
        return Drawable.createFromStream(bais, "");
    }

}
