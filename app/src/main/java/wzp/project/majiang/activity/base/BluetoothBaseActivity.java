package wzp.project.majiang.activity.base;

import android.os.Bundle;
import android.util.Log;

import wzp.project.majiang.widget.MyApplication;

public abstract class BluetoothBaseActivity extends BaseActivity {

	private static final String tag = "BluetoothBaseActivity";

	protected ReadDataThread readDataThread;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(tag, this.getClass().getSimpleName());
	}

	@Override
	protected void onResume() {
		super.onResume();

		// 先清空消息队列
		MyApplication.getMessageQueue().clear();

		if (MyApplication.btClientHelper != null
				&& MyApplication.btClientHelper.isBluetoothConnected()) {
			readDataThread = new ReadDataThread();
			readDataThread.start();
		}
	}

	@Override
	protected void onPause() {
		// 关闭读数据线程
		if (readDataThread != null) {
			readDataThread.isStop = true;
			readDataThread = null;
		}

		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	protected abstract void onBluetoothDataReceived(final byte[] recvData);

	protected class ReadDataThread extends Thread {

		public volatile boolean isStop = false;

		public ReadDataThread(String name) {
			super(name);
		}

		public ReadDataThread() {
			super(LOG_TAG + "_ReadDataThread");
		}

		@Override
		public void run() {
			while (!isStop) {
				if (MyApplication.getMessageQueue().size() > 0) {
					byte[] singleData = null;
					synchronized (MyApplication.getMessageQueue()) {
						if (MyApplication.getMessageQueue().size() > 0) {
							singleData = MyApplication.getMessageQueue().poll();
						}
					}
					if (singleData != null) {
						onBluetoothDataReceived(singleData);
					}
				}

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
