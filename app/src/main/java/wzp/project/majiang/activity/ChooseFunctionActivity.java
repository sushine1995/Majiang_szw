package wzp.project.majiang.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BluetoothBaseActivity;
import wzp.project.majiang.constant.BluetoothState;
import wzp.project.majiang.constant.ProjectConstants;
import wzp.project.majiang.util.BluetoothClientHelper;
import wzp.project.majiang.widget.MyApplication;

public class ChooseFunctionActivity extends BluetoothBaseActivity {

	private TextView tvBtState;
	private ImageButton ibtnBack;
	private ImageButton ibtnSearch;

	private Button btnDesignPlayMethod;
	private Button btnShowCard;

	private static final int REQUEST_ENABLE_BT = 0x00; // 请求打开蓝牙
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 0x01; // 请求安全连接蓝牙设备

	private BluetoothAdapter bluetoothAdapter;
	private SharedPreferences preferences;

	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ibtn_back:
					onBackPressed();
					break;

				case R.id.ibtn_search:
					Intent searchIntent = new Intent(ChooseFunctionActivity.this, DeviceListActivity.class);
					startActivityForResult(searchIntent, REQUEST_CONNECT_DEVICE_SECURE);
					break;

				case R.id.btn_designPlayMethod:
					ShowPlayMethodActivity.myStartActivity(ChooseFunctionActivity.this);
					break;

				case R.id.btn_showCard:
					MainActivity.myStartActivity(ChooseFunctionActivity.this);
					break;

				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_function);

		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (bluetoothAdapter == null) {
			Toast.makeText(this, "当前设备不具备蓝牙功能！",
					Toast.LENGTH_LONG).show();
			finish();
			return;
		}

		if (!bluetoothAdapter.isEnabled()) {
			// 蓝牙尚未打开
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			// 蓝牙已经打开
			initParam();
			initWidget();
		}
	}

	@Override
	protected void onDestroy() {
//		if (MyApplication.btClientHelper != null) {
//			MyApplication.btClientHelper.stop();
//		}
//
//		// 退出应用时，清空消息队列
//		MyApplication.getMessageQueue().clear();

		super.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_ENABLE_BT:
				if (resultCode == Activity.RESULT_OK) {
					initParam();
					initWidget();
				} else {
					Log.d(LOG_TAG, "蓝牙尚未开启");
					Toast.makeText(this, "蓝牙尚未开启，无法使用应用程序", Toast.LENGTH_SHORT).show();
					finish();
				}
				break;

			case REQUEST_CONNECT_DEVICE_SECURE:
				if (resultCode == Activity.RESULT_OK) {
					connectDevice(data);
				}
				break;

			default:
				break;
		}
	}

	private void initParam() {
        if (MyApplication.btClientHelper == null) {
            MyApplication.btClientHelper = new BluetoothClientHelper();
        }
        MyApplication.btClientHelper.setBluetoothConnect(new IBluetoothConnect() {
            @Override
            public void showToast(String info, int duration) {
                ChooseFunctionActivity.this.showToast(info, duration);
            }

            @Override
            public void showToast(String info) {
                ChooseFunctionActivity.this.showToast(info);
            }

            @Override
            public void showBtState(final int state) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (state) {
                            case BluetoothState.STATE_NONE:
                                tvBtState.setText("未连接");
                                break;

                            case BluetoothState.STATE_CONNECTING:
                                tvBtState.setText("连接中...");
                                break;

                            case BluetoothState.STATE_CONNECTED:
                                tvBtState.setText("已连接：" + MyApplication
                                        .btClientHelper.getRemoteDevName());

                                if (readDataThread == null && isFront) {
                                    readDataThread = new ReadDataThread();
                                    readDataThread.start();
                                }
                                break;

                            default:
                                break;
                        }
                    }
                });
            }
        });
	}

	private void initWidget() {
		tvBtState = (TextView) findViewById(R.id.tv_btState);
		ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
		ibtnSearch = (ImageButton) findViewById(R.id.ibtn_search);
		btnDesignPlayMethod = (Button) findViewById(R.id.btn_designPlayMethod);
		btnShowCard = (Button) findViewById(R.id.btn_showCard);

		ibtnBack.setOnClickListener(listener);
		ibtnSearch.setOnClickListener(listener);
		btnDesignPlayMethod.setOnClickListener(listener);
		btnShowCard.setOnClickListener(listener);

		if (MyApplication.btClientHelper != null && MyApplication.btClientHelper.isBluetoothConnected()) {
			tvBtState.setText("已连接: " + MyApplication.btClientHelper.getRemoteDevName());
		} else {
			tvBtState.setText("未连接");
		}
	}

	/**
	 * 连接蓝牙设备
	 *
	 * @param data
	 */
	private void connectDevice(Intent data) {
		// Get the device MAC address
		String address = data.getExtras().getString(ProjectConstants.EXTRA_DEVICE_ADDRESS);
		// Get the BluetoothDevice object
		BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
		// Attempt to connect to the device
		MyApplication.btClientHelper.connect(device);
	}

	public static void myStartActivity(Context context) {
		Intent intent = new Intent(context, ChooseFunctionActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onBluetoothDataReceived(byte[] recvData) {

	}
}
