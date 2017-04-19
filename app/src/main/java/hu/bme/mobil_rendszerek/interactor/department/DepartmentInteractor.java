package hu.bme.mobil_rendszerek.interactor.department;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.interactor.department.events.DepartmentUserEvent;
import hu.bme.mobil_rendszerek.interactor.department.events.SaveNewDepartmentEvent;
import hu.bme.mobil_rendszerek.model.Department;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.network.API;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by nyikes on 2017. 04. 22..
 */

public class DepartmentInteractor {
    @Inject
    API networkAPI;

    public DepartmentInteractor(){
        MobSoftApplication.injector.inject(this);
    }

    public void getUsers(String credential){
        Call<List<User>> usersQueryCall = networkAPI.usersFindByDepartmentId(credential);
        DepartmentUserEvent event = new DepartmentUserEvent();
        event.setCredentialToken(credential);
        try {
            Response<List<User>> response = usersQueryCall.execute();
            switch (response.code()) {
                case 200:
                    event.setUsers(response.body());
                    break;
            }
            event.setCode(response.code());
            EventBus.getDefault().post(event);
        } catch (Exception e) {
            event.setThrowable(e);
            event.setMessage("Nem elérhető a szerver");
            EventBus.getDefault().post(event);
        }
    }

    public void saveNewDepartment(String credential, List<Integer> userIds, Department department){
        Call<Void> addNewDeparment = networkAPI.departmentCreate(userIds,credential,department);
        SaveNewDepartmentEvent event = new SaveNewDepartmentEvent();
        event.setCredentialToken(credential);
        try {
            Response<Void> response = addNewDeparment.execute();
            switch (response.code()) {
                case 409:
                    event.setMessage("Már létezik ilyen nevű részleg");
                    break;
            }
            event.setCode(response.code());
            EventBus.getDefault().post(event);
        } catch (Exception e) {
            event.setThrowable(e);
            event.setMessage("Nem elérhető a szerver");
            EventBus.getDefault().post(event);
        }
    }
}
