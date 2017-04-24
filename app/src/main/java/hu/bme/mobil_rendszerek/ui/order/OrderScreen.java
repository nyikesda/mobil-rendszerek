package hu.bme.mobil_rendszerek.ui.order;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public interface OrderScreen {
    void showNetworkInformation(String message);
    void refreshStop();
    void doLoginFromOffline();
}
