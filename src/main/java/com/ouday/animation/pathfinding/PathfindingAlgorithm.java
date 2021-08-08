package com.ouday.animation.pathfinding;

import java.io.IOException;

public interface PathfindingAlgorithm {

    void init() throws IOException;

    void process() throws IOException;

    void drawBoard();

    boolean isCompleted();
}