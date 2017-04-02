package hu.bme.mobil_rendszerek.interactor.login.event;

import hu.bme.mobil_rendszerek.model.User;

/**
 * Created by nyikes on 2017. 04. 02..
 */

public class LoginEvent {
    private Throwable throwable;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {

        return user;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

}
