package com.invisee.finvest.ui.fragments.catalogue;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.adapters.rv.FundAllocationAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by fajarfatur on 1/22/16.
 */
@RuntimePermissions
public class FundAllocationFragment extends BaseFragment {

    public static final String TAG = FundAllocationFragment.class.getSimpleName();
    private static final String PACKAGES = "packages";

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;

    private FundAllocationPresenter presenter;

    @State
    Packages packages;
    @State
    public FundAllocationResponse response;


    public static Fragment getFragment(Packages packages) {
        Fragment f = new FundAllocationFragment();

        Bundle extras = new Bundle();
        extras.putSerializable(PACKAGES, packages);

        f.setArguments(extras);
        return f;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_fund_allocation;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (packages == null) {
            Bundle extras = getArguments();
            if (extras != null && extras.containsKey(PACKAGES)) {
                packages = (Packages) extras.getSerializable(PACKAGES);

            }
        }

        if (packages != null) {
            initRV();
            presenter = new FundAllocationPresenter(this);
            presenter.fundAllocation(packages);
        }

    }

    private void initRV() {
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);
//        rv.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.SimpleOnItemClickListener() {
//            @Override
//            public void onItemClick(View childView, int position) {
//                super.onItemClick(childView, position);
//                ApprovalDetailActivity.startActivity(ApprovalListActivity.this, (AvmList) childView.getTag());
//            }
//        }));
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void downloadfundfact() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(InviseeService.IMAGE_DOWNLOAD_URL + response.getData().get(0).getFundFactSheetKey() + "&token=" + PrefHelper.getString(PrefKey.TOKEN)));
        request.setDescription("A download package with some files");
        request.setTitle("Fund Fact Sheet " + response.getData().get(0).getProductName());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Fund Fact Sheet " + response.getData().get(0).getProductName()+".pdf");

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void downloadproscpectus() {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(InviseeService.IMAGE_DOWNLOAD_URL + response.getData().get(0).getProspectusKey() + "&token=" + PrefHelper.getString(PrefKey.TOKEN)));
        request.setDescription("A download package with some files");
        request.setTitle("Prospektus " + response.getData().get(0).getProductName());
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Prospektus " + response.getData().get(0).getProductName()+".pdf");

        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }


    public void showLoading() {
        progressbar.setVisibility(View.VISIBLE);
        rv.setVisibility(View.GONE);
    }

    public void hideLoading() {
        progressbar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);
    }

    public void loadList() {
        rv.setAdapter(new FundAllocationAdapter(response.getData()));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FundAllocationFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnClick(R.id.imgdownloadpros)
    void downloadprosClick() {
        FundAllocationFragmentPermissionsDispatcher.downloadproscpectusWithCheck(this);
    }

    @OnClick(R.id.imgdownloadfund)
    void downloadfundClick() {
        FundAllocationFragmentPermissionsDispatcher.downloadfundfactWithCheck(this);
    }

}
