import OtherClasses.Business;
import OtherClasses.Database;
import OtherClasses.FT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import OtherClasses.HT;
import com.google.gson.Gson;

import static OtherClasses.Database.insertTFIDF;

public class Main {

    //static HT<String, Business> businesses = new HT();//key= businessID, value= Business object
    //static HT<String, String> businessesByName= new Hashtable<>(); //key= businessName, value= businessID; so GUI can get businessID from businessName
    //static FT totalWordsFT = new FT(); //Hashtable of all words in all reviews of all businesses; 141223 unique words
    static ArrayList<Business> businesses = new ArrayList<>();//key= index, value= Business
    static HT bht = new HT();//key= businessID , value= businessCategories
    static HT businessesByName= new HT();//key= businessName, value= businessID
    static FT totalCategoriesFT = new FT(); //Hashtable of all categories of all businesses; currently 1006 elements
    static Database db= new Database();//to access db
    static boolean alreadyExecuted= false;//to sure convertDbTFIDFVectorBackToDoubleArray() runs once per program run

    public static void main() throws IOException {

        if(!alreadyExecuted) {


            ///////////////////////////////////Start of Vicky's code
            Gson gson = new Gson();

        /*//------------------------- turning 10,000 businesses into java objects and storing in arraylist----------------------
        FileReader busReader = new FileReader("src/main/java/OtherClasses/new_10000_Business.json");

        BufferedReader br = new BufferedReader(busReader);
        //Hashtable<String, String> bHT = new Hashtable<>();//will hold the 10000 business's businessID?
        int x = 0;
        Business newBusiness= new Business();
        while(br.ready() && x <= 10001 ){
            newBusiness= gson.fromJson(br.readLine(), Business.class);//convert JSON object to Business class
            businesses.put(newBusiness.getBusinessID(), newBusiness);//put newBusiness into businesses hashmap
            businessesByName.put(newBusiness.getBusinessName(), newBusiness.getBusinessID());//put newBusiness in: key= businessName, value= businessID
            x++;
//            for(Business b: businesses){
//                bHT.put(b.getBus_id(), b.getBus_name());
//            }

        }*/

            FileReader busReader = new FileReader("src/main/java/OtherClasses/new_10000_Business.json");
            BufferedReader br = new BufferedReader(busReader);

            Business newBusiness = new Business();
            while (br.ready()) {
                newBusiness = gson.fromJson(br.readLine(), Business.class);//convert JSON object to Business class
                businesses.add(newBusiness);//put newBusiness into businesses ArrayList; value= Business
                bht.add(newBusiness.getBusinessID(), newBusiness.getBusinessName());//put newBusiness in: key= businessID, value= businessName
                businessesByName.add(newBusiness.getBusinessName(), newBusiness.getBusinessID());//put newBusiness in: key= businessName, value= businessID
            }

        /*for(Business b: businesses){
            bht.add(b.getBusinessID(), b.getBusinessCategories());//key= businessID , value= businessCategories
        }*/

            //System.out.println(bht.getValue("bBDDEgkFA1Otx9Lfe7BZUQ"));


            //System.out.println("This is a business : "+businesses.get(businessesByName("The UPS Store")));
            //System.out.println("This is a business that doesn't exist: "+businesses.get(10005));
            //System.out.println("This is businessID for one of The UPS Store: "+businessesByName.getValue("The UPS Store"));//note: there are multiple stores w/ same name
       /* System.out.println("size= "+businesses.size());
        Business b1= businesses.get(1);//"business_id":"mpf3x-BjTdTEA3yCZrAYPw","name":"The UPS Store",
        System.out.println(b1.getBusinessName());*/


            br.close();
            busReader.close();

            //should have businesses arrayList with 10000 business (with businessIDs and businessNames) by this point (plus categories, but not individual categories)

            for (Business b : businesses) {//for each Business in businesses
                b.runFT();//puts categories of business b into a frequency table
                //for(int j=0; j<b.getIndividualCategories().length; j= j+1){//for every 30th category in categories
                for (int j = 0; j < b.getCategoriesFT().getSize(); j = j + 1) {//for every category in categoriesFT
                    totalCategoriesFT.add(b.getIndividualCategories()[j]);//adds individual category to totalCategoriesFT
                }
            }

            //System.out.println("size= "+totalCategoriesFT.getSize());//ie. 1161
            //System.out.println("quantity of businesses w/ pet= "+totalCategoriesFT.getCount("pet"));
            //businesses.get(1).runFT();
            //businesses.get(9999).printCategoriesHashTable();

            //System.out.println(bht.getValue("bBDDEgkFA1Otx9Lfe7BZUQ"));

            //System.out.println("indiv categories of business 1= "+businesses.get(1).getIndividualCategories().toString());

        /*businesses.get(1).setTfidfVector(5.33, 1);//sets tfidf of word (at indexCounter position) for businessX
        System.out.println("tfidf at index 5"+businesses.get(1).getTfidfVector().toString());*/

//------------------------ turning all reviews into java Review objects then putting (via concatenation) relevant reviews into corresponding Business----------------------
   /*     FileReader revReader = new FileReader("src/main/java/OtherClasses/new_reviews.json");
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
*/


            //businesses.get("mpf3x-BjTdTEA3yCZrAYPw").runFT();
            //System.out.println("review of business 1= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getBusinessName());
            //System.out.println("review of business 1= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getReview()); //THIS WORKS!
            //System.out.println("indiv words= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getIndividualWords());
            //System.out.println("count of the in business 1= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getCount("the"));
            //System.out.println("count of the in all businesses= "+totalWordsFT.getCount("the"));


            /////////////////////////////////////////////////////////////////////////////////////////////


            //System.out.println("this is the getTf call: "+ getTF(businesses.get("mpf3x-BjTdTEA3yCZrAYPw"), "the"));
            //System.out.println("this is the getIdf call: "+getIdf("the"));


            //System.out.println("businesses containing the= "+businessesContainingWord("pet"));
//        System.out.println("businesses containing the= "+totalWordsFT.getCount("the"));
            //setTfidfVectorForABusiness(businesses.get(1));
            //System.out.println("business 1's tfidfVector length= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getTfidfVector().length);
            //System.out.println("totalWordsHT's size= "+ totalWordsHT.getSize());
            //System.out.println("totalWordsHT table's index 0 key= "+totalWordsHT.getTable()[1].getKey());
            //System.out.println("businessX's tfidf[0] value= "+businesses[1].getTfidfVector()[0]);
            //System.out.println("total size= "+totalWordsFT.getSize());

            //setTfidfVectorForAllBusinesses();//UNCOMMENT THIS


            //System.out.println("businessX's tfidf[0] value= "+businesses[2].getTfidfVector()[1]);

            //System.out.println("diff BUSINESS COMPARED: "+getCosineSimilarity(businesses.get("mpf3x-BjTdTEA3yCZrAYPw"), businesses.get("FPF2G2svSMZ_twSPsoU_ZQ")));


            //System.out.println("the count= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getCount("the"));//takes like 10 seconds
       /* System.out.println("size of review= "+businesses.get("mpf3x-BjTdTEA3yCZrAYPw").getWordsHashTable().getSize());
        System.out.println("size of review= "+businesses.get("23kqKB5n32XkHliOA2JfFg").getWordsHashTable().getSize());
        System.out.println("size of review= "+businesses.get("B7tXEz7Je7DXnSkDN5xkEw").getWordsHashTable().getSize());*/

            //stem.out.println("TFIDF vector string= "+db.getTFIDF("mpf3x-BjTdTEA3yCZrAYPw"));
            //String tString= db.getTFIDF("mpf3x-BjTdTEA3yCZrAYPw");


       /* String[] holder= tString.split(",");
        System.out.println("holder length= "+holder.length);
        System.out.println("splitted= "+holder[44]);*/

        /*double[] doubleValues = Arrays.stream(tString.split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();
        System.out.println("at index 44= "+doubleValues[44]);*/


            //System.out.println("here is index 44 of bussiness 1= "+businesses.get(1).getTfidfVector()[44]);
            //System.out.println(getBestSimilarity(businesses.get(3))); //UNCOMMENT THIS
            //System.out.println(getOutputForGUI("The UPS Store"));

/*
        if(!alreadyExecuted) {
            convertDbTFIDFVectorBackToDoubleArray();//UNCOMMENT THIS
            alreadyExecuted=true;
        }*/

            convertDbTFIDFVectorBackToDoubleArray();//UNCOMMENT THIS
            alreadyExecuted=true;
        }

    }

