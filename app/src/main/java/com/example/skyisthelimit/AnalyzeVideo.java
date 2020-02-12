package com.example.skyisthelimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.Serializable;

public class AnalyzeVideo extends AppCompatActivity {

    String  mVideoFileName; // include the position and video file name together
    File mVideoFolder;

    private VideoView mVideoView;
    private MediaController mVideoControl;
    private ImageButton mStartandPause;
    private ImageButton mFront;
    private ImageButton mBack;
    private Boolean mVideoIsPlaying = false;

    private ImageButton mTakeOff;
    private ImageButton mLandDown;
    private ImageButton mCaptureTimingOk;
    private Boolean mIsTakeOffOk = false;
    private Boolean mIsLandDownOk = false;
    private int time_Take_off;
    private int time_Land_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzevideo);
        Intent intent = getIntent();
        //Camera.VideoInformation mVideoInformation = (Camera.VideoInformation)intent.getSerializableExtra("mVideoInformation");

        mVideoFileName = intent.getStringExtra("mVideoFileName");
        mVideoFolder = (File)intent.getExtras().get("mVideoFolder");

        mVideoView = (VideoView) findViewById(R.id.videoView);

        mVideoControl = new MediaController(this);
        mVideoControl.setAnchorView(mVideoView);
        mVideoView.setMediaController(mVideoControl);

        Uri mVideoUri = Uri.parse(mVideoFileName);
        mVideoView.setVideoURI(mVideoUri);
        //mVideoView.start();

        mStartandPause = (ImageButton) findViewById(R.id.btn_videoStartandPause);
        mFront = (ImageButton) findViewById(R.id.btn_videoFront);
        mBack = (ImageButton) findViewById(R.id.btn_videoBack);

        mStartandPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mVideoIsPlaying) {
                    mVideoIsPlaying = false;
                    mStartandPause.setImageResource(R.mipmap.btn_videostart);
                    mVideoView.pause();
                } else {
                    mVideoIsPlaying = true;
                    mStartandPause.setImageResource(R.mipmap.btn_videopause);
                    mVideoView.start();
                }
            }
        });

        mFront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mCurrent_msec = mVideoView.getCurrentPosition(); // return msec !!!
                //Toast.makeText(getApplicationContext(), "Current time" + mCurrent_msec+300, Toast.LENGTH_SHORT).show();

                mVideoView.seekTo(mCurrent_msec + 33);
                mVideoIsPlaying = false;
                mStartandPause.setImageResource(R.mipmap.btn_videostart);
                mVideoView.pause();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mCurrent_msec = mVideoView.getCurrentPosition(); // return msec !!!
                mVideoView.seekTo(mCurrent_msec - 33);
                mVideoIsPlaying = false;
                mStartandPause.setImageResource(R.mipmap.btn_videostart);
                mVideoView.pause();
            }
        });

        mTakeOff = findViewById(R.id.btn_takeoff);
        mLandDown = findViewById(R.id.btn_landdown);
        mCaptureTimingOk = findViewById(R.id.btn_captureTimingOk);

        mTakeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsTakeOffOk = true;
                time_Take_off = mVideoView.getCurrentPosition(); // return msec !!!

                if(mIsTakeOffOk && mIsLandDownOk) {
                    mCaptureTimingOk.setVisibility(View.VISIBLE);
                }

                Toast.makeText(getApplicationContext(), "Take-off: " + (double)time_Take_off/1000.0, Toast.LENGTH_SHORT).show();
            }
        });

        mLandDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIsLandDownOk = true;
                time_Land_down = mVideoView.getCurrentPosition(); // return msec !!!

                if(mIsTakeOffOk && mIsLandDownOk) {
                    mCaptureTimingOk.setVisibility(View.VISIBLE);
                }

                Toast.makeText(getApplicationContext(), "Land-down: " + (double)time_Land_down/1000.0, Toast.LENGTH_SHORT).show();
            }
        });

        mCaptureTimingOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnalyzeVideo.this , MaxHeight.class);
                intent.putExtra("time_Take_off", time_Take_off);
                intent.putExtra("time_Land_down", time_Land_down);
                startActivity( intent );
            }
        });
    }

}
