package youhyoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class Review {
	private Connection getConnection() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}//getConnection end

	Connection con=null;
	Statement stmt=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	String sql="";
	
	public void Insert_Review(HttpServletRequest req){
		try{
			con=getConnection();
			
			String save=req.getServletContext().getRealPath("//imgs/");
			MultipartRequest multi=new MultipartRequest(req, save, 5*1024*1024, "utf-8",new DefaultFileRenamePolicy());
			
			sql="insert into Review(rv_num,rv_score,rv_question,rv_id,rv_date,rv_view,rv_photo,rv_pension) values(0,?,?,?,NOW(),0,?,?)";

			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(multi.getParameter("rv_score")));
			pstmt.setString(2, multi.getParameter("rv_question"));
			pstmt.setString(3, multi.getParameter("rv_id"));
			
			if(multi.getFilesystemName("rv_photo")!=null){
				pstmt.setString(4, multi.getFilesystemName("rv_photo"));
			}else{
				pstmt.setString(4, "ready");
			}//else

			pstmt.setInt(5, Integer.parseInt(multi.getParameter("rv_pension")));
			
			
			pstmt.executeUpdate();
		
		}catch(Exception ex){
			System.out.println("Insert_Review() ¿À·ù"+ex);
		}finally{
			try{
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally
	}//Insert_Review()
}//class
