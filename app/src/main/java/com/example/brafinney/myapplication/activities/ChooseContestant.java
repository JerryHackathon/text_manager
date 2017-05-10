package com.example.brafinney.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.brafinney.myapplication.R;
import com.example.brafinney.myapplication.adapters.ContestantAdapter;
import com.example.brafinney.myapplication.models.Contestant;
import com.example.brafinney.myapplication.utils.Globall;

import java.io.Serializable;
import java.util.List;

public class ChooseContestant extends AppCompatActivity {

    ListView listView;
    public static final String SHORTCODE_BROUGHT_BACK = "SHORTCODEBROUGHBACK";
    public static final String SELECTED_CONTESTANT = "SELECTEDCONTESTANT";
    public static final String TIME_BROUGHT_BACK = "TIMESBROUGHTBACK";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contestant);
        listView = (ListView) findViewById(R.id.contestant_list);

        ContestantAdapter adapter = new ContestantAdapter(this,0, Globall.contestants);
        final String phoneValue =  getIntent().getStringExtra(MainActivity.SHORT_CODE);
        final String timesValue =  getIntent().getStringExtra(MainActivity.TIMES);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contestant contestant = Globall.contestants.get(i);
                Log.e("name", contestant.getName());
                Log.e("url",  contestant.getImage_url());
                startActivity(new Intent(ChooseContestant.this, MainActivity.class)
                        .putExtra(SELECTED_CONTESTANT, (Serializable) contestant)
                        .putExtra(SHORTCODE_BROUGHT_BACK, phoneValue)
                        .putExtra(TIME_BROUGHT_BACK, timesValue));

            }
        });

    }


}
