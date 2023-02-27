import yelp_dataset.Business;
import yelp_dataset.FT;
import yelp_dataset.JObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.SplittableRandom;

import com.google.gson.Gson;
import yelp_dataset.Review;

public class Main {

    static Hashtable<String, Business> businesses = new Hashtable<>();//key= businessID, value= Business object
    static Hashtable<String, String> businessesByName= new Hashtable<>(); //key= businessName, value= businessID; so GUI can get businessID from businessName
    static FT totalWordsFT = new FT(); //Hashtable of all words in all reviews of all businesses

    //Vicky's code
    //private static Object StringUtils;
    //private Business IOUtils;


    public static void main(String[] args) throws IOException {
        ///////////////////////////////////Start of Vicky's code
        Gson gson = new Gson();

        //------------------------- turning 10,000 businesses into java objects and storing in arraylist----------------------
        FileReader busReader = new FileReader("src/main/java/yelp_dataset/new_10000_Business.json");

        BufferedReader br = new BufferedReader(busReader);
        //Hashtable<String, String> bHT = new Hashtable<>();//will hold the 10000 business's businessID?
        int x = 0;
        Business newBusiness= new Business();
        while(br.ready() && x <= 100001 ){
            newBusiness= gson.fromJson(br.readLine(), Business.class);//convert JSON object to Business class
            businesses.put(newBusiness.getBusinessID(), newBusiness);//put newBusiness into businesses hashmap
            businessesByName.put(newBusiness.getBusinessName(), newBusiness.getBusinessID());//put newBusiness in: key= businessName, value= businessID
            x++;
//            for(Business b: businesses){
//                bHT.put(b.getBus_id(), b.getBus_name());
//            }

        }

       /* System.out.println("This is a business with id mpf3x-BjTdTEA3yCZrAYPw: "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw"));
        System.out.println("This is a business that doesn't exist: "+businesses.get("sheep"));
        System.out.println("This is businessID for one of The UPS Store: "+businessesByName.get("The UPS Store"));//note: there are multiple stores w/ same name
        System.out.println("size= "+businesses.size());*/
        //Business b1= businesses.get(1);//"business_id":"mpf3x-BjTdTEA3yCZrAYPw","name":"The UPS Store",



        br.close();
        busReader.close();

        //should have businesses arrayList with 10000 business (with businessIDs and businessNames) by this point(but not Review per business)
        //have now dealt with the Business yelp dataset

//------------------------ turning all reviews into java Review objects then putting (via concatenation) relevant reviews into corresponding Business----------------------
        FileReader revReader = new FileReader("src/main/java/yelp_dataset/new_reviews.json");
        BufferedReader rbr = new BufferedReader(revReader);

        Review newReview;
        int i = 0;
        while(rbr.ready()){
            newReview= gson.fromJson(rbr.readLine(), Review.class);//get a new Review object from JSON
            //System.out.println("new Review= "+newReview.equals(null));
            if(businesses.get(newReview.getBus_id()) !=null){//if newReview has a businessID that is in businesses
                businesses.get(newReview.getBus_id()).addToReview(newReview.getText());//add newReview's text to appropriate Business in businesses
            }
        }
        //at this point, each Business has its review set

        for(Business b: businesses.values()){//for each Business in businesses
            b.runFT();//puts review of business b into a frequency table
            for(int j=0; j<b.getIndividualWords().length; j++){//for each word in review
                totalWordsFT.add(b.getIndividualWords()[j]);//adds individual word to totalWordsHT
            }
        }



        //businesses.get("mpf3x-BjTdTEA3yCZrAYPw").runFT();
        //System.out.println("review of business 1= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getBusinessName());
        //System.out.println("review of business 1= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getReview()); //THIS WORKS!
        //System.out.println("indiv words= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getIndividualWords());
        //System.out.println("count of the in business 1= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getCount("the"));
        //System.out.println("count of the in all businesses= "+totalWordsFT.getCount("the"));






    /////////////////////////////////////////////////////////////////////////////////////////////


        //System.out.println("this is the getTf call: "+ getTF(businesses.get("mpf3x-BjTdTEA3yCZrAYPw"), "the"));
        //System.out.println("this is the getIdf call: "+getIdf("the"));



        System.out.println("businesses containing the= "+businessesContainingWord("the"));
        System.out.println("businesses containing the= "+totalWordsFT.getCount("the"));

        System.out.println("businesses containing my= "+businessesContainingWord("my"));
        System.out.println("businesses containing my= "+totalWordsFT.getCount("my"));
        //setTfidVectorForABusiness(businesses.get("mpf3x-BjTdTEA3yCZrAYPw"));
        //System.out.println("business 1's tfidfVector length= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getTfidfVector().length);
        //System.out.println("totalWordsHT's size= "+ totalWordsHT.getSize());
        //System.out.println("totalWordsHT table's index 0 key= "+totalWordsHT.getTable()[1].getKey());
        //System.out.println("businessX's tfidf[0] value= "+businesses[1].getTfidfVector()[0]);


        //setTfidfVectorForAllBusinesses();//UNCOMMENT THIS


        //System.out.println("businessX's tfidf[0] value= "+businesses[2].getTfidfVector()[1]);

        //System.out.println("diff BUSINESS COMPARED: "+getCosineSimilarity(businesses.get("mpf3x-BjTdTEA3yCZrAYPw"), businesses.get("FPF2G2svSMZ_twSPsoU_ZQ")));





        //System.out.println(getBestSimilarity(businesses.get("mpf3x-BjTdTEA3yCZrAYPw"))); //UNCOMMENT THIS

    }


