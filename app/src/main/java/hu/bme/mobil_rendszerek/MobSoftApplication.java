package hu.bme.mobil_rendszerek;

import com.orm.SugarApp;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.repository.Repository;
import hu.bme.mobil_rendszerek.ui.UIModule;

/**
 * Created by mobsoft on 2017. 03. 20..
 */

public class MobSoftApplication extends SugarApp {
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
}
