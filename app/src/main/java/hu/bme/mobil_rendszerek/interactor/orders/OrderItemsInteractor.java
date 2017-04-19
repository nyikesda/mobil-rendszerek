package hu.bme.mobil_rendszerek.interactor.orders;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.interactor.orders.events.GetOrderItemsEvent;
import hu.bme.mobil_rendszerek.interactor.orders.events.SaveOrderItemEvent;
import hu.bme.mobil_rendszerek.model.OrderItem;
import hu.bme.mobil_rendszerek.network.API;
import hu.bme.mobil_rendszerek.repository.Repository;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by nyikes on 2017. 04. 19..
 */

public class OrderItemsInteractor {
    @Inject
    API networkAPI;
    @Inject
    Repository repository;

    public OrderItemsInteractor() {
        MobSoftApplication.injector.inject(this);
    }

    public void saveOrderItem(OrderItem orderItem, String credential) {
        Call<Void> orderItemsQueryCall = networkAPI.orderItemCreate(credential, orderItem);
        SaveOrderItemEvent event = new SaveOrderItemEvent();
        event.setCredentialToken(credential);
        try {
            Response<Void> response = orderItemsQueryCall.execute();
            switch (response.code()) {
                case 409:
                    event.setMessage("Meghaladta a havi limitet");
                    break;
                case 200:
                    getOrderItemsToDepartment(orderItem.getDepartmentId(),credential);
                    break;
            }
            event.setCode(response.code());
            EventBus.getDefault().post(event);
        } catch (Exception e) {
            event.setThrowable(e);
            event.setMessage("Nem elérhető a szerver");
            EventBus.getDefault().post(event);
        }
    }

    public void getOrderItemsToDepartment(Integer departmentId, String credential) {
        Call<List<OrderItem>> orderItemsQueryCall = networkAPI.orderItemFindByDepartmentId(credential);
        GetOrderItemsEvent event = new GetOrderItemsEvent();
        event.setOrderItems(repository.getOrderItems(departmentId));
        event.setCode(200);
        event.setCredentialToken(credential);
        EventBus.getDefault().post(event);
        event = new GetOrderItemsEvent();
        event.setCredentialToken(credential);
        try {
            Response<List<OrderItem>> response = orderItemsQueryCall.execute();
            switch (response.code()){
                case 404:
                case 200:
                    repository.updateOrderItems(response.body(), departmentId);
                    break;
            }
            event.setCode(response.code());
            event.setOrderItems(response.body());
            EventBus.getDefault().post(event);
        } catch (Exception e) {
            event.setThrowable(e);
            event.setMessage("Nem elérhető a szerver");
            EventBus.getDefault().post(event);
        }
    }
}
