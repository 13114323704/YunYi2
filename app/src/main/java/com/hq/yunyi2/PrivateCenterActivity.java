package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hq.yunyi2.utils.CircleImageView;
import com.hq.yunyi2.utils.ImageCacheUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class PrivateCenterActivity extends Activity implements View.OnClickListener {

    private LinearLayout linearLayout_private_experience, linearLayout_word_manager, linearLayout_protect, linearLayout_family_way;
    private RelativeLayout relativeLayout_private_make, relativeLayout_animal_memory, relativeLayout_advice, relativeLayout_about;
    private ImageView imageView_user_qrcode;
    private CircleImageView circleImageView_user;
    private TextView textView_nickname,textView_sign;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_center);

        sharedPreferences=getSharedPreferences("myLoginName", MODE_PRIVATE);

        circleImageView_user=(CircleImageView) this.findViewById(R.id.user_image);
        textView_nickname=(TextView)this.findViewById(R.id.user_nickname);
        textView_sign=(TextView)this.findViewById(R.id.text_sign);
        Drawable drawable=loadDrawable();
        if(drawable!=null){
            circleImageView_user.setImageDrawable(loadDrawable());
        }

        textView_nickname.setText(sharedPreferences.getString("nickname","我是云忆"));

        imageView_user_qrcode = (ImageView) this.findViewById(R.id.user_qrcode_image);
        linearLayout_private_experience = (LinearLayout) this.findViewById(R.id.linearlayout_private_experience);
        linearLayout_word_manager = (LinearLayout) this.findViewById(R.id.linearlayout_word_manager);
        linearLayout_protect = (LinearLayout) this.findViewById(R.id.linearlayout_protect);
        linearLayout_family_way = (LinearLayout) this.findViewById(R.id.linearlayout_family_way);
        relativeLayout_private_make = (RelativeLayout) this.findViewById(R.id.relative_private_make);
        relativeLayout_animal_memory = (RelativeLayout) this.findViewById(R.id.relative_animal_memory);
        relativeLayout_advice = (RelativeLayout) this.findViewById(R.id.relative_advice);
        relativeLayout_about = (RelativeLayout) this.findViewById(R.id.relative_about);

        imageView_user_qrcode.setOnClickListener(this);
        linearLayout_private_experience.setOnClickListener(this);
        linearLayout_word_manager.setOnClickListener(this);
        linearLayout_protect.setOnClickListener(this);
        linearLayout_family_way.setOnClickListener(this);
        relativeLayout_private_make.setOnClickListener(this);
        relativeLayout_animal_memory.setOnClickListener(this);
        relativeLayout_advice.setOnClickListener(this);
        relativeLayout_about.setOnClickListener(this);
        circleImageView_user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_image:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra("return-data", true);
                intent.putExtra("crop", "circle");
                // 使用Intent.ACTION_GET_CONTENT这个Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 取得相片后返回本画面
                startActivityForResult(intent, 1);
                break;
            case R.id.user_qrcode_image:

                break;
            case R.id.linearlayout_private_experience:
                Intent intent_private_experiene = new Intent(PrivateCenterActivity.this, PrivateExperienceActivity.class);
                startActivity(intent_private_experiene);
                break;
            case R.id.linearlayout_word_manager:
                Intent intent_private_info = new Intent(PrivateCenterActivity.this, PrivateInfoActivity.class);
                startActivity(intent_private_info);
                break;
            case R.id.linearlayout_protect:
                Intent intent_protect = new Intent(PrivateCenterActivity.this, ProtectActivity.class);
                startActivity(intent_protect);
                break;
            case R.id.linearlayout_family_way:
                Intent intent_family_way = new Intent(PrivateCenterActivity.this, FamilyWayActivity.class);
                startActivity(intent_family_way);
                break;
            case R.id.relative_private_make:
                Intent intent_chat = new Intent(PrivateCenterActivity.this, ChatActivity.class);
                startActivity(intent_chat);
                break;
            case R.id.relative_animal_memory:
                Intent intent_animal_memory = new Intent(PrivateCenterActivity.this, AnimalMemoryActivity.class);
                startActivity(intent_animal_memory);
                break;
            case R.id.relative_advice:

                break;
            case R.id.relative_about:

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(PrivateCenterActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            Bitmap bitmap = ImageCacheUtil.getResizedBitmap(null, null,
                    PrivateCenterActivity.this, data.getData(), 100, true);
            BitmapDrawable drawable = new BitmapDrawable(null, bitmap);
            circleImageView_user.setScaleType(ImageView.ScaleType.CENTER_CROP);
            circleImageView_user.setImageDrawable(drawable);
            saveDrawable(drawable);
        }
    }

    // 使用sharedPreferences保存listview背景图片
    private void saveDrawable(Drawable drawable) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        String imageBase64 = new String(Base64.encodeToString(
                baos.toByteArray(), Base64.DEFAULT));
        editor.putString("user_image", imageBase64);
        editor.commit();
    }

    // 加载用sharedPreferences保存的图片
    private Drawable loadDrawable() {
        String temp = sharedPreferences.getString("user_image", "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(
                temp.getBytes(), Base64.DEFAULT));
        return Drawable.createFromStream(bais, "");
    }
}
