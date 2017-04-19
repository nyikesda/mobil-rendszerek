package hu.bme.mobil_rendszerek.interactor.department.events;

import java.util.List;

import hu.bme.mobil_rendszerek.model.User;

/**
 * Created by nyikes on 2017. 04. 22..
 */

public class DepartmentUserEvent extends DepartmentEvent {
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
