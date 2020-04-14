package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Reminder;
import com.invisee.finvest.ui.fragments.reminder.ReminderAddFragment;
import com.invisee.finvest.ui.fragments.reminder.ReminderEditFragment;
import com.invisee.finvest.ui.fragments.reminder.ReminderListFragment;

import butterknife.Bind;
import icepick.State;

public class ReminderActivity extends BaseActivity {


    private static final String REMINDER = "Reminder";


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;

    @State
    public Reminder reminder;

    public static void startActivity(BaseActivity sourceActivity, Reminder reminder) {

        Intent intent = new Intent(sourceActivity, ReminderActivity.class);
        intent.putExtra(REMINDER, reminder);
        sourceActivity.startActivity(intent);

    }

    public static void startActivity(BaseActivity sourceActivity) {

        Intent intent = new Intent(sourceActivity, ReminderActivity.class);
        sourceActivity.startActivity(intent);

    }

    @Override
    protected int getLayout() {
        return R.layout.a_reminder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }

        title.setText(R.string.reminderTitle);

        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(REMINDER))
            ReminderEditFragment.showFragment(this, (Reminder) getIntent().getExtras().getSerializable(REMINDER));
        else
            ReminderAddFragment.showFragment(this, null);

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        ReminderListActivity.startActivity(this);
        ReminderActivity.this.finish();
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
