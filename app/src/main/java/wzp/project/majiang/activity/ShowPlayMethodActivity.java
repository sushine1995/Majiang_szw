package wzp.project.majiang.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BluetoothBaseActivity;
import wzp.project.majiang.adapter.ShowPlayMethodVpAdapter;
import wzp.project.majiang.constant.PlayMethod;
import wzp.project.majiang.constant.ProjectConstants;
import wzp.project.majiang.entity.BasicParameter;
import wzp.project.majiang.entity.ChooseCardMethod;
import wzp.project.majiang.entity.ChooseCardParameter;
import wzp.project.majiang.entity.DiceParameter;
import wzp.project.majiang.entity.PlayMethodParameter;
import wzp.project.majiang.entity.SingleChooseCardMethod;
import wzp.project.majiang.fragment.ShowPlayMethodFragment;
import wzp.project.majiang.util.CRC16;
import wzp.project.majiang.util.CalculateUtil;
import wzp.project.majiang.widget.MyApplication;
import wzp.project.majiang.widget.MyProgressDialog;
import wzp.project.majiang.widget.SaveAsDialog;

import static wzp.project.majiang.widget.MyApplication.getContext;

/**
 * Created by wzp on 2017/8/28.
 */

public class ShowPlayMethodActivity extends BluetoothBaseActivity {

    private ImageButton ibtnBack;
    private Button btnSave;
    private Button btnSaveAs;
    private Button btnReadFile;
    private Button btnDownload;

    private TabLayout tabRecord;
    private ViewPager vpPlayMethod;

    private AlertDialog dlgExit;

    private List<Fragment> fragmentList;
    private ShowPlayMethodVpAdapter playMethodVpAdapter;

    private SaveAsDialog dlgSaveAs;
    private MyProgressDialog dlgProgress;

    private static  final int REQUEST_READ_WRITE_EXTERNAL_STORAGE = 0x02;
    private static  final int REQUEST_RECV_SEND_FILE = 0x22;


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ibtn_back:
                    onBackPressed();
                    break;

                case R.id.btn_save:
                    savePlayMethod();
                    break;

                case R.id.btn_saveAs:
                    dlgSaveAs.show();
                    break;

                case R.id.btn_readFile:
                    ReceiveSendFileActivity.myStartActivityForResult(ShowPlayMethodActivity.this, REQUEST_RECV_SEND_FILE);
                    break;

