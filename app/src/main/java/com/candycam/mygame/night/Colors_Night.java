package com.candycam.mygame.night;

import android.graphics.Color;
import android.widget.Button;

import com.candycam.mygame.R;


public class Colors_Night {

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

    public static void changeColor(Button b) {
        String value = String.valueOf(b.getText().toString());
        switch (value) {
            case "":
                return;
            case "0":
                return;
            case "2":
                b.setBackgroundResource(R.drawable.n_v2);
                b.setTextColor(Colors_Night.darkText);
                break;
            case "4":
                b.setBackgroundResource(R.drawable.n_v4);
                b.setTextColor(Colors_Night.darkText);
                break;
            case "8":
                b.setBackgroundResource(R.drawable.n_v8);
                b.setTextColor(Colors_Night.lightText);
                break;
            case "16":
                b.setBackgroundResource(R.drawable.n_v16);
                b.setTextColor(Colors_Night.lightText);
                break;
            case "32":
                b.setBackgroundResource(R.drawable.n_v32);
                b.setTextColor(Colors_Night.lightText);
                break;
            case "64":
                b.setBackgroundResource(R.drawable.n_v64);
                b.setTextColor(Colors_Night.lightText);
                break;
            case "128":
                b.setBackgroundResource(R.drawable.n_v128);
                b.setTextColor(Colors_Night.lightText);
                break;
            case "256":
                b.setBackgroundResource(R.drawable.n_v256);
                b.setTextColor(Colors_Night.lightText);
                break;
            case "512":
                b.setBackgroundResource(R.drawable.n_v512);
                b.setTextColor(Colors_Night.lightText);
                break;
            case "1024":
                b.setBackgroundResource(R.drawable.n_v1024);
                b.setTextColor(Colors_Night.lightText);
                break;
            case "2048":
                b.setBackgroundResource(R.drawable.n_v2048);
                b.setTextColor(Colors_Night.lightText);
                break;
            case "4096":
                b.setBackgroundResource(R.drawable.n_v4096);
                b.setTextColor(Colors_Night.lightText);

                break;
        }
    }

}
