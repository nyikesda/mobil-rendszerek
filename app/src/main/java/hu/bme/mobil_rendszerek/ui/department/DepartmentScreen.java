package hu.bme.mobil_rendszerek.ui.department;

import java.util.List;

import hu.bme.mobil_rendszerek.model.User;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public interface DepartmentScreen {
    void showNetworkInformation(String text);
    void doLoginFromOffline();
    void loadUserNames(List<User> users);
    void clear();
}
