package com.allteach.maze.student;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import java.util.Collections;
import java.util.List;

/** Canvas-only visualization. It receives a path; it must not contain learning logic. */
public final class MazeView extends View {
    private static final int PATH_COLOR = 0xff1565c0;
    private static final int TRAP_COLOR = 0xffef9a9a;
    private static final int GOAL_COLOR = 0xffa5d6a7;

    private final MazeEnvironment maze = new MazeEnvironment();
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Integer> path = Collections.emptyList();

    public MazeView(Context context) {
        super(context);
        setMinimumHeight(540);
    }

    /** State is encoded as row * COLS + col so the view remains independent of Trainer internals. */
    public void setPath(List<Integer> value) {
        path = value;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float cellSize = Math.min(getWidth() / (float) MazeEnvironment.COLS,
                getHeight() / (float) MazeEnvironment.ROWS);
        float leftOffset = (getWidth() - cellSize * MazeEnvironment.COLS) / 2f;

        drawCells(canvas, cellSize, leftOffset);
        drawPath(canvas, cellSize, leftOffset);
        drawLabels(canvas, cellSize, leftOffset);
    }

    private void drawCells(Canvas canvas, float cellSize, float leftOffset) {
        for (int row = 0; row < MazeEnvironment.ROWS; row++) {
            for (int col = 0; col < MazeEnvironment.COLS; col++) {
                paint.setColor(colorForCell(row, col));
                canvas.drawRect(leftOffset + col * cellSize, row * cellSize,
                        leftOffset + (col + 1) * cellSize, (row + 1) * cellSize, paint);

                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(Color.GRAY);
                canvas.drawRect(leftOffset + col * cellSize, row * cellSize,
                        leftOffset + (col + 1) * cellSize, (row + 1) * cellSize, paint);
                paint.setStyle(Paint.Style.FILL);
            }
        }
    }

    private int colorForCell(int row, int col) {
        if (maze.isWall(row, col)) {
            return Color.DKGRAY;
        }
        if (maze.isTrap(row, col)) {
            return TRAP_COLOR;
        }
        if (maze.isGoal(row, col)) {
            return GOAL_COLOR;
        }
        return Color.WHITE;
    }

    private void drawPath(Canvas canvas, float cellSize, float leftOffset) {
        paint.setColor(PATH_COLOR);
        for (int state : path) {
            int row = state / MazeEnvironment.COLS;
            int col = state % MazeEnvironment.COLS;
            float centerX = leftOffset + (col + .5f) * cellSize;
            float centerY = (row + .5f) * cellSize;
            canvas.drawCircle(centerX, centerY, cellSize * .18f, paint);
        }
    }

    private void drawLabels(Canvas canvas, float cellSize, float leftOffset) {
        paint.setColor(Color.BLACK);
        paint.setTextSize(cellSize * .22f);
        canvas.drawText("S", leftOffset + cellSize * .1f, cellSize * .3f, paint);
        canvas.drawText("G", leftOffset + (MazeEnvironment.GOAL_COL + .1f) * cellSize,
                (MazeEnvironment.GOAL_ROW + .3f) * cellSize, paint);
    }
}
