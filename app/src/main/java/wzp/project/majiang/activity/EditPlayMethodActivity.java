package wzp.project.majiang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BaseActivity;
import wzp.project.majiang.adapter.ModifyPlayMethodVpAdapter;
import wzp.project.majiang.entity.BasicParameter;
import wzp.project.majiang.entity.ChooseCardParameter;
import wzp.project.majiang.entity.DiceParameter;
import wzp.project.majiang.entity.PlayMethodParameter;
import wzp.project.majiang.fragment.BasicMethodFragment;
import wzp.project.majiang.fragment.ChooseCardMethodFragment;
import wzp.project.majiang.fragment.SetDiceFragment;
import wzp.project.majiang.constant.PlayMethod;
import wzp.project.majiang.widget.MyApplication;

public class EditPlayMethodActivity extends BaseActivity {

    private ImageButton ibtnBack;
    private TextView tvTitle;

    private TabLayout tabParam;
    private ViewPager vpParam;

    private String[] playMethodArr;
    private int playMethod;
    private List<Fragment> fragmentList;
    private ModifyPlayMethodVpAdapter modifyPlayMethodVpAdapter;

    private PlayMethodParameter parameter;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ibtn_back:
                    onBackPressed();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
//        intent.putExtra("parameter", JSON.toJSONString(parameter));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_play_method);

        initData();
        initWidget();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        Fragment fragment1 = new BasicMethodFragment();
        fragmentList.add(fragment1);

        Fragment fragment2 = new ChooseCardMethodFragment();
        fragmentList.add(fragment2);

        Fragment fragment3 = new SetDiceFragment();
        fragmentList.add(fragment3);

        modifyPlayMethodVpAdapter = new ModifyPlayMethodVpAdapter(getSupportFragmentManager(), fragmentList);

        playMethodArr = getResources().getStringArray(R.array.play_method_arr);
        playMethod = getIntent().getIntExtra("playMethod", PlayMethod.NO_1);

//        try {
//            parameter = JSON.parseObject(getIntent().getStringExtra("parameter"),
//                    PlayMethodParameter.class);
//        } catch (JSONException e) {
//            e.printStackTrace();
//
//        }

        parameter = MyApplication.getParameterList().get(playMethod);
    }

    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);

        tabParam = (TabLayout) findViewById(R.id.tab_param);
        vpParam = (ViewPager) findViewById(R.id.vp_param);

        tabParam.setupWithViewPager(vpParam);
        vpParam.setAdapter(modifyPlayMethodVpAdapter);
        vpParam.setOffscreenPageLimit(2);

        tvTitle.setText(playMethodArr[playMethod]);

        ibtnBack.setOnClickListener(listener);
    }

    public static void myStartActivity(Context context, int playMethod) {
        Intent intent = new Intent(context, EditPlayMethodActivity.class);
        intent.putExtra("playMethod", playMethod);
        context.startActivity(intent);
    }

    public static void myStartActivityForResult(Fragment fragment, int playMethod, int requestCode) {
        Intent intent = new Intent(fragment.getContext(), EditPlayMethodActivity.class);
        intent.putExtra("playMethod", playMethod);
        fragment.startActivityForResult(intent, requestCode);
    }

    public BasicParameter getBasicParameter() {
        return parameter.getBasicParameter();
    }

    public ChooseCardParameter getChooseCardParameter() {
        return parameter.getChooseCardParameter();
    }

    public DiceParameter getDiceParameter() {
        return parameter.getDiceParameter();
    }

    public int getPlayMethod() {
        return playMethod;
    }
}
