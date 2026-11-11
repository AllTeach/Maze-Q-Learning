package com.allteach.maze.teacher;

import java.util.List;
import java.util.Random;

/** Tabular Q-learning agent; q[row][col][action]. */
public final class MazeAgent {
    private final double[][][] q = new double[MazeEnvironment.ROWS][MazeEnvironment.COLS][4];
    private final Random random;
    private final double alpha, gamma;
    private double epsilon;

    public MazeAgent(double alpha, double gamma, double epsilon, long seed) {
        this.alpha=alpha; this.gamma=gamma; this.epsilon=epsilon; this.random=new Random(seed);
    }
    public int chooseAction(int row, int col, List<Integer> legal) {
        if (legal.isEmpty()) throw new IllegalArgumentException("A non-terminal state needs a legal action");
        if (random.nextDouble() < epsilon) return legal.get(random.nextInt(legal.size()));
        return bestAction(row, col, legal);
    }
    public int bestAction(int row, int col, List<Integer> legal) {
        double best = Double.NEGATIVE_INFINITY;
        for (int action : legal) best=Math.max(best,q[row][col][action]);
        int count=0; for (int action : legal) if (Double.compare(q[row][col][action],best)==0) count++;
        int choice=random.nextInt(count);
        for (int action : legal) if (Double.compare(q[row][col][action],best)==0 && choice--==0) return action;
        throw new AssertionError("unreachable");
    }
    public void update(int row, int col, int action, double reward, int nextRow, int nextCol, boolean terminal, List<Integer> nextLegal) {
        double target=reward + (terminal ? 0 : gamma * maxQ(nextRow,nextCol,nextLegal));
        q[row][col][action]+=alpha*(target-q[row][col][action]);
    }
    public double maxQ(int row, int col, List<Integer> legal) {
        double best=Double.NEGATIVE_INFINITY; for (int action : legal) best=Math.max(best,q[row][col][action]); return best;
    }
    public double value(int row, int col, int action) { return q[row][col][action]; }
    public void setEpsilon(double epsilon) { this.epsilon=epsilon; }
}
