package android.mybzdemo.pathMeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.mybzdemo.R;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author liuml
 * @explain pathMeasure 使用  实现小船在波浪上面
 * @time 2018/2/6 16:55
 */

public class BoatView extends View {
    private static final String TAG = "MyBzView3";
    ValueAnimator animator;
    private Path mPath;
    private Paint mPaint;
    private static final int INT_WAVE_LENGTH = 1000;//波长
    private int waveHeight = 60;//波浪高度
    private int mDeltax;//运动的值
    private Bitmap boatBmp;//小船
    private PathMeasure pathMeasure;

    private float[] pos;
    private float[] tan;
    private Matrix mMatrix;
    private float faction = 0;

    public BoatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        //用这种风格绘制的几何图形和文本将会被填充
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //小船
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;//小船压缩一半
        boatBmp = BitmapFactory.decodeResource(getResources(), R.drawable.timg, options);

        pos = new float[2];
        tan = new float[2];
        mMatrix = new Matrix();

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
        mPath.moveTo(-INT_WAVE_LENGTH + faction * INT_WAVE_LENGTH, orgin);
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

        //下面画出小船
        //先测量path
        pathMeasure = new PathMeasure(mPath, false);
        //先获取长度
        float length = pathMeasure.getLength();
        //将距离标记为0 <= distance <= getLength()，然后进行计算  对应的位置和切线。如果没有路径，返回false，
        //或者指定一条零长度的路径，在这种情况下，位置和切线变。
//        boolean posTan =pathMeasure.getPosTan()

        //通过api获取matrix
        pathMeasure.getMatrix(length * faction, mMatrix, PathMeasure.TANGENT_MATRIX_FLAG | PathMeasure.POSITION_MATRIX_FLAG);
        //需要减去小船本身的宽高
        mMatrix.preTranslate(-boatBmp.getWidth() / 2, -boatBmp.getHeight());
        canvas.drawBitmap(boatBmp, mMatrix, mPaint);


    }


    /**
     * 开启动画
     */
    public void startAnimator() {
       /* animator = ValueAnimator.ofInt(0, INT_WAVE_LENGTH);
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

        animator.start();*/
        //这里就之前说的 技巧  利用0到1的区间 转化数值
        animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(10000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                faction = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

    }

    public void stopanimator() {

        animator.cancel();
    }
}