                case R.id.btn_download:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        dlgProgress.show("参数设置中，请稍后...");

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                byte[] sendMsg = new byte[ProjectConstants.SET_PARAMETER_MSG_LENGTH];
                                int i = 0;
                                // 玩法参数
                                PlayMethodParameter parameter = null;
                                BasicParameter bp = null;
                                ChooseCardParameter ccp = null;
                                DiceParameter dp = null;
                                for (int j = 0; j < MyApplication.getParameterList().size(); j++) {
                                    i = 0;
                                    Arrays.fill(sendMsg, (byte) 0x00);

                                    // 报文头
                                    sendMsg[i++] = (byte) 0xf1;
                                    // 功能码
                                    sendMsg[i++] = (byte) 0x0d;
                                    // 玩法编号
                                    sendMsg[i++] = (byte) (j + 1);
                                    // 报文序列号，7个字节（暂定为0x00）
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;

                                    parameter = MyApplication.getParameterList().get(j);

                                    // 基本方式
                                    bp = parameter.getBasicParameter();
                                    sendMsg[i++] = Byte.parseByte(getResources().getStringArray(R.array.player_num_arr)[bp.getPlayerNum()]);
                                    sendMsg[i++] = Byte.parseByte(getResources().getStringArray(R.array.every_hand_num_arr)[bp.getEveryHandCardNum()]);
                                    sendMsg[i++] = Byte.parseByte(getResources().getStringArray(R.array.banker_card_num_arr)[bp.getBankerCardNum()]);
                                    sendMsg[i++] = Byte.parseByte(getResources().getStringArray(R.array.other_player_card_num_arr)[bp.getOtherPlayerCardNum()]);
                                    sendMsg[i++] = (byte) bp.getBankerSkip();
                                    sendMsg[i++] = (byte) bp.getGetCardMethod();
                                    sendMsg[i++] = Byte.parseByte(getResources().getStringArray(R.array.program_start_times_arr)[bp.getProgramStartTimes()]);
                                    sendMsg[i++] = Byte.parseByte(getResources().getStringArray(R.array.program_stop_times_arr)[bp.getProgramStopTimes()]);
                                    sendMsg[i++] = Byte.parseByte(getResources().getStringArray(R.array.continuous_work_rounds_arr)[bp.getContinuousWorkRound()]);
                                    sendMsg[i++] = (byte) (Integer.parseInt(getResources().getStringArray(R.array.total_rounds_arr)[bp.getTotalUseRound()]) / 256);
                                    sendMsg[i++] = (byte) (Integer.parseInt(getResources().getStringArray(R.array.total_rounds_arr)[bp.getTotalUseRound()]) % 256);
                                    sendMsg[i++] = Byte.parseByte(getResources().getStringArray(R.array.broadcast_card_num_arr)[bp.getBroadcastCardNum()]);
                                    sendMsg[i++] = bp.isVoiceBox() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isMachineHeadPosition() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isPanelInduction() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isMoneyBoxPosition() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isContinuousBroadcastCard() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isAssignedIDCardPosition() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isBroadcastWinCard() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isUseDiceTest() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isPengZhuanBroadcastCard() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isResetTest() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isDicePanelPositionNotification() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isBloodFight() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isDicePanelTroubleNotification() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isDigitScreenSwitch() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isThreePlayer() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = bp.isThreeLayer() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = (byte) bp.getTotalCardNum();
                                    sendMsg[i++] = (byte) (bp.getEastTop() + bp.getEastMiddle() + bp.getEastBottom());
                                    sendMsg[i++] = (byte) (bp.getSouthTop() + bp.getSouthMiddle() + bp.getSouthBottom());
                                    sendMsg[i++] = (byte) (bp.getWestTop() + bp.getWestMiddle() + bp.getWestBottom());
                                    sendMsg[i++] = (byte) (bp.getNorthTop() + bp.getNorthMiddle() + bp.getNorthBottom());
                                    sendMsg[i++] = (byte) bp.getEastTop();
                                    sendMsg[i++] = (byte) bp.getEastMiddle();
                                    sendMsg[i++] = (byte) bp.getEastBottom();
                                    sendMsg[i++] = (byte) bp.getSouthTop();
                                    sendMsg[i++] = (byte) bp.getSouthMiddle();
                                    sendMsg[i++] = (byte) bp.getSouthBottom();
                                    sendMsg[i++] = (byte) bp.getWestTop();
                                    sendMsg[i++] = (byte) bp.getWestMiddle();
                                    sendMsg[i++] = (byte) bp.getWestBottom();
                                    sendMsg[i++] = (byte) bp.getNorthTop();
                                    sendMsg[i++] = (byte) bp.getNorthMiddle();
                                    sendMsg[i++] = (byte) bp.getNorthBottom();

                                    // 备用，4个字节
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;


                                    // 选牌方式
                                    ccp = parameter.getChooseCardParameter();
                                    List<ChooseCardMethod> ccmList = ccp.getMethods();
                                    ChooseCardMethod ccm = null;
                                    List<SingleChooseCardMethod> sccmList = null;
                                    SingleChooseCardMethod sccm = null;
                                    int k = 0;
                                    for (; k < ccmList.size(); k++) {
                                        ccm = ccmList.get(k);

                                        sendMsg[i++] = (byte) (ccm.getLoopTimes() + 1);
                                        sccmList = ccm.getMethods();
                                        int l = 0;
                                        for (; l < sccmList.size(); l++) {
                                            sccm = sccmList.get(l);

                                            sendMsg[i++] = (byte) (sccm.getName() + 1);
                                            sendMsg[i++] = (byte) sccm.getNum();
                                            sendMsg[i++] = (byte) sccm.getSpecialRule();
                                        }
                                        while (l < 6) {
                                            for (int m = 0; m < 3; m++) {
                                                sendMsg[i++] = (byte) 0x00;
                                            }

                                            l++;
                                        }
                                    }
                                    while (k < 6) {
                                        for (int n = 0; n < 19; n++) {
                                            sendMsg[i++] = (byte) 0x00;
                                        }

                                        k++;
                                    }
                                    // 备用，4个字节
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;


                                    // 色子参数
                                    dp = parameter.getDiceParameter();
                                    sendMsg[i++] = (byte) (dp.getDiceNum() + 1);
                                    sendMsg[i++] = (byte) (dp.getUseDiceTimes() + 1);
                                    sendMsg[i++] = (byte) dp.getUseDiceMethod();
                                    sendMsg[i++] = (byte) dp.getStartCardMethod();
                                    sendMsg[i++] = (byte) dp.getStartCardSupplementFlowerMethod();
                                    sendMsg[i++] = dp.isOneFiveNineGetCard() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isEastSouthWestNorthAsColorCard() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isZhongFaBaiAsColorCard() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isAllWindCardAsColorCard() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isBankerAndLastPlayerChangePosition() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isOpenWealthGodMode() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = (byte) dp.getWealthGodStartCardMethod();
                                    sendMsg[i++] = (byte) dp.getWealthGodUseDiceMethod();
                                    sendMsg[i++] = (byte) dp.getWealthGodCondition();
                                    sendMsg[i++] = (byte) dp.getWindCardWealthGodLoopMethod();
                                    sendMsg[i++] = (byte) dp.getFixedWealthGod();
                                    sendMsg[i++] = (byte) dp.getWealthGodLastBlockNum();
                                    sendMsg[i++] = (byte) dp.getWealthGodStartCardPosition();
                                    sendMsg[i++] = (byte) (dp.getWealthGodPrecedenceNum() + 1);
                                    sendMsg[i++] = dp.isZhongAsFixedWealthGod() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isColorCardAsFixedWealthGod() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isYiTiaoAsFixedWealthGod() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isBaiBanAsFixedWealthGod() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isYaojiufeng() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isYaojiufengSuanGan() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isBaibanAsWealthGodSubstitute() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isFanpaifengpaiAsWealthGod() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.is13579() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isEastSouthWestNorthOrZhongFaBaiBusuandacha() ? (byte) 0x01 : (byte) 0x00;
                                    sendMsg[i++] = dp.isWealthGodIsEastWind() ? (byte) 0x01 : (byte) 0x00;

                                    // 备用，5个字节
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;
                                    sendMsg[i++] = (byte) 0x00;

                                    Log.d(LOG_TAG, "i = " + i + "; len = " + sendMsg.length);
//                            Log.d(LOG_TAG, Arrays.toString(sendMsg));

                                    byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SET_PARAMETER_MSG_LENGTH - 2);
                                    sendMsg[i++] = crc[0];
                                    sendMsg[i++] = crc[1];

