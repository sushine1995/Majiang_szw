package wzp.project.majiang.widget;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import wzp.project.majiang.entity.BasicParameter;
import wzp.project.majiang.entity.ChooseCardMethod;
import wzp.project.majiang.entity.ChooseCardParameter;
import wzp.project.majiang.entity.DiceParameter;
import wzp.project.majiang.entity.PlayMethodParameter;
import wzp.project.majiang.util.BluetoothClientHelper;
import wzp.project.majiang.util.MySharedPreferences;

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

	private static MySharedPreferences spPlayMethod;
	private static MySharedPreferences spSetting;

	public static List<PlayMethodParameter> parameterList = new ArrayList<>();
	
	private static final String LOG_TAG = "MyApplication";
	

	@Override
	public void onCreate() {
		Log.i(LOG_TAG, "MyApplication --> onCreate");
		
		context = getApplicationContext();

		spPlayMethod = new MySharedPreferences(context, "play_method_prefs");
		spSetting = new MySharedPreferences(context, "setting_prefs");
	}

	public static Context getContext() {
		return context;
	}

	public static MySharedPreferences getSpPlayMethod() {
		return spPlayMethod;
	}

	public static MySharedPreferences getSpSetting() {
		return spSetting;
	}

	public static List<PlayMethodParameter> getParameterList() {
		return parameterList;
	}

	public static Queue<byte[]> getMessageQueue() {
		return messageQueue;
	}
}
