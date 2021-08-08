package com.ouday.animation.pathfinding;

import com.ouday.data.Board;
import com.ouday.data.Cell;
import com.ouday.data.CellDetails;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.*;

public class BreadthFirstSearch implements PathfindingAlgorithm {

    private final Image startIcon = new Image(String.valueOf(getClass().getResource("/com/ouday/images/startIcon.png")));
    private final Image endIcon = new Image(String.valueOf(getClass().getResource("/com/ouday/images/endIcon.png")));
    private final Image wallIcon = new Image(String.valueOf(getClass().getResource("/com/ouday/images/wallIcon.png")));
    private final GraphicsContext graphicsContext;
    private final Board board;
    private final CellDetails cellDetails;
    private List<Cell> neighbourList = new ArrayList<>();
    private final List<Integer> pathList = new ArrayList<>();
    private final Deque<Cell> cellDeque = new ArrayDeque<>();
    private final List<Cell> cellList;
    private Cell startCell;
    private Cell endCell;
    private int startCellIndex;
    private int endCellIndex;
    private final int sizeOfTheCell;
    private int pathAtIndex;
    private final Integer[] path;
    private boolean pathFound;
    private boolean isDrawPath;


    public BreadthFirstSearch(Board board) {
        this.board = board;
        this.cellDetails = board.getCellDetails();
        this.graphicsContext = board.getGraphicsContext();
        this.sizeOfTheCell = board.getSizeOfTheCell();
        this.pathAtIndex = 1;
        this.cellList = board.getCellList();
        this.startCell = board.getStartCell();
        this.endCell = board.getEndCell();
        this.startCellIndex = board.getStartCellIndex();
        this.endCellIndex = board.getEndCellIndex();
        this.path = new Integer[cellList.size()];
        this.pathFound = false;
        this.isDrawPath = false;
    }

    @Override
    public void init() {
        this.startCell = board.getStartCell();
        this.startCellIndex = board.getStartCellIndex();
        if (!cellDeque.isEmpty()) {
            Cell cell = cellDeque.poll();
            int indexCell = cellDetails.getCellIndex(cell.getX(), cell.getY());
            cellList.get(indexCell).setSearching(false);
        }
        cellDeque.offer(startCell);
        cellList.get(startCellIndex).setSearching(true);
        drawBoard();
    }

    @Override
    public void process() {
        if (!pathFound) {
            if (!cellDeque.isEmpty()) {
                Cell cell = cellDeque.poll();
                int indexCell = cellDetails.getCellIndex(cell.getX(), cell.getY());
                neighbourList = cellDetails.getNeighbourList(cell);
                for (Cell neighbourCell : neighbourList) {
                    int index = cellDetails.getCellIndex(neighbourCell.getX(), neighbourCell.getY());
                    if (index == endCellIndex) {
                        path[index] = indexCell;
                        cellList.get(index).setSearching(true);
                        cellDeque.clear();
                        int end = endCellIndex;
                        for (Integer i = end; i != null; i = path[i])
                            pathList.add(i);
                        Collections.reverse(pathList);
                        pathFound = true;
                    } else {
                        cellList.get(index).setSearching(true);
                        path[index] = indexCell;
                        cellDeque.offer(cellList.get(index));
                    }
                }
                drawBoard();
            }
        } else if (!isDrawPath) {
            if (pathAtIndex < pathList.size() - 1) {
                if (pathAtIndex + 1 == pathList.size() - 2)
                    isDrawPath = true;
                pathAtIndex++;
                drawBoard();
            }
        }
    }