                                    // 发完一条报文，间隔1000ms，再发另一条
                                    MyApplication.btClientHelper.write(sendMsg);
                                    try {
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        showToast("数据发送异常");
                                    }
                                }
                            }
                        }, "download thread").start();
                    } else {
                        showToast("蓝牙尚未连接，程序烧录失败！");
                    }
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        dlgExit.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_WRITE_EXTERNAL_STORAGE);
        } else {
            setContentView(R.layout.activity_show_play_method);

            initData();
            initWidget();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setContentView(R.layout.activity_show_play_method);

                initData();
                initWidget();
            } else {
                Toast.makeText(getContext(), "系统拒绝读写外部文件，请到系统设置-权限管理中，打开此权限", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECV_SEND_FILE) {
            if (resultCode == RESULT_OK) {
                for (int i = 0; i < fragmentList.size(); i++) {
                    ((ShowPlayMethodFragment) fragmentList.get(i)).updateParameter();
                }
            }
        }
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        Fragment fragment1 = new ShowPlayMethodFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("method", PlayMethod.NO_1);
        fragment1.setArguments(bundle);
        fragmentList.add(fragment1);

        Fragment fragment2 = new ShowPlayMethodFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("method", PlayMethod.NO_2);
        fragment2.setArguments(bundle2);
        fragmentList.add(fragment2);

        Fragment fragment3 = new ShowPlayMethodFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("method", PlayMethod.NO_3);
        fragment3.setArguments(bundle3);
        fragmentList.add(fragment3);

        playMethodVpAdapter = new ShowPlayMethodVpAdapter(getSupportFragmentManager(), fragmentList);

        File basePath = new File(ProjectConstants.baseFilePath);
        if (!basePath.exists()) {
            basePath.mkdirs();
        }
    }

    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSaveAs = (Button) findViewById(R.id.btn_saveAs);
        btnReadFile = (Button) findViewById(R.id.btn_readFile);
        btnDownload = (Button) findViewById(R.id.btn_download);

        tabRecord = (TabLayout) findViewById(R.id.tab_record);
        vpPlayMethod = (ViewPager) findViewById(R.id.vp_playMethod);

        tabRecord.setupWithViewPager(vpPlayMethod);
        vpPlayMethod.setAdapter(playMethodVpAdapter);
        vpPlayMethod.setOffscreenPageLimit(2);

        dlgExit = new AlertDialog.Builder(this)
                .setTitle("注意")
                .setMessage("是否保存数据？")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savePlayMethod();
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create();

        dlgSaveAs = new SaveAsDialog(this);
        dlgSaveAs.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dlgSaveAs.isFilenameValidate()) {
                    savePlayMethodParameterAs(dlgSaveAs.getFilename());
                } else {
                    showToast("文件名只能包含数字、中英文字符、下划线！");
                }

                dlgSaveAs.dismiss();
            }
        });

        dlgProgress = new MyProgressDialog(this);

        ibtnBack.setOnClickListener(listener);
        btnSave.setOnClickListener(listener);
        btnSaveAs.setOnClickListener(listener);
        btnReadFile.setOnClickListener(listener);
        btnDownload.setOnClickListener(listener);
    }

    /**
     * 保存玩法参数
     */
    private void savePlayMethod() {
        for (Fragment fragment : fragmentList) {
            ((ShowPlayMethodFragment) fragment).savePlayMethod();
        }
        showToast("参数保存成功！");
    }

    /**
     * 另存玩法参数
     *
     * @param filename
     */
    private void savePlayMethodParameterAs(String filename) {
        List<PlayMethodParameter> parameterList = new ArrayList<>();
        for (Fragment fragment : fragmentList) {
            parameterList.add(((ShowPlayMethodFragment) fragment).getPlayMethodParameter());
            ((ShowPlayMethodFragment) fragment).savePlayMethod();
        }

        File file = new File(ProjectConstants.baseFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        filename = ProjectConstants.baseFilePath + "/" + filename + ".mj";

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.newDocument();
            Element root = doc.createElement("map");

            for (int i = 0; i < fragmentList.size(); i++) {
                Element playMethod = doc.createElement("string");
                playMethod.setAttribute("name", "playMethod" + (i + 1));
                playMethod.setTextContent(JSON.toJSONString(parameterList.get(i)));
                root.appendChild(playMethod);
            }
            doc.appendChild(root);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transFormer = transFactory.newTransformer();
            transFormer.setOutputProperty("encoding", "UTF-8");
            DOMSource domSource = new DOMSource(doc);
            FileOutputStream out = new FileOutputStream(filename);
            StreamResult xmlResult = new StreamResult(out);
            transFormer.transform(domSource, xmlResult);

            showToast("保存成功");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            showToast("保存失败");
        } catch (IOException e) {
            e.printStackTrace();
            showToast("保存失败");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            showToast("保存失败");
        } catch (TransformerException e) {
            e.printStackTrace();
            showToast("保存失败");
        }
    }

    @Override
    protected void onBluetoothDataReceived(byte[] recvData) {
        switch (CalculateUtil.byteToInt(recvData[1])) {
            case 0x01:
                showToast("参数设置成功");
                dlgProgress.dismiss();
                break;
        }
    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, ShowPlayMethodActivity.class);
        context.startActivity(intent);
    }
}
