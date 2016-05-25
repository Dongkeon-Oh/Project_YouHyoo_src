package youhyoo;

import java.sql.*;
import java.sql.Date;

import javax.sql.*;
import javax.naming.*;

import java.util.*;

public class DetailMgr {
	
	private DetailMgr(){}
	
	private static DetailMgr instance=new DetailMgr();
	public static DetailMgr getInstance(){
		return instance;
	}
	
	private Connection getConnection() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}
	
	//펜션정보 얻기----------------------------------------------------------
	public Pension_Dto getPension(int p_num){
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		Pension_Dto pension=new Pension_Dto();
		
		try{
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();//생성시 인자 안들어 감
			sql="select * from pension where p_num="+p_num;
			rs=stmt.executeQuery(sql);//실행시 인자 들어감

			while(rs.next()){
				pension.setP_num(rs.getInt("p_num"));
				pension.setP_name(rs.getString("p_name"));
				pension.setP_name(rs.getString("p_name"));
				pension.setP_addr1(rs.getString("p_addr1"));
				pension.setP_addr2(rs.getString("p_addr2"));
				pension.setP_addr3(rs.getString("p_addr3"));
				pension.setP_tel(rs.getString("p_tel"));
				pension.setP_paytype(rs.getInt("p_paytype"));
				pension.setP_account(rs.getString("p_account"));
				pension.setP_intro(rs.getString("p_intro"));
				pension.setP_photo(rs.getString("p_photo"));
				pension.setP_contect(rs.getString("p_contect"));
				pension.setP_lat(rs.getDouble("p_lat"));
				pension.setP_lng(rs.getDouble("p_lng"));
			}
		}
		catch(Exception ex){
			System.out.println("getPension() 예외 :"+ex);
		}
		finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}
			catch(Exception exx){}
		}
		return pension;
	}//getDetails() end

	//객실 정보 얻기------------------------------------------------------
	public List<Room_Dto> getRoom(int p_num){// throws Exception{
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		
		List<Room_Dto> roomList=new ArrayList<Room_Dto>();

		try{
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();//생성시 인자 안들어 감
			sql="select * from room where r_pension="+p_num;
			rs=stmt.executeQuery(sql);//실행시 인자 들어감

			while(rs.next()){
				Room_Dto room=new Room_Dto();
				
				room.setR_num(rs.getInt("r_num"));
				room.setR_name(rs.getString("r_name"));
				room.setR_max_wd(rs.getInt("r_max_wd"));
				room.setR_max_we(rs.getInt("r_max_we"));
				room.setR_min_wd(rs.getInt("r_min_wd"));
				room.setR_min_we(rs.getInt("r_min_we"));
				room.setR_maxcapa(rs.getInt("r_maxcapa"));
				room.setR_mincapa(rs.getInt("r_mincapa"));
				room.setR_size(rs.getInt("r_size"));
				room.setR_photo(rs.getString("r_photo"));
				room.setR_pension(rs.getInt("r_pension"));
				
				roomList.add(room);
			}
		}//try			
		catch(Exception ex){
			System.out.println("getRoom() 예외 :"+ex);
		}
		finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}
			catch(Exception exx){}
		}
		return roomList;
	}//getRoom() end
	
	//객실 정보 얻기 minimal------------------------------------------------------
	public List<Room_Dto> getRoomMin(int p_num){// throws Exception{
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		
		List<Room_Dto> roomList=new ArrayList<Room_Dto>();

		try{
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();//생성시 인자 안들어 감
			sql="select * from room where r_pension="+p_num;
			rs=stmt.executeQuery(sql);//실행시 인자 들어감

			while(rs.next()){
				Room_Dto room=new Room_Dto();
				
				room.setR_name(rs.getString("r_name"));
				room.setR_num(rs.getInt("r_num"));
				room.setR_max_wd(rs.getInt("r_max_wd"));
				room.setR_max_we(rs.getInt("r_max_we"));
				room.setR_min_wd(rs.getInt("r_min_wd"));
				room.setR_min_we(rs.getInt("r_min_we"));
				room.setR_maxcapa(rs.getInt("r_maxcapa"));
				room.setR_mincapa(rs.getInt("r_mincapa"));
				
				roomList.add(room);
			}
		}catch(Exception ex){
			System.out.println("getRoomMin() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}
		return roomList;
	}//getRoomMin() end
	
	public List<String> getRoomForOrder(int r_num, int p_num){// throws Exception{
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		
		List<String> names=new ArrayList<String>();

		try{
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();//생성시 인자 안들어 감
			sql="select r_name,(select p_name from pension where p_num="+p_num+") as p_name from room where r_num="+r_num;
			rs=stmt.executeQuery(sql);//실행시 인자 들어감

			if(rs.next()){
				names.add(rs.getString("r_name"));
				names.add(rs.getString("p_name"));
			}
		}catch(Exception ex){
			System.out.println("getRoomForOrder() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}
		return names;
	}//getRoomForOrder() end

	public void insertOrderRoom(OrderRoom_Dto dto){// throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="";
		
		try{
			con=getConnection();
			sql="insert into order_room values(0,?,?,?,?,?,?,?,?,?,?)";
			pstmt=con.prepareStatement(sql);
			
		//	pstmt.setInt(1, 0);
			pstmt.setInt(1, dto.getO_pnum());
			pstmt.setString(2, dto.getO_pname());
			pstmt.setInt(3, dto.getO_rnum());
			pstmt.setString(4, dto.getO_rname());
			pstmt.setInt(5, dto.getO_people());
			pstmt.setDate(6, (Date) dto.getO_date());
			pstmt.setInt(7, dto.getO_exprice());
			pstmt.setInt(8, dto.getO_price());
			pstmt.setBoolean(9, dto.getO_state());
			pstmt.setInt(10, dto.getO_group());
			
			pstmt.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("insertOrderRoom() 예외 : "+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end
	}//insertOrderRoom end
		
	// 최신 주문 번호 알아보기
	public int maxOrderNum(){// throws Exception{
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		int maxNum=-1;
		
		try{
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();//생성시 인자 안들어 감
			rs=stmt.executeQuery("select max(o_group) from order_room");//실행시 인자 들어감

			if(rs.next()){
				maxNum=rs.getInt("max(o_group)");
				maxNum+=1;
			}
		}catch(Exception ex){
			System.out.println("maxOrderNum() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}
		return maxNum;
	}//maxOrderNum() end
	
	
	public String orderUserCell(String id){// throws Exception{
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		String cell="ERRER";
				
		try{
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();//생성시 인자 안들어 감
			rs=stmt.executeQuery("select u_cell from user where u_id='"+id+"'");//실행시 인자 들어감

			if(rs.next()){
				cell=rs.getString("u_cell");
			}
		}catch(Exception ex){
			System.out.println("orderUserCell() 예외 :"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}
		return cell;
	}//maxOrderNum() end
	
	public void insertOrderUser(OrderUser_Dto dto){// throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="";
		
		try{
			con=getConnection();
			sql="insert into order_user values(0,?,?,?,?,?,?,?,?,now())";		
			pstmt=con.prepareStatement(sql);
			
		//	pstmt.setInt(1, 0);
			pstmt.setString(1, dto.getOu_customer());
			pstmt.setInt(2, dto.getOu_birth());
			pstmt.setString(3, dto.getOu_emercall());
			pstmt.setString(4, dto.getOu_request());
			pstmt.setString(5, dto.getOu_id());
			pstmt.setString(6, dto.getOu_cell());
			pstmt.setInt(7, dto.getOu_paytype());
			pstmt.setInt(8, dto.getOu_group());
			
			pstmt.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("insertOrderUser() 예외 : "+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception ex){}
		}//finally end
	}//insertOrderUser end
	
	//객실 주중최저가 얻기
	public int getMin_wd(int p_num){
		int min=0;
		
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		
		try{
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();//생성시 인자 안들어 감
			sql="select min(r_min_wd) from room where r_pension='"+p_num+"'";
			rs=stmt.executeQuery(sql);//실행시 인자 들어감

			while(rs.next()){
				min=rs.getInt("min(r_min_wd)");
			}
		}
		catch(Exception ex){
			System.out.println("getMin_wd() 예외 :"+ex);
		}
		finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}
			catch(Exception exx){}
		}		
		return min;
	}//getMin_wd
	
	//객실 주말최저가 얻기
		public int getMin_we(int p_num){
			int max=0;
			
			Connection con=null;
			Statement stmt=null;
			ResultSet rs=null;
			String sql="";
			
			try{
				con=getConnection();//커넥션 얻기
				stmt=con.createStatement();//생성시 인자 안들어 감
				sql="select min(r_min_we) from room where r_pension='"+p_num+"'";
				rs=stmt.executeQuery(sql);//실행시 인자 들어감

				while(rs.next()){
					max=rs.getInt("min(r_min_we)");
				}
			}
			catch(Exception ex){
				System.out.println("getMin_wd() 예외 :"+ex);
			}
			finally{
				try{
					if(rs!=null){rs.close();}
					if(stmt!=null){stmt.close();}
					if(con!=null){con.close();}
				}
				catch(Exception exx){}
			}		
			return max;
		}//getMin_wd
		
		//객실 주말최저가 얻기
	public List<OrderRoom_Dto> getOrder(int o_pension){
		Connection con=null;
		Statement stmt=null;
		ResultSet rs=null;
		String sql="";
		List<OrderRoom_Dto> orderList=new ArrayList<OrderRoom_Dto>();
		
		try{
			con=getConnection();//커넥션 얻기
			stmt=con.createStatement();//생성시 인자 안들어 감
			sql="select o_pname, o_rname, o_rnum, o_date from order_room where o_pnum="+o_pension;
			rs=stmt.executeQuery(sql);//실행시 인자 들어감
			
			while(rs.next()){
				OrderRoom_Dto o_list=new OrderRoom_Dto();
				o_list.setO_pname(rs.getString("o_pname"));
				o_list.setO_rname(rs.getString("o_rname"));
				o_list.setO_rnum(rs.getInt("o_rnum"));
				o_list.setO_date(rs.getDate("o_date"));
				orderList.add(o_list);
			}
		}
		catch(Exception ex){
			System.out.println("getOrder() 예외 :"+ex);
		}
		finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}
			catch(Exception exx){}
		}		
		return orderList;
	}//getMin_wd
}//class end		
