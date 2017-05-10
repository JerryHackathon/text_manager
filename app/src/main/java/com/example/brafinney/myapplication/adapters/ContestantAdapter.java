package com.example.brafinney.myapplication.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.brafinney.myapplication.R;
import com.example.brafinney.myapplication.models.Contestant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pianoafrik on 5/9/17.
 */

public class ContestantAdapter extends ArrayAdapter<Contestant> {

    private static class ViewHolder {
        TextView    contestantName;
        ImageView   contestantImage;
    }

    public ContestantAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Contestant> objects) {
        super(context, resource, objects);
    }

    @Nullable
    @Override
    public Contestant getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Contestant contestant = getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.contestant_item, parent, false);
            viewHolder.contestantName = (TextView)convertView.findViewById(R.id.contestantName);
            viewHolder.contestantImage = (ImageView)convertView.findViewById(R.id.contestantImage);

            convertView.setTag(viewHolder);
        } else{

            viewHolder = (ViewHolder)convertView.getTag();
        }


        viewHolder.contestantName.setText(contestant.getName());
        Picasso.with(getContext())
                .load(contestant.getImage_url())
                .into(viewHolder.contestantImage);


        return convertView;
    }
}
