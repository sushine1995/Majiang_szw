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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.EditPlayMethodActivity;
import wzp.project.majiang.adapter.ParamListAdapter;
import wzp.project.majiang.entity.BasicParameter;
import wzp.project.majiang.entity.ChooseCardMethod;
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

    private TextView tvDiceNum;
    private TextView tvUseDiceTimes;
    private TextView tvUseDiceMethod;
    private TextView tvStartCardMethod;
    private TextView tvStartCardSupplementFlowerMethod;
    private RecyclerView rvDiceParameter;
    private TextView tvWealthGodMode;
    private LinearLayout linearWealthGodMode;
    private TextView tvWealthGodStartCardMethod;
    private TextView tvWealthGodUseDiceMethod;
    private TextView tvWealthGodCondition;
    private TextView tvWindCardWealthGodLoopMethod;
    private TextView tvFixedWealthGod;
    private TextView tvWealthGodLastBlockNum;
    private TextView tvWealthGodStartCardPosition;
    private TextView tvWealthGodPrecedenceNum;
    private RecyclerView rvWealthGodParam;


    private Button btnModifyPlayMethod;

    private int method;
    private PlayMethodParameter parameter;

    private ParamListAdapter basicParamAdapter;
    private List<String> basicParamList = new ArrayList<>();
    private ParamListAdapter diceParamAdapter;
    private List<String> diceParamList = new ArrayList<>();
    private ParamListAdapter wealthGodParamAdapter;
    private List<String> wealthGodParamList = new ArrayList<>();

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
            ccp.setMethods(new ArrayList<ChooseCardMethod>());
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

        tvDiceNum = (TextView) view.findViewById(R.id.tv_diceNum);
        tvUseDiceTimes = (TextView) view.findViewById(R.id.tv_useDiceTimes);
        tvUseDiceMethod = (TextView) view.findViewById(R.id.tv_useDiceMethod);
        tvStartCardMethod = (TextView) view.findViewById(R.id.tv_startCardMethod);
        tvStartCardSupplementFlowerMethod = (TextView) view.findViewById(R.id.tv_startCardSupplementFlowerMethod);
        rvDiceParameter = (RecyclerView) view.findViewById(R.id.rv_diceParameter);
        tvWealthGodMode = (TextView) view.findViewById(R.id.tv_wealthGodMode);
        linearWealthGodMode = (LinearLayout) view.findViewById(R.id.linear_wealthGodMode);
        tvWealthGodStartCardMethod = (TextView) view.findViewById(R.id.tv_wealthGodStartCardMethod);
        tvWealthGodUseDiceMethod = (TextView) view.findViewById(R.id.tv_wealthGodUseDiceMethod);
        tvWealthGodCondition = (TextView) view.findViewById(R.id.tv_wealthGodCondition);
        tvWindCardWealthGodLoopMethod = (TextView) view.findViewById(R.id.tv_windCardWealthGodLoopMethod);
        tvFixedWealthGod = (TextView) view.findViewById(R.id.tv_fixedWealthGod);
        tvWealthGodLastBlockNum = (TextView) view.findViewById(R.id.tv_wealthGodLastBlockNum);
        tvWealthGodStartCardPosition = (TextView) view.findViewById(R.id.tv_wealthGodStartCardPosition);
        tvWealthGodPrecedenceNum = (TextView) view.findViewById(R.id.tv_wealthGodPrecedenceNum);
        rvWealthGodParam = (RecyclerView) view.findViewById(R.id.rv_wealthGodParam);

        btnModifyPlayMethod = (Button) view.findViewById(R.id.btn_modifyPlayMethod);


        basicParamAdapter = new ParamListAdapter(getContext(), basicParamList);
        rvBasicParameter.setAdapter(basicParamAdapter);
        rvBasicParameter.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        diceParamAdapter = new ParamListAdapter(getContext(), diceParamList);
        rvDiceParameter.setAdapter(diceParamAdapter);
        rvDiceParameter.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        wealthGodParamAdapter = new ParamListAdapter(getContext(), wealthGodParamList);
        rvWealthGodParam.setAdapter(wealthGodParamAdapter);
        rvWealthGodParam.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        btnModifyPlayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


        DiceParameter dp = parameter.getDiceParameter();
        tvDiceNum.setText(getResources().getStringArray(R.array.dice_num_arr)[dp.getDiceNum()]);
        tvUseDiceTimes.setText(getResources().getStringArray(R.array.use_dice_times_arr)[dp.getUseDiceTimes()]);
        tvUseDiceMethod.setText(getResources().getStringArray(R.array.use_dice_method_arr)[dp.getUseDiceMethod()]);
        tvStartCardMethod.setText(getResources().getStringArray(R.array.first_dice_position_send_card_method_arr)[dp.getStartCardMethod()]);
        tvStartCardSupplementFlowerMethod.setText(getResources().getStringArray(R.array.start_card_supplement_flower_method_arr)[dp.getStartCardSupplementFlowerMethod()]);

        diceParamList.clear();
        if (dp.isOneFiveNineGetCard()) {
            diceParamList.add("打色一、五、九对家抓牌");
        }
        if (dp.isEastSouthWestNorthAsColorCard()) {
            diceParamList.add("东南西北当花牌");
        }
        if (dp.isZhongFaBaiAsColorCard()) {
            diceParamList.add("中发白当花牌");
        }
        if (dp.isAllWindCardAsColorCard()) {
            diceParamList.add("所有风牌当花牌");
        }
        if (dp.isBankerAndLastPlayerChangePosition()) {
            diceParamList.add("胡牌庄家与上家换位置");
        }
        diceParamAdapter.notifyDataSetChanged();
        if (diceParamList.size() == 0) {
            rvDiceParameter.setVisibility(View.GONE);
        } else {
            rvDiceParameter.setVisibility(View.VISIBLE);
        }

        tvWealthGodMode.setText(dp.isOpenWealthGodMode() ? "开启" : "关闭");
        linearWealthGodMode.setVisibility(dp.isOpenWealthGodMode() ? View.VISIBLE : View.GONE);
        tvWealthGodStartCardMethod.setText(getResources().getStringArray(R.array.wealth_god_start_card_method_arr)[dp.getWealthGodStartCardMethod()]);
        tvWealthGodUseDiceMethod.setText(getResources().getStringArray(R.array.wealth_god_use_dice_method_arr)[dp.getWealthGodUseDiceMethod()]);
        tvWealthGodCondition.setText(getResources().getStringArray(R.array.wealth_god_condition_arr)[dp.getWealthGodCondition()]);
        tvWindCardWealthGodLoopMethod.setText(getResources().getStringArray(R.array.wind_card_wealth_god_loop_method_arr)[dp.getWindCardWealthGodLoopMethod()]);
        tvFixedWealthGod.setText(getResources().getStringArray(R.array.fixed_wealth_god_arr)[dp.getFixedWealthGod()]);
        tvWealthGodLastBlockNum.setText(getResources().getStringArray(R.array.wealth_god_last_block_num_arr)[dp.getWealthGodLastBlockNum()]);
        tvWealthGodStartCardPosition.setText(getResources().getStringArray(R.array.wealth_god_start_card_position_arr)[dp.getWealthGodStartCardPosition()]);
        tvWealthGodPrecedenceNum.setText(getResources().getStringArray(R.array.wealth_god_precedence_num_arr)[dp.getWealthGodPrecedenceNum()]);

        wealthGodParamList.clear();
        if (dp.isZhongAsFixedWealthGod()) {
            wealthGodParamList.add("红中为固定财神");
        }
        if (dp.isColorCardAsFixedWealthGod()) {
            wealthGodParamList.add("花牌为固定财神");
        }
        if (dp.isYiTiaoAsFixedWealthGod()) {
            wealthGodParamList.add("一条为固定财神");
        }
        if (dp.isBaiBanAsFixedWealthGod()) {
            wealthGodParamList.add("白板为固定财神");
        }
        if (dp.isYaojiufeng()) {
            wealthGodParamList.add("幺九风");
        }
        if (dp.isYaojiufengSuanGan()) {
            wealthGodParamList.add("幺九风算杆");
        }
        if (dp.isBaibanAsWealthGodSubstitute()) {
            wealthGodParamList.add("白板为财神替身");
        }
        if (dp.isFanpaifengpaiAsWealthGod()) {
            wealthGodParamList.add("翻牌风牌自身为财神");
        }
        if (dp.is13579()) {
            wealthGodParamList.add("13579任意三张");
        }
        if (dp.isEastSouthWestNorthOrZhongFaBaiBusuandacha()) {
            wealthGodParamList.add("东南西北/中发白不算大岔");
        }
        wealthGodParamAdapter.notifyDataSetChanged();
        if (wealthGodParamList.size() == 0) {
            rvWealthGodParam.setVisibility(View.GONE);
        } else {
            rvWealthGodParam.setVisibility(View.VISIBLE);
        }
    }

    public void savePlayMethod() {
        MyApplication.getSpPlayMethod().commitString("playMethod" + method,
                JSON.toJSONString(parameter));
    }
}
