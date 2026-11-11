package com.allteach.maze.teacher;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public final class MainActivity extends AppCompatActivity {
    private MazeView mazeView; private TextView status; private Trainer trainer;
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(24,24,24,24);
        status=new TextView(this); status.setText("לחצו אימון: 5,000 אפיזודות ברקע. עיגולים כחולים = המסלול החמדני.");
        mazeView=new MazeView(this); root.addView(status); root.addView(mazeView,new LinearLayout.LayoutParams(-1,0,1));
        LinearLayout buttons=new LinearLayout(this); buttons.setGravity(Gravity.CENTER);
        buttons.addView(button("אימון",v->train())); buttons.addView(button("הצג מסלול",v->showPath())); buttons.addView(button("איפוס",v->reset()));
        root.addView(buttons); setContentView(root); reset();
    }
    private Button button(String text, android.view.View.OnClickListener click) { Button b=new Button(this);b.setText(text);b.setOnClickListener(click);return b; }
    private void reset() { trainer=new Trainer(new MazeEnvironment(),new MazeAgent(.25,.92,.20,20261111L)); mazeView.setPath(java.util.Collections.<Integer>emptyList()); status.setText("אופס: טבלת Q אופסה. לפני אימון אין מסלול מבוסס ידע."); }
    private void train() {
        status.setText("מתאמנים ברקע…"); new Thread(() -> {
            Trainer.Result result=trainer.train(5000);
            runOnUiThread(() -> { mazeView.setPath(result.path); status.setText(result.path.isEmpty() ? "לא נמצא מסלול יציב: זהו מחזור או תקרת צעדים. בדקו תגמולים/ε." : "הסתיימו 5,000 אפיזודות; הגעה למטרה: "+result.goals+"."); });
        }).start();
    }
    private void showPath() { java.util.List<Integer> path=trainer.greedyPath(); mazeView.setPath(path); status.setText(path.isEmpty() ? "המדיניות עדיין יוצרת מחזור או לא הגיעה למטרה." : "מסלול חמדני מוצג בכחול."); }
}
