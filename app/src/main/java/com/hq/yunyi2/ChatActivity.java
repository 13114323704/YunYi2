package com.hq.yunyi2;

import android.animation.TimeAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ant.liao.GifView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hq.yunyi2.bean.MyOwnUserInfo;
import com.hq.yunyi2.bean.MyUserInfo;
import com.hq.yunyi2.utils.ChatHttpUtils;

import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;


public class ChatActivity extends Activity{

    private GifView image_connecting;

    private SharedPreferences sharedPreferences;
    private String string_username,string_nickname;
    private Gson gson = new Gson();
    private MyUserInfo myUserInfo = new MyUserInfo();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    string_nickname=(String)msg.obj;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("flag", "getToken");
                            params.put("username", string_username);
                            params.put("nickname", string_nickname);
                            params.put("imageuri","http://p.qq181.com/cms/1210/2012100413195471481.jpg");
                            String result = ChatHttpUtils.sendPostMessage(params, "utf-8");
                            Message message = Message.obtain();
                            message.obj = result;
                            message.what=2;
                            sendMessage(message);
                        }
                    }).start();
                    break;
                case 2:
                    String string_token=(String)msg.obj;
                    myUserInfo = gson.fromJson(string_token, new TypeToken<MyUserInfo>() {
                    }.getType());
                    //Toast.makeText(ChatActivity.this,myUserInfo.getToken(),Toast.LENGTH_LONG).show();
                    getConnect(myUserInfo.getToken());
                    RongIM.getInstance().enableNewComingMessageIcon(true);//显示新消息提醒
                    RongIM.getInstance().enableUnreadMessageIcon(true);//显示未读消息数目
                    initUserInfo();
                    Intent intent_chat_home=new Intent(ChatActivity.this,ChatHomeActivity.class);
                    startActivity(intent_chat_home);
                    /*if(RongIM.getInstance()!=null){
                        RongIM.getInstance().startPrivateChat(ChatActivity.this,"1001","客服");
                        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    }*/
                    break;
                case 3:
                    Toast.makeText(ChatActivity.this,"初始化失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sharedPreferences=getSharedPreferences("myLoginName", MODE_PRIVATE);
        string_username=sharedPreferences.getString("username","1002");
        image_connecting=(GifView)this.findViewById(R.id.image_connecting);
        image_connecting.setGifImage(R.drawable.connecting);
        image_connecting.setShowDimension(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("flag", "getNickName");
                    params.put("username", string_username);
                    String result = ChatHttpUtils.sendPostMessage(params, "utf-8");
                    Message message = Message.obtain();
                    message.obj = result;
                    message.what=1;
                    handler.sendMessage(message);
                }catch (Exception e){
                    Message message = Message.obtain();
                    message.what=3;
                    handler.sendMessage(message);
                }

            }
        }).start();

    }

    //连接服务器
    private void getConnect(String token){
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                if (s != null) {
                    Toast.makeText(ChatActivity.this, "连接客服成功！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    //实例化用户
    private void initUserInfo(){
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String s) {
                MyOwnUserInfo ownUserInfo = new MyOwnUserInfo(string_username, string_nickname);
                if (ownUserInfo.getUserId().equals(s)) {
                    return new UserInfo(ownUserInfo.getUserId(), ownUserInfo.getUserName(), Uri.parse(ownUserInfo.getImage_uri()));
                }
                return null;
            }
        },true);
    }

}
