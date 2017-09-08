package wzp.project.majiang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import wzp.project.majiang.R;
import wzp.project.majiang.widget.ListOptionButton;
import wzp.project.majiang.widget.ShowFunctionTipPopupWindow;
import wzp.project.majiang.widget.ShowFunctionTipTextView;

/**
 * Created by wzp on 2017/8/29.
 */

public class BasicMethodFragment extends Fragment {

    private ShowFunctionTipTextView tvPlayerNum;
    private ListOptionButton btnChoosePlayerNum;
    private ListOptionButton btnChooseMajiangNum;
    private Button btnEastTop;


    private ShowFunctionTipPopupWindow pwShowFunTip;




    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_playerNum:
//                    pwShowFunTip.setFunTip(R.string.player_num_fun_tip);
//                    pwShowFunTip.showAsDropDown(tvPlayerNum);
                    break;

            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_method, container, false);

        initWidget(view);

        return view;
    }

    private void initWidget(View view) {
        tvPlayerNum = (ShowFunctionTipTextView) view.findViewById(R.id.tv_playerNum);
        btnChoosePlayerNum = (ListOptionButton) view.findViewById(R.id.btn_choosePlayerNum);
        btnChooseMajiangNum = (ListOptionButton) view.findViewById(R.id.btn_chooseMajiangNum);
        btnEastTop = (Button) view.findViewById(R.id.btn_eastTop);

        pwShowFunTip = new ShowFunctionTipPopupWindow(getContext());

//        btnChoosePlayerNum.setListViewItems(getContext().getResources().getStringArray(R.array.player_num_arr));

    }


}
