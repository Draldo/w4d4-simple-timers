package com.example.admin.timersexample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName() + "TAG_";
    private ProgressBar mProgressBar;
    private TextView mTextview;
    private int mCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = (ProgressBar) findViewById(R.id.a_main_progress);
        mTextview = (TextView) findViewById(R.id.timer_text);
    }

    public void doMagic(View view) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCounter++;
                Log.d(TAG, "run: " + Thread.currentThread());
                if (mCounter%2 == 0)
                    mProgressBar.setVisibility(View.VISIBLE);
                else
                    mProgressBar.setVisibility(View.INVISIBLE);
                mProgressBar.setMax(100);
                mProgressBar.setProgress(mCounter);
                mTextview.setText(String.valueOf(mCounter));
                if (mCounter<100)
                    handler.postDelayed(this, 1000);
                else
                    Toast.makeText(getApplicationContext(), "Time's up", Toast.LENGTH_LONG).show();
            }
        }, 1000);

    }

    public void doAlarm(View view) {
        Intent intent = new Intent(this, MyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis()+ 5000,
                    pendingIntent);
        }
    }
}
