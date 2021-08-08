package com.ouday.controller;

import com.ouday.animation.AnimationPlayer;
import com.ouday.animation.mazegeneration.MazeGenerationAlgorithm;
import com.ouday.animation.pathfinding.PathfindingAlgorithm;
import com.ouday.data.Board;
import com.ouday.data.VisualizerData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VisualizerController {

    private List<FontIcon> fontIconList;
    private String algorithmName;
    private int sizeOfTheCell;
    private String mazeGenerationAlgorithmString;
    private String speed;
    private VisualizerData visualizerData;
    private AnimationPlayer animationPlayer;
    private PathfindingAlgorithm pathfindingAlgorithm;
    private MazeGenerationAlgorithm mazeGenerationAlgorithm;
    private Board board;


    @FXML
    private FontIcon closeIcon;

    @FXML
    private FontIcon minimizeIcon;

    @FXML
    private FontIcon playIcon;

    @FXML
    private FontIcon stopIcon;

    @FXML
    private FontIcon oneStepIcon;

    @FXML
    private FontIcon repeatIcon;

    @FXML
    private FontIcon homeIcon;

    @FXML
    private FontIcon drawIcon;

    @FXML
    private FontIcon eraserIcon;

    @FXML
    private FontIcon startIcon;

    @FXML
    private FontIcon endIcon;

    @FXML
    private FontIcon weightIcon;

    @FXML
    private Label algorithmNameLabel;

    @FXML
    private Label mazeGenerationAlgorithmLabel;

    @FXML
    private Label sizeOfTheCellLabel;

    @FXML
    private Label speedLabel;

    @FXML
    private Canvas canvas;

    @FXML
    void switchToHome(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/ouday/view/home.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void closeStage(MouseEvent event) {
        ((Stage) closeIcon.getScene().getWindow()).close();
    }

    @FXML
    void minimizeStage(MouseEvent event) {
        ((Stage) minimizeIcon.getScene().getWindow()).setIconified(true);
    }

    @FXML
    void start() throws IOException {
        animationPlayer.startAnimation();
    }

    @FXML
    public void oneStep(MouseEvent event) throws IOException {
        animationPlayer.oneStep();
    }

    @FXML
    void stop() {
        animationPlayer.stopAnimation();
    }

    @FXML
    void draw() {
        this.canvas.setOnMouseDragged(this::drawWalls);
    }

    private void drawWalls(MouseEvent mouseEvent) {
        if (!animationPlayer.isDrawCompleted()) {
            board.setWallCell((int) mouseEvent.getX(), (int) mouseEvent.getY());
            pathfindingAlgorithm.drawBoard();
        }
    }

    @FXML
    void eraser() {
        this.canvas.setOnMouseDragged(this::eraserWallsAndWeight);
    }

    private void eraserWallsAndWeight(MouseEvent mouseEvent) {
        if (!animationPlayer.isDrawCompleted()) {
            board.removeWallAndWeightCell((int) mouseEvent.getX(), (int) mouseEvent.getY());
            pathfindingAlgorithm.drawBoard();
        }
    }

    @FXML
    void drawStartCell() {
        this.canvas.setOnMouseDragged(this::drawNewStartCell);
    }

    private void drawNewStartCell(MouseEvent mouseEvent) {
        if (!animationPlayer.isDrawCompleted()) {
            board.setStartCell((int) mouseEvent.getX(), (int) mouseEvent.getY());
            try {
                pathfindingAlgorithm.init();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void drawEndCell() {
        this.canvas.setOnMouseDragged(this::drawNewEndCell);
    }

    private void drawNewEndCell(MouseEvent mouseEvent) {
        if (!animationPlayer.isDrawCompleted()) {
            board.setEndCell((int) mouseEvent.getX(), (int) mouseEvent.getY());
            pathfindingAlgorithm.drawBoard();
        }
    }

    @FXML
    void drawWeight() {
        this.canvas.setOnMouseDragged(this::drawWeightedCell);
    }

    private void drawWeightedCell(MouseEvent mouseEvent) {
        if (!animationPlayer.isDrawCompleted()) {
            board.setCellWeight((int) mouseEvent.getX(), (int) mouseEvent.getY());
            pathfindingAlgorithm.drawBoard();
        }
    }

    @FXML
    void repeat(MouseEvent event) throws IOException {
        this.visualizerData = new VisualizerData(this.algorithmName, this.sizeOfTheCell, this.mazeGenerationAlgorithmString, this.speed, this.canvas, this.fontIconList);
        pathfindingAlgorithm = visualizerData.getPathfindingAlgorithm();
        mazeGenerationAlgorithm = visualizerData.getMazeGenerationAlgorithm();
        board = visualizerData.getBoard();
        animationPlayer = new AnimationPlayer(this, mazeGenerationAlgorithm, pathfindingAlgorithm, fontIconList, visualizerData.getSpeed());
        if (mazeGenerationAlgorithmString.equals("None"))
            animationPlayer.oneStep();
        animationPlayer.oneStep();
        playIcon.setIconColor(Color.valueOf("#f7e9e3"));
        playIcon.setDisable(false);
        oneStepIcon.setIconColor(Color.valueOf("#f7e9e3"));
        oneStepIcon.setDisable(false);
    }

    public void setVisualizerDetails(String algorithmName, int sizeOfTheCell, String mazeGenerationAlgorithmString, String speed) throws IOException {
        this.algorithmName = algorithmName;
        this.sizeOfTheCell = sizeOfTheCell;
        this.mazeGenerationAlgorithmString = mazeGenerationAlgorithmString;
        this.speed = speed;
        algorithmNameLabel.setText("Algorithm's name: " + this.algorithmName);
        sizeOfTheCellLabel.setText("The size of the cell: " + this.sizeOfTheCell);
        mazeGenerationAlgorithmLabel.setText("Maze Generation Algorithm: " + this.mazeGenerationAlgorithmString);
        speedLabel.setText("The speed is: " + this.speed);
        this.fontIconList = new ArrayList<>();
        fontIconList.add(homeIcon);
        fontIconList.add(repeatIcon);
        fontIconList.add(playIcon);
        fontIconList.add(stopIcon);
        fontIconList.add(oneStepIcon);
        fontIconList.add(drawIcon);
        fontIconList.add(eraserIcon);
        fontIconList.add(startIcon);
        fontIconList.add(endIcon);
        fontIconList.add(weightIcon);
        this.visualizerData = new VisualizerData(this.algorithmName, this.sizeOfTheCell, this.mazeGenerationAlgorithmString, this.speed, this.canvas, this.fontIconList);
        pathfindingAlgorithm = visualizerData.getPathfindingAlgorithm();
        mazeGenerationAlgorithm = visualizerData.getMazeGenerationAlgorithm();
        board = visualizerData.getBoard();
        animationPlayer = new AnimationPlayer(this, mazeGenerationAlgorithm, pathfindingAlgorithm, fontIconList, visualizerData.getSpeed());
        if (mazeGenerationAlgorithmString.equals("None"))
            animationPlayer.oneStep();
        animationPlayer.oneStep();
    }

}