    @Override
    public void drawBoard() {
        this.endCell = board.getEndCell();
        this.endCellIndex = board.getEndCellIndex();
        graphicsContext.setFill(Color.valueOf("#1E2022"));
        graphicsContext.fillRect(0, 0, board.getWidth(), board.getHeight());
        for (int i = 0; i < cellList.size(); i++) {
            if (cellList.get(i).isVisited()) {
                graphicsContext.setFill(Color.valueOf("#1E2022"));
                graphicsContext.fillRect(cellList.get(i).getX(), cellList.get(i).getY(), sizeOfTheCell, sizeOfTheCell);
            }
            if (cellList.get(i).isSearching() && i != startCellIndex && i != endCellIndex) {
                graphicsContext.setFill(Color.valueOf("#e23e57"));
                graphicsContext.fillOval(cellList.get(i).getX(), cellList.get(i).getY(), sizeOfTheCell, sizeOfTheCell);

                graphicsContext.setFill(Color.valueOf("#1E2022"));
                graphicsContext.fillOval(cellList.get(i).getX() + (sizeOfTheCell * 0.15), cellList.get(i).getY() + (sizeOfTheCell * 0.15), sizeOfTheCell * 0.70, sizeOfTheCell * 0.70);

                graphicsContext.setFill(Color.valueOf("#e23e57"));
                graphicsContext.fillOval(cellList.get(i).getX() + (sizeOfTheCell * 0.30), cellList.get(i).getY() + (sizeOfTheCell * 0.30), sizeOfTheCell * 0.40, sizeOfTheCell * 0.40);
            }
        }
        for (Cell cell : neighbourList) {
            if (cellDetails.getCellIndex(cell.getX(), cell.getY()) != endCellIndex) {
                graphicsContext.setFill(Color.valueOf("#ffd460"));
                graphicsContext.fillOval(cell.getX(), cell.getY(), sizeOfTheCell, sizeOfTheCell);

                graphicsContext.setFill(Color.valueOf("#1E2022"));
                graphicsContext.fillOval(cell.getX() + (sizeOfTheCell * 0.15), cell.getY() + (sizeOfTheCell * 0.15), sizeOfTheCell * 0.70, sizeOfTheCell * 0.70);

                graphicsContext.setFill(Color.valueOf("#ffd460"));
                graphicsContext.fillOval(cell.getX() + (sizeOfTheCell * 0.30), cell.getY() + (sizeOfTheCell * 0.30), sizeOfTheCell * 0.40, sizeOfTheCell * 0.40);
            }
        }
        if (pathFound) {
            for (int i = 1; i <= pathAtIndex; i++) {
                graphicsContext.setFill(Color.valueOf("#FF9234"));
                graphicsContext.fillOval(cellList.get(pathList.get(i)).getX(), cellList.get(pathList.get(i)).getY(), sizeOfTheCell, sizeOfTheCell);

                graphicsContext.setFill(Color.valueOf("#1E2022"));
                graphicsContext.fillOval(cellList.get(pathList.get(i)).getX() + (sizeOfTheCell * 0.15), cellList.get(pathList.get(i)).getY() + (sizeOfTheCell * 0.15), sizeOfTheCell * 0.70, sizeOfTheCell * 0.70);

                graphicsContext.setFill(Color.valueOf("#FF9234"));
                graphicsContext.fillOval(cellList.get(pathList.get(i)).getX() + (sizeOfTheCell * 0.30), cellList.get(pathList.get(i)).getY() + (sizeOfTheCell * 0.30), sizeOfTheCell * 0.40, sizeOfTheCell * 0.40);
            }
        }
        for (int i = 0; i < cellList.size(); i++) {
            Cell cell = cellList.get(i);
            int x = cell.getX();
            int y = cell.getY();
            if (i == startCellIndex)
                graphicsContext.drawImage(startIcon, x, y, sizeOfTheCell, sizeOfTheCell);
            if (i == endCellIndex)
                graphicsContext.drawImage(endIcon, x, y, sizeOfTheCell, sizeOfTheCell);
            this.graphicsContext.setStroke(Color.valueOf("#F0FFF3"));
            if (cell.isCellWall())
                graphicsContext.drawImage(wallIcon, x, y, sizeOfTheCell, sizeOfTheCell);
            else {
                if (cell.getIsVisibleWallAtIndex(0))
                    this.graphicsContext.strokeLine(x, y, x + sizeOfTheCell, y);
                if (cell.getIsVisibleWallAtIndex(1))
                    this.graphicsContext.strokeLine(x + sizeOfTheCell, y, x + sizeOfTheCell, y + sizeOfTheCell);
                if (cell.getIsVisibleWallAtIndex(2))
                    this.graphicsContext.strokeLine(x + sizeOfTheCell, y + sizeOfTheCell, x, y + sizeOfTheCell);
                if (cell.getIsVisibleWallAtIndex(3))
                    this.graphicsContext.strokeLine(x, y, x, y + sizeOfTheCell);
            }
        }
    }

    @Override
    public boolean isCompleted() {
        return isDrawPath;
    }

}