package com.allteach.maze.teacher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import java.util.Collections;
import java.util.List;

public final class MazeView extends View {
    private final MazeEnvironment maze=new MazeEnvironment(); private final Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private List<Integer> path=Collections.emptyList();
    public MazeView(Context context) { super(context); setMinimumHeight(540); }
    public void setPath(List<Integer> path) { this.path=path; invalidate(); }
    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas); float cell=Math.min(getWidth()/5f,getHeight()/5f), ox=(getWidth()-cell*5)/2f;
        for(int r=0;r<5;r++) for(int c=0;c<5;c++) {
            paint.setColor(maze.isWall(r,c)?Color.DKGRAY:maze.isTrap(r,c)?0xffef9a9a:maze.isGoal(r,c)?0xffa5d6a7:Color.WHITE);
            canvas.drawRect(ox+c*cell,r*cell,ox+(c+1)*cell,(r+1)*cell,paint);
            paint.setStyle(Paint.Style.STROKE);paint.setStrokeWidth(2);paint.setColor(Color.GRAY);canvas.drawRect(ox+c*cell,r*cell,ox+(c+1)*cell,(r+1)*cell,paint);paint.setStyle(Paint.Style.FILL);
        }
        paint.setColor(0xff1565c0); for(int state:path) { int r=state/5,c=state%5; canvas.drawCircle(ox+(c+.5f)*cell,(r+.5f)*cell,cell*.18f,paint); }
        paint.setTextSize(cell*.22f);paint.setColor(Color.BLACK);canvas.drawText("S",ox+cell*.1f,cell*.3f,paint);canvas.drawText("G",ox+4.1f*cell,4.3f*cell,paint);
    }
}
