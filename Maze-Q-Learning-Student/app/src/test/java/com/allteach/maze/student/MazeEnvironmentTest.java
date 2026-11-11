package com.allteach.maze.student;

import static org.junit.Assert.*;
import org.junit.Test;
public class MazeEnvironmentTest {
 @Test public void mazeHasLegalStartAndTerminalGoal(){MazeEnvironment m=new MazeEnvironment();assertFalse(m.legalActions(0,0).isEmpty());assertTrue(m.isTerminal(4,4));}
 @Test public void starterAgentCompilesAndReturnsLegalAction(){MazeEnvironment m=new MazeEnvironment();MazeAgent a=new MazeAgent();int action=a.chooseAction(0,0,m.legalActions(0,0));assertTrue(m.legalActions(0,0).contains(action));}
}
