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
import au.com.bytecode.opencsv.CSVReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 *
 * @author FrankkieNL
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        showWelcome();
    }

    public void showWelcome() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("seen", false)) {
            fixWave8b();
            //
            prefs.edit().putBoolean("seen", true).commit();
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle(R.string.welcome_title);
            b.setMessage(R.string.welcome_message);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    //remove dialog
                }
            });
            b.create().show();
        }
    }

    public void fixWave8b() {
        //updating? remove data from Wave 8B, because thats now incorrect
        //installing for the first time? no data, no harm
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        for (int i = 1; i <= 12; i++) {
            edit.remove("w82p" + i);
        }
        edit.commit();
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
        StringBuilder sb = new StringBuilder();//export to CSV
        //Write CSV-header line
        sb.append("Key;Value\n");
        //http://stackoverflow.com/questions/46898/how-do-i-iterate-over-each-entry-in-a-map
        for (Map.Entry<String, ?> entry : all.entrySet()) {
            if (!Boolean.parseBoolean(entry.getValue().toString())) {
                //ignore false, save space.
                continue;
            }
            sb.append(entry.getKey()).append(";").append(entry.getValue()).append("\n");
        }
        //http://stackoverflow.com/questions/9948373/android-share-plain-text-using-intent-to-all-messaging-apps
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Blindbag Data");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, sb.toString());
        try {
            startActivity(Intent.createChooser(sharingIntent, "Export Blindbag Data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void importData() {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(R.string.data_import_paste);
        final EditText ed = new EditText(this);
        b.setView(ed);
        b.setPositiveButton(R.string.data_import, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                importCsv(ed.getText().toString());
            }
        });
        b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                //dismiss dialog
            }
        });
        b.create().show();
    }

    public void importCsv(String csv) {
        CSVReader reader = new CSVReader(new StringReader(csv), ';');
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String[] nextLine;
        int i = 0;
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        try {
            while ((nextLine = reader.readNext()) != null) {
                if (i == 0) {
                    //Skip First one!!
                    //Thats the Header-line
                    i++;
                    continue;
                }
                //they only contain 'true', false has been removed in the export fase.
                editor.putBoolean(nextLine[0], true /*Boolean.getBoolean(nextLine[1])*/);
            }
            editor.commit();
            Toast.makeText(this, R.string.data_import_done, Toast.LENGTH_LONG).show();
        } catch (IOException ex) {
            ex.printStackTrace();
            AlertDialog.Builder b = new AlertDialog.Builder(this);
            b.setTitle(R.string.error);
            b.setMessage(R.string.data_import_paste);
            b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    //remove dialog
                }
            });
            b.create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, getString(R.string.data_export));
        menu.add(0, 1, 0, getString(R.string.data_import));
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
