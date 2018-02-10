package android.mybzdemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mBtChange;

    private MyBzView1 mMybz1;
    private MyBzView2 mMybz2;
    private MyBzView3 mMybz3;
    private Button mBtControlVisible;
    boolean start = false;
    private Button mBtAnimStart;
    int screenWidth;
    int screenHeight;
    private ValueAnimator animator;
    private int type = 2;
    private ObjectAnimator animator1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtChange = (Button) findViewById(R.id.bt_change);
        mBtAnimStart = (Button) findViewById(R.id.bt_anim_start);

        mMybz1 = (MyBzView1) findViewById(R.id.mybz1);
        mMybz2 = (MyBzView2) findViewById(R.id.mybz2);
        mMybz3 = (MyBzView3) findViewById(R.id.mybz3);

        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        float density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        int densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        screenWidth = dm.widthPixels; // 屏幕宽（像素，如：3200px）
        screenHeight = dm.heightPixels; // 屏幕高（像素，如：1280px）

        mBtControlVisible = (Button) findViewById(R.id.bt_control_visible);
        mBtControlVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMybz3.controlVisible()) {
                    mMybz3.setControlVisible(false);
                } else {
                    mMybz3.setControlVisible(true);
                }
            }
        });
        mBtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case 1:
                        type = 2;
                        mMybz1.setVisibility(View.VISIBLE);
                        mMybz2.setVisibility(View.GONE);
                        mMybz3.setVisibility(View.GONE);
                        break;
                    case 2:
                        type = 3;
                        mMybz1.setVisibility(View.GONE);
                        mMybz2.setVisibility(View.VISIBLE);
                        mMybz3.setVisibility(View.GONE);
                        break;
                    case 3:
                        type = 1;
                        mMybz3.setVisibility(View.VISIBLE);
                        mMybz1.setVisibility(View.GONE);
                        mMybz2.setVisibility(View.GONE);
                        break;
                }
            }
        });


        mBtAnimStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start) {
                    start = false;
                    stopanimator();

                } else {
                    start = true;
                    startAnimator();
                }
            }
        });
    }

    /**
     * 开启动画
     */
    public void startAnimator() {
        mMybz3.startAnimator();
    }

    public void stopanimator() {

        mMybz3.stopanimator();
    }
}
