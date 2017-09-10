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

import java.util.ArrayList;
import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.adapter.ShowPlayMethodVpAdapter;
import wzp.project.majiang.fragment.ShowPlayMethodFragment;
import wzp.project.majiang.helper.constant.PlayMethod;

/**
 * Created by wzp on 2017/8/28.
 */

public class ShowPlayMethodActivity extends AppCompatActivity {

    private ImageButton ibtnBack;

    private TabLayout tabRecord;
    private ViewPager vpPlayMethod;

    private List<Fragment> fragmentList;
    private ShowPlayMethodVpAdapter playMethodVpAdapter;

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
        setContentView(R.layout.activity_show_play_method);

        initData();
        initWidget();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        Fragment fragment1 = new ShowPlayMethodFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("method", PlayMethod.NO_1);
        fragment1.setArguments(bundle);
        fragmentList.add(fragment1);

        Fragment fragment2 = new ShowPlayMethodFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("method", PlayMethod.NO_2);
        fragment2.setArguments(bundle2);
        fragmentList.add(fragment2);

        Fragment fragment3 = new ShowPlayMethodFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("method", PlayMethod.NO_3);
        fragment3.setArguments(bundle3);
        fragmentList.add(fragment3);

        playMethodVpAdapter = new ShowPlayMethodVpAdapter(getSupportFragmentManager(), fragmentList);
    }

    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);

        tabRecord = (TabLayout) findViewById(R.id.tab_record);
        vpPlayMethod = (ViewPager) findViewById(R.id.vp_playMethod);

        tabRecord.setupWithViewPager(vpPlayMethod);
        vpPlayMethod.setAdapter(playMethodVpAdapter);

        ibtnBack.setOnClickListener(listener);
    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, ShowPlayMethodActivity.class);
        context.startActivity(intent);
    }
}
