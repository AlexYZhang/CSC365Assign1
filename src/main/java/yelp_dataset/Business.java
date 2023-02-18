package yelp_dataset;

import java.util.ArrayList;

//all reviews put into one single lump review
public class Business {
    String businessID;
    String review; //all reviews put into this one
    public String[] individualWords;
    HT wordsHashTable= new HT();
    double[] tfidfVector;//holds tfidf value of each word

    public Business(String businessID){
        this.businessID= businessID;
    }


    //create HT by putting given review into a HT
    //change this later
    public void runHT() {
        individualWords= review.split(" ");

        //removes periods,commas
        //put into hashtable
        for(int i=0; i<individualWords.length; i++){
            individualWords[i]= individualWords[i].replaceAll("[.,: ]", "");
            individualWords[i]= individualWords[i].toLowerCase();//converts word to lowercase
            wordsHashTable.add(individualWords[i]);//add to hashTable
        }

       /* //to test tokenization
        System.out.println("This is the tokenized review:");
        for (String word: individualWords){
            System.out.println(word);
        }*/

        //for(Object word: wordsHashTable)

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

    //for TESTING only (remove later)
    public HT getWordsHashTable(){
        return wordsHashTable;
    }

    public String[] getIndividualWords(){
        return individualWords;
    }
}
