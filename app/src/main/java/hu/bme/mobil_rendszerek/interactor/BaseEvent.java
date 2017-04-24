package hu.bme.mobil_rendszerek.interactor;

/**
 * Created by nyikes on 2017. 04. 20..
 */

public class BaseEvent {
    private int code;
    private Throwable throwable;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
