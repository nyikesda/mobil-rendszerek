package hu.bme.mobil_rendszerek;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.mobil_rendszerek.interactor.InteractorModule;
import hu.bme.mobil_rendszerek.interactor.login.LoginInteractor;
import hu.bme.mobil_rendszerek.network.NetworkModule;
import hu.bme.mobil_rendszerek.ui.UIModule;
import hu.bme.mobil_rendszerek.ui.deparment.DepartmentActivity;
import hu.bme.mobil_rendszerek.ui.main.MainActivity;
import hu.bme.mobil_rendszerek.ui.main.MainPresenter;
import hu.bme.mobil_rendszerek.ui.order.OrderActivity;

/**
 * Created by mobsoft on 2017. 03. 20..
 */

@Singleton
@Component(modules = {UIModule.class, NetworkModule.class, InteractorModule.class})
public interface MobSoftApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(LoginInteractor loginInteractor);
    void inject(MainPresenter mainPresenter);
    void inject(OrderActivity orderActivity);
    void inject(DepartmentActivity departmentActivity);
}
