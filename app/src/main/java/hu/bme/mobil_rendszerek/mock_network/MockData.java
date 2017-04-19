package hu.bme.mobil_rendszerek.mock_network;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hu.bme.mobil_rendszerek.model.Credential;
import hu.bme.mobil_rendszerek.model.Department;
import hu.bme.mobil_rendszerek.model.OrderItem;
import hu.bme.mobil_rendszerek.model.User;

/**
 * Created by nyikes on 2017. 04. 22..
 */

public class MockData {
    private List<User> users;
    private List<Department> departments;
    private List<Credential> credentials;
    private List<OrderItem> orderItems;

    private int orderItemId = 0;
    private int departmentId =0;
    private Gson gson;

    public MockData(){
        gson = new Gson();
        createMockUsersAndCredentials();
        createDepartments();
        createMockOrderItems();
    }

    String Login(Credential c){
        Credential credential = null;
        for (Credential f : credentials)
            if (f.getUsername().equals(c.getUsername()) && f.getPassword().equals(c.getPassword())) {
                credential = f;
                break;
            }
        if (credential == null)
            return "";

        for (User u : users)
            if (u.getUsername().equals(credential.getUsername()))
                return gson.toJson(u);

        return "";
    }

    private void createDepartments() {
        departments = new ArrayList<>();
        Department d = new Department();
        d.setName("Test department");
        d.setMonthlyQuota(100000);
        d.setDepartmentId(departmentId++);
    }

    private void createMockUsersAndCredentials(){
        users = new ArrayList<>();
        credentials = new ArrayList<>();
        List<String> names = new ArrayList<String>(
                Arrays.asList("normal", "beszerzo", "admin"));
        for (String i : names){
            User u = new User();
            Credential c = new Credential();
            u.setFirstName("János");
            u.setCredential("asd");
            u.setDepartmentId(!i.equals("normal") ? 0 : null);
            u.setPrivilege(names.indexOf(i));
            u.setUsername(i);
            u.setLastName(i);
            c.setUsername(i);
            c.setPassword("asd");
            credentials.add(c);
            users.add(u);
        }
    }

    private void createMockOrderItems(){
        orderItems = new ArrayList<>();
    }
}
