package wzp.project.majiang.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BaseActivity;
import wzp.project.majiang.util.DensityUtil;

/**
 * Created by wzp on 2017/9/21.
 */

public class ReceiveSendFileActivity extends BaseActivity {

    private Button btnRecvSendMethod;

    private PopupWindow pwRecvSendMethod;
    private View vRecvSendMethod;
    private TextView tvQQ;
    private TextView tvWeChat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_send_file);

        initWidget();
    }

    private void initWidget() {
        btnRecvSendMethod = (Button) findViewById(R.id.btn_recvSendMethod);


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
}
