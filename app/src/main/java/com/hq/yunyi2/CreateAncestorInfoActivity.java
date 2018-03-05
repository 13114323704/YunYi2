package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Calendar;


public class CreateAncestorInfoActivity extends Activity{

    private ImageView image_location;
    private EditText editText_name,editText_relative,editText_location;
    private Button btn_create;
    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ancestor_info);
        editText_name=(EditText)this.findViewById(R.id.editext_name);
        editText_relative=(EditText)this.findViewById(R.id.editext_relative);
        editText_location=(EditText)this.findViewById(R.id.edittext_location);
        btn_create=(Button)this.findViewById(R.id.btn_create);
        image_location=(ImageView)this.findViewById(R.id.image_location);

        image_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreateAncestorInfoActivity.this,SearchMapActivity.class);
                startActivityForResult(intent,1);
            }
        });
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreateAncestorInfoActivity.this,AncestorInfoActivity.class);
                intent.putExtra("name",editText_name.getText().toString());
                intent.putExtra("relative",editText_relative.getText().toString());
                intent.putExtra("address",editText_location.getText().toString());
                intent.putExtra("time",c.get(Calendar.MONTH) + 1 + "月" + c.get(Calendar.DAY_OF_MONTH) + "日");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1){
            editText_location.setText(data.getStringExtra("address"));
        }
    }

    public void image_back(View view){
        finish();
    }
}
