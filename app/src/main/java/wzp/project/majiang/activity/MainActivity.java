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
import wzp.project.majiang.util.BluetoothClientHelper;
import wzp.project.majiang.helper.constant.BluetoothState;
import wzp.project.majiang.helper.constant.ProjectConstants;
import wzp.project.majiang.widget.MyApplication;

public class MainActivity extends BluetoothBaseActivity implements IBluetoothConnect {

	private TextView tvBtState;
	private ImageButton ibtnSearch;

	private Button btnAboveTable;
	private Button btnBelowTable;
	private Button btnHand;
	private Button btnBaopai;
	private Button btnRecoDire; // 识别方位
	private Button btnSetting;

	private static final int REQUEST_ENABLE_BT = 0x00; // 请求打开蓝牙
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 0x01; // 请求安全连接蓝牙设备

	private BluetoothAdapter bluetoothAdapter;
//	private BluetoothClientHelper btClientHelper;
	private SharedPreferences preferences;

	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ibtn_search:
					Intent searchIntent = new Intent(MainActivity.this, DeviceListActivity.class);
					startActivityForResult(searchIntent, REQUEST_CONNECT_DEVICE_SECURE);
					break;

				case R.id.ibtn_aboveTable:
					ShowMajiangActivity.myStartActivity(MainActivity.this, true);
					break;

				case R.id.ibtn_belowTable:
					ShowMajiangActivity.myStartActivity(MainActivity.this, false);
					break;

				case R.id.ibtn_hand:
					ShowHandMajiangActivity.myStartActivity(MainActivity.this);
					break;

				case R.id.ibtn_baopai:
					ShowBaopaiActivity.myStartActivity(MainActivity.this);
					break;

				case R.id.ibtn_recoDire:
					RecognizeDirectionActivity.myStartActivity(MainActivity.this);
					break;

				case R.id.ibtn_setting:
//					SettingActivity.myStartActivity(MainActivity.this);
					ShowPlayMethodActivity.myStartActivity(MainActivity.this);
					break;

				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
		if (MyApplication.btClientHelper != null) {
			MyApplication.btClientHelper.stop();
		}

		// 退出应用时，清空消息队列
		MyApplication.getMessageQueue().clear();

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
		MyApplication.btClientHelper = new BluetoothClientHelper(this);
	}

	private void initWidget() {
		tvBtState = (TextView) findViewById(R.id.tv_btState);
		ibtnSearch = (ImageButton) findViewById(R.id.ibtn_search);
		btnAboveTable = (Button) findViewById(R.id.ibtn_aboveTable);
		btnBelowTable = (Button) findViewById(R.id.ibtn_belowTable);
		btnHand = (Button) findViewById(R.id.ibtn_hand);
		btnBaopai = (Button) findViewById(R.id.ibtn_baopai);
		btnRecoDire = (Button) findViewById(R.id.ibtn_recoDire);
		btnSetting = (Button) findViewById(R.id.ibtn_setting);

		ibtnSearch.setOnClickListener(listener);
		btnAboveTable.setOnClickListener(listener);
		btnBelowTable.setOnClickListener(listener);
		btnHand.setOnClickListener(listener);
		btnBaopai.setOnClickListener(listener);
		btnRecoDire.setOnClickListener(listener);
		btnSetting.setOnClickListener(listener);
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
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
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

						if (readDataThread == null) {
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

	@Override
	protected void onBluetoothDataReceived(byte[] recvData) {

	}
}
