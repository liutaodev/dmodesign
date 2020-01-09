package com.liutao.demo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.liutao.demo.R;
import com.liutao.demo.widget.thumb.ThumbUpView;
import com.liutao.laud.widget.LaudDemo;
import com.liutao.laud.widget.LaudView;
import com.liutao.laud.widget.ThumbView;

public class ThumbUpSampleActivity extends AppCompatActivity {

    ThumbUpView newThumbUpView;
    LaudDemo laud_view;
    LaudView lv_test01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb_up_sample);
        newThumbUpView = findViewById(R.id.newThumbUpView);
        lv_test01 = findViewById(R.id.lv_test01);
    }
}
