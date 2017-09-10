package wzp.project.majiang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import wzp.project.majiang.R;


public class EditChooseMajiangMethodActivity extends AppCompatActivity {

    private ImageButton ibtnBack;

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
        setContentView(R.layout.activity_edit_choose_majiang_method);


        initWidget();
    }


    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);

        ibtnBack.setOnClickListener(listener);
    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, EditChooseMajiangMethodActivity.class);
        context.startActivity(intent);
    }
}
