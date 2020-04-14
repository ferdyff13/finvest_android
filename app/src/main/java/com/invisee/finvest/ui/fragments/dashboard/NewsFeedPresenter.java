package com.invisee.finvest.ui.fragments.dashboard;

import com.invisee.finvest.data.api.responses.NewsFeedResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/11/16.
 */
public class NewsFeedPresenter {

    private NewsFeedFragment fragment;

    public NewsFeedPresenter(NewsFeedFragment fragment) {
        this.fragment = fragment;
    }

    void getNews(){
        fragment.getApi().getAllNews(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NewsFeedResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.hidePb();
                        fragment.srl.setRefreshing(false);
                        fragment.showMessage(fragment.connectionErrorSwipe, null);
                    }

                    @Override
                    public void onNext(NewsFeedResponse newsFeedResponse) {
                        fragment.hidePb();
                        fragment.srl.setRefreshing(false);
                        if(newsFeedResponse.getCode() == fragment.successCode){
                            fragment.hideMessage();
                            fragment.newsFeed = newsFeedResponse;
                            fragment.loadNews();
                        }else{
                            fragment.showMessage(fragment.noData, null);
                        }
                    }
                });
    }

}
