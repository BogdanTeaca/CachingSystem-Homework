package cachingSystem.classes;

import java.sql.Timestamp;

import cachingSystem.interfaces.CacheStalePolicy;
import dataStructures.classes.Pair;

/**
 * The TimeAwareCache offers the same functionality as the LRUCache, but also stores a timestamp for
 * each element. The timestamp is updated after each get / put operation for a key. This
 * functionality allows for time based cache stale policies (e.g. removing entries that are older
 * than 1 second).
 */
public class TimeAwareCache<K, V> extends LRUCache<K, V> {
    /**
     * {@inheritDoc}
     */
    @Override
    public V get(final K key) {
        if (cacheMap.get(key) == null) {
            cacheListener.onMiss(key);
        }

        long timeNow = System.currentTimeMillis();
        cacheMap.get(key).setTimestamp(timeNow);

        cacheListener.onHit(key);

        return cacheMap.get(key).pair.getValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(final K key, final V value) {
        cacheListener.onPut(key, value);

        cacheList.updateNode(key, value);

        long timeNow = System.currentTimeMillis();
        cacheMap.get(key).setTimestamp(timeNow);
    }

    /**
     * Get the timestamp associated with a key, or null if the key is not stored in the cache.
     *
     * @param key the key
     * @return the timestamp, or null
     */
    public Timestamp getTimestampOfKey(final K key) {
        return cacheMap.get(key).getTimestamp();
    }

    /**
     * Set a cache stale policy that should remove all elements older than @millisToExpire
     * milliseconds. This is a convenience method for setting a time based policy for the cache.
     *
     * @param millisToExpire the expiration time, in milliseconds
     */
    public void setExpirePolicy(final long millisToExpire) {
        setStalePolicy(new CacheStalePolicy<K, V>() {
            @Override
            public boolean shouldRemoveEldestEntry(final Pair<K, V> entry) {
                long timeNow = System.currentTimeMillis();
                long timeThen = getTimestampOfKey(entry.getKey()).getTime();

                return !(timeNow - timeThen > millisToExpire);
            }
        });
    }
}
