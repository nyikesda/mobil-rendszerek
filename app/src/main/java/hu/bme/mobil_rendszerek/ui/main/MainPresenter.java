package hu.bme.mobil_rendszerek.ui.main;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.interactor.login.LoginEvent;
import hu.bme.mobil_rendszerek.interactor.login.LoginInteractor;
import hu.bme.mobil_rendszerek.ui.Presenter;

/**
 * Created by mobsoft on 2017. 03. 20..
 */

public class MainPresenter extends Presenter<MainScreen> {

    @Inject
    Executor networkExecutor;
    @Inject
    LoginInteractor loginInteractor;

    public void login(final String username, final String password){
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                loginInteractor.login(username,password);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final LoginEvent event) {
        if (event.getThrowable() != null) {
            event.getThrowable().printStackTrace();
            if (screen != null) {
                screen.offlineStart(event.getCredential());
            }
        } else {
            if (screen != null) {
                if (event.getCode() == 200) {
                    screen.showNextActivityDependsOnPrivilege(event.getUser(),event.getCredential());
                } else {
                    screen.showNetworkInformation(event.getMessage());
                }
            }
        }
    }

    @Override
    public void attachScreen(MainScreen screen) {
        super.attachScreen(screen);
        MobSoftApplication.injector.inject(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachScreen() {
        EventBus.getDefault().unregister(this);
        super.detachScreen();
    }
}