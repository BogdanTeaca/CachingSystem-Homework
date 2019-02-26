package dataStructures.classes;

import java.sql.Timestamp;

/**
 * Class that implements the logic of a doubly linked list node.
 *
 * @author TEACA BOGDAN, 325CD
 */
public class Node<K, V> {
    public Pair<K, V> pair;
    public Node<K, V> prev;
    public Node<K, V> next;
    private Timestamp time = new Timestamp(0); // Timestamp is required for TimeAwareCache

    /**
     * Constructor for the node.
     */
    public Node(final K key, final V value) {
        this.pair = new Pair<K, V>(key, value);
        this.next = null;
        this.prev = null;
    }

    /**
     * Setter for the timestamp of the node.
     *
     * @param millis = the time the node was created (the key was inserted or last updated in cache)
     */
    public void setTimestamp(final long millis) {
        time = new Timestamp(millis);
    }

    /**
     * Getter for the timestamp of the node.
     */
    public Timestamp getTimestamp() {
        return time;
    }
}
