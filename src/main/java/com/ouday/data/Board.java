package com.ouday.data;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final int width;
    private final int height;
    private final int sizeOfTheCell;
    private final List<Cell> cellList = new ArrayList<>();
    private final CellDetails cellDetails;
    private final GraphicsContext graphicsContext;
    private Cell startCell;
    private Cell endCell;
    private int startCellIndex;
    private int endCellIndex;


    public Board(Canvas canvas, int sizeOfTheCell) {
        this.graphicsContext = canvas.getGraphicsContext2D();
        this.height = (int) canvas.getHeight();
        this.width = (int) canvas.getWidth();
        this.sizeOfTheCell = sizeOfTheCell;
        for (int i = 0; i < height; i += sizeOfTheCell)
            for (int j = 0; j < width; j += sizeOfTheCell)
                cellList.add(new Cell(j, i));
        this.cellDetails = new CellDetails(this);
        this.startCell = cellList.get(0);
        this.endCell = cellList.get(cellList.size() - 1);
        startCellIndex = cellDetails.getCellIndex(startCell.getX(), startCell.getY());
        endCellIndex = cellDetails.getCellIndex(endCell.getX(), endCell.getY());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getSizeOfTheCell() {
        return sizeOfTheCell;
    }

    public List<Cell> getCellList() {
        return cellList;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setStartCell(int x, int y) {
        int startIndex = cellDetails.getCellIndex(x, y);
        if (startIndex != -1 && !cellList.get(startIndex).isCellWall() && startIndex != endCellIndex) {
            this.startCellIndex = startIndex;
            this.startCell = cellList.get(startCellIndex);
        }
    }

    public void setEndCell(int x, int y) {
        int endIndex = cellDetails.getCellIndex(x, y);
        if (endIndex != -1 && !cellList.get(endIndex).isCellWall() && endIndex != startCellIndex) {
            this.endCellIndex = endIndex;
            this.endCell = cellList.get(endCellIndex);
        }
    }

    public void setWallCell(int x, int y) {
        int wallCellIndex = cellDetails.getCellIndex(x, y);
        if (wallCellIndex != -1 && startCellIndex != wallCellIndex && endCellIndex != wallCellIndex)
            cellList.get(wallCellIndex).setCellWall(true);
    }

    public void removeWallAndWeightCell(int x, int y) {
        int wallCellIndex = cellDetails.getCellIndex(x, y);
        if (wallCellIndex != -1 && startCellIndex != wallCellIndex && endCellIndex != wallCellIndex)
            if (cellList.get(wallCellIndex).isCellWall())
                cellList.get(wallCellIndex).setCellWall(false);
            else
                cellList.get(wallCellIndex).setWeight(0);
    }

    public void setCellWeight(int x, int y) {
        int cellIndex = cellDetails.getCellIndex(x, y);
        if (cellIndex != -1 && startCellIndex != cellIndex && endCellIndex != cellIndex)
            cellList.get(cellIndex).setWeight(50);
    }

    public Cell getStartCell() {
        return startCell;
    }

    public Cell getEndCell() {
        return endCell;
    }

    public int getStartCellIndex() {
        return startCellIndex;
    }

    public int getEndCellIndex() {
        return endCellIndex;
    }

    public CellDetails getCellDetails() {
        return cellDetails;
    }

}