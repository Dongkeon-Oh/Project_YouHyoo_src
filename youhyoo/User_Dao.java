package youhyoo;
import java.sql.*;
import java.util.*;

import javax.sql.*; //DataSource
import javax.naming.*; //lookup

public class User_Dao {
	private static User_Dao instance=new User_Dao(); //��ü ���� 
	
	public static User_Dao getInstance(){
		return instance;
	}//getInstance()
	
	private User_Dao(){} //����Ʈ ������ 
	
	private Connection getConnection() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}//getConnection()
	
	//ȸ������ 
	public void insertMember(User_Dto dto) throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try{
			con=getConnection(); //Ŀ�ؼ� ��´� 
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
			
			pstmt.executeUpdate(); //���� ���� 
		}catch(Exception ex){
			System.out.println("insertMember() ����:"+ex);
		}finally{
			try{
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}
	}//insertMember() 
	
	//id�ߺ� üũ 
	public int confirmId(String u_id) throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;	
		int x=-1;
		try{
			con=getConnection(); //Ŀ�ؼ� ��� 
			pstmt=con.prepareStatement("select u_id from user where u_id=?");
			pstmt.setString(1, u_id);
			rs=pstmt.executeQuery(); //���� ����
			
			if(rs.next()){ //����� 
				x=1;
			}else{ //��밡��
				x=-1;
			}
		}catch(Exception ex){
			System.out.println("confirmId() ����:"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return x;
	}//confirmId()
	
	//�����ȣ �˻�, �ּ� �ڵ��Է� 
	public Vector<Zipcode_Dto> zipcode(String z_addr3){
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Vector <Zipcode_Dto>vec=new Vector<Zipcode_Dto>();
		
		try{
			con=getConnection(); //Ŀ�ؼ� ��´� 
			String sql="select * from zipcode where z_addr3 like '%"+z_addr3+"%'";
			pstmt=con.prepareStatement(sql);
			rs=pstmt.executeQuery(); //���� ���� 
			
			while(rs.next()){
				Zipcode_Dto dto=new Zipcode_Dto();
				dto.setZ_zipcode(rs.getString("z_zipcode"));
				dto.setZ_addr1(rs.getString("z_addr1"));
				dto.setZ_addr2(rs.getString("z_addr2"));
				dto.setZ_addr3(rs.getString("z_addr3"));
				dto.setZ_addr4(rs.getString("z_addr4"));
				
				vec.add(dto); //���Ϳ� �ֱ� 
			}//while			
		}catch(Exception ex){
				System.out.println("zipcode() ����:"+ex);
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
