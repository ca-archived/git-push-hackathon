package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.Repository;

/**
 * Created by Masato on 2018/01/27.
 */

public interface RepoView extends BaseView {

    public void showRepo(Repository repository);

    public void showReadMe(String readMeHtml);

    public void setStarActivated(boolean activated);

    public void setWatchActivated(boolean activated);
}
