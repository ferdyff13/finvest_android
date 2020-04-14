package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.fragments.activation.ActivationAccountFragment;

import butterknife.Bind;
import butterknife.BindString;

/**
 * Created by fajarfatur on 2/3/16.
 */

public class ActivationActivity extends BaseActivity {

    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;
    @BindString(R.string.activation_title)
    String activationTitle;

    private String email;
    private String password;

    public static void startActivity(BaseActivity sourceActivity, String email, String password) {
        Intent intent = new Intent(sourceActivity, ActivationActivity.class);
        intent.putExtra(EMAIL, email);
        intent.putExtra(PASSWORD, password);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_activation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title.setText(activationTitle);

        setSupportActionBar(toolbar);
        setTitle("");

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().hasExtra(EMAIL)) {
            email = getIntent().getStringExtra(EMAIL);
            password = getIntent().getStringExtra(PASSWORD);
            ActivationAccountFragment.showFragment(this, email, password);
        } else {
            Toast.makeText(this, "Can't find email to activate", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ActivationActivity.this, SignInActivity.class));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
