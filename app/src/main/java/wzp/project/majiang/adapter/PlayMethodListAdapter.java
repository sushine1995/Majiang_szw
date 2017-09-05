package wzp.project.majiang.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.EditPlayMethodActivity;
import wzp.project.majiang.entity.PlayMethod;
import wzp.project.majiang.entity.SinglePlayMethod;


public class PlayMethodListAdapter extends BaseAdapter {

    private Context context;
    private List<PlayMethod> playMethodList;

    public PlayMethodListAdapter(Context context, List<PlayMethod> playMethodList) {
        this.context = context;
        this.playMethodList = playMethodList;
    }

    @Override
    public int getCount() {
        return playMethodList.size();
    }

    @Override
    public Object getItem(int position) {
        return playMethodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        PlayMethod playMethod = (PlayMethod) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_play_method, parent, false);

            holder = new ViewHolder();
            holder.cbIsSelected = (CheckBox) convertView.findViewById(R.id.cb_isSelected);
            holder.ibtnEdit = (ImageButton) convertView.findViewById(R.id.ibtn_edit);
            holder.linearPlayMethodList = (LinearLayout) convertView.findViewById(R.id.linear_playMethodList);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cbIsSelected.setChecked(playMethod.isSelected());
        holder.ibtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditPlayMethodActivity.myStartActivity(context);
            }
        });
        listPlayMethod(context, holder.linearPlayMethodList, playMethod.getMethods());

        return convertView;
    }

    private void listPlayMethod(Context context, LinearLayout linearPlayMethodList, List<SinglePlayMethod> methodList) {
        for (SinglePlayMethod method : methodList) {
            LinearLayout linearAdded = new LinearLayout(context);
            linearAdded.setOrientation(LinearLayout.HORIZONTAL);
            TextView tvName = new TextView(context);
            tvName.setText(method.getName());
            linearAdded.addView(tvName);

            TextView tvNum = new TextView(context);
            tvNum.setText(" | 张数：" + method.getNumIndex());
            linearAdded.addView(tvNum);

            TextView tvSpecialRule = new TextView(context);
            tvSpecialRule.setText(" | " + method.getSpecialRuleIndex());
            linearAdded.addView(tvSpecialRule);

            linearPlayMethodList.addView(linearAdded);
        }
    }

    class ViewHolder {
        CheckBox cbIsSelected;
        ImageButton ibtnEdit;
        LinearLayout linearPlayMethodList;
    }

}
