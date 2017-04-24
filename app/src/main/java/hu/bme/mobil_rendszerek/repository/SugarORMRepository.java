package hu.bme.mobil_rendszerek.repository;

import android.content.Context;

import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import hu.bme.mobil_rendszerek.model.OrderItem;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public class SugarORMRepository implements Repository {
    @Override
    public void open(Context context) {
        SugarContext.init(context);
    }

    @Override
    public void close() {
        SugarContext.terminate();
    }

    @Override
    public List<OrderItem> getOrderItems(Integer departmentId) {
        return Select.from(OrderItem.class)
                .where(Condition.prop("department_id").eq(departmentId))
                .list();
    }

    @Override
    public void syncOrderItems(List<OrderItem> orderItems, Integer departmentId) {
        List<OrderItem> orderItemsOld = getOrderItems(departmentId);
        for (OrderItem item : orderItemsOld)
            SugarRecord.delete(item);
        SugarRecord.saveInTx(orderItems);
    }

    @Override
    public void addnewOrderItem(OrderItem orderItem) {
        SugarRecord.save(orderItem);
    }

    @Override
    public void modifyOrderItem(OrderItem orderItem) {
        deleteOrderItem(orderItem.getOrderItemId());
        addnewOrderItem(orderItem);
    }

    @Override
    public void deleteOrderItem(Integer orderItemId) {
        OrderItem oldItem = Select.from(OrderItem.class)
                .where(Condition.prop("order_item_id").eq(orderItemId))
                .first();
        SugarRecord.delete(oldItem);
    }
}