    //returns the String text to display in GUI
    public static String getOutputForGUI(String givenBusinessName){

        int index= getIndex(givenBusinessName);

        if(index==-1){
            return "Business name not found";
        }

        return getBestSimilarity(businesses.get(index));
    }

    //returns the index of the first Business (in businesses) that has given businessName
    public static int getIndex(String businessName){
        int indexOfGivenBusiness=-1;
        for(int i=0; i<businesses.size(); i++){//find index of given business
            if(businesses.get(i).getBusinessName().equals(businessName)){
                indexOfGivenBusiness= i;
                //System.out.println("index needed= "+indexOfGivenBusiness);
                return indexOfGivenBusiness;
            }
        }
        return -1;//if given businessName not found
    }


    //ONLY RUN THE FOLLOWING 4 METHODS TO GET DATABASE FILE:
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------------
   /* public static void setTfidfVectorForAllBusinesses(){
        for(Business b: businesses){//for every Business in businesses
            setTfidfVectorForABusiness(b);
        }
    }

    public static void setTfidfVectorForABusiness(Business businessX){
        businessX.setTfidfVectorSize(totalCategoriesFT.getSize());//make businessX's tfid vector the size of the number of unique words in all reviews of all businesses
        int indexCounter= 0;//index of businessX's tfidfVector

         for(int i = 0; i< totalCategoriesFT.getTableLength(); i++){//for each index in totalWordsHT hashTable
            for(FT.Node e = totalCategoriesFT.table[i]; e!=null; e=e.getNext()) {//for every Node (ie. unique word) in totalWordsHT
                businessX.setTfidfVector(getTfidf(businessX, (String) e.getKey()), indexCounter);//sets tfidf of word (at indexCounter position) for businessX
                indexCounter++;
            }
        }

        insertTFIDF(businessX.getBusinessID(), businessX.getTfidfVectorAsString());
        System.out.println("tfidf vector as string= "+ businessX.getTfidfVectorAsString());
        System.out.println("finished setTfidVectorForABusiness for: "+businessX.getBusinessID());
    }


    //returns the number of businesses that have the given word in their review
    public static int businessesContainingWord(String word){
        //search thru all businesses to find number of businesses with the given word
        int counter= 0; //counts number of businesses with given word

        for (int i=0; i<10000; i++){//search each business
            if(businesses.get(i).getCount(word) !=0) {
                counter++;
            }
        }

        for(String businessID: businesses.get){//for every Business in businesses
            if(businesses.get(businessID).getCount(word) !=0){
                counter++;
            }
        }

        return counter;
    }


    //returns TF-IDF calculation
    public static double getTfidf (Business businessX, String word){
        double tf= (double)businessX.getCount(word) / (double)businessX.individualCategories.length;
        double idf= Math.log(10000 / (double)businessesContainingWord(word)); //idf formula assuming 10000 businesses
        if(businessesContainingWord(word)==0)
            return 0.0;

        return tf * idf;
    }
*/
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //re-converts String TFIDF Vector (retrieved from db) back to double[], then place in correct Business class
    public static void convertDbTFIDFVectorBackToDoubleArray(){
        String businessID="";
        String tfidfVectorString="";
        for(int i=0; i<10000; i++){
            businessID= businesses.get(i).getBusinessID();
            tfidfVectorString= db.getTFIDF(businessID);//retrieve tfidfVector from db as a String
            double[] doubleValues = Arrays.stream(tfidfVectorString.split(","))
                    .mapToDouble(Double::parseDouble)
                    .toArray();
            businesses.get(i).setTfidfVector(doubleValues);
            System.out.println("finished index "+i);
        }

    }

