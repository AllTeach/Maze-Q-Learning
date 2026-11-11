package com.allteach.maze.teacher;

import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Test;

public class MazeAgentTest {
    @Test public void illegalMoveStaysPutAndHasPenalty() {
        MazeEnvironment maze=new MazeEnvironment(); MazeEnvironment.Transition t=maze.step(0,0,MazeEnvironment.UP);
        assertTrue(t.illegal); assertEquals(0,t.row); assertEquals(0,t.col); assertEquals(MazeEnvironment.WALL_REWARD,t.reward,0);
    }
    @Test public void terminalUpdateDoesNotBootstrap() {
        MazeAgent agent=new MazeAgent(1,.9,0,7); agent.update(0,0,MazeEnvironment.RIGHT,10,0,1,true,Arrays.asList(MazeEnvironment.RIGHT));
        assertEquals(10,agent.value(0,0,MazeEnvironment.RIGHT),0.0001);
    }
    @Test public void learningFindsGoalWithoutCycle() {
        Trainer trainer=new Trainer(new MazeEnvironment(),new MazeAgent(.25,.92,.20,11)); Trainer.Result result=trainer.train(6000);
        assertTrue(result.goals>0); assertFalse(result.path.isEmpty()); assertEquals(24,(int)result.path.get(result.path.size()-1));
    }
}
