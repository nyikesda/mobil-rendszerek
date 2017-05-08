package hu.bme.mobil_rendszerek.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import hu.bme.mobil_rendszerek.BuildConfig;
import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.model.OrderItem;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.ui.order.OrderItemsAdapter;
import hu.bme.mobil_rendszerek.ui.order.OrderPresenter;
import hu.bme.mobil_rendszerek.ui.order.OrderScreen;
import hu.bme.mobil_rendszerek.utils.RobolectricDaggerTestRunner;

import static hu.bme.mobil_rendszerek.TestHelper.setTestInjector;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by nyikes on 2017. 05. 08..
 */

@RunWith(RobolectricDaggerTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class OrderTest {
    private OrderPresenter orderPresenter;
    private OrderItem orderItem;
    private OrderScreen orderScreen;

    @Before
    public void setup() throws Exception {
        setTestInjector();
        orderPresenter = new OrderPresenter();
        User user = new User();
        user.setCredential("sabala");
        MobSoftApplication application = (MobSoftApplication) RuntimeEnvironment.application;

        orderPresenter.setUser(user);
        orderPresenter.setOrderItemsAdapter(new OrderItemsAdapter(new ArrayList<OrderItem>(), application.getApplicationContext(), new OrderItemsAdapter.DeleteListener() {
            @Override
            public void onDeleted(OrderItem orderItem) {

            }
        }));
        orderItem = new OrderItem();
        orderItem.setProductName("product");
        orderItem.setCount(10);
        orderItem.setCost(1000);

        orderScreen = mock(OrderScreen.class);
        orderPresenter.attachScreen(orderScreen);
    }

    @Test
    public void modifyOrderItem(){
        orderPresenter.modifyOrderItem(orderItem);
        ArgumentCaptor<String> information = ArgumentCaptor.forClass(String.class);
        verify(orderScreen, times(1)).showNetworkInformation(information.capture());
        String capturedInformation = information.getValue();
        assertEquals("Sikeres módosítás", capturedInformation);
    }

    @Test
    public void deleteOrderItem(){
        orderPresenter.deleteOrderItem(orderItem);
        ArgumentCaptor<String> information = ArgumentCaptor.forClass(String.class);
        verify(orderScreen, times(1)).showNetworkInformation(information.capture());
        String capturedInformation = information.getValue();
        assertEquals("Sikeres törlés", capturedInformation);
    }

    @Test
    public void addOrderItem(){
        orderPresenter.createOrderItem(orderItem);
        ArgumentCaptor<String> information = ArgumentCaptor.forClass(String.class);
        verify(orderScreen, times(1)).showNetworkInformation(information.capture());
        String capturedInformation = information.getValue();
        assertEquals("Sikeres mentés", capturedInformation);
    }

    @After
    public void tearDown() {
        orderPresenter.detachScreen();
    }
}
