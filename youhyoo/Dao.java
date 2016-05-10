package youhyoo;
import java.sql.*;

import javax.sql.*;
import javax.naming.*;

import youhyoo.Dao;

import java.util.*;

public class Dao {
	
	private Dao(){}
	
	private static Dao instance=new Dao();
	
	public static Dao getInstance(){
		return instance;
	}
	
	private Connection getConnection() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}
	
	public void insertArticle(Mgr mgr) throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="";
	}
}
