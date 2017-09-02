package wzp.project.majiang.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

/**
 * Created by wzp on 2017/9/2.
 */

public class ListOptionButton extends Button {

    private ListOptionPopupWindow pwListOption;
    private String[] options;
    private int selectedItemPosition;

    public ListOptionButton(Context context) {
        super(context);
        init(context);
    }

    public ListOptionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ListOptionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        pwListOption = new ListOptionPopupWindow(context);

        pwListOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setText(options[position]);
                selectedItemPosition = position;
                pwListOption.dismiss();
            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pwListOption.showAsDropDown(ListOptionButton.this);
            }
        });
    }

    public void setListViewItems(String[] options) {
        this.options = options;
        pwListOption.setListViewItems(options);
        setText(options[0]);
    }

    public void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
        if (options != null && selectedItemPosition < options.length) {
            setText(options[selectedItemPosition]);
        }
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }
}
