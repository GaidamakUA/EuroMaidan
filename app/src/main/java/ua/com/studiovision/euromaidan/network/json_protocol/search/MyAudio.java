package ua.com.studiovision.euromaidan.network.json_protocol.search;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gaidamak on 11.12.14.
 */
public class  MyAudio implements Parcelable {
    public String author;
    public String name;
    public Integer duration;
    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.name);
        dest.writeValue(this.duration);
        dest.writeString(this.url);
    }

    public MyAudio() {
    }

    private MyAudio(Parcel in) {
        this.author = in.readString();
        this.name = in.readString();
        this.duration = (Integer) in.readValue(Integer.class.getClassLoader());
        this.url = in.readString();
    }

    public static final Parcelable.Creator<MyAudio> CREATOR = new Parcelable.Creator<MyAudio>() {
        public MyAudio createFromParcel(Parcel source) {
            return new MyAudio(source);
        }

        public MyAudio[] newArray(int size) {
            return new MyAudio[size];
        }
    };
}
