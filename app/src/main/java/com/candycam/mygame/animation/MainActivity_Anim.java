package com.candycam.mygame.animation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.candycam.mygame.R;
import com.candycam.mygame.night.Game_Night;

import pl.droidsonroids.gif.GifImageButton;

public class MainActivity_Anim extends AppCompatActivity {

    private static Game_Anim game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

    }

    public static class PlaceholderFragment extends Fragment {

        private final int n = 4;
        private TextView score;
        private ImageView reset;
        private View view;
        public static GifImageButton[] gridIDs;
        private LinearLayout grid;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_anim, container, false);

            gridIDs = new GifImageButton[n * n];
            score = (TextView) rootView.findViewById(R.id.score);
            reset= (ImageView) rootView.findViewById(R.id.reset);
            view = (View) rootView.findViewById(R.id.view1);
            gridIDs[0] = (GifImageButton) rootView.findViewById(R.id.g00);
            gridIDs[1] = (GifImageButton) rootView.findViewById(R.id.g01);
            gridIDs[2] = (GifImageButton) rootView.findViewById(R.id.g02);
            gridIDs[3] = (GifImageButton) rootView.findViewById(R.id.g03);
            gridIDs[4] = (GifImageButton) rootView.findViewById(R.id.g10);
            gridIDs[5] = (GifImageButton) rootView.findViewById(R.id.g11);
            gridIDs[6] = (GifImageButton) rootView.findViewById(R.id.g12);
            gridIDs[7] = (GifImageButton) rootView.findViewById(R.id.g13);
            gridIDs[8] = (GifImageButton) rootView.findViewById(R.id.g20);
            gridIDs[9] = (GifImageButton) rootView.findViewById(R.id.g21);
            gridIDs[10] =(GifImageButton) rootView.findViewById(R.id.g22);
            gridIDs[11] =(GifImageButton) rootView.findViewById(R.id.g23);
            gridIDs[12] =(GifImageButton) rootView.findViewById(R.id.g30);
            gridIDs[13] =(GifImageButton) rootView.findViewById(R.id.g31);
            gridIDs[14] =(GifImageButton) rootView.findViewById(R.id.g32);
            gridIDs[15] =(GifImageButton) rootView.findViewById(R.id.g33);
//            grid = (LinearLayout)rootView.findViewById(R.id.grid);

            final MediaPlayer mediaPlayer=MediaPlayer.create(getActivity(),R.raw.beep);

            view.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
                public void onSwipeTop() {
                    //				Toast.makeText(GameActivity.this, "top", Toast.LENGTH_SHORT).show();
                    game.up();
                    refreshGrid();
                }

                public void onSwipeRight() {
                    //				Toast.makeText(GameActivity.this, "right", Toast.LENGTH_SHORT).show();
                    game.right();
                    refreshGrid();
                }

                public void onSwipeLeft() {
                    //				Toast.makeText(GameActivity.this, "left", Toast.LENGTH_SHORT).show();
                    game.left();
                    refreshGrid();
                }

                public void onSwipeBottom() {
                    //				Toast.makeText(GameActivity.this, "bottom", Toast.LENGTH_SHORT).show();
                    game.down();
                    refreshGrid();
                }
            });

            reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    game=new Game_Anim(getActivity());
                    score.setText("0");
                }
            });
            game = new Game_Anim(getActivity());
            refreshGrid();


            return rootView;
        }


        public void refreshGrid() {

            score.setText( String.valueOf(game.getScore()));

            if (game.isGameOver()) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Game Over")
                        .setMessage("Do you want to start over?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                game = new Game_Anim(getActivity());
                                refreshGrid();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                getActivity().finish();
                            }
                        })
                        .show();
            }
        }
    }

    private static class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context ctx) {
            gestureDetector = new GestureDetector(ctx, new GestureListener());
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                boolean result = false;
                try {
                    float diffY = e2.getY() - e1.getY();
                    float diffX = e2.getX() - e1.getX();
                    if (Math.abs(diffX) > Math.abs(diffY)) {
                        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                            if (diffX > 0) {
                                onSwipeRight();
                            } else {
                                onSwipeLeft();
                            }
                        }
                        result = true;
                    } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY > 0) {
                            onSwipeBottom();
                        } else {
                            onSwipeTop();
                        }
                    }
                    result = true;

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return result;
            }
        }

        public void onSwipeRight() {
        }

        public void onSwipeLeft() {
        }

        public void onSwipeTop() {
        }

        public void onSwipeBottom() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }
    }
}
