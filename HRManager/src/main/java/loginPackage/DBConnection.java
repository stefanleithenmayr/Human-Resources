package loginPackage;

import java.lang.reflect.Executable;
import java.sql.*;

public class DBConnection {
    private static DBConnection ourInstance = new DBConnection();
    public static DBConnection getInstance() {
        return ourInstance;
    }
    public static final String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/db";
    public static String userName;
    public static String password;
    private Connection conn;
    public static boolean isBoss = false;

    private DBConnection() {

    }

    public boolean login(String userName, String password) throws ClassNotFoundException {
        try {
            Class.forName(DRIVER_STRING);
            conn = DriverManager.getConnection(CONNECTION_STRING, "app", "app");
            boolean existUser = existUser(userName, password);
            this.userName = userName;
            this.password = password;
            if (existUser){
                return true;
            }
            return false;
        } catch (SQLException ex) {
            return false;
        }
    }

    public ResultSet getJobs() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM JOBS");
    }
    public ResultSet GetUsers() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM USERS");
    }
    public  String getUserSkills(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String userSkills;
        while(rs.next()){
            if (rs.getString("USERNAME").equals(userName)){
                userSkills = rs.getString("JOB_SKILLS");
                return userSkills;
            }
        }
        return "";
    }
    public int getUserAge(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        int age;
        while(rs.next()){
            if (rs.getString("USERNAME").equals(userName)){
                age =  rs.getInt("AGE");
                return age;
            }
        }
        return -1;
    }
    public String getUserRealName(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String realName;
        while(rs.next()){
            if (rs.getString("USERNAME").equals(userName)){
                realName =  rs.getString("REALNAME");
                return realName;
            }
        }
        return "";
    }
    public String getUserPlace(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String place;
        while(rs.next()){
            if (rs.getString("USERNAME").equals(userName)){
                place =  rs.getString("ORT");
                return place;
            }
        }
        return "";
    }
    public String getUserStreet(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String street;
        while(rs.next()){
            if (rs.getString("USERNAME").equals(userName)){
                street =  rs.getString("STRASSE");
                return street;
            }
        }
        return "";
    }
    public String getUserTelefonnumber(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String telefonNumber;
        while(rs.next()){
            if (rs.getString("USERNAME").equals(userName)){
                telefonNumber =  rs.getString("TELEFONNR");
                return telefonNumber;
            }
        }
        return "";
    }
    public String getUserEMail(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String eMail;
        while(rs.next()){
            if (rs.getString("USERNAME").equals(userName)){
                eMail =  rs.getString("EMAIL");
                return eMail;
            }
        }
        return "";
    }
    public String getUserDescription(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String description;
        while(rs.next()){
            if (rs.getString("USERNAME").equals(userName)){
                description =  rs.getString("BESCHREIBUNG");
                return description;
            }
        }
        return "";
    }
    public void UpdateAll(String updates) throws SQLException {
        Statement stmt = conn.createStatement();
        String[] lines = updates.split("'");

        stmt.executeUpdate("UPDATE users set USERNAME = '" + lines[0] +"'where username = '"+ userName+"'");
        userName = lines[0];
        stmt.executeUpdate("UPDATE users set PASSWORD = '" + lines[1] +"'where username = '"+ userName+"'");
        password = lines[1];
        stmt.executeUpdate("UPDATE users set REALNAME = '" + lines[2] +"'where username = '"+ userName+"'");
        stmt.executeUpdate("UPDATE users set JOB_SKILLS = '" + lines[3] +"'where username = '"+ userName+"'");
        int age;
        try {
            age = Integer.parseInt(lines[4]);
        } catch (NumberFormatException e) {
            age = -1;
        }
        if (age >= 0){
            stmt.executeUpdate("UPDATE users set AGE = " + age +"where username = '"+ userName+"'");
        }
        if (lines.length > 5){
            stmt.executeUpdate("UPDATE users set ORT = '" + lines[5] +"'where username = '"+ userName+"'");
            stmt.executeUpdate("UPDATE users set STRASSE = '" + lines[6] +"'where username = '"+ userName+"'");
            stmt.executeUpdate("UPDATE users set TELEFONNR = '" + lines[7] +"'where username = '"+ userName+"'");
            stmt.executeUpdate("UPDATE users set EMAIL = '" + lines[8] +"'where username = '"+ userName+"'");
            stmt.executeUpdate("UPDATE users set BESCHREIBUNG = '" + lines[9] +"'where username = '"+ userName+"'");
        }
        }
    public boolean existUser(String userName, String password) throws SQLException {

        ResultSet rs = getInstance().GetUsers();

        while (rs.next()){
            if (rs.getString("USERNAME").equals(userName) && rs.getString("PASSWORD").equals(password)){
                isBoss = Boolean.parseBoolean(rs.getString("isBoss"));
                return true;
            }
        }
        return false;
    }

    public String getJobDesc(String job) throws SQLException {

        String jobDesc = "";
        ResultSet rs = getInstance().getJobs();
        String jobNumber = this.getJobNumber(job);

        try {
            while (rs.next()) {
                try {
                    if (rs.getString("JOB_ID").equals(jobNumber)) {
                        jobDesc = rs.getString("JOB_DESC");
                        break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException ex) {
        }
        return jobDesc;
    }

    public String getJobSkills(String job) throws SQLException {
        String jobSkills = "";
        ResultSet rs = getInstance().getJobs();
        String jobNumber = this.getJobNumber(job);

        try {
            while (rs.next()) {
                try {
                    if (rs.getString("JOB_ID").equals(jobNumber)) {
                        jobSkills = rs.getString("JOB_SKILLS");
                        break;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException ex) {
        }
        return jobSkills;
    }

    private String getJobNumber(String jobString) {
        String number = "";
        boolean found = true;
        for (int i = 0; i < jobString.length() && found; i++) {
            if (jobString.charAt(i) >= '0' && jobString.charAt(i) <= '9') {
                number += jobString.charAt(i);
            } else found = false;
        }
        return number;
    }

    private int countJobs() throws SQLException {
        ResultSet rs = getInstance().getJobs();
        int count = 0;
        try {
            while (rs.next()) {
                count++;
            }
        } catch (SQLException ex) {
        }
        return count;
    }

    public void insertIntoJobs(String jobname, String jobdesc, String jobskills) throws SQLException {
        Statement stmt = conn.createStatement();
        String jobNumber = Integer.toString(this.countJobs()+1);
        stmt.executeUpdate("INSERT INTO JOBS " +
                "VALUES ('"+jobname + "',"+ jobNumber +",'"+ jobdesc+"', '"+jobskills+"')");
    }
}