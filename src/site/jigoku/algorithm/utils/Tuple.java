package site.jigoku.algorithm.utils;

public class Tuple<T, K> {
    private final T first;
    private final K second;
    public Tuple(T first, K second) {
        this.first = first;
        this.second = second;
    }
    public T getFirst() {
        return first;
    }
    public K getSecond() {
        return second;
    }
}
