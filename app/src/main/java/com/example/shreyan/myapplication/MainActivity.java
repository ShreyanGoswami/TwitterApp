package com.example.shreyan.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.AsyncListUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import twitter4j.Status;
import twitter4j.User;

public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    List<TwitterUser> ls;
    TimeLine tl;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView) findViewById(R.id.tlview);
        db=openOrCreateDatabase("mydb",MODE_PRIVATE,null);
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);
       // StrictMode.setVmPolicy (new StrictMode.VmPolicy.Builder().detectAll().penaltyLog()
                //.penaltyDeath().build());
        try {
            tl = new TimeLine();
            ls = new LoadTimelineTask().execute(tl).get();
            ls=filterTweets(ls);
            ListView simpleList = (ListView) findViewById(R.id.tlview);
            final TimelineList adapter = new TimelineList(getApplicationContext(), ls);
            simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    TwitterUser u=adapter.getItem(i);
                    Intent loadInfo=new Intent(MainActivity.this,DispInfo.class);
                    Log.i("ID",""+u.getId());
                    loadInfo.putExtra("Name",u.getName());
                    loadInfo.putExtra("id",""+u.getId());
                    loadInfo.putExtra("tweet",u.getTweet());
                    loadInfo.putExtra("handle",u.getHandle());
                    loadInfo.putExtra("profile",u.getProfilePic());
                    startActivity(loadInfo);
                }
            });
            simpleList.setAdapter(adapter);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ComposeTweet.class);
                startActivity(i);
            }
        });

    }

    List<TwitterUser> filterTweets(List<TwitterUser> ls){
        List<TwitterUser> newList=new ArrayList<TwitterUser>();
        String query="select * from keywords";
        List<String> keys=new ArrayList<>();
        Cursor data=db.rawQuery(query,null);
        data.moveToFirst();
        Log.i("FILTER","Inside");
        while(data.moveToNext()){
            keys.add(data.getString(0));
        }

        for(TwitterUser u:ls){
            int flag=0;
            for(String k:keys){
                if(u.getTweet().contains(k)){
                    Log.i("FITLER",u.getTweet()+" contains "+k);
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                newList.add(u);
            }
        }


        return newList;

    }
}

