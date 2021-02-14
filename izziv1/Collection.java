package izziv1;

interface Collection {
    static final String ERR_MSG_EMPTY = "Izziv1.Collection is empty.";
    static final String ERR_MSG_FULL = "Izziv1.Collection is full.";

    boolean isEmpty();
    boolean isFull();
    int size();
    String toString();
}