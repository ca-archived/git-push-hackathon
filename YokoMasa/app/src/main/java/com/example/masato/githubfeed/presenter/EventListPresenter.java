package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.event.Event;
import com.example.masato.githubfeed.view.EventListView;
import com.example.masato.githubfeed.view.PaginatingListView;

import java.util.List;

/**
 * Created by Masato on 2018/02/11.
 */

public class EventListPresenter extends PaginatingListPresenter {

    private String url;
    private EventListView view;

    @Override
    int onGetPaginatingItemViewType(BaseModel element) {
        return 0;
    }

    @Override
    public void onElementClicked(BaseModel element, int viewType) {
        Event event = (Event) element;
        view.startEventAction(event);
    }

    @Override
    protected void onFetchElement(int page) {
        GitHubApi.getApi().fetchEventList(url, page, this::handleResult);
    }

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<BaseModel> events = (List<BaseModel>) result.resultObject;
            onFetchedElements(events, true);
        } else {
            onFetchedElements(null, false);
        }
    }

    public EventListPresenter(PaginatingListView view, String url) {
        super(view);
        this.url = url;
        this.view = (EventListView) view;
    }
}
