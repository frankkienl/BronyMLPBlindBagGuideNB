package nl.frankkie.bronymlpblindbagguide.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author FrankkieNL
 */
public class Pony implements Parcelable {

    String ponyName;
    int ponyNumber;
    String imageName;
    String[] description;
    String blindbagCode;

    public String getPonyName() {
        return ponyName;
    }

    public String getImageName() {
        return imageName;
    }

    public String[] getDescription() {
        return description;
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
        description = new String[]{""}; //default
        in.readStringArray(description);
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
