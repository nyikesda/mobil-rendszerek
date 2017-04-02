package hu.bme.mobil_rendszerek.ui.deparment;

import org.greenrobot.eventbus.EventBus;

import hu.bme.mobil_rendszerek.ui.Presenter;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public class DepartmentPresenter extends Presenter<DepartmentScreen> {

    @Override
    public void attachScreen(DepartmentScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }
}
