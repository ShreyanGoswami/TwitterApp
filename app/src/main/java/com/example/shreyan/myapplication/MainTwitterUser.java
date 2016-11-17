package com.example.shreyan.myapplication;

import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Shreyan on 9/21/2016.
 */

public class MainTwitterUser {
    ConfigurationBuilder cb;
    String CONSUMER_KEY,CONSUMER_KEY_SECRET,accessToken,accessTokenSecret;
    MainTwitterUser(){
        CONSUMER_KEY="ZC2zqhYonjwPkFkWDd9sAELcV";
        CONSUMER_KEY_SECRET="znAz00yWyBDSP2Z0d6N8J03CXBU9JouaKbNKmtzBhKTIRAKTDf";
        accessToken = "387542053-Rta02C3mhuH1AqMlomgHjXKvq28n0zsTY1NfYZur";
        accessTokenSecret="SiUDVX5eI7Ro7ySnkLRl1OaTK34fxslXulAzCoeMk3VNX";
        cb=new ConfigurationBuilder();
    }

    ConfigurationBuilder getAuthenticationDetails(){
        cb.setDebugEnabled(true).setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret).setOAuthConsumerKey(CONSUMER_KEY).setOAuthConsumerSecret(CONSUMER_KEY_SECRET);
        return cb;
    }
}
