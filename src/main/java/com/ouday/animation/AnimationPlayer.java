package com.ouday.animation;

import com.ouday.animation.mazegeneration.MazeGenerationAlgorithm;
import com.ouday.animation.pathfinding.PathfindingAlgorithm;
import com.ouday.controller.VisualizerController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;

public class AnimationPlayer {

    private final Timeline timeline;
    private VisualizerController visualizerController;
    private final FontIcon playIcon;
    private final FontIcon stopIcon;
    private final FontIcon oneStepIcon;
    private final FontIcon repeatIcon;
    private final FontIcon homeIcon;
    private final FontIcon drawIcon;
    private final FontIcon eraserIcon;
    private final FontIcon startIcon;
    private final FontIcon endIcon;
    private final FontIcon weightIcon;
    private final PathfindingAlgorithm pathfindingAlgorithm;
    private final MazeGenerationAlgorithm mazeGenerationAlgorithm;
    private boolean isMazeGenerationStarted;
    private boolean isPathfindingStarted;
    private boolean isMazeGenerationCompleted;
    private boolean isPathfindingCompleted;
    private boolean isDrawCompleted;


    public AnimationPlayer(VisualizerController visualizerController, MazeGenerationAlgorithm mazeGenerationAlgorithm, PathfindingAlgorithm pathfindingAlgorithm, List<FontIcon> fontIconList, double speed) {
        this.visualizerController = visualizerController;
        this.isMazeGenerationStarted = false;
        this.isPathfindingStarted = false;
        this.isMazeGenerationCompleted = false;
        this.isPathfindingCompleted = false;
        this.isDrawCompleted = false;
        this.homeIcon = fontIconList.get(0);
        this.repeatIcon = fontIconList.get(1);
        this.playIcon = fontIconList.get(2);
        this.stopIcon = fontIconList.get(3);
        this.oneStepIcon = fontIconList.get(4);
        this.drawIcon = fontIconList.get(5);
        this.eraserIcon = fontIconList.get(6);
        this.startIcon = fontIconList.get(7);
        this.endIcon = fontIconList.get(8);
        this.weightIcon = fontIconList.get(9);
        this.mazeGenerationAlgorithm = mazeGenerationAlgorithm;
        this.pathfindingAlgorithm = pathfindingAlgorithm;
        this.timeline = new Timeline(new KeyFrame(Duration.millis(speed), this::oneStep));
        this.timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void oneStep(ActionEvent event) {
        try {
            oneStep();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void oneStep() throws IOException {
        if (!isMazeGenerationStarted)
            handleMazeGenerationStart();
        else if (!isMazeGenerationCompleted)
            handleMazeGeneration();
        else if (!isPathfindingCompleted)
            handlePathfinding();
    }

    private void handlePathfinding() throws IOException {
        if (!isDrawCompleted) {
            drawIcon.setIconColor(Color.valueOf("#c1c0b9"));
            drawIcon.setDisable(true);
            eraserIcon.setIconColor(Color.valueOf("#c1c0b9"));
            eraserIcon.setDisable(true);
            startIcon.setIconColor(Color.valueOf("#c1c0b9"));
            startIcon.setDisable(true);
            endIcon.setIconColor(Color.valueOf("#c1c0b9"));
            endIcon.setDisable(true);
            weightIcon.setIconColor(Color.valueOf("#c1c0b9"));
            weightIcon.setDisable(true);
            setDrawCompleted(true);
            pathfindingAlgorithm.process();
        } else if (!pathfindingAlgorithm.isCompleted())
            pathfindingAlgorithm.process();
        else {
            playIcon.setIconColor(Color.valueOf("#c1c0b9"));
            playIcon.setDisable(true);
            playIcon.setVisible(true);
            stopIcon.setVisible(false);
            oneStepIcon.setIconColor(Color.valueOf("#c1c0b9"));
            oneStepIcon.setDisable(true);
            homeIcon.setIconColor(Color.valueOf("#f7e9e3"));
            homeIcon.setDisable(false);
            repeatIcon.setIconColor(Color.valueOf("#f7e9e3"));
            repeatIcon.setDisable(false);
            setPathfindingCompleted(true);
            this.timeline.stop();
        }

    }

    private void handleMazeGeneration() throws IOException {
        if (!mazeGenerationAlgorithm.isCompleted())
            mazeGenerationAlgorithm.process();
        else {
            playIcon.setIconColor(Color.valueOf("#f7e9e3"));
            playIcon.setDisable(false);
            playIcon.setVisible(true);
            stopIcon.setVisible(false);
            oneStepIcon.setIconColor(Color.valueOf("#f7e9e3"));
            oneStepIcon.setDisable(false);
            homeIcon.setIconColor(Color.valueOf("#f7e9e3"));
            homeIcon.setDisable(false);
            repeatIcon.setIconColor(Color.valueOf("#f7e9e3"));
            repeatIcon.setDisable(false);
            drawIcon.setIconColor(Color.valueOf("#f7e9e3"));
            drawIcon.setDisable(false);
            eraserIcon.setIconColor(Color.valueOf("#f7e9e3"));
            eraserIcon.setDisable(false);
            startIcon.setIconColor(Color.valueOf("#f7e9e3"));
            startIcon.setDisable(false);
            endIcon.setIconColor(Color.valueOf("#f7e9e3"));
            endIcon.setDisable(false);
            String pathFindingAglorithmName = pathfindingAlgorithm.getClass().getSimpleName();
            if (!(pathFindingAglorithmName.equals("BreadthFirstSearch") || pathFindingAglorithmName.equals("DepthFirstSearch"))) {
                weightIcon.setIconColor(Color.valueOf("#f7e9e3"));
                weightIcon.setDisable(false);
            }
            setMazeGenerationCompleted(true);
            pathfindingAlgorithm.init();
            setPathfindingStarted(true);
            this.timeline.stop();
        }
    }

    private void handleMazeGenerationStart() throws IOException {
        drawIcon.setIconColor(Color.valueOf("#c1c0b9"));
        drawIcon.setDisable(true);
        eraserIcon.setIconColor(Color.valueOf("#c1c0b9"));
        eraserIcon.setDisable(true);
        startIcon.setIconColor(Color.valueOf("#c1c0b9"));
        startIcon.setDisable(true);
        endIcon.setIconColor(Color.valueOf("#c1c0b9"));
        endIcon.setDisable(true);
        weightIcon.setIconColor(Color.valueOf("#c1c0b9"));
        weightIcon.setDisable(true);
        mazeGenerationAlgorithm.init();
        setMazeGenerationStarted(true);
    }

    public void stopAnimation() {
        stopIcon.setVisible(false);
        playIcon.setVisible(true);
        homeIcon.setIconColor(Color.valueOf("#f7e9e3"));
        homeIcon.setDisable(false);
        repeatIcon.setIconColor(Color.valueOf("#f7e9e3"));
        repeatIcon.setDisable(false);
        oneStepIcon.setIconColor(Color.valueOf("#f7e9e3"));
        oneStepIcon.setDisable(false);
        timeline.stop();
    }

    public void startAnimation() {
        stopIcon.setVisible(true);
        playIcon.setVisible(false);
        homeIcon.setIconColor(Color.valueOf("#c1c0b9"));
        homeIcon.setDisable(true);
        repeatIcon.setIconColor(Color.valueOf("#c1c0b9"));
        repeatIcon.setDisable(true);
        oneStepIcon.setIconColor(Color.valueOf("#c1c0b9"));
        oneStepIcon.setDisable(true);
        timeline.play();
    }

    public void setMazeGenerationStarted(boolean mazeGenerationStarted) {
        isMazeGenerationStarted = mazeGenerationStarted;
    }

    public void setPathfindingStarted(boolean pathfindingStarted) {
        isPathfindingStarted = pathfindingStarted;
    }

    public void setMazeGenerationCompleted(boolean mazeGenerationCompleted) {
        isMazeGenerationCompleted = mazeGenerationCompleted;
    }

    public void setPathfindingCompleted(boolean pathfindingCompleted) {
        isPathfindingCompleted = pathfindingCompleted;
    }

    public boolean isDrawCompleted() {
        return isDrawCompleted;
    }

    public void setDrawCompleted(boolean drawCompleted) {
        isDrawCompleted = drawCompleted;
    }

}