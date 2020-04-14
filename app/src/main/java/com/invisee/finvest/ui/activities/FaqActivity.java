package com.invisee.finvest.ui.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.support.SupportFragment;

import butterknife.Bind;

/**
 * Created by pandu.abbiyuarsyah on 08/03/2017.
 */

public class FaqActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    @Override
    protected int getLayout() {
        return R.layout.a_faq;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportFragment.showFragment(this);
    }

}
