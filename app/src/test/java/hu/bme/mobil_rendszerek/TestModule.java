package hu.bme.mobil_rendszerek;

import android.content.Context;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.mobil_rendszerek.ui.UIModule;
import hu.bme.mobil_rendszerek.ui.department.DepartmentPresenter;
import hu.bme.mobil_rendszerek.ui.main.MainPresenter;
import hu.bme.mobil_rendszerek.ui.order.OrderPresenter;
import hu.bme.mobil_rendszerek.utils.UiExecutor;

@Module
public class TestModule {

	private final hu.bme.mobil_rendszerek.ui.UIModule UIModule;

	public TestModule(Context context) {
		this.UIModule = new UIModule(context);
	}

	@Provides
	public Context provideContext() {
		return UIModule.provideContext();
	}


	@Provides
	public MainPresenter provideMainPresenter() {
		return UIModule.provideMainPresenter();
	}

	@Provides
	public DepartmentPresenter provideDepartmentPresenter() {
		return UIModule.provideDepartmentPresenter();
	}

	@Provides
	public OrderPresenter provideOrderPresenter() {
		return UIModule.provideOrderPresenter();
	}

	@Provides
	@Singleton
	public Executor provideNetworkExecutor() {
		return new UiExecutor();
	}


}
