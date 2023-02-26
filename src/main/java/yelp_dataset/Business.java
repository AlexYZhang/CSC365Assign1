package yelp_dataset;

import com.google.gson.annotations.SerializedName;

//all reviews put into one single lump review
public class Business {

    @SerializedName("business_id")
    String businessID= null;

    @SerializedName("name")
    String businessName;
    String review; //all reviews put into this one
    public String[] individualWords;
    FT wordsHashTable= new FT();
    double[] tfidfVector;//holds tfidf value of each word

    public Business(){
    }

    public Business(String businessID){
        this.businessID= businessID;
    }


    //create FT by putting given review into a FT
    //change this later
    public void runFT() {
        individualWords= review.split(" ");

        /*//to test tokenization
        System.out.println("This is the tokenized review:");
        for (String word: individualWords){
            System.out.println(word);
        }*/



        //removes periods,commas
        //put into hashtable
        for(int i=0; i<individualWords.length; i++){
            //individualWords[i]= individualWords[i].replaceAll("[\n.,: ]", "");
            //individualWords[i]= individualWords[i].toLowerCase();//converts word to lowercase
            wordsHashTable.add(individualWords[i]);//add to hashTable
        }





    }

    //add to review (ie. concatenate more reviews found for this business)
    public void addToReview(String additionalReview){
        this.review= review +" "+additionalReview;
    }


    //returns quantity of given word in review
    public int getCount(String word){
        return wordsHashTable.getCount(word);
    }


    //sets the size of the tfidfVector
    public void setTfidfVectorSize(int size){
        tfidfVector= new double[size];
    }

    //sets TfidfVector arraylist------------------NOT FINISHED!!!
    public void setTfidfVector(double tfidf, int index){
        tfidfVector[index]= tfidf;
    }

    public double[] getTfidfVector() {
        return tfidfVector;
    }

    public void setBusinessName(String businessID){
        this.businessID= businessID;
    }

    public void setReview(String review){
        this.review= review;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public void printWordsHashTable(){
        System.out.println("(In Business.printWordsHashTable()): This is the printAll of HT:");
        wordsHashTable.printAll();
    }

    public String getReview() {
        return review;
    }

    public String getBusinessID() {
        return businessID;
    }

    public String getBusinessName(){
        return businessName;
    }

    //for TESTING only (remove later)
    public FT getWordsHashTable(){
        return wordsHashTable;
    }

    public String[] getIndividualWords(){
        return individualWords;
    }

    //adaptation of Vicki's code
    public String toString(){
        return "Business{" +
                "business_id='" + businessID + '\''+
                ", name='" + businessName + '\''+
                '}';
    }
}
