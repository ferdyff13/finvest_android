package com.invisee.finvest.ui.fragments.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.News;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

import butterknife.Bind;

/**
 * Created by fajarfatur on 3/11/16.
 */
public class DetailNewsFragment extends BaseFragment {

    public static final String TAG = DetailNewsFragment.class.getSimpleName();
    private static final String NEWS = "news";

    @Bind(R.id.ivPhoto)
    ImageView ivPhoto;
    @Bind(R.id.tvNewsContent)
    TextView tvNewsContent;

    private News news;
    private DetailNewsPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity, News news) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(NEWS, news);
            DetailNewsFragment fragment = new DetailNewsFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_detail_news;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailNewsPresenter(this);
        news = (News) getArguments().get(NEWS);
    }

    @Override
    public void onResume() {
        super.onResume();
        tvNewsContent.setText(Html.fromHtml(news.getNewsContent()).toString());
        Glide.with(this).load(InviseeService.IMAGE_DOWNLOAD_URL + news.getImageLocation()).into(ivPhoto);
    }

}
