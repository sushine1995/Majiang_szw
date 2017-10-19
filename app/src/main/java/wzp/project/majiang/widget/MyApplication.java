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

	public static List<PlayMethodParameter> parameterList = new ArrayList<>();
	
	private static final String LOG_TAG = "MyApplication";
	

	@Override
	public void onCreate() {
		Log.i(LOG_TAG, "MyApplication --> onCreate");
		
		context = getApplicationContext();

		spPlayMethod = new MySharedPreferences(context, "play_method_prefs");

		if (parameterList.size() == 0) {
			for (int i = 0; i < 3; i++) {
				String playMethod = MyApplication.getSpPlayMethod().getString("playMethod" + (i + 1), "");
				PlayMethodParameter parameter = null;
				try {
					parameter = JSON.parseObject(playMethod, PlayMethodParameter.class);
				} catch (JSONException e) {
					e.printStackTrace();
					parameter = null;
				}
				if (parameter == null) {
					// 设置默认的参数值
					parameter = new PlayMethodParameter();

					BasicParameter bp = new BasicParameter();
					bp.setTotalCardNum(144);
					bp.setEastTop(18);
					bp.setEastMiddle(0);
					bp.setEastBottom(18);
					bp.setNorthTop(18);
					bp.setNorthMiddle(0);
					bp.setNorthBottom(18);
					bp.setWestTop(18);
					bp.setWestMiddle(0);
					bp.setWestBottom(18);
					bp.setSouthTop(18);
					bp.setSouthMiddle(0);
					bp.setSouthBottom(18);
					parameter.setBasicParameter(bp);

					ChooseCardParameter ccp = new ChooseCardParameter();
					ccp.setMethods(new ArrayList<ChooseCardMethod>());
					parameter.setChooseCardParameter(ccp);

					DiceParameter dp = new DiceParameter();
					parameter.setDiceParameter(dp);

					spPlayMethod.commitString("playMethod" + i,
							JSON.toJSONString(parameter));
				}

				if (parameterList.size() <= i) {
					parameterList.add(parameter);
				} else {
					parameterList.set(i, parameter);
				}
			}
		}
	}

	public static Context getContext() {
		return context;
	}

	public static MySharedPreferences getSpPlayMethod() {
		return spPlayMethod;
	}

	public static List<PlayMethodParameter> getParameterList() {
		return parameterList;
	}

	public static Queue<byte[]> getMessageQueue() {
		return messageQueue;
	}
}
