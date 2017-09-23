package wzp.project.majiang.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import wzp.project.majiang.R;
import wzp.project.majiang.activity.base.BaseActivity;
import wzp.project.majiang.entity.PlayMethodParameter;
import wzp.project.majiang.widget.MyApplication;

import static android.app.Activity.RESULT_OK;


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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final File file = (File) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_show_file, parent, false);

            holder = new ViewHolder();
            holder.tvFilename = (TextView) convertView.findViewById(R.id.tv_filename);
            holder.tvRead = (TextView) convertView.findViewById(R.id.tv_read);
            holder.tvSend = (TextView) convertView.findViewById(R.id.tv_send);
            holder.tvDelete = (TextView) convertView.findViewById(R.id.tv_delete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String filename = file.getName().substring(0, file.getName().lastIndexOf('.'));
        holder.tvFilename.setText(filename);
        holder.tvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readFile(file)) {
                    ((BaseActivity) context).showToast("读取成功");
                    ((BaseActivity) context).setResult(RESULT_OK);
                    ((BaseActivity) context).finish();
                } else {
                    ((BaseActivity) context).showToast("读取失败");
                }
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("注意")
                        .setMessage("是否确定删除" + filename)
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File fileDel = dataList.get(position);
                                if (fileDel.delete()) {
                                    ((BaseActivity) context).showToast("删除成功");
                                }
                                dataList.remove(position);
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                            }
                        })
                        .show();
            }
        });


        return convertView;
    }

    private boolean readFile(File file) {
        try {
            DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = document.getDocumentElement();
            NodeList list = root.getElementsByTagName("string");

            List<String> strList = new ArrayList<>();
            for (int i = 0; i < list.getLength(); i++) {
                Element playMethod =  (Element) list.item(i);
                Log.d("ShowFileListAdapter", playMethod.getAttribute("name"));
                strList.add(playMethod.getTextContent());
            }

            for (int i = 0; i < 3; i++) {
                MyApplication.getParameterList().set(i, JSON.parseObject(strList.get(i), PlayMethodParameter.class));
            }

            return true;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    class ViewHolder {
        TextView tvFilename;
        TextView tvRead;
        TextView tvSend;
        TextView tvDelete;
    }

}
