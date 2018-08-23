package com.akshayb.runnlive_myfirstandroidgame.GameEngine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.akshayb.runnlive_myfirstandroidgame.MainThread;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread mainThread;
    private RectPlayer player;
    private Point playerPoint;
    private ObstacleManager obstacleManager;

    public GamePanel(Context context) {
        super(context);

        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(), this);
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255, 0, 0 ));
        playerPoint = new Point(150, 150);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mainThread = new MainThread(getHolder(), this);
        mainThread.setRunning(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (true) {
            try {
                mainThread.setRunning(false);
                mainThread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int)event.getX(), (int)event.getY());
        }
        return true;
        // return super.onTouchEvent(event);
    }

    public void update() {
        player.update(playerPoint);
        obstacleManager.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        obstacleManager.draw(canvas);
    }
}
