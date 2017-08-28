package wzp.project.majiang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import wzp.project.majiang.R;

/**
 * Created by wzp on 2017/8/28.
 */

public class ShowPlayMethodFragment extends Fragment {

    private TextView tv;

    private int method;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_play_method, container, false);

        method = getArguments().getInt("method");
        initWidget(view);

        return view;
    }



    private void initWidget(View view) {
        tv = (TextView) view.findViewById(R.id.tv);

        tv.setText("玩法" + method + "");
    }
}
