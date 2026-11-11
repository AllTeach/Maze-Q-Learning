package com.allteach.maze.teacher;

import java.util.ArrayList;
import java.util.List;

public final class Trainer {
    public static final int MAX_STEPS_PER_EPISODE = 80;
    private final MazeEnvironment maze; private final MazeAgent agent;
    public Trainer(MazeEnvironment maze, MazeAgent agent) { this.maze=maze; this.agent=agent; }
    public Result train(int episodes) {
        int goals=0;
        for (int episode=0; episode<episodes; episode++) {
            int row=maze.startRow(), col=maze.startCol();
            for (int step=0; step<MAX_STEPS_PER_EPISODE; step++) {
                int action=agent.chooseAction(row,col,maze.legalActions(row,col));
                MazeEnvironment.Transition t=maze.step(row,col,action);
                agent.update(row,col,action,t.reward,t.row,t.col,t.terminal,t.terminal ? new ArrayList<Integer>() : maze.legalActions(t.row,t.col));
                row=t.row; col=t.col;
                if (t.terminal) { if (maze.isGoal(row,col)) goals++; break; }
            }
        }
        return new Result(goals, greedyPath());
    }
    public List<Integer> greedyPath() {
        List<Integer> path=new ArrayList<>(); boolean[][] seen=new boolean[MazeEnvironment.ROWS][MazeEnvironment.COLS];
        int row=maze.startRow(), col=maze.startCol();
        for (int steps=0; steps<MAX_STEPS_PER_EPISODE; steps++) {
            path.add(row*MazeEnvironment.COLS+col);
            if (maze.isTerminal(row,col)) return path;
            if (seen[row][col]) return new ArrayList<Integer>();
            seen[row][col]=true;
            MazeEnvironment.Transition t=maze.step(row,col,agent.bestAction(row,col,maze.legalActions(row,col)));
            row=t.row; col=t.col;
        }
        return new ArrayList<Integer>();
    }
    public static final class Result { public final int goals; public final List<Integer> path; Result(int goals,List<Integer> path){this.goals=goals;this.path=path;} }
}
