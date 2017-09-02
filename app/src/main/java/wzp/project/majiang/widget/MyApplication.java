package wzp.project.majiang.widget;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

import wzp.project.majiang.util.BluetoothClientHelper;

/**
 * 提供一些全局的Application属性
 * @author Zippen
 *
 */
public class MyApplication extends Application {

	private static Context context;
	public static BluetoothClientHelper btClientHelper;

	// 报文队列
	private static Queue<byte[]> messageQueue = new LinkedList<>();
	
	private static final String LOG_TAG = "MyApplication";
	

	@Override
	public void onCreate() {
		Log.i(LOG_TAG, "MyApplication --> onCreate");
		
		context = getApplicationContext();

	}

	public static Context getContext() {
		return context;
	}


	public static Queue<byte[]> getMessageQueue() {
		return messageQueue;
	}
}
