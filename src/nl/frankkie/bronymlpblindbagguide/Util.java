package nl.frankkie.bronymlpblindbagguide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import nl.frankkie.bronymlpblindbagguide.model.Wave;
import nl.frankkie.bronymlpblindbagguide.model.WaveManager;

/**
 *
 * @author FrankkieNL
 */
public class Util {

    public static long mapLong(long x, long in_min, long in_max, long out_min, long out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static int mapInt(int x, int in_min, int in_max, int out_min, int out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    /**
     * Fetch the entire contents of a text file, and return it in a String. This
     * style of implementation does not throw Exceptions to the caller.
     *
     * @param aFile is a file which already exists and can be read.
     * @return contents of file
     */
    static public String getContents(File aFile) {
        //...checks on aFile are elided
        StringBuilder contents = new StringBuilder();

        try {
            //use buffering, reading one line at a time
            //FileReader always assumes default encoding is OK!
            BufferedReader input = new BufferedReader(new FileReader(aFile));
            try {
                String line = null; //not declared within while loop
        /*
                 * readLine is a bit quirky :
                 * it returns the content of a line MINUS the newline.
                 * it returns null only for the END of the stream.
                 * it returns an empty String if two newlines appear in a row.
                 */
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();
    }

    /**
     * Change the contents of text file in its entirety, overwriting any
     * existing text.
     *
     * This style of implementation throws all exceptions to the caller.
     *
     * @param aFile is an existing file which can be written to.
     * @param aContents contents of the file
     * @throws IllegalArgumentException if param does not comply.
     * @throws FileNotFoundException if the file does not exist.
     * @throws IOException if problem encountered during write.
     */
    static public void setContents(File aFile, String aContents)
            throws FileNotFoundException, IOException {
        if (aFile == null) {
            throw new IllegalArgumentException("File should not be null.");
        }
        if (!aFile.exists()) {
            //throw new FileNotFoundException ("File does not exist: " + aFile);
            aFile.createNewFile();
        }
        if (!aFile.isFile()) {
            throw new IllegalArgumentException("Should not be a directory: " + aFile);
        }
        if (!aFile.canWrite()) {
            throw new IllegalArgumentException("File cannot be written: " + aFile);
        }

        //use buffering
        Writer output = new BufferedWriter(new FileWriter(aFile));
        try {
            //FileWriter always assumes default encoding is OK!
            output.write(aContents);
        } finally {
            output.close();
        }
    }

    /**
     * Init waves
     * @return WaveManager which contains Waves.
     */
    public static WaveManager initWavesCSV(Context context) throws IOException {
        String waveString = context.getString(R.string.wave);
        String collectionSetsString = context.getString(R.string.collection_sets);
        String miniSetsString = context.getString(R.string.mini_sets);
        Wave w1 = new Wave(
                context, /* context for Assets */
                1, /* waveNr */
                waveString + " 1", /* waveName */
                R.string.wave_1, /* description */
                "covers/mlp-wave-1-blind-bag.jpg", /* image */
                "data/wave-1.csv" /* data */
        );
        Wave w2 = new Wave(context, 2, waveString + " 2", R.string.wave_2, "covers/mlp-wave-2-blind-bag.jpg", "data/wave-2.csv");
        Wave w3 = new Wave(context, 3, waveString + " 3", R.string.wave_3, "covers/mlp-wave-3-blind-bag.jpg", "data/wave-3.csv");
        Wave w4 = new Wave(context, 4, waveString + " 4", R.string.wave_4, "covers/mlp-wave-4-blind-bag.jpg", "data/wave-4.csv");
        Wave w5 = new Wave(context, 5, waveString + " 5", R.string.wave_5, "covers/mlp-wave-5-blind-bag.jpg", "data/wave-5.csv");
        Wave w6 = new Wave(context, 6, waveString + " 6", R.string.wave_6, "covers/mlp-wave-6-blind-bag.jpg", "data/wave-6.csv");
        Wave w7 = new Wave(context, 7, waveString + " 7", R.string.wave_7, "covers/mlp-wave-7-blind-bag.jpg", "data/wave-7.csv");
        Wave w8 = new Wave(context, 8, waveString + " 8", R.string.wave_8, "covers/mlp-wave-8-blind-bag.jpg", "data/wave-8.csv");
        Wave w8a = new Wave(context, 81, waveString + " 8a", R.string.wave_8a, "covers/mlp-wave-8a-uk-blind-bag.jpg", "data/wave-8a.csv");
        Wave w8b = new Wave(context, 82, waveString + " 8b", R.string.wave_8b, "covers/mlp-wave-8b-uk-blind-bag.jpg", "data/wave-8b.csv");
        Wave w9 = new Wave(context, 9, waveString + " 9", R.string.wave_9, "covers/mlp-wave-9-blind-bag.jpg", "data/wave-9.csv");
        Wave w9a = new Wave(context, 91, waveString + " 9a", R.string.wave_9a, "covers/mlp-wave-9a-uk-blind-bag.jpg", "data/wave-9a.csv");
        Wave w9b = new Wave(context, 92, waveString + " 9b", R.string.wave_9b, "covers/mlp-wave-9b-uk-blind-bag.jpg", "data/wave-9b.csv");
        Wave w10 = new Wave(context, 10, waveString + " 10", R.string.wave_10, "covers/mlp-wave-10-blind-bag.jpg", "data/wave-10.csv");
        Wave w10a = new Wave(context, 101, waveString + " 10a", R.string.wave_10a, "covers/mlp-wave-10a-uk-blind-bag.jpg", "data/wave-10a.csv");
        Wave w11 = new Wave(context, 11, waveString + " 11", R.string.wave_11, "covers/mlp-wave-11-blind-bag.jpg", "data/wave-11.csv");
        Wave collectionSets = new Wave(context, 9001, collectionSetsString, R.string.wave_collection_sets, "covers/mlp-collection-sets-blind-bag.jpg", "data/collection-sets.csv");
        Wave miniSets = new Wave(context, 9002, miniSetsString, R.string.wave_mini_sets, "covers/mlp-mini-sets-blind-bag.jpg", "data/mini-sets.csv");
        WaveManager wavemanager = new WaveManager();
        wavemanager.waves = new Wave[]{w1, w2, w3, w4, w5, w6, w7, w8, w8a, w8b, w9, w9a, w9b, w10, w10a, w11, collectionSets, miniSets};
        return wavemanager;
    }
}