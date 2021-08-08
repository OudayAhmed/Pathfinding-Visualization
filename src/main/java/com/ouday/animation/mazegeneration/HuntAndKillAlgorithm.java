package com.ouday.animation.mazegeneration;

import com.ouday.data.Board;
import com.ouday.data.Cell;
import com.ouday.data.CellDetails;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class HuntAndKillAlgorithm implements MazeGenerationAlgorithm {

    private final Board board;
    private final CellDetails cellDetails;
    private final GraphicsContext graphicsContext;
    private final List<Cell> cellList;
    private Cell startCell;
    private final Random random = new Random();
    private final int sizeOfTheCell;
    private int scanAtRow;
    private int finishScanAtRow;
    private int startCellIndex;
    private boolean huntMode;
    private boolean isFinish;


    public HuntAndKillAlgorithm(Board board) {
        this.board = board;
        this.cellDetails = board.getCellDetails();
        this.graphicsContext = board.getGraphicsContext();
        this.sizeOfTheCell = board.getSizeOfTheCell();
        this.cellList = board.getCellList();
        this.scanAtRow = 0;
        this.finishScanAtRow = 0;
        this.huntMode = false;
        this.isFinish = false;
    }

    @Override
    public void init() throws IOException {
        startCellIndex = random.nextInt(cellList.size());
        startCell = cellList.get(startCellIndex);
        startCell.setVisited(true);
        drawBoard();
    }

    @Override
    public void process() throws IOException {
        List<Cell> unVisitedNeighbourList = cellDetails.getUnVisitedNeighbourList(startCell);
        if (cellDetails.isAllCellsVisited()) {
            if (scanAtRow < cellList.size() / (board.getWidth() / sizeOfTheCell) - 1) {
                huntMode = true;
                scanAtRow++;
                if (scanAtRow == cellList.size() / (board.getWidth() / sizeOfTheCell) - 1)
                    isFinish = true;
                drawBoard();
            }
        } else if (unVisitedNeighbourList.size() == 0) {
            huntMode = true;
            drawBoard();
            boolean stop = false;
            int count = 0;
            for (int i = (board.getWidth() / sizeOfTheCell) * scanAtRow; i < ((board.getWidth() / sizeOfTheCell) * scanAtRow) + board.getWidth() / sizeOfTheCell && !stop; i++) {
                if (!cellList.get(i).isVisited()) {
                    List<Cell> visitedNeighbourList = cellDetails.getVisitedNeighbourList(cellList.get(i));
                    if (visitedNeighbourList.size() > 0) {
                        startCell = visitedNeighbourList.get(0);
                        startCellIndex = cellDetails.getCellIndex(startCell.getX(), startCell.getY());
                        stop = true;
                    }
                } else
                    count++;
            }
            if (!stop)
                scanAtRow++;
            if (count == board.getWidth() / sizeOfTheCell)
                finishScanAtRow++;
        } else {
            scanAtRow = finishScanAtRow;
            huntMode = false;
            int randomNeighbourIndex = random.nextInt(unVisitedNeighbourList.size());
            Cell randomNeighbourCell = unVisitedNeighbourList.get(randomNeighbourIndex);
            int neighbourCellIndex = cellDetails.getCellIndex(randomNeighbourCell.getX(), randomNeighbourCell.getY());
            Cell neighbourCell = cellList.get(neighbourCellIndex);
            neighbourCell.setVisited(true);
            cellDetails.removeWalls(startCellIndex, neighbourCellIndex);
            startCell = neighbourCell;
            startCellIndex = neighbourCellIndex;
            if (cellDetails.isAllCellsVisited() && scanAtRow == cellList.size() / (board.getWidth() / sizeOfTheCell) - 1)
                isFinish = true;
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
        if (huntMode) {
            for (int i = (board.getWidth() / sizeOfTheCell) * scanAtRow; i < ((board.getWidth() / sizeOfTheCell) * scanAtRow) + board.getWidth() / sizeOfTheCell; i++) {
                graphicsContext.setFill(Color.valueOf("#FFD460"));
                graphicsContext.fillRect(cellList.get(i).getX(), cellList.get(i).getY(), sizeOfTheCell, sizeOfTheCell);
            }
        } else {
            graphicsContext.setFill(Color.valueOf("#e23e57"));
            graphicsContext.fillRect(startCell.getX(), startCell.getY(), sizeOfTheCell, sizeOfTheCell);
        }
    }

    @Override
    public boolean isCompleted() {
        return isFinish;
    }

}