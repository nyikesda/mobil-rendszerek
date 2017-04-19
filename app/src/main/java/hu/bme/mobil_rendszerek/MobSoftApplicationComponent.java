package hu.bme.mobil_rendszerek;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.mobil_rendszerek.interactor.InteractorModule;
import hu.bme.mobil_rendszerek.interactor.department.DepartmentInteractor;
import hu.bme.mobil_rendszerek.interactor.login.LoginInteractor;
import hu.bme.mobil_rendszerek.interactor.orders.OrderItemsInteractor;
import hu.bme.mobil_rendszerek.network.NetworkModule;
import hu.bme.mobil_rendszerek.repository.RepositoryModule;
import hu.bme.mobil_rendszerek.ui.UIModule;
import hu.bme.mobil_rendszerek.ui.department.DepartmentActivity;
import hu.bme.mobil_rendszerek.ui.department.DepartmentPresenter;
import hu.bme.mobil_rendszerek.ui.main.MainActivity;
import hu.bme.mobil_rendszerek.ui.main.MainPresenter;
import hu.bme.mobil_rendszerek.ui.order.OrderActivity;
import hu.bme.mobil_rendszerek.ui.order.OrderPresenter;

/**
 * Created by mobsoft on 2017. 03. 20..
 */

@Singleton
@Component(modules = {UIModule.class, NetworkModule.class, RepositoryModule.class, InteractorModule.class})
public interface MobSoftApplicationComponent {
    void inject(MainActivity mainActivity);
    void inject(MainPresenter mainPresenter);
    void inject(OrderActivity orderActivity);
    void inject(DepartmentActivity departmentActivity);
    void inject(OrderItemsInteractor orderItemsInteractor);
    void inject(OrderPresenter orderPresenter);
    void inject(MobSoftApplication mobSoftApplication);
    void inject(LoginInteractor loginInteractor);
    void inject(DepartmentInteractor departmentInteractor);
    void inject(DepartmentPresenter departmentPresenter);
}
