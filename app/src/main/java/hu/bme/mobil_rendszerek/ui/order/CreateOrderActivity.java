package hu.bme.mobil_rendszerek.ui.order;

import android.content.Context;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.bme.mobil_rendszerek.R;

public class CreateOrderActivity extends AppCompatActivity implements SensorListener {

    public static final String KEY_PRODUCT_NAME = "KEY_PRODUCT_NAME";
    public static final String KEY_PRODUCT_PRICE = "KEY_PRODUCT_PRICE";
    public static final String KEY_PRODUCT_COUNT = "KEY_PRODUCT_COUNT";

    @BindView(R.id.productName)
    EditText productName;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.orderCount)
    EditText orderCount;

    //http://stackoverflow.com/questions/5271448/how-to-detect-shake-event-with-android
    private static final int FORCE_THRESHOLD = 350;
    private static final int TIME_THRESHOLD = 150;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int SHAKE_DURATION = 1000;
    private static final int SHAKE_COUNT = 3;

    private SensorManager mSensorMgr;
    private float mLastX=-1.0f, mLastY=-1.0f, mLastZ=-1.0f;
    private long mLastTime;
    private int mShakeCount = 0;
    private long mLastShake;
    private long mLastForce;
    //SENSOR SHAKE END

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorMgr = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        mSensorMgr.registerListener(this, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mSensorMgr.unregisterListener(this, SensorManager.SENSOR_ACCELEROMETER);
    }

    @OnClick(R.id.btnSave)
    void saveOrder() {
        if (productName.getText().toString().trim().equals("")) {
            productName.setError(getString(R.string.validation_required_product_name));
        } else if (price.getText().toString().trim().equals("")) {
            price.setError(getString(R.string.validation_required_price));
        } else if (orderCount.getText().toString().trim().equals("")) {
            orderCount.setError(getString(R.string.validation_required_product_count));
        } else {
            Intent intentResult = new Intent();
            intentResult.putExtra(KEY_PRODUCT_NAME, productName.getText());
            intentResult.putExtra(KEY_PRODUCT_PRICE, price.getText());
            intentResult.putExtra(KEY_PRODUCT_COUNT, orderCount.getText());
            setResult(RESULT_OK, intentResult);
            finish();
        }
    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor != SensorManager.SENSOR_ACCELEROMETER) return;
        long now = System.currentTimeMillis();

        if ((now - mLastForce) > SHAKE_TIMEOUT) {
            mShakeCount = 0;
        }

        if ((now - mLastTime) > TIME_THRESHOLD) {
            long diff = now - mLastTime;
            float speed = Math.abs(values[SensorManager.DATA_X] + values[SensorManager.DATA_Y] + values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ) / diff * 10000;
            if (speed > FORCE_THRESHOLD) {
                if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
                    mLastShake = now;
                    mShakeCount = 0;
                    finish();
                }
                mLastForce = now;
            }
            mLastTime = now;
            mLastX = values[SensorManager.DATA_X];
            mLastY = values[SensorManager.DATA_Y];
            mLastZ = values[SensorManager.DATA_Z];
        }
    }

    @Override
    public void onAccuracyChanged(int sensor, int accuracy) {

    }
}
