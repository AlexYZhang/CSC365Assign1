package OtherClasses;

import com.google.gson.annotations.SerializedName;

import static java.lang.Double.NaN;

public class Business {

    @SerializedName("business_id")
    String businessID;

    @SerializedName("name")
    String businessName;
    @SerializedName("categories")
    String categories;

    public String[] individualCategories;

    FT categoriesFT= new FT();

    double[] tfidfVector;//holds tfidf value of each category word


    public Business(String businessID){
        this.businessID= businessID;
    }

    public Business(){};


    //create FT by putting given categories into a FT
    //change this later
    public void runFT() {
        //System.out.println("in runFT");

        if(categories==null){
            categories="";
        }else {
            categories = categories.replaceAll("[^a-zA-Z0-9]", " ");
            categories = categories.trim().replaceAll(" +", " ");
            categories = categories.replaceAll("[,.!]", "");
            categories = categories.toLowerCase();
        }
        individualCategories= categories.split(" ");

        /*//adds each individual category into categoriesFT
        for(String s: individualCategories) {
            if(categories!=null)
                categoriesFT.add(s);//add to hashTable
        }*/

        for(int i=0; i<individualCategories.length;i=i+3) {
            //System.out.println("individualCategories= " + individualCategories[i]);
            categoriesFT.add(individualCategories[i]);
        }




       /* //to test tokenization
        System.out.println("This is the tokenized review:");
        for (String category: individualCategories){
            System.out.println(category);
        }*/





    }


    //returns quantity of given word in review
    public int getCount(String word){
        return categoriesFT.getCount(word);
    }


    //sets the size of the tfidfVector
    public void setTfidfVectorSize(int size){
        tfidfVector= new double[size];
    }

    //sets TfidfVector
    public void setTfidfVector(double tfidf, int index){
        tfidfVector[index]= tfidf;
    }

    //also sets TfidfVector
    public void setTfidfVector(double[] tVector){
        tfidfVector= tVector;
    }

    public double[] getTfidfVector() {
        return tfidfVector;
    }

    public String getTfidfVectorAsString(){

        String tfidfAsString=""+tfidfVector[0];
        for(int i=0; i<tfidfVector.length-1; i++){
            if(tfidfVector[i]==NaN){
                tfidfVector[i]=0.0;
            }
            tfidfAsString=tfidfAsString+ ","+tfidfVector[i];
        }

        return tfidfAsString;
    }


    public void setBusinessName(String businessID){
        this.businessID= businessID;
    }

    public String getBusinessCategories(){
        if(categories == null){
            return "";
        }

        categories = categories.replaceAll("[^a-zA-Z0-9]", " ");
        categories = categories.trim().replaceAll(" +", " ");
        categories = categories.replaceAll("[,.!]","");
        categories = categories.toLowerCase();

        return categories;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public void printCategoriesHashTable(){
        System.out.println("(In Business.printWordsHashTable()): This is the printAll of HT:");
        categoriesFT.printAll();
    }


    public String getBusinessID() {
        return businessID;
    }

    public String getBusinessName(){
        return businessName;
    }

    //for TESTING only (remove later)
    public FT getCategoriesFT(){
        return categoriesFT;
    }

    public String[] getIndividualCategories(){
        return individualCategories;
    }

    //adaptation of Vicky's code
    public String toString() {
        categories = categories.replaceAll("[^a-zA-Z0-9]", " ");
        categories = categories.trim().replaceAll(" +", " ");
        categories = categories.replaceAll("[,.!]","");
        categories = categories.toLowerCase();
        return "{\"business_id\":\"" + businessID + "\",\"name\":\"" + businessName + "\",\"categories\":\"" + categories + "}\n";
    }
}
