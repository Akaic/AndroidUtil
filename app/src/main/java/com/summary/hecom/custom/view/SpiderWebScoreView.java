package com.summary.hecom.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.summary.hecom.R;

import java.util.Random;

/**
 * Created by hecom on 2018/5/2.
 */

public class SpiderWebScoreView extends View {
    private final Context mContext;
    private float spiderX;
    private float spiderY;
    private float radius;
    private int angleCount = 5;           //蜘蛛网角的个数
    private float angle;                    //每个角的角度
    private double radians;                 //弧度度数
    private float hierarchy;                //层与层之间的间隔
    private Paint spiderPaint;
    private Paint scorePaint;
    private Path spiderPath;
    private Path scorePath;
    private Path linePath;
    private int maxScore = 10;
    private float[] score;
    private Paint textPaint;

    public SpiderWebScoreView(Context context) {
        this(context, null);
    }

    public SpiderWebScoreView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpiderWebScoreView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {

        //这里为了方便就直接写死了，可以通过自定义属性获取
        //圆心 x 坐标
        spiderX = 400f;
        //圆心 y 坐标
        spiderY = 400f;
        //圆 半径
        radius = 200f;
        setSpideData();


        //蜘蛛网画笔
        spiderPaint = new Paint();
        spiderPaint.setColor(getResources().getColor(R.color.mediumslateblue));
        spiderPaint.setStyle(Paint.Style.STROKE);
        spiderPaint.setStrokeWidth(2);
        //分数画笔
        scorePaint = new Paint();
        scorePaint.setColor(Color.parseColor("#80DAA520"));
        scorePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(50);
        textPaint.setStyle(Paint.Style.STROKE);

        //蜘蛛网路径
        spiderPath = new Path();

        //各个点到圆心的连接线
        linePath = new Path();

        //分数
        scorePath = new Path();

        Random random = new Random();
        score = new float[angleCount];
        //各个点的分数
        for (int i = 0; i < angleCount; i++) {
            score[i] = random.nextInt(10 + 1);
        }
    }

    private void setSpideData() {
        angle = 360 / angleCount;
        //层与层之间的宽度
        hierarchy = radius / angleCount;
        //每个角对应的弧度
        radians = Math.toRadians(angle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSpider(canvas);

    }

    //绘制蜘蛛网
    private void drawSpider(Canvas canvas) {
        float nextRadians;      //下个角的度数 总是相加
        float spiderStartX;     //x 坐标
        float spiderStartY;     //角度坐标
        float scoreStartX;
        float scoreStartY;
        float nextHierarchy;    //下一个圆的半径 最大为 radius
        for (int i = 1; i <= angleCount; i++) {

            spiderPath.reset();
            scorePath.reset();
            for (int j = 1; j <= angleCount; j++) {
              //绘制蜘蛛网需要使用到的数据
                nextHierarchy = hierarchy * i;  //每层圆的半径
                nextRadians = (float) (radians * j);    //每个角的角度
                spiderStartX = (float) (spiderX + Math.sin(nextRadians) * nextHierarchy);   //圆上的坐标 x
                spiderStartY = (float) (spiderY - Math.cos(nextRadians) * nextHierarchy);   //圆上的坐标 y

//                //绘制分数需要使用到的数据
                nextRadians = (float) (radians * j);
                //计算该分数占总半径的比例值
                nextHierarchy = nextHierarchy * score[j - 1] / maxScore;    //

                scoreStartX = (float) (spiderX + Math.sin(nextRadians) * nextHierarchy);
                scoreStartY = (float) (spiderY - Math.cos(nextRadians) * nextHierarchy);

                if (i == angleCount) {
                    linePath.reset();
                    linePath.moveTo(spiderX, spiderY);
                    linePath.lineTo(spiderStartX, spiderStartY);
                    //绘制点到圆点的直线
                    canvas.drawPath(linePath, spiderPaint);


//                    //绘制分数区域
                    if (j == 1) {
                        scorePath.moveTo(scoreStartX, scoreStartY);
                    } else {
                        scorePath.lineTo(scoreStartX, scoreStartY);
                    }
                }

                if (j == 1) {   //j == 1 时确定path 的起始点
                    spiderPath.moveTo(spiderStartX, spiderStartY);
                } else {
                    spiderPath.lineTo(spiderStartX, spiderStartY);
                }
            }

            //绘制蜘蛛网
            spiderPath.close();
            canvas.drawPath(spiderPath, spiderPaint);

//            //绘制分数
            scorePath.close();
            canvas.drawPath(scorePath, scorePaint);
        }
    }
}
