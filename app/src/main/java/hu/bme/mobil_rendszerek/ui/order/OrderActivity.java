package hu.bme.mobil_rendszerek.ui.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.R;

public class OrderActivity extends AppCompatActivity implements OrderScreen{

    @Inject
    OrderPresenter orderPresenter;

    @Override
    protected void onStart() {
        super.onStart();
        orderPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        orderPresenter.detachScreen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        MobSoftApplication.injector.inject(this);
    }
}
