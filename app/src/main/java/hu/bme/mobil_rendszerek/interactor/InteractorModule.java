package hu.bme.mobil_rendszerek.interactor;

import dagger.Module;
import dagger.Provides;
import hu.bme.mobil_rendszerek.interactor.login.LoginInteractor;

/**
 * Created by nyikes on 2017. 04. 02..
 */

@Module
public class InteractorModule {
    @Provides
    public LoginInteractor provideLoginInteractor(){
        return new LoginInteractor();
    }
}
