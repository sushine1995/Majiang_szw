package wzp.project.majiang.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import wzp.project.majiang.R;


public class ShowFileListAdapter extends BaseAdapter {

    private Context context;
    private List<File> dataList;


    public ShowFileListAdapter(Context context, List<File> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        File file = (File) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_show_choose_card_method, parent, false);

            holder = new ViewHolder();
            holder.tvFilename = (TextView) convertView.findViewById(R.id.tv_filename);
            holder.tvRead = (TextView) convertView.findViewById(R.id.tv_read);
            holder.tvSend = (TextView) convertView.findViewById(R.id.tv_send);
            holder.tvDelete = (TextView) convertView.findViewById(R.id.tv_delete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvFilename.setText(file.getName());
        holder.tvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return convertView;
    }

    class ViewHolder {
        TextView tvFilename;
        TextView tvRead;
        TextView tvSend;
        TextView tvDelete;
    }

}
