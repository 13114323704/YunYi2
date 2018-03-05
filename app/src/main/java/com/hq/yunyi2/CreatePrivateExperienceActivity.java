package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hq.yunyi2.utils.ImageCacheUtil;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;


public class CreatePrivateExperienceActivity extends Activity {

    private EditText editText_title, editText_content;
    private Button btn_create;
    private ImageView image_select, image_content;
    private SharedPreferences sharedPreferences;
    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_private_experience);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        sharedPreferences = getSharedPreferences("image_data", MODE_PRIVATE);
    }

    private void initView() {
        editText_title = (EditText) this.findViewById(R.id.edittext_title);
        editText_content = (EditText) this.findViewById(R.id.edittext_content);
        btn_create = (Button) this.findViewById(R.id.btn_create);
        image_select = (ImageView) this.findViewById(R.id.image_select);
        image_content = (ImageView) this.findViewById(R.id.image_content);
    }

    private void initListener() {
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDrawable(image_content.getDrawable());
                Intent intent = new Intent(CreatePrivateExperienceActivity.this, PrivateExperienceActivity.class);
                intent.putExtra("title", editText_title.getText().toString());
                intent.putExtra("content", editText_content.getText().toString());
                intent.putExtra("time", c.get(Calendar.MONTH) + 1 + "月" + c.get(Calendar.DAY_OF_MONTH) + "日");
                setResult(1, intent);
                finish();
            }
        });
        image_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra("return-data", true);
                intent.putExtra("crop", "circle");
                // 使用Intent.ACTION_GET_CONTENT这个Action
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // 取得相片后返回本画面
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 1) {
            Bitmap bitmap = ImageCacheUtil.getResizedBitmap(null, null,
                    CreatePrivateExperienceActivity.this, data.getData(), 100, true);
            BitmapDrawable drawable = new BitmapDrawable(null, bitmap);
            image_content.setImageDrawable(drawable);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        editor.putString("private_experience_image", imageBase64);
        editor.commit();
    }

    public void image_back(View view) {
        Intent intent = new Intent(CreatePrivateExperienceActivity.this, PrivateExperienceActivity.class);
        setResult(2, intent);
        finish();
    }
}
