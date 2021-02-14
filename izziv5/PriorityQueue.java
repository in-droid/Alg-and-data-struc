package izziv5;

interface PriorityQueue<T extends Comparable> extends Queue<T> {
    int getComparisons();
    int getMovements();
}