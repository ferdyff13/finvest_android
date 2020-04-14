package com.invisee.finvest;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.crashlytics.android.Crashlytics;
import com.invisee.finvest.crashreport.ACRAReportSenderFactory;
import com.invisee.finvest.data.api.InviseeService;
import com.invisee.finvest.util.FontsOverride;
import com.invisee.finvest.util.LocaleUtil;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by fajarfatur on 1/12/16.
 */



@ReportsCrashes(
        formUri = "",
        reportSenderFactoryClasses = {ACRAReportSenderFactory.class})
public class InviseeApplication extends Application {

    private static InviseeApplication instance;
    private InviseeService inviseeService;
    private SharedPreferences sharedPreferences;

    public static InviseeApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        ACRA.init(this);
        LocaleUtil.setLocale(new Locale("ID"));
        LocaleUtil.updateConfig(this, getBaseContext().getResources().getConfiguration());

        instance = this;
        setupFabric();
        setupTimber();
        setupWebService();
        setupRealm();
        setupSharedPreferences();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "fonts/FlamaRegular.otf");
    }

    public InviseeService getInviseeService() {
        return inviseeService;
    }

    private void setupFabric() {
        Fabric.with(this, new Crashlytics());
    }

    private void setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
//                @Override
//                protected String crelteStackElementTag(StackTraceElement element) {
//                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
//                }
            }
            );
        }
    }

    private void setupWebService() {
        inviseeService = new InviseeService(this);
    }

    private void setupRealm() {
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    private void setupSharedPreferences() {
        this.sharedPreferences = getSharedPreferences(InviseeApplication.class.getSimpleName(),
                Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtil.updateConfig(this,newConfig);
    }
}
