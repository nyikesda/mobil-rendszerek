package hu.bme.mobil_rendszerek.ui.order;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.interactor.orders.OrderItemsInteractor;
import hu.bme.mobil_rendszerek.interactor.orders.events.DeleteOrderItemEvent;
import hu.bme.mobil_rendszerek.interactor.orders.events.GetOrderItemsEvent;
import hu.bme.mobil_rendszerek.interactor.orders.events.ModifyOrderItemEvent;
import hu.bme.mobil_rendszerek.interactor.orders.events.OrderEvent;
import hu.bme.mobil_rendszerek.interactor.orders.events.SaveOrderItemEvent;
import hu.bme.mobil_rendszerek.model.OrderItem;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.ui.Presenter;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public class OrderPresenter extends Presenter<OrderScreen> {
    @Inject
    Executor networkExecutor;
    @Inject
    OrderItemsInteractor orderItemsInteractor;

    private User user;

    private OrderItemsAdapter orderItemsAdapter;

    public OrderItemsAdapter getOrderItemsAdapter() {
        return orderItemsAdapter;
    }

    public void setOrderItemsAdapter(OrderItemsAdapter orderItemsAdapter) {
        this.orderItemsAdapter = orderItemsAdapter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void attachScreen(OrderScreen screen) {
        super.attachScreen(screen);
        MobSoftApplication.injector.inject(this);
        EventBus.getDefault().register(this);
    }

    public void refreshOrderItems() {
        final Integer departmentId = user.getDepartmentId();
        final String credential = user.getCredential();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderItemsInteractor.getOrderItemsToDepartment(departmentId, credential);
            }
        });
    }

    public void createOrderItem(final OrderItem orderItem) {
        final String credential = user.getCredential();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderItemsInteractor.saveOrderItem(orderItem, credential);
            }
        });
    }

    public void deleteOrderItem(final OrderItem orderItem) {
        final String credential = user.getCredential();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderItemsInteractor.deleteOrderItem(orderItem, credential);
            }
        });
    }

    public void modifyOrderItem(final OrderItem orderItem) {
        final String credential = user.getCredential();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                orderItemsInteractor.modifyOrderItem(orderItem, credential);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final OrderEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showNetworkInformation(event.getMessage());
                user.setCredential(null);
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200 || event.getCode() == 404) {
                    if (event instanceof GetOrderItemsEvent)
                        notifyAdapterOnScreen(OrderItemOperation.refresh,event);
                    else if (event instanceof ModifyOrderItemEvent)
                        notifyAdapterOnScreen(OrderItemOperation.modifyOrderItem,event);
                    else if (event instanceof SaveOrderItemEvent)
                        notifyAdapterOnScreen(OrderItemOperation.newOrderItem,event);
                    else if (event instanceof DeleteOrderItemEvent)
                        notifyAdapterOnScreen(OrderItemOperation.removeOrderItem,event);
                } else {
                    if (user.getCredential() == null || event.getCode() == 401) {
                        screen.doLoginFromOffline();
                        return;
                    }
                    screen.showNetworkInformation(event.getMessage());
                }
            }
        }
    }

    private void notifyAdapterOnScreen(OrderItemOperation operation, OrderEvent event){
        List<OrderItem> orderItems = new ArrayList<>();
        switch (operation) {
            case refresh:
                orderItems = ((GetOrderItemsEvent) event).getOrderItems();
                break;
            case newOrderItem:
                screen.showNetworkInformation("Sikeres mentés");
                orderItems.add(((SaveOrderItemEvent) event).getOrderItem());
                break;
            case removeOrderItem:
                screen.showNetworkInformation("Sikeres törlés");
                orderItems.add(((DeleteOrderItemEvent) event).getOrderItem());
                break;
            case modifyOrderItem:
                screen.showNetworkInformation("Sikeres módosítás");
                orderItems.add(((ModifyOrderItemEvent) event).getOrderItem());
                break;
        }
        screen.showOrderItems(orderItems, operation);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }
}
