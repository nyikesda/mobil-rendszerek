package hu.bme.mobil_rendszerek.repository;

import android.content.Context;

import java.util.List;

import hu.bme.mobil_rendszerek.model.OrderItem;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public interface Repository {
    void open(Context context);
    void close();
    List<OrderItem> getOrderItems(Integer departmentId);
    void syncOrderItems(List<OrderItem> orderItems,Integer departmentId);
    void addnewOrderItem(OrderItem orderItem);
    void modifyOrderItem(OrderItem orderItem);
    void deleteOrderItem(Integer orderItemId);
}
