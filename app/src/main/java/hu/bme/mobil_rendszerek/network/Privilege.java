package hu.bme.mobil_rendszerek.network;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public enum Privilege {
    normal(0),administrator(2), purveyor(1);
    private final int value;
    Privilege(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
