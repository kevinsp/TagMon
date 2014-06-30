package fh.tagmon;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import fh.tagmon.client.gui.Fight;

/*GameEngineModule gEM = new GameEngineModule();

        setContentView(R.layout.activity_main);*/

public class MainActivity extends Activity {

    private final String TAG = "main";
    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mFilters;
    private TextView mText;
    private int mCount = 0;


    // private SharedPreferences sharedPref;
    
    
    /////testvariables
    //private String tagMonName = "Spongebob";
    ////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start of MonsterDAO test
        
//        Monster monster = null;
//        MonsterDAO monsterDAO = null;
//        
//		try {
//			monsterDAO = new MonsterDAOImpl(this);
//		} catch (SQLException e1) {
//			Log.d("tagmonDB", "SQLEX");
//		} catch (IOException e1) {
//			Log.d("tagmonDB", "IOEX");
//		}
//		
//        try {
//			monster = monsterDAO.getMonster("tag1");
//        } catch (MonsterDAOException e){
//        	Log.d("tagmonDB", "MonsterDAO");
//			e.printStackTrace();
//		} catch (Exception e) {
//			Log.d("tagmonDB", "FAIIIIIIIIIIIIIIL");
//			e.printStackTrace();
//		}
//        
//        Log.d("tagmonDB", "GETTING INTEL: " + monster.getIntelligence());
//        Log.d("tagmonDB", "KONSTITUTION: " + monster.getConstitution());
//        Log.d("tagmonDB", "CURR LIFE POINTS " + monster.getCurrentLifePoints());
        
        // END of MonsterDAO test

        //NFC
        mAdapter = NfcAdapter.getDefaultAdapter(this);

        // Create a generic PendingIntent that will be deliver to this activity. The NFC stack
        // will fill in the intent with the details of the discovered tag before delivering to
        // this activity.
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Setup intent filters for all types of NFC dispatches to intercept all discovered tags
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        IntentFilter tech = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        tech.addDataScheme("vnd.android.nfc");
        mFilters = new IntentFilter[] {
                ndef,
                tech,
                new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
        };



    }

    public void switchToStatsActivity(View view) {
//        Intent myIntent = new Intent(this, Stats.class);
//        startActivity(myIntent);
    }

    public void switchToTrainActivity(View view) {
//        Intent myIntent = new Intent(this, Train.class);
//        startActivity(myIntent);
    }

    public void switchToBattleActivity(View view) {
        Intent myIntent = new Intent(this, Fight.class);
        String battleMode = "normalPvE";
        myIntent.putExtra("battleMode", battleMode);
        startActivity(myIntent);
    }
/*
    public void switchToGenLabActivity(View view) {
        Intent myIntent = new Intent(this, GenLab.class);
        startActivity(myIntent);
    }
*/
    //handling button clicks
    public void onBtnClicked(View v) {
        if (v.getId() == R.id.switch_to_train_activity) {
            switchToTrainActivity(v);
        } else if (v.getId() == R.id.switch_to_battle_activity) {
            switchToBattleActivity(v);
        } else if (v.getId() == R.id.switch_to_stats_activity) {
            switchToStatsActivity(v);
        } else if (v.getId() == R.id.switch_to_gen_lab_activity) {
          //  switchToGenLabActivity(v);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, null);
    }

    @Override
    public void onNewIntent(Intent intent) {
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Log.i("Foreground dispatch", "Discovered tag with intent: " + intent);
        

        MifareUltralight leTag = MifareUltralight.get(tagFromIntent);

        try {
            leTag.connect();
            byte[] payload = leTag.readPages(0);

            String printi = "";
            for( int i = 0 ; i < 8 ; i++){
                printi += String.format("%02x", payload[i] & 0xFF);
            }

            Log.i("TEST", printi);

            Intent myIntent = new Intent(this, Fight.class);
            String monsterId = printi;
            myIntent.putExtra("monsterId", monsterId);
            startActivity(myIntent);


            leTag.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        mAdapter.disableForegroundDispatch(this);
    }
}

