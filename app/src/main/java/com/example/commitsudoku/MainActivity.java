package com.example.commitsudoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private SudokuBoard gameBoard;
    private SudokuSolve gameBoardSolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.SudokuBoard);
        gameBoardSolver = gameBoard.getSolver();
    }

    public void DigitPress1(View view){
        gameBoardSolver.setNumberPos(1);
        gameBoard.invalidate();
    }

    public void DigitPress2(View view){
        gameBoardSolver.setNumberPos(2);
        gameBoard.invalidate();
    }

    public void DigitPress3(View view){
        gameBoardSolver.setNumberPos(3);
        gameBoard.invalidate();
    }

    public void DigitPress4(View view){
        gameBoardSolver.setNumberPos(4);
        gameBoard.invalidate();
    }

    public void DigitPress5(View view){
        gameBoardSolver.setNumberPos(5);
        gameBoard.invalidate();
    }

    public void DigitPress6(View view){
        gameBoardSolver.setNumberPos(6);
        gameBoard.invalidate();
    }

    public void DigitPress7(View view){
        gameBoardSolver.setNumberPos(7);
        gameBoard.invalidate();
    }

    public void DigitPress8(View view){
        gameBoardSolver.setNumberPos(8);
        gameBoard.invalidate();
    }

    public void DigitPress9(View view){
        gameBoardSolver.setNumberPos(9);
        gameBoard.invalidate();
    }
}