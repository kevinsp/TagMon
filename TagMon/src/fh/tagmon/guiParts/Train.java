package fh.tagmon.guiParts;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import fh.tagmon.R;


public class Train extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
    }


    public void finishActivity(View view) {
        finish();
    }
}
