package hu.bme.mobil_rendszerek;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.mobil_rendszerek.ui.UIModule;
import hu.bme.mobil_rendszerek.ui.main.MainActivity;

/**
 * Created by mobsoft on 2017. 03. 20..
 */

@Singleton
@Component(modules = {UIModule.class})
public interface MobSoftApplicationComponent {
    void inject(MainActivity mainActivity);

}
