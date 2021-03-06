package com.example.commitsudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuSolve {
    private static int selected_row;
    private static int selected_column;

    int [][] board;
    int [][] solvedBoard;
    boolean [][] editBoard;
    ArrayList<ArrayList<Object>> emptyBoxIndex;

    SudokuSolve(){
        selected_row = -1;
        selected_column = -1;

        board = new int[9][9];
        solvedBoard = new int[9][9];
        editBoard = new boolean[9][9];

        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                board[r][c] = 0;
                solvedBoard[r][c] = 0;
                editBoard[r][c] = true;
            }
        }

        emptyBoxIndex = new ArrayList<>();
    }

    public void getEmptyBoxIndexes(){
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                if (this.board[r][c] == 0){
                    this.emptyBoxIndex.add(new ArrayList<>());
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size()-1).add(r);
                    this.emptyBoxIndex.get(this.emptyBoxIndex.size()-1).add(c);
                }
            }
        }
    }

    public boolean check(int row, int col){
        if (this.board[row][col] > 0){
            for (int i=0; i<9; i++){
                if (this.board[i][col] == this.board[row][col] && row != i){
                    return false;
                }
                if (this.board[row][i] == this.board[row][col] && col != i){
                    return false;
                }
            }
            int boxCol = col/3;
            int boxRow = row/3;
            for (int r=3*boxRow; r<3*boxRow+3; r++){
                for (int c=3*boxCol; c<3*boxCol+3; c++) {
                    if (this.board[r][c] == this.board[row][col] && row != r && col != c){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean solve(SudokuBoard display){
        int row = -1;
        int col = -1;
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                if (this.board[r][c] == 0){
                    row = r;
                    col = c;
                    break;
                }
            }
        }
        if (row == -1){
            return true;
        }

        // Permutation List
        List<Integer> dList = new ArrayList<>();
        for (int i=1; i<10; i++){
            dList.add(i);
        }
        Collections.shuffle(dList);

        for (int i: dList){
            this.board[row][col] = i;
            display.invalidate();
            if (check(row, col)){
                if (solve(display)){
                    return true;
                }
            }
            this.board[row][col] = 0;
        }
        return false;
    }

    public void resetBoard(){
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                this.board[r][c] = 0;
            }
        }
        this.emptyBoxIndex = new ArrayList<>();
    }

    public void generateNewBoard(SudokuBoard display, int numClues){
        resetBoard();
        solve(display);

        // Get Random clue Co-ordinates
        List<Integer> cList = new ArrayList<>();
        for (int i=1; i<81; i++){
            cList.add(i);
        }
        Collections.shuffle(cList);
        Set<Integer> clues = new HashSet<>();
        for(int i = 0; i < numClues; i++){
            clues.add(cList.get(i));
        }
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                this.solvedBoard[r][c] = this.board[r][c];
                if (!clues.contains(9*r+c)){
                    this.board[r][c] = 0;
                    this.editBoard[r][c] = true;
                } else {
                    this.editBoard[r][c] = false;
                }
            }
        }
    }

    public void revealBoard(){
        for (int r=0; r<9; r++){
            System.arraycopy(this.solvedBoard[r], 0, this.board[r], 0, 9);
        }
    }

    public boolean isValidPos(int r, int c){
        return r >= 1 && r <= 9 && c >= 1 && c <= 9;
    }
    public boolean isEditable(int r, int c){
        return this.editBoard[r][c];
    }

    public void setNumberPos(int num){
        if (isValidPos(selected_row, selected_column) && editBoard[selected_row-1][selected_column-1]){
            if (this.board[selected_row-1][selected_column-1] == num){
                this.board[selected_row-1][selected_column-1] = 0;
            } else {
                this.board[selected_row-1][selected_column-1] = num;
            }
        }
    }

    public int[][] getBoard(){
        return this.board;
    }

    public ArrayList<ArrayList<Object>> getEmptyBoxIndex(){
        return this.emptyBoxIndex;
    }

    public int getSelectedRow(){
        return selected_row;
    }

    public int getSelectedColumn(){
        return selected_column;
    }

    public void setSelectedRow(int r){
        selected_row = r;
    }

    public void setSelectedColumn(int c){
        selected_column = c;
    }
}
