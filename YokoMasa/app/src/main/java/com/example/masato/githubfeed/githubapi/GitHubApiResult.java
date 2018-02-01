package com.example.masato.githubfeed.githubapi;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

/**
 * Created by Masato on 2018/01/31.
 */

public class GitHubApiResult {
    public boolean isSuccessful;
    public Failure failure;
    public Map<String, List<String>> header;
    public Object resultObject;
}
