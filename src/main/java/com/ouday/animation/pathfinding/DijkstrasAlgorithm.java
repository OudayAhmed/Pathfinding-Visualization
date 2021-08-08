package com.ouday.animation.pathfinding;

import com.ouday.data.Board;
import com.ouday.data.Cell;
import com.ouday.data.CellDetails;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.IOException;
import java.util.*;

import static java.lang.Double.POSITIVE_INFINITY;

public class DijkstrasAlgorithm implements PathfindingAlgorithm {

    private final Image wallIcon = new Image(String.valueOf(getClass().getResource("/com/ouday/images/wallIcon.png")));
    private final Image weightIcon = new Image(String.valueOf(getClass().getResource("/com/ouday/images/weightIcon.png")));
    private final Image startIcon = new Image(String.valueOf(getClass().getResource("/com/ouday/images/startIcon.png")));
    private final Image endIcon = new Image(String.valueOf(getClass().getResource("/com/ouday/images/endIcon.png")));
    private final GraphicsContext graphicsContext;
    private final Board board;
    private final CellDetails cellDetails;
    private ArrayList<ArrayList<Pair<Integer, Integer>>> adjList;
    private List<Cell> neighbourList = new ArrayList<>();
    private List<Double> dist;
    private List<Integer> pathList;
    private PriorityQueue<Pair<Integer, Integer>> pq;
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
    private boolean isStart;


    public DijkstrasAlgorithm(Board board) {
        this.board = board;
        this.cellDetails = board.getCellDetails();
        this.graphicsContext = board.getGraphicsContext();
        this.sizeOfTheCell = board.getSizeOfTheCell();
        this.adjList = cellDetails.getAdjList();
        this.pathAtIndex = 1;
        this.cellList = board.getCellList();
        this.startCell = board.getStartCell();
        this.endCell = board.getEndCell();
        this.startCellIndex = board.getStartCellIndex();
        this.endCellIndex = board.getEndCellIndex();
        this.path = new Integer[cellList.size()];
        this.pathFound = false;
        this.isDrawPath = false;
        this.isStart = false;
    }

    @Override
    public void init() throws IOException {
        this.startCell = board.getStartCell();
        this.startCellIndex = board.getStartCellIndex();
        drawBoard();
    }

    @Override
    public void process() throws IOException {
        if (!pathFound) {
            if (!isStart) {
                this.adjList = cellDetails.getAdjList();
                this.dist = new ArrayList<>();
                for (int i = 0; i < cellList.size(); i++)
                    dist.add(POSITIVE_INFINITY);
                dist.set(startCellIndex, (double) 0);
                pq = new PriorityQueue<>(1, Comparator.comparingInt(Pair::getKey));
                pq.offer(new Pair<>(0, startCellIndex));
                isStart = true;
                process();
            }
            if (!pq.isEmpty()) {
                Pair<Integer, Integer> top = pq.remove();
                int distance = top.getKey();
                int nextCellIndex = top.getValue();
                cellList.get(nextCellIndex).setSearching(true);
                boolean stop = distance > dist.get(nextCellIndex);
                if (!stop) {
                    neighbourList = cellDetails.getNeighbourList(cellList.get(nextCellIndex));
                    for (int i = 0; i < neighbourList.size(); i++) {
                        Cell cell = neighbourList.get(i);
                        int index = 0;
                        for (int k = 0; k < adjList.get(nextCellIndex).size(); k++)
                            if (adjList.get(nextCellIndex).get(k).getValue() == cellDetails.getCellIndex(cell.getX(), cell.getY()))
                                index = k;
                        Pair<Integer, Integer> u = adjList.get(nextCellIndex).get(index);
                        int weightToV = u.getKey();
                        int v = u.getValue();
                        if (dist.get(nextCellIndex) + weightToV < dist.get(v)) {
                            dist.set(v, dist.get(nextCellIndex) + weightToV);
                            pq.offer(new Pair<>(dist.get(v).intValue(), v));
                            neighbourList.add(cellList.get(v));
                        }
                        if (v == endCellIndex) {
                            path[v] = nextCellIndex;
                            cellList.get(v).setSearching(true);
                            pq.clear();
                            pathList = new ArrayList<>();
                            int end = endCellIndex;
                            for (Integer j = end; j != null; j = path[j])
                                pathList.add(j);
                            Collections.reverse(pathList);
                            pathFound = true;
                        } else
                            path[v] = nextCellIndex;
                    }
                }
                drawBoard();
            }
        } else if (!isDrawPath) {
            if (pathAtIndex < pathList.size() - 1) {
                pathAtIndex++;
                if (pathAtIndex + 1 == pathList.size() - 1)
                    isDrawPath = true;
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
            if (i == startCellIndex)
                graphicsContext.drawImage(startIcon, cellList.get(i).getX(), cellList.get(i).getY(), sizeOfTheCell, sizeOfTheCell);
            if (i == endCellIndex)
                graphicsContext.drawImage(endIcon, cellList.get(i).getX(), cellList.get(i).getY(), sizeOfTheCell, sizeOfTheCell);
            if (cellList.get(i).isSearching() && i != startCellIndex && i != endCellIndex) {
                graphicsContext.setFill(Color.valueOf("#e23e57"));
                graphicsContext.fillOval(cellList.get(i).getX(), cellList.get(i).getY(), sizeOfTheCell, sizeOfTheCell);

                graphicsContext.setFill(Color.valueOf("#1E2022"));
                graphicsContext.fillOval(cellList.get(i).getX() + (sizeOfTheCell * 0.15), cellList.get(i).getY() + (sizeOfTheCell * 0.15), sizeOfTheCell * 0.70, sizeOfTheCell * 0.70);

                graphicsContext.setFill(Color.valueOf("#e23e57"));
                graphicsContext.fillOval(cellList.get(i).getX() + (sizeOfTheCell * 0.30), cellList.get(i).getY() + (sizeOfTheCell * 0.30), sizeOfTheCell * 0.40, sizeOfTheCell * 0.40);
            }
            if (cellList.get(i).getWeight() > 1)
                graphicsContext.drawImage(weightIcon, cellList.get(i).getX(), cellList.get(i).getY(), sizeOfTheCell, sizeOfTheCell);
        }
        for (Cell cell : neighbourList) {
            int x = cell.getX();
            int y = cell.getY();
            if (cellDetails.getCellIndex(x, y) != endCellIndex && cell.getWeight() == 1) {
                graphicsContext.setFill(Color.valueOf("#ffd460"));
                graphicsContext.fillOval(x, y, sizeOfTheCell, sizeOfTheCell);

                graphicsContext.setFill(Color.valueOf("#1E2022"));
                graphicsContext.fillOval(x + (sizeOfTheCell * 0.15), y + (sizeOfTheCell * 0.15), sizeOfTheCell * 0.70, sizeOfTheCell * 0.70);

                graphicsContext.setFill(Color.valueOf("#ffd460"));
                graphicsContext.fillOval(x + (sizeOfTheCell * 0.30), y + (sizeOfTheCell * 0.30), sizeOfTheCell * 0.40, sizeOfTheCell * 0.40);
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
        for (Cell cell : cellList) {
            int x = cell.getX();
            int y = cell.getY();
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