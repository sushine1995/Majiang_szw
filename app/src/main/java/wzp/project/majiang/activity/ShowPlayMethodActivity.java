package wzp.project.majiang.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BaseActivity;
import wzp.project.majiang.adapter.ShowPlayMethodVpAdapter;
import wzp.project.majiang.entity.PlayMethodParameter;
import wzp.project.majiang.fragment.ShowPlayMethodFragment;
import wzp.project.majiang.helper.constant.PlayMethod;
import wzp.project.majiang.helper.constant.ProjectConstants;
import wzp.project.majiang.util.DensityUtil;
import wzp.project.majiang.widget.SaveAsDialog;

/**
 * Created by wzp on 2017/8/28.
 */

public class ShowPlayMethodActivity extends BaseActivity {

    private ImageButton ibtnBack;
    private ImageButton ibtnSave;
    private ImageButton ibtnMoreFun;

    private TabLayout tabRecord;
    private ViewPager vpPlayMethod;

    private AlertDialog dlgExit;

    private List<Fragment> fragmentList;
    private ShowPlayMethodVpAdapter playMethodVpAdapter;

    private View vMoreFun;
    private TextView tvSaveAs;
    private TextView tvReadFile;
    private TextView tvDownload;
    private PopupWindow pwMoreFun;

    private SaveAsDialog dlgSaveAs;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ibtn_back:
                    onBackPressed();
                    break;

                case R.id.ibtn_save:
                    savePlayMethod();
                    break;

                case R.id.ibtn_moreFun:
                    pwMoreFun.showAsDropDown(ibtnMoreFun, -DensityUtil.dp2px(ShowPlayMethodActivity.this, 150)
                            , DensityUtil.dp2px(ShowPlayMethodActivity.this, 1));
                    break;

                case R.id.tv_saveAs:
                    pwMoreFun.dismiss();
                    dlgSaveAs.show();
                    break;

                case R.id.tv_readFile:
                    pwMoreFun.dismiss();
                    ReceiveSendFileActivity.myStartActivity(ShowPlayMethodActivity.this);
                    break;

                case R.id.tv_download:
                    pwMoreFun.dismiss();

                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        dlgExit.show();
    }

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
        ibtnSave = (ImageButton) findViewById(R.id.ibtn_save);
        ibtnMoreFun = (ImageButton) findViewById(R.id.ibtn_moreFun);

        tabRecord = (TabLayout) findViewById(R.id.tab_record);
        vpPlayMethod = (ViewPager) findViewById(R.id.vp_playMethod);

        tabRecord.setupWithViewPager(vpPlayMethod);
        vpPlayMethod.setAdapter(playMethodVpAdapter);
        vpPlayMethod.setOffscreenPageLimit(2);

        vMoreFun = LayoutInflater.from(this).inflate(R.layout.pop_win_more_fun, null);
        tvSaveAs = (TextView) vMoreFun.findViewById(R.id.tv_saveAs);
        tvReadFile = (TextView) vMoreFun.findViewById(R.id.tv_readFile);
        tvDownload = (TextView) vMoreFun.findViewById(R.id.tv_download);

        pwMoreFun = new PopupWindow(vMoreFun, (int) DensityUtil.dp2px(this, 180), LinearLayout.LayoutParams.WRAP_CONTENT);
        pwMoreFun.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popupwindow_bg)));
        pwMoreFun.setFocusable(true);

        dlgExit = new AlertDialog.Builder(this)
                .setTitle("注意")
                .setMessage("是否保存数据？")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savePlayMethod();
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create();

        dlgSaveAs = new SaveAsDialog(this);
        dlgSaveAs.setPositiveButton(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dlgSaveAs.isFilenameValidate()) {



                } else {
                    showToast("文件名只能包含数字、中英文字符、下划线！");
                }
            }
        });

        ibtnBack.setOnClickListener(listener);
        ibtnSave.setOnClickListener(listener);
        ibtnMoreFun.setOnClickListener(listener);
        tvSaveAs.setOnClickListener(listener);
        tvReadFile.setOnClickListener(listener);
        tvDownload.setOnClickListener(listener);
    }

    private void savePlayMethod() {
        for (Fragment fragment : fragmentList) {
            ((ShowPlayMethodFragment) fragment).savePlayMethod();
        }
        showToast("参数保存成功！");
    }

    private void savePlayMethodParameterAs(String filename) {
        List<PlayMethodParameter> parameterList = new ArrayList<>();
        for (Fragment fragment : fragmentList) {
            parameterList.add(((ShowPlayMethodFragment) fragment).getPlayMethodParameter());
        }

        filename = ProjectConstants.baseFilePath + "/" + filename;
        try {
            FileOutputStream fos = new FileOutputStream(filename);



            showToast("文件保存成功！");
        } catch (FileNotFoundException e) {
            e.printStackTrace();

            showToast("文件不存在！");
        }

    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, ShowPlayMethodActivity.class);
        context.startActivity(intent);
    }
}
