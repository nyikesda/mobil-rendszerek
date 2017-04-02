package hu.bme.mobil_rendszerek.interactor.login;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.interactor.login.event.LoginEvent;
import hu.bme.mobil_rendszerek.network.LoginAPI;

/**
 * Created by nyikes on 2017. 04. 02..
 */

public class LoginInteractor {
    @Inject
    LoginAPI loginAPI;

    public LoginInteractor(){
        MobSoftApplication.injector.inject(this);
    }

    public void login(String userName, String password){
        LoginEvent event = new LoginEvent();
        //TODO initialize call
        try{
            EventBus.getDefault().post(event);
        } catch (Exception e){
            event.setThrowable(e);
            EventBus.getDefault().post(event);
        }
    }
}
