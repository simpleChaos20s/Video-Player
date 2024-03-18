package com.example.myvideoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity{
    private SeekBar seekBar;
    private ImageButton playPauseBtn, skipNextBtn, skipPreBtn;
    private TextView currTime, videoDuration;
    private VideoView videoView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("Hello");
        videoView = (VideoView) findViewById(R.id.video_view);
        playPauseBtn = findViewById(R.id.play_button);
        skipPreBtn = findViewById(R.id.skip_previous_button);
        skipNextBtn = findViewById(R.id.skip_next_button);
        seekBar = findViewById(R.id.seek_bar);
        currTime = findViewById(R.id.text_progress);
        videoDuration = findViewById(R.id.text_total_time);

        setVideo();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                seekBar.setMax(videoView.getDuration());

                int totalDuration = videoView.getDuration();
                int currentPosition = videoView.getCurrentPosition();
                String totalDurationString = formatDuration(totalDuration);
                String currentPositionString = formatDuration(currentPosition);
                currTime.setText(currentPositionString);
                videoDuration.setText(totalDurationString);

                videoView.start();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser){
                    videoView.seekTo(progress);
                    int currentPosition = videoView.getCurrentPosition();
//                    seekBar.setProgress(currentPosition);
                    String currentPositionString = formatDuration(currentPosition);
                    currTime.setText(currentPositionString);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                setVideo();
                if(videoView.isPlaying()){
                    videoView.pause();
//                    playPauseBtn.setImageDrawable(getResources().getDrawable(R.drawable.pause_button));
                    playPauseBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pause_button));
                }else {
                    videoView.start();
                    playPauseBtn.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.play_button));
                }
            }
        });

        skipPreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(videoView.getDuration()-10000);
            }
        });

        skipNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.seekTo(videoView.getDuration()+10000);
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                seekBar.setProgress(0);
                Toast.makeText(getApplicationContext(), "Video completed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSeekBar() {
        seekBar.setMax(videoView.getDuration());

    }

    private String formatDuration(int duration) {
        int hours = duration / 3600000;
        int minutes = (duration % 3600000) / 60000;
        int seconds = (duration % 60000) / 1000;
        if(hours!= 0){
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }else{
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    private void setVideo() {
        String uriPath
                = "android.resource://"
                + getPackageName()
                + "/" + R.raw.kaguradeath;
        System.out.println(uriPath);
        Uri uri = Uri.parse(uriPath);
        videoView.setVideoURI(uri);
    }
}