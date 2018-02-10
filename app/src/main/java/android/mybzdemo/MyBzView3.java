package android.mybzdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author liuml
 * @explain 水波纹
 * @time 2018/2/6 16:55
 */

public class MyBzView3 extends View {
    private static final String TAG = "MyBzView3";
    ValueAnimator animator;
    private Path mPath;
    private Paint mPaint;
    private static final int INT_WAVE_LENGTH = 1000;//波长
    private int waveHeight = 60;
    private int mDeltax;

    public MyBzView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        //用这种风格绘制的几何图形和文本将会被填充
        //在同一时间抚摸，尊重与中风相关的领域
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mPath = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //清除路径上的任何线条和曲线，使其为空。
        mPath.reset();

        int orgin = 800;
        int halfLength = INT_WAVE_LENGTH / 2;
        //起始点移动到左边屏幕的左边,根据不断改变起始点 动态的改变位置
        mPath.moveTo(-INT_WAVE_LENGTH + mDeltax, orgin);
        //从起始点开始
        for (int i = -INT_WAVE_LENGTH; i < getWidth() + INT_WAVE_LENGTH;
             i += INT_WAVE_LENGTH) {
            //使用rQuadTo 是相对位移 不用重新设置起始点
            mPath.rQuadTo(halfLength / 2, waveHeight, halfLength, 0);
            mPath.rQuadTo(halfLength / 2, -waveHeight, halfLength, 0);
        }
        //上面是画曲线

        //下面是画出界面左边和右边的一条线 这样就可以闭合
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();//让线闭合

        //把线画出来
        canvas.drawPath(mPath, mPaint);
    }


    /**
     * 开启动画
     */
    public void startAnimator() {
        animator = ValueAnimator.ofInt(0, INT_WAVE_LENGTH);
        animator.setDuration(1000);
        //设置为线性的
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDeltax = (int) animation.getAnimatedValue();
                //根据不断改变起始点 动态的改变位置
                postInvalidate();
            }
        });

        animator.start();

    }

    public void stopanimator() {

        animator.cancel();
    }
}
