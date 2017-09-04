package pebble.tests;

interface Collection<E> {
    void addAt(int offset, E element);
}

interface List<E> extends Collection<E> {
    void add(E element);
}
