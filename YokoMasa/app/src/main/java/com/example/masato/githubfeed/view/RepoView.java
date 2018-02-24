package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.Repository;

/**
 * Created by Masato on 2018/01/27.
 */

public interface RepoView extends BaseView {
    public void setUpContent(Repository repository);
}
