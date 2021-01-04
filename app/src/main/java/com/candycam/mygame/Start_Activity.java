package com.candycam.mygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.candycam.mygame.orignal.MainActivity;

public class Start_Activity extends AppCompatActivity {
    TextView name,name2;

    int delay = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getWindow().setFlags(1024,1024);

        name=findViewById(R.id.name1);
//        Typeface type = Typeface.createFromAsset(getAssets(),"NhadiemElansDemoRegular.ttf");
//        name.setTypeface(type);



        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(Start_Activity.this, Home_Activity.class));
                finish();
            }
        }, delay);
    }
}