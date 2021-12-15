package edu.msu.smalle45.emergency;


import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable {

    /**
     * The name of the person for this contact
     */
    public String contactName;

    /**
     * The number for this contact
     */
    public String contactNumber;

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public Contact(String name, String number) {
        this.contactName = name;
        this.contactNumber = number;
    }

    protected Contact(Parcel in) {
        contactName = in.readString();
        contactNumber = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(contactName);
        parcel.writeString(contactNumber);
    }
}
