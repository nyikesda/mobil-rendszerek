package hu.bme.mobil_rendszerek.ui.main;

import hu.bme.mobil_rendszerek.model.Credential;
import hu.bme.mobil_rendszerek.model.User;

/**
 * Created by mobsoft on 2017. 03. 20..
 */

public interface MainScreen {
    void showNetworkInformation(String text);
    void offlineStart(Credential credential);
    void showNextActivityDependsOnPrivilege(User user, Credential credential);
}
