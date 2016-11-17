package com.example.shreyan.myapplication;

import android.graphics.Bitmap;

/**
 * Created by Shreyan on 9/21/2016.
 */

class TwitterUser{
    String name,handle,tweet;
    Bitmap bitmap;
    long id;


    TwitterUser(String name,String handle,String tweet,Bitmap smallProfilePic,long id){
        this.name=name;
        this.handle="@"+handle;
        this.tweet=tweet;
        this.bitmap=smallProfilePic;
        this.id=id;
    }



    String getName(){return name;}
    String getHandle(){return handle;}
    String getTweet(){return tweet;}
    Bitmap getProfilePic(){return bitmap;}
    long getId(){return this.id;}

}