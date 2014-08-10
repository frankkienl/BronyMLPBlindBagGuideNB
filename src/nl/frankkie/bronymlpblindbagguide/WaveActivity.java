package nl.frankkie.bronymlpblindbagguide;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import nl.frankkie.bronymlpblindbagguide.model.Pony;
import nl.frankkie.bronymlpblindbagguide.model.Wave;

/**
 * Created by FrankkieNL on 27-8-13.
 */
public class WaveActivity extends Activity {

    Wave wave;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wave = getIntent().getParcelableExtra("wave");
        if (wave == null) {
            Toast.makeText(this, "Select a Wave", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        initUI();
        Log.v("MLP", "Wave init done");
    }

    private void initUI() {
        setContentView(R.layout.activity_wave);
        //
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup container = (ViewGroup) findViewById(R.id.code_container);
        Pony[] ponies = wave.getPonies();
        for (int i = 0; i < ponies.length; i++) {
            final Pony pony = ponies[i];
            final int ponyNr = i;
            ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.layout_row_ponies, container, false);
            try {
                Drawable d = Drawable.createFromStream(getAssets().open(pony.getImageName()), null);
                ((ImageView) viewGroup.findViewById(R.id.row_icon)).setImageDrawable(d);
            } catch (Exception e) {
                //ignore
            }
            ((TextView) viewGroup.findViewById(R.id.row_title)).setText(pony.getPonyName());
            if (pony.getDescription() == null || pony.getDescription().length == 0) {
                viewGroup.findViewById(R.id.row_secondLine).setVisibility(View.GONE);
            } else {
                StringBuilder sb = new StringBuilder();
                if (pony.getBlindbagCode() != null && !"".equals(pony.getBlindbagCode())) {
                    sb.append(pony.getBlindbagCode()).append("\n");
                }
                for (String s : pony.getDescription()) {
                    sb.append(s).append("\n");
                }
                ((TextView) viewGroup.findViewById(R.id.row_secondLine)).setText(sb.toString());
            }
            //
            CheckBox cb = (CheckBox) viewGroup.findViewById(R.id.row_checkbox);
            cb.setChecked(prefs.getBoolean("w" + wave.getWaveNumber() + "p" + ponyNr, false));
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    prefs.edit().putBoolean("w" + wave.getWaveNumber() + "p" + ponyNr, b).commit();
                }
            });
            //
            viewGroup.setFocusable(true);
            //add to container
            container.addView(viewGroup);
        }
    }
}
