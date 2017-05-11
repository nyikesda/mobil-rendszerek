package hu.bme.mobil_rendszerek.ui;

import android.content.Context;

import com.google.android.gms.analytics.Tracker;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.ui.department.DepartmentPresenter;
import hu.bme.mobil_rendszerek.ui.main.MainPresenter;
import hu.bme.mobil_rendszerek.ui.order.OrderPresenter;

/**
 * Created by mobsoft on 2017. 03. 20..
 */

@Module
public class UIModule {
    private Context context;

    public UIModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }

    @Provides
    @Singleton
    public DepartmentPresenter provideDepartmentPresenter() {
        return new DepartmentPresenter();
    }

    @Provides
    @Singleton
    public OrderPresenter provideOrderPresenter() {
        return new OrderPresenter();
    }

    @Provides
    @Singleton
    public Executor provideNetworkExecutor() {
        return Executors.newFixedThreadPool(5);
    }

    @Provides
    @Singleton
    public Tracker provideTracker() {
        MobSoftApplication application = (MobSoftApplication) context.getApplicationContext();
        return application.getDefaultTracker();
    }
}
