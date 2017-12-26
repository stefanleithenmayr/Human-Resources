package loginPackage;

import java.sql.*;

public class DBConnection {
    private static DBConnection ourInstance = new DBConnection();
    public static DBConnection getInstance() {
        return ourInstance;
    }
    public static final String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/db";
    public static String userName;
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
    public void UpdateSkills(String skills) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql = "UPDATE users " + "SET job_skills =  '"+skills+"' WHERE username like '"+userName+"'";
        stmt.executeUpdate(sql);
    }
    public void UpdateUserName(String userName1) throws SQLException{
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("UPDATE users set USERNAME = '" + userName1 +"'where username = '"+ userName+"'");
        userName = userName1;
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