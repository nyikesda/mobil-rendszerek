package hu.bme.mobil_rendszerek.interactor.login;

import hu.bme.mobil_rendszerek.interactor.BaseEvent;
import hu.bme.mobil_rendszerek.model.Credential;
import hu.bme.mobil_rendszerek.model.User;

/**
 * Created by nyikes on 2017. 04. 21..
 */

public class LoginEvent extends BaseEvent {
    private User user;
    private Credential credential;

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
