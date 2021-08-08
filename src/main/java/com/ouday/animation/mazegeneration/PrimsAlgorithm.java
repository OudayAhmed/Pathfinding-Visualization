package com.ouday.animation.mazegeneration;

import com.ouday.data.Board;
import com.ouday.data.Cell;
import com.ouday.data.CellDetails;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimsAlgorithm implements MazeGenerationAlgorithm {

    private final Board board;
    private final CellDetails cellDetails;
    private final GraphicsContext graphicsContext;
    private final List<Cell> cellList;
    private List<Cell> neighbourList = new ArrayList<>();
    private final List<Cell> unVisitedNeighbourList = new ArrayList<>();
    private final Random random = new Random();
    private final int sizeOfTheCell;


    public PrimsAlgorithm(Board board) {
        this.board = board;
        this.cellDetails = board.getCellDetails();
        this.graphicsContext = board.getGraphicsContext();
        this.sizeOfTheCell = board.getSizeOfTheCell();
        this.cellList = board.getCellList();
    }

    @Override
    public void init() {
        int startCellIndex = random.nextInt(cellList.size());
        Cell cell = cellList.get(startCellIndex);
        cell.setVisited(true);
        graphicsContext.setFill(Color.valueOf("#311d3f"));
        graphicsContext.fillRect(cell.getX(), cell.getY(), sizeOfTheCell, sizeOfTheCell);
        this.neighbourList = cellDetails.getUnVisitedNeighbourList(cell);
        unVisitedNeighbourList.addAll(neighbourList);
        drawBoard();
    }

    @Override
    public void process() {
        if (!unVisitedNeighbourList.isEmpty()) {
            int randomNeighbourIndex = random.nextInt(unVisitedNeighbourList.size());
            Cell nextCell = unVisitedNeighbourList.get(randomNeighbourIndex);
            int nextCellIndex = cellDetails.getCellIndex(nextCell.getX(), nextCell.getY());
            Cell cell = cellList.get(nextCellIndex);
            if (!cell.isVisited()) {
                cell.setVisited(true);
                this.neighbourList = cellDetails.getUnVisitedNeighbourList(cell);
                for (Cell unVisitedCell : neighbourList)
                    if (!unVisitedNeighbourList.contains(unVisitedCell))
                        unVisitedNeighbourList.add(unVisitedCell);
                List<Cell> visitedNeighbourList = cellDetails.getVisitedNeighbourList(cell);
                Cell visitedNeighbourCell = visitedNeighbourList.get(random.nextInt(visitedNeighbourList.size()));
                int visitedNeighbourCellIndex = cellDetails.getCellIndex(visitedNeighbourCell.getX(), visitedNeighbourCell.getY());
                cellDetails.removeWalls(visitedNeighbourCellIndex, nextCellIndex);
                unVisitedNeighbourList.remove(nextCell);
            }
            drawBoard();
        }
    }

    public void drawBoard() {
        graphicsContext.setFill(Color.valueOf("#393E46"));
        graphicsContext.fillRect(0, 0, board.getWidth(), board.getHeight());
        for (Cell cell : cellList) {
            if (unVisitedNeighbourList.contains(cell)) {
                graphicsContext.setFill(Color.valueOf("#FFD460"));
                graphicsContext.fillRect(cell.getX(), cell.getY(), sizeOfTheCell, sizeOfTheCell);
            } else if (cell.isVisited()) {
                graphicsContext.setFill(Color.valueOf("#1E2022"));
                graphicsContext.fillRect(cell.getX(), cell.getY(), sizeOfTheCell, sizeOfTheCell);
            }
            graphicsContext.setLineWidth(1);
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
    }

    @Override
    public boolean isCompleted() {
        return unVisitedNeighbourList.isEmpty();
    }

}