package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.News;
import com.invisee.finvest.ui.fragments.news.DetailNewsFragment;

import butterknife.Bind;

/**
 * Created by fajarfatur on 3/11/16.
 */
public class NewsActivity extends BaseActivity {

    private static final String NEWS = "news";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    private News news;

    public static void startActivity(BaseActivity sourceActivity, News news) {
        Intent intent = new Intent(sourceActivity, NewsActivity.class);
        intent.putExtra(NEWS, news);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_news;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        news = (News) getIntent().getSerializableExtra(NEWS);
        title.setText(news.getNewsTitle());
        DetailNewsFragment.showFragment(this, news);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
