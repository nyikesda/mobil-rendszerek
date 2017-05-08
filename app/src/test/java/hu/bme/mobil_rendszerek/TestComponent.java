package hu.bme.mobil_rendszerek;

import javax.inject.Singleton;

import dagger.Component;
import hu.bme.mobil_rendszerek.interactor.InteractorModule;
import hu.bme.mobil_rendszerek.mock_network.MockNetworkModule;
import hu.bme.mobil_rendszerek.repository.TestRepositoryModule;

@Singleton
@Component(modules = {MockNetworkModule.class, TestModule.class, InteractorModule.class, TestRepositoryModule.class})
public interface TestComponent extends MobSoftApplicationComponent {
}
