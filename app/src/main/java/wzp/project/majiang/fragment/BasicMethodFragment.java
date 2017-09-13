package wzp.project.majiang.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.EditPlayMethodActivity;
import wzp.project.majiang.entity.BasicParameter;
import wzp.project.majiang.widget.ListOptionButton;

/**
 * Created by wzp on 2017/8/29.
 */

public class BasicMethodFragment extends Fragment {

    private EditPlayMethodActivity activity;

    private ListOptionButton btnChoosePlayerNum;
    private ListOptionButton btnChooseEveryHandCardNum;
    private ListOptionButton btnBankerCardNum;
    private ListOptionButton btnChooseMajiangNum;


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
        btnChoosePlayerNum = (ListOptionButton) view.findViewById(R.id.btn_choosePlayerNum);
        btnChooseEveryHandCardNum = (ListOptionButton) view.findViewById(R.id.btn_chooseEveryHandNum);
        btnBankerCardNum = (ListOptionButton) view.findViewById(R.id.btn_bankerCardNum);
        btnChooseMajiangNum = (ListOptionButton) view.findViewById(R.id.btn_chooseMajiangNum);



        // 初始化控件参数
        btnChoosePlayerNum.setSelectedItemPosition(basicParameter.getPlayerNum());
        btnChooseEveryHandCardNum.setSelectedItemPosition(basicParameter.getEveryHandCardNum());
        btnBankerCardNum.setSelectedItemPosition(basicParameter.getBankerCardNum());
    }


    public void updateParameter() {
        basicParameter.setPlayerNum(btnChoosePlayerNum.getSelectedItemPosition());
    }
}
