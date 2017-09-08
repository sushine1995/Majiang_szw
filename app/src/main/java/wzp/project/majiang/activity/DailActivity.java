package wzp.project.majiang.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Method;

import wzp.project.majiang.R;
import wzp.project.majiang.helper.constant.ProjectConstants;

public class DailActivity extends AppCompatActivity {

    private EditText edtNum;
    private Button btnDialDel;
    private Button btnNum0;
    private Button btnNum1;
    private Button btnNum2;
    private Button btnNum3;
    private Button btnNum4;
    private Button btnNum5;
    private Button btnNum6;
    private Button btnNum7;
    private Button btnNum8;
    private Button btnNum9;
    private Button btnNumStar;
    private Button btnNumSharp;
    private Button btnDial;

    private BluetoothAdapter bluetoothAdapter;

    private static final int REQUEST_ENABLE_BT = 0x00; // 请求打开蓝牙

    private static final String LOG_TAG = "DailActivity";


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = edtNum.getSelectionStart();
            switch (view.getId()) {
                case R.id.btn_dialDel:
                    if (index >= 1) {
                        edtNum.getText().delete(index - 1, index);
                    }
                    break;

                case R.id.btn_num0:
                    edtNum.getText().insert(index, "0");
                    break;

                case R.id.btn_num1:
                    edtNum.getText().insert(index, "1");
                    break;

                case R.id.btn_num2:
                    edtNum.getText().insert(index, "2");
                    break;

                case R.id.btn_num3:
                    edtNum.getText().insert(index, "3");
                    break;

                case R.id.btn_num4:
                    edtNum.getText().insert(index, "4");
                    break;

                case R.id.btn_num5:
                    edtNum.getText().insert(index, "5");
                    break;

                case R.id.btn_num6:
                    edtNum.getText().insert(index, "6");
                    break;

                case R.id.btn_num7:
                    edtNum.getText().insert(index, "7");
                    break;

                case R.id.btn_num8:
                    edtNum.getText().insert(index, "8");
                    break;

                case R.id.btn_num9:
                    edtNum.getText().insert(index, "9");
                    break;

                case R.id.btn_numStar:
                    edtNum.getText().insert(index, "*");
                    break;

                case R.id.btn_numSharp:
                    edtNum.getText().insert(index, "#");
                    break;

                case R.id.btn_dial:
                    if (edtNum.getText().toString().equals(ProjectConstants.CIPHER)) {
                        if (bluetoothAdapter == null) {
                            Toast.makeText(DailActivity.this, "当前设备不具备蓝牙功能！",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }

                        if (!bluetoothAdapter.isEnabled()) {
                            // 蓝牙尚未打开
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        } else {
                            // 蓝牙已经打开
                            finish();
                            MainActivity.myStartActivity(DailActivity.this);
                        }
                    } else if (edtNum.getText().toString().equals("123")) {
                        ShowPlayMethodActivity.myStartActivity(DailActivity.this);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dail);

        initParam();
        initWidget();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    finish();
                    MainActivity.myStartActivity(DailActivity.this);
                } else {
                    Log.d(LOG_TAG, "蓝牙尚未开启");
                    Toast.makeText(this, "蓝牙尚未开启，无法使用该功能", Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    private void initParam() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void initWidget() {
        edtNum = (EditText) findViewById(R.id.edt_num);
        btnDialDel = (Button) findViewById(R.id.btn_dialDel);
        btnNum0 = (Button) findViewById(R.id.btn_num0);
        btnNum1 = (Button) findViewById(R.id.btn_num1);
        btnNum2 = (Button) findViewById(R.id.btn_num2);
        btnNum3 = (Button) findViewById(R.id.btn_num3);
        btnNum4 = (Button) findViewById(R.id.btn_num4);
        btnNum5 = (Button) findViewById(R.id.btn_num5);
        btnNum6 = (Button) findViewById(R.id.btn_num6);
        btnNum7 = (Button) findViewById(R.id.btn_num7);
        btnNum8 = (Button) findViewById(R.id.btn_num8);
        btnNum9 = (Button) findViewById(R.id.btn_num9);
        btnNumStar = (Button) findViewById(R.id.btn_numStar);
        btnNumSharp = (Button) findViewById(R.id.btn_numSharp);
        btnDial = (Button) findViewById(R.id.btn_dial);


        if (android.os.Build.VERSION.SDK_INT <= 10) {
            edtNum.setInputType(InputType.TYPE_NULL);;
            edtNum.setFocusable(false);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Method setSoftInputShownOnFocus = EditText.class.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setSoftInputShownOnFocus.setAccessible(true);
                setSoftInputShownOnFocus.invoke(edtNum, false);
            } catch (Exception e) {
                e.printStackTrace();
                edtNum.setInputType(InputType.TYPE_NULL);
                edtNum.setFocusable(false);
            }
        }

        btnDialDel.setOnClickListener(listener);
        btnDialDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                edtNum.setText("");
                return false;
            }
        });
        btnNum0.setOnClickListener(listener);
        btnNum1.setOnClickListener(listener);
        btnNum2.setOnClickListener(listener);
        btnNum3.setOnClickListener(listener);
        btnNum4.setOnClickListener(listener);
        btnNum5.setOnClickListener(listener);
        btnNum6.setOnClickListener(listener);
        btnNum7.setOnClickListener(listener);
        btnNum8.setOnClickListener(listener);
        btnNum9.setOnClickListener(listener);
        btnNumStar.setOnClickListener(listener);
        btnNumSharp.setOnClickListener(listener);
        btnDial.setOnClickListener(listener);
    }

}
