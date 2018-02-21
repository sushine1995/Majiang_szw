package wzp.project.majiang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BluetoothBaseActivity;
import wzp.project.majiang.constant.ProjectConstants;
import wzp.project.majiang.util.CRC16;
import wzp.project.majiang.util.CalculateUtil;
import wzp.project.majiang.widget.AddSubWidget;
import wzp.project.majiang.widget.ListOptionButton;
import wzp.project.majiang.widget.MyApplication;

/**
 * Created by wzp on 2017/8/28.
 */

public class StudyTestActivity extends BluetoothBaseActivity {

    private ImageButton ibtnBack;

    private Button btnDirectionStudyEast;
    private Button btnDirectionStudySouth;
    private Button btnDirectionStudyWest;
    private Button btnDirectionStudyNorth;
    private Button btnUseDiceEast;
    private Button btnUseDiceSouth;
    private Button btnUseDiceWest;
    private Button btnUseDiceNorth;
    private Button btnUpDown1;
    private Button btnUpDown2;
    private Button btnCheckMajiang;
    private ImageView ivCheckMajiang;
    private ListOptionButton btnRedDice;
    private ListOptionButton btnWhiteDice;
    private Button btnUseDice;
    private Button btnRemoteControlStudy;
    private Button btnChannelTest;
    private TextView tvChannelTestEast;
    private TextView tvChannelTestSouth;
    private TextView tvChannelTestWest;
    private TextView tvChannelTestNorth;
    private TextView tvChannelNo;
    private ImageView ivChannelTestMajiang;
    private AddSubWidget asBrakeTime;
    private Button btnSetBrakeTime;
    private AddSubWidget asUseDiceDelay;
    private Button btnSetUseDiceDelay;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ibtn_back:
                    onBackPressed();
                    break;

