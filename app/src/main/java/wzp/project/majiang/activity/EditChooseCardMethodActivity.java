package wzp.project.majiang.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BaseActivity;
import wzp.project.majiang.adapter.SingleChooseCardListAdapter;
import wzp.project.majiang.entity.ChooseCardMethod;
import wzp.project.majiang.entity.SingleChooseCardMethod;
import wzp.project.majiang.widget.ListOptionButton;

import static wzp.project.majiang.widget.MyApplication.getContext;


public class EditChooseCardMethodActivity extends BaseActivity {

    private ImageButton ibtnBack;
    private TextView tvTitle;
    private ImageButton ibtnSave;
    private ListOptionButton btnLoopTimes;
    private RecyclerView rvPlayMethod;
    private CheckBox cbA;
    private ListOptionButton btnNumA;
    private ListOptionButton btnSpecialRuleA;
    private CheckBox cbB;
    private ListOptionButton btnNumB;
    private ListOptionButton btnSpecialRuleB;
    private CheckBox cbC;
    private ListOptionButton btnNumC;
    private ListOptionButton btnSpecialRuleC;
    private CheckBox cbD;
    private ListOptionButton btnNumD;
    private ListOptionButton btnSpecialRuleD;
    private CheckBox cbE;
    private ListOptionButton btnNumE;
    private ListOptionButton btnSpecialRuleE;
    private CheckBox cbF;
    private ListOptionButton btnNumF;
    private ListOptionButton btnSpecialRuleF;
    private CheckBox cbG;
    private ListOptionButton btnNumG;
    private ListOptionButton btnSpecialRuleG;
    private CheckBox cbH;
    private ListOptionButton btnNumH;
    private ListOptionButton btnSpecialRuleH;
    private CheckBox cbI;
    private ListOptionButton btnNumI;
    private ListOptionButton btnSpecialRuleI;
    private CheckBox cbJ;
    private ListOptionButton btnNumJ;
    private ListOptionButton btnSpecialRuleJ;
    private CheckBox cbK;
    private ListOptionButton btnNumK;
    private ListOptionButton btnSpecialRuleK;
    private CheckBox cbL;
    private ListOptionButton btnNumL;
    private ListOptionButton btnSpecialRuleL;
    private CheckBox cbM;
    private ListOptionButton btnNumM;
    private ListOptionButton btnSpecialRuleM;
    private CheckBox cbN;
    private ListOptionButton btnNumN;
    private ListOptionButton btnSpecialRuleN;
    private CheckBox cbO;
    private ListOptionButton btnNumO;
    private ListOptionButton btnSpecialRuleO;
    private CheckBox cbP;
    private ListOptionButton btnNumP;
    private ListOptionButton btnSpecialRuleP;
    private CheckBox cbQ;
    private ListOptionButton btnNumQ;
    private ListOptionButton btnSpecialRuleQ;
    private CheckBox cbR;
    private ListOptionButton btnNumR;
    private ListOptionButton btnSpecialRuleR;
    private CheckBox cbS;
    private ListOptionButton btnNumS;
    private ListOptionButton btnSpecialRuleS;

    private AlertDialog dlgExit;

    private List<SingleChooseCardMethod> list;
    private SingleChooseCardListAdapter adapter;

    private List<CheckBox> cbList = new ArrayList<>();
    private List<ListOptionButton> btnNumList = new ArrayList<>();
    private List<ListOptionButton> btnSpecialRuleList = new ArrayList<>();

    private ChooseCardMethod chooseCardMethod;

    private String[] playMethodNameArr;
    private String[] loopTimesArr;

    private int index; // 数据索引

    private static final int MAX_NUM = 6;


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ibtn_back:
                    onBackPressed();
                    break;

                case R.id.ibtn_save:
                    showToast("保存成功");
                    Intent intent = new Intent();
                    intent.putExtra("index", index);
                    intent.putExtra("chooseCardMethod", JSON.toJSONString(chooseCardMethod));
                    setResult(RESULT_OK, intent);
                    finish();
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
        setContentView(R.layout.activity_edit_choose_majiang_method);

