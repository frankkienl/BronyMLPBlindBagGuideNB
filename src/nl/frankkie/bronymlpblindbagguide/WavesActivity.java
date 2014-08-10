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
import com.google.gson.Gson;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        initWaves();
        initUI();
    }

    public void initWaves() {
        Gson gson = new Gson();
        try {
            InputStream data = getAssets().open("data.json");
            wavemanager = gson.fromJson(new InputStreamReader(data), WaveManager.class);
        } catch (Exception e) {
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
        initWaves();
        initUI();
    }

    public void initUI() {
        setContentView(R.layout.activity_code);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup container = (ViewGroup) findViewById(R.id.code_container);
        for (final Wave w : wavemanager.waves) {
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.layout_row, container, false);
            try {
                Drawable d = Drawable.createFromStream(getAssets().open(w.getWaveCover()), null);
                ((ImageView) viewGroup.findViewById(R.id.row_icon)).setImageDrawable(d);
            } catch (Exception e) {
                //ignore
            }
            ((TextView) viewGroup.findViewById(R.id.row_title)).setText("Wave " + w.getWaveName());
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
