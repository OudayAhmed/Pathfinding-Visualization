package com.ouday.animation.mazegeneration;

import com.ouday.data.Board;
import com.ouday.data.Cell;

import java.util.List;

public class EmptyGrid implements MazeGenerationAlgorithm {

    private final List<Cell> cellList;


    public EmptyGrid(Board board) {
        this.cellList = board.getCellList();
    }

    @Override
    public void init() {
        for (Cell cell : cellList) cell.setIsVisibleWall(new boolean[]{false, false, false, false});
    }

    @Override
    public void process() {
    }

    @Override
    public boolean isCompleted() {
        return true;
    }

}