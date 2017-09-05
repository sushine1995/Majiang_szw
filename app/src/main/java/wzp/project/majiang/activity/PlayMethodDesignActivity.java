package wzp.project.majiang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.fragment.BasicMethodFragment;
import wzp.project.majiang.fragment.ChoosePlayMethodFragment;
import wzp.project.majiang.fragment.SetDiceFragment;
import wzp.project.majiang.adapter.PlayMethodVpAdapter;

/**
 * Created by wzp on 2017/8/28.
 */

public class PlayMethodDesignActivity extends AppCompatActivity {

    private TabLayout tabRecord;
    private ViewPager vpPlayMethod;

    private List<Fragment> fragmentList;
    private PlayMethodVpAdapter playMethodVpAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_method_design);

        initData();
        initWidget();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
//        Fragment fragment1 = new ShowPlayMethodFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("method", 1);
//        fragment1.setArguments(bundle);
//        fragmentList.add(fragment1);
        Fragment fragment1 = new BasicMethodFragment();
        fragmentList.add(fragment1);

        Fragment fragment2 = new SetDiceFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("method", 2);
        fragment2.setArguments(bundle2);
        fragmentList.add(fragment2);

        Fragment fragment3 = new ChoosePlayMethodFragment();
        Bundle bundle3 = new Bundle();
        fragment3.setArguments(bundle3);
        fragmentList.add(fragment3);

        playMethodVpAdapter = new PlayMethodVpAdapter(getSupportFragmentManager(), fragmentList);
    }

    private void initWidget() {
        tabRecord = (TabLayout) findViewById(R.id.tab_record);
        vpPlayMethod = (ViewPager) findViewById(R.id.vp_playMethod);

        tabRecord.setupWithViewPager(vpPlayMethod);
        vpPlayMethod.setAdapter(playMethodVpAdapter);
    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, PlayMethodDesignActivity.class);
        context.startActivity(intent);
    }
}
