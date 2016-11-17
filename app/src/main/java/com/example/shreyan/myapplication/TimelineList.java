package com.example.shreyan.myapplication;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;
import twitter4j.User;

/**
 * Created by Shreyan on 9/5/2016.
 */

class ListModel{
    String handle,tweet,name;
    ListModel(String h,String t){
        this.handle=h;
        this.tweet=t;
    }

    String getHandle(){
        return handle;
    }
    String getTweet(){
        return tweet;
    }
}

class TimelineList extends BaseAdapter {
    Context context;
    List<TwitterUser> tweets;
    LayoutInflater inflater;


    TimelineList(Context AppContext, List<TwitterUser> s) {
        this.context = AppContext;
        this.tweets = s;
        inflater = LayoutInflater.from(AppContext);
    }


    public int getCount() {
        if (tweets.size() > 0) {
            return tweets.size();
        } else {
            return 0;
        }
    }


    public TwitterUser getItem(int pos) {
        return tweets.get(pos);
    }


    public long getItemId(int pos) {
        return 0;
    }


    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.row_layout, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView tweet = (TextView) view.findViewById(R.id.tweet_content);
        TextView handle = (TextView) view.findViewById(R.id.handle);
        ImageView img = (ImageView) view.findViewById(R.id.smallPic);

        tweet.setText(tweets.get(i).getTweet());
        name.setText(tweets.get(i).getName());
        handle.setText(tweets.get(i).getHandle());
        img.setImageBitmap(Bitmap.createScaledBitmap(tweets.get(i).getProfilePic(), 120, 120, false));
        return view;
    }





}

