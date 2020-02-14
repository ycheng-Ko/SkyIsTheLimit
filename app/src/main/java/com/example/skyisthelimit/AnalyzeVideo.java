package com.example.skyisthelimit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

        final Uri mVideoUri = Uri.parse(mVideoFileName);
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
                int next = mVideoView.getCurrentPosition() + 33; // return msec !!!

                mVideoView.seekTo( next );
                mVideoIsPlaying = false;
                mStartandPause.setImageResource(R.mipmap.btn_videostart);

                mVideoView.start();
                mVideoView.pause();
                //callpause(next, mVideoView);
                //mVideoView.pause();

                //Toast.makeText(getApplicationContext(), "Expected: " + next + "Current time: " + (float)mVideoView.getCurrentPosition() / 1000.0, Toast.LENGTH_SHORT).show();
            }
        });

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next = mVideoView.getCurrentPosition() - 33; // return msec !!!

                mVideoView.seekTo( next );
                mVideoIsPlaying = false;
                mStartandPause.setImageResource(R.mipmap.btn_videostart);
;
                mVideoView.start();
                mVideoView.pause();
                //Toast.makeText(getApplicationContext(), "Expected: " + next + "Current time: " + (float)mVideoView.getCurrentPosition() / 1000.0, Toast.LENGTH_SHORT).show();
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
                mIsTakeOffOk = false;
                mIsLandDownOk = false;
                mCaptureTimingOk.setVisibility(View.INVISIBLE);
            }
        });


        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        Integer now = mVideoView.getCurrentPosition();
                        Log.d("CV2I" , now.toString());

                        Toast.makeText(getApplicationContext(), "Current time: " + (float)now/ 1000.0, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}
