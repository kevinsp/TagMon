package fh.tagmon.guiParts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import fh.tagmon.R;


public class Stats extends Activity {

    private final String TAG = "Stats";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
     //   app = (GlobalVariables) getApplicationContext();

       // attachStatsToList();
    }

    public void finishActivity(View view) {
        finish();
    }
/*
    public void attachStatsToList() {
        if (app.getTagMon() != null) {
            JSONObject stats = app.getTagMon().getStats();
            TableLayout table = (TableLayout) findViewById(R.id.statusTableView);

            for (Iterator<String> iter = stats.keys(); iter.hasNext(); ) {
                try {
                    table.setColumnShrinkable(1, true);
                    TableRow tr = new TableRow(this);


                    String key = iter.next();
                    String value = String.valueOf(stats.get(key));

                    int resID = getResources().getIdentifier(key, "string", getPackageName());
                    String translatedKey = getResources().getString(resID);
                    String debugMessage = String.format("%s: %s", translatedKey, value);
                    Log.d(TAG, debugMessage);

                    TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f);

                    //add the textviews and rows to the table
                    TextView keyTextView = new TextView(getApplicationContext());
                    keyTextView.setText(translatedKey);
                    keyTextView.setTextAppearance(getApplicationContext(), R.style.statusTextViews);
                    keyTextView.setGravity(Gravity.CENTER);
                    keyTextView.setLayoutParams(params);

                    TextView valueTextView = new TextView(getApplicationContext());
                    valueTextView.setText(value);
                    valueTextView.setTextAppearance(getApplicationContext(), R.style.statusTextViews);
                    valueTextView.setGravity(Gravity.CENTER);
                    valueTextView.setLayoutParams(params);

                    tr.addView(keyTextView);
                    tr.addView(valueTextView);

                    table.addView(tr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
}
