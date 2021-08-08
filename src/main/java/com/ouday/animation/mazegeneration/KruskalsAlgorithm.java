package com.ouday.animation.mazegeneration;

import com.ouday.data.Board;
import com.ouday.data.Cell;
import com.ouday.data.CellDetails;
import com.ouday.data.UnionFind;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KruskalsAlgorithm implements MazeGenerationAlgorithm {

    private final Board board;
    private final CellDetails cellDetails;
    private final UnionFind unionFind;
    private final GraphicsContext graphicsContext;
    private final List<Cell> cellList;
    private final ArrayList<ArrayList<Integer>> edgeList;
    private final int sizeOfTheCell;
    private Cell fromCell;
    private Cell toCell;


    public KruskalsAlgorithm(Board board) {
        this.board = board;
        this.cellDetails = board.getCellDetails();
        this.edgeList = cellDetails.getAllEdges();
        this.graphicsContext = board.getGraphicsContext();
        this.sizeOfTheCell = board.getSizeOfTheCell();
        this.cellList = board.getCellList();
        this.unionFind = new UnionFind(cellList.size());
    }

    @Override
    public void init() throws IOException {
        Pair<Integer, Integer> randomEdge = cellDetails.getRandomEdge(edgeList);
        int fromCellIndex = randomEdge.getKey();
        fromCell = cellList.get(fromCellIndex);
        fromCell.setVisited(true);
        int toCellIndex = randomEdge.getValue();
        toCell = cellList.get(toCellIndex);
        cellList.get(toCellIndex).setVisited(true);
        unionFind.union(fromCellIndex, toCellIndex);
        cellDetails.removeWalls(fromCellIndex, toCellIndex);
        drawBoard();
    }

    @Override
    public void process() {
        Pair<Integer, Integer> randomEdge = cellDetails.getRandomEdge(edgeList);
        int fromCellIndex = randomEdge.getKey();
        fromCell = cellList.get(fromCellIndex);
        fromCell.setVisited(true);
        int toCellIndex = randomEdge.getValue();
        toCell = cellList.get(toCellIndex);
        cellList.get(toCellIndex).setVisited(true);
        if (!unionFind.isSameSet(fromCellIndex, toCellIndex)) {
            unionFind.union(fromCellIndex, toCellIndex);
            cellDetails.removeWalls(fromCellIndex, toCellIndex);
            drawBoard();
        } else
            process();
    }

    private void drawBoard() {
        graphicsContext.setFill(Color.valueOf("#393E46"));
        graphicsContext.fillRect(0, 0, board.getWidth(), board.getHeight());
        for (Cell cell : cellList) {
            if (cell.isVisited()) {
                graphicsContext.setFill(Color.valueOf("#1E2022"));
                graphicsContext.fillRect(cell.getX(), cell.getY(), sizeOfTheCell, sizeOfTheCell);
            }
            this.graphicsContext.setStroke(Color.valueOf("#F0FFF3"));
            if (cell.getIsVisibleWallAtIndex(0) && cell.isVisited())
                this.graphicsContext.strokeLine(cell.getX(), cell.getY(), cell.getX() + sizeOfTheCell, cell.getY());
            if (cell.getIsVisibleWallAtIndex(1) && cell.isVisited())
                this.graphicsContext.strokeLine(cell.getX() + sizeOfTheCell, cell.getY(), cell.getX() + sizeOfTheCell, cell.getY() + sizeOfTheCell);
            if (cell.getIsVisibleWallAtIndex(2) && cell.isVisited())
                this.graphicsContext.strokeLine(cell.getX() + sizeOfTheCell, cell.getY() + sizeOfTheCell, cell.getX(), cell.getY() + sizeOfTheCell);
            if (cell.getIsVisibleWallAtIndex(3) && cell.isVisited())
                this.graphicsContext.strokeLine(cell.getX(), cell.getY(), cell.getX(), cell.getY() + sizeOfTheCell);
        }
        graphicsContext.setFill(Color.valueOf("#FFD460"));
        graphicsContext.fillRect(fromCell.getX(), fromCell.getY(), sizeOfTheCell, sizeOfTheCell);

        graphicsContext.setFill(Color.valueOf("#e23e57"));
        graphicsContext.fillRect(toCell.getX(), toCell.getY(), sizeOfTheCell, sizeOfTheCell);
    }

    @Override
    public boolean isCompleted() {
        return unionFind.getNumberOfSets() == 1;
    }

}