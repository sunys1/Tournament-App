/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TournamentInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author 1693031
 */
@Path("cegepgim")
public class GenericResource {
    
    static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
    static final String DB_URL = "jdbc:oracle:thin:@144.217.163.57:1521:XE";
    static final String USER = "singer";
    static final String PASS = "anypw5";
    
    String sql, score, win, TournamentName, duration, TournamentType;
    int TeamID, GameID, TournamentID;
    
    int team_id;
    int team_ranking;
    String team_name;
    
    Connection conn = null;

    Statement stmt = null;
    JSONObject singleParti = new JSONObject();
    JSONObject singleTournament = new JSONObject();
    
    JSONArray AllParticipation = new JSONArray();
    JSONArray AllTournament = new JSONArray();
    
    JSONObject singleTeam = new JSONObject();
    JSONArray teams = new JSONArray();
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of TournamentInfo.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Path("test")
    @Produces("application/json")
    public String getJson() {
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
            System.out.println("Creating statement...");
            stmt = conn.createStatement();

            sql = "SELECT * FROM PARTICIPATION";        

            ResultSet rs = stmt.executeQuery(sql);
           
            while(rs.next()){
                
                TeamID = rs.getInt("TEAMID");   
                GameID = rs.getInt("GAMEID");
                score = rs.getString("SCORE");
                win = rs.getString("WIN");
                
                
                singleParti.accumulate("TEAMID", TeamID);
                singleParti.accumulate("GAMEID", GameID);
                singleParti.accumulate("SCORE", score);
                singleParti.accumulate("WIN", win);
                
                
                AllParticipation.add(singleParti);
                singleParti.clear();
            
            }
            rs.close();
            stmt.close();
            conn.close();

        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return AllParticipation.toString();
    }

    @GET
    @Path("test2&{TeamID}&{GameID}")
    @Produces("application/json")
    public String getJson2(@PathParam("TeamID")int TID,@PathParam("GameID")int GID) {
        
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
         
            sql = "SELECT * FROM PARTICIPATION where TEAMID = " + TID + " AND GameID = " + GID;
                    

            ResultSet rs = stmt.executeQuery(sql); 
           
            while(rs.next()){
                
                TeamID = rs.getInt("TEAMID");   
                GameID = rs.getInt("GAMEID");
                score = rs.getString("SCORE");
                win = rs.getString("WIN");
                
                singleParti.accumulate("TEAMID", TeamID);
                singleParti.accumulate("GAMEID", GameID);
                singleParti.accumulate("SCORE", score);
                singleParti.accumulate("WIN", win);
            }   
                rs.close();
                stmt.close();
                conn.close();
            
        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return singleParti.toString();
    }
    
    @GET
    @Path("test3")
    @Produces("application/json")
    public String getJson3() {
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            
            sql = "SELECT * FROM TOURNAMENT";        

            ResultSet rs = stmt.executeQuery(sql);
           
            while(rs.next()){
                
                TournamentID = rs.getInt("TOURNAMENTID");   
                TournamentName = rs.getString("TOURNAMENTNAME");
                duration = rs.getString("DURATION");
                TournamentType = rs.getString("TOURNAMENTTYPE");
                
                
                singleTournament.accumulate("TOURNAMENTID", TournamentID);
                singleTournament.accumulate("TOURNAMENTNAME", TournamentName);
                singleTournament.accumulate("DURATION", duration);
                singleTournament.accumulate("TOURNAMENTTYPE", TournamentType);
                
                
                AllTournament.add(singleTournament);
                singleTournament.clear();
            
            }
            rs.close();
            stmt.close();
            conn.close();

        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return AllTournament.toString();
    }
    
    @GET
    @Path("test4&{TID}")
    @Produces("application/json")
    public String getJson4(@PathParam("TID")int TID) {
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            
            sql = "SELECT * FROM TOURNAMENT where TOURNAMENTID = " + TID;        

            ResultSet rs = stmt.executeQuery(sql);
           
            while(rs.next()){
                
                TournamentID = rs.getInt("TOURNAMENTID");   
                TournamentName = rs.getString("TOURNAMENTNAME");
                duration = rs.getString("DURATION");
                TournamentType = rs.getString("TOURNAMENTTYPE");
                
                
                singleTournament.accumulate("TOURNAMENTID", TournamentID);
                singleTournament.accumulate("TOURNAMENTNAME", TournamentName);
                singleTournament.accumulate("DURATION", duration);
                singleTournament.accumulate("TOURNAMENTTYPE", TournamentType);
            
            }
            rs.close();
            stmt.close();
            conn.close();

        }
        catch(SQLException se){
            se.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        return singleTournament.toString();
    }
    
    
    @GET
    @Path("test5")
    @Produces("application/json")
    public String getJson5() {
        
       Connection conn =null;
       PreparedStatement stmt=null;
       JSONArray countries=new JSONArray();
       JSONObject singlecountry= new JSONObject();
       try{
           Class.forName(JDBC_DRIVER);
           
           System.out.println("Connecting to database...");
           conn =DriverManager.getConnection(DB_URL,USER,PASS);
           
           System.out.println("Creating statement...");
           
           String sql;
           
           
          
           sql="SELECT * FROM GAME";
           
           stmt=conn.prepareStatement(sql);
           
          
           ResultSet rs=stmt.executeQuery();
           
           
          
           
           while(rs.next()){
               
               int gameid =rs.getInt("GAMEID");
               int tournamentid =rs.getInt("TOURNAMENTID");
               String gametime =rs.getString("GAMETIME");
               String gamedate =rs.getString("GAMEDATE");
               String duration =rs.getString("DURATION");
              
               
               
               singlecountry.accumulate("GAMEID",gameid);
               singlecountry.accumulate("TOURNAMENTID",tournamentid);
               singlecountry.accumulate("GAMETIME",gametime);
               singlecountry.accumulate("GAMEDATE",gamedate);
               singlecountry.accumulate("DURATION",duration);
               countries.add(singlecountry);
               
               
               
               singlecountry.clear();
           }
           
           
           rs.close();
           
           stmt.close();
           conn.close();
           
       }catch(SQLException se){
           se.printStackTrace();
       }catch(Exception e){
           e.printStackTrace();
       }
        
       
       return countries.toString();
    }
    
