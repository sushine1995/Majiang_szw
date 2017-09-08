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
import wzp.project.majiang.entity.ChooseMajiangMethod;
import wzp.project.majiang.entity.SingleChooseMajiangMethod;


public class ChooseMajiangMethodFragment extends Fragment {

    private Button btnEdit;
    private ListView lvPlayMethod;

    private List<ChooseMajiangMethod> methodList;
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

        ChooseMajiangMethod chooseMajiangMethod = new ChooseMajiangMethod();
        chooseMajiangMethod.setLoopTimes(3);
        chooseMajiangMethod.setSelected(true);
        List<SingleChooseMajiangMethod> singleChooseMajiangMethodList = new ArrayList<>();
        SingleChooseMajiangMethod singleChooseMajiangMethod = new SingleChooseMajiangMethod();
        singleChooseMajiangMethod.setName("A:平胡");
        singleChooseMajiangMethod.setNumIndex(4);
        singleChooseMajiangMethod.setSpecialRuleIndex(22);
        singleChooseMajiangMethodList.add(singleChooseMajiangMethod);
        singleChooseMajiangMethod = new SingleChooseMajiangMethod();
        singleChooseMajiangMethod.setName("B:平胡");
        singleChooseMajiangMethod.setNumIndex(4);
        singleChooseMajiangMethod.setSpecialRuleIndex(22);
        singleChooseMajiangMethodList.add(singleChooseMajiangMethod);
        singleChooseMajiangMethod = new SingleChooseMajiangMethod();
        singleChooseMajiangMethod.setName("C:平胡");
        singleChooseMajiangMethod.setNumIndex(4);
        singleChooseMajiangMethod.setSpecialRuleIndex(22);
        singleChooseMajiangMethodList.add(singleChooseMajiangMethod);
        chooseMajiangMethod.setMethods(singleChooseMajiangMethodList);
        chooseMajiangMethod.setMethods(singleChooseMajiangMethodList);
        methodList.add(chooseMajiangMethod);

        chooseMajiangMethod = new ChooseMajiangMethod();
        chooseMajiangMethod.setLoopTimes(3);
        chooseMajiangMethod.setSelected(true);
        singleChooseMajiangMethodList = new ArrayList<>();
        singleChooseMajiangMethod = new SingleChooseMajiangMethod();
        singleChooseMajiangMethod.setName("A:平胡");
        singleChooseMajiangMethod.setNumIndex(4);
        singleChooseMajiangMethod.setSpecialRuleIndex(22);
        singleChooseMajiangMethodList.add(singleChooseMajiangMethod);
        singleChooseMajiangMethod = new SingleChooseMajiangMethod();
        singleChooseMajiangMethod.setName("B:平胡");
        singleChooseMajiangMethod.setNumIndex(4);
        singleChooseMajiangMethod.setSpecialRuleIndex(22);
        singleChooseMajiangMethodList.add(singleChooseMajiangMethod);
        singleChooseMajiangMethod = new SingleChooseMajiangMethod();
        singleChooseMajiangMethod.setName("C:平胡");
        singleChooseMajiangMethod.setNumIndex(4);
        singleChooseMajiangMethod.setSpecialRuleIndex(22);
        singleChooseMajiangMethodList.add(singleChooseMajiangMethod);
        chooseMajiangMethod.setMethods(singleChooseMajiangMethodList);
        chooseMajiangMethod.setMethods(singleChooseMajiangMethodList);
        methodList.add(chooseMajiangMethod);

        chooseMajiangMethod = new ChooseMajiangMethod();
        chooseMajiangMethod.setLoopTimes(3);
        chooseMajiangMethod.setSelected(true);
        singleChooseMajiangMethodList = new ArrayList<>();
        singleChooseMajiangMethod = new SingleChooseMajiangMethod();
        singleChooseMajiangMethod.setName("A:平胡");
        singleChooseMajiangMethod.setNumIndex(4);
        singleChooseMajiangMethod.setSpecialRuleIndex(22);
        singleChooseMajiangMethodList.add(singleChooseMajiangMethod);
        singleChooseMajiangMethod = new SingleChooseMajiangMethod();
        singleChooseMajiangMethod.setName("B:平胡");
        singleChooseMajiangMethod.setNumIndex(4);
        singleChooseMajiangMethod.setSpecialRuleIndex(22);
        singleChooseMajiangMethodList.add(singleChooseMajiangMethod);
        singleChooseMajiangMethod = new SingleChooseMajiangMethod();
        singleChooseMajiangMethod.setName("C:平胡");
        singleChooseMajiangMethod.setNumIndex(4);
        singleChooseMajiangMethod.setSpecialRuleIndex(22);
        singleChooseMajiangMethodList.add(singleChooseMajiangMethod);
        chooseMajiangMethod.setMethods(singleChooseMajiangMethodList);
        chooseMajiangMethod.setMethods(singleChooseMajiangMethodList);
        methodList.add(chooseMajiangMethod);
    }


    private void initWidget(View view) {
//        btnEdit = (Button) view.findViewById(btn_edt);

//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditChooseMajiangMethodActivity.myStartActivity(getContext());
//            }
//        });

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
