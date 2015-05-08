package pl.codingexercise.codingexercise.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by michalkachel on 08.05.2015.
 */
public class Notification implements Parcelable {

    @SerializedName("identifier")
    private String mIdentifier;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("button_title")
    private String mButtonTitle;
    @SerializedName("image_path")
    private String mImagePath;


    public Notification() {
    }

    public static Notification parseFromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Notification.class);
    }

    public static Notification getFirstElementFromJsonArray(String json){
        Gson gson = new Gson();

        Notification[] notifications = gson.fromJson(json, Notification[].class);
        if(notifications.length > 0){
            return notifications[0];
        } else {
            return new Notification();
        }
    }

    public Spanned prepareMessage() {
        return Html.fromHtml(mDescription);
    }

    public String getIdentifier() {
        return mIdentifier;
    }

    public void setIdentifier(String identifier) {
        mIdentifier = identifier;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getButtonTitle() {
        return mButtonTitle;
    }

    public void setButtonTitle(String buttonTitle) {
        mButtonTitle = buttonTitle;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mIdentifier);
        dest.writeString(this.mTitle);
        dest.writeString(this.mDescription);
        dest.writeString(this.mButtonTitle);
        dest.writeString(this.mImagePath);
    }

    private Notification(Parcel in) {
        this.mIdentifier = in.readString();
        this.mTitle = in.readString();
        this.mDescription = in.readString();
        this.mButtonTitle = in.readString();
        this.mImagePath = in.readString();
    }

    public static final Parcelable.Creator<Notification> CREATOR = new Parcelable.Creator<Notification>() {
        public Notification createFromParcel(Parcel source) {
            return new Notification(source);
        }

        public Notification[] newArray(int size) {
            return new Notification[size];
        }
    };
}
