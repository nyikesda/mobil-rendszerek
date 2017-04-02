package hu.bme.mobil_rendszerek.model;

/**
 * Created by nyikes on 2017. 04. 02..
 */

public class User {
    private Privilege privilege;

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public Privilege getPrivilege() {
        return privilege;
    }
}
