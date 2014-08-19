package nl.frankkie.bronymlpblindbagguide;

import android.app.backup.BackupAgentHelper;
import android.app.backup.SharedPreferencesBackupHelper;

/**
 *
 * @author FrankkieNL
 */
public class MyBackupAgent extends BackupAgentHelper {

    public static final String PREFS_FILENAME = "nl.frankkie.bronymlpblindbagguide_preferences";
    
    // A key to uniquely identify the set of backup data
    public static final String PREFS_BACKUP_KEY = "prefs";
    
    @Override
    public void onCreate() {
        SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, PREFS_FILENAME);
        addHelper(PREFS_BACKUP_KEY, helper);
    }    
    
}
