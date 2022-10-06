package com.hfad.stopwatch_aw;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Chronometer stopwatch;
    private boolean running = false;
    private long offset = 0;

    public static final String OFFSET_KEY = "offset";
    public static final String RUNNING_KEY = "running";
    public static final String BASE_KEY = "base";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatch = findViewById(R.id.stopwatch);

        Button btnStart = findViewById(R.id.start_button);
        Button btnPause = findViewById(R.id.pause_button);
        Button btnReset = findViewById(R.id.reset_button);

        if(savedInstanceState != null)
        {
            offset = savedInstanceState.getLong(OFFSET_KEY);
            running = savedInstanceState.getBoolean(RUNNING_KEY);


            if(running)
            {
                stopwatch.setBase(savedInstanceState.getLong(BASE_KEY));
                stopwatch.start();
            }
            else
            {
                setBaseTime();
            }
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running)
                {
                   setBaseTime();
                   stopwatch.start();
                   running = true;

                }
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(running)
                {
                    saveOffset();
                    stopwatch.stop();
                    running = false;

                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset = 0;
                setBaseTime();
            }
        });
    }

    protected void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putLong(OFFSET_KEY, offset);
        savedInstanceState.putBoolean(RUNNING_KEY, running);
        savedInstanceState.putLong(BASE_KEY, stopwatch.getBase());
    }

    public void setBaseTime()
    {
        stopwatch.setBase(SystemClock.elapsedRealtime() - offset);
    }

    public void saveOffset()
    {
        offset = SystemClock.elapsedRealtime() - stopwatch.getBase();
    }
}