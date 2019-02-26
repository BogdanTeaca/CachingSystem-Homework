package dataStructures.classes;

import cachingSystem.classes.LRUCache;

/**
 * Class that implements a Doubly Linked List. The operations also updates the
 * hash map entries of the cache.
 *
 * @author TEACA BOGDAN, 325CD
 */
public class GenericDoublyLinkedList<K, V> {
    private LRUCache<K, V> lruCache;
    private Node<K, V> firstNode;
    private Node<K, V> lastNode;

    public GenericDoublyLinkedList(final LRUCache<K, V> lruCache) {
        this.lruCache = lruCache;
    }

    /**
     * Method that inserts a node into the linked list and into the hash map of the cache.
     */
    private void insert(final Node<K, V> node) {
        K key = node.pair.getKey();
        lruCache.getCacheMap().put(key, node);

        if (firstNode == null) { // if list is empty
            firstNode = node;
            lastNode = node;

            return;
        } else {
            firstNode.prev = node;
            node.next = firstNode;
            firstNode = node;
        }
    }

    /**
     * Method that returns the value associated with the key and pushes it to front of the list.
     */
    public V get(final K key) {
        Node<K, V> node = lruCache.getCacheMap().get(key);

        if (node != null) {
            pushNodeToFront(key);

            return node.pair.getValue();
        }

        return null;
    }

    /**
     * Method that pushes a node to the front of the list.
     */
    public void pushNodeToFront(final K key) {
        Node<K, V> node = lruCache.getCacheMap().get(key);

        remove(key);
        insert(node);
    }

    /**
     * Method for updating a node (if it is already in cache) or calling the insert method (if
     * the key is not in cache).
     */
    public void updateNode(final K key, final V value) {
        Node<K, V> node = lruCache.getCacheMap().get(key);

        if (node != null) {
            pushNodeToFront(key);

            node.pair.setValue(value);
        } else {
            insert(new Node<K, V>(key, value));

            lruCache.clearStaleEntries();
        }
    }

    /**
     * Method for removing the node that contains the key.
     */
    public void remove(final K key) {
        Node<K, V> node = lruCache.getCacheMap().get(key);

        if (lruCache.getCacheMap().containsKey(key)) {
            lruCache.getCacheMap().remove(key);
        }

        if (firstNode == lastNode) { // list contain a single node
            firstNode = null;
            lastNode = null;
        } else if (node == firstNode) { // the node to be deleted is the first one
            firstNode.next.prev = null;
            firstNode = firstNode.next;
        } else if (node == lastNode) { // the node to be deleted is the last one
            lastNode.prev.next = null;
            lastNode = lastNode.prev;
        } else { // the node to be deleted is in the interior of the list
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    /**
     * Getter for the last node in the doubly linked list.
     */
    public Node<K, V> getLastNode() {
        return lastNode;
    }
}
