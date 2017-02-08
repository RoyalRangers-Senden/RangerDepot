package rangerdepot;

import java.sql.*;


public class DatabaseInterface
{
    private RangerDepot depot;
    
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL_PREFIX = "jdbc:mysql://";
    public static final String URL_SUFFIX_PORT = ":3306/royalrangers";
    public static final String URL_SUFFIX = "/royalrangers";
    
    Connection conn = null;
    Connection testConn = null;
    Statement stmt = null;
    
    private String url = null;
    private String userName = null;
   
    public DatabaseInterface(RangerDepot depot)
    {
        this.depot = depot;
        
        System.out.print("Loading Driver...-> ");
        try
        {
            Class.forName(JDBC_DRIVER);
            System.out.println("Driver loaded");
        }catch(Exception e){System.err.println("Error: Driver not found.");e.printStackTrace();System.exit(1);}
    }
    public void exit()
    {
        try
        {
            stmt.close();
            conn.close();
            testConn.close();
        }catch(Exception e){}
    }
    
    
    
    
    public int connect(String url,String user,String password)
    {
        if(url.contains(":"))
            url = URL_PREFIX+url+URL_SUFFIX;
        else
            url = URL_PREFIX+url+URL_SUFFIX_PORT;
        
        try
        {
            conn = DriverManager.getConnection(url,user,password);
            stmt = conn.createStatement();
            System.out.println("Connection established.");
            
            this.url = url;
            userName = user;
        }catch(SQLException e)
        {
            depot.showError(Strings.CONNECTION_ERROR+" Errorcode:"+e.getErrorCode());e.printStackTrace();
            System.err.println(Strings.CONNECTION_ERROR+" Errorcode:"+e.getErrorCode());e.printStackTrace();
            return e.getErrorCode();
        }
        return -1;
    }
    public boolean testPassword(String pw)
    {
        boolean tempB = false;
        try
        {
            testConn = DriverManager.getConnection(url,userName,pw);
            tempB = true;
            testConn.close();
        }catch(SQLException e){}
        
        return tempB;
    }
    
    
    public ResultSet read(String s)
    {
        try
        {
            return stmt.executeQuery(s);
        }catch(SQLException e)
        {
            System.err.println("Error: while reading from Database! Errorcode:"+e.getErrorCode());
            e.printStackTrace();
            return null;
        }
    }
    public int execute (String s)
    {
        int tempInt = -1;
        try
        {
            stmt.executeUpdate(s);
        }catch(SQLException e)
        {
            System.err.println("Error: while executing sql command! Errorcode:"+e.getErrorCode());
            e.printStackTrace();
            tempInt = e.getErrorCode();
        }
        return tempInt;
    }
}
