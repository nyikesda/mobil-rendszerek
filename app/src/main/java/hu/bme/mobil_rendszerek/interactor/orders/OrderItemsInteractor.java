package hu.bme.mobil_rendszerek.interactor.orders;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.interactor.BaseEvent;
import hu.bme.mobil_rendszerek.interactor.orders.events.DeleteOrderItemEvent;
import hu.bme.mobil_rendszerek.interactor.orders.events.GetOrderItemsEvent;
import hu.bme.mobil_rendszerek.interactor.orders.events.ModifyOrderItemEvent;
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
        Call<OrderItem> orderItemsQueryCall = networkAPI.orderItemCreate(orderItem,credential);
        SaveOrderItemEvent event = new SaveOrderItemEvent();
        try {
            Response<OrderItem> response = orderItemsQueryCall.execute();
            switch (response.code()) {
                case 409:
                    event.setMessage("Meghaladta a havi limitet");
                    break;
                case 200:
                    event.setOrderItem(response.body());
                    repository.addnewOrderItem(response.body());
                    break;
            }
            eventPostSuccessMessage(response.code(),event);
        } catch (Exception e) {
            eventPostFailMessage(event,e);
        }
    }

    public void modifyOrderItem(OrderItem orderItem, String credential){
        Call<OrderItem> orderItemModify = networkAPI.orderItemModify(credential,orderItem);
        ModifyOrderItemEvent event = new ModifyOrderItemEvent();
        try {
            Response<OrderItem> response = orderItemModify.execute();
            switch (response.code()) {
                case 200:
                    repository.modifyOrderItem(response.body());
                    event.setOrderItem(response.body());
                    break;
                case 409:
                    event.setMessage("Meghaladta a havi limitet, módosítás érvénytelen");
                    break;
            }
            eventPostSuccessMessage(response.code(),event);
        } catch (Exception e) {
            eventPostFailMessage(event,e);
        }
    }

    public void deleteOrderItem(OrderItem orderItem, String credential){
        Call<Void> orderItemModify = networkAPI.orderItemDelete(orderItem.getOrderItemId(),credential);
        DeleteOrderItemEvent event = new DeleteOrderItemEvent();
        event.setOrderItem(orderItem);
        try {
            Response<Void> response = orderItemModify.execute();
            switch (response.code()) {
                case 200:
                    repository.deleteOrderItem(orderItem.getOrderItemId());
                    break;
            }
            eventPostSuccessMessage(response.code(),event);
        } catch (Exception e) {
            eventPostFailMessage(event,e);
        }
    }

    private void eventPostFailMessage(BaseEvent event, Exception e){
        event.setThrowable(e);
        event.setMessage("Nem elérhető a szerver");
        EventBus.getDefault().post(event);
    }

    private void eventPostSuccessMessage(Integer code,BaseEvent event){
        event.setCode(code);
        EventBus.getDefault().post(event);
    }

    public void getOrderItemsToDepartment(Integer departmentId, String credential) {
        Call<List<OrderItem>> orderItemsQueryCall = networkAPI.orderItemFindByDepartmentId(credential);
        GetOrderItemsEvent event = new GetOrderItemsEvent();
        event.setOrderItems(repository.getOrderItems(departmentId));
        eventPostSuccessMessage(200,event);
        event = new GetOrderItemsEvent();
        try {
            Response<List<OrderItem>> response = orderItemsQueryCall.execute();
            switch (response.code()){
                case 404:
                case 200:
                    repository.syncOrderItems(response.body(), departmentId);
                    break;
            }
            event.setOrderItems(response.body());
            eventPostSuccessMessage(response.code(),event);
        } catch (Exception e) {
            eventPostFailMessage(event,e);
        }
    }
}
