package observerPattern.classes;

import observerPattern.interfaces.CacheListener;

/**
 * The StatsListener collects hit / miss / update stats for a cache.
 *
 * @param <K>
 * @param <V>
 */
public class StatsListener<K, V> implements CacheListener<K, V> {
    // counters for total number of hits, misses and updates
    private int nrHits = 0;
    private int nrMisses = 0;
    private int nrUpdates = 0;

    /**
     * Get the number of hits for the cache.
     *
     * @return number of hits
     */
    public int getHits() {
        return nrHits;
    }

    /**
     * Get the number of misses for the cache.
     *
     * @return number of misses
     */
    public int getMisses() {
        return nrMisses;
    }

    /**
     * Get the number of updates (put operations) for the cache.
     *
     * @return number of updates
     */
    public int getUpdates() {
        return nrUpdates;
    }

    /**
     * Update the total number of hits.
     */
    @Override
    public void onHit(final K key) {
        nrHits++;
    }

    /**
     * Update the total number of misses.
     */
    @Override
    public void onMiss(final K key) {
        nrMisses++;
    }

    /**
     * Update the total number of puts (updates).
     */
    @Override
    public void onPut(final K key, final V value) {
        nrUpdates++;
    }
}
