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

public class PhrasesActivity extends AppCompatActivity {


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
        words.add(new Words(R.raw.phrase_where_are_you_going,"Where are you going?", "minto wuksus"));
        words.add(new Words(R.raw.phrase_what_is_your_name,"What is your name?", "tinnә oyaase'nә"));
        words.add(new Words(R.raw.phrase_my_name_is,"My name is.", "oyaaset.."));
        words.add(new Words(R.raw.phrase_how_are_you_feeling,"How are you feeling?", "michәksәs?"));
        words.add(new Words(R.raw.phrase_im_feeling_good,"I’m feeling good", "kuchi achit"));
        words.add(new Words(R.raw.phrase_are_you_coming,"Are you coming?", "әәnәs'aa?"));
        words.add(new Words(R.raw.phrase_yes_im_coming,"Yes, I’m coming", "hәә’ әәnәm"));
        words.add(new Words(R.raw.phrase_im_coming,"I’m coming.", "әәnәm"));
        words.add(new Words(R.raw.phrase_lets_go,"Let’s go.", "yoowutis"));
        words.add(new Words(R.raw.phrase_come_here,"Come here.", "әnni'nem"));


        ListView gridView = (ListView) findViewById(R.id.list);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Initialize the mediaplayer object with the corret audio file to start playing.
                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this,words.get(position).getAudioId());

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
        gridView.setAdapter(new CustomAdapter(this, words, R.color.category_phrases));
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
