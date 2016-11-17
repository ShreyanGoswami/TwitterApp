package com.example.shreyan.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class ComposeTweet extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);


        Button b=(Button)findViewById(R.id.sendButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MultiAutoCompleteTextView tv=(MultiAutoCompleteTextView)findViewById(R.id.box);
                String text=tv.getText().toString();

                try {
                    int t=new PostTweetTask().execute(text).get();
                    if(t==1) {
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getApplicationContext(),"Failed to send",Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}

class PostTweetTask extends AsyncTask<String,Integer,Integer>{
    MainTwitterUser user;
    Twitter t;
    @Override
    protected Integer doInBackground(String... s) {
        user=new MainTwitterUser();
        ConfigurationBuilder cb=user.getAuthenticationDetails();
        TwitterFactory tf=new TwitterFactory(cb.build());
        t=tf.getInstance();
        try{
            t.updateStatus(s[0]);
            return new Integer(1);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new Integer(0);
    }
}
