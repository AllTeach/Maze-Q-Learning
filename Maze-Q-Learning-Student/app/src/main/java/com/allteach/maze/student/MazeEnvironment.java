package com.allteach.maze.student;

import java.util.ArrayList;
import java.util.List;

/**
 * The environment, not the learning algorithm.
 *
 * A state is one current grid position (row, col). This class defines the
 * deterministic transition function: given state + action, {@link #step}
 * returns the next state and immediate reward. It deliberately does not know
 * about Q-values, epsilon, alpha, or training episodes.
 */
public final class MazeEnvironment {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int ACTION_COUNT = 4;

    public static final int ROWS = 5;
    public static final int COLS = 5;
    public static final int START_ROW = 0;
    public static final int START_COL = 0;
    public static final int GOAL_ROW = 4;
    public static final int GOAL_COL = 4;
    public static final int TRAP_ROW = 2;
    public static final int TRAP_COL = 2;

    public static final double STEP_REWARD = -1.0;
    public static final double ILLEGAL_MOVE_REWARD = -3.0;
    public static final double GOAL_REWARD = 30.0;
    public static final double TRAP_REWARD = -15.0;

    private static final boolean[][] WALLS = {
            {false, false, false, false, false},
            {true, true, false, true, false},
            {false, false, false, true, false},
            {false, true, true, true, false},
            {false, false, false, false, false}
    };

    /** A wall includes cells outside the grid; that keeps boundary checks in one place. */
    public boolean isWall(int row, int col) {
        return isOutsideMaze(row, col) || WALLS[row][col];
    }

    public boolean isGoal(int row, int col) {
        return row == GOAL_ROW && col == GOAL_COL;
    }

    public boolean isTrap(int row, int col) {
        return row == TRAP_ROW && col == TRAP_COL;
    }

    public boolean isTerminal(int row, int col) {
        return isGoal(row, col) || isTrap(row, col);
    }

    /** Returns only actions that change position to a non-wall cell. */
    public List<Integer> legalActions(int row, int col) {
        List<Integer> actions = new ArrayList<>();

        for (int action = UP; action < ACTION_COUNT; action++) {
            int nextRow = nextRow(row, action);
            int nextCol = nextCol(col, action);

            if (!isWall(nextRow, nextCol)) {
                actions.add(action);
            }
        }

        return actions;
    }

    /**
     * Applies one action. An illegal action is defined as a non-transition:
     * the agent remains in the same state and gets -3. Training should normally
     * choose only legal actions; this behavior makes the rule explicit and safe.
     */
    public Transition step(int row, int col, int action) {
        int nextRow = nextRow(row, action);
        int nextCol = nextCol(col, action);

        if (isWall(nextRow, nextCol)) {
            return new Transition(row, col, ILLEGAL_MOVE_REWARD, false);
        }

        double reward = rewardFor(nextRow, nextCol);
        boolean terminal = isTerminal(nextRow, nextCol);
        return new Transition(nextRow, nextCol, reward, terminal);
    }

    private boolean isOutsideMaze(int row, int col) {
        return row < 0 || row >= ROWS || col < 0 || col >= COLS;
    }

    private int nextRow(int row, int action) {
        if (action == UP) {
            return row - 1;
        }
        if (action == DOWN) {
            return row + 1;
        }
        return row;
    }

    private int nextCol(int col, int action) {
        if (action == LEFT) {
            return col - 1;
        }
        if (action == RIGHT) {
            return col + 1;
        }
        return col;
    }

    private double rewardFor(int row, int col) {
        if (isGoal(row, col)) {
            return GOAL_REWARD;
        }
        if (isTrap(row, col)) {
            return TRAP_REWARD;
        }
        return STEP_REWARD;
    }

    /** Immutable observation after an action: next state, immediate reward, and terminal status. */
    public static final class Transition {
        public final int row;
        public final int col;
        public final double reward;
        public final boolean terminal;

        Transition(int row, int col, double reward, boolean terminal) {
            this.row = row;
            this.col = col;
            this.reward = reward;
            this.terminal = terminal;
        }
    }
}
