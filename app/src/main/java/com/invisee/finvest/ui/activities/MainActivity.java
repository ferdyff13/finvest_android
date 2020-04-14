package com.invisee.finvest.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.pojo.Menu;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.adapters.rv.DrawerAdapter;
import com.invisee.finvest.ui.fragments.contact_us.ContactUsFragment;
import com.invisee.finvest.ui.fragments.new_dashboard.NewDashboardFragment;
import com.invisee.finvest.ui.fragments.privacypolicy.PrivacyPolicyFragment;
import com.invisee.finvest.ui.fragments.support.SupportFragment;
import com.invisee.finvest.ui.fragments.termsandcondition.TermsAndConditionFragment;
import com.invisee.finvest.util.FontsOverride;
import com.invisee.finvest.util.ui.RecyclerItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class MainActivity extends BaseActivity {

    /*Navigation Header*/

    @Bind(R.id.ivPhotoProfile)
    ImageView ivPhotoProfile;
    @Bind(R.id.ibClose)
    ImageButton ibClose;
    @Bind(R.id.tvName)
    TextView tvName;
    @Bind(R.id.tvEmail)
    TextView tvEmail;
    @Bind(R.id.txvCustomerStatus)
    TextView txvCustomerStatus;
    @Bind(R.id.txvStatus)
    TextView txvStatus;
    @Bind(R.id.tv_completeness)
    TextView tvCompleteness;
    @Bind(R.id.pb_completeness)
    ProgressBar pbCompleteness;


    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.navigationView)
    NavigationView navigationView;
    @Bind(R.id.drawer)
    DrawerLayout drawer;
    @Bind(R.id.rv)
    RecyclerView rv;
    @BindString(R.string.menu_menu)
    String menu;
    @BindString(R.string.menu_dashboard)
    String menuDashboard;
    @BindString(R.string.menu_portfolio)
    String menuPortfolio;
    @BindString(R.string.menu_wallet)
    String menuWallet;
    @BindString(R.string.menu_reminder)
    String menuReminder;
    @BindString(R.string.menu_transaction)
    String menuTransaction;
    @BindString(R.string.menu_catalogue)
    String menuCatalogue;
    @BindString(R.string.menu_support)
    String menuSupport;
    @BindString(R.string.menu_setting)
    String menuSetting;
    @BindString(R.string.menu_FAQ)
    String menuFAQ;
    @BindString(R.string.menu_terms_and_condition)
    String menuTermsAndCondition;
    @BindString(R.string.menu_privacy_policy)
    String menuPrivacyPolicy;
    @BindString(R.string.menu_contactus)
    String menuContactUs;
    @BindString(R.string.menu_user_profile)
    String menuUserProfile;
    @BindString(R.string.menu_logout)
    String menuLogout;
    @BindDrawable(R.drawable.ic_white_dashboard)
    Drawable icDashboard;
    @BindDrawable(R.drawable.ic_profile_white)
    Drawable icProfile;
    @BindDrawable(R.drawable.ic_reminder_white)
    Drawable icReminder;
    @BindDrawable(R.drawable.ic_white_terms_conditions)
    Drawable icTermsAndConditions;
    @BindDrawable(R.drawable.ic_white_contact)
    Drawable icContact;
    @BindDrawable(R.drawable.ic_wallet_white)
    Drawable icWallet;
    @BindDrawable(R.drawable.ic_transaction_white)
    Drawable icTransaction;
    @BindDrawable(R.drawable.ic_catalogue_white)
    Drawable icCatalogue;
    @BindDrawable(R.drawable.ic_support_white)
    Drawable icSupport;
    @BindDrawable(R.drawable.ic_settings_white)
    Drawable icSetting;
    @BindDrawable(R.drawable.ic_logout_white)
    Drawable icLogout;
    @BindDrawable(R.drawable.ic_white_frequently_asked_question)
    Drawable icFaq;
    @BindDrawable(R.drawable.ic_white_privacy_policy)
    Drawable icPrivacyPolicy;

    private MainPresenter presenter;

    private Menu selectedMenu;

    public static void startActivity(BaseActivity sourceActivity) {
        Intent intent = new Intent(sourceActivity, MainActivity.class);
        sourceActivity.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.a_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/FlamaRegular.otf");

        showProgressDialog(loading);
        ButterKnife.bind(this);
        setNotifCount(0);
        presenter = new MainPresenter(this);
        presenter.cartList();
        presenter.getCompleteness();

        setSupportActionBar(toolbar);
        setTitle("");

        initDrawer();
        initMenuRV();
        populateFirstMenu();
        dismissProgressDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvName.setText("Hi" + " " + PrefHelper.getString(PrefKey.FIRST_NAME));
        tvEmail.setText(PrefHelper.getString(PrefKey.EMAIL));
        setNotifCount(PrefHelper.getInt(PrefKey.CART));

        if (PrefHelper.getString(PrefKey.IMAGE).equals(null))  {
            ivPhotoProfile.setImageResource(R.drawable.photo);
        } else {
            Picasso.with(this).load(InviseeService.IMAGE_DOWNLOAD_URL + PrefHelper.getString(PrefKey.IMAGE) + "&token=" + PrefHelper.getString(PrefKey.TOKEN)).into(ivPhotoProfile);
        }

    }

    @OnClick(R.id.ibClose)
    void close() {
        drawer.closeDrawers();
    }

    @OnClick(R.id.nav_header)
    void toUserProfile() {
        UserProfileActivity.startActivity(this);
    }

    private void populateFirstMenu() {
        if (selectedMenu != null) {
            rootingMenu(selectedMenu);
        } else {
            NewDashboardFragment.showFragment(this);
        }
    }

    private void initDrawer() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (selectedMenu != null) {
                    rootingMenu(selectedMenu);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public void customerStatus() {
        txvStatus.setText("Status : ");
        if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("ACT")) {
            txvCustomerStatus.setText("Active");
        } else if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("PEN")) {
            txvCustomerStatus.setText("Pending");
        } else if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("VER")) {
            txvCustomerStatus.setText("Verified");
        }
    }

    private void initMenuRV() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new DrawerAdapter(this, initDrawerMenu()));
        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                super.onItemClick(childView, position);
                Menu menu = (Menu) childView.getTag();
                selectedMenu = menu;
                drawer.closeDrawers();
            }
        }));
    }

    private List<Menu> initDrawerMenu() {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu(R.string.menu_dashboard, icDashboard, menuDashboard));
        menuList.add(new Menu(R.string.menu_FAQ, icFaq, menuFAQ));
        menuList.add(new Menu(R.string.menu_terms_and_condition, icTermsAndConditions, menuTermsAndCondition));
        menuList.add(new Menu(R.string.menu_privacy_policy, icPrivacyPolicy, menuPrivacyPolicy));
        menuList.add(new Menu(R.string.menu_contactus, icContact, menuContactUs));
        menuList.add(new Menu(R.string.menu_logout, icLogout, menuLogout));
        return menuList;
    }

    private void rootingMenu(Menu menu) {
        if (menu == null) return;
        selectedMenu = menu;
        switch (menu.getId()) {
            case R.string.menu_dashboard:
                NewDashboardFragment.showFragment(this);
                break;
            case R.string.menu_FAQ:
                SupportFragment.showFragment(this);
                break;
            case R.string.menu_terms_and_condition:
                TermsAndConditionFragment.showFragment(this);
                break;
            case R.string.menu_privacy_policy:
                PrivacyPolicyFragment.showFragment(this);
                break;
             case R.string.menu_contactus:
                ContactUsFragment.showFragment(this);
                break;
            case R.string.menu_logout:
                new MaterialDialog.Builder(this)
                        .iconRes(R.mipmap.ic_launcher_finvest)
                        .backgroundColor(cDanger)
                        .title(getString(R.string.logout).toUpperCase())
                        .titleColor(Color.WHITE)
                        .content(R.string.are_u_sure)
                        .contentColor(Color.WHITE)
                        .positiveText(R.string.yes)
                        .positiveColor(Color.WHITE)
                        .negativeText(R.string.no)
                        .negativeColor(cGrey)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                dialog.dismiss();
                                presenter.logout();
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            if (currentFragment instanceof NewDashboardFragment) {
                    Snackbar snackbar = Snackbar.make(MainActivity.this.findViewById(android.R.id.content), R.string.exit_apps, Snackbar.LENGTH_LONG).setAction(R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                View view = snackbar.getView();
                TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_action);
                tv.setTypeface(null, Typeface.BOLD);
                snackbar.show();
            } else {
                NewDashboardFragment.showFragment(this);
            }
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

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }



}
