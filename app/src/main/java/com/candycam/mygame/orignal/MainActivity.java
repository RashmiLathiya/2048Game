package com.candycam.mygame.orignal;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

    private static Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(1024,1024);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {

        private final int n = 4;
        private TextView score;
        private TextView reset;
        private View view;
        public static Button[] gridIDs;
        private LinearLayout grid;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
            View rootView= inflater.inflate(R.layout.fragment_main, container, false);

            gridIDs = new Button[n * n];
            score = (TextView) rootView.findViewById(R.id.score);
            reset= (TextView) rootView.findViewById(R.id.reset);
            view = (View) rootView.findViewById(R.id.view1);
            gridIDs[0] = (Button) rootView.findViewById(R.id.g00);
            gridIDs[1] = (Button) rootView.findViewById(R.id.g01);
            gridIDs[2] = (Button) rootView.findViewById(R.id.g02);
            gridIDs[3] = (Button) rootView.findViewById(R.id.g03);
            gridIDs[4] = (Button) rootView.findViewById(R.id.g10);
            gridIDs[5] = (Button) rootView.findViewById(R.id.g11);
            gridIDs[6] = (Button) rootView.findViewById(R.id.g12);
            gridIDs[7] = (Button) rootView.findViewById(R.id.g13);
            gridIDs[8] = (Button) rootView.findViewById(R.id.g20);
            gridIDs[9] = (Button) rootView.findViewById(R.id.g21);
            gridIDs[10] = (Button) rootView.findViewById(R.id.g22);
            gridIDs[11] = (Button) rootView.findViewById(R.id.g23);
            gridIDs[12] = (Button) rootView.findViewById(R.id.g30);
            gridIDs[13] = (Button) rootView.findViewById(R.id.g31);
            gridIDs[14] = (Button) rootView.findViewById(R.id.g32);
            gridIDs[15] = (Button) rootView.findViewById(R.id.g33);
//            grid = (LinearLayout)rootView.findViewById(R.id.grid);

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
                    game=new Game(getActivity());
                    score.setText("0");
                }
            });
            game = new Game(getActivity());
            refreshGrid();


            return rootView;
        }


        public void refreshGrid() {

            score.setText( String.valueOf(game.getScore()));

            if (game.isGameOver()) {
                Dialog dialog=new Dialog(getActivity());
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.game_over);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                Button yes=dialog.findViewById(R.id.yes);
                Button no=dialog.findViewById(R.id.no);
                yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        game = new Game(getActivity());
                        refreshGrid();
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getActivity().finish();
                    }
                });
                dialog.show();
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
