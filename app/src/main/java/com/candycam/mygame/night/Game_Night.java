package com.candycam.mygame.night;

import android.content.Context;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.candycam.mygame.R;
import com.candycam.mygame.orignal.Cell;
import com.candycam.mygame.night.Colors_Night;
import com.candycam.mygame.orignal.MainActivity;

import java.util.ArrayList;

public class Game_Night {

    private final int n = 4;
    private int score;
    private Cell[] board;
    private boolean gameOver;
    private Context ctx;
    Handler mainHandler;
    Animation fadeInAnimation;

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Cell[] getBoard() {
        return board;
    }

    public void setBoard(Cell[] board) {
        this.board = board;
    }

    public int getN() {
        return n;
    }

    public Game_Night(Context ctx) {
        //create the board
        this.score = 0;
        this.gameOver = false;
        this.board = new Cell[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[(i * n) + j] = new Cell();
            }
        }
        this.ctx = ctx;
        mainHandler = new Handler(ctx.getMainLooper());
        fadeInAnimation = AnimationUtils.loadAnimation(ctx, R.anim.abc_fade_in);
        fadeInAnimation.setDuration(300);

        generateInitial();

    }

    public boolean isGameOver() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[(i * n) + j].getValue() == 0) return false;
                if (j + 1 < n && board[(i * n) + j + 1] != null) {
                    if (board[(i * n) + j].getValue() == board[(i * n) + j + 1].getValue()) {
                        return false;
                    }
                }
                if (i + 1 < n && board[((i + 1) * n) + j] != null) {
                    if (board[(i * n) + j].getValue() == board[((i + 1) * n) + j].getValue()) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean thereAreEmptyCells() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[(i * n) + j].getValue() == 0) return true;
            }
        }
        return false;
    }

    public void resetStackable() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[(i * n) + j].setStackable(true);
            }
        }
    }

    public void generateInitial() {
        //generate two random points
        int ax, ay, bx, by;
        ax = bx = (int) (Math.random() * n);
        ay = by = (int) (Math.random() * n);
        while (ax == bx && ay == by) {
            bx = (int) (Math.random() * n);
            by = (int) (Math.random() * n);
        }

        //add values to the generated points
        board[(ax * n) + ay].setValue(2);
        board[(bx * n) + by].setValue(2);

        refreshColors();
    }

    public void generateNewValue() {
        int ax, ay;
        ax = (int) (Math.random() * n);
        ay = (int) (Math.random() * n);
        while (board[(ax * n) + ay].getValue() != 0) {
            ax = (int) (Math.random() * n);
            ay = (int) (Math.random() * n);
        }
        board[(ax * n) + ay] = new Cell(2);

        final Button temp = MainActivity_Night.PlaceholderFragment.gridIDs[(ax * n) + ay];

        if ((temp.getAnimation() == null)) {
            changeColorAndText(temp, board[(ax * n) + ay]);
//            temp.startAnimation(fadeInAnimation);

        } else {
            final int x = ax, y = ay;
            temp.getAnimation().setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    temp.startAnimation(fadeInAnimation);
                    changeColorAndText(temp, board[(x * n) + y]);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    public void moveButton(final Button from, final Button to, final Cell fromC, final Cell toC) {

        float fromX, fromY, toX, toY;
        fromX = from.getX();
        fromY = from.getY();
        toX = to.getX();
        toY = to.getY();

        final TranslateAnimation animation = new TranslateAnimation(
                0,//fromY-10,
                toX - fromX,
                0,//fromX-10,
                toY - fromY
        );
        animation.setDuration(200);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
                animation.setDuration(1);

                from.startAnimation(animation);

                changeColorAndText(from, fromC);
                changeColorAndText(to, toC);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                from.bringToFront();
                from.startAnimation(animation);
            }
        });
    }

    public void changeColorAndText(Button button, Cell buttonC) {
        String fromValue = String.valueOf(buttonC.getValue());
        if (!fromValue.equals("0")) {
            button.setText(fromValue);
            Colors_Night.changeColor(button);
            if (MainActivity_Night.PlaceholderFragment.sound_check==true){

                MainActivity_Night.PlaceholderFragment.mediaPlayer.start();
            }
        } else {
            button.setText("");
            button.setBackgroundResource(R.drawable.bg_box_night);
        }
    }

    public void refresh(final int[] fromJ, final int[] fromI, final int[] toJ, final int[] toI) {
        final ArrayList<Button> alreadyChanghed = new ArrayList<Button>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        //move the button
                        if (fromJ[(i * n) + j] != toJ[(i * n) + j] || fromI[(i * n) + j] != toI[(i * n) + j]) {
                            moveButton(
                                    MainActivity_Night.PlaceholderFragment.gridIDs[(fromI[(i * n) + j] * n) + fromJ[(i * n) + j]],
                                    MainActivity_Night.PlaceholderFragment.gridIDs[(toI[(i * n) + j] * n) + toJ[(i * n) + j]],
                                    getBoard()[(fromI[(i * n) + j] * n) + fromJ[(i * n) + j]],
                                    getBoard()[(toI[(i * n) + j] * n) + toJ[(i * n) + j]]
                            );
                            alreadyChanghed.add(MainActivity_Night.PlaceholderFragment.gridIDs[(toI[(i * n) + j] * n) + toJ[(i * n) + j]]);
                        }
                    }
                }
            }
        });
        t.start();
    }

    private void refreshColors() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //move the button
                String value = String.valueOf(getBoard()[(i * n) + j].getValue());
                if (!value.equals("0")) {
                    MainActivity_Night.PlaceholderFragment.gridIDs[(i * n) + j].setText(value);
                    Colors_Night.changeColor(MainActivity_Night.PlaceholderFragment.gridIDs[(i * n) + j]);
                } else {
                    MainActivity_Night.PlaceholderFragment.gridIDs[(i * n) + j].setText("");
                    MainActivity_Night.PlaceholderFragment.gridIDs[(i * n) + j].setBackgroundResource(R.drawable.bg_box_night);
                }

            }
        }
    }

    public void up() {
        boolean changesMade = false;
        int[] fromI = new int[n * n], toI = new int[n * n];
        int[] fromJ = new int[n * n], toJ = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!board[(i * n) + j].isEmpty()) {
                    int ii = i;
                    boolean flag = true;

                    fromJ[(i * n) + j] = toJ[(i * n) + j] = j;
                    fromI[(i * n) + j] = toI[(i * n) + j] = i;

                    while (flag && ii > 0) {
                        //if the cell on top is not empty
                        if ((board[((ii - 1) * n) + j] != null) && !board[((ii - 1) * n) + j].isEmpty()) {
                            //if the cell on top is equal to the current cell and if it wasn't already stacked in this step
                            if (board[(ii * n) + j].getValue() == board[((ii - 1) * n) + j].getValue() && board[((ii) * n) + j].isStackable()) {
                                //double the value of the top cell and remove the bottom cell
                                board[((ii - 1) * n) + j].setValue(board[((ii - 1) * n) + j].getValue() * 2);
                                board[((ii - 1) * n) + j].setStackable(false);
                                board[(ii * n) + j] = new Cell();
                                score += board[((ii - 1) * n) + j].getValue();
                                flag = false;
                                changesMade = true;

                                toI[(i * n) + j] = ii - 1;
                            }
                        } else {
                            board[((ii - 1) * n) + j] = new Cell(board[(ii * n) + j].getValue());
                            board[(ii * n) + j] = new Cell();
                            changesMade = true;

                            toI[(i * n) + j] = ii - 1;

                        }
                        ii--;
                    }
                }
            }
        }

        refresh(fromJ, fromI, toJ, toI);

        if (thereAreEmptyCells() && changesMade) generateNewValue();
        resetStackable();
    }

    public void down() {
        boolean changesMade = false;
        int[] fromI = new int[n * n], toI = new int[n * n];
        int[] fromJ = new int[n * n], toJ = new int[n * n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (!board[(i * n) + j].isEmpty()) {
                    int ii = i;
                    int jj = j;
                    boolean flag = true;

                    fromJ[(i * n) + j] = toJ[(i * n) + j] = j;
                    fromI[(i * n) + j] = toI[(i * n) + j] = i;

                    while (flag && ii < n - 1) {
                        //if the cell on top is not empty
                        if ((board[((ii + 1) * n) + j] != null) && !board[((ii + 1) * n) + j].isEmpty()) {
                            //if the cell on top is equal to the current cell and if it wasn't already stacked in this step
                            if (board[(ii * n) + j].getValue() == board[((ii + 1) * n) + j].getValue() && board[((ii) * n) + j].isStackable()) {
                                //double the value of the top cell and remove the bottom cell
                                board[((ii + 1) * n) + j].setValue(board[((ii + 1) * n) + j].getValue() * 2);
                                board[((ii + 1) * n) + j].setStackable(false);
                                board[(ii * n) + j] = new Cell();
                                score += board[((ii + 1) * n) + j].getValue();
                                flag = false;
                                changesMade = true;

                                toI[(i * n) + j] = ii + 1;
                            }
                        } else {
                            board[((ii + 1) * n) + j] = new Cell(board[(ii * n) + j].getValue());
                            board[(ii * n) + j] = new Cell();
                            changesMade = true;

                            toI[(i * n) + j] = ii + 1;
                        }
                        ii++;
                    }
                }
            }
        }
        refresh(fromJ, fromI, toJ, toI);

        if (thereAreEmptyCells() && changesMade) generateNewValue();
        resetStackable();
    }

    public void left() {
        boolean changesMade = false;
        int[] fromI = new int[n * n], toI = new int[n * n];
        int[] fromJ = new int[n * n], toJ = new int[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!board[(i * n) + j].isEmpty()) {
                    int ii = i;
                    int jj = j;
                    boolean flag = true;

                    fromJ[(i * n) + j] = toJ[(i * n) + j] = j;
                    fromI[(i * n) + j] = toI[(i * n) + j] = i;

                    while (flag && jj > 0) {
                        //if the cell on top is not empty
                        if ((board[(i * n) + jj - 1] != null) && !board[(i * n) + jj - 1].isEmpty()) {
                            //if the cell on top is equal to the current cell and if it wasn't already stacked in this step
                            if (board[(i * n) + jj].getValue() == board[(i * n) + jj - 1].getValue() && board[(i * n) + jj].isStackable()) {
                                //double the value of the top cell and remove the bottom cell
                                board[(i * n) + jj - 1].setValue(board[(i * n) + jj - 1].getValue() * 2);
                                board[(i * n) + jj - 1].setStackable(false);
                                board[(i * n) + jj] = new Cell();
                                score += board[(i * n) + jj - 1].getValue();
                                flag = false;
                                changesMade = true;

                                toJ[(i * n) + j] = jj - 1;
                            }
                        } else {
                            board[(i * n) + jj - 1] = new Cell(board[(i * n) + jj].getValue());
                            board[(i * n) + jj] = new Cell();
                            changesMade = true;

                            toJ[(i * n) + j] = jj - 1;
                        }
                        jj--;
                    }
                }
            }
        }
        refresh(fromJ, fromI, toJ, toI);
        if (thereAreEmptyCells() && changesMade) generateNewValue();
        resetStackable();
    }

    public void right() {
        boolean changesMade = false;
        int[] fromI = new int[n * n], toI = new int[n * n];
        int[] fromJ = new int[n * n], toJ = new int[n * n];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (!board[(i * n) + j].isEmpty()) {
                    int ii = i;
                    int jj = j;
                    boolean flag = true;

                    fromJ[(i * n) + j] = toJ[(i * n) + j] = j;
                    fromI[(i * n) + j] = toI[(i * n) + j] = i;

                    while (flag && jj < n - 1) {
                        //if the cell on top is not empty
                        if ((board[(i * n) + jj + 1] != null) && !board[(i * n) + jj + 1].isEmpty()) {
                            //if the cell on top is equal to the current cell and if it wasn't already stacked in this step
                            if (board[(i * n) + jj].getValue() == board[(i * n) + jj + 1].getValue() && board[(i * n) + jj].isStackable()) {
                                //double the value of the top cell and remove the bottom cell
                                board[(i * n) + jj + 1].setValue(board[(i * n) + jj + 1].getValue() * 2);
                                board[(i * n) + jj + 1].setStackable(false);
                                board[(i * n) + jj] = new Cell();
                                score += board[(i * n) + jj + 1].getValue();
                                flag = false;
                                changesMade = true;

                                toJ[(i * n) + j] = jj + 1;
                            }
                        } else {
                            board[(i * n) + jj + 1] = new Cell(board[(i * n) + jj].getValue());
                            board[(i * n) + jj] = new Cell();
                            changesMade = true;

                            toJ[(i * n) + j] = jj + 1;
                        }
                        jj++;
                    }
                }
            }
        }
        refresh(fromJ, fromI, toJ, toI);
        if (thereAreEmptyCells() && changesMade) generateNewValue();
        resetStackable();
    }
}