                case R.id.btn_directionStudyEast:
                case R.id.btn_directionStudySouth:
                case R.id.btn_directionStudyWest:
                case R.id.btn_directionStudyNorth:
                case R.id.btn_useDiceEast:
                case R.id.btn_useDiceSouth:
                case R.id.btn_useDiceWest:
                case R.id.btn_useDiceNorth:
                case R.id.btn_upDown1:
                case R.id.btn_upDown2:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        int direction = 0;
                        switch (v.getId()) {
                            case R.id.btn_directionStudyEast:
                            case R.id.btn_useDiceEast:
                                direction = 0xd5;
                                break;
                            case R.id.btn_directionStudySouth:
                            case R.id.btn_useDiceSouth:
                                direction = 0xd6;
                                break;
                            case R.id.btn_directionStudyWest:
                            case R.id.btn_useDiceWest:
                                direction = 0xd7;
                                break;
                            case R.id.btn_directionStudyNorth:
                            case R.id.btn_useDiceNorth:
                                direction = 0xd8;
                                break;
                            case R.id.btn_upDown1:
                                direction = 0xd9;
                                break;
                            case R.id.btn_upDown2:
                                direction = 0xda;
                                break;
                            default:
                                break;
                        }

                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) direction;
                        sendMsg[2] = (byte) 0x01;
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_checkMajiang:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa2;
                        sendMsg[2] = (byte) 0x01;
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_useDice:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa3;
                        sendMsg[2] = (byte) 0x01;
                        sendMsg[3] = (byte) (btnRedDice.getSelectedItemPosition() + 1); // 红色点数
                        sendMsg[4] = (byte) (btnWhiteDice.getSelectedItemPosition() + 1); // 白色点数
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_remoteControlStudy:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa4;
                        sendMsg[2] = (byte) 0x01;
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_channelTest:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa5;
                        sendMsg[2] = (byte) 0x01;
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_setBrakeTime:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa6;
                        sendMsg[2] = (byte) 0x01;
                        sendMsg[3] = (byte) asBrakeTime.getCurVal();
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_setUseDiceDelay:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa7;
                        sendMsg[2] = (byte) 0x01;
                        sendMsg[3] = (byte) asUseDiceDelay.getCurVal();
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_test);

        initWidget();
    }

    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
        btnDirectionStudyEast = (Button) findViewById(R.id.btn_directionStudyEast);
        btnDirectionStudySouth = (Button) findViewById(R.id.btn_directionStudySouth);
        btnDirectionStudyWest = (Button) findViewById(R.id.btn_directionStudyWest);
        btnDirectionStudyNorth = (Button) findViewById(R.id.btn_directionStudyNorth);
        btnUseDiceEast = (Button) findViewById(R.id.btn_useDiceEast);
        btnUseDiceSouth = (Button) findViewById(R.id.btn_useDiceSouth);
        btnUseDiceWest = (Button) findViewById(R.id.btn_useDiceWest);
        btnUseDiceNorth = (Button) findViewById(R.id.btn_useDiceNorth);
        btnUpDown1 = (Button) findViewById(R.id.btn_upDown1);
        btnUpDown2 = (Button) findViewById(R.id.btn_upDown2);
        btnCheckMajiang = (Button) findViewById(R.id.btn_checkMajiang);
        ivCheckMajiang = (ImageView) findViewById(R.id.iv_checkMajiang);
        btnRedDice = (ListOptionButton) findViewById(R.id.btn_redDice);
        btnWhiteDice = (ListOptionButton) findViewById(R.id.btn_whiteDice);
        btnUseDice = (Button) findViewById(R.id.btn_useDice);
        btnRemoteControlStudy = (Button) findViewById(R.id.btn_remoteControlStudy);
        btnChannelTest = (Button) findViewById(R.id.btn_channelTest);
        tvChannelTestEast = (TextView) findViewById(R.id.tv_channelTestEast);
        tvChannelTestSouth = (TextView) findViewById(R.id.tv_channelTestSouth);
        tvChannelTestWest = (TextView) findViewById(R.id.tv_channelTestWest);
        tvChannelTestNorth = (TextView) findViewById(R.id.tv_channelTestNorth);
        tvChannelNo = (TextView) findViewById(R.id.tv_channelNo);
        ivChannelTestMajiang = (ImageView) findViewById(R.id.iv_channelTestMajiang);
        asBrakeTime = (AddSubWidget) findViewById(R.id.as_brakeTime);
        btnSetBrakeTime = (Button) findViewById(R.id.btn_setBrakeTime);
        asUseDiceDelay = (AddSubWidget) findViewById(R.id.as_useDiceDelay);
        btnSetUseDiceDelay = (Button) findViewById(R.id.btn_setUseDiceDelay);


        ibtnBack.setOnClickListener(listener);
        btnDirectionStudyEast.setOnClickListener(listener);
        btnDirectionStudySouth.setOnClickListener(listener);
        btnDirectionStudyWest.setOnClickListener(listener);
        btnDirectionStudyNorth.setOnClickListener(listener);
        btnUseDiceEast.setOnClickListener(listener);
        btnUseDiceSouth.setOnClickListener(listener);
        btnUseDiceWest.setOnClickListener(listener);
        btnUseDiceNorth.setOnClickListener(listener);
        btnUpDown1.setOnClickListener(listener);
        btnUpDown2.setOnClickListener(listener);
        btnCheckMajiang.setOnClickListener(listener);
        btnUseDice.setOnClickListener(listener);
        btnRemoteControlStudy.setOnClickListener(listener);
        btnChannelTest.setOnClickListener(listener);
        btnSetBrakeTime.setOnClickListener(listener);
        btnSetUseDiceDelay.setOnClickListener(listener);
    }

    @Override
    protected void onBluetoothDataReceived(byte[] recvData) {
        switch (CalculateUtil.byteToInt(recvData[1])) {
            case 0xd5:
            case 0xd6:
            case 0xd7:
            case 0xd8:
            case 0xd9:
            case 0xda:
                if (CalculateUtil.byteToInt(recvData[3]) == 0x00) {
                    showToast("正在识别");
                } else if (CalculateUtil.byteToInt(recvData[3]) == 0x01) {
                    showToast("识别成功");
                }
                break;

            case 0xa2:
                final int imageRes = CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(recvData[3]));
                if (imageRes != -1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivCheckMajiang.setImageResource(imageRes);
                            ivCheckMajiang.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ivCheckMajiang.setVisibility(View.GONE);
                        }
                    });
                }
                break;

            case 0xa3:
                showToast("打色成功");
                break;

            case 0xa4:
                showToast("遥控器学习成功");
                break;

            case 0xa5:
                final TextView[] tvDirectionArr = new TextView[] {tvChannelTestEast, tvChannelTestSouth,
                    tvChannelTestWest, tvChannelTestNorth};
                for (int i = 0; i < 4; i++) {
                    int resource = CalculateUtil.byteToInt(recvData[3 + i]) == 0x01
                            ? R.drawable.green_dot : R.drawable.red_dot;
                    final Drawable drawable = getResources().getDrawable(resource);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

                    final int j = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvDirectionArr[j].setCompoundDrawables(null, null, null, drawable);
                        }
                    });
                }
                final int channelNo = CalculateUtil.byteToInt(recvData[7]);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvChannelNo.setText(String.valueOf(channelNo));
                        int majiangRes = CalculateUtil.getMajiangImage(channelNo);
                        if (majiangRes != -1) {
                            ivChannelTestMajiang.setImageResource(majiangRes);
                            ivChannelTestMajiang.setVisibility(View.VISIBLE);
                        } else {
                            ivChannelTestMajiang.setVisibility(View.GONE);
                        }
                    }
                });

                break;

            case 0xa6:
                showToast("刹车时间设置成功");
                break;

            case 0xa7:
                showToast("打色延迟设置成功");
                break;
        }
    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, StudyTestActivity.class);
        context.startActivity(intent);
    }
}
