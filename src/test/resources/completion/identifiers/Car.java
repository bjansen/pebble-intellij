package pebble.tests;

public class Car {
    public List<Part> parts = new ArrayList<>();
}

interface List<E> extends Collection<E>, java.lang.Iterable<Part> {
    void add(E element);
}
