package youhyoo;
import java.sql.*;
import java.util.*;

import javax.sql.*; //DataSource
import javax.naming.*; //lookup

public class User_Dao {
	private static User_Dao instance=new User_Dao(); //객체 생성 
	
	public static User_Dao getInstance(){
		return instance;
	}//getInstance()
	
	private User_Dao(){} //디폴트 생성자 
	
	private Connection getConnection() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}//getConnection()
	
	//회원가입 
	public void insertMember(User_Dto dto) throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try{
			con=getConnection(); //커넥션 얻는다 
			pstmt=con.prepareStatement("insert into youhyoo values(?,?,?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1, dto.getU_id());
			pstmt.setString(2, dto.getU_name());
			pstmt.setString(3, dto.getU_pwd());
			pstmt.setString(4, dto.getU_type());
			pstmt.setString(5, dto.getU_cell());
			pstmt.setString(6, dto.getU_addr());
			pstmt.setString(7, dto.getU_zipcode());
			pstmt.setInt(8, dto.getU_birth());
			pstmt.setString(9, dto.getU_email());
			pstmt.setInt(10, dto.getU_point());
			
			pstmt.executeUpdate(); //쿼리 수행 
		}catch(Exception ex){
			System.out.println("insertMember() 예외:"+ex);
		}finally{
			try{
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}
	}//insertMember() 
	
	//id중복 체크 
	public int confirmId(String u_id) throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;	
		int x=-1;
		try{
			con=getConnection(); //커넥션 얻기 
			pstmt=con.prepareStatement("select u_id from user where u_id=?");
			pstmt.setString(1, u_id);
			rs=pstmt.executeQuery(); //쿼리 수행
			
			if(rs.next()){ //사용중 
				x=1;
			}else{ //사용가능
				x=-1;
			}
		}catch(Exception ex){
			System.out.println("confirmId() 예외:"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return x;
	}//confirmId()
	
	//우편번호 검색, 주소 자동입력 
	public Vector<Zipcode_Dto> zipcode(String z_addr3){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Vector <Zipcode_Dto>vec=new Vector<Zipcode_Dto>();
		
		try{
			con=getConnection(); //커넥션 얻는다 
			String sql="select * from zipcode where z_addr3 like '%"+z_addr3+"%'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery(); //쿼리 수행 
			
			while(rs.next()){
				Zipcode_Dto dto=new Zipcode_Dto();
				dto.setZ_zipcode(rs.getString("z_zipcode"));
				dto.setZ_addr1(rs.getString("z_addr1"));
				dto.setZ_addr2(rs.getString("z_addr2"));
				dto.setZ_addr3(rs.getString("z_addr3"));
				dto.setZ_addr4(rs.getString("z_addr4"));
				
				vec.add(dto); //벡터에 넣기 
			}//while			
		}catch(Exception ex){
				System.out.println("zipcode() 오류:"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}				
			}catch(Exception exx){}
		}
		return vec;
	}//zipcodeRead()
	
	
}//class
