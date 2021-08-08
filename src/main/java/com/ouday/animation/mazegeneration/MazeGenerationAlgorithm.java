package com.ouday.animation.mazegeneration;

import java.io.IOException;

public interface MazeGenerationAlgorithm {

    void init() throws IOException;

    void process() throws IOException;

    boolean isCompleted();
}
