package wzp.project.majiang.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.EditPlayMethodActivity;
import wzp.project.majiang.entity.DiceParameter;
import wzp.project.majiang.widget.ListOptionButton;
import wzp.project.majiang.widget.ListOptionPopupWindow;
import wzp.project.majiang.widget.ShowFunctionTipPopupWindow;

/**
 * Created by wzp on 2017/8/29.
 */

public class SetDiceFragment extends Fragment {

    private EditPlayMethodActivity activity;

    private TextView tvPlayerNum;
    private Button btnChoosePlayerNum;
    private ListOptionButton btnChooseMajiangNum;
    private Button btnEastTop;


    private ShowFunctionTipPopupWindow pwShowFunTip;
    private ListOptionPopupWindow pwListOption;

    private DiceParameter diceParameter;



    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_playerNum:
                    pwShowFunTip.setFunTip(R.string.player_num_fun_tip);
                    pwShowFunTip.showAsDropDown(tvPlayerNum);
                    break;

                case R.id.btn_choosePlayerNum:
                    pwListOption.showAsDropDown(btnChoosePlayerNum);
                    break;

                case R.id.btn_eastTop:
                    pwListOption.showAsDropDown(btnEastTop);
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
        View view = inflater.inflate(R.layout.fragment_set_dice, container, false);

        initData();
        initWidget(view);

        return view;
    }

    private void initData() {
        diceParameter = activity.getDiceParameter();
    }

    private void initWidget(View view) {


    }

}
