package nl.frankkie.bronymlpblindbagguide.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

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

    public int getWaveNumber(){
        return waveNumber;
    }
    
    public String getWaveCover() {
        return imageName;
    }
    
    public String getWaveName(){
        return waveName;
    }
    
    public String getDescription(){
        return description;
    }
    
    public Pony[] getPonies(){
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
            ponies = Arrays.copyOf(parcelableArray, parcelableArray.length, Pony[].class);
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
