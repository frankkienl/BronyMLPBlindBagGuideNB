package nl.frankkie.bronymlpblindbagguide;

import android.app.Application;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.acra.ACRA;
import org.json.JSONObject;

/**
 *
 * @author fbouwens
 */
public class SecretAcraKey {

    /**
     *
     * @param app
     */
    public static void providePassword(Application app) {
        try {
            InputStream in = app.getAssets().open("acrapassword.json");
            //Some Magic inputstream to string coversion
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while (true) {
                String line = reader.readLine();
                if (line != null) {
                    sb.append(line).append("\n");
                } else {
                    break;
                }
            }
            //Some JSON reading
            JSONObject jSONObject = new JSONObject(sb.toString());
            String formUri = jSONObject.getString("formUri");
            String formUriBasicAuthLogin = jSONObject.getString("formUriBasicAuthLogin");
            String formUriBasicAuthPassword = jSONObject.getString("formUriBasicAuthPassword");

            ACRA.getConfig().setFormUri(formUri);
            ACRA.getConfig().setFormUriBasicAuthLogin(formUriBasicAuthLogin);
            ACRA.getConfig().setFormUriBasicAuthPassword(formUriBasicAuthPassword);
        } catch (Exception ex) {
            //Apparantly something went wrong, so no Crash Reporting.
            //Kinda ironic..
            Log.e("MLP","Could not start ACRA Crash Reporting in BronyMLPBlindBagGuide");
            ex.printStackTrace();
        }
    }
}
