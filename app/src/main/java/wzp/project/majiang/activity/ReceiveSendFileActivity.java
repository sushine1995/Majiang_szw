package wzp.project.majiang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BaseActivity;
import wzp.project.majiang.adapter.ShowFileListAdapter;
import wzp.project.majiang.helper.constant.ProjectConstants;
import wzp.project.majiang.util.DensityUtil;

/**
 * Created by wzp on 2017/9/21.
 */

public class ReceiveSendFileActivity extends BaseActivity {

    private ImageButton ibtnBack;
    private Button btnRecvSendMethod;

    private PopupWindow pwRecvSendMethod;
    private View vRecvSendMethod;
    private TextView tvQQ;
    private TextView tvWeChat;

    private ListView lvFile;

    private List<File> fileList;
    private ShowFileListAdapter showFileListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_send_file);

        initData();
        initWidget();
    }

    private void initData() {
        File file = new File(ProjectConstants.baseFilePath);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });
        fileList = new ArrayList<>();
        fileList.addAll(Arrays.asList(files));

        showFileListAdapter = new ShowFileListAdapter(this, fileList);
    }

    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
        btnRecvSendMethod = (Button) findViewById(R.id.btn_recvSendMethod);
        lvFile = (ListView) findViewById(R.id.lv_file);

        vRecvSendMethod = getLayoutInflater().inflate(R.layout.pop_win_recv_send_method, null);
        tvQQ = (TextView) vRecvSendMethod.findViewById(R.id.tv_qq);
        tvWeChat = (TextView) vRecvSendMethod.findViewById(R.id.tv_weChat);
        tvQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwRecvSendMethod.dismiss();
                btnRecvSendMethod.setText("QQ");
            }
        });
        tvWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwRecvSendMethod.dismiss();
                btnRecvSendMethod.setText("微信");
            }
        });

        pwRecvSendMethod = new PopupWindow(vRecvSendMethod, (int) DensityUtil.dp2px(this, 60), LinearLayout.LayoutParams.WRAP_CONTENT);
        pwRecvSendMethod.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popupwindow_bg)));
        pwRecvSendMethod.setFocusable(true);

        lvFile.setAdapter(showFileListAdapter);


        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnRecvSendMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwRecvSendMethod.showAsDropDown(v, 0,
                        DensityUtil.dp2px(ReceiveSendFileActivity.this, 1));
            }
        });
    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, ReceiveSendFileActivity.class);
        context.startActivity(intent);
    }

    public static void myStartActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ReceiveSendFileActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
}
