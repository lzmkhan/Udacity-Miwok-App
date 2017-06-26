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
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    final static ArrayList<Words> wordsColor = new ArrayList<Words>();
    final static ArrayList<Words> wordsFamily = new ArrayList<Words>();
    final static ArrayList<Words> wordsPhrases = new ArrayList<Words>();
    final static ArrayList<Words> wordsNumbers = new ArrayList<Words>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);





    }


    public void initializeData(){

        // Adding Colors list.
        wordsColor.add(new Words("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        wordsColor.add(new Words("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        wordsColor.add(new Words("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        wordsColor.add(new Words("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        wordsColor.add(new Words("black", "kululli", R.drawable.color_black, R.raw.color_black));
        wordsColor.add(new Words("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        wordsColor.add(new Words("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        wordsColor.add(new Words("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));


        // Adding Family list
        wordsFamily.add(new Words("father", "әpә",R.drawable.family_father,R.raw.family_father));
        wordsFamily.add(new Words("mother", "әṭa", R.drawable.family_mother,R.raw.family_mother));
        wordsFamily.add(new Words("son", "angsi", R.drawable.family_son,R.raw.family_son));
        wordsFamily.add(new Words("daughter", "tune", R.drawable.family_daughter,R.raw.family_daughter));
        wordsFamily.add(new Words("older brother", "taachi", R.drawable.family_older_brother,R.raw.family_older_brother));
        wordsFamily.add(new Words("younger brother", "chalitti", R.drawable.family_younger_brother,R.raw.family_younger_brother));
        wordsFamily.add(new Words("older sister", "teṭe", R.drawable.family_older_sister,R.raw.family_older_sister));
        wordsFamily.add(new Words("younger sister", "kolliti", R.drawable.family_younger_sister,R.raw.family_younger_sister));
        wordsFamily.add(new Words("grandmother", "ama", R.drawable.family_grandmother,R.raw.family_grandmother));
        wordsFamily.add(new Words("grandfather", "paapa", R.drawable.family_grandfather,R.raw.family_grandfather));


        // Adding Numbers list
        wordsNumbers.add(new Words("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        wordsNumbers.add(new Words("Two", "otiiko", R.drawable.number_two, R.raw.number_two));
        wordsNumbers.add(new Words("Three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        wordsNumbers.add(new Words("Four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        wordsNumbers.add(new Words("Five", "massokka", R.drawable.number_five, R.raw.number_five));
        wordsNumbers.add(new Words("Six", "temmokka", R.drawable.number_six, R.raw.number_six));
        wordsNumbers.add(new Words("Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        wordsNumbers.add(new Words("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        wordsNumbers.add(new Words("Nine", "wo’e", R.drawable.number_nine, R.raw.number_nine));
        wordsNumbers.add(new Words("Ten", "na’aacha", R.drawable.number_ten, R.raw.number_ten));


        // Adding Phrases list
        wordsPhrases.add(new Words(R.raw.phrase_where_are_you_going,"Where are you going?", "minto wuksus"));
        wordsPhrases.add(new Words(R.raw.phrase_what_is_your_name,"What is your name?", "tinnә oyaase'nә"));
        wordsPhrases.add(new Words(R.raw.phrase_my_name_is,"My name is.", "oyaaset.."));
        wordsPhrases.add(new Words(R.raw.phrase_how_are_you_feeling,"How are you feeling?", "michәksәs?"));
        wordsPhrases.add(new Words(R.raw.phrase_im_feeling_good,"I’m feeling good", "kuchi achit"));
        wordsPhrases.add(new Words(R.raw.phrase_are_you_coming,"Are you coming?", "әәnәs'aa?"));
        wordsPhrases.add(new Words(R.raw.phrase_yes_im_coming,"Yes, I’m coming", "hәә’ әәnәm"));
        wordsPhrases.add(new Words(R.raw.phrase_im_coming,"I’m coming.", "әәnәm"));
        wordsPhrases.add(new Words(R.raw.phrase_lets_go,"Let’s go.", "yoowutis"));
        wordsPhrases.add(new Words(R.raw.phrase_come_here,"Come here.", "әnni'nem"));
    }


    // If you have large number of fragments or fragment with large datasets, use FragmentStatePagerAdapter
    // As it creates, saves, destroy the fragment as opposed to keeping it in memory like this class.
    private class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter{


        public FragmentPagerAdapter(FragmentManager fm) {
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

        public MyFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            View rootView = inflater.inflate(R.layout.fragment, container, false);

            ListView listView = (ListView) rootView.findViewById(R.id.list);
            int position = getArguments().getInt("POSITION");
            switch(position){
                case 0:
                    listView.setAdapter(new CustomAdapter(getActivity().getBaseContext(), wordsColor,R.color.category_colors));
                    break;
                case 1:
                    listView.setAdapter(new CustomAdapter(getActivity().getBaseContext(), wordsPhrases,R.color.category_phrases));
                    break;
                case 2:
                    listView.setAdapter(new CustomAdapter(getActivity().getBaseContext(), wordsFamily,R.color.category_family));
                    break;
                case 3:
                    listView.setAdapter(new CustomAdapter(getActivity().getBaseContext(), wordsNumbers,R.color.category_numbers));
                    break;
                default:
                    listView.setAdapter(new CustomAdapter(getActivity().getBaseContext(), wordsColor,R.color.category_colors));
                    break;
            }





            return rootView;
        }


    }
}
