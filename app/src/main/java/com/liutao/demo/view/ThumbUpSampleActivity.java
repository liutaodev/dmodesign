package com.liutao.demo.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.liutao.demo.R;
import com.liutao.demo.widget.thumb.ThumbUpView;
import com.liutao.demo.widget.thumb.ThumbView;
import com.liutao.laud.widget.LaudView;

public class ThumbUpSampleActivity extends AppCompatActivity {

    ThumbUpView newThumbUpView;
    LaudView laud_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb_up_sample);
        newThumbUpView = findViewById(R.id.newThumbUpView);
        newThumbUpView.setThumbUpClickListener(new ThumbView.ThumbUpClickListener() {
            @Override
            public void thumbUpFinish() {
                Log.d("MainActivity","New点赞成功");
            }

            @Override
            public void thumbDownFinish() {
                Log.d("MainActivity","New取消点赞成功");
            }
        });
        laud_view = findViewById(R.id.laud_view);
    }
}
