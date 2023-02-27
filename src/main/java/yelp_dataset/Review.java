package yelp_dataset;

import com.google.gson.annotations.SerializedName;

//this class is Vicky's code
public class Review {
    @SerializedName("business_id")
    String b;
    @SerializedName("text")
    String t;

    public String getBus_id() { return b; }
    public String getText(){ return t; }

    public String toString() {
        return "Review{" +
                "b=' " + b + '\''+
                ", t=" + t +
                '}';
    }

}
