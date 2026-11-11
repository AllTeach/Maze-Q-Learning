package com.allteach.maze.student;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Starter screen. The random demo proves that the maze visualization works
 * before learning is implemented. Students later replace that demonstration
 * with a Trainer running on a background Thread.
 */
public final class MainActivity extends AppCompatActivity {
    private MazeView mazeView; private TextView status; private final MazeEnvironment maze=new MazeEnvironment(); private final Random random=new Random(9);
    @Override public void onCreate(Bundle b){super.onCreate(b);LinearLayout root=new LinearLayout(this);root.setPadding(24,24,24,24);root.setOrientation(LinearLayout.VERTICAL);status=new TextView(this);mazeView=new MazeView(this);Button train=new Button(this);train.setText("הריצו בסיס אקראי");train.setOnClickListener(v->randomDemo());Button reset=new Button(this);reset.setText("איפוס");reset.setOnClickListener(v->{mazeView.setPath(new ArrayList<Integer>());status.setText("מוכנים למשימות TODO ב-MazeAgent.");});root.addView(status);root.addView(mazeView,new LinearLayout.LayoutParams(-1,0,1));root.addView(train);root.addView(reset);setContentView(root);status.setText("גרסת תלמיד: המבוך עובד; המדיניות כרגע אקראית עד למימוש TODO.");}
    private void randomDemo(){List<Integer> path=new ArrayList<>();int r=0,c=0;for(int i=0;i<30&&!maze.isTerminal(r,c);i++){path.add(r*5+c);List<Integer> legal=maze.legalActions(r,c);MazeEnvironment.Transition t=maze.step(r,c,legal.get(random.nextInt(legal.size())));r=t.row;c=t.col;}path.add(r*5+c);mazeView.setPath(path);status.setText("זהו בסיס אקראי, לא Q-learning. השלימו TODO ואז החליפו את ההדגמה באימון.");}
}
