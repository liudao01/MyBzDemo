package android.mybzdemo.pathMeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author liuml
 * @explain 利用getSegment 画
 * @time 2018/2/6 16:55
 */

public class MyPathMeasureBase extends View {

    private Path mPath;
    private Paint mPaint;
    private PathMeasure mPathMeasure;
    private float mAnimatorValue;
    private Path mDst;
    private float mLength;

    public MyPathMeasureBase(Context context) {
        super(context);
    }

    public MyPathMeasureBase(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        //---画笔
        //ANTI_ALIAS_FLAG绘制时不允许使用反锯齿的标志。
        mPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(5);

        //---路径
        mPath = new Path();
        //画个矩形
        RectF rect = new RectF(200, 200, 500, 500);
        mPath.addRect(rect, Path.Direction.CW);


        //---路径测量
        mPathMeasure = new PathMeasure();
        //和path关联 true getLength时候是包括闭合的
        mPathMeasure.setPath(mPath,true);
        mLength = mPathMeasure.getLength();

        mDst = new Path();


        //---动画
        //这里有个技巧 把所有大数 或者小数 全部改造成0-1 或者-1 到1 这个区间 这样就好操作了
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
    }


    public MyPathMeasureBase(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDst.reset();
        // 硬件加速的BUG
        mDst.lineTo(0,0);
        //通过不断添加结束点 来画出矩形
        float stop = mLength * mAnimatorValue;
        //通过计算改变起始值  这里的原理是当画到一半的时候 不断改变起始值 最终让起始点和终点相等
        float start = (float) (stop - ((0.5 - Math.abs(mAnimatorValue - 0.5)) * mLength));
        mPathMeasure.getSegment(start, stop, mDst, true);
        canvas.drawPath(mDst, mPaint);


    }

}
