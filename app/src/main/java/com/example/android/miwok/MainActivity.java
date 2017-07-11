/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    static MediaPlayer mMediaPlayer;
    static AudioManager mAudioManager;
    static AudioManager.OnAudioFocusChangeListener onFocuschangeListener = new AudioManager.OnAudioFocusChangeListener() {
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

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // Obtaining an instance of the audiomanager object from the system services
        // So that we can get audio focus to play our media
        mAudioManager = (AudioManager)this.getSystemService(Context.AUDIO_SERVICE);

    }

    /**
     *  Used to release the mediaplayer object.
     */
    private static void releaseMediaPlayer(){

        // Check if mediaPlayer object is null before calling the release method.
        if ( mMediaPlayer != null){

            // Release the mediaplayer object by calling the release() method.
            mMediaPlayer.release();

            // Assign null to mediaplayer to signify that there is no audio associated to it.
            mMediaPlayer = null;
        }
    }



    // If you have large number of fragments or fragment with large datasets, use FragmentStatePagerAdapter
    // As it creates, saves, destroy the fragment as opposed to keeping it in memory like this class.
    private class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter{


        FragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            super.getPageTitle(position);

            switch(position){
                case 0:
                    return "Colors";
                case 1:
                    return "Phrases";
                case 2:
                    return "Family";
                case 3:
                    return "Numbers";
                default:
                    return "Colors";

            }
        }

        @Override
        public Fragment getItem(int position) {

            MyFragment fragment = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("POSITION" , position);
            fragment.setArguments(bundle);

            return fragment ;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


    public static class MyFragment extends Fragment {

        ArrayList<Words> words = new ArrayList<Words>();


        public MyFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View rootView = inflater.inflate(R.layout.fragment, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.list);
            int position = getArguments().getInt("POSITION");
            final int color ;

            switch(position){
                case 0:

                    // Adding Colors list.
                    words.add(new Words("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
                    words.add(new Words("green", "chokokki", R.drawable.color_green, R.raw.color_green));
                    words.add(new Words("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
                    words.add(new Words("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
                    words.add(new Words("black", "kululli", R.drawable.color_black, R.raw.color_black));
                    words.add(new Words("white", "kelelli", R.drawable.color_white, R.raw.color_white));
                    words.add(new Words("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
                    words.add(new Words("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

                    color = R.color.category_colors;

                    break;
                case 1:

                    // Adding Phrases list
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
                    color = R.color.category_phrases;
                    break;
                case 2:

                    // Adding Family list
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
                    color = R.color.category_family;
                    break;
                case 3:

                    // Adding Numbers list
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
                    color = R.color.category_numbers;
                    break;

                default:

                    // Adding Colors list.
                    words.add(new Words("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
                    words.add(new Words("green", "chokokki", R.drawable.color_green, R.raw.color_green));
                    words.add(new Words("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
                    words.add(new Words("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
                    words.add(new Words("black", "kululli", R.drawable.color_black, R.raw.color_black));
                    words.add(new Words("white", "kelelli", R.drawable.color_white, R.raw.color_white));
                    words.add(new Words("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
                    words.add(new Words("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
                    color = R.color.category_colors;
                    break;
            }

            listView.setAdapter(new CustomAdapter(getActivity().getBaseContext(), words,color));


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // Initialize the mediaplayer object with the corret audio file to start playing.
                    mMediaPlayer = MediaPlayer.create(getContext(),words.get(position).getAudioId());

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





            return rootView;
        }

        @Override
        public void onStop(){

            super.onStop();

            // Release the mediaplayer object when the activity is no longer on top.
            releaseMediaPlayer();

        }


    }
}
