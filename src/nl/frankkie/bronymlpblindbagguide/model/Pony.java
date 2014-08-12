package nl.frankkie.bronymlpblindbagguide.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author FrankkieNL
 */
public class Pony implements Parcelable {

    /**
     * Name of the pony. This will appear on-screen.
     */
    String ponyName;
    /**
     * The ponyNumber is an internal ID for the app. It is not the number that
     * appears on the screen, nor is it the 'real' number as in the set. The
     * number on the screen must be in description[0]. This number will be used
     * to save the collected-state to SharedPreferences!
     */
    int ponyNumber;
    /**
     * Path and filename to the image in assets/. for example: Wave
     * 1/01_PinkiePie.jpg
     */
    String imageName;
    /**
     * Description of the pony. This will appear on-screen. Its convention to
     * put the ponyNumber (of the IRL set) in description[0].
     */
    String[] description; //default
    /**
     * Code that appears on the IRL bag. This will appear on-screen.
     */
    String blindbagCode;

    /**
     * Constructor for CSV Deserializer.
     * @param ponyNumber id
     * @param ponyName name
     * @param imageName image
     * @param description code and description
     * @param blindbagCode blindbag code
     */
    public Pony(int ponyNumber, String ponyName, String imageName, String[] description, String blindbagCode) {
        this.ponyNumber = ponyNumber;
        this.ponyName = ponyName;
        this.imageName = imageName;
        this.description = description;
        this.blindbagCode = blindbagCode;
    }

    public String getPonyName() {
        return ponyName;
    }

    public String getImageName() {
        return imageName;
    }

    public String[] getDescription() {
        return description;
    }
    
    public String getBlindbagCode(){
        return blindbagCode;
    }

    //<editor-fold desc="Parcelable" defaultstate="collapsed">
    //////////////
    //PARCELABLE//
    //////////////
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Pony> CREATOR
            = new Parcelable.Creator<Pony>() {
                public Pony createFromParcel(Parcel in) {
                    return new Pony(in);
                }

                public Pony[] newArray(int size) {
                    return new Pony[size];
                }
            };

    private Pony(Parcel in) {
        ponyName = in.readString();
        ponyNumber = in.readInt();
        imageName = in.readString();
        description = in.createStringArray();
        blindbagCode = in.readString();
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(ponyName);
        out.writeInt(ponyNumber);
        out.writeString(imageName);
        out.writeStringArray(description);
        out.writeString(blindbagCode);
    }
    //</editor-fold>
}
