package hu.bme.mobil_rendszerek.repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nyikes on 2017. 04. 03..
 */

@Module
public class RepositoryModule {
    @Singleton
    @Provides
    public Repository provideRepository(){
        return new SugarORMRepository();
    }

}
