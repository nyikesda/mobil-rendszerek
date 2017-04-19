package hu.bme.mobil_rendszerek.interactor.orders.events;

/**
 * Created by nyikes on 2017. 04. 20..
 */

public class BaseEvent {
    private int code;
    private Throwable throwable;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
