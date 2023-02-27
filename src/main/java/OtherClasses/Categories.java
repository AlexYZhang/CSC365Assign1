package OtherClasses;

import com.google.gson.annotations.SerializedName;

public class Categories {
        @SerializedName("business_id")
        String b;
        @SerializedName("categories")
        String c;

        public String getBus_id() {
            return b;
        }

        public String getCategories() {
            return c;
        }

        public String toString() {
            return "Categories{" +
                    "b=' " + b + '\'' +
                    ", c=" + c +
                    '}';
        }


}
