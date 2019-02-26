package observerPattern.classes;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import observerPattern.interfaces.CacheListener;

/**
 * The KeyStatsListener collects key-level stats for cache operations.
 *
 * @param <K>
 * @param <V>
 */
public class KeyStatsListener<K, V> implements CacheListener<K, V> {
    /**
     * Internal class that implements a comparator for two entries of type <K, int>
     *
     * The comparator is required for sorting the lists of keys by the total number of hits,
     * misses and updates.
     *
     * Because the comparator is only used in this class, it is not worth it to implement
     * the comparator in a new file and so I chose to make it an internal class.
     */
    class EntryComparator implements Comparator<Map.Entry<K, Integer>> {
        @Override
        public int compare(final Entry<K, Integer> first, final Entry<K, Integer> second) {
            if (first.getValue() == second.getValue()) {
                return 0;
            } else if (first.getValue() < second.getValue()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    // HashMaps for storing the number of hits, misses and updates for each key
    protected HashMap<K, Integer> cacheHits = new HashMap<K, Integer>();
    protected HashMap<K, Integer> cacheMisses = new HashMap<K, Integer>();
    protected HashMap<K, Integer> cacheUpdates = new HashMap<K, Integer>();

    /**
     * Get the number of hits for a key.
     *
     * @param key the key
     * @return number of hits
     */
    public int getKeyHits(final K key) {
        if (cacheHits.containsKey(key)) {
            return cacheHits.get(key);
        } else {
            return 0;
        }
    }

    /**
     * Get the number of misses for a key.
     *
     * @param key the key
     * @return number of misses
     */
    public int getKeyMisses(final K key) {
        if (cacheMisses.containsKey(key)) {
            return cacheMisses.get(key);
        } else {
            return 0;
        }
    }

    /**
     * Get the number of updates for a key.
     *
     * @param key the key
     * @return number of updates
     */
    public int getKeyUpdates(final K key) {
        if (cacheUpdates.containsKey(key)) {
            return cacheUpdates.get(key);
        } else {
            return 0;
        }
    }

    /**
     * Get the @top most hit keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopHitKeys(final int top) {
        LinkedList<HashMap.Entry<K, Integer>> keyEntriesList;
        keyEntriesList = new LinkedList<HashMap.Entry<K, Integer>>(cacheHits.entrySet());

        return createTopList(keyEntriesList, top);
    }

    /**
     * Get the @top most missed keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopMissedKeys(final int top) {
        LinkedList<HashMap.Entry<K, Integer>> keyEntriesList;
        keyEntriesList = new LinkedList<HashMap.Entry<K, Integer>>(cacheMisses.entrySet());

        return createTopList(keyEntriesList, top);
    }

    /**
     * Get the @top most updated keys.
     *
     * @param top number of top keys
     * @return the list of keys
     */
    public List<K> getTopUpdatedKeys(final int top) {
        LinkedList<HashMap.Entry<K, Integer>> keyEntriesList;
        keyEntriesList = new LinkedList<HashMap.Entry<K, Integer>>(cacheUpdates.entrySet());

        return createTopList(keyEntriesList, top);
    }

    /**
     * Utility method for creating a list for the top n hit/missed/updated keys.
     */
    private List<K> createTopList(final LinkedList<HashMap.Entry<K, Integer>> list, final int n) {
        Collections.sort(list, new EntryComparator());

        LinkedList<K> topList = new LinkedList<K>();

        for (int i = 0; i < n; i++) {
            topList.add(list.get(i).getKey());
        }

        return topList;
    }

    /**
     * Update the hits number for key.
     */
    @Override
    public void onHit(final K key) {
        int nrHits = 1;

        if (cacheHits.containsKey(key)) {
            nrHits = cacheHits.get(key) + 1;
        }

        cacheHits.put(key, nrHits);
    }

    /**
     * Update the misses number for key.
     */
    @Override
    public void onMiss(final K key) {
        int nrMisses = 1;

        if (cacheMisses.containsKey(key)) {
            nrMisses = cacheMisses.get(key) + 1;
        }

        cacheMisses.put(key, nrMisses);
    }

    /**
     * Update the puts (updates) number for key.
     */
    @Override
    public void onPut(final K key, final V value) {
        int nrUpdates = 1;

        if (cacheUpdates.containsKey(key)) {
            nrUpdates = cacheUpdates.get(key) + 1;
        }

        cacheUpdates.put(key, nrUpdates);
    }
}
