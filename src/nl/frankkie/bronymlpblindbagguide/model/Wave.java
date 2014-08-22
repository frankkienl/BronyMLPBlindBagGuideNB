package nl.frankkie.bronymlpblindbagguide.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import nl.frankkie.bronymlpblindbagguide.CSVDeserializer;

/**
 *
 * @author FrankkieNL
 */
public class Wave implements Parcelable {

    int waveNumber;
    String waveName;
    String description;
    String imageName;
    Pony[] ponies;
    String csvFile;

    /**
     * Constructor for CSV Deserializer
     *
     * @param c context to access Assets.
     * @param waveNumber
     * @param waveName
     * @param description
     * @param imageName
     * @param csvFile
     * @throws IOException
     */
    public Wave(Context c, int waveNumber, String waveName, int description, String imageName, String csvFile) throws IOException {
        this.waveNumber = waveNumber;
        this.waveName = waveName;
        this.description = c.getString(description);
        this.imageName = imageName;
        this.csvFile = csvFile;
        ponies = CSVDeserializer.getPonies(c, csvFile);
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public String getWaveCover() {
        return imageName;
    }

    public String getWaveName() {
        return waveName;
    }

    public String getDescription() {
        return description;
    }

    public Pony[] getPonies() {
        return ponies;
    }

    //<editor-fold desc="Parcelable" defaultstate="collapsed">
    //////////////
    //PARCELABLE//
    //////////////
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Wave> CREATOR
            = new Parcelable.Creator<Wave>() {
                public Wave createFromParcel(Parcel in) {
                    return new Wave(in);
                }

                public Wave[] newArray(int size) {
                    return new Wave[size];
                }
            };

    private Wave(Parcel in) {
        waveNumber = in.readInt();
        waveName = in.readString();
        description = in.readString();
        imageName = in.readString();
        //http://stackoverflow.com/questions/10071502/read-writing-arrays-of-parcelable-objects
        Parcelable[] parcelableArray = in.readParcelableArray(this.getClass().getClassLoader());
        if (parcelableArray != null) {            
            //ponies = Arrays.copyOf(parcelableArray, parcelableArray.length, Pony[].class);
            //Array.copyOf is API9+, we support 7+, do it with System.arrayCopy instead. (#9)
            ponies = new Pony[parcelableArray.length];
            System.arraycopy(parcelableArray, 0, ponies, 0, parcelableArray.length);
        }
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(waveNumber);
        out.writeString(waveName);
        out.writeString(description);
        out.writeString(imageName);
        out.writeParcelableArray(ponies, 0);
    }
    //</editor-fold>
}
