package com.example.android.miwok;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Marcus Khan on 4/30/2017.
 */

public class CustomAdapter extends ArrayAdapter<Words> {
    private int color;


    public CustomAdapter(@NonNull Context context, List<Words> list, int color) {
        super(context, 0, list);
        this.color = color;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_list_miwok_item_1, parent, false);
            RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout);
            layout.setBackgroundResource(color);
        } else {
            view = convertView;
        }

        TextView defaultText = (TextView) view.findViewById(R.id.english);
        defaultText.setText(getItem(position).getDefaultTranslation());

        TextView miwokText = (TextView) view.findViewById(R.id.miwok);
        miwokText.setText(getItem(position).getMiwokTranslation());

        ImageView image = (ImageView) view.findViewById(R.id.image);
        if(getItem(position).getImageId() == Words.NO_IMAGE){
            image.setVisibility(View.GONE);
        }else {
            image.setImageResource(getItem(position).getImageId());
        }



        return view;
    }
}