package com.allteach.maze.student;

import java.util.List;
import java.util.Random;

/**
 * Student work area: a tabular Q-learning policy.
 *
 * qTable[row][col][action] is an estimate of the long-term discounted return
 * after taking action in the current state. It is not a map of rewards and it
 * is not a value per cell: each cell needs four action values.
 *
 * The app runs with a random baseline until the TODOs are completed.
 */
public final class MazeAgent {
    private double[][][] qTable;

    /** alpha: how strongly one new observation changes an old estimate. */
    public final double alpha = .25;
    /** gamma: how much a future value is discounted; 0 means only immediate reward matters. */
    public final double gamma = .92;
    /** epsilon: probability of exploration rather than current-best exploitation. */
    public final double epsilon = .20;

    private final Random random = new Random(20261111L);

    public MazeAgent() {
        // TODO 1: allocate qTable as
        // [MazeEnvironment.ROWS][MazeEnvironment.COLS][MazeEnvironment.ACTION_COUNT].
        // Keep the default all-zero initialization.
    }

    public int chooseAction(int row, int col, List<Integer> legalActions) {
        // TODO 2: epsilon-greedy only over legalActions. Break greedy ties randomly.
        // Exploration: with probability epsilon, return one random legal action.
        // Exploitation: otherwise find the largest qTable[row][col][action]
        // among legal actions, collect all actions with that value, then select
        // one of the tied best actions randomly. Never pick a wall action.
        return legalActions.get(random.nextInt(legalActions.size()));
    }

    public double maxQ(int row, int col, List<Integer> legalActions) {
        // TODO 3: return the largest legal action value at the next state.
        // This is max_a' Q(s', a'), where s' is the transition's next state.
        return 0.0;
    }

    public void updateQ(
            int row,
            int col,
            int action,
            double reward,
            int nextRow,
            int nextCol,
            boolean terminal,
            List<Integer> nextLegalActions) {
        // TODO 4: Q(s,a) ← Q(s,a)+α[reward+(terminal ? 0 : γ·maxQ(s'))−Q(s,a)].
        // The bracket is the temporal-difference error: target minus old estimate.
        // Do not bootstrap terminal states: after goal/trap there is no next action.
        // No solution is provided in this starter.
    }
}
