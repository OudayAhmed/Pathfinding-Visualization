package com.ouday.data;

import java.util.ArrayList;
import java.util.List;

public class UnionFind {

    private final List<Integer> rank = new ArrayList<>();
    private final List<Integer> parent = new ArrayList<>();
    private final List<Integer> sizeOfSet = new ArrayList<>();
    private int numberOfSets;


    public UnionFind(int numberOfSets) {
        this.numberOfSets = numberOfSets;
        for (int i = 0; i < numberOfSets; i++) {
            rank.add(0);
            parent.add(i);
            sizeOfSet.add(1);
        }
    }

    public void union(int a, int b) {
        if (!isSameSet(a, b)) {
            int x = find(a);
            int y = find(b);
            if (rank.get(x) > rank.get(y)) {
                sizeOfSet.set(x, sizeOfSet.get(x) + sizeOfSet.get(y));
                parent.set(y, x);
            } else {
                sizeOfSet.set(y, sizeOfSet.get(y) + sizeOfSet.get(x));
                parent.set(x, y);
                if (rank.get(x).equals(rank.get(y)))
                    rank.set(y, rank.get(y) + 1);
            }
            numberOfSets--;
        }
    }

    public boolean isSameSet(int a, int b) {
        return find(a) == find(b);
    }

    public int find(int a) {
        if (parent.get(a) == a)
            return a;
        else {
            parent.set(a, find(parent.get(a)));
            return parent.get(a);
        }
    }

    public int getNumberOfSets() {
        return numberOfSets;
    }

}