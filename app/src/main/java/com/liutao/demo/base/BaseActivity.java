package com.liutao.demo.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    protected void initData(){

    }

    protected void initView(){

    }
    protected void initLoad(){

    }
    protected void initListener(){

    }

    protected void toActivity(Activity activity,Class<? extends Activity> cls){
        Intent intent = new Intent(activity,cls);
        startActivity(intent);
    }

    protected void toActivity(Activity activity,Class<? extends Activity> cls,Bundle bundle){
        Intent intent = new Intent(activity,cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
