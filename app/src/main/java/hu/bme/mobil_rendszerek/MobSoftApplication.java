package hu.bme.mobil_rendszerek;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.repository.Repository;
import hu.bme.mobil_rendszerek.ui.UIModule;

/**
 * Created by mobsoft on 2017. 03. 20..
 */

public class MobSoftApplication extends android.app.Application {

    private Tracker mTracker;
    public static MobSoftApplicationComponent injector;
    @Inject
    Repository repository;

    @Override
    public void onCreate() {
        super.onCreate();

        injector =
                DaggerMobSoftApplicationComponent.builder().
                        uIModule(
                                new UIModule(this)
                        ).build();
        injector.inject(this);
        repository.open(getApplicationContext());
    }

    public void setInjector(MobSoftApplicationComponent injector) {
        MobSoftApplication.injector = injector;
        injector.inject(this);
        repository.open(getApplicationContext());
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
}
