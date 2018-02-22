package com.example.masato.githubfeed.presenter;

import android.os.Parcelable;

import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.view.PaginatingListView;
import static com.example.masato.githubfeed.view.PaginatingListView.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Masato on 2018/01/29.
 *
 * このクラスはツイッターのように、あるスクロール地点まで行くと次のページのものを取ってくるという機能を実装したクラスです。
 * PaginatingListFragmentのonCreatePresenter()コールバックでこのクラスのインスタンスを渡すことができます。
 * 実際になんのオブジェクトのリストを持つか、RecyclerViewのリストの要素がタップされたときになんの処理を行うか、という
 * ことについては、このクラスのサブクラスが実装します。
 *
 */

public abstract class PaginatingListPresenter {

    private static final int DEFAULT_FETCH_THRESHOLD = 15;

    private PaginatingListView view;
    private ArrayList<BaseModel> elementList = new ArrayList<>();
    private int currentPage = 1;
    private int maxPage = -1;
    private int fetchThreshold = DEFAULT_FETCH_THRESHOLD;
    private boolean feedMaxedOut = false;
    private boolean refreshing = false;
    private boolean fetching = false;

    public void setFetchThreshold(int fetchThreshold) {
        this.fetchThreshold = fetchThreshold;
    }

    public void setMaxPage(int maxPage) { this.maxPage = maxPage; }

    /**
     * 現在取得が完了しているページをセットします。
     * @param currentPage 現在取得が完了しているページ。
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 現在取得が完了しているページを返します。
     * @return 現在取得が完了しているページ。
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * リストをセットします。
     * @param elementList セットするリスト。
     */
    public void setElementList(ArrayList<BaseModel> elementList) {
        this.elementList = elementList;
    }

    /**
     * リストを返します。
     * @return 持っているリスト。
     */
    public ArrayList<BaseModel> getElementList() {
        return elementList;
    }

    /**
     * ポジションの位置にあるアイテムを返します。
     * @param position リスト中のアイテムのポジション。
     * @return 該当するポジションにあるアイテム。
     */
    public BaseModel getItem(int position) {
        return elementList.get(position);
    }

    public int onGetItemCount() {
        return getItemCount();
    }

    /**
     * ポジションに応じたviewTypeを返します。リストに何もなければNOTHING_TO_SHOW_VIEW、
     * リストの最後のポジションであればLOADING_VIEW、リストの最後のポジションだがもうこれ以上次のページから
     * アイテムが取得できない場合はELEMENT_VIEWを返します。
     * @param position viewTypeを知りたいポジション。
     * @return そのポジションに応じたviewType。
     */
    public int onGetItemViewType(int position) {
        if (feedMaxedOut) {
            if (elementList.size() == 0) {
                return NOTHING_TO_SHOW_VIEW;
            }
            return onGetPaginatingItemViewType(getItem(position));
        }
        if (position == getItemCount() - 1) {
            return LOADING_VIEW;
        }
        return ELEMENT_VIEW;
    }

    abstract int onGetPaginatingItemViewType(BaseModel element);

    /**
     * PaginatingListFragmentのRecyclerViewのアイテムがクリックされたときにコールバックされます。
     * ここでクリックされたときの処理を実装します。
     * @param element クリックされたポジションのアイテム。
     */
    public abstract void onElementClicked(BaseModel element, int viewType);

    /**
     * ユーザーがある程度スクロールしたとき、またはユーザーが画面を下に引っ張って更新したときに呼ばれます。
     * そのページにあるアイテムを取得する処理を実装します。
     * @param page アイテムをとってきてほしいページ。
     */
    protected abstract void onFetchElement(int page);

    /**
     * このクラスのサブクラスはonFetchElement()でアイテムの取得をした結果をこのメソッドで通知する必要があります。
     * 取得に成功したらfetchSucceededをtrueを、失敗したらfalseを渡してください。
     * 取得に失敗した場合はelementsはnullで構いません。取得に成功したが、アイテムが0個である場合は空のリストを渡してください。
     * @param elements 取得したアイテムのリスト。
     * @param fetchSucceeded アイテムの取得が成功したかどうか。
     */
    protected void onFetchedElements(List<BaseModel> elements, boolean fetchSucceeded) {
        if (fetchSucceeded) {
            addElements(elements);
        } else {
            view.stopRefreshing();
            refreshing = false;
            fetching = false;
        }
    }

    /**
     * 取得したアイテムのリストを持っているリストに追加します。アイテムの数やフラグによって処理が変わるので
     * onFetchElement()によって取得したアイテムはこのメソッドで追加される必要があります。
     * @param elements 取得したアイテムのリスト。
     */
    private void addElements(List<BaseModel> elements) {
        if (refreshing) {
            this.elementList.clear();
            this.elementList.addAll(elements);
            view.stopRefreshing();
            refreshing = false;
        } else {
            if (elements.size() == 0) {
                feedMaxedOut = true;
            } else {
                this.elementList.addAll(elements);
                feedMaxedOut = currentPage == maxPage;
            }
        }
        view.updateAdapter();
        fetching = false;
    }

    /**
     * PaginatingListFragmentのRecyclerViewが初期化されるとき、またはユーザーが画面を下に引っ張ったときに
     * リストを初期化するために呼びます。
     */
    public void refresh() {
        view.hideErrorView();
        if (refreshing) {
            return;
        }
        currentPage = 1;
        onFetchElement(1);
        refreshing = true;
        fetching = true;
    }

    /**
     * 与えられたポジションがリストの終端に近づいていて、fetchThresholdで定められた基準を下回っているかどうかを
     * 判定します。例えばアイテムが20個ありthresholdが5の時、最後から5番目のアイテムが表示されるときに次のページ
     * からのアイテムの取得が必要だと判断され、onFetchElement()が呼ばれます。
     * @param position 現在表示されようとしているアイテムのポジション。
     */
    public void fetchElementIfNeeded(int position) {
        if (fetching) {
            return;
        }
        int remaining = getItemCount() - position;
        if (remaining < fetchThreshold && !feedMaxedOut) {
            fetching = true;
            onFetchElement(currentPage + 1);
            currentPage++;
        }
    }

    /**
     * 表示するアイテムの数を返します。リストが0個だからと言って0が帰るわけではありません。
     * リストが0個の時は、取得中であることを表すLOADING_VIEW1個か、取得が終わり本当にアイテムが0個であること
     * を表すNOTHING_TO_SHOW_VIEW1個を返します。
     * @return 表示するアイテムの数。
     */
    private int getItemCount() {
        if (feedMaxedOut) {
            if (elementList.size() == 0) {
                return 1;
            }
            return elementList.size();
        }
        return elementList.size() + 1;
    }

    PaginatingListPresenter(PaginatingListView view) {
        this.view = view;
    }
}
