package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;


public class CreateFamilyWayActivity extends Activity {

    private EditText editText_content;
    private Button btn_create;
    private Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_family_way);
        editText_content = (EditText) this.findViewById(R.id.edittext_content);
        btn_create = (Button) this.findViewById(R.id.btn_create);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateFamilyWayActivity.this, FamilyWayActivity.class);
                intent.putExtra("content", editText_content.getText().toString());
                intent.putExtra("time", c.get(Calendar.MONTH) + 1 + "月" + c.get(Calendar.DAY_OF_MONTH) + "日");
                startActivity(intent);
            }
        });
    }

    public void image_back(View view) {
        finish();
    }
}
