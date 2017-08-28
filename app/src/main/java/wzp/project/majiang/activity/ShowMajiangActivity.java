package wzp.project.majiang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.Arrays;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BaseActivity;
import wzp.project.majiang.helper.widget.MyApplication;
import wzp.project.majiang.util.CalculateUtil;

public class ShowMajiangActivity extends BaseActivity {

	private ImageButton ibtnBack;
	private TextView tvPosition;
	private ImageButton ibtnMoreFun;

	private LinearLayout linearNorth;
	private LinearLayout linearNorthTop;
	private LinearLayout linearNorthBottom;
	private LinearLayout linearSouth;
	private LinearLayout linearSouthTop;
	private LinearLayout linearSouthBottom;
	private LinearLayout linearWest;
	private LinearLayout linearWestTop;
	private LinearLayout linearWestBottom;
	private LinearLayout linearEast;
	private LinearLayout linearEastTop;
	private LinearLayout linearEastBottom;

	private PopupMenu pmSelectDirection;
	private PopupWindow pwSelectDirection;

	private boolean isAboveTable; // 是否为桌面牌（true：桌面牌；false：台下牌）

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_majiang);

		initParam();
		initWidget();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (MyApplication.btClientHelper == null
				|| !MyApplication.btClientHelper.isBluetoothConnected()) {
			showToast("蓝牙设备尚未连接");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initParam() {
		isAboveTable = getIntent().getBooleanExtra("isAboveTable", true);
	}

	private void initWidget() {
		ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
		tvPosition = (TextView) findViewById(R.id.tv_position);
		ibtnMoreFun = (ImageButton) findViewById(R.id.ibtn_moreFun);

		linearNorth = (LinearLayout) findViewById(R.id.linear_north);
		linearNorthTop = (LinearLayout) findViewById(R.id.linear_northTop);
		linearNorthBottom = (LinearLayout) findViewById(R.id.linear_northBottom);
		linearSouth = (LinearLayout) findViewById(R.id.linear_south);
		linearSouthTop = (LinearLayout) findViewById(R.id.linear_southTop);
		linearSouthBottom = (LinearLayout) findViewById(R.id.linear_southBottom);
		linearWest = (LinearLayout) findViewById(R.id.linear_west);
		linearWestTop = (LinearLayout) findViewById(R.id.linear_westTop);
		linearWestBottom = (LinearLayout) findViewById(R.id.linear_westBottom);
		linearEast = (LinearLayout) findViewById(R.id.linear_east);
		linearEastTop = (LinearLayout) findViewById(R.id.linear_eastTop);
		linearEastBottom = (LinearLayout) findViewById(R.id.linear_eastBottom);

		pmSelectDirection = new PopupMenu(this, ibtnMoreFun);
		getMenuInflater().inflate(R.menu.menu_select_direction, pmSelectDirection.getMenu());
		pmSelectDirection.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				boolean isChecked = !item.isChecked();
				item.setChecked(isChecked);

				switch (item.getItemId()) {
					case R.id.menu_east:
						if (isChecked) {
							linearEast.setVisibility(View.VISIBLE);
						} else {
							linearEast.setVisibility(View.INVISIBLE);
						}
						break;

					case R.id.menu_south:
						if (isChecked) {
							linearSouth.setVisibility(View.VISIBLE);
						} else {
							linearSouth.setVisibility(View.INVISIBLE);
						}
						break;

					case R.id.menu_west:
						if (isChecked) {
							linearWest.setVisibility(View.VISIBLE);
						} else {
							linearWest.setVisibility(View.INVISIBLE);
						}
						break;

					case R.id.menu_north:
						if (isChecked) {
							linearNorth.setVisibility(View.VISIBLE);
						} else {
							linearNorth.setVisibility(View.INVISIBLE);
						}
						break;
				}

				return true;
			}
		});

		ibtnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		ibtnMoreFun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pmSelectDirection.show();
			}
		});

		// 控件内容初始化
		if (isAboveTable) {
			tvPosition.setText("桌面牌");
		} else {
			tvPosition.setText("台下牌");
		}
		initMajiang(18);
	}

	/**
	 * 初始化麻将牌
	 *
	 * @param num
	 */
	private void initMajiang(int num) {
		ImageView ivMajiang = null;

		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.unknown);
		Matrix matrix = new Matrix();
		matrix.setRotate(90);
		Bitmap westBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);

		Matrix matrix2 = new Matrix();
		matrix2.setRotate(-90);
		Bitmap eastBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix2, true);

		// 北、南方向
		LinearLayout.LayoutParams paramsNorSou = new LinearLayout.LayoutParams(
				0, LinearLayout.LayoutParams.MATCH_PARENT, 1);

		// 西、东方向
		LinearLayout.LayoutParams paramsWesEas = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);

		// 北
		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsNorSou);
			ivMajiang.setImageBitmap(bitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearNorthTop.addView(ivMajiang);
		}

		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsNorSou);
			ivMajiang.setImageBitmap(bitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearNorthBottom.addView(ivMajiang);
		}

		// 南
		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsNorSou);
			ivMajiang.setImageBitmap(bitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearSouthTop.addView(ivMajiang);
		}

		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsNorSou);
			ivMajiang.setImageBitmap(bitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearSouthBottom.addView(ivMajiang);
		}

		// 西
		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsWesEas);
			ivMajiang.setImageBitmap(westBitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearWestTop.addView(ivMajiang);
		}

		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsWesEas);
			ivMajiang.setImageBitmap(westBitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearWestBottom.addView(ivMajiang);
		}

		// 东
		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsWesEas);
			ivMajiang.setImageBitmap(eastBitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearEastTop.addView(ivMajiang);
		}

		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsWesEas);
			ivMajiang.setImageBitmap(eastBitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearEastBottom.addView(ivMajiang);
		}
	}

	/**
	 * 启动Activity
	 *
	 * @param context
	 * @param isAboveTable 是否为桌面牌；true: 桌面牌；false: 台下牌
	 */
	public static void myStartActivity(Context context, boolean isAboveTable) {
		Intent intent = new Intent(context, ShowMajiangActivity.class);
		intent.putExtra("isAboveTable", isAboveTable);
		context.startActivity(intent);
	}

	/**
	 * 更新麻将牌
	 *
	 * @param recvData
	 */
	private void updateMajiang(byte[] recvData) {
		final byte[] copyRecvData = Arrays.copyOf(recvData, recvData.length);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				int num = CalculateUtil.byteToInt(copyRecvData[2]);
				// num必须小于设置的数量，还需要根据不同方位进一步判断
				// num最多不能超过36
				if (num > 36) {
					Log.e(LOG_TAG, "麻将牌数目异常：" + num);
					return;
				}

				LinearLayout linearTop = null;
				LinearLayout linearBottom = null;
//				Bitmap majiangBitmap = BitmapFactory.decodeResource(getResources(),
//						R.drawable.unknown);
				Bitmap majiangBitmap = null;
				Matrix matrix = new Matrix();
				switch (CalculateUtil.byteToInt(copyRecvData[1])) {
					// 东
					case 0xe1:
					case 0xe5:
						matrix.setRotate(-90);

						linearTop = linearEastTop;
						linearBottom = linearEastBottom;
						break;

					// 南
					case 0xe2:
					case 0xe6:
						linearTop = linearSouthTop;
						linearBottom = linearSouthBottom;
						break;

					// 西
					case 0xe3:
					case 0xe7:
						matrix.setRotate(90);

						linearTop = linearWestTop;
						linearBottom = linearWestBottom;
						break;

					// 北
					case 0xe4:
					case 0xe8:
						linearTop = linearNorthTop;
						linearBottom = linearNorthBottom;
						break;

					default:
						break;
				}
//				majiangBitmap = Bitmap.createBitmap(majiangBitmap, 0, 0, majiangBitmap.getWidth(),
//						majiangBitmap.getHeight(), matrix, true);

				for (int i = 0; i < linearTop.getChildCount(); i++) {
//					((ImageView) linearTop.getChildAt(i)).setImageBitmap(majiangBitmap);
					linearTop.getChildAt(i).setVisibility(View.INVISIBLE);
				}
				for (int i = 0; i < linearBottom.getChildCount(); i++) {
//					((ImageView) linearBottom.getChildAt(i)).setImageBitmap(majiangBitmap);
					linearBottom.getChildAt(i).setVisibility(View.INVISIBLE);
				}

				int index; // ImageView在父容器中的索引
				int oddEven; // 奇偶
				for (int i = 0; i < num; i++) {
					if (CalculateUtil.byteToInt(copyRecvData[1]) == 0xe1
							|| CalculateUtil.byteToInt(copyRecvData[1]) == 0xe5
							|| CalculateUtil.byteToInt(copyRecvData[1]) == 0xe4
							|| CalculateUtil.byteToInt(copyRecvData[1]) == 0xe8) {
						// 东方位先后顺序为，从下至上
						// 北方位先后顺序为，从右至左
						// 最多36张牌，上下分别为18张，index范围：0~17
						index = 17 - i / 2;
					} else {
						index = i / 2;
					}
					oddEven = i & 0x01;

					majiangBitmap = BitmapFactory.decodeResource(getResources(),
							CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(copyRecvData[3 + i])));
					majiangBitmap = Bitmap.createBitmap(majiangBitmap, 0, 0,
							majiangBitmap.getWidth(), majiangBitmap.getHeight(),
							matrix, true);
					if (oddEven == 0) {
						// 偶数（上-linearTop）
						((ImageView) linearTop.getChildAt(index)).setImageBitmap(majiangBitmap);
						linearTop.getChildAt(index).setVisibility(View.VISIBLE);
					} else {
						// 奇数（下-linearBottom）
						((ImageView) linearBottom.getChildAt(index)).setImageBitmap(majiangBitmap);
						linearBottom.getChildAt(index).setVisibility(View.VISIBLE);
					}
				}
			}
		});
	}


	@Override
	protected void onBluetoothDataReceived(byte[] recvData) {
		if (isAboveTable) {
			// 桌面牌
			switch (CalculateUtil.byteToInt(recvData[1])) {
				// 东
				case 0xe1:
				// 南
				case 0xe2:
				// 西
				case 0xe3:
				// 北
				case 0xe4:
					updateMajiang(recvData);
					break;

				default:
					break;
			}
		} else {
			// 台下牌
			switch (CalculateUtil.byteToInt(recvData[1])) {
				// 东
				case 0xe5:
				// 南
				case 0xe6:
				// 西
				case 0xe7:
				// 北
				case 0xe8:
					updateMajiang(recvData);
					break;

				default:
					break;
			}
		}
	}
}
