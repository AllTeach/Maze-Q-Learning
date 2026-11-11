package com.allteach.maze.teacher;

import java.util.ArrayList;
import java.util.List;

/** Deterministic 5x5 environment. Illegal moves stay in the same state. */
public final class MazeEnvironment {
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    public static final int ROWS = 5, COLS = 5;
    public static final double STEP_REWARD = -1, WALL_REWARD = -3, GOAL_REWARD = 30, TRAP_REWARD = -15;
    private static final boolean[][] WALL = {
            {false,false,false,false,false}, {true,true,false,true,false}, {false,false,false,true,false},
            {false,true,true,true,false}, {false,false,false,false,false}};

    public int startRow() { return 0; }
    public int startCol() { return 0; }
    public int goalRow() { return 4; }
    public int goalCol() { return 4; }
    public boolean isWall(int row, int col) { return row < 0 || row >= ROWS || col < 0 || col >= COLS || WALL[row][col]; }
    public boolean isGoal(int row, int col) { return row == goalRow() && col == goalCol(); }
    public boolean isTrap(int row, int col) { return row == 2 && col == 2; }
    public boolean isTerminal(int row, int col) { return isGoal(row, col) || isTrap(row, col); }
    public List<Integer> legalActions(int row, int col) {
        List<Integer> actions = new ArrayList<>();
        for (int action = UP; action <= RIGHT; action++) if (!isWall(nextRow(row, action), nextCol(col, action))) actions.add(action);
        return actions;
    }
    public Transition step(int row, int col, int action) {
        int nextRow = nextRow(row, action), nextCol = nextCol(col, action);
        if (isWall(nextRow, nextCol)) return new Transition(row, col, WALL_REWARD, false, true);
        double reward = isGoal(nextRow, nextCol) ? GOAL_REWARD : isTrap(nextRow, nextCol) ? TRAP_REWARD : STEP_REWARD;
        return new Transition(nextRow, nextCol, reward, isTerminal(nextRow, nextCol), false);
    }
    private int nextRow(int row, int action) { return row + (action == UP ? -1 : action == DOWN ? 1 : 0); }
    private int nextCol(int col, int action) { return col + (action == LEFT ? -1 : action == RIGHT ? 1 : 0); }
    public static final class Transition {
        public final int row, col; public final double reward; public final boolean terminal, illegal;
        Transition(int row, int col, double reward, boolean terminal, boolean illegal) { this.row=row; this.col=col; this.reward=reward; this.terminal=terminal; this.illegal=illegal; }
    }
}
