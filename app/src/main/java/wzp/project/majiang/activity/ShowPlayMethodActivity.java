package wzp.project.majiang.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BaseActivity;
import wzp.project.majiang.adapter.ShowPlayMethodVpAdapter;
import wzp.project.majiang.entity.PlayMethodParameter;
import wzp.project.majiang.fragment.ShowPlayMethodFragment;
import wzp.project.majiang.helper.constant.PlayMethod;
import wzp.project.majiang.helper.constant.ProjectConstants;
import wzp.project.majiang.util.DensityUtil;
import wzp.project.majiang.widget.SaveAsDialog;

import static wzp.project.majiang.widget.MyApplication.getContext;

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

    private static  final int REQUEST_READ_WRITE_EXTERNAL_STORAGE = 0x02;
    private static  final int REQUEST_RECV_SEND_FILE = 0x22;


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
//                    ReceiveSendFileActivity.myStartActivity(ShowPlayMethodActivity.this);
                    ReceiveSendFileActivity.myStartActivityForResult(ShowPlayMethodActivity.this, REQUEST_RECV_SEND_FILE);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_READ_WRITE_EXTERNAL_STORAGE);
        } else {
            setContentView(R.layout.activity_show_play_method);

            initData();
            initWidget();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setContentView(R.layout.activity_show_play_method);

                initData();
                initWidget();
            } else {
                Toast.makeText(getContext(), "系统拒绝读写外部文件，请到系统设置-权限管理中，打开此权限", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_RECV_SEND_FILE) {
            if (resultCode == RESULT_OK) {
                for (int i = 0; i < fragmentList.size(); i++) {
                    ((ShowPlayMethodFragment) fragmentList.get(i)).updateParameter();
                }
            }
        }
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
                    savePlayMethodParameterAs(dlgSaveAs.getFilename());
                } else {
                    showToast("文件名只能包含数字、中英文字符、下划线！");
                }

                dlgSaveAs.dismiss();
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

        File file = new File(ProjectConstants.baseFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        filename = ProjectConstants.baseFilePath + "/" + filename + ".xml";

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.newDocument();
            Element root = doc.createElement("map");

            for (int i = 0; i < fragmentList.size(); i++) {
                Element playMethod = doc.createElement("string");
                playMethod.setAttribute("name", "playMethod" + (i + 1));
                playMethod.setTextContent(JSON.toJSONString(parameterList.get(i)));
                root.appendChild(playMethod);
            }
            doc.appendChild(root);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transFormer = transFactory.newTransformer();
            transFormer.setOutputProperty("encoding", "UTF-8");
            DOMSource domSource = new DOMSource(doc);
            FileOutputStream out = new FileOutputStream(filename);
            StreamResult xmlResult = new StreamResult(out);
            transFormer.transform(domSource, xmlResult);

            showToast("保存成功");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            showToast("保存失败");
        } catch (IOException e) {
            e.printStackTrace();
            showToast("保存失败");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            showToast("保存失败");
        } catch (TransformerException e) {
            e.printStackTrace();
            showToast("保存失败");
        }
    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, ShowPlayMethodActivity.class);
        context.startActivity(intent);
    }
}
