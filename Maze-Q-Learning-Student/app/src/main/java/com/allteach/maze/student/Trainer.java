package com.allteach.maze.student;

import java.util.ArrayList;
import java.util.List;

/**
 * Student integration task: connects the environment and MazeAgent.
 *
 * This class does not decide the maze rules and does not draw pixels. Its only
 * job is to repeat the learning conversation: state -> action -> transition ->
 * Q update. The starter app deliberately uses a random demonstration until
 * this class is implemented.
 */
public final class Trainer {
    public static final int MAX_STEPS_PER_EPISODE = 80;
    private final MazeEnvironment maze;
    private final MazeAgent agent;

    public Trainer(MazeEnvironment maze, MazeAgent agent) {
        this.maze = maze;
        this.agent = agent;
    }

    /**
     * TODO 5: Run the requested number of episodes.
     *
     * For each episode:
     * 1. Start at (0, 0).
     * 2. Repeat at most MAX_STEPS_PER_EPISODE times.
     * 3. Get legal actions; ask the agent to choose one.
     * 4. Ask the environment for the transition.
     * 5. Update the Q-table using the old state, action, reward, next state,
     *    terminal flag, and legal actions at the next state.
     * 6. Move to the next state and stop this episode when it is terminal.
     *
     * Count goal arrivals and return a Result. Do not update Android Views here:
     * training must remain pure Java so it is testable and can run off the UI thread.
     *
     * The old row/col must be supplied to updateQ before assigning row/col from
     * the transition. A terminal transition has no valid next action, so pass an
     * empty next-action list and rely on the terminal flag.
     */
    public Result train(int episodes) {
        int goals = 0;
        // TODO: Implement the episode and step loops described above.
        return new Result(goals, greedyPath());
    }

    /**
     * TODO 6: Follow only the best known legal action from the start.
     *
     * Add every visited state as row * MazeEnvironment.COLS + col. Stop at a
     * terminal state. If a state repeats, or the step cap is reached, return
     * an empty list: that makes a policy cycle visible in the UI.
     *
     * This method is an evaluation, not training: never call chooseAction
     * because it sometimes explores randomly. Instead find the best legal Q
     * action using the same tie rule as the greedy branch of chooseAction.
     */
    public List<Integer> greedyPath() {
        return new ArrayList<>();
    }

    public static final class Result {
        public final int goals;
        public final List<Integer> path;

        public Result(int goals, List<Integer> path) {
            this.goals = goals;
            this.path = path;
        }
    }
}
