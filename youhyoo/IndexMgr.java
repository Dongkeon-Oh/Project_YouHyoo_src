package youhyoo;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class IndexMgr {
	
	private IndexMgr(){}
	private static IndexMgr instance=new IndexMgr();
	public static IndexMgr getInstance(){
		return instance;
	}
	
	private Connection getConnection() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}
	
	//--------------------	
	// 1. 펜션 목록 리스트
	//--------------------
	public List<Pension_Dto> getIndexPensionList() throws Exception{
		String sql="";//변수
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		List <Pension_Dto> pensionList=new ArrayList<Pension_Dto>();
			
		try{
		//처리내용 
			con=getConnection();//커넥션 얻기
			sql="select * from Pension order by p_num desc";
			
			stmt=con.createStatement();//생성시 인자 안들어 감
			rs=stmt.executeQuery(sql);// 실행싱 인자 들어감 
			
			while(rs.next()){
				Pension_Dto pension=new Pension_Dto();
				
				pension.setP_num(rs.getInt("p_num"));
				pension.setP_name(rs.getString("p_name"));
				pension.setP_addr1(rs.getString("p_addr1"));
				pension.setP_addr2(rs.getString("p_addr2"));
				pension.setPhoto(rs.getString("p_photo"));
				
				pensionList.add(pension);//모델빈을 list에 넣는다 *******
			}//while end 
		
		}catch(Exception ex){
			System.out.println("getIndexPensionList() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally end
		return pensionList;
	}//getIndexPensionList() end
	
	//--------------------	
	// 2. 객실 가격 리스트
	//--------------------
	public List<Room_Dto> getIndexRoomList(int pensionNumber[]) throws Exception{
        Connection con=null;
  	    PreparedStatement pstmt=null;
  	    ResultSet rs=null;
  	    String sql = null; 
  	    List<Room_Dto> roomList=new ArrayList<Room_Dto>();
  	    
  	    try{
  	    	//처리문 
  			con=getConnection();
  			
  			for(int i=0; i<pensionNumber.length; i++){
	  			sql= "select r_minpri_we,r_minpri_wd from room where r_pension=?";
	  			pstmt = con.prepareStatement(sql);
	            pstmt.setInt(1, pensionNumber[i]);
	            rs = pstmt.executeQuery();//###
	            if(rs.next()){
	            	Room_Dto room=new Room_Dto();
	            	
	            	room.setR_minpri_we(rs.getInt("r_minpri_we"));
	            	room.setR_minpri_wd(rs.getInt("r_minpri_wd"));
	
	              	roomList.add(room);
	            }//while end 
  			}
  	    }catch(Exception ex){
  	    	System.out.println("getProduct() 예외 :"+ex);
  	    }finally{
  	    	try{
  	    		if(rs!=null){rs.close();}
  	    		if(pstmt!=null){pstmt.close();}
  	    		if(con!=null){con.close();}
  	    	}catch(Exception exx){}
  	    }//finally end
  	    return roomList;
	}//getIndexRoomList() end
}