    //returns the two Cosine Similarity values closest to 1 with given businessX
    public static String getBestSimilarity(Business businessX){

        if(businessX==null)
            return "Given business is null";

        double bestCosineSimilarityValue;
        double secondBestCosineSimilarityValue;
        Business closestBusiness;
        Business secondClosestBusiness;


        int initialIndex= 0;//index to set up initial closestBusiness and secondClosestBusiness

        closestBusiness= businesses.get(initialIndex);
        bestCosineSimilarityValue = getCosineSimilarity(businessX, businesses.get(initialIndex));//NOT necessarily the highest Cosine Similarity value
        if (businessX.getBusinessName().equals(businesses.get(initialIndex).getBusinessName())){//if businessX and businesses[initialIndex] are the same business
            initialIndex++;
            bestCosineSimilarityValue = getCosineSimilarity(businessX, businesses.get(initialIndex));//set to next business
            closestBusiness= businesses.get(initialIndex);
        }
        initialIndex++;

        secondBestCosineSimilarityValue= getCosineSimilarity(businessX, businesses.get(initialIndex));//NOT necessarily the second-highest Cosine Similarity value
        secondClosestBusiness= businesses.get(initialIndex);
        if (businessX.getBusinessName().equals(businesses.get(initialIndex).getBusinessName())){//if businessX and businesses[initialIndex] are the same business
            initialIndex++;
            secondBestCosineSimilarityValue = getCosineSimilarity(businessX, businesses.get(initialIndex));//set to next business
            secondClosestBusiness= businesses.get(initialIndex);
        }
        initialIndex++;

        //switch values if second is bigger than best value
        if(secondBestCosineSimilarityValue>bestCosineSimilarityValue){
            double holder= secondBestCosineSimilarityValue;
            secondBestCosineSimilarityValue= bestCosineSimilarityValue;
            bestCosineSimilarityValue= holder;
        }

        for(int i=initialIndex; i<businesses.size(); i++) {//for each Business b in businesses
            if (!businessX.getBusinessName().equals(businesses.get(i).getBusinessName())) {//if b is not the same business as businessX
                double cosSim = getCosineSimilarity(businessX, businesses.get(i));//calculate similarity value b/t businesses
                System.out.println("(comparing: "+businessX.getBusinessID()+", "+businesses.get(i).getBusinessID()+")\t");
                //System.out.println("(bestCosSim= "+bestCosineSimilarityValue+" from "+closestBusiness.getBusinessID()+ " secondBestCosSim= "+secondBestCosineSimilarityValue+" from "+secondClosestBusiness.getBusinessID());
                if (cosSim > bestCosineSimilarityValue) {
                    System.out.print("(cosSim>bestCosineSimilarityValue)\t");
                    secondBestCosineSimilarityValue= bestCosineSimilarityValue;//second-best value becomes previous-best value
                    secondClosestBusiness= closestBusiness;//second-best business becomes previous-best business
                    bestCosineSimilarityValue= cosSim;//update best value
                    closestBusiness= businesses.get(i);//update best business
                    System.out.println("(bestCosSim= "+bestCosineSimilarityValue+" from "+closestBusiness.getBusinessID()+ " secondBestCosSim= "+secondBestCosineSimilarityValue+" from "+secondClosestBusiness.getBusinessID());
                }
            }
        }


       /* return closestBusiness.getBusinessName()+" is similar at cosine similarity value= "+bestCosineSimilarityValue+"\n"+
                secondClosestBusiness.getBusinessName()+" is also similar at cosine similarity value= "+secondBestCosineSimilarityValue+"\n";*/
        return closestBusiness.getBusinessName()+"\n"+ secondClosestBusiness.getBusinessName();
    }


    public static double getCosineSimilarity(Business businessA, Business businessB){

       /* if(businessA.equals(businessB)){
            return 1.0;
        }else {
            return calculateCosineSimilarity(businessA.getTfidfVector(), businessB.getTfidfVector());
        }*/

        return calculateCosineSimilarity(businessA.getTfidfVector(), businessB.getTfidfVector());

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


      /* //calculate idf for given word
    public static double getIdf(String word){
        //NOTE; Math.log here is actually the natural log (ie. ln)
        return Math.log(10000 / (double)businessesContainingWord(word)); //idf formula assuming 10000 businesses
    }*/

    /* //calculate tf for word
    public static double getTF(Business businessX, String word){
        //System.out.println("count of word= "+businessX.getCount(word));
        //System.out.println("review length= "+businessX.individualWords.length);
        return (double)businessX.getCount(word) / (double)businessX.individualWords.length; //tf formula
    }
*/




}
