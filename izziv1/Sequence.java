package izziv1;

interface Sequence<T> extends Collection {
    static final String ERR_MSG_INDEX = "Wrong index in sequence.";

    T get(int i) throws CollectionException;

    void add(T x) throws CollectionException;
}