    public static void setTfidfVectorForAllBusinesses(){
        /*for(int i=0; i<businesses.size(); i++){
            setTfidVectorForABusiness(businesses.get(i));
        }*/

        for(Business b: businesses.values()){//for every Business in businesses
            setTfidVectorForABusiness(b);
        }

    }

    public static void setTfidVectorForABusiness(Business businessX){
        businessX.setTfidfVectorSize(totalWordsFT.getSize());//make businessX's tfid vector the size of the number of unique words in all reviews of all businesses
        int indexCounter= 0;//index of businessX's tfidfVector
        for(int i = 0; i< totalWordsFT.getTableLength(); i++){//for each index in totalWordsHT hashTable
            for(FT.Node e = totalWordsFT.table[i]; e!=null; e=e.getNext()) {//for every Node (ie. unique word) in totalWordsHT
                businessX.setTfidfVector(getTfidf(businessX, (String) e.getKey()), indexCounter);//sets tfidf of word (at indexCounter position) for businessX
                indexCounter++;
            }
        }
        System.out.println("finished setTfidVectorForABusiness for: "+businessX.getBusinessID());
    }

    //calculate tf for word
    public static double getTF(Business businessX, String word){
        //System.out.println("count of word= "+businessX.getCount(word));
        //System.out.println("review length= "+businessX.individualWords.length);
        return (double)businessX.getCount(word) / (double)businessX.individualWords.length; //tf formula
    }

    //returns the number of businesses that have the given word in their review
    public static int businessesContainingWord(String word){
        //search thru all businesses to find number of businesses with the given word
        int counter= 0; //counts number of businesses with given word

        /*for (int i=0; i<3; i++){//search each business
            if(businesses.get(i).getCount(word) !=0) {
                counter++;
            }
        }*/

        for(String businessID: businesses.keySet()){//for every Business in businesses
            if(businesses.get(businessID).getCount(word) !=0){
                counter++;
            }
        }

        return counter;
    }

    //calculate idf for given word
    public static double getIdf(String word){
        //NOTE; Math.log here is actually the natural log (ie. ln)
        return Math.log(10000 / (double)businessesContainingWord(word)); //idf formula assuming 10000 businesses
    }

    //returns TF-IDF calculation
    public static double getTfidf (Business businessX, String word){
        return getTF(businessX, word) * getIdf(word);
    }

    //returns the two Cosine Similarity values closest to 1 with given businessX
    public static String getBestSimilarity(Business businessX){

        if(businessX==null)
            return "Given business is null";

        //int initialIndex= 0;//index to set up initial closestBusiness and secondClosestBusiness
        //String initialBusinessID= "Pns2l4eNsfO8kk83dixA6A";
        //String secondBusinessID= "mpf3x-BjTdTEA3yCZrAYPw";

        double bestCosineSimilarityValue= 0.0;
        double secondBestCosineSimilarityValue= 0.0;
        Business closestBusiness= null;
        Business secondClosestBusiness= null;

        //DID NOT DO: switch bestCosineSimilarityValue and secondBestCosineSimilarityValue if bestCosineSimilarityValue is bigger

        for(Business b: businesses.values()) {//for each Business b in businesses
            if (!businessX.equals(b)) {//if b is not the same business as businessX
                double cosSim = getCosineSimilarity(businessX, b);//calculate similarity value b/t businesses
                System.out.println("(comparing: "+businessX.getBusinessID()+", "+b.getBusinessID()+")\t");
                System.out.println("(bestCosSim= "+bestCosineSimilarityValue+" from "+closestBusiness.getBusinessID()+ " secondBestCosSim= "+secondBestCosineSimilarityValue+" from "+secondClosestBusiness.getBusinessID());
                if (cosSim > bestCosineSimilarityValue) {
                    System.out.print("(cosSim>bestCosineSimilarityValue)\t");
                    secondBestCosineSimilarityValue= bestCosineSimilarityValue;//second-best value becomes previous-best value
                    secondClosestBusiness= closestBusiness;//second-best business becomes previous-best business
                    bestCosineSimilarityValue= cosSim;//update best value
                    closestBusiness= b;//update best business
                    System.out.println("(bestCosSim= "+bestCosineSimilarityValue+" from "+closestBusiness.getBusinessID()+ " secondBestCosSim= "+secondBestCosineSimilarityValue+" from "+secondClosestBusiness.getBusinessID());
                }
            }
        }

        return closestBusiness.getBusinessName()+" is similar at cosine similarity value= "+bestCosineSimilarityValue+"\n"+
                secondClosestBusiness.getBusinessName()+" is also similar at cosine similarity value= "+secondBestCosineSimilarityValue+"\n";
    }

    public static double getCosineSimilarity(Business businessA, Business businessB){
        if(businessA.equals(businessB)){
            return 1.0;
        }else {
            return calculateCosineSimilarity(businessA.getTfidfVector(), businessB.getTfidfVector());
        }
    }

    //returns cosine similarity between tfidf vectors of businessA (ie. tfidfVectorA) and businessB (ie. tfidfCectorB)
    public static double calculateCosineSimilarity(double[] tfidfVectorA, double[] tfidfVectorB){
        double dotProduct =0;
        double lengthA =0;
        double lengthB =0;

        //formula from:
        //https://stackoverflow.com/questions/520241/how-do-i-calculate-the-cosine-similarity-of-two-vectors
        for(int i=0; i<tfidfVectorA.length; i++){
            dotProduct= dotProduct + (tfidfVectorA[i] * tfidfVectorB[i]);
            lengthA= lengthA + Math.pow(tfidfVectorA[i], 2);
            lengthB= lengthB + Math.pow(tfidfVectorB[i], 2);
        }

        return dotProduct / (Math.sqrt(lengthA) * Math.sqrt(lengthB));
    }


}
