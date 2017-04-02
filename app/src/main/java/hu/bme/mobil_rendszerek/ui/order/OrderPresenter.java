package hu.bme.mobil_rendszerek.ui.order;

import org.greenrobot.eventbus.EventBus;

import hu.bme.mobil_rendszerek.ui.Presenter;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public class OrderPresenter extends Presenter<OrderScreen> {
    @Override
    public void attachScreen(OrderScreen screen) {
        super.attachScreen(screen);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }
}