        initData();
        initWidget();
    }

    private void initData() {
        playMethodNameArr = getResources().getStringArray(R.array.play_method_name_arr);
        loopTimesArr = getResources().getStringArray(R.array.loop_times_arr);

        String json = getIntent().getStringExtra("chooseCardMethod");
        if (!TextUtils.isEmpty(json)) {
            chooseCardMethod = JSON.parseObject(json, ChooseCardMethod.class);
        } else {
            // 如果json为null，表示当前需要添加数据，初始化一个默认的ChooseCardMethod实例
            chooseCardMethod = new ChooseCardMethod();
            chooseCardMethod.setLoopTimes(0);
            chooseCardMethod.setSelected(true);
            chooseCardMethod.setMethods(new ArrayList<SingleChooseCardMethod>());
        }

        list = chooseCardMethod.getMethods();
        adapter = new SingleChooseCardListAdapter(this, list);

        index = getIntent().getIntExtra("index", -1);
    }

    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ibtnSave = (ImageButton) findViewById(R.id.ibtn_save);
        btnLoopTimes = (ListOptionButton) findViewById(R.id.btn_loopTimes);
        rvPlayMethod = (RecyclerView) findViewById(R.id.rv_playMethod);
        cbA = (CheckBox) findViewById(R.id.cb_a);
        btnNumA = (ListOptionButton) findViewById(R.id.btn_numA);
        btnSpecialRuleA = (ListOptionButton) findViewById(R.id.btn_specialRuleA);
        cbB = (CheckBox) findViewById(R.id.cb_b);
        btnNumB = (ListOptionButton) findViewById(R.id.btn_numB);
        btnSpecialRuleB = (ListOptionButton) findViewById(R.id.btn_specialRuleB);
        cbC = (CheckBox) findViewById(R.id.cb_c);
        btnNumC = (ListOptionButton) findViewById(R.id.btn_numC);
        btnSpecialRuleC = (ListOptionButton) findViewById(R.id.btn_specialRuleC);
        cbD = (CheckBox) findViewById(R.id.cb_d);
        btnNumD = (ListOptionButton) findViewById(R.id.btn_numD);
        btnSpecialRuleD = (ListOptionButton) findViewById(R.id.btn_specialRuleD);
        cbE = (CheckBox) findViewById(R.id.cb_e);
        btnNumE = (ListOptionButton) findViewById(R.id.btn_numE);
        btnSpecialRuleE = (ListOptionButton) findViewById(R.id.btn_specialRuleE);
        cbF = (CheckBox) findViewById(R.id.cb_f);
        btnNumF = (ListOptionButton) findViewById(R.id.btn_numF);
        btnSpecialRuleF = (ListOptionButton) findViewById(R.id.btn_specialRuleF);
        cbG = (CheckBox) findViewById(R.id.cb_g);
        btnNumG = (ListOptionButton) findViewById(R.id.btn_numG);
        btnSpecialRuleG = (ListOptionButton) findViewById(R.id.btn_specialRuleG);
        cbH = (CheckBox) findViewById(R.id.cb_h);
        btnNumH = (ListOptionButton) findViewById(R.id.btn_numH);
        btnSpecialRuleH = (ListOptionButton) findViewById(R.id.btn_specialRuleH);
        cbI = (CheckBox) findViewById(R.id.cb_i);
        btnNumI = (ListOptionButton) findViewById(R.id.btn_numI);
        btnSpecialRuleI = (ListOptionButton) findViewById(R.id.btn_specialRuleI);
        cbJ = (CheckBox) findViewById(R.id.cb_j);
        btnNumJ = (ListOptionButton) findViewById(R.id.btn_numJ);
        btnSpecialRuleJ = (ListOptionButton) findViewById(R.id.btn_specialRuleJ);
        cbK = (CheckBox) findViewById(R.id.cb_k);
        btnNumK = (ListOptionButton) findViewById(R.id.btn_numK);
        btnSpecialRuleK = (ListOptionButton) findViewById(R.id.btn_specialRuleK);
        cbL = (CheckBox) findViewById(R.id.cb_l);
        btnNumL = (ListOptionButton) findViewById(R.id.btn_numL);
        btnSpecialRuleL = (ListOptionButton) findViewById(R.id.btn_specialRuleL);
        cbM = (CheckBox) findViewById(R.id.cb_m);
        btnNumM = (ListOptionButton) findViewById(R.id.btn_numM);
        btnSpecialRuleM = (ListOptionButton) findViewById(R.id.btn_specialRuleM);
        cbN = (CheckBox) findViewById(R.id.cb_n);
        btnNumN = (ListOptionButton) findViewById(R.id.btn_numN);
        btnSpecialRuleN = (ListOptionButton) findViewById(R.id.btn_specialRuleN);
        cbO = (CheckBox) findViewById(R.id.cb_o);
        btnNumO = (ListOptionButton) findViewById(R.id.btn_numO);
        btnSpecialRuleO = (ListOptionButton) findViewById(R.id.btn_specialRuleO);
        cbP = (CheckBox) findViewById(R.id.cb_p);
        btnNumP = (ListOptionButton) findViewById(R.id.btn_numP);
        btnSpecialRuleP = (ListOptionButton) findViewById(R.id.btn_specialRuleP);
        cbQ = (CheckBox) findViewById(R.id.cb_q);
        btnNumQ = (ListOptionButton) findViewById(R.id.btn_numQ);
        btnSpecialRuleQ = (ListOptionButton) findViewById(R.id.btn_specialRuleQ);
        cbR = (CheckBox) findViewById(R.id.cb_r);
        btnNumR = (ListOptionButton) findViewById(R.id.btn_numR);
        btnSpecialRuleR = (ListOptionButton) findViewById(R.id.btn_specialRuleR);
        cbS = (CheckBox) findViewById(R.id.cb_s);
        btnNumS = (ListOptionButton) findViewById(R.id.btn_numS);
        btnSpecialRuleS = (ListOptionButton) findViewById(R.id.btn_specialRuleS);

        dlgExit = new AlertDialog.Builder(this)
                .setTitle("注意")
                .setMessage("是否保存数据？")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("保存成功");
                        Intent intent = new Intent();
                        intent.putExtra("index", index);
                        intent.putExtra("chooseCardMethod", JSON.toJSONString(chooseCardMethod));
                        setResult(RESULT_OK, intent);
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

        cbList.add(cbA);
        cbList.add(cbB);
        cbList.add(cbC);
        cbList.add(cbD);
        cbList.add(cbE);
        cbList.add(cbF);
        cbList.add(cbG);
        cbList.add(cbH);
        cbList.add(cbI);
        cbList.add(cbJ);
        cbList.add(cbK);
        cbList.add(cbL);
        cbList.add(cbM);
        cbList.add(cbN);
        cbList.add(cbO);
        cbList.add(cbP);
        cbList.add(cbQ);
        cbList.add(cbR);
        cbList.add(cbS);

        btnNumList.add(btnNumA);
        btnNumList.add(btnNumB);
        btnNumList.add(btnNumC);
        btnNumList.add(btnNumD);
        btnNumList.add(btnNumE);
        btnNumList.add(btnNumF);
        btnNumList.add(btnNumG);
        btnNumList.add(btnNumH);
        btnNumList.add(btnNumI);
        btnNumList.add(btnNumJ);
        btnNumList.add(btnNumK);
        btnNumList.add(btnNumL);
        btnNumList.add(btnNumM);
        btnNumList.add(btnNumN);
        btnNumList.add(btnNumO);
        btnNumList.add(btnNumP);
        btnNumList.add(btnNumQ);
        btnNumList.add(btnNumR);
        btnNumList.add(btnNumS);

        btnSpecialRuleList.add(btnSpecialRuleA);
        btnSpecialRuleList.add(btnSpecialRuleB);
        btnSpecialRuleList.add(btnSpecialRuleC);
        btnSpecialRuleList.add(btnSpecialRuleD);
        btnSpecialRuleList.add(btnSpecialRuleE);
        btnSpecialRuleList.add(btnSpecialRuleF);
        btnSpecialRuleList.add(btnSpecialRuleG);
        btnSpecialRuleList.add(btnSpecialRuleH);
        btnSpecialRuleList.add(btnSpecialRuleI);
        btnSpecialRuleList.add(btnSpecialRuleJ);
        btnSpecialRuleList.add(btnSpecialRuleK);
        btnSpecialRuleList.add(btnSpecialRuleL);
        btnSpecialRuleList.add(btnSpecialRuleM);
        btnSpecialRuleList.add(btnSpecialRuleN);
        btnSpecialRuleList.add(btnSpecialRuleO);
        btnSpecialRuleList.add(btnSpecialRuleP);
        btnSpecialRuleList.add(btnSpecialRuleQ);
        btnSpecialRuleList.add(btnSpecialRuleR);
        btnSpecialRuleList.add(btnSpecialRuleS);

        rvPlayMethod.setAdapter(adapter);
        rvPlayMethod.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));


        // 初始化控件值
        tvTitle.setText("数据" + (index + 1));
        btnLoopTimes.setSelectedItemPosition(chooseCardMethod.getLoopTimes());
        int name;
        for (SingleChooseCardMethod singleChooseCardMethod :
                chooseCardMethod.getMethods()) {
            name = singleChooseCardMethod.getName();
            cbList.get(name).setChecked(true);
            btnNumList.get(name).setSelectedItemPosition(singleChooseCardMethod.getNum());
            btnSpecialRuleList.get(name).setSelectedItemPosition(singleChooseCardMethod.getSpecialRule());
        }

        if (list.size() == MAX_NUM) {
            for (int k = 0; k < cbList.size(); k++) {
                if (!cbList.get(k).isChecked()) {
                    cbList.get(k).setClickable(false);
                }
            }
            showToast("最多只能添加6条数据！");
        }


        // 设置监听器，注意：一定要放在初始化控件值的代码后面
        ibtnBack.setOnClickListener(listener);
        ibtnSave.setOnClickListener(listener);
        for (int i = 0; i < cbList.size(); i++) {
            final int j = i;
            cbList.get(j).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    SingleChooseCardMethod method = new SingleChooseCardMethod();
                    method.setName(j);
                    method.setNum(btnNumList.get(j).getSelectedItemPosition());
                    method.setSpecialRule(btnSpecialRuleList.get(j).getSelectedItemPosition());

                    if (isChecked) {
                        if (list.size() < MAX_NUM) {
                            list.add(method);
                            if (list.size() == MAX_NUM) {
                                for (int k = 0; k < cbList.size(); k++) {
                                    if (!cbList.get(k).isChecked()) {
                                        cbList.get(k).setClickable(false);
                                    }
                                }
                                showToast("最多只能添加6条数据！");
                            }
                        }
                    } else {
                        list.remove(method);
                        if (list.size() == MAX_NUM - 1) {
                            for (int k = 0; k < cbList.size(); k++) {
                                cbList.get(k).setClickable(true);
                            }
                        }
                    }

                    adapter.notifyDataSetChanged();
                }
            });

            btnNumList.get(j).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (cbList.get(j).isChecked()) {
                        SingleChooseCardMethod method = new SingleChooseCardMethod();
                        method.setName(j);

                        list.get(list.indexOf(method)).setNum(btnNumList.get(j).getSelectedItemPosition());
                        adapter.notifyDataSetChanged();
                    }
                }
            });

            btnSpecialRuleList.get(j).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (cbList.get(j).isChecked()) {
                        SingleChooseCardMethod method = new SingleChooseCardMethod();
                        method.setName(j);

                        list.get(list.indexOf(method)).setSpecialRule(btnSpecialRuleList.get(j).getSelectedItemPosition());
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

        btnLoopTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chooseCardMethod.setLoopTimes(position);
            }
        });
    }

    public static void myStartActivityForResult(Context context, Fragment fragment, int index, int requestCode) {
        Intent intent = new Intent(context, EditChooseCardMethodActivity.class);
        intent.putExtra("index", index);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void myStartActivityForResult(Context context, Fragment fragment, int index, String json, int requestCode) {
        Intent intent = new Intent(context, EditChooseCardMethodActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("chooseCardMethod", json);
        fragment.startActivityForResult(intent, requestCode);
    }
}
