package com.example.commitsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int boardHighlightSfx;

    private SudokuBoard gameBoard;
    private SudokuSolve gameBoardSolver;
    private Button solveBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();

        solveBTN = findViewById(R.id.solveButton);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        boardHighlightSfx = soundPool.load(this, R.raw.board_highlight_sfx, 1);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

    public void boardPress(){
        soundPool.play(boardHighlightSfx, 1, 1, 0, 0, 1);
    }

    public void digitPress1(View view){
        gameBoardSolver.setNumberPos(1);
        gameBoard.invalidate();
    }

    public void digitPress2(View view){
        gameBoardSolver.setNumberPos(2);
        gameBoard.invalidate();
    }

    public void digitPress3(View view){
        gameBoardSolver.setNumberPos(3);
        gameBoard.invalidate();
    }

    public void digitPress4(View view){
        gameBoardSolver.setNumberPos(4);
        gameBoard.invalidate();
    }

    public void digitPress5(View view){
        gameBoardSolver.setNumberPos(5);
        gameBoard.invalidate();
    }

    public void digitPress6(View view){
        gameBoardSolver.setNumberPos(6);
        gameBoard.invalidate();
    }

    public void digitPress7(View view){
        gameBoardSolver.setNumberPos(7);
        gameBoard.invalidate();
    }

    public void digitPress8(View view){
        gameBoardSolver.setNumberPos(8);
        gameBoard.invalidate();
    }

    public void digitPress9(View view){
        gameBoardSolver.setNumberPos(9);
        gameBoard.invalidate();
    }

    public void solve(View view){
        if (solveBTN.getText().toString().equals(getString(R.string.Solve))){
            solveBTN.setText(getString(R.string.New));

            gameBoardSolver.getEmptyBoxIndexes();

            SolveBoardThread solveBoardThread = new SolveBoardThread();
            new Thread(solveBoardThread).start();
        } else {
            solveBTN.setText(getString(R.string.Solve));
            gameBoardSolver.generateNewBoard(gameBoard, 17);
        }
        gameBoard.invalidate();
    }

    class SolveBoardThread implements Runnable{
        @Override
        public void run() {
            gameBoardSolver.solve(gameBoard);
        }
    }
}