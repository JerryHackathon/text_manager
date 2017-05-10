package com.example.brafinney.myapplication.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.brafinney.myapplication.models.Contestant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by pianoafrik on 5/9/17.
 */

public class Globall {

    public static List<Contestant> contestants = new ArrayList<>(Arrays.asList(
         new Contestant("Zolile", "https://s23.postimg.org/mgrg37xfv/15232314_10211680954483901_2785968541291555517_n.jpg"),
         new Contestant("Peter", "https://s18.postimg.org/h6p24jhvt/ohana.jpg")
    ));

   public static List<String> list = new ArrayList<String>(Arrays.asList(
           "Select Number Of Votes",
           "5",
           "10",
           "20",
           "30"
   ));

    public static List<String> listSchedule = new ArrayList<String>(Arrays.asList(
            "Select secs from now",
            "5",
            "10",
            "60",
            "120",
            "1800",
            "3600",
            "43200",
            "86400"
    ));


    public static void toast (Context context, String str ,boolean bool) {

        if(bool)
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

    }

    public static String shortCode, contestantName, herNumber, herMessage;

}
