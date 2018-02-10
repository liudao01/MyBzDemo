package android.mybzdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author liuml
 * @explain
 * @time 2018/2/6 16:55
 */

public class MyBzView2 extends View {

    private Paint paint;
    private Path bzPath;
    private Point controlPoint1 = new Point(200, 200);
    private Point controlPoint2 = new Point(500, 800);
    private int pointDown = 0;// 1 是在第一个控制点2 是在第二个控制点 0 是什么都不在

    public MyBzView2(Context context) {
        super(context);
    }

    public MyBzView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBzView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);

        bzPath = new Path();

        //起始点
        bzPath.moveTo(50, 500);
        bzPath.cubicTo(controlPoint1.x, controlPoint1.y, controlPoint2.x, controlPoint2.y, 700, 500);
//        bzPath.rCubicTo()
        //绘制路径
        canvas.drawPath(bzPath, paint);


        //绘制控制点
        canvas.drawCircle(controlPoint1.x, controlPoint1.y, 20, paint);
        canvas.drawCircle(controlPoint2.x, controlPoint2.y, 20, paint);
//        canvas.drawPoint(controlPoint1.x, controlPoint1.y, paint);
//        canvas.drawPoint(controlPoint2.x, controlPoint2.y, paint);

        paint.setTextSize(100);
        paint.setStrokeWidth(5);
        canvas.drawText("三阶贝塞尔曲线", 50, 700, paint);
        paint.reset();

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
}
