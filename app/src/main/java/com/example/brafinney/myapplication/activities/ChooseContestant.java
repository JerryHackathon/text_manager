package com.example.brafinney.myapplication.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brafinney.myapplication.R;
import com.example.brafinney.myapplication.adapters.ContestantAdapter;
import com.example.brafinney.myapplication.initializers.MyActivity;
import com.example.brafinney.myapplication.interfaces.ContestantResponse;
import com.example.brafinney.myapplication.models.Contestant;
import com.example.brafinney.myapplication.utils.Globall;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidsdk.devless.io.devless.main.Devless;

public class ChooseContestant extends AppCompatActivity {

    ListView listView;
    public static final String SHORTCODE_BROUGHT_BACK = "SHORTCODEBROUGHBACK";
    public static final String SELECTED_CONTESTANT = "SELECTEDCONTESTANT";
    public static final String TIME_BROUGHT_BACK = "TIMESBROUGHTBACK";
    Devless devless;
    MyActivity app;
    SharedPreferences sp;
    EditText etContestantName;
    TextView btnAddContestant;
    ProgressDialog pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_contestant);
        listView = (ListView) findViewById(R.id.contestant_list);

        sp = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        app = (MyActivity)getApplication();
        devless = app.getDevless();
        devless.addUserToken(sp);
        pg = new ProgressDialog(this);

        final String phoneValue =  getIntent().getStringExtra(MainActivity.SHORT_CODE);
        final String timesValue =  getIntent().getStringExtra(MainActivity.TIMES);


        etContestantName = (EditText)findViewById(R.id.etContestantName);
        btnAddContestant = (TextView)findViewById(R.id.btnAddContestant);

        refreshList();


        btnAddContestant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contestantName = Globall.getValue(etContestantName);
                if(contestantName.length() > 0){
                    addContestant(contestantName);
                    etContestantName.setText("");
                } else {
                    Globall.toast(view.getContext(), "fill the nini", false);
                }



            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Contestant contestant = Globall.contestants.get(i);
                final TextView tv =  (TextView) view.findViewById(R.id.contestantName);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       devless.delete(Globall.CONTESTANT_SERVICE, Globall.CONTESTANT_TABLE, contestant.getId(),
                               new Devless.RequestResponse() {
                                   @Override
                                   public void OnSuccess(String s) {
                                       refreshList();
                                       Globall.toast(ChooseContestant.this, s, true);
                                   }
                               });
                    }
                });


                final ImageView image = (ImageView)view.findViewById(R.id.contestantImage);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> fields = new HashMap<>();
                        fields.put("name", Globall.getValue(etContestantName));
                        devless.edit(Globall.CONTESTANT_SERVICE, Globall.CONTESTANT_TABLE, fields, contestant.getId(),
                                new Devless.RequestResponse() {
                                    @Override
                                    public void OnSuccess(String s) {
                                        refreshList();
                                    }
                                });

                    }
                });

                final ImageView imageStatus = (ImageView)view.findViewById(R.id.imgStatus);
                imageStatus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(ChooseContestant.this, MainActivity.class)
                                .putExtra(SELECTED_CONTESTANT, (Serializable) contestant)
                                .putExtra(SHORTCODE_BROUGHT_BACK, phoneValue)
                                .putExtra(TIME_BROUGHT_BACK, timesValue));
                    }
                });

            }
        });

    }


    public void addContestant(String name) {
        pg.setTitle("adding data");
        pg.show();
        Globall.addContestant(devless, name, new Devless.RequestResponse() {
            @Override
            public void OnSuccess(String s) {
                refreshList();
                pg.dismiss();
            }
        });
    }


    public void refreshList() {
        pg.setTitle("Getting contestants from devless");
        pg.show();

        Globall.getContestants(devless, new ContestantResponse() {
            @Override
            public void OnSuccess(List<Contestant> contestants) {
                pg.dismiss();
                Globall.contestants = contestants;
                ContestantAdapter adapter = new ContestantAdapter(ChooseContestant.this,0, Globall.contestants);
                listView.setAdapter(adapter);
            }
        });
    }


}
