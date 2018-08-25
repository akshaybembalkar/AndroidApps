package com.akshayb.runnlive_myfirstandroidgame.GameEngine;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.akshayb.runnlive_myfirstandroidgame.Constants;

import java.util.ArrayList;

public class ObstacleManager {
    // higher index = lover on screen = higher Y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;
    private long startTime;
    private long initTime;
    private int score = 0;

    public ObstacleManager (int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        startTime = initTime = System.currentTimeMillis();
        obstacles = new ArrayList<>();

        populateObstacles();

    }

    public boolean playerCollide (RectPlayer player) {
        for (Obstacle obs: obstacles) {
            if (obs.playerCollide(player)) {
                return true;
            }
        }
        return false;
    }

    private void populateObstacles() {
        int currY = -5 * Constants.SCREEN_HEIGHT/4;
        // while (obstacles.get(obstacles.size() - 1).getRectangle().bottom < 0) {
        while (currY < 0) {
            int xStart = (int)(Math.random() * Constants.SCREEN_WIDTH - playerGap);
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap ));
            currY += obstacleHeight + obstacleGap;
        }
    }

    public void update() {
        int elapsedTime = (int)(System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        // Keep increasing the speed every 2 secs with Math.sqrt
        float speed = (float) (Math.sqrt(1 + (startTime - initTime)/5000.0)) * Constants.SCREEN_HEIGHT/10000.0f; // divide by 10000 milli second == second
        for (Obstacle obs: obstacles) {
            obs.incrementY(speed * elapsedTime);
        }
        if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int)((Math.random()) * Constants.SCREEN_WIDTH - playerGap);
            System.out.println("Start x  " + xStart);
            /** If xStart if 0, left obstacle will be hidden and there won't be enough space for player */
            if (xStart < 0.5) {
                // System.out.println("***Returning****");
                return;
            }
            int startY = obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap;
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, startY ,playerGap));
            obstacles.remove(obstacles.size() - 1);
            score ++;
        }
    }

    public void draw (Canvas canvas) {
        for (Obstacle obs: obstacles) {
            obs.draw(canvas);
        }
        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText("Score: " + score, 50, 50 + paint.descent() - paint.ascent(), paint);
    }
}
