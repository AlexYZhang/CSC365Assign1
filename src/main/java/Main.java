import yelp_dataset.Business;
import yelp_dataset.HT;
import yelp_dataset.JObject;

public class Main {

    static JObject[] jsonObjects= new JObject[3]; //holds JSON objects
    static Business[] businesses= new Business[3];//should hold 10000 businesses (change from the 3)
    static HT totalWordsHT= new HT(); //Hashtable of all words in all reviews of all businesses

    public static void main(String args[]){

        //assuming will have an Object with strings from Yelp JSON file
        //use 3 businesses to test idea
        //NOTE: all words are treated as lowercase!!!!
        jsonObjects[0]= new JObject("businessA", "This is a great restaurant, I said.");
        /*jsonObjects[1]= new JObject("businessB", "Great place to hang out after work: " +
                "the prices are decent, and the ambience is fun. It's a bit loud, but very lively. " +
                "The staff is friendly, and the food is good. They have a good selection of drinks.");*/
        jsonObjects[1]= new JObject("businessB", "This is a great restaurant, I said.");
        jsonObjects[2]= new JObject("businessC", "If you decide to eat here, " +
                "just be aware it is going to take about 2 hours from beginning to end. " +
                "We have tried it multiple times, because I want to like it! I have been to " +
                "it's other locations in NJ and never had a bad experience. \n\nThe food is good, " +
                "but it takes a very long time to come out. The waitstaff is very young, but usually pleasant. " +
                "We have just had too many experiences where we spent way too long waiting. " +
                "We usually opt for another diner or restaurant on the weekends, in order to be done quicker.");
        //JObject d= new JObject("businessA", "This is the second review.");


        /*//adds JSON Objects to jsonObjects array (real data should parse into jsonObjects array)
        jsonObjects[0]= a;
        jsonObjects[1]= b;
        jsonObjects[2]= c;*/


        //create each Business, set data for the Business, and put into businesses array
        for(int i=0; i<3; i++){//put each JObject data into a Business
            businesses[i]= new Business(jsonObjects[i].getBusinessID());//set businessID
            businesses[i].setReview(jsonObjects[i].getReview());//set review (use data from Object a)
            businesses[i].runHT();//puts review into a hashTable
            //System.out.println("business "+i+" review: "+businesses[i].getReview());
            //System.out.println();
            for(int j=0; j<businesses[i].getIndividualWords().length; j++){//for each word in review
                totalWordsHT.add(businesses[i].getIndividualWords()[j]);//adds individual word to totalWordsHT
            }
        }

        //System.out.println("this is the getTf call: "+ getTF(businesses[1], "the"));
        //System.out.println("Businesses containing the word= "+businessesContainingWord("the"));
        //System.out.println("this is the getIdf call: "+getIdf("the"));

        //setTfidVectorForABusiness(businesses[1]);
        //System.out.println("business 1's tfidfVector length= "+businesses[1].getTfidfVector().length);
        //System.out.println("totalWordsHT's size= "+ totalWordsHT.getSize());
        //System.out.println("totalWordsHT table's index 0 key= "+totalWordsHT.getTable()[1].getKey());
        //System.out.println("businessX's tfidf[0] value= "+businesses[1].getTfidfVector()[0]);

        setTfidfVectorForAllBusinesses();
        //System.out.println("businessX's tfidf[0] value= "+businesses[2].getTfidfVector()[1]);

        //System.out.println(getCosineSimilarity(businesses[0], businesses[0]));

        System.out.println(getBestSimilarity(businesses[1]));

    }

    public static void setTfidfVectorForAllBusinesses(){
        for(int i=0; i<businesses.length; i++){
            setTfidVectorForABusiness(businesses[i]);
        }
    }

