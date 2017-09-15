package wzp.project.majiang.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.EditPlayMethodActivity;
import wzp.project.majiang.adapter.ParamListAdapter;
import wzp.project.majiang.entity.BasicParameter;
import wzp.project.majiang.entity.ChooseCardParameter;
import wzp.project.majiang.entity.DiceParameter;
import wzp.project.majiang.entity.PlayMethodParameter;
import wzp.project.majiang.widget.MyApplication;

/**
 * Created by wzp on 2017/8/28.
 */

public class ShowPlayMethodFragment extends Fragment {

    private TextView tvPlayerNum;
    private TextView tvEveryHandCardNum;
    private TextView tvBankerCardNum;
    private TextView tvOtherPlayerCardNum;
    private TextView tvBankerSkip;
    private TextView tvGetCardMethod;
    private TextView tvProgramStartTimes;
    private TextView tvProgramStopTimes;
    private TextView tvContinuousWorkTimes;
    private TextView tvTotalRounds;
    private TextView tvBroadcastCardNum;
    private TextView tvLayerNum;
    private TextView tvMachineGear;
    private TextView tvTotalNum;
    private TextView tvEastTop;
    private TextView tvEastMiddle;
    private TextView tvEastBottom;
    private TextView tvSouthTop;
    private TextView tvSouthMiddle;
    private TextView tvSouthBottom;
    private TextView tvWestTop;
    private TextView tvWestMiddle;
    private TextView tvWestBottom;
    private TextView tvNorthTop;
    private TextView tvNorthMiddle;
    private TextView tvNorthBottom;

    private RecyclerView rvBasicParameter;

    private Button btnModifyPlayMethod;

    private int method;
    private PlayMethodParameter parameter;

    private ParamListAdapter basicParamAdapter;
    private List<String> basicParamList = new ArrayList<>();

    private static final int REQUEST_EDIT_PLAY_METHOD = 0x01;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ShowPlayMethodFragment", "onActivityResult");
        switch (requestCode) {
            case REQUEST_EDIT_PLAY_METHOD:
                if (resultCode == Activity.RESULT_OK) {
                    parameter = JSON.parseObject(data.getStringExtra("parameter"),
                            PlayMethodParameter.class);
                    updateParameter();
                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("ShowPlayMethodFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_show_play_method, container, false);

        initData();
        initWidget(view);

        return view;
    }

    private void initData() {
        method = getArguments().getInt("method");

        String playMethod = MyApplication.getSpPlayMethod().getString("playMethod" + method, "");
        if (playMethod.equals("")) {
            // 设置默认的参数值
            parameter = new PlayMethodParameter();

            BasicParameter bp = new BasicParameter();
            parameter.setBasicParameter(bp);

            ChooseCardParameter ccp = new ChooseCardParameter();
            parameter.setChooseCardParameter(ccp);

            DiceParameter dp = new DiceParameter();
            parameter.setDiceParameter(dp);

            MyApplication.getSpPlayMethod().commitString("playMethod" + method,
                    JSON.toJSONString(parameter));
        } else {
            parameter = JSON.parseObject(playMethod, PlayMethodParameter.class);
        }
    }

    private void initWidget(View view) {
        tvPlayerNum = (TextView) view.findViewById(R.id.tv_playerNum);
        tvEveryHandCardNum = (TextView) view.findViewById(R.id.tv_everyHandCardNum);
        tvOtherPlayerCardNum = (TextView) view.findViewById(R.id.tv_otherPlayerCardNum);
        tvBankerCardNum = (TextView) view.findViewById(R.id.tv_bankerCardNum);
        tvBankerSkip = (TextView) view.findViewById(R.id.tv_bankerSkip);
        tvGetCardMethod = (TextView) view.findViewById(R.id.tv_getCardMethod);
        tvProgramStartTimes = (TextView) view.findViewById(R.id.tv_programStartTimes);
        tvProgramStopTimes = (TextView) view.findViewById(R.id.tv_programStopTimes);
        tvContinuousWorkTimes = (TextView) view.findViewById(R.id.tv_continuousWorkRounds);
        tvTotalRounds = (TextView) view.findViewById(R.id.tv_totalRounds);
        tvBroadcastCardNum = (TextView) view.findViewById(R.id.tv_broadcastCardNum);
        tvLayerNum = (TextView) view.findViewById(R.id.tv_layerNum);
        tvMachineGear = (TextView) view.findViewById(R.id.tv_machineGear);
        tvTotalNum = (TextView) view.findViewById(R.id.tv_totalNum);
        tvEastTop = (TextView) view.findViewById(R.id.tv_eastTop);
        tvEastMiddle = (TextView) view.findViewById(R.id.tv_eastMiddle);
        tvEastBottom = (TextView) view.findViewById(R.id.tv_eastBottom);
        tvSouthTop = (TextView) view.findViewById(R.id.tv_southTop);
        tvSouthMiddle = (TextView) view.findViewById(R.id.tv_southMiddle);
        tvSouthBottom = (TextView) view.findViewById(R.id.tv_southBottom);
        tvWestTop = (TextView) view.findViewById(R.id.tv_westTop);
        tvWestMiddle = (TextView) view.findViewById(R.id.tv_westMiddle);
        tvWestBottom = (TextView) view.findViewById(R.id.tv_westBottom);
        tvNorthTop = (TextView) view.findViewById(R.id.tv_northTop);
        tvNorthMiddle = (TextView) view.findViewById(R.id.tv_northMiddle);
        tvNorthBottom = (TextView) view.findViewById(R.id.tv_northBottom);

        rvBasicParameter = (RecyclerView) view.findViewById(R.id.rv_basicParameter);

        btnModifyPlayMethod = (Button) view.findViewById(R.id.btn_modifyPlayMethod);

        basicParamAdapter = new ParamListAdapter(getContext(), basicParamList);
        rvBasicParameter.setAdapter(basicParamAdapter);
        rvBasicParameter.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        btnModifyPlayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EditPlayMethodActivity.myStartActivity(getContext(), method);
                Intent intent = new Intent(getContext(), EditPlayMethodActivity.class);
                intent.putExtra("playMethod", method);
                intent.putExtra("parameter", JSON.toJSONString(parameter));
                startActivityForResult(intent, REQUEST_EDIT_PLAY_METHOD);
            }
        });

