package wzp.project.majiang.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import wzp.project.majiang.R;
import wzp.project.majiang.adapter.BluetoothDeviceAdapter;
import wzp.project.majiang.constant.ProjectConstants;

public class DeviceListActivity extends Activity {

	private TextView tvPairedDevice;
	private ListView lvPairedDevice;
	private TextView tvNewDevice;
	private ListView lvNewDevice;
	private Button btnScan;
	
	private List<BluetoothDevice> pairedDeviceList = new ArrayList<>();
	private List<BluetoothDevice> newDeviceList = new ArrayList<>();
	
	private BluetoothDeviceAdapter pairedDeviceAdapter;
	private BluetoothDeviceAdapter newDeviceAdapter;
	
	private BluetoothAdapter mBtAdapter;
	
	private static final String TAG = "DeviceListActivity";

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG, action);

			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// 搜索到一个蓝牙设备
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				// 判断蓝牙是否配对过
				if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
					if (!newDeviceList.contains(device)) {
						newDeviceList.add(device);
						newDeviceAdapter.notifyDataSetChanged();						
					}
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				// 蓝牙搜索结束(两种情况：1.调用了cancelDiscovery()方法；2.搜索蓝牙的时间到了)
				setProgressBarIndeterminateVisibility(false);
				setTitle("请选择蓝牙设备");
				if (newDeviceList.size() == 0) {
					tvNewDevice.setText("未搜索到蓝牙设备");
				}

				btnScan.setVisibility(View.VISIBLE);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Setup the window
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_device_list);
		
		setTitle("请选择蓝牙设备");

		// Set result CANCELED in case the user backs out
		setResult(Activity.RESULT_CANCELED);

		initParam();
		initWidget();
		registerBluetoothSearchBroadcast();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}

		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
	}
	
	private void initParam() {
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();
				
		pairedDeviceAdapter = new BluetoothDeviceAdapter(this, R.layout.listitem_device_info,
				pairedDeviceList);
		newDeviceAdapter = new BluetoothDeviceAdapter(this, R.layout.listitem_device_info,
				newDeviceList);
	}
	
	private void initWidget() {
		tvPairedDevice = (TextView) findViewById(R.id.tv_pairedDevice);
		lvPairedDevice = (ListView) findViewById(R.id.lv_pairedDevice);
		tvNewDevice = (TextView) findViewById(R.id.tv_newDevice);
		lvNewDevice = (ListView) findViewById(R.id.lv_newDevice);
		btnScan = (Button) findViewById(R.id.btn_scan);
		
		lvPairedDevice.setAdapter(pairedDeviceAdapter);
		lvPairedDevice.setOnItemClickListener(new DeviceClickListener(pairedDeviceList));
		
		lvNewDevice.setAdapter(newDeviceAdapter);
		lvNewDevice.setOnItemClickListener(new DeviceClickListener(newDeviceList));
		
		btnScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doDiscovery();
				v.setVisibility(View.GONE);
			}
		});
		
		/*
		 * 控件数值初始化
		 */
		Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

		if (pairedDevices.size() > 0) {
			tvPairedDevice.setText("已配对的设备");

			pairedDeviceList.addAll(pairedDevices);
			pairedDeviceAdapter.notifyDataSetChanged();
		} else {
			tvPairedDevice.setText("尚未配对蓝牙设备");
		}
	}
	
	/**
	 * 注册蓝牙搜索广播
	 */
	private void registerBluetoothSearchBroadcast() {
		// Register for broadcasts when a device is discovered and discovery has finished
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);
	}

	/**
	 * 搜索蓝牙设备
	 */
	private void doDiscovery() {
		Log.d(TAG, "doDiscovery()");
		
		// If we're already discovering, stop it
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}

		// Indicate scanning in the title
		setProgressBarIndeterminateVisibility(true);
		setTitle("正在扫描中...");

		// Turn on sub-title for new devices
		tvNewDevice.setVisibility(View.VISIBLE);

		// Request discover from BluetoothAdapter
		mBtAdapter.startDiscovery();
	}
	
	private class DeviceClickListener implements OnItemClickListener {
		private List<BluetoothDevice> deviceList;
		
		public DeviceClickListener(List<BluetoothDevice> deviceList) {
			this.deviceList = deviceList;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			mBtAdapter.cancelDiscovery();

			String address = deviceList.get(position).getAddress();

			// Create the result Intent and include the MAC address
			Intent intent = new Intent();
			intent.putExtra(ProjectConstants.EXTRA_DEVICE_ADDRESS, address);

			// Set result and finish this Activity
			setResult(Activity.RESULT_OK, intent);
			finish();		
		}
	}
}
