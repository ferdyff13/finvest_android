package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.signIn.ForgotPasswordFragment;
import com.invisee.finvest.ui.fragments.signIn.ForgotPasswordTempFragment;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by pandu.abbiyuarsyah on 20/04/2017.
 */

public class ForgotPasswordActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;
    @BindString(R.string.forgot_pwd_title)
    String forgotPasswordTittle;

    public static void startActivity(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, ForgotPasswordActivity.class);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_forgot_password;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupToolbar();
        ForgotPasswordTempFragment.showFragment(this);
}

    public void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);
        title.setText(forgotPasswordTittle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(ForgotPasswordActivity.this, SignInActivity.class));
        ForgotPasswordActivity.this.finish();
    }

}
