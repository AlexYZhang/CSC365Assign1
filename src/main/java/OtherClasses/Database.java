package OtherClasses;

import java.sql.*;

public class Database {

    public Database(){
        //createDatabase();
    }

    public static void main(String[] args) throws Exception{

        //creates a database named Database1 w/ following tables: TFIDFValues
        //createDatabase();

      /*  insertTFIDF("businessIDtest", "{1,2,3}");
        System.out.println(getTFIDF("businessIDtest"));*/


    }

    //connection to db
    static Connection connection= getDBConnection();

    //for testing
    protected static void validConnection(){
        try {
            System.out.println("connection closed:" +connection.isClosed());
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    //returns a Connection to Database1.db; returns null if fails
    protected static Connection getDBConnection(){

        try{
            Connection c = DriverManager.getConnection("jdbc:sqlite:Database1.db");
            return c;//returns Connection
        } catch ( Exception e ) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return null;
    }

    /*//clears all data from table
    public static void clearDb(){
        String jarSQL = "DELETE FROM TFIDFValues";

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(jarSQL);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    //creates Database1 w/ following tables: User, Server, Jar, Memory, JarContributor
    //run once by developer (to create the db and tables) and never again
    private static void createDatabase(){

        try {
            //gives a Connection object representing connection w/ database;
            //query the db through this Connection object
            //c = DriverManager.getConnection("jdbc:sqlite:"+tableName+".db");
            Connection c = connection;
            //System.out.println("created database successfully");

            //Creates a Statement object (ie. query/command) for
            //sending SQL statements to the database connected to Connection c
            Statement stmt = c.createStatement();

            //Create TFIDFValues table:
            //"column_name column_data_type,"
            String command= "CREATE TABLE IF NOT EXISTS TFIDFValues" +
                    "(ID      INTEGER PRIMARY KEY     NOT NULL," +
                    " BusinessID                 String, " +
                    " TFIDF         String)";
            stmt.executeUpdate(command);//gives number of rows affected by executing "command"

            /*
            close() releases this Connection object's database and
            JDBC resources immediately instead of waiting
            for them to be automatically released
            */
            stmt.close();
            //testing: c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        System.out.println("Tables created successfully");

    }

    //returns new Jar's JarID if successful, 0 if not
    public static void insertTFIDF(String businessID, String tfidfVector){

        try{
            Connection c = connection;//gets a Connection to Database1.db
            //System.out.println("Opened database (for insertJar) successfully");

            //insert new Jar into db
            String sql = "INSERT INTO TFIDFValues VALUES(?,?,?)" ;
            PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//return generated keys
            //stmt.setInt(1,ROWID); //did not insert UserID b/c INTEGER PRIMARY KEY auto-increments
            stmt.setString(2,businessID);
            stmt.setString(3, tfidfVector);//second question mark (in sql)
            stmt.executeUpdate();//execute sql

            ResultSet rs= stmt.getGeneratedKeys();//returns any auto-generated keys rows, 0 if none
            //newJarID= rs.getInt(1);//returns value of column 1(ie. JarID)

            stmt.close();
            //testing: c.close();

        }catch( Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            //return 0;
        }

    }

    //get TFIDF vector as a String
    public static String getTFIDF(String businessID) {

        String result="";
        try {
            Connection c = connection;
            //System.out.println("Opened database (for getCreatorID) successfully");

            String sql = "SELECT TFIDF FROM TFIDFValues WHERE BusinessID=?";

            PreparedStatement stmt = c.prepareStatement(sql);
            stmt.setString(1, businessID);

            ResultSet rs = stmt.executeQuery();
            result = rs.getString("TFIDF");//get value of CreatorID column; 0 if doesn't exist

            stmt.close();
            //testing: c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        return result;

    }





}
