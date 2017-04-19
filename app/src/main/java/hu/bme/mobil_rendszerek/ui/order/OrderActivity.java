package hu.bme.mobil_rendszerek.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.bme.mobil_rendszerek.MobSoftApplication;
import hu.bme.mobil_rendszerek.R;
import hu.bme.mobil_rendszerek.model.OrderItem;
import hu.bme.mobil_rendszerek.model.User;
import hu.bme.mobil_rendszerek.ui.main.MainActivity;

import static hu.bme.mobil_rendszerek.ui.order.CreateOrderActivity.KEY_PRODUCT_NAME;

public class OrderActivity extends AppCompatActivity implements OrderScreen {

    public static final int REQUEST_NEW_ORDER_CODE = 100;
    public static final String KEY_USER = "KEY_USER";

    @Inject
    OrderPresenter orderPresenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.orderItemsERV)
    EmptyRecyclerView recyclerView;
    @BindView(R.id.emptyTV)
    View emptyTV;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.addButton)
    FloatingActionButton fab;

    OrderItemsAdapter orderItemsAdapter;
    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onResume() {
        super.onResume();
        orderPresenter.attachScreen(this);
        orderPresenter.refreshOrderItems(orderPresenter.getUser().getDepartmentId(), orderPresenter.getUser().getCredential());
        if (getIntent().hasExtra(KEY_USER)){
            showNetworkInformation(getString(R.string.login)+" "+orderPresenter.getUser().getLastName() + " " + orderPresenter.getUser().getFirstName());
            getIntent().removeExtra(KEY_USER);
        }
    }

    @OnClick({R.id.emptyTV,R.id.addButton})
    void showNewOrderDialog() {
        if (orderPresenter.getUser().getCredential() == null){
            showNetworkInformation(getString(R.string.offline_have_to_refresh));
            return;
        }
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        OrderActivity.this,
                        fab,
                        "create");
        Intent i = new Intent();
        i.setClass(this, CreateOrderActivity.class);
        startActivityForResult(i, REQUEST_NEW_ORDER_CODE, options.toBundle());
    }

    @OnClick(R.id.logout)
    void logout(){
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(MainActivity.KEY_LOGOUT,true);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != REQUEST_NEW_ORDER_CODE)
            return;
        switch (resultCode) {
            case RESULT_OK:
                OrderItem orderItem = new OrderItem();
                orderItem.setDepartmentId(0);
                orderItem.setOrderItemId(0);
                orderItem.setProductName(data.getExtras().get(KEY_PRODUCT_NAME).toString());
                orderItem.setCount(Integer.parseInt(data.getExtras().get(CreateOrderActivity.KEY_PRODUCT_COUNT).toString()));
                orderItem.setCost(Integer.parseInt(data.getExtras().get(CreateOrderActivity.KEY_PRODUCT_PRICE).toString()));
                orderPresenter.createOrderItem(orderItem, orderPresenter.getUser().getCredential());
                break;
        }
    }

    @Override
    protected  void onPause(){
        super.onPause();
        orderPresenter.detachScreen();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        MobSoftApplication.injector.inject(this);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        User user = null;
        if (getIntent().hasExtra(KEY_USER)){
            user = (User) getIntent().getSerializableExtra(KEY_USER);
            orderPresenter.setUser(user);
        } else {
            user = orderPresenter.getUser();
        }

        final User finalUser = user;
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderPresenter.refreshOrderItems(finalUser.getDepartmentId(), finalUser.getCredential());
            }
        });

        registerForContextMenu(recyclerView);
        recyclerView.setEmptyView(emptyTV);

        coordinatorLayout = (CoordinatorLayout)
                findViewById(R.id.activity_order);

        toolbar.setTitle(toolbar.getTitle()+(user.getUserId() == null ? " (Offline)" : " (Online)"));
    }

    @Override
    public void showNetworkInformation(String message) {
        swipeContainer.setRefreshing(false);
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showOrderItems(List<OrderItem> orderItems) {
        swipeContainer.setRefreshing(false);
        orderItemsAdapter = new OrderItemsAdapter(orderItems == null ? new ArrayList<OrderItem>() : orderItems, this);
        recyclerView.setAdapter(orderItemsAdapter);
    }

    @Override
    public void doLoginFromOffline() {
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);
    }

}
