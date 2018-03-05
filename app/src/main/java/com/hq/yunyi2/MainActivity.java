package com.hq.yunyi2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hq.yunyi2.customview.Mydialog;
import com.hq.yunyi2.utils.CircleImageView;
import com.hq.yunyi2.utils.LoginHttpUtils;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DrawerLayout drawerLayout;
    private ImageView image_open, image_private_center;
    private RelativeLayout relativeLayout_left_drawer;
    private int screenWidth;
    private LinearLayout linearLayout_family_circle;
    private LinearLayout linearLayout_memory_ancestor;
    private LinearLayout linearLayout_memory_hall;
    private LinearLayout linearLayout_special_community;
    private LinearLayout linearLayout_message;
    private LinearLayout linearLayout_service_list;
    private LinearLayout linearLayout_time_record;
    private LinearLayout linearLayout_exit;
    private Mydialog mydialog_died, mydialog_alive;
    private int screenHeight;
    private TextView textView_user_nickname;
    private CircleImageView user_image;

    private LinearLayout tree_layout;
    private View view_family_tree1, view_family_tree2, view_family_tree3, view_family_tree4;
    private View view_family_tree5, view_family_tree6, view_family_tree7, view_family_tree8;
    private View view_family_tree9, view_family_tree10, view_family_tree11, view_family_tree12;
    private View view_family_tree13, view_family_tree14, view_family_tree15, view_family_tree16;
    private View view_family_tree17, view_family_tree18;

    //初始化用户相关
    private SharedPreferences sharedPreferences;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string_result = (String) msg.obj;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (msg.what == 1) {
                editor.putString("nickname", string_result);
            } else {
                editor.putString("nickname", "我是云忆");
            }
            editor.commit();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        initData();
        initView();
        initListener();
        //初始化用户
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("flag", "2");
                    params.put("username", sharedPreferences.getString("username", "1001"));
                    String result = LoginHttpUtils.sendPostMessage(params, "utf-8");
                    Message message = Message.obtain();
                    message.obj = result;
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }
        }).start();
    }

    private void initData() {
        sharedPreferences = getSharedPreferences("myLoginName", MODE_PRIVATE);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;
    }

    private void initView() {
        initLeftDrawer();
        initTree();
        drawerLayout = (DrawerLayout) this.findViewById(R.id.drawr_layout);
        //drawerLayout.setScrimColor(Color.GREEN);
        image_open = (ImageView) this.findViewById(R.id.image_open);
        image_private_center = (ImageView) this.findViewById(R.id.image_private_center);

        relativeLayout_left_drawer = (RelativeLayout) this.findViewById(R.id.left_drawer);
        ViewGroup.LayoutParams lp = relativeLayout_left_drawer.getLayoutParams();
        lp.width = (int) (0.8 * screenWidth);
        relativeLayout_left_drawer.setLayoutParams(lp);

        textView_user_nickname=(TextView)this.findViewById(R.id.textview_nickname);
        textView_user_nickname.setText(sharedPreferences.getString("nickname","我是云忆"));
        user_image=(CircleImageView)this.findViewById(R.id.left_drawer_user_image);
        Drawable drawable=loadDrawable();
        if(drawable!=null){
            user_image.setImageDrawable(drawable);
        }
    }

    private void initLeftDrawer() {

        linearLayout_family_circle = (LinearLayout) this.findViewById(R.id.linearlayout_family_circle);
        linearLayout_memory_ancestor = (LinearLayout) this.findViewById(R.id.linearlayout_memory_ancestor);
        linearLayout_memory_hall = (LinearLayout) this.findViewById(R.id.linearlayout_memory_hall);
        linearLayout_message = (LinearLayout) this.findViewById(R.id.linearlayout_message);
        linearLayout_service_list = (LinearLayout) this.findViewById(R.id.linearlayout_service_list);
        linearLayout_time_record = (LinearLayout) this.findViewById(R.id.linearlayout_time_record);
        linearLayout_special_community = (LinearLayout) this.findViewById(R.id.linearlayout_special_circle);
        linearLayout_exit=(LinearLayout)this.findViewById(R.id.linearLayout_exit);
    }

    private void initTree() {
        tree_layout=(LinearLayout) this.findViewById(R.id.tree_layout);
        ViewGroup.LayoutParams lp=tree_layout.getLayoutParams();
        lp.height=screenHeight-200;
        tree_layout.setLayoutParams(lp);

        mydialog_died = new Mydialog(MainActivity.this, R.style.customDialog,
                R.layout.dialog_died_info);
        mydialog_alive = new Mydialog(MainActivity.this, R.style.customDialog,
                R.layout.dialog_alive_info);

        view_family_tree1 = (View) this.findViewById(R.id.item_family_tree1);
        view_family_tree1.findViewById(R.id.dot_bottom).setVisibility(View.VISIBLE);
        ImageView imageView1 = (ImageView) view_family_tree1.findViewById(R.id.person_image);
        imageView1.setImageResource(R.mipmap.default_man);
        TextView textView1 = (TextView) view_family_tree1.findViewById(R.id.person_content);
        textView1.setText("太爷爷-李军");
        view_family_tree1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_died.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree2 = (View) this.findViewById(R.id.item_family_tree2);
        view_family_tree2.findViewById(R.id.dot_right).setVisibility(View.VISIBLE);
        view_family_tree2.findViewById(R.id.dot_bottom).setVisibility(View.VISIBLE);
        ImageView imageView2 = (ImageView) view_family_tree2.findViewById(R.id.person_image);
        imageView2.setImageResource(R.mipmap.default_woman);
        TextView textView2 = (TextView) view_family_tree2.findViewById(R.id.person_content);
        textView2.setText("姑奶奶-李红");
        view_family_tree2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_died.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree3 = (View) this.findViewById(R.id.item_family_tree3);
        view_family_tree3.findViewById(R.id.dot_left).setVisibility(View.VISIBLE);
        view_family_tree3.findViewById(R.id.dot_bottom).setVisibility(View.VISIBLE);
        ImageView imageView3 = (ImageView) view_family_tree3.findViewById(R.id.person_image);
        imageView3.setImageResource(R.mipmap.head4);
        TextView textView3 = (TextView) view_family_tree3.findViewById(R.id.person_content);
        textView3.setText("爷爷-李刚");
        view_family_tree3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree4 = (View) this.findViewById(R.id.item_family_tree4);
        view_family_tree4.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        view_family_tree4.findViewById(R.id.dot_bottom).setVisibility(View.VISIBLE);
        ImageView imageView4 = (ImageView) view_family_tree4.findViewById(R.id.person_image);
        imageView4.setImageResource(R.mipmap.head8);
        TextView textView4 = (TextView) view_family_tree4.findViewById(R.id.person_content);
        textView4.setText("表叔-张家豪");
        view_family_tree4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree5 = (View) this.findViewById(R.id.item_family_tree5);
        view_family_tree5.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        view_family_tree5.findViewById(R.id.dot_bottom).setVisibility(View.VISIBLE);
        ImageView imageView5 = (ImageView) view_family_tree5.findViewById(R.id.person_image);
        imageView5.setImageResource(R.mipmap.default_woman);
        TextView textView5 = (TextView) view_family_tree5.findViewById(R.id.person_content);
        textView5.setText("姑母-李丽君");
        view_family_tree5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_died.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree6 = (View) this.findViewById(R.id.item_family_tree6);
        view_family_tree6.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        view_family_tree6.findViewById(R.id.dot_bottom).setVisibility(View.VISIBLE);
        ImageView imageView6 = (ImageView) view_family_tree6.findViewById(R.id.person_image);
        imageView6.setImageResource(R.mipmap.head4);
        TextView textView6 = (TextView) view_family_tree6.findViewById(R.id.person_content);
        textView6.setText("父亲-李毅");
        view_family_tree6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree7 = (View) this.findViewById(R.id.item_family_tree7);
        view_family_tree7.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        view_family_tree7.findViewById(R.id.dot_bottom).setVisibility(View.VISIBLE);
        ImageView imageView7 = (ImageView) view_family_tree7.findViewById(R.id.person_image);
        imageView7.setImageResource(R.mipmap.head4);
        TextView textView7 = (TextView) view_family_tree7.findViewById(R.id.person_content);
        textView7.setText("大表哥-张宇");
        view_family_tree7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree8 = (View) this.findViewById(R.id.item_family_tree8);
        view_family_tree8.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        view_family_tree8.findViewById(R.id.dot_bottom).setVisibility(View.INVISIBLE);
        ImageView imageView8 = (ImageView) view_family_tree8.findViewById(R.id.person_image);
        imageView8.setImageResource(R.mipmap.head7);
        TextView textView8 = (TextView) view_family_tree8.findViewById(R.id.person_content);
        textView8.setText("大表姐-张丽");
        view_family_tree8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree9 = (View) this.findViewById(R.id.item_family_tree9);
        view_family_tree9.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        view_family_tree9.findViewById(R.id.dot_bottom).setVisibility(View.INVISIBLE);
        ImageView imageView9 = (ImageView) view_family_tree9.findViewById(R.id.person_image);
        imageView9.setImageResource(R.mipmap.head8);
        TextView textView9 = (TextView) view_family_tree9.findViewById(R.id.person_content);
        textView9.setText("小表弟-何佳");
        view_family_tree9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree10 = (View) this.findViewById(R.id.item_family_tree10);
        view_family_tree10.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        view_family_tree10.findViewById(R.id.dot_bottom).setVisibility(View.VISIBLE);
        ImageView imageView10 = (ImageView) view_family_tree10.findViewById(R.id.person_image);
        imageView10.setImageResource(R.mipmap.head1);
        TextView textView10 = (TextView) view_family_tree10.findViewById(R.id.person_content);
        textView10.setText("我-李嘉诚");
        view_family_tree10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree11 = (View) this.findViewById(R.id.item_family_tree11);
        view_family_tree11.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        view_family_tree11.findViewById(R.id.dot_bottom).setVisibility(View.VISIBLE);
        ImageView imageView11 = (ImageView) view_family_tree11.findViewById(R.id.person_image);
        imageView11.setImageResource(R.mipmap.head3);
        TextView textView11 = (TextView) view_family_tree11.findViewById(R.id.person_content);
        textView11.setText("妹妹-李舒仁");
        view_family_tree11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree12 = (View) this.findViewById(R.id.item_family_tree12);
        view_family_tree12.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        ImageView imageView12 = (ImageView) view_family_tree12.findViewById(R.id.person_image);
        imageView12.setImageResource(R.mipmap.head7);
        TextView textView12 = (TextView) view_family_tree12.findViewById(R.id.person_content);
        textView12.setText("表侄女-张美");
        view_family_tree12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree13 = (View) this.findViewById(R.id.item_family_tree13);
        view_family_tree13.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        ImageView imageView13 = (ImageView) view_family_tree13.findViewById(R.id.person_image);
        imageView13.setImageResource(R.mipmap.head4);
        TextView textView13 = (TextView) view_family_tree13.findViewById(R.id.person_content);
        textView13.setText("表侄儿-张清");
        view_family_tree13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree14 = (View) this.findViewById(R.id.item_family_tree14);
        view_family_tree14.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        ImageView imageView14 = (ImageView) view_family_tree14.findViewById(R.id.person_image);
        imageView14.setImageResource(R.mipmap.head1);
        TextView textView14 = (TextView) view_family_tree14.findViewById(R.id.person_content);
        textView14.setText("表侄儿-张壮");
        view_family_tree14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree15 = (View) this.findViewById(R.id.item_family_tree15);
        view_family_tree15.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        ImageView imageView15 = (ImageView) view_family_tree15.findViewById(R.id.person_image);
        imageView15.setImageResource(R.mipmap.head4);
        TextView textView15 = (TextView) view_family_tree15.findViewById(R.id.person_content);
        textView15.setText("大儿子-李志");
        view_family_tree15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree16 = (View) this.findViewById(R.id.item_family_tree16);
        view_family_tree16.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        ImageView imageView16 = (ImageView) view_family_tree16.findViewById(R.id.person_image);
        imageView16.setImageResource(R.mipmap.head5);
        TextView textView16 = (TextView) view_family_tree16.findViewById(R.id.person_content);
        textView16.setText("二女儿-李丽");
        view_family_tree16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree17 = (View) this.findViewById(R.id.item_family_tree17);
        view_family_tree17.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        ImageView imageView17 = (ImageView) view_family_tree17.findViewById(R.id.person_image);
        imageView17.setImageResource(R.mipmap.head2);
        TextView textView17 = (TextView) view_family_tree17.findViewById(R.id.person_content);
        textView17.setText("小女儿-李奇");
        view_family_tree17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });

        view_family_tree18 = (View) this.findViewById(R.id.item_family_tree18);
        view_family_tree18.findViewById(R.id.dot_top).setVisibility(View.VISIBLE);
        ImageView imageView18 = (ImageView) view_family_tree18.findViewById(R.id.person_image);
        imageView18.setImageResource(R.mipmap.head3);
        TextView textView18 = (TextView) view_family_tree18.findViewById(R.id.person_content);
        textView18.setText("大妹妹-李仁");
        view_family_tree18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydialog_alive.showAsMyStyle(screenWidth, screenHeight);
            }
        });
    }

    private void initListener() {

        linearLayout_family_circle.setOnClickListener(this);
        linearLayout_memory_ancestor.setOnClickListener(this);
        linearLayout_memory_hall.setOnClickListener(this);
        linearLayout_message.setOnClickListener(this);
        linearLayout_service_list.setOnClickListener(this);
        linearLayout_time_record.setOnClickListener(this);
        linearLayout_special_community.setOnClickListener(this);
        linearLayout_exit.setOnClickListener(this);

        image_open.setOnClickListener(this);
        image_private_center.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_open:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.image_private_center:
                Intent intent_private_center = new Intent(MainActivity.this, PrivateCenterActivity.class);
                startActivity(intent_private_center);
                break;
            case R.id.linearlayout_family_circle:
                Intent intent_family_circle = new Intent(MainActivity.this, FamilyCircleActivity.class);
                startActivity(intent_family_circle);
                break;
            case R.id.linearlayout_memory_ancestor:
                Intent intent_memory_ancestor = new Intent(MainActivity.this, AncestorInfoActivity.class);
                startActivity(intent_memory_ancestor);
                break;
            case R.id.linearlayout_message:
                Intent intent_message = new Intent(MainActivity.this, MessageActivity.class);
                startActivity(intent_message);
                break;
            case R.id.linearlayout_service_list:
                Intent intent_service_list = new Intent(MainActivity.this, ServiceListActivity.class);
                startActivity(intent_service_list);
                break;
            case R.id.linearlayout_time_record:
                Intent intent_time_record = new Intent(MainActivity.this, TimeRecordActivity.class);
                startActivity(intent_time_record);
                break;
            case R.id.linearlayout_memory_hall:
                Intent intent_memory_hall = new Intent(MainActivity.this, MemoryHallActivity.class);
                startActivity(intent_memory_hall);
                break;
            case R.id.linearlayout_special_circle:
                Intent intent_special_community = new Intent(MainActivity.this, SpecialCommunityActivity.class);
                startActivity(intent_special_community);
                break;
            case R.id.linearLayout_exit:
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

    // 加载用sharedPreferences保存的图片
    private Drawable loadDrawable() {
        String temp = sharedPreferences.getString("user_image", "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(
                temp.getBytes(), Base64.DEFAULT));
        return Drawable.createFromStream(bais, "");
    }
}
