package android.mybzdemo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author liuml
 * @explain 水波纹
 * @time 2018/2/6 16:55
 */

public class MyBzView3 extends View {

    private static final String TAG = "MyBzView3";
    private Paint paint;
    private Path bzPath;
    //水波纹有四个控制点
    private Point controlPoint1 = new Point(200, 200);
    private Point controlPoint2 = new Point(500, 800);
    private int pointDown = 0;// 1 是在第一个控制点2 是在第二个控制点 0 是什么都不在
    int waterHeight = 200;

    private boolean controlVisible = true;
    int screenWidth;
    int screenHeight;
    private ValueAnimator animator;
    private int curValue;

    public MyBzView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        screenWidth = dm.widthPixels; // 屏幕宽（像素，如：3200px）
        screenHeight = dm.heightPixels; // 屏幕高（像素，如：1280px）

        controlPoint1.x = screenWidth / 4;

        controlPoint1.y = screenHeight / 2 + waterHeight;

        controlPoint2.x = screenWidth / 4 * 3;
        controlPoint2.y = screenHeight / 2 - waterHeight;

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);

        bzPath = new Path();
        //起始点
        bzPath.moveTo(-screenWidth+curValue, screenHeight / 2);
//        bzPath.moveTo(-800, 500);
//        bzPath.moveTo(0, screenHeight / 2);
        //左边的
        bzPath.cubicTo(-controlPoint1.x+curValue, controlPoint1.y, -controlPoint2.x+curValue, controlPoint2.y, curValue, screenHeight / 2);
//        bzPath.cubicTo(-100, 500, -200, 600, 300, 800);
        canvas.drawPath(bzPath, paint);
        bzPath.cubicTo(controlPoint1.x+curValue, controlPoint1.y, controlPoint2.x+curValue, controlPoint2.y, screenWidth+curValue, screenHeight / 2);
        //绘制路径
        canvas.drawPath(bzPath, paint);
//        paint.setColor(Color.RED);
        //画矩形 从水平线往下
        canvas.drawRect(0, screenHeight / 2, screenWidth, screenHeight, paint);
        paint.setColor(Color.BLUE);
        //绘制控制点
        if (controlVisible) {
            canvas.drawCircle(controlPoint1.x, controlPoint1.y, 20, paint);
            canvas.drawCircle(controlPoint2.x, controlPoint2.y, 20, paint);
        }
        paint.reset();
    }

    public void setControlVisible(boolean visible) {
        this.controlVisible = visible;
        invalidate();
    }

    public boolean controlVisible() {
        return controlVisible;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //判断按下的点是否在控制点上
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (controlPoint1.x - 20 < x && x < controlPoint1.x + 20
                        && controlPoint1.y - 20 < y && y < controlPoint1.y + y) {
                    //在控制点1上
                    pointDown = 1;
                }
                if (controlPoint2.x - 20 < x && x < controlPoint2.x + 20
                        && controlPoint2.y - 20 < y && y < controlPoint2.y + y) {
                    //在控制点2上
                    pointDown = 2;
                }

            case MotionEvent.ACTION_MOVE:
                if (pointDown == 1) {
                    controlPoint1.x = (int) event.getX();
                    controlPoint1.y = (int) event.getY();
                }
                if (pointDown == 2) {
                    controlPoint2.x = (int) event.getX();
                    controlPoint2.y = (int) event.getY();
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                pointDown = 0;
                break;
        }

        return true;
    }

    /**
     * 开启动画
     */
    public void startAnimator() {
        animator = ValueAnimator.ofInt(0, screenHeight / 2, screenWidth, screenHeight / 2);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curValue = (int) animation.getAnimatedValue();
                Log.d(TAG, "onAnimationUpdate: curValue=" + curValue);
                Log.d(TAG, "===============================================================" );
                postInvalidate();
            }
        });

        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.start();
//        animator1 = ObjectAnimator.ofFloat(mMybz3,"translationX",0,screenWidth);
//        animator1.setDuration(2000);
//        animator1.setRepeatCount(ValueAnimator.INFINITE);
////        animator1.setRepeatMode();
//        animator1.start();
    }

    public void stopanimator() {

        animator.cancel();
//        animator1.cancel();
    }
}