    @GET
    @Path("test6&{GID}")
    @Produces("application/json")
    public String getJson6(@PathParam("GID") int gID) {
        Connection conn =null;
       PreparedStatement stmt=null;
       JSONArray countries=new JSONArray();
       
       JSONObject singlecountry= new JSONObject();
       try{
           Class.forName(JDBC_DRIVER);
           
           System.out.println("Connecting to database...");
           conn =DriverManager.getConnection(DB_URL,USER,PASS);
           
           System.out.println("Creating statement...");
           
           String sql;
           
           
           
           sql="SELECT * FROM GAME WHERE GAMEID = "+ gID;

           stmt=conn.prepareStatement(sql);
           
          
           ResultSet rs=stmt.executeQuery();
           
           
          
           
           while(rs.next()){
               
               int gameid =rs.getInt("GAMEID");
               int tournamentid =rs.getInt("TOURNAMENTID");
               String gametime =rs.getString("GAMETIME");
               String gamedate =rs.getString("GAMEDATE");
               String duration =rs.getString("DURATION");
              
               
               
               singlecountry.accumulate("GAMEID",gameid);
               singlecountry.accumulate("TOURNAMENTID",tournamentid);
               singlecountry.accumulate("GAMETIME",gametime);
               singlecountry.accumulate("GAMEDATE",gamedate);
               singlecountry.accumulate("DURATION",duration);
              
           }
           
           System.out.println(singlecountry);
           rs.close();
           
           stmt.close();
           conn.close();
           
       }catch(SQLException se){
           se.printStackTrace();
       }catch(Exception e){
           e.printStackTrace();
       }
        
       
       return singlecountry.toString();
    } 
    
    @GET
    @Path("TEAM")
    @Produces("application/json")
    public String getJson7() {
        

        
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database....");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Creating statement");

            String sql;
            stmt = conn.createStatement();
            sql = "SELECT * FROM TEAM";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {

                team_id = rs.getInt("TEAMID");
                team_name = rs.getString("TEAMNAME");
                team_ranking = rs.getInt("TEAMRANKING");
                

                singleTeam.accumulate("TEAMID", team_id);
                singleTeam.accumulate("TEAMNAME", team_name);
                singleTeam.accumulate("TEAMRANKING", team_ranking);
                

                teams.add(singleTeam);
                singleTeam.clear();
            }

