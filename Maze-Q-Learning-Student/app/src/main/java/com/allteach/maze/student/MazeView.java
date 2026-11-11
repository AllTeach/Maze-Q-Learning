package com.allteach.maze.student;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import java.util.Collections;
import java.util.List;

public final class MazeView extends View {
    private final MazeEnvironment maze=new MazeEnvironment(); private final Paint p=new Paint(Paint.ANTI_ALIAS_FLAG); private List<Integer> path=Collections.emptyList();
    public MazeView(Context c){super(c);setMinimumHeight(540);}
    public void setPath(List<Integer> value){path=value;invalidate();}
    @Override protected void onDraw(Canvas canvas){float s=Math.min(getWidth()/5f,getHeight()/5f),x=(getWidth()-s*5)/2f;for(int r=0;r<5;r++)for(int c=0;c<5;c++){p.setColor(maze.isWall(r,c)?Color.DKGRAY:maze.isTrap(r,c)?0xffef9a9a:maze.isGoal(r,c)?0xffa5d6a7:Color.WHITE);canvas.drawRect(x+c*s,r*s,x+(c+1)*s,(r+1)*s,p);p.setStyle(Paint.Style.STROKE);p.setColor(Color.GRAY);canvas.drawRect(x+c*s,r*s,x+(c+1)*s,(r+1)*s,p);p.setStyle(Paint.Style.FILL);}p.setColor(0xff1565c0);for(int state:path)canvas.drawCircle(x+(state%5+.5f)*s,(state/5+.5f)*s,s*.18f,p);p.setColor(Color.BLACK);p.setTextSize(s*.22f);canvas.drawText("S",x+s*.1f,s*.3f,p);canvas.drawText("G",x+4.1f*s,4.3f*s,p);}
}
