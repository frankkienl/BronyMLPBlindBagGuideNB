package nl.frankkie.bronymlpblindbagguide;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import nl.frankkie.bronymlpblindbagguide.model.Wave;

import nl.frankkie.bronymlpblindbagguide.model.WaveManager;

/**
 * Created by FrankkieNL on 27-8-13.
 */
public class WavesActivity extends Activity {

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
            Wave w1 = new Wave(
                    this, /* context for Assets */
                    1, /* waveNr */
                    "Wave 1", /* waveName */
                    R.string.wave_1, /* description */
                    "covers/mlp-wave-1-blind-bag.jpg", /* image */
                    "data/wave-1.csv" /* data */
            );
            Wave w2 = new Wave(this, 2, "Wave 2", R.string.wave_2, "covers/mlp-wave-2-blind-bag.jpg", "data/wave-2.csv");
            Wave w3 = new Wave(this, 3, "Wave 3", R.string.wave_3, "covers/mlp-wave-3-blind-bag.jpg", "data/wave-3.csv");
            Wave w4 = new Wave(this, 4, "Wave 4", R.string.wave_4, "covers/mlp-wave-4-blind-bag.jpg", "data/wave-4.csv");
            Wave w5 = new Wave(this, 5, "Wave 5", R.string.wave_5, "covers/mlp-wave-5-blind-bag.jpg", "data/wave-5.csv");
            Wave w6 = new Wave(this, 6, "Wave 6", R.string.wave_6, "covers/mlp-wave-6-blind-bag.jpg", "data/wave-6.csv");
            Wave w7 = new Wave(this, 7, "Wave 7", R.string.wave_7, "covers/mlp-wave-7-blind-bag.jpg", "data/wave-7.csv");
            Wave w8 = new Wave(this, 8, "Wave 8", R.string.wave_8, "covers/mlp-wave-8-blind-bag.jpg", "data/wave-8.csv");
            Wave w8a = new Wave(this, 81, "Wave 8a", R.string.wave_8a, "covers/mlp-wave-8a-uk-blind-bag.jpg", "data/wave-8a.csv");
            Wave w8b = new Wave(this, 82, "Wave 8b", R.string.wave_8b, "covers/mlp-wave-8b-uk-blind-bag.jpg", "data/wave-8b.csv");
            Wave w9 = new Wave(this, 9, "Wave 9", R.string.wave_9, "covers/mlp-wave-9-blind-bag.jpg", "data/wave-9.csv");
            Wave w9a = new Wave(this, 91, "Wave 9a", R.string.wave_9a, "covers/mlp-wave-9a-uk-blind-bag.jpg", "data/wave-9a.csv");
            Wave w9b = new Wave(this, 92, "Wave 9b", R.string.wave_9b, "covers/mlp-wave-9b-uk-blind-bag.jpg", "data/wave-9b.csv");
            Wave w10 = new Wave(this, 10, "Wave 10", R.string.wave_10, "covers/mlp-wave-10-blind-bag.jpg", "data/wave-10.csv");
            Wave w10a = new Wave(this, 101, "Wave 10a", R.string.wave_10a, "covers/mlp-wave-10a-uk-blind-bag.jpg", "data/wave-10.csv");
            Wave w11 = new Wave(this, 11, "Wave 11", R.string.wave_11, "covers/mlp-wave-11-blind-bag.jpg", "data/wave-11.csv");
            Wave collectionSets = new Wave(this, 9001, "Collection Sets", R.string.wave_collection_sets, "covers/mlp-collection-sets-blind-bag.jpg", "data/collection-sets.csv");
            Wave miniSets = new Wave(this, 9002, "Mini Sets", R.string.wave_mini_sets, "covers/mlp-mini-sets-blind-bag.jpg", "data/mini-sets.csv");
            wavemanager = new WaveManager();
            wavemanager.waves = new Wave[]{w1, w2, w3, w4, w5, w6, w7, w8, w8a, w8b, w9, w9a, w9b, w10, w10a, w11, collectionSets, miniSets};
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

    public void initUI() {
        setContentView(R.layout.activity_code);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup container = (ViewGroup) findViewById(R.id.code_container);
        if (wavemanager == null) {
            initWavesCSV();
        }
        for (final Wave w : wavemanager.waves) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.layout_row, container, false);
            try {
                Drawable d = Drawable.createFromStream(getAssets().open(w.getWaveCover()), null);
                ((ImageView) viewGroup.findViewById(R.id.row_icon)).setImageDrawable(d);
            } catch (Exception e) {
                //ignore
            }
            ((TextView) viewGroup.findViewById(R.id.row_title)).setText(w.getWaveName());
            if (w.getDescription() == null || w.getDescription().equals("")) {
                viewGroup.findViewById(R.id.row_secondLine).setVisibility(View.GONE);
            } else {
                ((TextView) viewGroup.findViewById(R.id.row_secondLine)).setText(w.getDescription());
            }
            //Completion
            ProgressBar progressBar = (ProgressBar) viewGroup.findViewById(R.id.row_progress);
            int nrHasPoniesFromWave = 0;
            int nrOfPoniesInWave = w.getPonies().length;
            for (int i = 0; i < nrOfPoniesInWave; i++) {
                if (prefs.getBoolean("w" + w.getWaveNumber() + "p" + i, false)) {
                    nrHasPoniesFromWave++;
                }
            }
            progressBar.setProgress(Util.mapInt(nrHasPoniesFromWave, 0, nrOfPoniesInWave, 0, 100));
            ((TextView) viewGroup.findViewById(R.id.row_progress_text)).setText("" + nrHasPoniesFromWave + " / " + nrOfPoniesInWave);
            //
            viewGroup.setFocusable(true);
            viewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setClass(WavesActivity.this, WaveActivity.class);
                    intent.putExtra("wave", w);
                    startActivity(intent);
                }
            });
            //add to container
            container.addView(viewGroup);
        }
    }
}
