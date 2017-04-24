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

public class OrderActivity extends AppCompatActivity implements OrderScreen, OrderItemsAdapter.DeleteListener {

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

    CoordinatorLayout coordinatorLayout;


    @Override
    protected void onResume() {
        super.onResume();
        orderPresenter.attachScreen(this);
        if (getIntent().hasExtra(KEY_USER)){
            showNetworkInformation(getString(R.string.login)+" "+orderPresenter.getUser().getLastName() + " " + orderPresenter.getUser().getFirstName());
            orderPresenter.refreshOrderItems();
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
                    orderItem.setProductName(data.getExtras().get(KEY_PRODUCT_NAME).toString());
                    orderItem.setCount(Integer.parseInt(data.getExtras().get(CreateOrderActivity.KEY_PRODUCT_COUNT).toString()));
                    orderItem.setCost(Integer.parseInt(data.getExtras().get(CreateOrderActivity.KEY_PRODUCT_PRICE).toString()));
                    orderPresenter.createOrderItem(orderItem);
                break;
            case RESULT_CANCELED:
                showNetworkInformation(getString(R.string.no_modification));
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

        if (getIntent().hasExtra(KEY_USER)){
            User user = (User) getIntent().getSerializableExtra(KEY_USER);
            orderPresenter.setUser(user);
            toolbar.setTitle(toolbar.getTitle()+(user.getUserId() == null ? " (Offline)" : " (Online)"));
        }

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                orderPresenter.refreshOrderItems();
            }
        });

        registerForContextMenu(recyclerView);
        recyclerView.setEmptyView(emptyTV);

        if (orderPresenter.getOrderItemsAdapter() == null) {
            OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(new ArrayList<OrderItem>(), this, this);
            orderPresenter.setOrderItemsAdapter(orderItemsAdapter);
        }
        recyclerView.setAdapter(orderPresenter.getOrderItemsAdapter());

        coordinatorLayout = (CoordinatorLayout)
                findViewById(R.id.activity_order);

    }

    @Override
    public void showNetworkInformation(String message) {
        swipeContainer.setRefreshing(false);
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void refreshStop() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void doLoginFromOffline() {
        Intent i = new Intent();
        i.setClass(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onDeleted(OrderItem orderItem) {
        orderPresenter.deleteOrderItem(orderItem);
    }
}
