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
    private static final int RANDOM_DEMO_STEP_LIMIT = 30;

    private final MazeEnvironment maze = new MazeEnvironment();
    private final Random random = new Random(9);
    private MazeView mazeView;
    private TextView status;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setPadding(24, 24, 24, 24);
        root.setOrientation(LinearLayout.VERTICAL);

        status = new TextView(this);
        mazeView = new MazeView(this);

        Button randomDemoButton = new Button(this);
        randomDemoButton.setText("הדגמה אקראית (עדיין לא לומד)");
        randomDemoButton.setOnClickListener(view -> showRandomDemo());

        Button resetButton = new Button(this);
        resetButton.setText("איפוס תצוגה");
        resetButton.setOnClickListener(view -> resetDisplay());

        root.addView(status);
        root.addView(mazeView, new LinearLayout.LayoutParams(-1, 0, 1));
        root.addView(randomDemoButton);
        root.addView(resetButton);
        setContentView(root);

        status.setText("גרסת תלמיד: המבוך והתצוגה עובדים. "
                + "הלחצן מציג מסלול אקראי בלבד; הוא אינו מאמן ואינו משתפר. "
                + "אחרי השלמת TODOs מחליפים אותו באימון באמצעות Trainer.");
    }

    private void resetDisplay() {
        mazeView.setPath(new ArrayList<Integer>());
        status.setText("התצוגה אופסה. עדיין אין למידה עד שתממשו את MazeAgent ואת Trainer.");
    }

    private void showRandomDemo() {
        List<Integer> path = new ArrayList<>();
        int row = MazeEnvironment.START_ROW;
        int col = MazeEnvironment.START_COL;

        for (int step = 0; step < RANDOM_DEMO_STEP_LIMIT; step++) {
            path.add(encodeState(row, col));
            if (maze.isTerminal(row, col)) {
                break;
            }

            List<Integer> legalActions = maze.legalActions(row, col);
            int randomAction = legalActions.get(random.nextInt(legalActions.size()));
            MazeEnvironment.Transition transition = maze.step(row, col, randomAction);

            row = transition.row;
            col = transition.col;
        }

        path.add(encodeState(row, col));
        mazeView.setPath(path);
        status.setText("זו הדגמה אקראית בלבד: לחיצה נוספת עשויה לתת מסלול אחר. "
                + "הסוכן לא שומר Q-values, ולכן אינו מתקדם ואינו לומד.");
    }

    private int encodeState(int row, int col) {
        return row * MazeEnvironment.COLS + col;
    }
}
