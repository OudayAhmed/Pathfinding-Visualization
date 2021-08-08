package com.ouday.data;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class CellDetails {

    private final Board board;
    private final List<Cell> cellList;
    private final int sizeOfTheCell;
    private final Random random;


    public CellDetails(Board board) {
        this.board = board;
        this.cellList = board.getCellList();
        this.sizeOfTheCell = board.getSizeOfTheCell();
        this.random = new Random();
    }

    public int getCellIndex(int x, int y) {
        for (int i = 0; i < cellList.size(); i++)
            if (x >= cellList.get(i).getX() && x < cellList.get(i).getX() + sizeOfTheCell && y >= cellList.get(i).getY() && y < cellList.get(i).getY() + sizeOfTheCell)
                return i;

        return -1;
    }

    public List<Cell> getUnSearchingNeighbourList(Cell cell) {
        List<Cell> neighbourListTemp = new ArrayList<>();

        int topCellY = cell.getY() - sizeOfTheCell;
        if (topCellY >= 0) {
            int topCellX = cell.getX();
            int topCellIndex = getCellIndex(topCellX, topCellY);
            if (topCellIndex != -1)
                if (!cellList.get(topCellIndex).isSearching())
                    neighbourListTemp.add(cellList.get(topCellIndex));
        }

        int rightCellX = cell.getX() + sizeOfTheCell;
        if (rightCellX < board.getWidth()) {
            int rightCellY = cell.getY();
            int rightCellIndex = getCellIndex(rightCellX, rightCellY);
            if (rightCellIndex != -1)
                if (!cellList.get(rightCellIndex).isSearching())
                    neighbourListTemp.add(cellList.get(rightCellIndex));
        }

        int bottomCellY = cell.getY() + sizeOfTheCell;
        if (bottomCellY <= board.getHeight()) {
            int bottomCellX = cell.getX();
            int bottomCellIndex = getCellIndex(bottomCellX, bottomCellY);
            if (bottomCellIndex != -1)
                if (!cellList.get(bottomCellIndex).isSearching())
                    neighbourListTemp.add(cellList.get(bottomCellIndex));
        }

        int leftCellX = cell.getX() - sizeOfTheCell;
        if (leftCellX >= 0) {
            int leftCellY = cell.getY();
            int leftCellIndex = getCellIndex(leftCellX, leftCellY);
            if (leftCellIndex != -1)
                if (!cellList.get(leftCellIndex).isSearching())
                    neighbourListTemp.add(cellList.get(leftCellIndex));
        }

        return neighbourListTemp;
    }

    public List<Cell> getNeighbourList(Cell cell) {
        List<Cell> neighbourListTemp = new ArrayList<>();

        int topCellY = cell.getY() - sizeOfTheCell;
        if (topCellY >= 0) {
            int topCellX = cell.getX();
            int topCellIndex = getCellIndex(topCellX, topCellY);
            if (topCellIndex != -1)
                if (!cellList.get(topCellIndex).isSearching() && !cellList.get(topCellIndex).getIsVisibleWallAtIndex(2) && !cellList.get(topCellIndex).isCellWall())
                    neighbourListTemp.add(cellList.get(topCellIndex));
        }

        int rightCellX = cell.getX() + sizeOfTheCell;
        if (rightCellX < board.getWidth()) {
            int rightCellY = cell.getY();
            int rightCellIndex = getCellIndex(rightCellX, rightCellY);
            if (rightCellIndex != -1)
                if (!cellList.get(rightCellIndex).isSearching() && !cellList.get(rightCellIndex).getIsVisibleWallAtIndex(3) && !cellList.get(rightCellIndex).isCellWall())
                    neighbourListTemp.add(cellList.get(rightCellIndex));
        }

        int bottomCellY = cell.getY() + sizeOfTheCell;
        if (bottomCellY <= board.getHeight()) {
            int bottomCellX = cell.getX();
            int bottomCellIndex = getCellIndex(bottomCellX, bottomCellY);
            if (bottomCellIndex != -1)
                if (!cellList.get(bottomCellIndex).isSearching() && !cellList.get(bottomCellIndex).getIsVisibleWallAtIndex(0) && !cellList.get(bottomCellIndex).isCellWall())
                    neighbourListTemp.add(cellList.get(bottomCellIndex));
        }

        int leftCellX = cell.getX() - sizeOfTheCell;
        if (leftCellX >= 0) {
            int leftCellY = cell.getY();
            int leftCellIndex = getCellIndex(leftCellX, leftCellY);
            if (leftCellIndex != -1)
                if (!cellList.get(leftCellIndex).isSearching() && !cellList.get(leftCellIndex).getIsVisibleWallAtIndex(1) && !cellList.get(leftCellIndex).isCellWall())
                    neighbourListTemp.add(cellList.get(leftCellIndex));
        }

        return neighbourListTemp;
    }

    public int getCountNorthEastNeighbour(Cell cell) {
        int count = 0;

        int topCellY = cell.getY() - sizeOfTheCell;
        if (topCellY >= 0) {
            int topCellX = cell.getX();
            int topCellIndex = getCellIndex(topCellX, topCellY);
            if (topCellIndex != -1)
                if (cellList.get(topCellIndex).getIsVisibleWallAtIndex(2))
                    count++;
        }

        int rightCellX = cell.getX() + sizeOfTheCell;
        if (rightCellX < board.getWidth()) {
            int rightCellY = cell.getY();
            int rightCellIndex = getCellIndex(rightCellX, rightCellY);
            if (rightCellIndex != -1)
                if (cellList.get(rightCellIndex).getIsVisibleWallAtIndex(3))
                    count++;
        }

        return count;
    }

    public int getNorthNeighbourIndex(Cell cell) {
        int topCellY = cell.getY() - sizeOfTheCell;
        if (topCellY >= 0) {
            int topCellX = cell.getX();
            int topCellIndex = getCellIndex(topCellX, topCellY);
            if (topCellIndex != -1)
                if (cellList.get(topCellIndex).getIsVisibleWallAtIndex(2))
                    return topCellIndex;
        }

        return -1;
    }

    public int getEastNeighbourIndex(Cell cell) {
        int rightCellX = cell.getX() + sizeOfTheCell;
        if (rightCellX < board.getWidth()) {
            int rightCellY = cell.getY();
            int rightCellIndex = getCellIndex(rightCellX, rightCellY);
            if (rightCellIndex != -1)
                if (cellList.get(rightCellIndex).getIsVisibleWallAtIndex(3))
                    return rightCellIndex;
        }

        return -1;
    }

    public int getRandomUnVisitedNeighbourCell(Stack<Cell> cellStack, Cell cell) {
        List<Cell> neighbourList = getUnVisitedNeighbourList(cell);
        if (neighbourList.size() > 0) {
            int randomIndex = random.nextInt(neighbourList.size());
            int neighbourRandomIndex = getCellIndex(neighbourList.get(randomIndex).getX(), neighbourList.get(randomIndex).getY());
            cellList.get(neighbourRandomIndex).setVisited(true);
            removeWalls(getCellIndex(cell.getX(), cell.getY()), neighbourRandomIndex);
            cellStack.add(cellList.get(neighbourRandomIndex));
            return neighbourRandomIndex;
        }

        return -1;
    }

    public Pair<Integer, Integer> getRandomEdge(ArrayList<ArrayList<Integer>> edgeList) {
        int randomEdgeFromIndex = random.nextInt(edgeList.size());
        while (edgeList.get(randomEdgeFromIndex).size() == 0)
            randomEdgeFromIndex = random.nextInt(edgeList.size());
        int randomEdgeToIndex = random.nextInt(edgeList.get(randomEdgeFromIndex).size());
        Pair<Integer, Integer> randomEdge = new Pair<>(randomEdgeFromIndex, edgeList.get(randomEdgeFromIndex).get(randomEdgeToIndex));
        edgeList.get(randomEdgeFromIndex).remove(randomEdgeToIndex);

        return randomEdge;
    }

    public ArrayList<ArrayList<Integer>> getAllEdges() {
        ArrayList<ArrayList<Integer>> edgeList = new ArrayList<>();
        for (int i = 0; i < cellList.size(); i++) {
            edgeList.add(new ArrayList<>());
            List<Cell> neighbourList = getNeighbourListWithVisibleEdge(cellList.get(i));
            for (Cell neighbourCell : neighbourList) {
                int neighbourCellIndex = getCellIndex(neighbourCell.getX(), neighbourCell.getY());
                edgeList.get(i).add(neighbourCellIndex);
                removeEdges(i, neighbourCellIndex);
            }
        }
        return edgeList;
    }

    public ArrayList<ArrayList<Pair<Integer, Integer>>> getAdjList() {
        ArrayList<ArrayList<Pair<Integer, Integer>>> AdjList = new ArrayList<>();
        for (int i = 0; i < cellList.size(); i++) {
            AdjList.add(new ArrayList<>());
            List<Cell> neighbourList = getNeighbourList(cellList.get(i));
            for (Cell neighbourCell : neighbourList) {
                int neighbourCellIndex = getCellIndex(neighbourCell.getX(), neighbourCell.getY());
                AdjList.get(i).add(new Pair<>(cellList.get(neighbourCellIndex).getWeight(), neighbourCellIndex));
            }
        }
        return AdjList;
    }

    public List<Cell> getNeighbourListWithVisibleEdge(Cell cell) {
        List<Cell> neighbourList = new ArrayList<>();

        int topCellY = cell.getY() - sizeOfTheCell;
        if (topCellY < board.getHeight()) {
            int topCellX = cell.getX();
            int topCellIndex = getCellIndex(topCellX, topCellY);
            if (topCellIndex != -1)
                if (cell.getIsVisibleEdgeAtIndex(0))
                    neighbourList.add(cellList.get(topCellIndex));
        }

        int bottomCellY = cell.getY() + sizeOfTheCell;
        if (bottomCellY >= 0) {
            int bottomCellX = cell.getX();
            int bottomCellIndex = getCellIndex(bottomCellX, bottomCellY);
            if (bottomCellIndex != -1)
                if (cell.getIsVisibleEdgeAtIndex(2))
                    neighbourList.add(cellList.get(bottomCellIndex));
        }

        int rightCellX = cell.getX() + sizeOfTheCell;
        if (rightCellX < board.getWidth()) {
            int rightCellY = cell.getY();
            int rightCellIndex = getCellIndex(rightCellX, rightCellY);
            if (rightCellIndex != -1)
                if (cell.getIsVisibleEdgeAtIndex(1))
                    neighbourList.add(cellList.get(rightCellIndex));
        }

        int leftCellX = cell.getX() - sizeOfTheCell;
        if (leftCellX >= 0) {
            int leftCellY = cell.getY();
            int leftCellIndex = getCellIndex(leftCellX, leftCellY);
            if (leftCellIndex != -1)
                if (cell.getIsVisibleEdgeAtIndex(3))
                    neighbourList.add(cellList.get(leftCellIndex));
        }

        return neighbourList;
    }

    public List<Cell> getUnVisitedNeighbourList(Cell cell) {
        List<Cell> neighbourList = new ArrayList<>();

        int topCellY = cell.getY() - sizeOfTheCell;
        if (topCellY < board.getHeight()) {
            int topCellX = cell.getX();
            int topCellIndex = getCellIndex(topCellX, topCellY);
            if (topCellIndex != -1)
                if (!cellList.get(topCellIndex).isVisited())
                    neighbourList.add(cellList.get(topCellIndex));
        }

        int bottomCellY = cell.getY() + sizeOfTheCell;
        if (bottomCellY >= 0) {
            int bottomCellX = cell.getX();
            int bottomCellIndex = getCellIndex(bottomCellX, bottomCellY);
            if (bottomCellIndex != -1)
                if (!cellList.get(bottomCellIndex).isVisited())
                    neighbourList.add(cellList.get(bottomCellIndex));
        }

        int rightCellX = cell.getX() + sizeOfTheCell;
        if (rightCellX < board.getWidth()) {
            int rightCellY = cell.getY();
            int rightCellIndex = getCellIndex(rightCellX, rightCellY);
            if (rightCellIndex != -1)
                if (!cellList.get(rightCellIndex).isVisited())
                    neighbourList.add(cellList.get(rightCellIndex));
        }

        int leftCellX = cell.getX() - sizeOfTheCell;
        if (leftCellX >= 0) {
            int leftCellY = cell.getY();
            int leftCellIndex = getCellIndex(leftCellX, leftCellY);
            if (leftCellIndex != -1)
                if (!cellList.get(leftCellIndex).isVisited())
                    neighbourList.add(cellList.get(leftCellIndex));
        }

        return neighbourList;
    }

    public List<Cell> getVisitedNeighbourList(Cell cell) {
        List<Cell> neighbourList = new ArrayList<>();

        int topCellY = cell.getY() - sizeOfTheCell;
        if (topCellY < board.getHeight()) {
            int topCellX = cell.getX();
            int topCellIndex = getCellIndex(topCellX, topCellY);
            if (topCellIndex != -1)
                if (cellList.get(topCellIndex).isVisited())
                    neighbourList.add(cellList.get(topCellIndex));
        }

        int bottomCellY = cell.getY() + sizeOfTheCell;
        if (bottomCellY >= 0) {
            int bottomCellX = cell.getX();
            int bottomCellIndex = getCellIndex(bottomCellX, bottomCellY);
            if (bottomCellIndex != -1)
                if (cellList.get(bottomCellIndex).isVisited())
                    neighbourList.add(cellList.get(bottomCellIndex));
        }

        int rightCellX = cell.getX() + sizeOfTheCell;
        if (rightCellX < board.getWidth()) {
            int rightCellY = cell.getY();
            int rightCellIndex = getCellIndex(rightCellX, rightCellY);
            if (rightCellIndex != -1)
                if (cellList.get(rightCellIndex).isVisited())
                    neighbourList.add(cellList.get(rightCellIndex));
        }

        int leftCellX = cell.getX() - sizeOfTheCell;
        if (leftCellX >= 0) {
            int leftCellY = cell.getY();
            int leftCellIndex = getCellIndex(leftCellX, leftCellY);
            if (leftCellIndex != -1)
                if (cellList.get(leftCellIndex).isVisited())
                    neighbourList.add(cellList.get(leftCellIndex));
        }

        return neighbourList;
    }

    public boolean isAllCellsVisited() {
        for (Cell cell : cellList)
            if (!cell.isVisited())
                return false;

        return true;
    }

    public void removeWalls(int startCellIndex, int neighbourRandomIndex) {
        Cell startCell = cellList.get(startCellIndex);
        Cell neighbourRandomCell = cellList.get(neighbourRandomIndex);
        if (neighbourRandomCell.getX() == startCell.getX() && neighbourRandomCell.getY() - sizeOfTheCell == startCell.getY()) {
            neighbourRandomCell.setVisibleWallAtIndex(0, false);
            startCell.setVisibleWallAtIndex(2, false);
        }
        if (neighbourRandomCell.getX() + sizeOfTheCell == startCell.getX() && neighbourRandomCell.getY() == startCell.getY()) {
            neighbourRandomCell.setVisibleWallAtIndex(1, false);
            startCell.setVisibleWallAtIndex(3, false);
        }
        if (neighbourRandomCell.getX() == startCell.getX() && neighbourRandomCell.getY() + sizeOfTheCell == startCell.getY()) {
            neighbourRandomCell.setVisibleWallAtIndex(2, false);
            startCell.setVisibleWallAtIndex(0, false);
        }
        if (neighbourRandomCell.getX() - sizeOfTheCell == startCell.getX() && neighbourRandomCell.getY() == startCell.getY()) {
            neighbourRandomCell.setVisibleWallAtIndex(3, false);
            startCell.setVisibleWallAtIndex(1, false);
        }
    }

    public void removeEdges(int startCellIndex, int neighbourRandomIndex) {
        Cell startCell = cellList.get(startCellIndex);
        Cell neighbourRandomCell = cellList.get(neighbourRandomIndex);
        if (neighbourRandomCell.getX() == startCell.getX() && neighbourRandomCell.getY() - sizeOfTheCell == startCell.getY()) {
            neighbourRandomCell.setVisibleEdgeAtIndex(0, false);
            startCell.setVisibleEdgeAtIndex(2, false);
        }
        if (neighbourRandomCell.getX() + sizeOfTheCell == startCell.getX() && neighbourRandomCell.getY() == startCell.getY()) {
            neighbourRandomCell.setVisibleEdgeAtIndex(1, false);
            startCell.setVisibleEdgeAtIndex(3, false);
        }
        if (neighbourRandomCell.getX() == startCell.getX() && neighbourRandomCell.getY() + sizeOfTheCell == startCell.getY()) {
            neighbourRandomCell.setVisibleEdgeAtIndex(2, false);
            startCell.setVisibleEdgeAtIndex(0, false);
        }
        if (neighbourRandomCell.getX() - sizeOfTheCell == startCell.getX() && neighbourRandomCell.getY() == startCell.getY()) {
            neighbourRandomCell.setVisibleEdgeAtIndex(3, false);
            startCell.setVisibleEdgeAtIndex(1, false);
        }
    }

}