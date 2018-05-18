package com.summary.hecom.custom.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.summary.hecom.R;

/**
 * Created by hecom on 2018/5/1.
 */

public class TickView extends View implements View.OnClickListener {

    private final Context mContext;
    private Paint paint;
    private Path path;
    private float mCircleRadiu;
    private float mStrokeWidth;
    private TypedArray typedArray;
    private int mSelectCircleColor;
    private int mSelectedCircleColor;
    private int mSelectHookColor;
    private int mSelectedHookColor;
    private Paint mCirclePaint;
    private Paint mHookPaint;
    private boolean isCheck;
    private float mCircleStartX;
    private float mCircleStartY;
    private float[] index;
    private int sweepAngle;
    private boolean isDrawArc;
    private float radiu;
    private boolean drawArcEnd;
    private boolean drawCircleEnd;
    private float radiu1;

    public TickView(Context context) {
        this(context, null);
    }

    public TickView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TickView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initAttr(attrs);
        init();
        resetPaint();
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.TickView);
        //圆半径
        mCircleRadiu = typedArray.getDimension(R.styleable.TickView_circleRadiu, 150);
        //画笔宽度（弧宽度）
        mStrokeWidth = typedArray.getDimension(R.styleable.TickView_circle_width, 0);
        if (mStrokeWidth == 0) {
            mStrokeWidth = 10;
        }
        //未点击时，圆弧颜色
        mSelectCircleColor = typedArray.getColor(R.styleable.TickView_nocheck_circle_color, getResources().getColor(R.color.lightgray));
        //点击 View 后 颜色
        mSelectedCircleColor = typedArray.getColor(R.styleable.TickView_check_circle_color, getResources().getColor(R.color.yellow));
        //未点击 View 时，钩子的颜色
        mSelectHookColor = typedArray.getColor(R.styleable.TickView_nochecck_hook_color, getResources().getColor(R.color.lightgray));
        //点击后钩子的颜色
        mSelectedHookColor = typedArray.getColor(R.styleable.TickView_check_hook_color, getResources().getColor(R.color.white));
    }


    private void init() {

        //圆弧（圆）画笔
        mCirclePaint = new Paint();
        //钩子画笔
        mHookPaint = new Paint();
        //一个逐渐缩小的圆
        paint = new Paint();

        //钩子路径
        path = new Path();

        //初始化钩子三个点的坐标
        index = new float[6];
        index[0] = mCircleRadiu * 2 / 3;       //第一个点 x 大约在 1/3 圆直径的位置
        index[1] = mCircleRadiu;               //第一个点 y 大约在 1/2 圆直径的位置
        index[2] = mCircleRadiu;               //第二个点 x 大约在 1/2 圆直径的位置
        index[3] = (float) (1.3 * mCircleRadiu); //第二个点 y 大约在圆直径的 1/2 偏下一点位置
        index[4] = mCircleRadiu * 2 / 3 * 2;    //第三个点 x 大约在 2/3 圆直径的位置
        index[5] = (float) (0.7 * mCircleRadiu); //第三个点 y 大约在圆直径 1/2 偏上一点的位置


        setOnClickListener(this);
    }

    //每次状态改变后重置画笔
    public void resetPaint() {
        mCirclePaint.reset();
        mHookPaint.reset();
        path.reset();
        if (isCheck) {
            if (drawArcEnd) {
                mCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
            } else {
                mCirclePaint.setStyle(Paint.Style.STROKE);
                mCirclePaint.setStrokeWidth(mStrokeWidth);
            }

            mCirclePaint.setColor(mSelectedCircleColor);

            mHookPaint.setColor(mSelectedHookColor);
        } else {
            mCirclePaint.setStyle(Paint.Style.STROKE);
            mCirclePaint.setStrokeWidth(mStrokeWidth);
            mCirclePaint.setColor(mSelectCircleColor);

            mHookPaint.setColor(mSelectHookColor);
        }
        mHookPaint.setStyle(Paint.Style.STROKE);
        mHookPaint.setStrokeWidth(mStrokeWidth);

        mCirclePaint.setAntiAlias(true);
        mHookPaint.setAntiAlias(true);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setColor(getResources().getColor(R.color.white));
        paint.setAntiAlias(true);

        path.moveTo(index[0], index[1]);
        path.lineTo(index[2], index[3]);
        path.lineTo(index[4], index[5]);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //View 被点击
        if (isCheck) {
            //圆弧动态绘制结束（在这里是黄色的圆环）
            if (drawArcEnd) {
                //绘制底部圆形黄色背景
                canvas.drawCircle(mCircleRadiu, mCircleRadiu, mCircleRadiu, mCirclePaint);
                //绘制上面白色的圆（是一个越来越小，最终会消失的一个白色的圆）
                canvas.drawCircle(mCircleRadiu, mCircleRadiu, radiu, paint);
                if (drawCircleEnd) {
                    //绘制底部圆形黄色背景（一个抖动效果，先变大，在变回原来大小）
                    canvas.drawCircle(mCircleRadiu, mCircleRadiu, radiu1, mCirclePaint);
                    //绘制钩子
                    canvas.drawPath(path, mHookPaint);
                }
            } else {
                //动态绘制选中状态 时圆环
                canvas.drawArc(new RectF(mStrokeWidth, mStrokeWidth, mCircleRadiu * 2, mCircleRadiu * 2), 0, sweepAngle, false, mCirclePaint);
            }
        } else {    //默认状态（未被点击时状态）
            //绘制未选中状态
            canvas.drawArc(new RectF(mStrokeWidth, mStrokeWidth, mCircleRadiu * 2, mCircleRadiu * 2), 0, 360, false, mCirclePaint);
            //绘制钩子
            canvas.drawPath(path, mHookPaint);
        }
    }

    public void animation() {
        //圆弧动画（黄色圆环）
        ValueAnimator animator = ValueAnimator.ofInt(0, 360);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                sweepAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        //缓慢显示黄色圆背景的动画（也是是文章中提到的第三步）
        final ValueAnimator animator1 = ValueAnimator.ofFloat(mCircleRadiu, 0);
        animator1.setDuration(1000);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radiu = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        //一个抖动效果（让圆先变大，然后在变为原来的大小）
        final ValueAnimator animator2 = ValueAnimator.ofFloat(mCircleRadiu, (float) (mCircleRadiu * 1.5), mCircleRadiu);
        animator2.setDuration(500);
        animator2.setInterpolator(new LinearInterpolator());
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radiu1 = (float) animation.getAnimatedValue();
                invalidate();
            }
        });



        //圆弧（圆环）绘制结束后，开始后面的绘制
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                drawArcEnd = true;
                resetPaint();
                invalidate();
                animator1.start();
            }
        });

        //绘制最后一步
        animator1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                drawCircleEnd = true;
                animator2.start();

            }
        });
        animator.start();
    }

    @Override
    public void onClick(View v) {
        isCheck = true;
        resetPaint();
        animation();

    }
}
