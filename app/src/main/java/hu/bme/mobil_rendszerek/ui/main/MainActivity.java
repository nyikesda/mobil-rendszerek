package hu.bme.mobil_rendszerek.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.R;
import hu.bme.mobil_rendszerek.model.Privilege;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.ui.deparment.DepartmentActivity;
import hu.bme.mobil_rendszerek.ui.order.OrderActivity;

public class MainActivity extends AppCompatActivity  implements MainScreen {

    @Inject
    MainPresenter mainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobSoftApplication.injector.inject(this);

        //TODO Login fields and button and call
        //mainPresenter.login(userName, password);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.attachScreen(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.detachScreen();
    }


    @Override
    public void showNetworkError(String text) {
        //TODO
    }

    @Override
    public void showOptionsDependsOnPrivilege(User user) {
        if (user.getPrivilege() == Privilege.administrator) {
            Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
            //intent.putExtra(KEY_ARTIST,artistSearchTerm);
            startActivity(intent);
        } else if (user.getPrivilege() == Privilege.purveyor){
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            //intent.putExtra(KEY_ARTIST,artistSearchTerm);
            startActivity(intent);
        }
    }
}