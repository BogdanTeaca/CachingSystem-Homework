package observerPattern.classes;

import java.util.LinkedList;

import observerPattern.interfaces.CacheListener;

/**
 * The BroadcastListener broadcasts cache events to other listeners that have been added to it.
 */
public class BroadcastListener<K, V> implements CacheListener<K, V> {
    private LinkedList<CacheListener<K, V>> listenersList = new LinkedList<CacheListener<K, V>>();

    /**
     * Add a listener to the broadcast list.
     *
     * @param listener the listener
     */
    public void addListener(final CacheListener<K, V> listener) {
        listenersList.add(listener);
    }

    /**
     * Notify all observers about the onHit operation for key.
     */
    @Override
    public void onHit(final K key) {
        for (CacheListener<K, V> l : listenersList) {
            l.onHit(key);
        }
    }

    /**
     * Notify all observers about the onMiss operation for key.
     */
    @Override
    public void onMiss(final K key) {
        for (CacheListener<K, V> l : listenersList) {
            l.onMiss(key);
        }
    }

    /**
     * Notify all observers about the onPut operation for key.
     */
    @Override
    public void onPut(final K key, final V value) {
        for (CacheListener<K, V> l : listenersList) {
            l.onPut(key, value);
        }
    }
}
