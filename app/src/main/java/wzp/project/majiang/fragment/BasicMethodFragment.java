package wzp.project.majiang.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.EditPlayMethodActivity;
import wzp.project.majiang.entity.BasicParameter;
import wzp.project.majiang.widget.ListOptionButton;

/**
 * Created by wzp on 2017/8/29.
 */

public class BasicMethodFragment extends Fragment {

    private EditPlayMethodActivity activity;

    private ListOptionButton btnPlayerNum;
    private ListOptionButton btnEveryHandCardNum;
    private ListOptionButton btnBankerCardNum;
    private ListOptionButton btnOtherPlayerCardNum;
    private ListOptionButton btnBankerSkip;
    private ListOptionButton btnGetCardMethod;
    private ListOptionButton btnProgramStartTimes;
    private ListOptionButton btnProgramStopTimes;
    private ListOptionButton btnContinuousWorkRound;
    private ListOptionButton btnTotalUseRound;
    private ListOptionButton btnBroadcastCardNum;
    private CheckBox cbVoiceBox;
    private CheckBox cbMachineHeadPosition;


    private BasicParameter basicParameter;



    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_playerNum:

                    break;

            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (EditPlayMethodActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_method, container, false);

        initData();
        initWidget(view);

        return view;
    }

    private void initData() {
        basicParameter = activity.getBasicParameter();
    }

    private void initWidget(View view) {
        btnPlayerNum = (ListOptionButton) view.findViewById(R.id.btn_playerNum);
        btnEveryHandCardNum = (ListOptionButton) view.findViewById(R.id.btn_everyHandNum);
        btnBankerCardNum = (ListOptionButton) view.findViewById(R.id.btn_bankerCardNum);
        btnOtherPlayerCardNum = (ListOptionButton) view.findViewById(R.id.btn_otherPlayerCardNum);
        btnBankerSkip = (ListOptionButton) view.findViewById(R.id.btn_bankerSkip);
        btnGetCardMethod = (ListOptionButton) view.findViewById(R.id.btn_getCardMethod);
        btnProgramStartTimes = (ListOptionButton) view.findViewById(R.id.btn_programStartTimes);
        btnProgramStopTimes = (ListOptionButton) view.findViewById(R.id.btn_programStopTimes);
        btnContinuousWorkRound = (ListOptionButton) view.findViewById(R.id.btn_continuousWorkingRound);
        btnTotalUseRound = (ListOptionButton) view.findViewById(R.id.btn_totalUseRound);
        btnBroadcastCardNum = (ListOptionButton) view.findViewById(R.id.btn_broadcastCardNum);
        cbVoiceBox = (CheckBox) view.findViewById(R.id.cb_voiceBox);
        cbMachineHeadPosition = (CheckBox) view.findViewById(R.id.cb_machineHeadPosition);


        btnPlayerNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setPlayerNum(position);
            }
        });
        btnEveryHandCardNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setEveryHandCardNum(position);
            }
        });
        btnBankerCardNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setBankerCardNum(position);
            }
        });
        btnOtherPlayerCardNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setOtherPlayerCardNum(position);
            }
        });
        btnBankerSkip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setBankerSkip(position);
            }
        });
        btnGetCardMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setGetCardMethod(position);
            }
        });
        btnProgramStartTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setProgramStartTimes(position);
            }
        });
        btnProgramStopTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setProgramStopTimes(position);
            }
        });
        btnContinuousWorkRound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setContinuousWorkRound(position);
            }
        });
        btnTotalUseRound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setTotalUseRound(position);
            }
        });
        btnBroadcastCardNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setBroadcastCardNum(position);
            }
        });
        cbVoiceBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setVoiceBox(isChecked);
            }
        });
        cbMachineHeadPosition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setMachineHeadPosition(isChecked);
            }
        });


        // 初始化控件参数
        btnPlayerNum.setSelectedItemPosition(basicParameter.getPlayerNum());
        btnEveryHandCardNum.setSelectedItemPosition(basicParameter.getEveryHandCardNum());
        btnBankerCardNum.setSelectedItemPosition(basicParameter.getBankerCardNum());
        btnOtherPlayerCardNum.setSelectedItemPosition(basicParameter.getOtherPlayerCardNum());
        btnBankerSkip.setSelectedItemPosition(basicParameter.getBankerSkip());
        btnGetCardMethod.setSelectedItemPosition(basicParameter.getGetCardMethod());
        btnProgramStartTimes.setSelectedItemPosition(basicParameter.getProgramStartTimes());
        btnProgramStopTimes.setSelectedItemPosition(basicParameter.getProgramStopTimes());
        btnContinuousWorkRound.setSelectedItemPosition(basicParameter.getContinuousWorkRound());
        btnTotalUseRound.setSelectedItemPosition(basicParameter.getTotalUseRound());
        btnBroadcastCardNum.setSelectedItemPosition(basicParameter.getBroadcastCardNum());
        cbVoiceBox.setChecked(basicParameter.isVoiceBox());
    }

//    public void updateParameter() {
//        basicParameter.setPlayerNum(btnChoosePlayerNum.getSelectedItemPosition());
//    }
}
