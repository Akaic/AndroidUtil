package com.summary.hecom.custom.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.summary.hecom.R;

/**
 * Created by hecom on 2018/4/28.
 */

public class TitleBar extends LinearLayout implements View.OnClickListener {

    private View titleBar;
    private ImageView leftImg;
    private TextView leftText;
    private TextView title;
    private TextView rightText;
    private ImageView rightImg;
    private TypedArray typedArray;
    private OnBtnClickListener onLeftBtnClickListener;
    private OnBtnClickListener onRightBtnClickListener;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);

        initClick();
    }


    private void initView(Context context, AttributeSet attrs) {
        titleBar = LayoutInflater.from(context).inflate(R.layout.layout_title, this, true);

        leftImg = titleBar.findViewById(R.id.img_left);
        leftText = titleBar.findViewById(R.id.text_left);
        title = titleBar.findViewById(R.id.text_title);
        rightText = titleBar.findViewById(R.id.text_right);
        rightImg = titleBar.findViewById(R.id.img_right);


        typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        if (typedArray != null) {
            titleBar.setBackgroundColor(typedArray.getColor(R.styleable.TitleBar_layout_background, getResources().getColor(R.color.white)));

            leftImg.setImageResource(typedArray.getResourceId(R.styleable.TitleBar_left_img_src, R.mipmap.title_back_normal));
            setVisible(leftImg, R.styleable.TitleBar_left_img_visible, true);

            leftText.setText(typedArray.getString(R.styleable.TitleBar_left_text_text));
            leftText.setTextColor(typedArray.getColor(R.styleable.TitleBar_left_text_color, getResources().getColor(R.color.darkgray)));
            leftText.setTextSize(TypedValue.COMPLEX_UNIT_SP, typedArray.getInteger(R.styleable.TitleBar_left_text_size, 13));
            setVisible(leftText, R.styleable.TitleBar_left_text_visible, true);

            title.setText(typedArray.getString(R.styleable.TitleBar_title_text));
            title.setTextColor(typedArray.getColor(R.styleable.TitleBar_title_color, getResources().getColor(R.color.black)));
            title.setTextSize(TypedValue.COMPLEX_UNIT_SP, typedArray.getInteger(R.styleable.TitleBar_title_size, 18));
            setVisible(title, R.styleable.TitleBar_title_visible, true);

            rightText.setText(typedArray.getString(R.styleable.TitleBar_right_text_text));
            rightText.setTextColor(typedArray.getColor(R.styleable.TitleBar_right_text_color, getResources().getColor(R.color.darkgray)));
            rightText.setTextSize(TypedValue.COMPLEX_UNIT_SP, typedArray.getInteger(R.styleable.TitleBar_right_text_size, 13));
            setVisible(rightText, R.styleable.TitleBar_right_text_visible, false);

            rightImg.setImageResource(typedArray.getResourceId(R.styleable.TitleBar_right_img_src, R.mipmap.title_back_normal));
            setVisible(rightImg, R.styleable.TitleBar_left_img_visible, false);
            typedArray.recycle();
        }
    }

    private void initClick() {
        leftImg.setOnClickListener(this);
        leftText.setOnClickListener(this);
        rightImg.setOnClickListener(this);
        rightText.setOnClickListener(this);
    }

    private void setVisible(View view, int attId, boolean def) {
        boolean value = typedArray.getBoolean(attId, def);
        if (value) {
            view.setVisibility(VISIBLE);
        } else {
            view.setVisibility(INVISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_left:
            case R.id.text_left:
                onLeftBtnClickListener.onClick();
                break;
            case R.id.img_right:
            case R.id.text_right:
                onRightBtnClickListener.onClick();
                break;
        }
    }


    public void setleftOnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onLeftBtnClickListener = onBtnClickListener;
    }

    public void setrightOnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onRightBtnClickListener = onBtnClickListener;
    }

    public interface OnBtnClickListener {
        void onClick();
    }
}
