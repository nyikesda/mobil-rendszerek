package hu.bme.mobil_rendszerek.interactor.orders.events;

import java.util.List;

import hu.bme.mobil_rendszerek.model.OrderItem;

/**
 * Created by nyikes on 2017. 04. 19..
 */

public class GetOrderItemsEvent extends OrderEvent {
    private List<OrderItem> orderItems;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
