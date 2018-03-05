package com.hq.yunyi2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class ProtectActivity extends Activity{

    private Switch switch_private_experience,switch_private_info,switch_family_outside,switch_family_inside;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protect);

        sharedPreferences=getSharedPreferences("switch",MODE_PRIVATE);

        switch_private_experience=(Switch)this.findViewById(R.id.switch_private_experience);
        switch_private_info=(Switch)this.findViewById(R.id.switch_private_info);
        switch_family_outside=(Switch)this.findViewById(R.id.switch_family_outside);
        switch_family_inside=(Switch)this.findViewById(R.id.switch_family_inside);

        String state_private_experience=sharedPreferences.getString("state_private_experience","false");
        String state_private_info=sharedPreferences.getString("state_private_info","false");
        String state_family_outside=sharedPreferences.getString("state_family_outside","false");
        String state_family_inside=sharedPreferences.getString("state_family_inside","false");
        if(state_private_experience.equals("true")){
            switch_private_experience.setChecked(true);
        }else{
            switch_private_experience.setChecked(false);
        }
        if(state_private_info.equals("true")){
            switch_private_info.setChecked(true);
        }else{
            switch_private_info.setChecked(false);
        }
        if(state_family_outside.equals("true")){
            switch_family_outside.setChecked(true);
        }else{
            switch_family_outside.setChecked(false);
        }
        if(state_family_inside.equals("true")){
            switch_family_inside.setChecked(true);
        }else{
            switch_family_inside.setChecked(false);
        }
    }

    public void image_back(View view){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(switch_private_experience.isChecked()){
            editor.putString("state_private_experience","true");
        }else{
            editor.putString("state_private_experience","false");
        }
        if(switch_private_info.isChecked()){
            editor.putString("state_private_info","true");
        }else{
            editor.putString("state_private_info","false");
        }
        if(switch_family_outside.isChecked()){
            editor.putString("state_family_outside","true");
        }else{
            editor.putString("state_family_outside","false");
        }
        if(switch_family_inside.isChecked()){
            editor.putString("state_family_inside","true");
        }else{
            editor.putString("state_family_inside","false");
        }
        editor.commit();
        Intent intent=new Intent(ProtectActivity.this,PrivateCenterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent intent=new Intent(ProtectActivity.this,PrivateCenterActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }
}
