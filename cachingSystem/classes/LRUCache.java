package cachingSystem.classes;

import java.util.HashMap;

import dataStructures.classes.GenericDoublyLinkedList;
import dataStructures.classes.Node;
import dataStructures.classes.Pair;

/**
 * This cache is very similar to the FIFOCache, but guarantees O(1) complexity for the get, put and
 * remove operations.
 */
public class LRUCache<K, V> extends ObservableCache<K, V> {
    // A hash map and a doubly linked list guarantees the O(1) complexity for get, put and remove
    protected GenericDoublyLinkedList<K, V> cacheList = new GenericDoublyLinkedList<K, V>(this);
    protected HashMap<K, Node<K, V>> cacheMap = new HashMap<K, Node<K, V>>();

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(final K key) {
        if (cacheMap.get(key) == null) {
            cacheListener.onMiss(key);
        }

        cacheListener.onHit(key);
        cacheList.pushNodeToFront(key);

        return cacheList.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(final K key, final V value) {
        cacheListener.onPut(key, value);
        cacheList.updateNode(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return cacheMap.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(final K key) {
        cacheList.remove(key);

        return cacheList.get(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearAll() {
         cacheList = new GenericDoublyLinkedList<K, V>(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<K, V> getEldestEntry() {
        return cacheList.getLastNode().pair;
    }

    /**
     * Getter pentru cacheMap.
     */
    public HashMap<K, Node<K, V>> getCacheMap() {
        return cacheMap;
    }
}
