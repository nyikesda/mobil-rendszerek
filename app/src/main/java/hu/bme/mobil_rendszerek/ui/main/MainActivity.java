package hu.bme.mobil_rendszerek.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.R;
import hu.bme.mobil_rendszerek.model.Credential;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.network.Privilege;
import hu.bme.mobil_rendszerek.ui.department.DepartmentActivity;
import hu.bme.mobil_rendszerek.ui.order.OrderActivity;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements MainScreen {
    public static final String KEY_LOGOUT = "KEY_LOGOUT";
    public static final String KEY_DEPARTMENTID = "departmentID";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PRIVILEGE = "privilege";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_FIRSTNAME = "firstName";

    @Inject
    Tracker mTracker;
    @Inject
    MainPresenter mainPresenter;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    RelativeLayout main;
    SharedPreferences sharedPref;
    Credential credential;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        main = (RelativeLayout) findViewById(R.id.activity_main);
        ButterKnife.bind(this);
        MobSoftApplication.injector.inject(this);
        sharedPref = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
    }

    @OnClick(R.id.login)
    void login() {
        if (username.getText().toString().equals("") && credential != null) {
            mainPresenter.login(credential.getUsername(), credential.getPassword());
        } else {
            mainPresenter.login(username.getText().toString(), password.getText().toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.attachScreen(this);

        if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean(KEY_LOGOUT)) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.commit();
            getIntent().removeExtra(KEY_LOGOUT);
        } else {
            String userName = sharedPref.getString(KEY_USERNAME, null);
            String password = sharedPref.getString(KEY_PASSWORD, null);
            if (userName != null && password != null) {
                credential = new Credential();
                credential.setUsername(userName);
                credential.setPassword(password);
                login();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mainPresenter.detachScreen();
    }


    @Override
    public void showNetworkInformation(String text) {
        Snackbar.make(main, text, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void offlineStart(Credential credential) {
        if (this.credential == null){
            showNetworkInformation(getString(R.string.server_not_running));
            return;
        }
        Integer privilege = sharedPref.getInt(KEY_PRIVILEGE, -1);
        if (credential.getUsername().equals(this.credential.getUsername()) &&
                credential.getPassword().equals(this.credential.getPassword())) {
            User user = new User();
            user.setPrivilege(privilege);
            user.setDepartmentId(sharedPref.getInt(KEY_DEPARTMENTID, -1));
            user.setFirstName(sharedPref.getString(KEY_FIRSTNAME, null));
            user.setLastName(sharedPref.getString(KEY_LASTNAME, null));
            showNextActivityDependsOnPrivilege(user, credential);
        } else {
            showNetworkInformation(getString(R.string.login_offline_failed));
        }

    }

    @Override
    public void showNextActivityDependsOnPrivilege(User user, Credential credential) {

        if (user.getDepartmentId() != null){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(KEY_USERNAME, credential.getUsername());
            editor.putString(KEY_PASSWORD, credential.getPassword());
            editor.putInt(KEY_PRIVILEGE, user.getPrivilege());
            editor.putInt(KEY_DEPARTMENTID, user.getDepartmentId());
            editor.putString(KEY_LASTNAME, user.getLastName());
            editor.putString(KEY_FIRSTNAME, user.getFirstName());
            editor.commit();
        }

        if (user.getUserId() == null && user.getPrivilege() == Privilege.administrator.getValue()) {
            showNetworkInformation(getString(R.string.offline_mode_text));
            return;
        }

        if (user.getPrivilege() == Privilege.administrator.getValue()) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Start")
                    .setAction("Department screen start")
                    .setValue(1)
                    .build());
            Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
            intent.putExtra(OrderActivity.KEY_USER, user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (user.getPrivilege() == Privilege.purveyor.getValue()) {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Start")
                    .setAction("Order screen start")
                    .setValue(1)
                    .build());
            Intent intent = new Intent(MainActivity.this, OrderActivity.class);
            intent.putExtra(OrderActivity.KEY_USER, user);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            showNetworkInformation(getString(R.string.non_administrator_or_purveyor));
        }

    }
}