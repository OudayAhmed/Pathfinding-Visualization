package com.ouday.animation.mazegeneration;

import com.ouday.data.Board;
import com.ouday.data.Cell;
import com.ouday.data.CellDetails;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Random;
import java.util.Stack;

public class RecursiveBacktracking implements MazeGenerationAlgorithm {

    private final Board board;
    private final CellDetails cellDetails;
    private final GraphicsContext graphicsContext;
    private final List<Cell> cellList;
    private final Stack<Cell> cellStack = new Stack<>();
    private final Random random = new Random();
    private final int sizeOfTheCell;
    private int nextCellIndex;
    private boolean isStart;


    public RecursiveBacktracking(Board board) {
        this.board = board;
        this.cellDetails = board.getCellDetails();
        this.graphicsContext = board.getGraphicsContext();
        this.sizeOfTheCell = board.getSizeOfTheCell();
        this.cellList = board.getCellList();
        this.isStart = false;
    }

    @Override
    public void init() {
        drawBoard();
        int startCellIndex = random.nextInt(cellList.size());
        Cell cell = cellList.get(startCellIndex);
        cell.setVisited(true);
        cellStack.add(cell);
        graphicsContext.setFill(Color.valueOf("#e23e57"));
        graphicsContext.fillRect(cell.getX(), cell.getY(), sizeOfTheCell, sizeOfTheCell);
        nextCellIndex = cellDetails.getRandomUnVisitedNeighbourCell(cellStack, cell);
    }

    @Override
    public void process() {
        if (isStart) {
            if (!cellStack.empty()) {
                if (nextCellIndex == -1) {
                    Cell cell = cellStack.pop();
                    nextCellIndex = cellDetails.getRandomUnVisitedNeighbourCell(cellStack, cell);
                } else {
                    nextCellIndex = cellDetails.getRandomUnVisitedNeighbourCell(cellStack, cellList.get(nextCellIndex));
                    if (nextCellIndex == -1) {
                        Cell cell = cellStack.pop();
                        nextCellIndex = cellDetails.getRandomUnVisitedNeighbourCell(cellStack, cell);
                    }
                }
                drawBoard();
            }
        } else {
            isStart = true;
            drawBoard();
        }
    }

    public void drawBoard() {
        graphicsContext.setFill(Color.valueOf("#393E46"));
        graphicsContext.fillRect(0, 0, board.getWidth(), board.getHeight());
        for (Cell cell : cellList) {
            int x = cell.getX();
            int y = cell.getY();
            boolean isVisited = cell.isVisited();
            if (isVisited && cellStack.contains(cell)) {
                graphicsContext.setFill(Color.valueOf("#e23e57"));
                graphicsContext.fillRect(x, y, sizeOfTheCell, sizeOfTheCell);
            } else if (isVisited && !cellStack.contains(cell)) {
                graphicsContext.setFill(Color.valueOf("#1E2022"));
                graphicsContext.fillRect(x, y, sizeOfTheCell, sizeOfTheCell);
            } else if (!isVisited) {
                graphicsContext.setFill(Color.valueOf("#393E46"));
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
    }

    @Override
    public boolean isCompleted() {
        return cellStack.empty();
    }

}