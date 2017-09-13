package wzp.project.majiang.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {

	private static final String tag = "BaseActivity";

	protected final String LOG_TAG = this.getClass().getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d(tag, this.getClass().getSimpleName());
	}


	public void showToast(final String info) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(BaseActivity.this, info, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void showToast(final String info, final int duration) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(BaseActivity.this, info, duration).show();
			}
		});
	}


}