    public static void setTfidVectorForABusiness(Business businessX){
        businessX.setTfidfVectorSize(totalWordsHT.getSize());//make businessX's tfid vector the size of the number of unique words in all reviews of all businesses
        int indexCounter= 0;//index of businessX's tfidfVector
        for(int i=0; i<totalWordsHT.getTableLength(); i++){//for each index in totalWordsHT hashTable
            for(HT.Node e = totalWordsHT.table[i]; e!=null; e=e.getNext()) {//for every Node (ie. unique word) in totalWordsHT
                businessX.setTfidfVector(getTfidf(businessX, (String) e.getKey()), indexCounter);//sets tfidf of word (at indexCounter position) for businessX
                indexCounter++;
            }
        }
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
        for (int i=0; i<3; i++){//search each business
            if(businesses[i].getCount(word) !=0) {
                counter++;
            }
        }
        return counter;
    }

    //calculate idf for given word
    public static double getIdf(String word){
        //NOTE; Math.log here is actually the natural log (ie. ln)
        return Math.log(3.0 / (double)businessesContainingWord(word)); //idf formula assuming 10000 businesses
    }

    //returns TF-IDF calculation
    public static double getTfidf (Business businessX, String word){
        return getTF(businessX, word) * getIdf(word);
    }

    //returns the two Cosine Similarity values closest to 1 with given businessX
    public static String getBestSimilarity(Business businessX){

        int initialIndex= 0;//index to set up initial closestBusiness and secondClosestBusiness

        double bestCosineSimilarityValue = getCosineSimilarity(businessX, businesses[initialIndex]);//NOT necessarily the highest Cosine Similarity value
        String closestBusiness= businesses[initialIndex].getBusinessID();
        if (businessX.getBusinessID().equals(businesses[initialIndex].getBusinessID())){//if businessX and businesses[initialIndex] are the same business
            initialIndex++;
            bestCosineSimilarityValue = getCosineSimilarity(businessX, businesses[initialIndex]);//set to next business
            closestBusiness= businesses[initialIndex].getBusinessID();
        }
        initialIndex++;

        double secondBestCosineSimilarityValue= getCosineSimilarity(businessX, businesses[initialIndex]);//NOT necessarily the second-highest Cosine Similarity value
        String secondClosestBusiness= businesses[initialIndex].getBusinessID();
        if (businessX.getBusinessID().equals(businesses[initialIndex].getBusinessID())){//if businessX and businesses[initialIndex] are the same business
            initialIndex++;
            secondBestCosineSimilarityValue = getCosineSimilarity(businessX, businesses[initialIndex]);//set to next business
            secondClosestBusiness= businesses[initialIndex].getBusinessID();
        }
        initialIndex++;

        //DID NOT DO: switch bestCosineSimilarityValue and secondBestCosineSimilarityValue if bestCosineSimilarityValue is bigger

        for(int i=initialIndex; i<businesses.length; i++){//for all businesses
            if(!businessX.getBusinessID().equals(businesses[i].getBusinessID())){//for all businesses not the given businessX
                double cosSim= getCosineSimilarity(businessX, businesses[i]);//calculate similarity value b/t businesses
                //System.out.print("(comparing: "+businessX.getBusinessID()+", "+businesses[i].getBusinessID()+")\t");
                if(cosSim>bestCosineSimilarityValue){
                    //System.out.print("(cosSim>bestCosineSimilarityValue)\t");
                    secondBestCosineSimilarityValue= bestCosineSimilarityValue;//second-best value becomes previous-best value
                    secondClosestBusiness= closestBusiness;//second-best business becomes previous-best business
                    bestCosineSimilarityValue= cosSim;//update best value
                    closestBusiness= businesses[i].getBusinessID();//update best business
                }
            }

        }

        return closestBusiness+" is similar at cosine similarity value= "+bestCosineSimilarityValue+"\n"+
                secondClosestBusiness+" is also similar at cosine similarity value= "+secondBestCosineSimilarityValue+"\n";
    }

    public static double getCosineSimilarity(Business businessA, Business businessB){
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


}
