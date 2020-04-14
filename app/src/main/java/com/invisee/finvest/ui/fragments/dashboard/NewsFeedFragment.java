package com.invisee.finvest.ui.fragments.dashboard;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.News;
import com.invisee.finvest.data.api.responses.NewsFeedResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.NewsActivity;
import com.invisee.finvest.ui.adapters.rv.NewsFeedAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.eventBus.RxBusObject;
import com.invisee.finvest.util.ui.RecyclerItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;
import icepick.State;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class NewsFeedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = NewsFeedFragment.class.getSimpleName();

    @Bind(R.id.pb)
    ProgressBar pb;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.tvMessage)
    TextView tvMessage;
    @Bind(R.id.ivMessageIcon)
    ImageView ivMessageIcon;
    @Bind(R.id.rv)
    RecyclerView rv;
    @State
    NewsFeedResponse newsFeed;

    private NewsFeedPresenter presenter;
    private boolean activityCreated, visibleToUser;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new NewsFeedFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_news_feed;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new NewsFeedPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initRV();
        initSRL();
        if (newsFeed != null) {
            loadNews();
        } else {
            showPb();
            presenter.getNews();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityCreated = true;
        if (visibleToUser) {
            getBus().send(new RxBusObject(RxBusObject.RxBusKey.SELECTED_DASHBOARD_MENU, 2));
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            visibleToUser = true;
            if (activityCreated) {
                getBus().send(new RxBusObject(RxBusObject.RxBusKey.SELECTED_DASHBOARD_MENU, 2));
            }
        }else{
            visibleToUser = false;
        }
    }

    private void initRV() {
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                super.onItemClick(childView, position);
                News news = (News) childView.getTag();
                NewsActivity.startActivity((BaseActivity) getActivity(), news);
            }
        }));
    }

    private void initSRL() {
        srl.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        srl.setOnRefreshListener(this);
        srl.setProgressViewOffset(false, 0, 200);
    }

    public void loadNews() {
        rv.setAdapter(new NewsFeedAdapter(getActivity(), newsFeed.getData()));
    }

    @Override
    public void onRefresh() {
        newsFeed.setData(new ArrayList<News>());
        rv.getAdapter().notifyDataSetChanged();
        srl.setRefreshing(true);
        presenter.getNews();
    }

    public void showPb() {
        hideMessage();
        pb.setVisibility(View.VISIBLE);
    }

    public void hidePb() {
        pb.setVisibility(View.INVISIBLE);
    }

    public void showMessage(String message, Drawable icon) {
        tvMessage.setText(message);
        ivMessageIcon.setImageDrawable(icon);
        tvMessage.setVisibility(View.VISIBLE);
        ivMessageIcon.setVisibility(View.VISIBLE);
    }

    public void hideMessage() {
        tvMessage.setVisibility(View.INVISIBLE);
        ivMessageIcon.setVisibility(View.INVISIBLE);
    }
}
