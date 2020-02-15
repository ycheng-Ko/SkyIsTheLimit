package com.example.skyisthelimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MaxHeight extends AppCompatActivity {

    double time_HangTime;
    double maxHeight;
    private TextView mHangTime;
    private TextView mMaxHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxheight);
        Intent intent = getIntent();

        time_HangTime = (double)(intent.getIntExtra("time_Land_down",0) -
                            intent.getIntExtra("time_Take_off",0))/1000.0;
        maxHeight = 0.5 * 9.8 * (time_HangTime / 2) * (time_HangTime / 2) * 100;
        //Toast.makeText(getApplicationContext(), "Max height: " + maxHeight + "cm", Toast.LENGTH_SHORT).show();

        mHangTime = findViewById(R.id.hangtime);
        mMaxHeight = findViewById(R.id.maxheight);

        mHangTime.setText("  Hang time:   " + String.format("%.3f", time_HangTime) + " (sec)");
        mMaxHeight.setText("  Vertical jump: " + String.format("%.3f", maxHeight) + " (cm)");
        mHangTime.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Paintingwithchocolate-K5mo.ttf"));
        mMaxHeight.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Paintingwithchocolate-K5mo.ttf"));
    }
}
