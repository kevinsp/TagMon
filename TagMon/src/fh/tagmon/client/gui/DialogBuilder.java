package fh.tagmon.client.gui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import fh.tagmon.R;
import fh.tagmon.gameengine.abilitys.Ability;
import fh.tagmon.gameengine.abilitys.AbilityComponentTypes;
import fh.tagmon.gameengine.abilitys.IAbilityComponent;
import fh.tagmon.gameengine.player.choseability.AbilityTargetRestriction;


public class DialogBuilder extends Dialog {

    private Context context;
    private String title = "";
    private List<?> items;
    private View.OnClickListener onClickListener;
    private int timeTilClose;
    private Object obj;
    private Handler closeTimeHandler = new Handler();
    private String text;
    private DialogAction dialogAction;



    //ability choose Dialog
    public DialogBuilder(Context context, String title, List<?> items, Object obj, View.OnClickListener onClickListener, DialogAction dialogAction) {
        super(context, R.style.customDialog);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
        this.context = context;
        this.title = title;
        this.items = items;
        this.obj = obj;
        this.onClickListener = onClickListener;
        this.dialogAction = dialogAction;
        initDialog();

    }

    public DialogBuilder(Context context) {
        super(context);
        initDialog();
    }

    //summary dialog
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

        if (items != null && onClickListener != null) {
            setContentView(R.layout.custom_dialog);
            setItemsInScrollList();
            setTitle(title);
        } else if(timeTilClose != 0 && !text.equals("")) {
            setContentView(R.layout.custom_dialog_summary);
            setTitle(title);
            setText();
            closeAfterTime();
        }
        showDialog();

    }

    private void setText() {
        TextView tv = (TextView) findViewById(R.id.summaryText);
        tv.setText(text);
        /*
        TableLayout table = (TableLayout) findViewById(R.id.custom_dialog_scroll_table);
        TableRow tr = new TableRow(context);
        TextView tv = new TextView(context);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size));
        tv.setLayoutParams(new TableRow.LayoutParams(1));
        tv.setGravity(Gravity.CENTER);
        tr.addView(tv);
        table.addView(tr);*/
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
        for (Object item : items) {
            TableRow tr = new TableRow(context);
            LinearLayout ll = new LinearLayout(context);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            tlp.setMargins(20,0,0,0);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setGravity(Gravity.CENTER);
            ll.setLayoutParams(tlp);




            List <Drawable> drawables = getDrawableForItem(item);
            if (!drawables.isEmpty()) {

                for (Drawable drawable : drawables) {
                    ImageView iv = new ImageView(context);
                    iv.setImageDrawable(drawable);

                    iv.setAdjustViewBounds(true);
                    iv.setMaxWidth(85);
                    LinearLayout.LayoutParams imagelp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    iv.setLayoutParams(imagelp);
                    ll.addView(iv);
                }
            }
            tr.addView(ll);

            TextView tv = new TextView(context);
            String itemName = getTextForItem(item);
            tv.setText(itemName);

            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size));
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
           // lp.setMargins(10, 0, 0, 10);
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tr.addView(tv);
            //ll.addView(tv);

            tr.setTag(counter);
            tr.setOnClickListener(onClickListener);
            //tr.addView(ll);
            table.addView(tr);
            counter++;
        }
    }

    private List <Drawable> getDrawableForItem(Object obj) {
        List <Drawable> drawables = new LinkedList<Drawable>();

        if (obj instanceof Ability && dialogAction == DialogAction.CHOOSE_ABILITY) {
            // targetRestriction 0=default, 1=self, 2=enemy, 3=selfANDenemy, 4=enemygroup, 5=owngroup, 6=selfANDenemygroup, 7=owngroupANDenemy
            AbilityTargetRestriction atr = ((Ability) obj).getTargetRestriction();
            atr.getTargetList();
            switch(atr) {
                case SELF:
                    drawables.add(context.getResources().getDrawable(R.drawable.target_self));
                    break;
                case ENEMY:
                    drawables.add(context.getResources().getDrawable(R.drawable.target_enemy));
                    break;
            }

            LinkedList<IAbilityComponent> acl = ((Ability) obj).getAbilityComponents();
            for (IAbilityComponent iac : acl) {
                AbilityComponentTypes act = iac.getComponentType();
                //DAMAGE, BUFF, HEAL, STUN, SCHADENSABSORBATION;
                switch (act) {
                    case DAMAGE:
                        drawables.add(context.getResources().getDrawable(R.drawable.attack));
                        break;
                    case BUFF:
                        drawables.add(context.getResources().getDrawable(R.drawable.buff));
                        break;
                    case HEAL:
                        drawables.add(context.getResources().getDrawable(R.drawable.heal));
                        break;
                    case STUN:
                       drawables.add(context.getResources().getDrawable(R.drawable.debuff));
						break;
                    case SCHADENSABSORBATION:
                        drawables.add(context.getResources().getDrawable(R.drawable.block));
                        break;

                }
            }
        }

        return drawables;
    }

    private String getTextForItem(Object obj) {
        String name = "";

        if (obj instanceof Ability && dialogAction == DialogAction.CHOOSE_ABILITY) {
            name = ((Ability) obj).getAbilityName();
        } else if (obj instanceof String) {
            name = (String) obj;
        }



        return name;
    }

}
