package hu.bme.mobil_rendszerek.ui.main;

import hu.bme.mobil_rendszerek.model.User;

/**
 * Created by mobsoft on 2017. 03. 20..
 */

public interface MainScreen {
    void showNetworkError(String text);
    void showOptionsDependsOnPrivilege(User user);
}
