package wzp.project.majiang.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import wzp.project.majiang.R;
import wzp.project.majiang.util.DensityUtil;

public class ListOptionPopupWindow extends PopupWindow {

	private Context context;

	private ListView listView;
	
	private ArrayAdapter<String> adapter;
	private int screenHeight;

	private static final int POP_WIN_WIDTH = 140;

	
	@SuppressLint("InflateParams")
	public ListOptionPopupWindow(Context context) {
		this.context = context;
		setWidth(DensityUtil.dp2px(context, POP_WIN_WIDTH));
		
		listView = (ListView) LayoutInflater.from(context).inflate(R.layout.pop_win_list, null);
		adapter = new ArrayAdapter<>(context, R.layout.listitem_pw_list);
		listView.setAdapter(adapter);
		
		setContentView(listView);
		setBackgroundDrawable(new ColorDrawable(context.getResources()
				.getColor(R.color.popupwindow_bg)));
		setFocusable(true);

		screenHeight = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay().getHeight();
	}
	
	public ListOptionPopupWindow(Context context, String[] items) {
		this(context);
		setListViewItems(items);
	}

	public void setListViewItems(String[] items) {
		adapter.clear();
		adapter.addAll(items);

		if (items.length > 5) {
			setHeight((int) (5 * context.getResources().getDimension(R.dimen.list_item_option_height)));
		} else {
			setHeight(LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
		}
	}
	
	public void setOnItemClickListener(OnItemClickListener listener) {
		listView.setOnItemClickListener(listener);
	}

	@Override
	public void showAsDropDown(View anchor) {
		int[] outLocation = new int[2];
		anchor.getLocationInWindow(outLocation);

		if (outLocation[1] > 2.0 / 3 * screenHeight) {
			if (adapter.getCount() > 0) {
				if (adapter.getCount() > 5) {
					showAsDropDown(anchor, 0, -(int) (5 * context.getResources().getDimension(R.dimen.list_item_option_height)
							+ context.getResources().getDimension(R.dimen.btn_option_height)));
				} else {
					showAsDropDown(anchor, 0, -(int) (adapter.getCount() * context.getResources().getDimension(R.dimen.list_item_option_height)
							+ context.getResources().getDimension(R.dimen.btn_option_height)));
				}
			}
		} else {
			showAsDropDown(anchor, 0, 0);
		}
	}
}
