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
			pstmt=con.prepareStatement("insert into user values(?,?,?,?,?,?,?,?,?,?)");
			
			pstmt.setString(1, dto.getU_id());
			pstmt.setString(2, dto.getU_name());
			pstmt.setString(3, dto.getU_pwd());
			pstmt.setString(4, dto.getU_type());
			//String c=dto.getU_cell();
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
	public Vector<Zipcode_Dto> zipcodeRead(String z_addr3){
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
				System.out.println("zipcodeRead() 오류:"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}				
			}catch(Exception exx){}
		}
		return vec;
	}//zipcodeRead()
	
	//로그인 
	public int userCheck(String u_id,String u_pwd) throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String dbPwd="";
		int x=-1;
		
		try{
			con=getConnection(); //커넥션 얻기 
			pstmt=con.prepareStatement("select * from user where u_id=?");
			pstmt.setString(1, u_id);
			rs=pstmt.executeQuery(); //쿼리 수행 
			
			if(rs.next()){
				dbPwd=rs.getString("u_pwd");
				if(u_pwd.equals(dbPwd)){
					x=1; //인증 성공
				}else{
					x=0; //암호 틀림 
				}
			}else{
				x=-1; //해당 아이디 없음 
			}//else 
		}catch(Exception ex){
			System.out.println("userCheck() 오류:"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return x;
	}//userCheck()
	
	//회원정보 얻기
		public User_Dto getUser(String u_id){
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			User_Dto dto=null;
			try{
				con=getConnection(); //커넥션 얻는다 
				String sql="select * from user where u_id="+"'"+u_id+"'";
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery(); //쿼리 수행 
				
				while(rs.next()){
					dto=new User_Dto();
					
					dto.setU_name(rs.getString("u_name"));
					dto.setU_pwd(rs.getString("u_pwd"));
					dto.setU_cell(rs.getString("u_cell"));
					dto.setU_addr(rs.getString("u_addr"));
					dto.setU_zipcode(rs.getString("u_zipcode"));
					dto.setU_birth(rs.getInt("u_birth"));
					dto.setU_email(rs.getString("u_email"));
					dto.setU_point(rs.getInt("u_point"));
				}//while			
			}catch(Exception ex){
					System.out.println("getUser 오류:"+ex);
			}finally{
				try{
					if(rs!=null){rs.close();}
					if(pstmt!=null){pstmt.close();}
					if(con!=null){con.close();}				
				}catch(Exception exx){}
			}
			return dto;
		}//getUser() end
		
		//회원정보 수정
		public void updateUser(User_Dto dto) throws Exception{
			Connection con=null;
			PreparedStatement pstmt=null;

			try{
				con=getConnection();//커넥션 얻기
				String sql="update user set u_name=?,u_pwd=?,u_cell=?,u_addr=?,u_zipcode=?,u_birth=?,u_email=? where u_id=?";

				pstmt=con.prepareStatement(sql);//생성시 인자 들어감

				pstmt.setString(1, dto.getU_name());
				pstmt.setString(2, dto.getU_pwd());
				pstmt.setString(3, dto.getU_cell());
				pstmt.setString(4, dto.getU_addr());
				pstmt.setString(5, dto.getU_zipcode());
				pstmt.setInt(6, dto.getU_birth());
				pstmt.setString(7, dto.getU_email());
				pstmt.setString(8, dto.getU_id());

				pstmt.executeUpdate(); //쿼리 수행
			}
			catch(Exception ex){
				System.out.println("updateUser() 예외 :"+ex);
			}
			finally{
				try{
					if(pstmt!=null){pstmt.close();}
					if(con!=null){con.close();}
				}
				catch(Exception exx){}
			}//finally
		}//updateUser() end
		
		//회원 탈퇴
		public int deleteUser(String u_id, String u_pwd) throws Exception{
			Connection con=null;
			PreparedStatement pstmt=null;
			PreparedStatement pstmt2=null;
			ResultSet rs=null;
			String dbPwd="";
			int x=-1;

			try{
				con=getConnection();//커넥션 얻기
				pstmt=con.prepareStatement("select u_pwd from user where u_id=?");
				pstmt.setString(1, u_id);
				rs=pstmt.executeQuery();

				if(rs.next()){
					dbPwd=rs.getString("u_pwd");
					if(dbPwd.equals(u_pwd)){
						pstmt2=con.prepareStatement("delete from user where u_id=?");
						pstmt2.setString(1, u_id);
						pstmt2.executeUpdate();//쿼리 실행
						x=1;//회원 탈퇴
					}
					else{
						x=-1;//비밀 번호 틀림
					}
				}//if
				else{
					x=0;//존제하지 않을때
				}
			}
			catch(Exception ex){
				System.out.println("deleteUser() 예외 :"+ex);
			}
			finally{
				try{
					if(rs!=null){rs.close();}
					if(pstmt2!=null){pstmt2.close();}
					if(pstmt!=null){pstmt.close();}
					if(con!=null){con.close();}
				}
				catch(Exception exx){}
			}//finally
			return x;
		}//deleteUser() end
}//class
