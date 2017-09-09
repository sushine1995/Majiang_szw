package wzp.project.majiang.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
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
import wzp.project.majiang.activity.EditChooseMajiangMethodActivity;
import wzp.project.majiang.entity.ChooseMajiangMethod;
import wzp.project.majiang.entity.SingleChooseMajiangMethod;
import wzp.project.majiang.util.DensityUtil;


public class PlayMethodListAdapter extends BaseAdapter {

    private Context context;
    private List<ChooseMajiangMethod> chooseMajiangMethodList;

    private String[] playMethodArr;
    private String[] basicNumArr;
    private String[] specialRuleArr;

    public PlayMethodListAdapter(Context context, List<ChooseMajiangMethod> chooseMajiangMethodList) {
        this.context = context;
        this.chooseMajiangMethodList = chooseMajiangMethodList;

        playMethodArr = context.getResources().getStringArray(R.array.play_method_name_arr);
        basicNumArr = context.getResources().getStringArray(R.array.basic_num_arr);
        specialRuleArr = context.getResources().getStringArray(R.array.special_rule_arr);
    }

    @Override
    public int getCount() {
        return chooseMajiangMethodList.size();
    }

    @Override
    public Object getItem(int position) {
        return chooseMajiangMethodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ChooseMajiangMethod chooseMajiangMethod = (ChooseMajiangMethod) getItem(position);

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

        holder.cbIsSelected.setChecked(chooseMajiangMethod.isSelected());
        holder.cbIsSelected.setText("数据" + (position + 1) + "(循环" + chooseMajiangMethod.getLoopTimes() + "次)");
        holder.ibtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditChooseMajiangMethodActivity.myStartActivity(context);
            }
        });
        listPlayMethod(context, holder.linearPlayMethodList, chooseMajiangMethod.getMethods());

        return convertView;
    }

    private void listPlayMethod(Context context, LinearLayout linearPlayMethodList, List<SingleChooseMajiangMethod> methodList) {
        for (SingleChooseMajiangMethod method : methodList) {
            LinearLayout linearAdded = new LinearLayout(context);
            linearAdded.setOrientation(LinearLayout.HORIZONTAL);
            TextView tvName = new TextView(context);
            tvName.setTextSize(14);
            tvName.setText(method.getName());
            LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(0, (int) DensityUtil.dp2px(context, 30), 1);
            tvName.setLayoutParams(paramName);
            linearAdded.addView(tvName);


            LinearLayout.LayoutParams paramSeparatorLine = new LinearLayout.LayoutParams((int) DensityUtil.dp2px(context, 1), ViewGroup.LayoutParams.MATCH_PARENT);
            paramSeparatorLine.setMargins(0, (int) DensityUtil.dp2px(context, 5), 0, (int) DensityUtil.dp2px(context, 5));

            View separatorLine1 = new View(context);
            separatorLine1.setLayoutParams(paramSeparatorLine);
            separatorLine1.setBackgroundColor(context.getResources().getColor(R.color.white));
            linearAdded.addView(separatorLine1);

            TextView tvNum = new TextView(context);
            tvName.setTextSize(14);
            tvNum.setText("张数：" + basicNumArr[method.getNumIndex()]);
            LinearLayout.LayoutParams paramNum = new LinearLayout.LayoutParams(0, (int) DensityUtil.dp2px(context, 30), 1);
            tvNum.setLayoutParams(paramNum);
            tvNum.setGravity(Gravity.CENTER);
            linearAdded.addView(tvNum);

            View separatorLine2 = new View(context);
            separatorLine2.setLayoutParams(paramSeparatorLine);
            separatorLine2.setBackgroundColor(context.getResources().getColor(R.color.white));
            linearAdded.addView(separatorLine2);

            TextView tvSpecialRule = new TextView(context);
            tvName.setTextSize(14);
            tvSpecialRule.setText(specialRuleArr[method.getSpecialRuleIndex()]);
            LinearLayout.LayoutParams paramSpecialRule = new LinearLayout.LayoutParams(0, (int) DensityUtil.dp2px(context, 30), 2);
            tvSpecialRule.setLayoutParams(paramSpecialRule);
            tvSpecialRule.setPadding((int) DensityUtil.dp2px(context, 20), 0, 0, 0);
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