        updateParameter();
    }

    private void updateParameter() {
        BasicParameter bp = parameter.getBasicParameter();
        tvPlayerNum.setText(getResources().getStringArray(R.array.player_num_arr)[bp.getPlayerNum()]);
        tvEveryHandCardNum.setText(getResources().getStringArray(R.array.every_hand_num_arr)[bp.getEveryHandCardNum()]);
        tvBankerCardNum.setText(getResources().getStringArray(R.array.banker_card_num_arr)[bp.getBankerCardNum()]);
        tvOtherPlayerCardNum.setText(getResources().getStringArray(R.array.other_player_card_num_arr)[bp.getOtherPlayerCardNum()]);
        tvBankerSkip.setText(getResources().getStringArray(R.array.banker_skip_arr)[bp.getBankerSkip()]);
        tvGetCardMethod.setText(getResources().getStringArray(R.array.get_card_method_arr)[bp.getGetCardMethod()]);
        tvProgramStartTimes.setText(getResources().getStringArray(R.array.program_start_times_arr)[bp.getProgramStartTimes()]);
        tvProgramStopTimes.setText(getResources().getStringArray(R.array.program_stop_times_arr)[bp.getProgramStopTimes()]);
        tvContinuousWorkTimes.setText(getResources().getStringArray(R.array.continuous_work_rounds_arr)[bp.getContinuousWorkRound()]);
        tvTotalRounds.setText(getResources().getStringArray(R.array.total_rounds_arr)[bp.getTotalUseRound()]);
        tvBroadcastCardNum.setText(getResources().getStringArray(R.array.broadcast_card_num_arr)[bp.getBroadcastCardNum()]);

        basicParamList.clear();
        if (bp.isVoiceBox()) {
            basicParamList.add("便携式语音盒");
        }
        if (bp.isMachineHeadPosition()) {
            basicParamList.add("机头定位");
        }
        if (bp.isPanelInduction()) {
            basicParamList.add("面板感应");
        }
        if (bp.isMoneyBoxPosition()) {
            basicParamList.add("钱盒定位");
        }
        if (bp.isContinuousBroadcastCard()) {
            basicParamList.add("连续报牌");
        }
        if (bp.isAssignedIDCardPosition()) {
            basicParamList.add("指定ID卡定位");
        }
        if (bp.isBroadcastWinCard()) {
            basicParamList.add("报胡牌");
        }
        if (bp.isUseDiceTest()) {
            basicParamList.add("打色测试");
        }
        if (bp.isPengZhuanBroadcastCard()) {
            basicParamList.add("碰转报牌");
        }
        if (bp.isResetTest()) {
            basicParamList.add("复位测试");
        }
        if (bp.isDicePanelPositionNotification()) {
            basicParamList.add("色盘定位提示");
        }
        if (bp.isBloodFight()) {
            basicParamList.add("血战");
        }
        if (bp.isDicePanelTroubleNotification()) {
            basicParamList.add("色盘故障提示");
        }
        if (bp.isDigitScreenSwitch()) {
            basicParamList.add("数显档位开关");
        }
        if (bp.isThreePlayer()) {
            basicParamList.add("三人玩法三人点数");
        }
        basicParamAdapter.notifyDataSetChanged();
        if (basicParamList.size() == 0) {
            rvBasicParameter.setVisibility(View.GONE);
        } else {
            rvBasicParameter.setVisibility(View.VISIBLE);
        }

        tvLayerNum.setText(bp.isThreeLayer() ? "三层" : "两层");
        tvMachineGear.setText(getResources().getStringArray(R.array.machine_gear_arr)[bp.getMachineGear()]);
    }

    public void savePlayMethod() {
        MyApplication.getSpPlayMethod().commitString("playMethod" + method,
                JSON.toJSONString(parameter));
    }
}
