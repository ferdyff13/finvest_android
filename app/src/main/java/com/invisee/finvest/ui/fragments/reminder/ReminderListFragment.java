package com.invisee.finvest.ui.fragments.reminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Reminder;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.ReminderActivity;
import com.invisee.finvest.ui.adapters.rv.ReminderAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class ReminderListFragment extends BaseFragment {

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.llReminder)
    LinearLayout llReminder;
    @Bind(R.id.llNoReminder)
    LinearLayout llNoReminder;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;

    public static final String TAG = ReminderListFragment.class.getSimpleName();

    private ReminderListPresenter presenter;
    public List<Reminder> reminderList;
    private ReminderAdapter adapter;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, new ReminderListFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_reminder_list;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ReminderListPresenter(this);
        setHasOptionsMenu(true);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getReminderList();
        initRV();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        inflater.inflate(R.menu.menu_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            ReminderActivity.startActivity((BaseActivity) getActivity());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void initRV() {
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);
    }

    void noReminder(boolean b) {
        llNoReminder.setVisibility(b ? View.VISIBLE : View.GONE);
        llReminder.setVisibility(b ? View.GONE : View.VISIBLE);
    }

    public void loadList() {
        adapter = new ReminderAdapter(getActivity(), reminderList, presenter);
        rv.setAdapter(adapter);
    }

    public void showProgressBar(){
        pbLoading.setVisibility(View.VISIBLE);
        lnConnectionError.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
    }

    public void dismissProgressBar(){
        lnProgressBar.setVisibility(View.GONE);
        lnDismissBar.setVisibility(View.VISIBLE);
    }

    public void connectionError() {
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        lnConnectionError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvTryAgain)
    void retryConnection() {
        presenter.getReminderList();
    }

}
