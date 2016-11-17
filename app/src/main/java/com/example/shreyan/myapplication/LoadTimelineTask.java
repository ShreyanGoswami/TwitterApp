package com.example.shreyan.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.User;

/**
 * Created by Shreyan on 10/21/2016.
 */

public class LoadTimelineTask extends AsyncTask<TimeLine,Integer,List<TwitterUser>> {
    TimeLine tl;
    List<TwitterUser> timelineList;
    private static final String TAG = "LoadTimelineTask";
    @Override
    protected List<TwitterUser> doInBackground(TimeLine... param) {
        tl=param[0];
        List<twitter4j.Status> l= new ArrayList<>();
        timelineList= new ArrayList<>();
        try {
            l = tl.getTweets();
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            for(twitter4j.Status s:l){
                User u=s.getUser();
                String str=u.getBiggerProfileImageURL();
                Bitmap bmp= BitmapFactory.decodeStream((InputStream)new URL(str).getContent());
                timelineList.add(new TwitterUser(u.getName(),u.getScreenName(),s.getText(),bmp,s.getId()));
            }
            return timelineList;
        }catch(Exception e){
            e.printStackTrace();
        }
        return timelineList;
    }


}
