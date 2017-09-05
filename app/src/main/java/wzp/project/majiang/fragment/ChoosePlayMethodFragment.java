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
import wzp.project.majiang.adapter.PlayMethodListAdapter;
import wzp.project.majiang.entity.PlayMethod;
import wzp.project.majiang.entity.SinglePlayMethod;


public class ChoosePlayMethodFragment extends Fragment {

    private Button btnEdit;
    private ListView lvPlayMethod;

    private List<PlayMethod> methodList;
    private PlayMethodListAdapter playMethodListAdapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_play_method, container, false);

        initData();
        initWidget(view);

        return view;
    }

    private void initData() {
        methodList = new ArrayList<>();
        playMethodListAdapter = new PlayMethodListAdapter(getContext(), methodList);

        PlayMethod playMethod = new PlayMethod();
        playMethod.setLoopTimes(3);
        playMethod.setSelected(true);
        List<SinglePlayMethod> singlePlayMethodList = new ArrayList<>();
        SinglePlayMethod singlePlayMethod = new SinglePlayMethod();
        singlePlayMethod.setName("A:平胡");
        singlePlayMethod.setNumIndex(4);
        singlePlayMethod.setSpecialRuleIndex(22);
        singlePlayMethodList.add(singlePlayMethod);
        singlePlayMethod = new SinglePlayMethod();
        singlePlayMethod.setName("B:平胡");
        singlePlayMethod.setNumIndex(4);
        singlePlayMethod.setSpecialRuleIndex(22);
        singlePlayMethodList.add(singlePlayMethod);
        singlePlayMethod = new SinglePlayMethod();
        singlePlayMethod.setName("C:平胡");
        singlePlayMethod.setNumIndex(4);
        singlePlayMethod.setSpecialRuleIndex(22);
        singlePlayMethodList.add(singlePlayMethod);
        playMethod.setMethods(singlePlayMethodList);
        playMethod.setMethods(singlePlayMethodList);
        methodList.add(playMethod);

        playMethod = new PlayMethod();
        playMethod.setLoopTimes(3);
        playMethod.setSelected(true);
        singlePlayMethodList = new ArrayList<>();
        singlePlayMethod = new SinglePlayMethod();
        singlePlayMethod.setName("A:平胡");
        singlePlayMethod.setNumIndex(4);
        singlePlayMethod.setSpecialRuleIndex(22);
        singlePlayMethodList.add(singlePlayMethod);
        singlePlayMethod = new SinglePlayMethod();
        singlePlayMethod.setName("B:平胡");
        singlePlayMethod.setNumIndex(4);
        singlePlayMethod.setSpecialRuleIndex(22);
        singlePlayMethodList.add(singlePlayMethod);
        singlePlayMethod = new SinglePlayMethod();
        singlePlayMethod.setName("C:平胡");
        singlePlayMethod.setNumIndex(4);
        singlePlayMethod.setSpecialRuleIndex(22);
        singlePlayMethodList.add(singlePlayMethod);
        playMethod.setMethods(singlePlayMethodList);
        playMethod.setMethods(singlePlayMethodList);
        methodList.add(playMethod);

        playMethod = new PlayMethod();
        playMethod.setLoopTimes(3);
        playMethod.setSelected(true);
        singlePlayMethodList = new ArrayList<>();
        singlePlayMethod = new SinglePlayMethod();
        singlePlayMethod.setName("A:平胡");
        singlePlayMethod.setNumIndex(4);
        singlePlayMethod.setSpecialRuleIndex(22);
        singlePlayMethodList.add(singlePlayMethod);
        singlePlayMethod = new SinglePlayMethod();
        singlePlayMethod.setName("B:平胡");
        singlePlayMethod.setNumIndex(4);
        singlePlayMethod.setSpecialRuleIndex(22);
        singlePlayMethodList.add(singlePlayMethod);
        singlePlayMethod = new SinglePlayMethod();
        singlePlayMethod.setName("C:平胡");
        singlePlayMethod.setNumIndex(4);
        singlePlayMethod.setSpecialRuleIndex(22);
        singlePlayMethodList.add(singlePlayMethod);
        playMethod.setMethods(singlePlayMethodList);
        playMethod.setMethods(singlePlayMethodList);
        methodList.add(playMethod);
    }


    private void initWidget(View view) {
//        btnEdit = (Button) view.findViewById(btn_edt);

//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditPlayMethodActivity.myStartActivity(getContext());
//            }
//        });

        lvPlayMethod = (ListView) view.findViewById(R.id.lv_play_method);

        lvPlayMethod.setAdapter(playMethodListAdapter);

//        lvPlayMethod.addFooterView();
    }
}
