package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener audioFocusChangeListener;
    int mPosition = 0;
    int mPreviousVolume;
    int mMediaState = 0;
    private static final int PLAYING = 1;
    private static final int PAUSED = 2;
    private static final int RELEASED = 3;
    private static final int DUCKED = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);



        final ArrayList<Words> words = new ArrayList<Words>();
        words.add(new Words("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Words("Two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Words("Three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new Words("Four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Words("Five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Words("Six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Words("Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Words("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Words("Nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Words("Ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));

        audioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);


        // Implement callback method to handle various audio focus states
        audioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener(){

            @Override
            public void onAudioFocusChange(int focusChange) {

                Log.d("AUDIO FOCUS", "" + focusChange);
                switch(focusChange){
                    case AudioManager.AUDIOFOCUS_GAIN:
                        Log.d("AUDIO FOCUS","GAINED");
                        //start/resume audio
                        if(mMediaPlayer != null  && mMediaState == PAUSED)
                        {
                            mMediaPlayer.start();
                        }
                        else if ( mMediaState == DUCKED){
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,mPreviousVolume, 0);
                        }

                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        //stop audio and release mediaplayer resources
                        releaseMediaPlayer();

                        mMediaState = RELEASED;
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        //pause audio
                        if ( mMediaPlayer != null && mMediaPlayer.isPlaying()){
                            mMediaPlayer.pause();
                            mMediaState = PAUSED;
                        }
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        //lower the volume
                        if ( mMediaPlayer != null && mMediaPlayer.isPlaying()){

                            mPreviousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
                            mMediaState = DUCKED;
                        }
                        break;

                }
            }
        };



        ListView gridView = (ListView) findViewById(R.id.list);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosition = position;
                int result = audioManager.requestAudioFocus(audioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    releaseMediaPlayer();
                    mMediaPlayer = MediaPlayer.create(NumbersActivity.this, words.get(mPosition).getAudioId());
                    mMediaPlayer.start();
                    mMediaState = PLAYING;
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                            mMediaState = RELEASED;

                        }
                    });
                }
                Log.d("NumbersActivity","Current Word: " +  words.get(position));
            }
        });
        gridView.setAdapter(new CustomAdapter(this, words, R.color.category_numbers));
    }



    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            audioManager.abandonAudioFocus(audioFocusChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        releaseMediaPlayer();
    }
}
