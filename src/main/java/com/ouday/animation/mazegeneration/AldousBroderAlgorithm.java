package com.ouday.animation.mazegeneration;

import com.ouday.data.Board;
import com.ouday.data.Cell;
import com.ouday.data.CellDetails;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class AldousBroderAlgorithm implements MazeGenerationAlgorithm {

    private final Board board;
    private final CellDetails cellDetails;
    private final GraphicsContext graphicsContext;
    private final List<Cell> cellList;
    private Cell currentCell;
    private final Random random = new Random();
    private final int sizeOfTheCell;
    private int currentCellIndex;


    public AldousBroderAlgorithm(Board board) {
        this.board = board;
        this.cellDetails = board.getCellDetails();
        this.graphicsContext = board.getGraphicsContext();
        this.sizeOfTheCell = board.getSizeOfTheCell();
        this.cellList = board.getCellList();
    }

    @Override
    public void init() throws IOException {
        currentCellIndex = random.nextInt(cellList.size());
        currentCell = cellList.get(currentCellIndex);
        currentCell.setVisited(true);
        drawBoard();
    }

    @Override
    public void process() throws IOException {
        List<Cell> neighbourList = cellDetails.getUnSearchingNeighbourList(currentCell);
        Cell randomCellNeighbourIndex = neighbourList.get(random.nextInt(neighbourList.size()));
        int neighbourIndex = cellDetails.getCellIndex(randomCellNeighbourIndex.getX(), randomCellNeighbourIndex.getY());
        Cell neighbourCell = cellList.get(neighbourIndex);
        if (!neighbourCell.isVisited()) {
            neighbourCell.setVisited(true);
            cellDetails.removeWalls(currentCellIndex, neighbourIndex);
        }
        currentCellIndex = neighbourIndex;
        currentCell = neighbourCell;
        drawBoard();
    }

    public void drawBoard() {
        graphicsContext.setFill(Color.valueOf("#393E46"));
        graphicsContext.fillRect(0, 0, board.getWidth(), board.getHeight());
        for (Cell cell : cellList) {
            int x = cell.getX();
            int y = cell.getY();
            boolean isVisited = cell.isVisited();
            if (isVisited) {
                graphicsContext.setFill(Color.valueOf("#1E2022"));
                graphicsContext.fillRect(x, y, sizeOfTheCell, sizeOfTheCell);
            }
            this.graphicsContext.setStroke(Color.valueOf("#F0FFF3"));
            if (cell.getIsVisibleWallAtIndex(0) && isVisited)
                this.graphicsContext.strokeLine(x, y, x + sizeOfTheCell, y);
            if (cell.getIsVisibleWallAtIndex(1) && isVisited)
                this.graphicsContext.strokeLine(x + sizeOfTheCell, y, x + sizeOfTheCell, y + sizeOfTheCell);
            if (cell.getIsVisibleWallAtIndex(2) && isVisited)
                this.graphicsContext.strokeLine(x + sizeOfTheCell, y + sizeOfTheCell, x, y + sizeOfTheCell);
            if (cell.getIsVisibleWallAtIndex(3) && isVisited)
                this.graphicsContext.strokeLine(x, y, x, y + sizeOfTheCell);
        }
        graphicsContext.setFill(Color.valueOf("#e23e57"));
        graphicsContext.fillRect(currentCell.getX(), currentCell.getY(), sizeOfTheCell, sizeOfTheCell);
    }

    @Override
    public boolean isCompleted() {
        return cellDetails.isAllCellsVisited();
    }

}