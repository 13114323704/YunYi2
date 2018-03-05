package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xys.libzxing.zxing.encoding.EncodingUtils;


public class QRcodeActivity extends Activity {

    private String flag_string1,flag_string2;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privateinfo_qrcode);
        imageView=(ImageView)this.findViewById(R.id.qrcode_image);

        Intent intent=getIntent();
        flag_string1=intent.getStringExtra("name");
        flag_string2=intent.getStringExtra("motto");
        try{
            Bitmap bitmap= EncodingUtils.createQRCode("姓名:"+flag_string1+"  格言:"+flag_string2,200,200,null);
            imageView.setImageBitmap(bitmap);

        }catch (Exception e){

        }
    }

    public void image_back(View view) {
        finish();
    }
}
