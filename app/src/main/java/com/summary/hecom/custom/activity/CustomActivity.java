package com.summary.hecom.custom.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.summary.hecom.R;
import com.summary.hecom.custom.view.TitleBar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by hecom on 2018/4/27.
 */

public class CustomActivity extends AppCompatActivity {

    private TitleBar titleBar;
    private RecyclerView recyclerView;
    private List<String> mDatas;
    private CommonAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customui);

        initVisibility();
        initView();

    }

    private void initVisibility() {

        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add("1、自定义标签流容器");
        mDatas.add("2、好看的打钩小动画");
        mDatas.add("3、蜘蛛网评分控件");

        adapter = new CommonAdapter<String>(this, R.layout.item_list, mDatas) {
            @Override
            protected void convert(ViewHolder holder, String text, int position) {
                holder.setText(R.id.tv_title, text);
            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (position == 0) {
                    startActivity(new Intent(CustomActivity.this, FlowLayoutActivity.class));
                } else if (position ==1){
                    startActivity(new Intent(CustomActivity.this, TickViewActivity.class));
                }else if (position ==2){
                    startActivity(new Intent(CustomActivity.this, SpiderWebScoreViewActivity.class));
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    private void initView() {
        titleBar = findViewById(R.id.tl_titlebar);

        titleBar.setleftOnClickListener(new TitleBar.OnBtnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });


        recyclerView = findViewById(R.id.rl_custom_content);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

}
