package hu.bme.mobil_rendszerek.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.annotation.Config;

import hu.bme.mobil_rendszerek.BuildConfig;
import hu.bme.mobil_rendszerek.model.Credential;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.network.Privilege;
import hu.bme.mobil_rendszerek.ui.main.MainPresenter;
import hu.bme.mobil_rendszerek.ui.main.MainScreen;
import hu.bme.mobil_rendszerek.utils.RobolectricDaggerTestRunner;

import static hu.bme.mobil_rendszerek.TestHelper.setTestInjector;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(RobolectricDaggerTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class LoginTest {

    private MainPresenter mainPresenter;

    @Before
    public void setup() throws Exception {
        setTestInjector();
        mainPresenter = new MainPresenter();
    }

    @Test
    public void testLogin() {
        MainScreen mainScreen = mock(MainScreen.class);
        mainPresenter.attachScreen(mainScreen);
        mainPresenter.login("beszerzo","asd");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Credential> credentialCaptor = ArgumentCaptor.forClass(Credential.class);
        verify(mainScreen, times(1)).showNextActivityDependsOnPrivilege(userCaptor.capture(),credentialCaptor.capture());

        User capturedUser = userCaptor.getValue();
        Credential credential = credentialCaptor.getValue();

        assertEquals("beszerzo", credential.getUsername());
        assertEquals("asd", credential.getPassword());
        assertEquals(Privilege.purveyor.getValue(), capturedUser.getPrivilege().intValue());
    }



    @After
    public void tearDown() {
        mainPresenter.detachScreen();
    }
}