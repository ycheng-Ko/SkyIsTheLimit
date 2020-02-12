package com.example.skyisthelimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MaxHeight extends AppCompatActivity {

    double time_HangTime;
    double maxHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maxheight);
        Intent intent = getIntent();

        time_HangTime = (double)(intent.getIntExtra("time_Land_down",0) -
                            intent.getIntExtra("time_Take_off",0))/1000.0;
        maxHeight = 0.5 * 9.8 * (time_HangTime / 2) * (time_HangTime / 2) * 100;

        //Toast.makeText(getApplicationContext(), "Max height: " + maxHeight + "cm", Toast.LENGTH_SHORT).show();
    }
}
