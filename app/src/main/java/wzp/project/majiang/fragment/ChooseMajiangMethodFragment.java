package wzp.project.majiang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.EditChooseMajiangMethodActivity;
import wzp.project.majiang.adapter.PlayMethodListAdapter;
import wzp.project.majiang.entity.ChooseCardMethod;
import wzp.project.majiang.entity.SingleChooseCardMethod;


public class ChooseMajiangMethodFragment extends Fragment {

    private Button btnEdit;
    private ListView lvPlayMethod;

    private List<ChooseCardMethod> methodList;
    private PlayMethodListAdapter playMethodListAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_majiang_method, container, false);

        initData();
        initWidget(view);

        return view;
    }

    private void initData() {
        methodList = new ArrayList<>();
        playMethodListAdapter = new PlayMethodListAdapter(getContext(), methodList);

        ChooseCardMethod chooseCardMethod = new ChooseCardMethod();
        chooseCardMethod.setLoopTimes(3);
        chooseCardMethod.setSelected(true);
        List<SingleChooseCardMethod> singleChooseCardMethodList = new ArrayList<>();
        SingleChooseCardMethod singleChooseCardMethod = new SingleChooseCardMethod();
        singleChooseCardMethod.setName("A:平胡");
        singleChooseCardMethod.setNumIndex(4);
        singleChooseCardMethod.setSpecialRuleIndex(5);
        singleChooseCardMethodList.add(singleChooseCardMethod);
        singleChooseCardMethod = new SingleChooseCardMethod();
        singleChooseCardMethod.setName("B:平胡");
        singleChooseCardMethod.setNumIndex(4);
        singleChooseCardMethod.setSpecialRuleIndex(5);
        singleChooseCardMethodList.add(singleChooseCardMethod);
        singleChooseCardMethod = new SingleChooseCardMethod();
        singleChooseCardMethod.setName("C:平胡");
        singleChooseCardMethod.setNumIndex(4);
        singleChooseCardMethod.setSpecialRuleIndex(5);
        singleChooseCardMethodList.add(singleChooseCardMethod);
        chooseCardMethod.setMethods(singleChooseCardMethodList);
        chooseCardMethod.setMethods(singleChooseCardMethodList);
        methodList.add(chooseCardMethod);

        chooseCardMethod = new ChooseCardMethod();
        chooseCardMethod.setLoopTimes(3);
        chooseCardMethod.setSelected(true);
        singleChooseCardMethodList = new ArrayList<>();
        singleChooseCardMethod = new SingleChooseCardMethod();
        singleChooseCardMethod.setName("A:平胡");
        singleChooseCardMethod.setNumIndex(4);
        singleChooseCardMethod.setSpecialRuleIndex(5);
        singleChooseCardMethodList.add(singleChooseCardMethod);
        singleChooseCardMethod = new SingleChooseCardMethod();
        singleChooseCardMethod.setName("B:平胡");
        singleChooseCardMethod.setNumIndex(4);
        singleChooseCardMethod.setSpecialRuleIndex(5);
        singleChooseCardMethodList.add(singleChooseCardMethod);
        singleChooseCardMethod = new SingleChooseCardMethod();
        singleChooseCardMethod.setName("C:平胡");
        singleChooseCardMethod.setNumIndex(4);
        singleChooseCardMethod.setSpecialRuleIndex(5);
        singleChooseCardMethodList.add(singleChooseCardMethod);
        chooseCardMethod.setMethods(singleChooseCardMethodList);
        chooseCardMethod.setMethods(singleChooseCardMethodList);
        methodList.add(chooseCardMethod);

        chooseCardMethod = new ChooseCardMethod();
        chooseCardMethod.setLoopTimes(3);
        chooseCardMethod.setSelected(true);
        singleChooseCardMethodList = new ArrayList<>();
        singleChooseCardMethod = new SingleChooseCardMethod();
        singleChooseCardMethod.setName("A:平胡");
        singleChooseCardMethod.setNumIndex(4);
        singleChooseCardMethod.setSpecialRuleIndex(5);
        singleChooseCardMethodList.add(singleChooseCardMethod);
        singleChooseCardMethod = new SingleChooseCardMethod();
        singleChooseCardMethod.setName("B:平胡");
        singleChooseCardMethod.setNumIndex(4);
        singleChooseCardMethod.setSpecialRuleIndex(5);
        singleChooseCardMethodList.add(singleChooseCardMethod);
        singleChooseCardMethod = new SingleChooseCardMethod();
        singleChooseCardMethod.setName("C:平胡");
        singleChooseCardMethod.setNumIndex(4);
        singleChooseCardMethod.setSpecialRuleIndex(5);
        singleChooseCardMethodList.add(singleChooseCardMethod);
        chooseCardMethod.setMethods(singleChooseCardMethodList);
        chooseCardMethod.setMethods(singleChooseCardMethodList);
        methodList.add(chooseCardMethod);
    }


    private void initWidget(View view) {
        lvPlayMethod = (ListView) view.findViewById(R.id.lv_play_method);

        lvPlayMethod.setAdapter(playMethodListAdapter);

        Button btnAddData = (Button) LayoutInflater.from(getContext()).inflate(R.layout.view_add_data, null);
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditChooseMajiangMethodActivity.myStartActivity(getContext());
            }
        });
        lvPlayMethod.addFooterView(btnAddData);
    }
}
