package cachingSystem.classes;

import dataStructures.classes.Pair;

/**
 * Class that adapts the FIFOCache class to the ObservableCache abstract class.
 */
public class ObservableFIFOCache<K, V> extends ObservableCache<K, V> {
    // Composition relationship between FIFOCache and ObservableFIFOCache
    private FIFOCache<K, V> cache = new FIFOCache<K, V>();

    /**
     * {@inheritDoc}
     */
    @Override
    public V get(final K key) {
        if (cache.get(key) == null) {
            cacheListener.onMiss(key);
        }

        cacheListener.onHit(key);

        return cache.get(key);

        // TODO Auto-generated method stub
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(final K key, final V value) {
        cacheListener.onPut(key, value);

        cache.put(key, value);

        clearStaleEntries();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return cache.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public V remove(final K key) {
        return cache.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearAll() {
        cache.clearAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<K, V> getEldestEntry() {
        return cache.getEldestEntry();
    }
}
