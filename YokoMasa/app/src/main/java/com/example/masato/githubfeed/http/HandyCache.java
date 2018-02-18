package com.example.masato.githubfeed.http;

import android.util.Log;
import android.util.LruCache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Masato on 2018/02/18.
 */

class HandyCache {

    private static HandyCache instance;

    private LruCache<String, byte[]> cache;
    private Map<String, String> eTags = new HashMap<>();

    static HandyCache getInstance() {
        if (instance == null) {
            instance = new HandyCache();
        }
        return instance;
    }

    boolean hasCache(String url) {
        return cache.get(url) != null;
    }

    byte[] getCachedBytes(String url) {
        return cache.get(url);
    }

    void cache(String url, String eTag,  byte[] bytes) {
        cache.put(url, bytes);
        eTags.put(url, eTag);
        int cacheSizeKb = cache.size() / 1024;
        int maxCacheSizeKb = cache.maxSize() / 1024;
        Log.i("gh_feed", "cache size: " + cacheSizeKb + "/" + maxCacheSizeKb + "kb");
    }

    String getETag(String url) {
        return eTags.get(url);
    }

    private HandyCache() {
        long maxMemory = Runtime.getRuntime().maxMemory();
        int cacheSize = (int) maxMemory / 8;
        cache = new LruCache<String, byte[]>(cacheSize) {
            @Override
            protected int sizeOf(String key, byte[] value) {
                return value.length;
            }
        };
    }
}
