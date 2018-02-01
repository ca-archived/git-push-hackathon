package com.example.masato.githubfeed.http;

import com.example.masato.githubfeed.http.ConnectionResult;

/**
 * Created by Masato on 2018/01/31.
 */

public interface ResultBodyConverter {
    public Object convert(ConnectionResult successfulResult);
}
