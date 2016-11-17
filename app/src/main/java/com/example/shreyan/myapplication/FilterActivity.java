package com.example.shreyan.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FilterActivity extends AppCompatActivity {
    String tweet;
    String[] keywords;
    SQLiteDatabase db;
    EditText present,avail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Intent i=getIntent();
        tweet=i.getStringExtra("Tweet");

        //remove keywords which are urls
        keywords=tweet.split(" ");
        present=(EditText)findViewById(R.id.presentET);
        avail=(EditText)findViewById(R.id.avail);
        String str="";
        for(String s:keywords){
            str+=s+", ";
        }
        avail.setText(str);
        loadKeywords();
        Button b=(Button)findViewById(R.id.addKeys);
        b.setOnClickListener(new AddtoDB());
    }


    void loadKeywords(){
        db=openOrCreateDatabase("mydb",MODE_PRIVATE,null);
       // db.execSQL("drop table Keywords");
        db.execSQL("create table IF NOT EXISTS Keywords(" +
                "Name varchar(30) primary key)");
        Cursor res=db.rawQuery("Select * from Keywords",null);
        res.moveToFirst();
        String keys="";
        while(res.moveToNext()){
            keys+=res.getString(0)+", ";
        }
        Log.i("Keys",keys);
        if(keys.length()>1){
            keys=keys.substring(0,keys.length()-1);
        }else{
            keys="";
        }
        present.setText(keys);


    }

    class AddtoDB implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String query="insert into Keywords (Name) values ";
            String keys=avail.getText().toString();
            String [] keysToBeInserted=keys.split(",");
            for(String s:keysToBeInserted){
                query+="('"+s+"'),";
            }
            query=query.substring(0,query.length()-1);
            db.execSQL(query);
            Log.i("Query",query);
            Intent i=new Intent(FilterActivity.this,MainActivity.class);
            db.close();
            startActivity(i);
        }
    }


}
