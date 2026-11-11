package com.allteach.maze.student;

import java.util.List;
import java.util.Random;

/** Student work area. The app runs with a random baseline until TODOs are completed. */
public final class MazeAgent {
    private double[][][] qTable;
    public final double alpha=.25, gamma=.92, epsilon=.20;
    private final Random random=new Random(20261111L);
    public MazeAgent() {
        // TODO 1: allocate qTable as [MazeEnvironment.ROWS][MazeEnvironment.COLS][4].
        // Keep the default all-zero initialization.
    }
    public int chooseAction(int row,int col,List<Integer> legalActions) {
        // TODO 2: epsilon-greedy only over legalActions. Break greedy ties randomly.
        return legalActions.get(random.nextInt(legalActions.size())); // Baseline random policy; replace.
    }
    public double maxQ(int row,int col,List<Integer> legalActions) {
        // TODO 3: return the largest legal action value at the next state.
        return 0.0;
    }
    public void updateQ(int row,int col,int action,double reward,int nextRow,int nextCol,boolean terminal,List<Integer> nextLegalActions) {
        // TODO 4: Q(s,a) ← Q(s,a)+α[reward+(terminal ? 0 : γ·maxQ(s'))−Q(s,a)].
        // Do not bootstrap terminal states. No solution is provided in this starter.
    }
}
