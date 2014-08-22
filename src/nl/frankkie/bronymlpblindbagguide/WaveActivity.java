package nl.frankkie.bronymlpblindbagguide;

import android.app.Activity;
import android.app.ListActivity;
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
public class WaveActivity extends ListActivity {

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
    
    public void initUI(){
        setContentView(R.layout.activity_with_listview);
        ListView lv = getListView();
        lv.setAdapter(new WaveListAdapter(this));        
    }

    @Override
    protected void onStop() {
        super.onStop();
        //try to clear the images from memory..
        System.gc();
    }

    public class WaveListAdapter extends BaseAdapter {

        LayoutInflater inflater;
        SharedPreferences prefs;

        public WaveListAdapter(Activity act) {
            inflater = act.getLayoutInflater();
            prefs = PreferenceManager.getDefaultSharedPreferences(act);
        }

        public int getCount() {
            return wave.getPonies().length;
        }

        public Object getItem(int position) {
            return wave.getPonies()[position];
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final Pony pony = (Pony) getItem(position);
            final int ponyNr = position;
            if (convertView == null) {
                convertView = (ViewGroup) inflater.inflate(R.layout.layout_row_ponies, parent, false);
            }
            try {
                //Note: I prefix it here with images.
                Drawable d = Drawable.createFromStream(getAssets().open("images/" + pony.getImageName()), null);
                ((ImageView) convertView.findViewById(R.id.row_icon)).setImageDrawable(d);
            } catch (Exception e) {
                //ignore
            }
            ((TextView) convertView.findViewById(R.id.row_title)).setText(pony.getPonyName());
            if (pony.getDescription() == null || pony.getDescription().length == 0) {
                convertView.findViewById(R.id.row_secondLine).setVisibility(View.GONE);
            } else {
                StringBuilder sb = new StringBuilder();
                for (String s : pony.getDescription()) {
                    sb.append(s).append("\n");
                }
                ((TextView) convertView.findViewById(R.id.row_secondLine)).setText(sb.toString());
            }
            //
            CheckBox cb = (CheckBox) convertView.findViewById(R.id.row_checkbox);
            cb.setChecked(prefs.getBoolean("w" + wave.getWaveNumber() + "p" + ponyNr, false));
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    prefs.edit().putBoolean("w" + wave.getWaveNumber() + "p" + ponyNr, b).commit();
                }
            });
            //
            convertView.setFocusable(true);
            return convertView;
        }
    }
}
