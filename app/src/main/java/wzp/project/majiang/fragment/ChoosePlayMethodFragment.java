package wzp.project.majiang.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.EditPlayMethodActivity;

import static wzp.project.majiang.R.id.btn_edt;


public class ChoosePlayMethodFragment extends Fragment {

    private Button btnEdit;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_play_method, container, false);

        initWidget(view);

        return view;
    }



    private void initWidget(View view) {
        btnEdit = (Button) view.findViewById(btn_edt);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditPlayMethodActivity.myStartActivity(getContext());
            }
        });
    }
}
