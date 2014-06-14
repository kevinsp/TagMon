package fh.tagmon.client.gui;

import fh.tagmon.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class DialogBuilder extends Dialog {

    private Context context;
    private String title = "";
    private CharSequence[] items;
    private View.OnClickListener onClickListener;
    private int timeTilClose;
    private Object obj;
    private Handler closeTimeHandler = new Handler();
    private String text;


    public DialogBuilder(Context context, String title, CharSequence[] items, Object obj, View.OnClickListener onClickListener) {
        super(context, R.style.customDialog);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        this.context = context;
        this.title = title;
        this.items = items;
        this.obj = obj;
        this.onClickListener = onClickListener;
        initDialog();

    }

    public DialogBuilder(Context context) {
        super(context);
        initDialog();
    }

    public DialogBuilder(Context context, String title, String text, int timeTilClose) {
        //timeTilClose = -1 -> isn't closed automatically
        super(context);
        this.context = context;
        this.title = title;
        this.timeTilClose = timeTilClose;
        this.text = text;
        initDialog();
    }


    private void showDialog() {
        this.show();
    }

    private void initDialog() {

        setContentView(R.layout.custom_dialog);
        setTitle(title);

        if (items != null && onClickListener != null) {
            setItemsInScrollList();
        } else if(timeTilClose != 0 && !text.equals("")) {
            setText();
            closeAfterTime();
        }


        showDialog();

    }

    private void setText() {
        TableLayout table = (TableLayout) findViewById(R.id.custom_dialog_scroll_table);
        TableRow tr = new TableRow(context);
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size));
        tv.setLayoutParams(new TableRow.LayoutParams(1));
        tv.setGravity(Gravity.CENTER);
        tr.addView(tv);
        table.addView(tr);
    }

    private void closeAfterTime() {
        if (this.timeTilClose != -1) {
            final Dialog d = this;
            closeTimeHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    d.dismiss();
                }
            }, timeTilClose);
        }
    }

    private void setItemsInScrollList() {
        TableLayout table = (TableLayout) findViewById(R.id.custom_dialog_scroll_table);
        table.setTag(obj);

        int counter = 0;
        for (CharSequence item : items) {
            TableRow tr = new TableRow(context);

            TextView tv = new TextView(context);
            tv.setText(item);
            tv.setOnClickListener(onClickListener);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size));
            tv.setLayoutParams(new TableRow.LayoutParams(1));
            tv.setGravity(Gravity.CENTER);

            tv.setTag(counter);

            counter++;
            tr.addView(tv);
            table.addView(tr);
        }
    }
}
