package nl.frankkie.bronymlpblindbagguide;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import nl.frankkie.bronymlpblindbagguide.model.Wave;

import nl.frankkie.bronymlpblindbagguide.model.WaveManager;

/**
 * Created by FrankkieNL on 27-8-13.
 */
public class WavesActivity extends ListActivity {

    WaveManager wavemanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    /**
     * Init waves
     */
    public void initWavesCSV() {
        try {
            wavemanager = Util.initWavesCSV(this);
        } catch (IOException e) {
            //Its kinda a big deal when this happens!
            e.printStackTrace();
            //So let the user know.
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle("Fatal Error");
            b.setMessage("Data not found, contact developer of this app.\n" + e);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    //remove dialog
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //re init, to show new progress !!
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.waves);
        //ListView lv = (ListView) findViewById(android.R.id.list);
        ListView lv = getListView();
        lv.setAdapter(new WavesListViewAdapter(this));
    }

    public class WavesListViewAdapter extends BaseAdapter {

        LayoutInflater inflater;
        SharedPreferences prefs;

        public WavesListViewAdapter(Activity act) {
            inflater = act.getLayoutInflater();
            prefs = PreferenceManager.getDefaultSharedPreferences(act);
            if (wavemanager == null) {
                initWavesCSV();
            }
        }

        public int getCount() {
            return wavemanager.waves.length;
        }

        public Object getItem(int position) {
            return wavemanager.waves[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = (View) inflater.inflate(R.layout.layout_row, parent, false);
            }
            final Wave w = (Wave) getItem(position);
            try {
                Drawable d = Drawable.createFromStream(getAssets().open(w.getWaveCover()), null);
                ((ImageView) convertView.findViewById(R.id.row_icon)).setImageDrawable(d);
            } catch (Exception e) {
                //ignore
            }
            ((TextView) convertView.findViewById(R.id.row_title)).setText(w.getWaveName());
            if (w.getDescription() == null || w.getDescription().equals("")) {
                convertView.findViewById(R.id.row_secondLine).setVisibility(View.GONE);
            } else {
                ((TextView) convertView.findViewById(R.id.row_secondLine)).setText(w.getDescription());
            }
            //Completion
            ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.row_progress);
            int nrHasPoniesFromWave = 0;
            int nrOfPoniesInWave = w.getPonies().length;
            for (int i = 0; i < nrOfPoniesInWave; i++) {
                if (prefs.getBoolean("w" + w.getWaveNumber() + "p" + i, false)) {
                    nrHasPoniesFromWave++;
                }
            }
            progressBar.setProgress(Util.mapInt(nrHasPoniesFromWave, 0, nrOfPoniesInWave, 0, 100));
            ((TextView) convertView.findViewById(R.id.row_progress_text)).setText("" + nrHasPoniesFromWave + " / " + nrOfPoniesInWave);
            //
            convertView.setFocusable(true);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(WavesActivity.this, WaveActivity.class);
                    intent.putExtra("wave", w);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
