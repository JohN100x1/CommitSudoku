package com.example.commitsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
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
            gameBoardSolver.resetBoard();
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