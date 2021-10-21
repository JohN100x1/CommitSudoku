package com.example.commitsudoku;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SudokuBoard extends View {
    private MainActivity activity;

    private final int boardColour;
    private final int cellFillColour;
    private final int cellsHighlightColour;
    private final Paint boardColourPaint = new Paint();
    private final Paint cellFillColourPaint = new Paint();
    private final Paint cellsHighlightColourPaint = new Paint();

    private final int letterColour;
    private final int letterColourSolve;

    private final Paint letterPaint = new Paint();
    private final Rect letterPaintBounds = new Rect();

    private int cellSize;
    private int offsetY;

    private final SudokuSolve solver = new SudokuSolve();

    public SudokuBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        try {
            activity = (MainActivity) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SudokuBoard, 0, 0);

        try{
            boardColour = a.getInteger(R.styleable.SudokuBoard_boardColour, 0);
            cellFillColour = a.getInteger(R.styleable.SudokuBoard_cellFillColour, 0);
            cellsHighlightColour = a.getInteger(R.styleable.SudokuBoard_cellsHighlightColour, 0);
            letterColour = a.getInteger(R.styleable.SudokuBoard_letterColour, 0);
            letterColourSolve = a.getInteger(R.styleable.SudokuBoard_letterColourSolve, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int width, int height){
        super.onMeasure(width, height);

        int dimension = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        cellSize = dimension / 9;

        setMeasuredDimension(dimension, dimension);
        offsetY = this.getMeasuredHeight() / 3;
    }

    @Override
    protected void onDraw(Canvas canvas){
        boardColourPaint.setStyle(Paint.Style.STROKE);
        boardColourPaint.setStrokeWidth(16);
        boardColourPaint.setColor(boardColour);
        boardColourPaint.setAntiAlias(true);

        cellFillColourPaint.setStyle(Paint.Style.FILL);
        cellFillColourPaint.setColor(cellFillColour);
        cellFillColourPaint.setAntiAlias(true);

        cellsHighlightColourPaint.setStyle(Paint.Style.FILL);
        cellsHighlightColourPaint.setColor(cellsHighlightColour);
        cellsHighlightColourPaint.setAntiAlias(true);

        letterPaint.setStyle(Paint.Style.FILL);
        letterPaint.setAntiAlias(true);
        letterPaint.setColor(letterColour);
        letterPaint.setTextSize(cellSize);

        colourCell(canvas, solver.getSelectedRow(), solver.getSelectedColumn());
        canvas.drawRect(0,0,getWidth(), getHeight(), boardColourPaint);
        drawBoard(canvas);
        drawNumbers(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        boolean isValid;
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN){
            activity.boardPress();
            int row = (int) Math.ceil((-offsetY+y)/cellSize);
            int col = (int) Math.ceil(x/cellSize);
            solver.setSelectedRow(row);
            solver.setSelectedColumn(col);
            isValid = true;
        } else {
            isValid = false;
        }
        return isValid;
    }

    private void drawNumbers(Canvas canvas){
        for (int r=0; r<9; r++){
            for (int c=0; c<9; c++){
                if (solver.getBoard()[r][c] != 0){
                    String text = Integer.toString(solver.getBoard()[r][c]);
                    float width, height;

                    letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
                    width = letterPaint.measureText(text);
                    height = letterPaintBounds.height();

                    canvas.drawText(text, (c*cellSize) + (cellSize - width)/2, offsetY+(r+1)*cellSize - (cellSize - height)/2, letterPaint);
                }
            }
        }

        letterPaint.setColor(letterColourSolve);

        for (ArrayList<Object> letter : solver.getEmptyBoxIndex()){
            int r = (int) letter.get(0);
            int c = (int) letter.get(1);
            String text = Integer.toString(solver.getBoard()[r][c]);
            float width, height;

            letterPaint.getTextBounds(text, 0, text.length(), letterPaintBounds);
            width = letterPaint.measureText(text);
            height = letterPaintBounds.height();

            canvas.drawText(text, (c*cellSize) + (cellSize - width)/2, offsetY+(r+1)*cellSize - (cellSize - height)/2, letterPaint);
        }
    }

    private void colourCell(Canvas canvas, int r, int c){
        if (solver.isValidPos(r, c)){
            int[] box = getBox(r, c);
            canvas.drawRect((c-1)*cellSize, offsetY, c*cellSize, offsetY+cellSize*9, cellsHighlightColourPaint);
            canvas.drawRect(0, offsetY+(r-1)*cellSize, cellSize*9, offsetY+r*cellSize, cellsHighlightColourPaint);
            canvas.drawRect(box[0]*cellSize, offsetY+box[1]*cellSize, box[2]*cellSize, offsetY+box[3]*cellSize, cellsHighlightColourPaint);
            canvas.drawRect((c-1)*cellSize, offsetY+(r-1)*cellSize, c*cellSize, offsetY+r*cellSize, cellFillColourPaint);
        }
        invalidate();
    }

    private void drawThickLine(){
        boardColourPaint.setStyle(Paint.Style.STROKE);
        boardColourPaint.setStrokeWidth(10);
        boardColourPaint.setColor(boardColour);
    }

    private void drawThinLine(){
        boardColourPaint.setStyle(Paint.Style.STROKE);
        boardColourPaint.setStrokeWidth(4);
        boardColourPaint.setColor(boardColour);
    }

    private void drawBoard(Canvas canvas){
        for (int c = 0; c < 10; c++){
            if (c%3 == 0){
                drawThickLine();
            } else {
                drawThinLine();
            }
            canvas.drawLine(cellSize * c, offsetY, cellSize * c, offsetY + getWidth(), boardColourPaint);
        }
        for (int r = 0; r < 10; r++){
            if (r%3 == 0){
                drawThickLine();
            } else {
                drawThinLine();
            }
            canvas.drawLine(0, offsetY + cellSize * r, getWidth(), offsetY + cellSize * r, boardColourPaint);
        }
    }

    private int[] getBox(int r, int c){
        int[] box = new int[4];
        box[0] = 3*((c-1)/3);
        box[1] = 3*((r-1)/3);
        box[2] = 3*((c+2)/3);
        box[3] = 3*((r+2)/3);
        return box;
    }

    public SudokuSolve getSolver(){
        return this.solver;
    }
}
