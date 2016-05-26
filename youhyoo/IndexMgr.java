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
					sql="select * from Pension where p_addr1 like '"+locationchecker+"%' order by p_num desc";
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
	// 3-1. 위시리스트 확인 (중복체크을 위해 확인이 필요)
	//--------------------
	public Boolean checkWishlist(String userId, int roomNumber){// throws Exception{
        Connection con=null;
  	    PreparedStatement pstmt=null;
  	    ResultSet rs=null;
  	    String sql = null; 
  	    Boolean wishChecker=false;
  	    
  	    try{
  			con=getConnection();
  			
	  		sql= "select count(*) total from wishlist where w_id=? and w_pnum=?";
	  		pstmt = con.prepareStatement(sql);
	  		pstmt.setString(1, userId);
	  		pstmt.setInt(2, roomNumber);
	        rs = pstmt.executeQuery();
	        if(rs.next()){  
	        	if(rs.getInt("total")==0){
	        		wishChecker=true;
	        	}
	        }//while end 
  			
  	    }catch(Exception ex){
  	    	System.out.println("checkWishlist() 예외 :"+ex);
  	    }finally{
  	    	try{
  	    		if(rs!=null){rs.close();}
  	    		if(pstmt!=null){pstmt.close();}
  	    		if(con!=null){con.close();}
  	    	}catch(Exception ex){}
  	    }//finally end
  	    return wishChecker;
	}//checkWishlist() end
	
	//--------------------	
	// 3-2. 위시리스트 작성
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
	// 7-1. 예약정보 리스트 얻기
	//--------------------
	public List<OrderRoom_Dto> getOrder(String u_id ,String sDate, String eDate){
		//select * from order_room where o_num=(select ou_num from order_user where ou_id='dj');
		
		//select * from order_room inner join order_user;
		//select ou_num,o_date,o_pname,o_state,ou_customer,ou_cell,ou_id from order_room inner join order_user where ou_id='dj' and o_date between '2016-05-24' and '2016-05-31' order by o_date;
		String sql="select distinct o_pname from order_room inner join"
				+" order_user where ou_id='"+u_id+"'"+" and ou_date between '"+sDate+"'"+
				" and '"+eDate+"' order by ou_date";
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
			/*	
			OrderRoom_Dto order=new OrderRoom_Dto();
			order.setO_num(rs.getInt("o_num"));
			order.setO_pnum(rs.getInt("o_pnum"));
			order.setO_pname(rs.getString("o_pname"));
			order.setO_rnum(rs.getInt("o_rnum"));
			order.setO_people(rs.getInt("o_people"));
			order.setO_rname(rs.getString("o_rname"));
			order.setO_date(rs.getDate("o_date"));
			order.setO_exprice(rs.getInt("o_exprice"));
			order.setO_price(rs.getInt("o_price"));
			order.setO_state(rs.getBoolean("o_state"));
			order.setO_group(rs.getInt("o_group"));
			*/
			OrderRoom_Dto order=new OrderRoom_Dto();
			
			order.setO_pname(rs.getString("o_pname"));
			
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
	
	//--------------------	
	// 7-2. 예약정보 리스트 얻기
	//--------------------
	public List<OrderUser_Dto> getUser(String u_id ,String sDate, String eDate){
		
		String sql="select ou_num,ou_customer,ou_cell,ou_date from"
				+" order_user where ou_id='"+u_id+"'"+" and ou_date between '"+sDate+"'"+
				" and '"+eDate+"' order by ou_date";
		/*
		String sql="select ou_num,ou_customer,ou_cell,ou_id,o_date,o_rname from order_room inner join"
				+" order_user where ou_id='"+u_id+"'"+" and o_date between '"+sDate+"'"+
				" and '"+eDate+"' order by o_date";
		*/		
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		List<OrderUser_Dto> ou_list=new ArrayList<OrderUser_Dto>();	

		try{
			//처리내용
			con=getConnection();//커넥션 얻기
			pstmt=con.prepareStatement(sql);//생성시 인자 넣는다
			rs = pstmt.executeQuery();

			while(rs.next()){
			
				OrderUser_Dto ou=new OrderUser_Dto();
				ou.setOu_num(rs.getInt("ou_num"));
				ou.setOu_customer(rs.getString("ou_customer"));
				ou.setOu_cell(rs.getString("ou_cell"));
				ou.setOu_date(rs.getDate("ou_date"));
				
				ou_list.add(ou);
			}

		}catch(Exception ex){
			System.out.println("getUser() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end

		return ou_list;
	}//getUser() end
	
	//--------------------	
	// 8-1. 예약 상세정보 얻기(객실) 
	//--------------------
	public List<OrderRoom_Dto> getOrderRoom(int ou_num){
		//select * from order_room where o_group=any(select ou_num from order_user where ou_num=1);
		String sql="select * from order_room where o_group=any(select ou_num from order_user where ou_num="+ou_num+")";
				
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
				order.setO_pnum(rs.getInt("o_pnum"));
				order.setO_pname(rs.getString("o_pname"));
				order.setO_rnum(rs.getInt("o_rnum"));
				order.setO_people(rs.getInt("o_people"));
				order.setO_rname(rs.getString("o_rname"));
				order.setO_date(rs.getDate("o_date"));
				order.setO_exprice(rs.getInt("o_exprice"));
				order.setO_price(rs.getInt("o_price"));
				order.setO_state(rs.getBoolean("o_state"));
				order.setO_group(rs.getInt("o_group"));
				
				o_list.add(order);
			}

		}catch(Exception ex){
			System.out.println("getOrderRoom() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end

		return o_list;
	}//getOrderRoom() end
	
	//--------------------	
	// 8-2. 예약 상세정보 얻기(유저) 
	//--------------------
	public OrderUser_Dto getOrderUser(int ou_num){
		//select * from order_user where ou_num=1;
		String sql="select * from order_user where ou_num="+ou_num;
				
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;	
		OrderUser_Dto ou=new OrderUser_Dto();
		try{
			//처리내용
			con=getConnection();//커넥션 얻기
			pstmt=con.prepareStatement(sql);//생성시 인자 넣는다
			rs = pstmt.executeQuery();

			while(rs.next()){
				
				ou.setOu_num(rs.getInt("ou_num"));
				ou.setOu_customer(rs.getString("ou_customer"));
				ou.setOu_birth(rs.getInt("ou_birth"));
				ou.setOu_emercall(rs.getString("ou_emercall"));
				ou.setOu_request(rs.getString("ou_request"));
				ou.setOu_id(rs.getString("ou_id"));
				ou.setOu_cell(rs.getString("ou_cell"));
				ou.setOu_paytype(rs.getInt("ou_paytype"));
				ou.setOu_date(rs.getDate("ou_date"));
			}

		}catch(Exception ex){
			System.out.println("getOrderUser() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end

		return ou;
	}//getOrderUser() end	
	
	//--------------------	
	// 9. 질문리스트 얻기
	//--------------------
	public List<Q_pension_Dto> getQList(String u_id){
		String sql="select * from Q_pension where qp_id="+"'"+u_id+"'";
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement pstmt=null;
		List<Q_pension_Dto> q_list=new ArrayList<Q_pension_Dto>();	

		try{
			//처리내용
			con=getConnection();//커넥션 얻기
			pstmt=con.prepareStatement(sql);//생성시 인자 넣는다
			rs = pstmt.executeQuery();

			while(rs.next()){
				Q_pension_Dto dto=new Q_pension_Dto();
				
				dto.setQp_num(rs.getInt("qp_num"));
				dto.setQp_state(rs.getBoolean("qp_state"));
				dto.setQp_title(rs.getString("qp_title"));
				dto.setQp_question(rs.getString("qp_question"));
				dto.setQp_id(rs.getString("qp_id"));
				dto.setQp_date(rs.getDate("qp_date"));
				dto.setQp_view(rs.getInt("qp_view"));
				dto.setQp_answer(rs.getString("qp_answer"));
				dto.setQp_pension(rs.getInt("qp_pension"));
				
				q_list.add(dto);//list에 넣기
			}

		}catch(Exception ex){
			System.out.println("getQList() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end

		return q_list;
	}//getQList() end
	
	//--------------------	
	// 10. 잔여객실(팬션) 얻기
	//--------------------
	/****
	select p_num,p_name,p_addr1,p_addr2 from pension where p_num=(select r_pension from room where r_pension=1 and r_num
	=(select o_rnum from order_room where o_date!='2016-05-30')) and p_addr1="경기도";
	****/ 
	public List<Pension_Dto> getDPList(String location){
		Connection con=null;
		ResultSet rs=null;
		Statement stmt=null;
		List <Pension_Dto> pList=new ArrayList<Pension_Dto>();
		
		try{
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();
			String sql="select p_num,p_name,p_addr1,p_addr2 from pension where p_addr1 like "
					+ "'"+location+"%' or p_addr2 like '"+location+"%'";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Pension_Dto p_dto=new Pension_Dto();
				
				//업소,  
				//addr1,addr2 
				p_dto.setP_num(rs.getInt("p_num"));
				p_dto.setP_name(rs.getString("p_name"));
				p_dto.setP_addr1(rs.getString("p_addr1"));
				p_dto.setP_addr2(rs.getString("p_addr2"));
				
				pList.add(p_dto);
			}//while
		}catch(Exception ex){
			System.out.println("getDPList() 예외"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return pList;
	}//getDPList()
	
	//--------------------	
	// 11. 잔여객실(객실) 얻기
	//--------------------
	/*
	select * from room where r_pension=1 and r_num Not In(select o_rnum from order_room where o_date='2016-05-27');
	
	select * from room where r_num Not In(select o_rnum from order_room where o_date='2016-05-20');
	
	select * from room inner join order_room where o_date<>'2016-05-27' and r_pension=1;
	
	select * from room inner join pension on p_num=r_pension where r_pension="+p_num  
	*/
	public List<Room_Dto> getDRList(int p_num, String o_date){
		Connection con=null;
		ResultSet rs=null;
		Statement stmt=null;
		
		List <Room_Dto>rList=new ArrayList<Room_Dto>();
		try{
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();
			String sql="select * from room where r_pension="+p_num+" and r_num Not In"
					+ "(select o_rnum from order_room where o_date="+"'"+o_date+"'"+")";
			rs=stmt.executeQuery(sql);

			while(rs.next()){
				Room_Dto r_dto=new Room_Dto();
				
			    //방정보
				r_dto.setR_name(rs.getString("r_name")); //객실이름
				r_dto.setR_size(rs.getInt("r_size")); //평수
				r_dto.setR_mincapa(rs.getInt("r_mincapa"));
				r_dto.setR_maxcapa(rs.getInt("r_maxcapa"));
				r_dto.setR_max_wd(rs.getInt("r_max_wd")); //성수기 주중가
				r_dto.setR_min_wd(rs.getInt("r_min_wd")); //비성수기 주중가
				r_dto.setR_max_we(rs.getInt("r_max_we")); //성수기 주말가
				r_dto.setR_min_we(rs.getInt("r_min_we")); //비성수기 주말가
				
				rList.add(r_dto);
			}//while
		}catch(Exception ex){
			System.out.println("getDRList() 예외"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return rList;
	}//getDRList()
	
	//select o_group,sum(o_price) from order_room where o_group=any(select ou_num from order_user where ou_id='dj') group by o_group;
	//--------------------	
	// 12. 적립금 내역 얻기(적립금)
	//--------------------
	public List<OrderRoom_Dto> getPoint(String u_id){
		Connection con=null;
		ResultSet rs=null;
		Statement stmt=null;
		
		List<OrderRoom_Dto> pList=new ArrayList<OrderRoom_Dto>();
		try{
			
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();
			String sql="select o_group,sum(o_price) from order_room where o_group="
					+ "any(select ou_num from order_user where ou_id='"+u_id+"') group by o_group";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				OrderRoom_Dto o=new OrderRoom_Dto();
				o.setO_group(rs.getInt("o_group"));
				o.setO_price(rs.getInt("sum(o_price)")/50);		
				pList.add(o);
			}//while
		}catch(Exception ex){
			System.out.println("getPoint() 예외"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return pList;
	}//getPoint
	
	//--------------------	
	// 13. 적립금 내역 얻기2(일자,예약번호)
	//--------------------
	public List<OrderUser_Dto> getPuser(String u_id){
		Connection con=null;
		ResultSet rs=null;
		Statement stmt=null;
		
		List<OrderUser_Dto> uList=new ArrayList<OrderUser_Dto>();
		try{
			
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();
			String sql="select ou_num,ou_date from order_user where ou_id='"+u_id+"' order by ou_num desc";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				OrderUser_Dto ou=new OrderUser_Dto();
				ou.setOu_num(rs.getInt("ou_num"));
				ou.setOu_date(rs.getDate("ou_date"));		
				uList.add(ou);
			}//while
		}catch(Exception ex){
			System.out.println("getPoint() 예외"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return uList;
	}//getPoint
}
