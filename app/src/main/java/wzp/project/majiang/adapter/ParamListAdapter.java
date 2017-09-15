package wzp.project.majiang.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import wzp.project.majiang.R;


public class ParamListAdapter extends RecyclerView.Adapter<ParamListAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> datas;
    private OnItemClickLitener mOnItemClickLitener;


    public ParamListAdapter(Context context, List<String> datas) {
        mContext = context;
        this.datas = datas;
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.listitem_param, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int i) {
       holder.tvParamName.setText(datas.get(i));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvParamName;

        public MyViewHolder(View view) {
            super(view);
            tvParamName = (TextView) view.findViewById(R.id.tv_paramName);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
