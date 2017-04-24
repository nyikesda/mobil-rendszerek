package hu.bme.mobil_rendszerek.ui.department;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.interactor.department.DepartmentInteractor;
import hu.bme.mobil_rendszerek.interactor.department.events.DepartmentEvent;
import hu.bme.mobil_rendszerek.interactor.department.events.DepartmentUserEvent;
import hu.bme.mobil_rendszerek.interactor.department.events.SaveNewDepartmentEvent;
import hu.bme.mobil_rendszerek.model.Department;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.ui.Presenter;

/**
 * Created by nyikes on 2017. 04. 03..
 */

public class DepartmentPresenter extends Presenter<DepartmentScreen> {
    @Inject
    Executor networkExecutor;
    @Inject
    DepartmentInteractor departmentInteractor;
    boolean[] selectedUsers;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void attachScreen(DepartmentScreen screen) {
        super.attachScreen(screen);
        MobSoftApplication.injector.inject(this);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        super.detachScreen();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final DepartmentEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.showNetworkInformation(event.getMessage());
                selectedUsers = null;
                screen.doLoginFromOffline();
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    if (event instanceof DepartmentUserEvent)
                        screen.loadUserNames(((DepartmentUserEvent) event).getUsers());
                    else if (event instanceof SaveNewDepartmentEvent) {
                        screen.showNetworkInformation("Sikeres mentés");
                        selectedUsers = null;
                        screen.clear();
                    }
                } else {
                    if (user.getCredential() == null || event.getCode() == 401) {
                        selectedUsers = null;
                        screen.doLoginFromOffline();
                        return;
                    }
                    if (event.getMessage() != null && !event.getMessage().equals(""))
                        screen.showNetworkInformation(event.getMessage());
                }
            }
        }
    }

    public void getUsers() {
        final String credential = user.getCredential();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                departmentInteractor.getUsers(credential);
            }
        });
    }

    public void saveNewDepartment(final List<Integer> userIds, final Department department) {
        final String credential = user.getCredential();
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                departmentInteractor.saveNewDepartment(credential, userIds, department);
            }
        });
    }

    public void setSelectedUsers(boolean[] selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public boolean[] getSelectedUsers() {
        return selectedUsers;
    }
}
