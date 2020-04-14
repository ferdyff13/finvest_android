package com.invisee.finvest.ui.fragments.support;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Faq;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.adapters.rv.FaqListAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;

import java.util.List;

import butterknife.Bind;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class SupportFragment extends BaseFragment {

    public static final String TAG = SupportFragment.class.getSimpleName();

    @Bind(R.id.rv)
    RecyclerView rv;

    private SupportPresenter presenter;
    public List<Faq> faqList;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new SupportFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_support;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.SimpleOnItemClickListener() {
//            @Override
//            public void onItemClick(View childView, int position) {
//                super.onItemClick(childView, position);
//            }
//        }));

        presenter = new SupportPresenter(this);
        presenter.getFaq();

    }

    public void loadList() {
        rv.setAdapter(new FaqListAdapter(getActivity(), faqList));
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("FAQ");
    }
}