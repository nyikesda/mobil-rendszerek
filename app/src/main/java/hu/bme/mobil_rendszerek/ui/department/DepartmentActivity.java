package hu.bme.mobil_rendszerek.ui.department;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.R;
import hu.bme.mobil_rendszerek.model.Department;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.ui.main.MainActivity;
import io.fabric.sdk.android.Fabric;

import static hu.bme.mobil_rendszerek.ui.order.OrderActivity.KEY_USER;

public class DepartmentActivity extends AppCompatActivity implements DepartmentScreen, MultiSpinner.MultiSpinnerListener {

    @Inject
    Tracker mTracker;
    @Inject
    DepartmentPresenter departmentPresenter;

    @BindView(R.id.people_select)
    MultiSpinner multiSpinner;
    @BindView(R.id.departmentName)
    EditText departmentName;
    @BindView(R.id.monthlyQuota)
    EditText monthlyQuota;

    CoordinatorLayout coordinatorLayout;
    List<User> users;


    @Override
    protected void onResume() {
        super.onResume();
        departmentPresenter.attachScreen(this);
        departmentPresenter.getUsers();
        if (getIntent().hasExtra(KEY_USER)){
            showNetworkInformation(getString(R.string.login)+" "+departmentPresenter.getUser().getLastName() + " " + departmentPresenter.getUser().getFirstName());
            getIntent().removeExtra(KEY_USER);
        }
    }

    @Override
    public void doLoginFromOffline() {
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void loadUserNames(List<User> users) {
        List<String> userList = new ArrayList<>();
        for (User u : users) {
            userList.add(u.getFirstName() + " " + u.getLastName() + "(" + u.getUsername() + ")");
        }
        multiSpinner.setItems(userList, this, departmentPresenter.getSelectedUsers());
        this.users = users;
    }

    @Override
    public void clear() {
        monthlyQuota.setText("");
        departmentName.setText("");
        multiSpinner.setItems(new ArrayList<String>(), this, departmentPresenter.getSelectedUsers());
        departmentPresenter.getUsers();
    }


    @Override
    protected void onPause() {
        super.onPause();
        departmentPresenter.detachScreen();
    }

    @OnClick(R.id.logout)
    void logout() {
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(MainActivity.KEY_LOGOUT, true);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        MobSoftApplication.injector.inject(this);
        ButterKnife.bind(this);
        Fabric.with(this, new Crashlytics());
        coordinatorLayout = (CoordinatorLayout)
                findViewById(R.id.activity_department);

        if (getIntent().hasExtra(KEY_USER)) {
            User user = (User) getIntent().getSerializableExtra(KEY_USER);
            departmentPresenter.setUser(user);
        }
        multiSpinner.setItems(new ArrayList<String>(), this, null);
    }

    @Override
    public void onItemsSelected(boolean[] selected) {
        if (selected == null){
            showNetworkInformation(getString(R.string.empty_list_purveyor));
        } else
        departmentPresenter.setSelectedUsers(selected);
    }

    @Override
    public void showNetworkInformation(String text) {
        Snackbar.make(coordinatorLayout, text, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.btnSave)
    public void addNewDepartment() {
        List<Integer> userIds = new ArrayList<>();
        Department d = new Department();
        if (departmentName.getText().toString().trim().equals("")) {
            departmentName.setError(getString(R.string.validation_required_department_name));
        } else if (monthlyQuota.getText().toString().trim().equals("")) {
            monthlyQuota.setError(getString(R.string.validation_required_department_monthly_quota));
        } else if (departmentPresenter.getSelectedUsers() == null) {
            showNetworkInformation(getString(R.string.validation_required_department_worker));
        } else {
            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Department")
                    .setAction("Create")
                    .setValue(1)
                    .build());
            d.setMonthlyQuota(Integer.parseInt(monthlyQuota.getText().toString()));
            d.setName(departmentName.getText().toString());
            for (int i = 0; i < departmentPresenter.getSelectedUsers().length; i++)
                if (departmentPresenter.getSelectedUsers()[i])
                    userIds.add(users.get(i).getUserId());
            if (userIds.size() == 0) {
                showNetworkInformation(getString(R.string.validation_required_department_worker));
            } else
                departmentPresenter.saveNewDepartment(userIds, d);
        }
    }
}
