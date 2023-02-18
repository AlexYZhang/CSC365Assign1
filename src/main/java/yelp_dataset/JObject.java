package yelp_dataset;

//mimics an object that I assume we will get from JSON data
public class JObject {

    String businessID="test";//maps to business name in Business data file
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
