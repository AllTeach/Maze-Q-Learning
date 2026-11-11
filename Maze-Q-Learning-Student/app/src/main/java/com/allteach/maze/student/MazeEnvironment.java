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
    public static final int UP=0, DOWN=1, LEFT=2, RIGHT=3, ROWS=5, COLS=5;
    private static final boolean[][] WALL={{false,false,false,false,false},{true,true,false,true,false},{false,false,false,true,false},{false,true,true,true,false},{false,false,false,false,false}};
    /** A wall includes cells outside the grid; that keeps boundary checks in one place. */
    public boolean isWall(int row,int col){return row<0||row>=ROWS||col<0||col>=COLS||WALL[row][col];}
    public boolean isGoal(int row,int col){return row==4&&col==4;}
    public boolean isTrap(int row,int col){return row==2&&col==2;}
    public boolean isTerminal(int row,int col){return isGoal(row,col)||isTrap(row,col);}
    /** Returns only actions that change position to a non-wall cell. */
    public List<Integer> legalActions(int row,int col){List<Integer> a=new ArrayList<>();for(int x=0;x<4;x++)if(!isWall(row+(x==UP?-1:x==DOWN?1:0),col+(x==LEFT?-1:x==RIGHT?1:0)))a.add(x);return a;}

    /**
     * Applies one action. An illegal action is defined as a non-transition:
     * the agent remains in the same state and gets -3. Training should normally
     * choose only legal actions; this behavior makes the rule explicit and safe.
     */
    public Transition step(int row,int col,int action){int nr=row+(action==UP?-1:action==DOWN?1:0),nc=col+(action==LEFT?-1:action==RIGHT?1:0);if(isWall(nr,nc))return new Transition(row,col,-3,false);double reward=isGoal(nr,nc)?30:isTrap(nr,nc)?-15:-1;return new Transition(nr,nc,reward,isTerminal(nr,nc));}

    /** Immutable observation after an action: next state, immediate reward, and terminal status. */
    public static final class Transition {public final int row,col;public final double reward;public final boolean terminal;Transition(int r,int c,double v,boolean t){row=r;col=c;reward=v;terminal=t;}}
}
