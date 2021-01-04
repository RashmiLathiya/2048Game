package com.candycam.mygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.candycam.mygame.night.MainActivity_Night;
import com.candycam.mygame.orignal.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Home_Activity extends AppCompatActivity {

    private Button orignal,night,anim;
    public static MediaPlayer mediaPlayer;
    private BottomSheetDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().setFlags(1024,1024);

        orignal=findViewById(R.id.orignal);
        night=findViewById(R.id.night);
       // anim=findViewById(R.id.anim);

        mediaPlayer =MediaPlayer.create(this,R.raw.beep);

        orignal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_Activity.this, MainActivity_Night.class);
                startActivity(intent);
            }
        });

//        anim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Home_Activity.this, MainActivity_Anim.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void onBackPressed() {
//        Intent intent=new Intent(Home_Activity.this, Back_Activity.class);
//        startActivity(intent);
        openDialog();
    }

    private void openDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_back, null);
        dialog = new BottomSheetDialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setContentView(view);
        ImageView yes=dialog.findViewById(R.id.yes);
        ImageView no=dialog.findViewById(R.id.no);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home_Activity.this,Back_Activity.class);
                startActivity(intent);

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}