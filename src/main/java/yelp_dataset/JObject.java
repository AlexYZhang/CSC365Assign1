package yelp_dataset;

import com.google.gson.annotations.SerializedName;

//mimics an object that I assume we will get from JSON data
public class JObject {

    @SerializedName("business_id") //businessID field here is business_id in JSON file
    String businessID="test";//maps to business name in Business data file
    @SerializedName("text")//review field here is text in JSON file
    String review;


    public JObject(String businessID, String review){
        this.businessID= businessID;
        this.review= review;
    }

    public String getBusinessID() {
        return businessID;
    }

    public String getReview() {
        return review;
    }


}
