package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.Repository;

/**
 * Created by Masato on 2018/02/02.
 */

public interface RepoOverviewView extends BaseView {
    public void showOverview(Repository repository);

    public void showReadMe(String readMeHtml);

    public void setStarActivated(boolean activated);

    public void setWatchActivated(boolean activated);
}
