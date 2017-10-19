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
import android.widget.TextView;

import java.util.Arrays;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BluetoothBaseActivity;
import wzp.project.majiang.util.CalculateUtil;
import wzp.project.majiang.widget.MyApplication;

public class ShowHandMajiangActivity extends BluetoothBaseActivity {

	private ImageButton ibtnBack;
	private ImageButton ibtnMoreFun;

	private LinearLayout linearNorth;
	private LinearLayout linearSouth;
	private LinearLayout linearWest;
	private LinearLayout linearEast;

	private ImageView ivDice1;
	private ImageView ivDice2;

	private TextView tvPlayTypeEast;
	private TextView tvPlayTypeSouth;
	private TextView tvPlayTypeWest;
	private TextView tvPlayTypeNorth;

	private PopupMenu pmSelectDirection;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_hand_majiang);

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

	}

	private void initWidget() {
		ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
		ibtnMoreFun = (ImageButton) findViewById(R.id.ibtn_moreFun);

		linearNorth = (LinearLayout) findViewById(R.id.linear_north);
		linearSouth = (LinearLayout) findViewById(R.id.linear_south);
		linearWest = (LinearLayout) findViewById(R.id.linear_west);
		linearEast = (LinearLayout) findViewById(R.id.linear_east);

		ivDice1 = (ImageView) findViewById(R.id.iv_dice1);
		ivDice2 = (ImageView) findViewById(R.id.iv_dice2);

		tvPlayTypeEast = (TextView) findViewById(R.id.tv_playTypeEast);
		tvPlayTypeSouth = (TextView) findViewById(R.id.tv_playTypeSouth);
		tvPlayTypeWest = (TextView) findViewById(R.id.tv_playTypeWest);
		tvPlayTypeNorth = (TextView) findViewById(R.id.tv_playTypeNorth);

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
		//initMajiang(18;
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
//			ivMajiang.setVisibility(View.INVISIBLE);

			linearNorth.addView(ivMajiang);
		}

		// 南
		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsNorSou);
			ivMajiang.setImageBitmap(bitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearSouth.addView(ivMajiang);
		}

		// 西
		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsWesEas);
			ivMajiang.setImageBitmap(westBitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearWest.addView(ivMajiang);
		}

		// 东
		for (int i = 0; i < num; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsWesEas);
			ivMajiang.setImageBitmap(eastBitmap);
			ivMajiang.setVisibility(View.INVISIBLE);

			linearEast.addView(ivMajiang);
		}
	}

	/**
	 * 启动Activity
	 *
	 * @param context
	 */
	public static void myStartActivity(Context context) {
		Intent intent = new Intent(context, ShowHandMajiangActivity.class);
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
				// num最多不能超过18
				if (num > 18) {
					Log.e(LOG_TAG, "麻将牌数目异常：" + num);
					return;
				}

				LinearLayout linearDirection = null;
				Bitmap majiangBitmap = null;
				Matrix matrix = new Matrix();
				int majiangDirection = CalculateUtil.byteToInt(copyRecvData[1]);
				switch (majiangDirection) {
					// 东
					case 0xe9:
						matrix.setRotate(-90);
						linearDirection = linearEast;
						break;

					// 南
					case 0xec:
						linearDirection = linearSouth;
						break;

					// 西
					case 0xeb:
						matrix.setRotate(90);
						linearDirection = linearWest;
						break;

					// 北
					case 0xea:
						linearDirection = linearNorth;
						break;

					default:
						break;
				}

				if(num > linearDirection.getChildCount()) {
					int addedChildCount = num - linearDirection.getChildCount();

					LinearLayout.LayoutParams params = null;
					if (majiangDirection == 0xe9 || majiangDirection == 0xeb) {
						params = new LinearLayout.LayoutParams(
								getResources().getDimensionPixelSize(R.dimen.a_majiang_height),
								getResources().getDimensionPixelSize(R.dimen.a_majiang_width));
					} else {
						params = new LinearLayout.LayoutParams(
								getResources().getDimensionPixelSize(R.dimen.a_majiang_width),
								getResources().getDimensionPixelSize(R.dimen.a_majiang_height));
					}

					ImageView ivMajiang = null;
					for (int i = 0; i < addedChildCount; i++) {
						ivMajiang = new ImageView(ShowHandMajiangActivity.this);
						ivMajiang.setLayoutParams(params);

						linearDirection.addView(ivMajiang);
					}
				} else {
					int delChidCount = linearDirection.getChildCount() - num;
					for (int i = 0; i < delChidCount; i++) {
						linearDirection.removeViewAt(linearDirection.getChildCount() - 1);
					}
				}

				int index; // ImageView在父容器中的索引
				for (int i = 0; i < num; i++) {
					if (majiangDirection == 0xe9 || majiangDirection == 0xec) {
						// 东方位先后顺序为，从下至上
						// 北方位先后顺序为，从右至左
						// 最多18张牌，index范围：0~17
						index = num - 1 - i;
					} else {
						index = i;
					}

					majiangBitmap = BitmapFactory.decodeResource(getResources(),
							CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(copyRecvData[3 + i])));
					majiangBitmap = Bitmap.createBitmap(majiangBitmap, 0, 0,
							majiangBitmap.getWidth(), majiangBitmap.getHeight(),
							matrix, true);
					((ImageView) linearDirection.getChildAt(index)).setImageBitmap(majiangBitmap);
				}
			}
		});
	}

	/**
	 * 更新色子和位置标识（庄家位置和我的位置）
	 *
	 * @param recvData
	 */
	private void updateDiceAndPositionFlag(byte[] recvData) {
		final byte[] copyRecvData = Arrays.copyOf(recvData, recvData.length);
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				int diceRes1 = -1;
				switch (CalculateUtil.byteToInt(copyRecvData[3])) {
					case 0x01:
						diceRes1 = R.drawable.dot1;
						break;

					case 0x02:
						diceRes1 = R.drawable.dot2;
						break;

					case 0x03:
						diceRes1 = R.drawable.dot3;
						break;

					case 0x04:
						diceRes1 = R.drawable.dot4;
						break;

					case 0x05:
						diceRes1 = R.drawable.dot5;
						break;

					case 0x06:
						diceRes1 = R.drawable.dot6;
						break;
				}
				if (diceRes1 != -1) {
					ivDice1.setImageResource(diceRes1);
				}

				int diceRes2 = -1;
				switch (CalculateUtil.byteToInt(copyRecvData[4])) {
					case 0x01:
						diceRes2 = R.drawable.dot1;
						break;

					case 0x02:
						diceRes2 = R.drawable.dot2;
						break;

					case 0x03:
						diceRes2 = R.drawable.dot3;
						break;

					case 0x04:
						diceRes2 = R.drawable.dot4;
						break;

					case 0x05:
						diceRes2 = R.drawable.dot5;
						break;

					case 0x06:
						diceRes2 = R.drawable.dot6;
						break;
				}
				if (diceRes2 != -1) {
					ivDice2.setImageResource(diceRes2);
				}

				tvPlayTypeEast.setText("");
				tvPlayTypeSouth.setText("");
				tvPlayTypeWest.setText("");
				tvPlayTypeNorth.setText("");
				switch (CalculateUtil.byteToInt(copyRecvData[5])) {
					case 0x01:
						tvPlayTypeEast.setText("庄");
						tvPlayTypeEast.setTextColor(getResources().getColor(R.color.red));
						break;

					case 0x02:
						tvPlayTypeSouth.setText("庄");
						tvPlayTypeSouth.setTextColor(getResources().getColor(R.color.red));
						break;

					case 0x03:
						tvPlayTypeWest.setText("庄");
						tvPlayTypeWest.setTextColor(getResources().getColor(R.color.red));
						break;

					case 0x04:
						tvPlayTypeNorth.setText("庄");
						tvPlayTypeNorth.setTextColor(getResources().getColor(R.color.red));
						break;

					default:
						break;
				}
				switch (CalculateUtil.byteToInt(copyRecvData[6])) {
					case 0x01:
						tvPlayTypeEast.setText("我");
						tvPlayTypeEast.setTextColor(getResources().getColor(R.color.blue));
						break;

					case 0x02:
						tvPlayTypeSouth.setText("我");
						tvPlayTypeSouth.setTextColor(getResources().getColor(R.color.blue));
						break;

					case 0x03:
						tvPlayTypeWest.setText("我");
						tvPlayTypeWest.setTextColor(getResources().getColor(R.color.blue));
						break;

					case 0x04:
						tvPlayTypeNorth.setText("我");
						tvPlayTypeNorth.setTextColor(getResources().getColor(R.color.blue));
						break;

					default:
						break;
				}
			}
		});
	}


	@Override
	protected void onBluetoothDataReceived(byte[] recvData) {
		// 桌面牌
		switch (CalculateUtil.byteToInt(recvData[1])) {
			// 东
			case 0xe9:
			// 南
			case 0xea:
			// 西
			case 0xeb:
			// 北
			case 0xec:
				updateMajiang(recvData);
				break;

			// 色子
			case 0xed:
				updateDiceAndPositionFlag(recvData);
				break;

			default:
				break;
		}
	}
}
