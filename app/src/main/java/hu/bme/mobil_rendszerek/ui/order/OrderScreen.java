package hu.bme.mobil_rendszerek.ui.order;

import java.util.List;

import hu.bme.mobil_rendszerek.model.OrderItem;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public interface OrderScreen {
    void showNetworkInformation(String message);
    void showOrderItems(List<OrderItem> orderItems);
    void doLoginFromOffline();
}
