package hu.bme.mobil_rendszerek.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hu.bme.mobil_rendszerek.model.OrderItem;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public class MemoryRepository implements Repository {

    private static List<OrderItem> orderItems;

    @Override
    public void open(Context context) {
        orderItems = new ArrayList<>();
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setProductName("asd");
        orderItem1.setOrderItemId(5);
        orderItem1.setDepartmentId(0);
        orderItem1.setCost(500);
        orderItem1.setCount(2);
        orderItem1.setDate(new Date(System.currentTimeMillis()));
        orderItems.add(orderItem1);
    }

    @Override
    public void close() {

    }

    @Override
    public List<OrderItem> getOrderItems(Integer departmentId) {
        return orderItems;
    }

    @Override
    public void syncOrderItems(List<OrderItem> orderItems,Integer departmentId) {
        MemoryRepository.orderItems = orderItems;
    }

    @Override
    public void addnewOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    @Override
    public void modifyOrderItem(OrderItem orderItem) {
        deleteOrderItem(orderItem.getOrderItemId());
        addnewOrderItem(orderItem);
    }

    @Override
    public void deleteOrderItem(Integer orderItemId) {
        OrderItem oldItem = null;
        for (OrderItem o : orderItems)
            if (o.getOrderItemId().equals(orderItemId)){
                oldItem = o;
                break;
            }
        orderItems.remove(oldItem);
    }
}
