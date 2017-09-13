package wzp.project.majiang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.EditPlayMethodActivity;
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

    private Button btnModifyPlayMethod;

    private int method;
    private PlayMethodParameter parameter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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

        btnModifyPlayMethod = (Button) view.findViewById(R.id.btn_modifyPlayMethod);

        btnModifyPlayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditPlayMethodActivity.myStartActivity(getContext(), method);
            }
        });


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
        tvLayerNum.setText(bp.isThreeLayer() ? "三层" : "两层");
        tvMachineGear.setText(getResources().getStringArray(R.array.machine_gear_arr)[bp.getMachineGear()]);


    }
}
