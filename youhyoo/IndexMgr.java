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
	public List<Pension_Dto> getIndexPensionList(String location) throws Exception{
		String sql="";//변수
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		List <Pension_Dto> pensionList=new ArrayList<Pension_Dto>();
			
		try{
		//처리내용 
			con=getConnection();//커넥션 얻기
			if(location.equals("index")){
				sql="select * from Pension order by p_num desc";
			}else{
				String locationchecker=location.substring(0, 3);
				location=location.substring(3, location.length());
				if(locationchecker.equals("hot")){
					sql="select * from Pension where p_addr2 like '"+location+"%' order by p_num desc";
				}else{
					sql="select * from Pension where p_addr1 like '"+location+"%' order by p_num desc";
				}
			}
			stmt=con.createStatement();//생성시 인자 안들어 감
			rs=stmt.executeQuery(sql);// 실행싱 인자 들어감 
			
			while(rs.next()){
				Pension_Dto pension=new Pension_Dto();
				
				pension.setP_num(rs.getInt("p_num"));
				pension.setP_name(rs.getString("p_name"));
				pension.setP_addr1(rs.getString("p_addr1"));
				pension.setP_addr2(rs.getString("p_addr2"));
				pension.setP_photo(rs.getString("p_photo"));
				
				pensionList.add(pension);//모델빈을 list에 넣는다 *******
			}//while end 
		
		}catch(Exception ex){
			System.out.println("getIndexPensionList() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
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
	  			sql= "select r_min_we,r_min_wd from room where r_pension=?";
	  			pstmt = con.prepareStatement(sql);
	            pstmt.setInt(1, pensionNumber[i]);
	            rs = pstmt.executeQuery();
	            if(rs.next()){
	            	Room_Dto room=new Room_Dto();
	            	
	            	room.setR_min_we(rs.getInt("r_min_we"));
	            	room.setR_min_wd(rs.getInt("r_min_wd"));
	
	              	roomList.add(room);
	            }//while end 
  			}
  	    }catch(Exception ex){
  	    	System.out.println("getIndexRoomList() 예외 :"+ex);
  	    }finally{
  	    	try{
  	    		if(rs!=null){rs.close();}
  	    		if(pstmt!=null){pstmt.close();}
  	    		if(con!=null){con.close();}
  	    	}catch(Exception ex){}
  	    }//finally end
  	    return roomList;
	}//getIndexRoomList() end
	
	//--------------------	
	// 3. 위시리스트 작성
	//--------------------
	public void setWishlist(String userId, int roomNumber){
		String sql="insert into wishlist values('"+userId+"',"+roomNumber+")";
		Connection con=null;
		PreparedStatement pstmt=null;
			
		try{
		//처리내용 
			con=getConnection();//커넥션 얻기
			pstmt=con.prepareStatement(sql);//생성시 인자 넣는다
			pstmt.executeUpdate();//쿼리수행 ###
					
		}catch(Exception ex){
			System.out.println("setWishlist() 예외 :"+ex);
		}finally{
			try{
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end
	}//end setWishlist()
	
	//--------------------	
	// 4. 지도 검색 리스트 - 위치
	//--------------------
	public List<Pension_Dto> getMapPensionList() throws Exception{
		String sql="";//변수
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		List <Pension_Dto> pensionList=new ArrayList<Pension_Dto>();
			
		try{
		//처리내용 
			con=getConnection();//커넥션 얻기
			sql="select p_num, p_name, p_lat, p_lng, p_photo, p_addr1, p_addr2, p_tel from Pension ";
			stmt=con.createStatement();//생성시 인자 안들어 감
			rs=stmt.executeQuery(sql);// 실행싱 인자 들어감 
			
			while(rs.next()){
				Pension_Dto pension=new Pension_Dto();
				
				pension.setP_num(rs.getInt("p_num"));
				pension.setP_name(rs.getString("p_name"));
				pension.setP_lat(rs.getDouble("p_lat"));
				pension.setP_lng(rs.getDouble("p_lng"));
				
				StringTokenizer pensionPhoto = new StringTokenizer(rs.getString("p_photo"),"|");
				
				pension.setP_photo(pensionPhoto.nextToken());
				pension.setP_addr1(rs.getString("p_addr1"));
				pension.setP_addr2(rs.getString("p_addr2"));
				pension.setP_tel(rs.getString("p_tel"));
				
				pensionList.add(pension);//모델빈을 list에 넣는다 *******
			}//while end 
		
		}catch(Exception ex){
			System.out.println("getMapPensionList() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end
		return pensionList;
	}//getMapPensionList() end
	
	//--------------------	
	// 5. 위시리스트 얻기
	//--------------------
	public List<Pension_Dto> getWishlist(String u_id){
		String sql="select p_num,p_name,p_photo from pension where p_num=any"
				+ "(select w_pnum from Wishlist where w_id="+"'"+u_id+"')";
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		List<Pension_Dto> w_list=new ArrayList<Pension_Dto>();	
			
		try{
		//처리내용
			con=getConnection();//커넥션 얻기
			pstmt=con.prepareStatement(sql);//생성시 인자 넣는다
			rs = pstmt.executeQuery();
			
			while(rs.next()){
			Pension_Dto wish=new Pension_Dto();
			wish.setP_num(rs.getInt("p_num"));
			wish.setP_name(rs.getString("p_name"));
			wish.setP_photo(rs.getString("p_photo"));
			
			w_list.add(wish);
			}
					
		}catch(Exception ex){
			System.out.println("getWishlist() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end
		
		return w_list;
	}//getWishlist() end
	
	//--------------------	
	// 6. 위시리스트 삭제
	//--------------------
	public void delWishlist(String w_id, int w_pnum){
		Connection con=null;
		Statement stmt=null;
		String sql="delete from Wishlist where w_id="+"'"+w_id+"'"+" and w_pnum="+"'"+w_pnum+"'";
		try{
			con=getConnection();
			stmt=con.createStatement();
			stmt.executeUpdate(sql);
			
		}catch(Exception ex){
			System.out.println("delWishlist() 예외 :"+ex);
		}finally{
			try{
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end
	}//delWishlist() end
	
	//--------------------	
	// 7. 예약정보 얻기
	//--------------------
	public List<OrderRoom_Dto> getOrder(String u_id){
		String sql="select p_num,p_name,p_photo from pension where p_num=any"
				+ "(select w_pnum from Wishlist where w_id="+"'"+u_id+"')";
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		List<OrderRoom_Dto> o_list=new ArrayList<OrderRoom_Dto>();	
			
		try{
		//처리내용
			con=getConnection();//커넥션 얻기
			pstmt=con.prepareStatement(sql);//생성시 인자 넣는다
			rs = pstmt.executeQuery();
			
			while(rs.next()){
			OrderRoom_Dto order=new OrderRoom_Dto();
			order.setO_num(rs.getInt("o_num"));
			
			
			o_list.add(order);
			}
					
		}catch(Exception ex){
			System.out.println("getOrder() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end
		
		return o_list;
	}//getOrder() end

}
