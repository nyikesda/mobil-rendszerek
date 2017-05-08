package hu.bme.mobil_rendszerek.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import hu.bme.mobil_rendszerek.BuildConfig;
import hu.bme.mobil_rendszerek.model.Department;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.ui.department.DepartmentPresenter;
import hu.bme.mobil_rendszerek.ui.department.DepartmentScreen;
import hu.bme.mobil_rendszerek.utils.RobolectricDaggerTestRunner;

import static hu.bme.mobil_rendszerek.TestHelper.setTestInjector;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by nyikes on 2017. 05. 08..
 */

@RunWith(RobolectricDaggerTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class DepartmentTest {
    private DepartmentPresenter departmentPresenter;

    @Before
    public void setup() throws Exception {
        setTestInjector();
        departmentPresenter = new DepartmentPresenter();
        User user = new User();
        user.setCredential("sabala");
        departmentPresenter.setUser(user);
    }

    @Test
    public void newDepartment(){
        DepartmentScreen departmentScreen = mock(DepartmentScreen.class);
        departmentPresenter.attachScreen(departmentScreen);
        List<Integer> userIds = new ArrayList<>();
        userIds.add(5);
        Department department = new Department();
        department.setMonthlyQuota(100000);
        department.setName("Best informatics");
        departmentPresenter.saveNewDepartment(userIds,department);
        ArgumentCaptor<String> information = ArgumentCaptor.forClass(String.class);
        verify(departmentScreen, times(1)).showNetworkInformation(information.capture());
        String capturedInformation = information.getValue();
        assertEquals("Sikeres mentés", capturedInformation);

    }

    @After
    public void tearDown() {
        departmentPresenter.detachScreen();
    }


}
