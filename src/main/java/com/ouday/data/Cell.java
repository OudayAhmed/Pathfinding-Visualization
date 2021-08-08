package com.ouday.data;

public class Cell {

    private final int x;
    private final int y;
    private int weight;
    private boolean visited;
    private boolean[] isVisibleWall;
    private final boolean[] isVisibleEdge;
    private boolean isCellWall;
    private boolean isSearching;


    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        visited = false;
        isCellWall = false;
        isSearching = false;
        isVisibleWall = new boolean[]{true, true, true, true};
        isVisibleEdge = new boolean[]{true, true, true, true};
        weight = 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCellWall() {
        return isCellWall;
    }

    public void setCellWall(boolean cellWall) {
        isCellWall = cellWall;
    }

    public boolean isSearching() {
        return isSearching;
    }

    public void setSearching(boolean searching) {
        isSearching = searching;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean setVisibleWallAtIndex(int i, boolean j) {
        return isVisibleWall[i] = j;
    }

    public boolean getIsVisibleWallAtIndex(int i) {
        return isVisibleWall[i];
    }

    public void setIsVisibleWall(boolean[] isVisibleWall) {
        this.isVisibleWall = isVisibleWall;
    }

    public boolean setVisibleEdgeAtIndex(int i, boolean j) {
        return isVisibleEdge[i] = j;
    }

    public boolean getIsVisibleEdgeAtIndex(int i) {
        return isVisibleEdge[i];
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}