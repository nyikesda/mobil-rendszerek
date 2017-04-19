package hu.bme.mobil_rendszerek.interactor.login;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.model.Credential;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.network.API;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by nyikes on 2017. 04. 21..
 */

public class LoginInteractor {
    @Inject
    API networkAPI;

    public LoginInteractor() {
        MobSoftApplication.injector.inject(this);
    }

    public void login(String username, String password){
        Credential credential = new Credential();
        credential.setUsername(username);
        credential.setPassword(password);
        Call<User> loginQueryCall = networkAPI.login(credential);
        LoginEvent event = new LoginEvent();
        event.setCredential(credential);
        try {
            Response<User> response = loginQueryCall.execute();
            switch (response.code()) {
                case 200:
                    event.setUser(response.body());
                    break;
                case 401:
                    event.setMessage("Nem megfelelő felhasználónév vagy jelszó");
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
