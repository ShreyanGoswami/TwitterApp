package com.example.shreyan.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class DispInfo extends AppCompatActivity {
    long id;
    String handle,name,reply,tweet;
    EditText et;
    MainTwitterUser user;
    Twitter twitter;
    TextView nametv,handletv,tweettv;
    ImageView img;
    Button b;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_info);
        initUser();
        initVariables();
        initInitalUI();

        Button replyBttn=(Button)findViewById(R.id.replyBttn);
        Button rttnBttn=(Button)findViewById(R.id.rtBttn);
        replyBttn.setOnClickListener(new ReplyAction());
        rttnBttn.setOnClickListener(new RetweetAction());
        Button filterbttn=(Button)findViewById(R.id.filterBttn);
        filterbttn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i=new Intent(DispInfo.this,FilterActivity.class);
                i.putExtra("Tweet",tweet);
                startActivity(i);

            }
        });
        Log.i("Loaded",""+id);


    }

    void initUser(){
        user=new MainTwitterUser();
        TwitterFactory tf=new TwitterFactory(user.getAuthenticationDetails().build());
        twitter=tf.getInstance();
    }

    void initVariables(){
        Intent i=getIntent();
        name=i.getStringExtra("Name");
        handle=i.getStringExtra("handle");
        //int id=Integer.parseInt(i.getStringExtra("id"));
        String idstr=i.getStringExtra("id");
        Log.i("Actual",idstr);
        id=Long.parseLong(idstr);
        tweet=i.getStringExtra("tweet");
        bmp = i.getParcelableExtra("profile");

    }

    void initInitalUI(){
        nametv=(TextView)findViewById(R.id.nametv);
        handletv=(TextView)findViewById(R.id.handletv);
        tweettv=(TextView)findViewById(R.id.tweettv);
        img=(ImageView)findViewById(R.id.profilepic);
        et=(EditText)findViewById(R.id.replyET);
        reply=handle+" ";
        et.setText(reply);
        try {
            img.setImageBitmap(Bitmap.createScaledBitmap(bmp,120,120,false));
        }catch(Exception e){

        }
        nametv.setText(name);
        handletv.setText(handle);
        tweettv.setText(tweet);
    }

    class ReplyAction implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            //Log.i("ID",""+id);
            reply=et.getText().toString();
            try {
                Boolean success = new Reply().execute(reply).get();
                Toast.makeText(getApplicationContext(),""+success,Toast.LENGTH_SHORT);
            }catch(Exception e) {

            }
            et.clearFocus();
            et.setText(handle);
        }
    }

    class Reply extends AsyncTask<String,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            StatusUpdate statusReply=new StatusUpdate(reply);
            statusReply.setInReplyToStatusId(id);
            try{
                twitter.updateStatus(statusReply);
                return true;
            }catch(TwitterException e){
                return false;
            }
        }
    }

    class RetweetAction implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            try {
                Boolean success=new Retweet().execute().get();
                Toast.makeText(getApplicationContext(),""+success,Toast.LENGTH_SHORT);
                Log.i("STATUS",""+success);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    class Retweet extends AsyncTask<Void,Integer,Boolean>{

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                twitter.retweetStatus(id);
                return true;
            }catch(Exception e){
                return false;
            }
        }
    }

}



