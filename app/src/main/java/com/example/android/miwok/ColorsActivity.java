package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;
    AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener onFocuschangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch(focusChange){
                case AudioManager.AUDIOFOCUS_GAIN:
                    // Audio Focus obtained

                    // Resume the audio.
                    mMediaPlayer.start();

                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    // Audio Focus Lost permanently
                    releaseMediaPlayer();
                    break;

                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    // Audio Focus Lost temporarily so we can pause our media
                    if ( mMediaPlayer != null ){
                        mMediaPlayer.pause();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        // Enabling Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Obtaining an instance of the audiomanager object from the system services
        // So that we can get audio focus to play our media
        mAudioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> words = new ArrayList<Words>();

        words.add(new Words("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Words("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Words("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Words("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Words("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Words("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        words.add(new Words("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Words("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        ListView gridView = (ListView) findViewById(R.id.list);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Initialize the mediaplayer object with the corret audio file to start playing.
                mMediaPlayer = MediaPlayer.create(ColorsActivity.this,words.get(position).getAudioId());

                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // Release the mediaplayer object once the audio file completes playing.
                        releaseMediaPlayer();
                    }
                });

                // Request audio focus to play the file.
                int result = mAudioManager.requestAudioFocus(onFocuschangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                // If Audio Focus is obtained, play the audio clip.
                if ( result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer.start();

                }
            }
        });
        gridView.setAdapter(new CustomAdapter(this, words,R.color.category_colors));

    }

    /**
     *  Used to release the mediaplayer object.
     */
    private void releaseMediaPlayer(){

        // Check if mediaPlayer object is null before calling the release method.
        if ( mMediaPlayer != null){

            // Release the mediaplayer object by calling the release() method.
            mMediaPlayer.release();

            // Assign null to mediaplayer to signify that there is no audio associated to it.
            mMediaPlayer = null;
        }

        mAudioManager.abandonAudioFocus(onFocuschangeListener);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                // Navigate to the parent activity declared in the android manifest.
                // If this activity is invoked from some other activity because of some intent filters, declared by this
                // Activity, then we need to manually start a new task and create the activity.
                // Refer https://developer.android.com/training/implementing-navigation/ancestral.html for more details.
                NavUtils.navigateUpFromSameTask(this);

                Log.d("Colors","home up clicksed");
                return true;

        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop(){
        super.onStop();

        // Release the mediaplayer object when the activity is no longer on top.
        releaseMediaPlayer();

    }



}
