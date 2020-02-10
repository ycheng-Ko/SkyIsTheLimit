package com.example.skyisthelimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_start = findViewById(R.id.btn_start);

        btn_start.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
//                DisplayMetrics dm = new DisplayMetrics();
//                getWindowManager().getDefaultDisplay().getMetrics(dm);
//                setTitle("寬 , 高 = " + dm.widthPixels + " , " + dm.heightPixels);
                Intent intent = new Intent(MainActivity.this , Camera.class);
                startActivity( intent );

            }
        });
    }
}
