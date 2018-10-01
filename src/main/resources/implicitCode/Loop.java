/**
 * Special variables exposed by the {@code loop} implicit variable
 * in {@code for} loops.
 */
abstract class Loop {

    /**
     * A zero-based index that increments with every iteration.
     */
    public int index;

    /**
     * The size of the object we are iterating over.
     */
    public int length;

    /**
     * True if first iteration.
     */
    public boolean first;

    /**
     * True if last iteration.
     */
    public boolean last;

    /**
     * The number of iterations from the end of the loop.
     */
    public int revindex;
}
