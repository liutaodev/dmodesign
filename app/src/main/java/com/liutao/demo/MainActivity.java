package com.liutao.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.liutao.demo.adapter.MenuListAdapter;
import com.liutao.demo.base.BaseActivity;
import com.liutao.demo.bean.MenuItem;
import com.liutao.demo.view.ThumbUpSampleActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView rv_menu;
    private List<MenuItem> mDataList = new ArrayList<>();
    private MenuListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_menu = findViewById(R.id.rv_menu);
        mDataList.add(new MenuItem("1","即刻点赞"));
        mDataList.add(new MenuItem("2","其他测试"));
        initRecyclerView();
    }

    private void initRecyclerView(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rv_menu.setLayoutManager(gridLayoutManager);
        mAdapter = new MenuListAdapter(mDataList);
        mAdapter.setOnItemClickListener(new MenuListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MenuItem item) {
                switch (item.getCode()){
                    case "1":
                        toActivity(MainActivity.this,ThumbUpSampleActivity.class);
                        break;
                    default :
                        Toast.makeText(MainActivity.this,"敬请期待",Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
        rv_menu.setAdapter(mAdapter);
    }

}
