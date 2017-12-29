package loginPackage;

import java.lang.reflect.Executable;
import java.sql.*;

public class DBConnection {
    private static DBConnection ourInstance = new DBConnection();

    public static DBConnection getInstance() {
        return ourInstance;
    }

    public static final String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/db;create=true";
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
            if (existUser) {
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
<<<<<<< HEAD
    public void insertAppliance(String userName, Integer job_id) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO JOB_APPLICATIONS VALUES ('"+userName+"',"+job_id+")");
    }
    public Integer getApplicationIdByName(String name) throws SQLException {
        ResultSet rs = getInstance().getJobs();
        while (rs.next()){
            if (rs.getString("JOB_NAME").equals(name)){
                return rs.getInt("JOB_ID");
            }
        }
        return -1;
    }
    public ResultSet GetJob_Appliances() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM JOB_APPLICATIONS");
    }
    public String GetJob_AppliancesNameID() throws SQLException {
        ResultSet rs = GetJob_Appliances();
        String returnString = "";
        while(rs.next()){
            returnString += (rs.getString("USERNAME")+";"+rs.getInt("JOB_ID")+"\n");
        }
        return returnString;
    }
    public  String getUserSkills(String userName) throws SQLException {
=======

    public String getUserSkills(String userName) throws SQLException {
>>>>>>> 3c2af4f3e155bd3e28f70c7d1757f421b734f5a5
        ResultSet rs = getInstance().GetUsers();
        String userSkills;
        while (rs.next()) {
            if (rs.getString("USERNAME").equals(userName)) {
                userSkills = rs.getString("JOB_SKILLS");
                return userSkills;
            }
        }
        return "";
    }

    public int getUserAge(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        int age;
        while (rs.next()) {
            if (rs.getString("USERNAME").equals(userName)) {
                age = rs.getInt("AGE");
                return age;
            }
        }
        return -1;
    }

    public String getUserRealName(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String realName;
        while (rs.next()) {
            if (rs.getString("USERNAME").equals(userName)) {
                realName = rs.getString("REALNAME");
                return realName;
            }
        }
        return "";
    }

    public String getUserPlace(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String place;
        while (rs.next()) {
            if (rs.getString("USERNAME").equals(userName)) {
                place = rs.getString("ORT");
                return place;
            }
        }
        return "";
    }

    public String getUserStreet(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String street;
        while (rs.next()) {
            if (rs.getString("USERNAME").equals(userName)) {
                street = rs.getString("STRASSE");
                return street;
            }
        }
        return "";
    }

    public String getUserTelefonnumber(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String telefonNumber;
        while (rs.next()) {
            if (rs.getString("USERNAME").equals(userName)) {
                telefonNumber = rs.getString("TELEFONNR");
                return telefonNumber;
            }
        }
        return "";
    }

    public String getUserEMail(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String eMail;
        while (rs.next()) {
            if (rs.getString("USERNAME").equals(userName)) {
                eMail = rs.getString("EMAIL");
                return eMail;
            }
        }
        return "";
    }

    public String getUserDescription(String userName) throws SQLException {
        ResultSet rs = getInstance().GetUsers();
        String description;
        while (rs.next()) {
            if (rs.getString("USERNAME").equals(userName)) {
                description = rs.getString("BESCHREIBUNG");
                return description;
            }
        }
        return "";
    }

    public void UpdateAll(String updates) throws SQLException {
        Statement stmt = conn.createStatement();
        String[] lines = updates.split("'");

        stmt.executeUpdate("UPDATE users set USERNAME = '" + lines[0] + "'where username = '" + userName + "'");
        userName = lines[0];
        stmt.executeUpdate("UPDATE users set PASSWORD = '" + lines[1] + "'where username = '" + userName + "'");
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
            if (lines[9] == null){
                lines[9] = " ";
            }
            stmt.executeUpdate("UPDATE users set BESCHREIBUNG = '" + lines[9] +"'where username = '"+ userName+"'");
        }
        }

    public boolean existUser(String userName, String password) throws SQLException {

        ResultSet rs = getInstance().GetUsers();

        while (rs.next()) {
            if (rs.getString("USERNAME").equals(userName) && rs.getString("PASSWORD").equals(password)) {
                isBoss = Boolean.parseBoolean(rs.getString("isBoss"));
                return true;
            }
        }
        return false;
    }
    public String getUserNameJobIDByString(String givenString) throws SQLException {
         ResultSet rsappliances = getInstance().GetJob_Appliances();
         ResultSet rsjobs = getInstance().getJobs();
         ResultSet rsUsers = getInstance().GetUsers();
         String userName = "";
         String realName = "";
         Integer jobID = -1;
         while (rsUsers.next()){
            if (givenString.contains(rsUsers.getString("REALNAME"))){
                userName = rsUsers.getString("USERNAME");
                break;
            }
        }
         while (rsjobs.next()){
             if (givenString.contains(rsjobs.getString("JOB_NAME"))){
                jobID = rsjobs.getInt("JOB_ID");
                break;
             }
         }
         return userName+";"+jobID;
    }
    public String getJobByJobID(String job_id) throws SQLException {
        String jobSkills = "";
        ResultSet rs = getInstance().getJobs();
        while (rs.next()){
            if (rs.getString("JOB_ID").equals(job_id)){
                return rs.getString("JOB_NAME");
            }
        }
        return "";
    }
    public Integer getJobID(String job) throws SQLException {
        ResultSet rs = getInstance().getJobs();
        Integer jobNumber = Integer.parseInt(this.getJobNumber(job));
        return jobNumber;
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
        String jobNumber = Integer.toString(this.countJobs() + 1);
        stmt.executeUpdate("INSERT INTO JOBS " +
                "VALUES ('" + jobname + "'," + jobNumber + ",'" + jobdesc + "', '" + jobskills + "')");
    }
}