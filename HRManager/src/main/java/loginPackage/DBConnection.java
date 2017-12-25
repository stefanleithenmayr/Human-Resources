package loginPackage;

import java.sql.*;

public class DBConnection {
    private static DBConnection ourInstance = new DBConnection();

    public static DBConnection getInstance() {
        return ourInstance;
    }

    public static final String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/db;create=true";

    private Connection conn;

    private DBConnection() {

    }

    public boolean login(String userName, String password) throws ClassNotFoundException {
        try {
            Class.forName(DRIVER_STRING);
            conn = DriverManager.getConnection(CONNECTION_STRING, userName, password);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public ResultSet getJobs() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM JOBS");
    }

    public String getJobDesc(String job) throws SQLException {

        String jobDesc = "";
        ResultSet rs = getInstance().getJobs();
        String jobNumber = this.getJobNumber(job);

        try {
            while (rs.next()) {
                try {
                    if (rs.getString("JOB_ID").equals(jobNumber)){
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
                    if (rs.getString("JOB_ID").equals(jobNumber)){
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
            }else found = false;
        }
        return number;
    }
}