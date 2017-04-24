package hu.bme.mobil_rendszerek.interactor.orders.events;

import hu.bme.mobil_rendszerek.model.OrderItem;

/**
 * Created by nyikes on 2017. 04. 23..
 */

public class ModifyOrderItemEvent extends OrderEvent {
    private OrderItem orderItem;

    public OrderItem getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItem orderItem) {
        this.orderItem = orderItem;
    }
}
