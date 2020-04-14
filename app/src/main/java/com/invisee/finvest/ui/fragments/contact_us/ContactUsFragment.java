package com.invisee.finvest.ui.fragments.contact_us;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;

import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by pandu.abbiyuarsyah on 09/03/2017.
 */

@RuntimePermissions
public class ContactUsFragment extends BaseFragment {

    public static final String TAG = ContactUsFragment.class.getSimpleName();

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.container, new ContactUsFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_contactus;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Hubungi Kami");
    }

    @OnClick(R.id.btnPhoneLimit)
    void doCallHunting() {
        ContactUsFragmentPermissionsDispatcher.callHuntingWithCheck(this);
    }

    @OnClick(R.id.tvPhoneLimit)
    void doCallHuntingFromTv() {
        ContactUsFragmentPermissionsDispatcher.callHuntingWithCheck(this);
    }

    @OnClick(R.id.btnSendMail)
    void doSendMail(){
        sendMail();
    }

    @OnClick(R.id.tvSendMail)
    void doSendMailTv(){
        sendMail();
    }

    @OnClick(R.id.btnWhatsApp)
    void doSendWhatsapp(){
        openWhatsApp();
    }

    @OnClick(R.id.tvWhatsApp)
    void doSendWhatsappTv(){
        openWhatsApp();
    }

    @NeedsPermission(Manifest.permission.CALL_PHONE)
    void callHunting() {
        String call = "(021)344 0792";
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + call));
        try {
            startActivity(in);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
        }
    }

    void sendMail(){
        String address = "support@invisee.com";
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + address));
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, "support@invisee.com");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        try {
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ContactUsFragmentPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    private void openWhatsApp() {
        String smsNumber = "+628113340792";
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix
            sendIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(sendIntent);
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(getContext(), "WhatsApp belum terpasang", Toast.LENGTH_SHORT).show();
//            try {
//                startActivity(goToMarket);
//            } catch (android.content.ActivityNotFoundException ex) {
//                Toast.makeText(getContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
//            }
        }
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}
