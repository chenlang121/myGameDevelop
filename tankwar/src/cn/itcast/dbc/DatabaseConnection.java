package cn.itcast.dbc;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static String DBDRIVER;
    private static String DBURL;
    private static String USER;
    private static String PASSWORD;
    private Connection conn=null;
    static {
        Properties pro=new Properties();

        try {
            InputStream is =DatabaseConnection.class.getClassLoader().getResourceAsStream("jdbc.properties");

            pro.load(is);
            DBURL=pro.getProperty("url");
            DBDRIVER=pro.getProperty("driver");
            USER=pro.getProperty("user");
            PASSWORD=pro.getProperty("password");
            Class.forName(DBDRIVER);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        System.out.println(PASSWORD);
    }
    public DatabaseConnection(){

        try {

            this.conn= DriverManager.getConnection(DBURL,USER,PASSWORD);
            System.out.println(conn);
        } catch ( SQLException e) {
            e.printStackTrace();
        }

    }
    public Connection getConnection(){
        return this.conn;
    }
    public void close(){
        if(this.conn!=null) {
            try {
                this.conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}