            System.out.println(teams);
            rs.close();

            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();

        }


        return teams.toString();
        

    }
    
    @GET
    @Path("team2&{id}")
    @Produces("application/json")
    public String getJson8(@PathParam("id") int theID) {
    JSONObject singleDepartment = new JSONObject();
   
    Connection conn = null;

    PreparedStatement stmt = null;

       try {
           Class.forName(JDBC_DRIVER);

           System.out.println("Connecting to databse...");

           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           System.out.println("Creating statement...");
           
           String sql;
   
           
         
           sql = "SELECT * FROM TEAM WHERE TEAMID = "+theID;
         
           stmt=conn.prepareStatement(sql);
           
           ResultSet rs = stmt.executeQuery(sql);
     
             
            while (rs.next()) {

                team_id = rs.getInt("TEAMID");
                team_name = rs.getString("TEAMNAME");
                team_ranking = rs.getInt("TEAMRANKING");
                

                singleTeam.accumulate("TEAMID", team_id);
                singleTeam.accumulate("TEAMNAME", team_name);
                singleTeam.accumulate("TEAMRANKING", team_ranking);
                

                teams.add(singleTeam);
                singleTeam.clear();
            }
       } catch (SQLException se) {
           se.printStackTrace();
       } catch (Exception e) {
           e.printStackTrace();
       }
       
       
   
        return teams.toString();
           
   }
    
    @GET
    @Path("hello&{id}")
    @Produces("application/json")
    public String getJson9(@PathParam("id")int theID) {
        JSONObject singleEnroNumber = new JSONObject();
        
        JSONArray enrollments = new JSONArray();
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Creating statement...");
            
            String sql;
           sql = "SELECT * FROM ENROLLMENT WHERE TEAMID = " + theID;
           
           stmt = conn.prepareStatement(sql);
           
            System.out.println("the query is " + sql);
            ResultSet rs = stmt.executeQuery();
            
            String date;
            int tid,tourid;
            
            
            
            while(rs.next()){
                
                tid = rs.getInt("TEAMID");
                tourid = rs.getInt("TOURNAMENTID");
                date = rs.getString("ENROLLMENT_DATE");
               
                
                singleEnroNumber.accumulate("TeamId",tid);
                singleEnroNumber.accumulate("TournamentId",tourid);
                singleEnroNumber.accumulate("EnrollmentDate",date);
                
            }
            
            rs.close();
           
           
            
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
            }
     return singleEnroNumber.toString();
    }
    
    @GET
    @Path("hello")
    @Produces("application/json")
    public String getJson10() {
        JSONObject singleEnroNumber = new JSONObject();
        
        JSONArray enrollments = new JSONArray();
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Creating statement...");
            
            String sql;
           sql = "SELECT * FROM ENROLLMENT";
           
           stmt = conn.prepareStatement(sql);
           
            System.out.println("the query is " + sql);
            ResultSet rs = stmt.executeQuery();
            
            String date;
            int tid,tourid;
            
            
            
            while(rs.next()){
                
                tid = rs.getInt("TEAMID");
                tourid = rs.getInt("TOURNAMENTID");
                date = rs.getString("ENROLLMENT_DATE");
               
                
                singleEnroNumber.accumulate("TeamId",tid);
                singleEnroNumber.accumulate("TournamentId",tourid);
                singleEnroNumber.accumulate("EnrollmentDate",date);
                enrollments.add(singleEnroNumber);
                singleEnroNumber.clear();
                
            }
            System.out.println(enrollments);
            rs.close();
           
           
            
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
            }
     return enrollments.toString();
    }
    
    
    
    @GET
    @Path("player&{id}")
    @Produces("application/json")
    public String getJson11(@PathParam("id")int PID) {
        JSONObject singlePlayer = new JSONObject();
        
        JSONArray players = new JSONArray();
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Creating statement...");
            
            String sql;
           sql = "SELECT * FROM PLAYER WHERE PLAYER_ID = " + PID;
           
           stmt = conn.prepareStatement(sql);
           
            System.out.println("the query is " + sql);
            ResultSet rs = stmt.executeQuery();
            
            String gender,fn,ln,dob;
            int pid,tid;
            long phone;
            
            
            
            while(rs.next()){
                
                tid = rs.getInt("TEAMID");
                pid = rs.getInt("PLAYER_ID");
                phone = rs.getLong("PHONE");
                gender = rs.getString("GENDER");
                fn = rs.getString("FIRST_NAME");
                ln = rs.getString("LAST_NAME");
                dob = rs.getString("DOB");
               
                
                singlePlayer.accumulate("PlayerId",pid);
                singlePlayer.accumulate("TeamId",tid);
                singlePlayer.accumulate("Gender",gender);
                singlePlayer.accumulate("FirstName",fn);
                singlePlayer.accumulate("LastName",ln);
                singlePlayer.accumulate("DOB",dob);
                singlePlayer.accumulate("Phone",phone);
                
                
            }
            
            rs.close();
           
           
            
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
            }
     return singlePlayer.toString();
    }
    
    
    @GET
    @Path("player")
    @Produces("application/json")
    public String getJson12() {
        JSONObject singlePlayer = new JSONObject();
        
        JSONArray players = new JSONArray();
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try{
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Creating statement...");
            
            String sql;
           sql = "SELECT * FROM PLAYER";
           
           stmt = conn.prepareStatement(sql);
           
            System.out.println("the query is " + sql);
            ResultSet rs = stmt.executeQuery();
            
            String gender,fn,ln,dob;
            int pid,tid;
            long phone;
            
            
            
            while(rs.next()){
                
                tid = rs.getInt("TEAMID");
                pid = rs.getInt("PLAYER_ID");
                phone = rs.getLong("PHONE");
                gender = rs.getString("GENDER");
                fn = rs.getString("FIRST_NAME");
                ln = rs.getString("LAST_NAME");
                dob = rs.getString("DOB");
               
                
                singlePlayer.accumulate("PlayerId",pid);
                singlePlayer.accumulate("TeamId",tid);
                singlePlayer.accumulate("Gender",gender);
                singlePlayer.accumulate("FirstName",fn);
                singlePlayer.accumulate("LastName",ln);
                singlePlayer.accumulate("DOB",dob);
                singlePlayer.accumulate("Phone",phone);
                players.add(singlePlayer);
                singlePlayer.clear();
                
            }
            System.out.println(players);
            rs.close();
           
           
            
            stmt.close();
            conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
            }
     return players.toString();
    }
    
}
