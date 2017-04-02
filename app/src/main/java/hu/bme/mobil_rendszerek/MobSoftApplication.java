package hu.bme.mobil_rendszerek;

import android.app.Application;

import hu.bme.mobil_rendszerek.ui.UIModule;

/**
 * Created by mobsoft on 2017. 03. 20..
 */

public class MobSoftApplication extends Application {
    public static MobSoftApplicationComponent injector;

    @Override
    public void onCreate() {
        super.onCreate();

        injector =
                DaggerMobSoftApplicationComponent.builder().
                        uIModule(
                                new UIModule(this)
                        ).build();
    }
}
