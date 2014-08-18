package nl.frankkie.bronymlpblindbagguide;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author FrankkieNL
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    public void initUI() {
        setContentView(R.layout.activity_main);
        TextView mainWelcome = (TextView) findViewById(R.id.main_tv);
        Linkify.addLinks(mainWelcome, Linkify.ALL);
        findViewById(R.id.btn_codes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, WavesActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_tutorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_credits).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CreditsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void exportData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Map<String, ?> all = prefs.getAll();
        JSONObject jsonObject = new JSONObject();
        //http://stackoverflow.com/questions/46898/how-do-i-iterate-over-each-entry-in-a-map
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            try {
                if (!Boolean.parseBoolean(entry.getValue().toString())) {
                    //ignore false, save space.
                    continue;
                }
                jsonObject.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        String jsonData = "";
        try {
            jsonData = jsonObject.toString(2);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        //http://stackoverflow.com/questions/9948373/android-share-plain-text-using-intent-to-all-messaging-apps
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Blindbag Data");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, jsonData);
        try {
            startActivity(Intent.createChooser(sharingIntent, "Export Blindbag Data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importData() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Import Data, paste here");
        final EditText ed = new EditText(this);
        b.setView(ed);
        b.setPositiveButton("Import", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                importJson(ed.getText().toString());
            }
        });
        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //dismiss dialog
            }
        });
        b.create().show();
    }

    public void importJson(String json) {
        try {
            JSONObject jSONObject = new JSONObject(json);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            prefs.edit().clear();
            Iterator it = jSONObject.keys();
            while (it.hasNext()) {
                //they only contain 'true', false has been removed in the export fase.
                prefs.edit().putBoolean(it.next().toString(), true);
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Incorrect data, import failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Export data");
        menu.add(0, 1, 0, "Import data");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0: {
                exportData();
                break;
            }
            case 1: {
                importData();
                break;
            }
        }
        return true;
    }
}
