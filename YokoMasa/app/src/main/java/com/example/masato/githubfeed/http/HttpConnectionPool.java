package com.example.masato.githubfeed.http;

import com.example.masato.githubfeed.http.HandyHttpURLConnection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

/**
 * Created by Masato on 2018/01/20.
 */

public class HttpConnectionPool {

    private ExecutorService executorService;
    private Map<String, String> defHeader = new HashMap<>();

    public void setDefHeader(String key, String val) {
        defHeader.put(key, val);
    }

    public HandyHttpURLConnection newConnection(String url) {
        HandyHttpURLConnection connection = new HandyHttpURLConnection(url, executorService);
        Set<String> keys = defHeader.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            connection.setHeader(key, defHeader.get(key));
        }
        return connection;
    }

    public HttpConnectionPool(ExecutorService executorService) {
        this.executorService = executorService;
    }

}
