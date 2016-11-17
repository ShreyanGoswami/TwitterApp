package com.example.shreyan.myapplication;

/**
 * Created by Shreyan on 8/29/2016.
 */

import java.util.List;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

public class TimeLine {

    Twitter twitter;
    AccessToken oathAccessToken;
    Paging p;
    int pageCount,noPerPage;
    TimeLine(){
        pageCount=1;
        noPerPage=20;
        MainTwitterUser us=new MainTwitterUser();
        ConfigurationBuilder cb=us.getAuthenticationDetails();
        TwitterFactory tf=new TwitterFactory(cb.build());
        twitter=tf.getInstance();
        p=new Paging(pageCount,noPerPage);
    }

    List<Status> getTweets() throws TwitterException{
        List<Status> latest=twitter.getHomeTimeline(p);
        this.pageCount++;
        p.setPage(pageCount);
        return latest;
    }
}

