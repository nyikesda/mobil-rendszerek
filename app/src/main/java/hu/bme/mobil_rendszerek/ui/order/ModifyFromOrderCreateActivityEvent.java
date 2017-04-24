package hu.bme.mobil_rendszerek.ui.order;

import hu.bme.mobil_rendszerek.model.OrderItem;

/**
 * Created by nyikes on 2017. 04. 24..
 */

public class ModifyFromOrderCreateActivityEvent {
    private OrderItem orderItem;

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
