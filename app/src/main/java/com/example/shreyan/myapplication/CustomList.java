package com.example.shreyan.myapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import twitter4j.Status;

/**
 * Created by Shreyan on 8/29/2016.
 */

public class CustomList extends ListActivity {

    TimeLine tl;
    List<Status> latest;
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
            Class c = Class.forName("com.example.shreyan.myapplication.MainActivity.java");
            Intent i = new Intent(getApplicationContext(), c);
            startActivity(i);
        }catch(Exception e){

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<Status>(CustomList.this,android.R.layout.simple_list_item_1,latest));
        tl=new TimeLine();
        try {
            latest = tl.getTweets();
        }catch(Exception e){

        }
    }
}
