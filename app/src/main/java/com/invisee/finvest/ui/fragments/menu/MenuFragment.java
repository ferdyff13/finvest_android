package com.invisee.finvest.ui.fragments.menu;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.invisee.finvest.R;
import com.invisee.finvest.data.pojo.Menu;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.adapters.rv.MenuAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.ui.fragments.catalogue.ListOfCatalogueFragment;
import com.invisee.finvest.ui.fragments.new_dashboard.NewDashboardFragment;
import com.invisee.finvest.util.ui.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDrawable;

/**
 * Created by fajarfatur on 1/14/16.
 */
public class MenuFragment extends BaseFragment {

    public static final String TAG = MenuFragment.class.getSimpleName();

    @Bind(R.id.rv)
    RecyclerView rv;
    @BindDrawable(R.drawable.ic_wallet)
    Drawable icWallet;
    @BindDrawable(R.drawable.ic_dashboard)
    Drawable icDashboard;
    @BindDrawable(R.drawable.ic_transaction)
    Drawable icTransaction;
    @BindDrawable(R.drawable.ic_catalogue)
    Drawable icCatalogue;
    @BindDrawable(R.drawable.ic_support)
    Drawable icSupport;
    @BindDrawable(R.drawable.ic_aboutus)
    Drawable icAboutus;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, new MenuFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_menu;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    public void onResume() {
        super.onResume();
        rv.setAdapter(new MenuAdapter(getActivity(), initMenu()));
        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position) {
                super.onItemClick(childView, position);
                Menu menu = (Menu) childView.getTag();
                if(menu.getName().equalsIgnoreCase("Dashboard")){
                    NewDashboardFragment.showFragment((BaseActivity) getActivity());
                }else if(menu.getName().equalsIgnoreCase("Catalogue")){
                    ListOfCatalogueFragment.showFragment((BaseActivity) getActivity());
                }
            }
        }));
    }

    private List<Menu> initMenu() {
        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu(R.string.menu_wallet, icWallet, "Wallet"));
        menuList.add(new Menu(R.string.menu_dashboard, icDashboard, "Dashboard"));
        menuList.add(new Menu(R.string.menu_transaction, icTransaction, "Transaction"));
        menuList.add(new Menu(R.string.menu_catalogue, icCatalogue, "Catalogue"));
        menuList.add(new Menu(R.string.menu_support, icSupport, "Support"));
        menuList.add(new Menu(R.string.menu_about_us, icAboutus, "About us"));
        return menuList;
    }
}
