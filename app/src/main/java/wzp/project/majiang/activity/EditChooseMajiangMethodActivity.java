package wzp.project.majiang.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import wzp.project.majiang.R;


public class EditChooseMajiangMethodActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_choose_majiang_method);


        initWidget();
    }


    private void initWidget() {

    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, EditChooseMajiangMethodActivity.class);
        context.startActivity(intent);
    }
}
