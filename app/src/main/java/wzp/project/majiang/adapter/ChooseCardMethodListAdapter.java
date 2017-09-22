package wzp.project.majiang.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.EditChooseCardMethodActivity;
import wzp.project.majiang.activity.EditPlayMethodActivity;
import wzp.project.majiang.entity.ChooseCardMethod;
import wzp.project.majiang.entity.SingleChooseCardMethod;
import wzp.project.majiang.util.DensityUtil;


public class ChooseCardMethodListAdapter extends BaseAdapter {

    private Context context;
    private Fragment fragment;
    private List<ChooseCardMethod> chooseCardMethodList;

    private String[] loopTimesArr;
    private String[] playMethodArr;
    private String[] basicNumArr;
    private String[] specialRuleArr;

    public ChooseCardMethodListAdapter(Context context, Fragment fragment, List<ChooseCardMethod> chooseCardMethodList) {
        this.context = context;
        this.fragment = fragment;
        this.chooseCardMethodList = chooseCardMethodList;

        loopTimesArr = context.getResources().getStringArray(R.array.loop_times_arr);
        playMethodArr = context.getResources().getStringArray(R.array.play_method_name_arr);
        basicNumArr = context.getResources().getStringArray(R.array.basic_num_arr);
        specialRuleArr = context.getResources().getStringArray(R.array.special_rule_arr);
    }

    public ChooseCardMethodListAdapter(Context context, List<ChooseCardMethod> chooseCardMethodList) {
        this.context = context;
        this.chooseCardMethodList = chooseCardMethodList;

        loopTimesArr = context.getResources().getStringArray(R.array.loop_times_arr);
        playMethodArr = context.getResources().getStringArray(R.array.play_method_name_arr);
        basicNumArr = context.getResources().getStringArray(R.array.basic_num_arr);
        specialRuleArr = context.getResources().getStringArray(R.array.special_rule_arr);
    }

    @Override
    public int getCount() {
        return chooseCardMethodList.size();
    }

    @Override
    public Object getItem(int position) {
        return chooseCardMethodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final ChooseCardMethod chooseCardMethod = (ChooseCardMethod) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_edit_choose_card_method, parent, false);

            holder = new ViewHolder();
            holder.cbIsSelected = (CheckBox) convertView.findViewById(R.id.cb_isSelected);
            holder.ibtnEdit = (ImageButton) convertView.findViewById(R.id.ibtn_edit);
            holder.linearPlayMethodList = (LinearLayout) convertView.findViewById(R.id.linear_playMethodList);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.cbIsSelected.setChecked(chooseCardMethod.isSelected());
        holder.cbIsSelected.setText("数据" + (position + 1) + "(循环" + loopTimesArr[chooseCardMethod.getLoopTimes()] + "次)");
        holder.cbIsSelected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chooseCardMethod.setSelected(isChecked);
            }
        });
        holder.ibtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EditChooseCardMethodActivity.myStartActivityForResult(context, fragment,
//                        position, JSON.toJSONString(chooseCardMethod),
//                        ChooseCardMethodFragment.REQUEST_EDIT_CHOOSE_CARD_METHOD);

                EditChooseCardMethodActivity.myStartActivityForResult(context,
                        ((EditPlayMethodActivity) context).getPlayMethod(),
                        position);
            }
        });
        listPlayMethod(context, holder.linearPlayMethodList, chooseCardMethod.getMethods());

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog dlgDelete = new AlertDialog.Builder(context)
                        .setTitle("注意")
                        .setMessage("您确定删除该条记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                chooseCardMethodList.remove(chooseCardMethod);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

                return false;
            }
        });

        return convertView;
    }

    private void listPlayMethod(Context context, LinearLayout linearPlayMethodList, List<SingleChooseCardMethod> methodList) {
        int childCount = linearPlayMethodList.getChildCount();
        for (int i = 0; i < childCount; i++) {
            linearPlayMethodList.removeViewAt(0);
        }

        for (SingleChooseCardMethod method : methodList) {
            LinearLayout linearAdded = new LinearLayout(context);
            linearAdded.setOrientation(LinearLayout.HORIZONTAL);
            TextView tvName = new TextView(context);
            tvName.setTextSize(14);
            tvName.setText(playMethodArr[method.getName()]);
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
            tvNum.setText("张数：" + basicNumArr[method.getNum()]);
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
            tvSpecialRule.setText(specialRuleArr[method.getSpecialRule()]);
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
