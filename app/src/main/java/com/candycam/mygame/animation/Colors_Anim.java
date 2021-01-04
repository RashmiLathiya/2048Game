package com.candycam.mygame.animation;

import android.graphics.Color;
import android.widget.Button;

import com.candycam.mygame.R;

import pl.droidsonroids.gif.GifImageButton;


public class Colors_Anim {

    static int lightText = Color.parseColor("#f9f6f2");
    static int darkText = Color.parseColor("#FFFFFF");

    static int V0 = Color.parseColor("#2B2A3B");
    static int V2 = Color.parseColor("#eee4da");
    static int V4 = Color.parseColor("#ede0c8");
    static int V8 = Color.parseColor("#f2b179");
    static int V16 = Color.parseColor("#f59563");
    static int V32 = Color.parseColor("#f67c5f");
    static int V64 = Color.parseColor("#f65e3b");
    static int V128 = Color.parseColor("#edcf72");
    static int V256 = Color.parseColor("#edcc61");
    static int V512 = Color.parseColor("#edc850");
    static int V1024 = Color.parseColor("#edc53f");
    static int V2048 = Color.parseColor("#edc22e");
    static int V4096 = Color.parseColor("#3c3a32");

    public static void changeColor(GifImageButton button) {
        String value = String.valueOf("2");
        switch (value) {
            case "":
                return;
            case "0":
                return;
            case "2":
                button.setBackgroundResource(R.drawable.a_2);
                //    b.setTextColor(Colors_Anim.darkText);
                break;
            case "4":
                button.setBackgroundResource(R.drawable.a_4);
//                    b.setTextColor(Colors_Anim.darkText);
                break;
            case "8":
                button.setBackgroundResource(R.drawable.a_8);
//                    b.setTextColor(Colors_Anim.lightText);
                break;
            case "16":
                button.setBackgroundResource(R.drawable.a_16);
//                    b.setTextColor(Colors_Anim.lightText);
                break;
            case "32":
                button.setBackgroundResource(R.drawable.a_32);
//                    button.setTextColor(Colors_Anim.lightText);
                break;
            case "64":
                button.setBackgroundResource(R.drawable.a_64);
//                    button.setTextColor(Colors_Anim.lightText);
                break;
            case "128":
                button.setBackgroundResource(R.drawable.a_128);
//                    button.setTextColor(Colors_Anim.lightText);
                break;
            case "256":
                button.setBackgroundResource(R.drawable.a_256);
//                    button.setTextColor(Colors_Anim.lightText);
                break;
            case "512":
                button.setBackgroundResource(R.drawable.a_512);
//                    button.setTextColor(Colors_Anim.lightText);
                break;
            case "1024":
                button.setBackgroundResource(R.drawable.a_1024);
//                    button.setTextColor(Colors_Anim.lightText);
                break;
            case "2048":
                button.setBackgroundResource(R.drawable.a_512);
//                    button.setTextColor(Colors_Anim.lightText);
                break;
            case "4096":
                button.setBackgroundResource(R.drawable.a_512);
//                    button.setTextColor(Colors_Anim.lightText);
                break;
        }

    }

}
