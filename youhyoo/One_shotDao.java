package youhyoo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.sql.*;
import javax.naming.*;

public class One_shotDao {

		private Connection getConnection() throws Exception{
			Context ct=new InitialContext();
			DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
			return ds.getConnection();
		}//getConnection end
	
	Connection con=null;
	Statement stmt=null;
	ResultSet rs=null;
	//생성자 : 초기화 작업
	
	public List get_P_List(String sql){
		
		List <Pension_Dto>p_num=new ArrayList<Pension_Dto>();
		
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select p_num,p_name,p_addr1,p_addr2 from pension where p_num=any(select distinct ra_pnum from detail_Around inner join detail_Facility on ra_pnum=rf_pnum inner join detail_Support on ra_pnum=rs_pnum inner join detail_Structure on ra_pnum=rr_pnum where "+sql);
			while(rs.next()){
				Pension_Dto p_dto=new Pension_Dto();
				
				//업소,  
				//addr1,addr2 
				p_dto.setP_num(rs.getInt("p_num"));
				p_dto.setP_name(rs.getString("p_name"));
				p_dto.setP_addr1(rs.getString("p_addr1"));
				p_dto.setP_addr2(rs.getString("p_addr2"));
				
				p_num.add(p_dto);
			}//while
		}catch(Exception ex){
			System.out.println("get_P_List() 오류"+ex);
		}finally{
			try{
			if(rs!=null){rs.close();}
			if(stmt!=null){stmt.close();}
			if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return p_num;
	}//getList()
	
	public List get_R_List(int p_num){
		
		List <Room_Dto>r_list=new ArrayList<Room_Dto>();
		try{
			con=getConnection();
			stmt=con.createStatement();
			rs=stmt.executeQuery("select * from room inner join pension on p_num=r_pension where r_pension="+p_num);

			while(rs.next()){
				Room_Dto r_dto=new Room_Dto();
				
			    //인원,추가금액,요금
				r_dto.setR_name(rs.getString("r_name")); //빈객실
				r_dto.setR_size(rs.getInt("r_size")); //구조
				r_dto.setR_mincapa(rs.getInt("r_mincapa"));
				r_dto.setR_maxcapa(rs.getInt("r_maxcapa"));
				
				//추가금액?????????????????

				//요금
				r_dto.setR_max_wd(rs.getInt("r_max_wd")); //성수기 주중가
				r_dto.setR_min_wd(rs.getInt("r_min_wd")); //비성수기 주중가
				r_dto.setR_max_we(rs.getInt("r_max_we")); //성수기 주말가
				r_dto.setR_min_we(rs.getInt("r_min_we")); //비성수기 주말가
				
				r_list.add(r_dto);
			}//while
		}catch(Exception ex){
			System.out.println("get_r_List() 오류"+ex);
		}finally{
			try{
			if(rs!=null){rs.close();}
			if(stmt!=null){stmt.close();}
			if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return r_list;
	}//getList()
}//class

