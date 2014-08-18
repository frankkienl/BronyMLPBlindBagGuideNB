package nl.frankkie.bronymlpblindbagguide;

import android.content.Context;
import au.com.bytecode.opencsv.CSVReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import nl.frankkie.bronymlpblindbagguide.model.Pony;

/**
 *
 * @author FrankkieNL
 */
public class CSVDeserializer {

    /**
     * CSV to Pony[] Context needed to access Assets.
     *
     * @param context application context, to access Assets.
     * @param file filename in the Assets.
     * @return array of ponies.
     */
    public static Pony[] getPonies(Context context, String file) throws IOException {
        ArrayList<Pony> ponies = new ArrayList<Pony>();
        Pony[] ans = new Pony[0];
        InputStream inputStream = context.getAssets().open(file);
        CSVReader reader = new CSVReader(new InputStreamReader(inputStream), ';');
        //Photo;Name;Comment
        String[] nextLine;
        int i = 0;
        while ((nextLine = reader.readNext()) != null) {
            if (i == 0) {
                //Skip First one!!
                i++;
                continue;
            }
            // nextLine[] is an array of values from the line
            String[] descriptionTemp = nextLine[2].split("\\|");
            Pony p = new Pony(
                    i++, /* id */
                    nextLine[1], /* name */
                    nextLine[0], /* image */
                    descriptionTemp
            );
            ponies.add(p);
        }
        ans = ponies.toArray(ans);
        return ans;
    }
}
