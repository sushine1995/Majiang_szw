package com.wzp.majiang.activity;

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

import com.wzp.majiang.R;
import com.wzp.majiang.activity.base.BluetoothBaseActivity;
import com.wzp.majiang.constant.ProjectConstants;
import com.wzp.majiang.util.CalculateUtil;
import com.wzp.majiang.widget.MyApplication;

import java.util.Arrays;

public class ShowMajiangActivity extends BluetoothBaseActivity {

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

	private TextView tvBlockNumNorth;
	private TextView tvBlockNumSouth;
	private TextView tvBlockNumWest;
	private TextView tvBlockNumEast;

	private ImageView ivMoreMajiang1;
	private ImageView ivMoreMajiang2;
	private ImageView ivMoreMajiang3;
	private ImageView ivMoreMajiang4;
	private ImageView ivLessMajiang1;
	private ImageView ivLessMajiang2;
	private ImageView ivLessMajiang3;
	private ImageView ivLessMajiang4;
	private TextView tvWrongNum;
	private TextView tvErrorTip;

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

		tvBlockNumNorth = (TextView) findViewById(R.id.tv_blockNumNorth);
		tvBlockNumSouth = (TextView) findViewById(R.id.tv_blockNumSouth);
		tvBlockNumWest = (TextView) findViewById(R.id.tv_blockNumWest);
		tvBlockNumEast = (TextView) findViewById(R.id.tv_blockNumEast);

		ivMoreMajiang1 = (ImageView) findViewById(R.id.iv_moreMajiang1);
		ivMoreMajiang2 = (ImageView) findViewById(R.id.iv_moreMajiang2);
		ivMoreMajiang3 = (ImageView) findViewById(R.id.iv_moreMajiang3);
		ivMoreMajiang4 = (ImageView) findViewById(R.id.iv_moreMajiang4);
		ivLessMajiang1 = (ImageView) findViewById(R.id.iv_lessMajiang1);
		ivLessMajiang2 = (ImageView) findViewById(R.id.iv_lessMajiang2);
		ivLessMajiang3 = (ImageView) findViewById(R.id.iv_lessMajiang3);
		ivLessMajiang4 = (ImageView) findViewById(R.id.iv_lessMajiang4);
		tvWrongNum = (TextView) findViewById(R.id.tv_wrongMajiangNum);
		tvErrorTip = (TextView) findViewById(R.id.tv_errorTip);

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
				Bitmap majiangBitmap = null;
				Matrix matrix = new Matrix();
				TextView tvBlockNum = null;
				switch (CalculateUtil.byteToInt(copyRecvData[1])) {
					// 东
					case 0xe1:
					case 0xe5:
						matrix.setRotate(-90);

						linearTop = linearEastTop;
						linearBottom = linearEastBottom;
						tvBlockNum = tvBlockNumEast;
						break;

					// 南
					case 0xe2:
					case 0xe6:
						linearTop = linearSouthTop;
						linearBottom = linearSouthBottom;
						tvBlockNum = tvBlockNumSouth;
						break;

					// 西
					case 0xe3:
					case 0xe7:
						matrix.setRotate(90);

						linearTop = linearWestTop;
						linearBottom = linearWestBottom;
						tvBlockNum = tvBlockNumWest;
						break;

					// 北
					case 0xe4:
					case 0xe8:
						linearTop = linearNorthTop;
						linearBottom = linearNorthBottom;
						tvBlockNum = tvBlockNumNorth;
						break;

					default:
						break;
				}

				for (int i = 0; i < linearTop.getChildCount(); i++) {
					linearTop.getChildAt(i).setVisibility(View.INVISIBLE);
				}
				for (int i = 0; i < linearBottom.getChildCount(); i++) {
					linearBottom.getChildAt(i).setVisibility(View.INVISIBLE);
				}

				int index; // ImageView在父容器中的索引
				int oddEven; // 奇偶
				int imageId; // 麻将图片的资源ID
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

					imageId = CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(copyRecvData[3 + i]));
					if (imageId != -1) {
						// 正常显示麻将图片
						majiangBitmap = BitmapFactory.decodeResource(getResources(), imageId);
						majiangBitmap = Bitmap.createBitmap(majiangBitmap, 0, 0,
								majiangBitmap.getWidth(), majiangBitmap.getHeight(),
								matrix, true);
						if (oddEven == 1) {
							// 奇数（上-linearTop）
							((ImageView) linearTop.getChildAt(index)).setImageBitmap(majiangBitmap);
							linearTop.getChildAt(index).setVisibility(View.VISIBLE);
						} else {
							// 偶数（下-linearBottom）
							((ImageView) linearBottom.getChildAt(index)).setImageBitmap(majiangBitmap);
							linearBottom.getChildAt(index).setVisibility(View.VISIBLE);
						}
					} else {
						// 当前位置不显示麻将
						if (oddEven == 1) {
							// 奇数（上-linearTop）
							linearTop.getChildAt(index).setVisibility(View.INVISIBLE);
						} else {
							// 偶数（下-linearBottom）
							linearBottom.getChildAt(index).setVisibility(View.INVISIBLE);
						}
					}
				}

				int blockNum = (int) Math.ceil(num / 2.0);
				tvBlockNum.setText(String.valueOf(blockNum));
			}
		});
	}

	@Override
	protected void onBluetoothDataReceived(final byte[] recvData) {
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

		switch (CalculateUtil.byteToInt(recvData[1])) {
			case 0xe0:
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						int majiangRes;

						/*
						多牌
						 */
						int moreNum = CalculateUtil.byteToInt(recvData[2]);
						if (moreNum > 4) { // 最多4张牌，超过则认为出错，忽略此条报文
							return;
						}
						ImageView[] ivMoreMajiangArr = new ImageView[]{
								ivMoreMajiang1, ivMoreMajiang2,
								ivMoreMajiang3, ivMoreMajiang4
						};
						for (int i = 0; i < moreNum; i++) {
							majiangRes = CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(recvData[3 + i]));
							if (majiangRes != ProjectConstants.INVISIBLE_MAJIANG) {
								ivMoreMajiangArr[i].setImageResource(majiangRes);
								ivMoreMajiangArr[i].setVisibility(View.VISIBLE);
							} else {
								ivMoreMajiangArr[i].setVisibility(View.INVISIBLE);
							}
						}

						/*
						少牌
						 */
						int lessNum = CalculateUtil.byteToInt(recvData[7]);
						if (lessNum > 4) { // 最多4张牌，超过则认为出错，忽略此条报文
							return;
						}
						ImageView[] ivLessMajiangArr = new ImageView[]{
								ivLessMajiang1, ivLessMajiang2,
								ivLessMajiang3, ivLessMajiang4
						};
						for (int i = 0; i < lessNum; i++) {
							majiangRes = CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(recvData[8 + i]));
							if (majiangRes != ProjectConstants.INVISIBLE_MAJIANG) {
								ivLessMajiangArr[i].setImageResource(majiangRes);
								ivLessMajiangArr[i].setVisibility(View.VISIBLE);
							} else {
								ivLessMajiangArr[i].setVisibility(View.INVISIBLE);
							}
						}

						// 错牌数量
						tvWrongNum.setText(CalculateUtil.byteToInt(recvData[12]) + "张");

						// 故障提示
						if (CalculateUtil.byteToInt(recvData[13]) == 1) {
							tvErrorTip.setText("请检查牌");
						} else {
							tvErrorTip.setText("");
						}
					}
				});
				break;

			default:
				break;
		}

	}
}
