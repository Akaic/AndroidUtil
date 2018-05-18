package com.summary.hecom.custom.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.summary.hecom.R;
import com.summary.hecom.custom.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by hecom on 2018/4/28.
 */

public class FlowLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowlayout);
        final FlowLayout flowLayout = findViewById(R.id.fl_content);

        final List<String> list = new ArrayList<>();
        list.add("美妆");
        list.add("画板");
        list.add("漫画");
        list.add("高科技");
        list.add("韩国电影");
        list.add("高富帅");
        list.add("鸿泰安");
        list.add("外语");
        list.add("财经");
        list.add("大叔");
        list.add("非主流");
        list.add("暴走漫画");
        list.add("心理学");
        list.add("汉语");
        list.add("白富美");
        list.add("自定义");
        flowLayout.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = LayoutInflater.from(FlowLayoutActivity.this).inflate(R.layout.item_tag, parent, false);
                TextView textView = view.findViewById(R.id.tv_text);
                textView.setText(list.get(position));
                return view;
            }
        });

        flowLayout.setItemClickListener(new FlowLayout.TagItemClickListener() {
            @Override
            public void itemClick(int position) {
                Toast.makeText(FlowLayoutActivity.this, list.get(position), Toast.LENGTH_LONG).show();
            }
        });
    }
}
