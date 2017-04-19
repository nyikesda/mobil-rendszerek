package hu.bme.mobil_rendszerek.interactor;

import dagger.Module;
import dagger.Provides;
import hu.bme.mobil_rendszerek.interactor.department.DepartmentInteractor;
import hu.bme.mobil_rendszerek.interactor.login.LoginInteractor;
import hu.bme.mobil_rendszerek.interactor.orders.OrderItemsInteractor;

/**
 * Created by nyikes on 2017. 04. 19..
 */

@Module
public class InteractorModule {
    @Provides
    public OrderItemsInteractor proviOrderItemsInteractor() {
        return new OrderItemsInteractor();
    }

    @Provides
    public LoginInteractor loginInteractor() {
        return new LoginInteractor();
    }

    @Provides
    public DepartmentInteractor departmentInteractor() {
        return new DepartmentInteractor();
    }
}
