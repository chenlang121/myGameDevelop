package test.cn.itcast.dbc; 

import cn.itcast.dbc.DatabaseConnection;
import org.testng.annotations.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/** 
* DatabaseConnection Tester. 
* 
* @author <Authors name> 
* @since <pre>1ÔÂ 10, 2021</pre> 
* @version 1.0 
*/ 
public class DatabaseConnectionTest {
    private Connection conn=null;
    private static String DBDRIVER;
    private static String DBURL;
    private static String USER;
    private static String PASSWORD;

@Before
public void before() throws Exception {


} 

@After
public void after() throws Exception {

} 

/** 
* 
* Method: main(String[] args) 
* 
*/ 
@Test
public void testMain() throws Exception { 
//TODO: Test goes here...
    try {
        InputStream is =DatabaseConnection.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pro=new Properties();

        pro.load(is);
        DBURL=pro.getProperty("url");
        DBDRIVER=pro.getProperty("driver");
        USER=pro.getProperty("user");
        PASSWORD=pro.getProperty("password");
        Class.forName(DBDRIVER);
    } catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
    }
    System.out.println(DBURL);
    System.out.println(DBDRIVER);
} 

/** 
* 
* Method: getConnection() 
* 
*/ 
@Test
public void testGetConnection() throws Exception {
//TODO: Test goes here...

    System.out.println(123434);
} 

/** 
* 
* Method: close() 
* 
*/ 
@Test
public void testClose() throws Exception { 
//TODO: Test goes here... 
}


} 
