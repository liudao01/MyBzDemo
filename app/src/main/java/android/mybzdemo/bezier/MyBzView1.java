package android.mybzdemo.bezier;

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

public class MyBzView1 extends View {

    private Paint paint;
    private Path bzPath;
    private Point controlPoint = new Point(200, 200);

    public MyBzView1(Context context) {
        super(context);
    }

    public MyBzView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBzView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        bzPath.moveTo(50, 500);
        bzPath.quadTo(controlPoint.x, controlPoint.y, 700, 500);


        //绘制路径
        canvas.drawPath(bzPath, paint);
        //绘制控制点
        canvas.drawPoint(controlPoint.x, controlPoint.y, paint);

        paint.setTextSize(100);
        paint.setStrokeWidth(5);
        canvas.drawText("二阶贝塞尔曲线", 50, 400, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                controlPoint.x = (int) event.getX();
                controlPoint.y = (int) event.getY();
                invalidate();
                break;
        }

        return true;

    }
}
