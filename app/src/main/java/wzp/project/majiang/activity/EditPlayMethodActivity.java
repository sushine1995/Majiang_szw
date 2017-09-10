package wzp.project.majiang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.adapter.ModifyPlayMethodVpAdapter;
import wzp.project.majiang.fragment.BasicMethodFragment;
import wzp.project.majiang.fragment.ChooseMajiangMethodFragment;
import wzp.project.majiang.fragment.SetDiceFragment;
import wzp.project.majiang.helper.constant.PlayMethod;

public class EditPlayMethodActivity extends AppCompatActivity {

    private ImageButton ibtnBack;

    private TextView tvTitle;
    private TabLayout tabParam;
    private ViewPager vpParam;

    private String[] playMethodArr;
    private int playMethod;
    private List<Fragment> fragmentList;
    private ModifyPlayMethodVpAdapter modifyPlayMethodVpAdapter;

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

        Fragment fragment2 = new ChooseMajiangMethodFragment();
        fragmentList.add(fragment2);

        Fragment fragment3 = new SetDiceFragment();
        fragmentList.add(fragment3);

        modifyPlayMethodVpAdapter = new ModifyPlayMethodVpAdapter(getSupportFragmentManager(), fragmentList);

        playMethodArr = getResources().getStringArray(R.array.play_method_arr);
        playMethod = getIntent().getIntExtra("playMethod", PlayMethod.NO_1);
    }

    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tabParam = (TabLayout) findViewById(R.id.tab_param);
        vpParam = (ViewPager) findViewById(R.id.vp_param);

        tabParam.setupWithViewPager(vpParam);
        vpParam.setAdapter(modifyPlayMethodVpAdapter);

        tvTitle.setText(playMethodArr[playMethod - 1]);

        ibtnBack.setOnClickListener(listener);
    }

    public static void myStartActivity(Context context, int playMethod) {
        Intent intent = new Intent(context, EditPlayMethodActivity.class);
        intent.putExtra("playMethod", playMethod);
        context.startActivity(intent);
    }
}
