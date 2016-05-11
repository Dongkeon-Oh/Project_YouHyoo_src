package youhyoo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.*;
import javax.naming.*;

public class You {
	
	private static final String DRIVER="com.mysql.jdbc.Driver";
	private static final String URL="jdbc:mysql://localhost:3306/youhyoo";
	private static final String USER="root";
	private static final String PWD="12345";
	
	//생성자 : 초기화 작업
	public You(){
		try{
			Class.forName(DRIVER); //드라이버 로딩
			System.out.println("드라이버 로딩 성공");
		}catch(ClassNotFoundException ex1){
			System.out.println("드라이버 로딩 실패:"+ex1);
		}
	}//생성자 end
	
	public List getList(String sql){
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		List <Pension_Dto>list=null;
		list=new ArrayList<Pension_Dto>();
		
		try{
			con=DriverManager.getConnection(URL, USER, PWD);
			stmt=con.createStatement();
			rs=stmt.executeQuery(sql);
			
			while(rs.next()){
				Pension_Dto p_dto=new Pension_Dto(); 
				//업소,       빈객실,구조,인원,추가금액,요금
				//addr1,addr2 
				
				//업소
				p_dto.setP_name(rs.getString("p_name"));
				//p_dto.setP_(rs.getString("p_name"));
				
				//p_dto.setP_addr1(rs.getString("p_addr1"));
				//p_dto.setP_addr2(rs.getString("p_addr2"));
				//빈객실
				
				
				
				//구조
				
				//인원
				
				//추가요금
				
				//요금
				list.add(p_dto);
			}//while
		}catch(Exception ex){
			System.out.println("getList() 오류"+ex);
		}finally{
			try{
			if(rs!=null){rs.close();}
			if(stmt!=null){stmt.close();}
			if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return list;
	}//getList()
}//class

