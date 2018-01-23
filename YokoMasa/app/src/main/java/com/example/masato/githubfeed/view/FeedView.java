package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.FeedEntry;

import java.util.List;
import java.util.Map;

/**
 * Created by Masato on 2018/01/19.
 */

public interface FeedView extends BaseView{

    public void preparePager(Map<String, String> feedUrls);

}
