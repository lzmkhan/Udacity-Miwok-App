package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity {


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

        // Obtaining an instance of the audiomanager object from the system services
        // So that we can get audio focus to play our media
        mAudioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Words> words = new ArrayList<Words>();
        words.add(new Words("father", "әpә",R.drawable.family_father,R.raw.family_father));
        words.add(new Words("mother", "әṭa", R.drawable.family_mother,R.raw.family_mother));
        words.add(new Words("son", "angsi", R.drawable.family_son,R.raw.family_son));
        words.add(new Words("daughter", "tune", R.drawable.family_daughter,R.raw.family_daughter));
        words.add(new Words("older brother", "taachi", R.drawable.family_older_brother,R.raw.family_older_brother));
        words.add(new Words("younger brother", "chalitti", R.drawable.family_younger_brother,R.raw.family_younger_brother));
        words.add(new Words("older sister", "teṭe", R.drawable.family_older_sister,R.raw.family_older_sister));
        words.add(new Words("younger sister", "kolliti", R.drawable.family_younger_sister,R.raw.family_younger_sister));
        words.add(new Words("grandmother", "ama", R.drawable.family_grandmother,R.raw.family_grandmother));
        words.add(new Words("grandfather", "paapa", R.drawable.family_grandfather,R.raw.family_grandfather));


        ListView gridView = (ListView) findViewById(R.id.list);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // Initialize the mediaplayer object with the corret audio file to start playing.
                mMediaPlayer = MediaPlayer.create(FamilyActivity.this,words.get(position).getAudioId());

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
        gridView.setAdapter(new CustomAdapter(this, words, R.color.category_family));

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
    }


    @Override
    public void onStop(){
        super.onStop();

        // Release the mediaplayer object when the activity is no longer on top.
        releaseMediaPlayer();

    }

}
