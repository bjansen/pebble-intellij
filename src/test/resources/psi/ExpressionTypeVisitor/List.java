package pebble.tests;

interface List<E> {
    List<E> subList(int fromIndex, int toIndex);

    java.util.Iterator<E> iterator();
}
