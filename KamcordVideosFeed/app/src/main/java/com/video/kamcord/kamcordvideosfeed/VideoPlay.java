package com.video.kamcord.kamcordvideosfeed;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoPlay extends AppCompatActivity {

    private VideoView videoView;
    private String video_url;
    private MediaController mediaController;
    private Uri link;
    private ProgressBar progressBar;
    private TextView heartCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        //Specify ActionBar properties.
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Video");
        getSupportActionBar().setIcon(R.drawable.kamcordlogo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        video_url = getIntent().getStringExtra("video_url");
        String hearCount = getIntent().getStringExtra("heartCount");

        videoView = (VideoView)findViewById(R.id.video_view);
        progressBar = (ProgressBar) findViewById(R.id.videoProgressBar);
        heartCount = (TextView) findViewById(R.id.heartCountText);

        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        mediaController.setMediaPlayer(videoView);
        videoView.setMediaController(mediaController);
        //Toast.makeText(getApplicationContext(),video_url,Toast.LENGTH_LONG).show();
        new videoInBackground().execute(video_url);
        heartCount.setText(hearCount);
    }

    /**********************************************************************************************/
    /*
        AsyncTask videoInBackground loads .mp4 video in background and plays it in videoView.
     */
    private class videoInBackground extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                //link = Uri.parse(params[0].replace(" ","%20"));
                link = Uri.parse(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            videoView.setVideoURI(link);
            videoView.requestFocus();
            videoView.start();
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                if(videoView.isPlaying())
                    videoView.stopPlayback();
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
