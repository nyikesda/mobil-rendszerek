package hu.bme.mobil_rendszerek.ui.deparment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.R;

public class DepartmentActivity extends AppCompatActivity implements DepartmentScreen {

    @Inject
    DepartmentPresenter departmentPresenter;

    @Override
    protected void onStart() {
        super.onStart();
        departmentPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        departmentPresenter.detachScreen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        MobSoftApplication.injector.inject(this);
    }
}
