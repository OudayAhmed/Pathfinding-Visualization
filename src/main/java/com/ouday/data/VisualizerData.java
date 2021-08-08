package com.ouday.data;

import com.ouday.animation.mazegeneration.*;
import com.ouday.animation.pathfinding.BreadthFirstSearch;
import com.ouday.animation.pathfinding.DepthFirstSearch;
import com.ouday.animation.pathfinding.DijkstrasAlgorithm;
import com.ouday.animation.pathfinding.PathfindingAlgorithm;
import javafx.scene.canvas.Canvas;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class VisualizerData {

    private final String algorithmName;
    private final String mazeGenerationAlgorithm;
    private final String speed;
    private final Board board;


    public VisualizerData(String algorithmName, int sizeOfTheCell, String mazeGenerationAlgorithm, String speed, Canvas canvas, List<FontIcon> fontIconList) {
        this.algorithmName = algorithmName;
        this.mazeGenerationAlgorithm = mazeGenerationAlgorithm;
        this.speed = speed;
        this.board = new Board(canvas, sizeOfTheCell);
    }

    public PathfindingAlgorithm getPathfindingAlgorithm() {
        switch (algorithmName) {
            case "Breadth-first Search":
                return new BreadthFirstSearch(board);
            case "Depth-first Search":
                return new DepthFirstSearch(board);
            case "Dijkstra's Algorithm":
                return new DijkstrasAlgorithm(board);
        }
        return null;
    }

    public MazeGenerationAlgorithm getMazeGenerationAlgorithm() {
        switch (mazeGenerationAlgorithm) {
            case "Recursive Backtracking":
                return new RecursiveBacktracking(board);
            case "None":
                return new EmptyGrid(board);
            case "Prim's Algorithm":
                return new PrimsAlgorithm(board);
            case "Kruskal's Algorithm":
                return new KruskalsAlgorithm(board);
            case "Hunt-and-Kill algorithm":
                return new HuntAndKillAlgorithm(board);
            case "Binary Tree algorithm":
                return new BinaryTreeAlgorithm(board);
            case "Sidewinder algorithm":
                return new SidewinderAlgorithm(board);
            case "Aldous-Broder algorithm":
                return new AldousBroderAlgorithm(board);
        }
        return null;
    }

    public double getSpeed() {
        switch (speed) {
            case "10 ms":
                return 10;
            case "100 ms":
                return 100;
            case "1 s":
                return 1000;
        }
        return Double.parseDouble(null);
    }

    public Board getBoard() {
        return board;
    }

}