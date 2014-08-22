package nl.frankkie.bronymlpblindbagguide;

import android.app.Application;
import android.os.Debug;
import android.util.Log;
import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

/**
 *
 * @author FrankkieNL
 */
@ReportsCrashes(
        formKey = "", // This is required for backward compatibility but not used
        formUri = "http://wofje.8s.nl:5984/acra-blindbag/_design/acra-storage/_update/report", //SECRET to be set later
        //Apparantly, the URL has to be set directly, or ACRA won't work.
        reportType = org.acra.sender.HttpSender.Type.JSON,
        httpMethod = org.acra.sender.HttpSender.Method.PUT,
        formUriBasicAuthLogin = "", //SECRET to be set later
        formUriBasicAuthPassword = "" //SECRET to be set later
)
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate(); //To change body of generated methods, choose Tools | Templates.

        //Crash reports
        ACRA.init(this);
        //Set the Secret Password
        SecretAcraKey.providePassword(this);

        //DEBUG
        //Debug.startMethodTracing("bronyblindbag.debugtrace");        
    }
}
