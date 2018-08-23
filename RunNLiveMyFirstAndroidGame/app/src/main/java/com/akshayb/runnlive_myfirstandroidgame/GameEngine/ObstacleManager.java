package com.akshayb.runnlive_myfirstandroidgame.GameEngine;

import android.graphics.Canvas;

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

    public ObstacleManager (int playerGap, int obstacleGap, int obstacleHeight, int color) {
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;
        startTime = System.currentTimeMillis();
        obstacles = new ArrayList<>();

        populateObstacles();

    }

    private void populateObstacles() {
        int currY = -5* Constants.SCREEN_HEIGHT/4;
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
        float speed = Constants.SCREEN_HEIGHT/10000.0f; // divide by milli second == second
        for (Obstacle obs: obstacles) {
            obs.incrementY(speed * elapsedTime);
        }
        if (obstacles.get(obstacles.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int)(Math.random() * Constants.SCREEN_WIDTH - playerGap);
            int startY = obstacles.get(0).getRectangle().top - obstacleHeight - obstacleGap;
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart, startY ,playerGap));
            obstacles.remove(obstacles.size() - 1);
        }
    }

    public void draw (Canvas canvas) {
        for (Obstacle obs: obstacles) {
            obs.draw(canvas);
        }
    }
}
