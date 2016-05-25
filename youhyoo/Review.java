package youhyoo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
			
			sql="insert into Review(rv_num,rv_score,rv_content,rv_id,rv_date,rv_view,rv_photo,rv_pension,rv_title) values(0,?,?,?,NOW(),0,?,?,?)";

			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, Integer.parseInt(multi.getParameter("rv_score")));
			pstmt.setString(2, multi.getParameter("rv_content"));
			pstmt.setString(3, multi.getParameter("rv_id"));
			
			if(multi.getFilesystemName("rv_photo")!=null){
				pstmt.setString(4, multi.getFilesystemName("rv_photo"));
			}else{
				pstmt.setString(4, "ready");
			}//else

			pstmt.setInt(5, Integer.parseInt(multi.getParameter("rv_pension")));
			pstmt.setString(6, multi.getParameter("rv_title"));
			
			pstmt.executeUpdate();
		
		}catch(Exception ex){
			System.out.println("Insert_Review() 오류"+ex);
		}finally{
			try{
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally
	}//Insert_Review()
	
	public List<Review_Dto> List_Review(int pension_num){
		List<Review_Dto> list=new ArrayList<Review_Dto>();
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			
			sql="select * from review where rv_pension="+pension_num;
			
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Review_Dto dto=new Review_Dto();
				
				dto.setRv_num(rs.getInt("rv_num"));
				dto.setRv_title(rs.getString("rv_title")); //타이틀로 대체함
				dto.setRv_date(rs.getDate("rv_date"));
				dto.setRv_id(rs.getString("rv_id"));
				dto.setRv_pension(rs.getInt("rv_pension"));
				dto.setRv_photo(rs.getString("rv_photo"));
				dto.setRv_content(rs.getString("rv_content"));
				dto.setRv_score(rs.getInt("rv_score"));
				dto.setRv_view(rs.getInt("rv_view"));
				
				list.add(dto);
			}
		}catch(Exception ex){
			System.out.println("List_Review() 오류"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally
		return list;
	}//List_Review()
	
	public void View_Review(String num){
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			sql="update review set rv_view=rv_view+1 where rv_num="+num;

			stmt.executeUpdate(sql);
			
		
		}catch(Exception ex){
			System.out.println("View_Review() 오류"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally

	}//View_Review()
}//class
