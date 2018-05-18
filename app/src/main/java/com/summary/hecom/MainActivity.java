package com.summary.hecom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.summary.hecom.custom.activity.CustomActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mDatas = null;
    private CommonAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVisibility();
        initView();


    }

    private void initVisibility() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add("（1）常见自定义控件");

        adapter = new CommonAdapter<String>(this, R.layout.item_list, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String text, int position) {
                holder.setText(R.id.tv_title, text);
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(new Intent(MainActivity.this, CustomActivity.class));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });


    }

    private void initView() {
        RecyclerView rl_content = findViewById(R.id.rl_content);

        rl_content.setAdapter(adapter);

        rl_content.setLayoutManager(new LinearLayoutManager(this));
        rl_content.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }
}
