package youhyoo;

import java.sql.*;

import javax.sql.*;//DataSource
import javax.naming.*;//lookup

import com.sun.corba.se.spi.orbutil.fsm.State;

import java.util.*;

public class PensionDao {
	private static PensionDao instance=new PensionDao();
	
	public static PensionDao getInstance(){
		return instance;
	}
	
	private PensionDao(){
		
	}
	
	private Connection getConnection() throws Exception{
		Context ct=new InitialContext();
		DataSource ds=(DataSource)ct.lookup("java:comp/env/jdbc/mysql");
		return ds.getConnection();
	}//getConnection end
	
	public void insertQuestion(Q_pension_Dto dto, int qp_pension) throws Exception{
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="";
		
		try{
			con=getConnection();
			sql="insert into Q_pension(qp_title,qp_question,qp_id,qp_date,qp_view,qp_pension)"+
					"values(?,?,?,NOW(),?,?)";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setString(1, dto.getQp_title());
			pstmt.setString(2, dto.getQp_question());
			pstmt.setString(3, dto.getQp_id());
			pstmt.setInt(4, 0);
			pstmt.setInt(5, qp_pension);
			pstmt.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("insertQuestion() 예외 : "+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally end
	}//insertQuestion end
	
	public int getArticleCount(int p_num) throws Exception{
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int x=0;
		
		try{
			con=getConnection();
			pstmt=con.prepareStatement("select count(*) from Q_pension where qp_pension="+p_num);
			rs=pstmt.executeQuery();//쿼리수행
			
			if(rs.next()){
				x=rs.getInt(1);
			}//if
		}catch(Exception ex1){
			System.out.println("getArticleCount() 예외:"+ex1);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally end
		return x;
	}//getArticleCount end
	
	public List<Q_pension_Dto> getList(int start,int cnt, int p_num) throws Exception{
		//mysql에서 int start(시작 위치),int end(갯수)
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List <Q_pension_Dto>list=new ArrayList<Q_pension_Dto>();
		try{
			//처리문
			con=getConnection();//커넥션 얻기
			pstmt=con.prepareStatement("select * from Q_pension where qp_pension=? order by qp_num desc limit ?,?");
			pstmt.setInt(1, p_num);
			pstmt.setInt(2, start-1);
			pstmt.setInt(3, cnt);
			rs=pstmt.executeQuery();//쿼리 수행

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
				
				list.add(dto);//list에 넣기
			}

		}catch(Exception ex){
			System.out.println("getList() 예외:"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally end
		return list;//***
	}//getList end
	
	public Q_pension_Dto ViewsIncrease(int qp_num) throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		Statement stmt=null;
		ResultSet rs=null;
		Q_pension_Dto dto=new Q_pension_Dto();
		
		try{
			con=getConnection();
			pstmt=con.prepareStatement("update Q_pension set qp_view=qp_view+1 where qp_num=?");
			pstmt.setInt(1, qp_num);
			pstmt.executeUpdate();
			
			String sql = "select qp_view from q_pension where qp_num="+qp_num;
			stmt=con.createStatement();
			rs=stmt.executeQuery(sql);
			
			if(rs.next()){
				dto.setQp_view(rs.getInt("qp_view"));
			}
			
		}catch(Exception ex){
			System.out.println("ViewsIncrease() 예외 : "+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}
		return dto;
	}
	
	public void updateAnswer(Q_pension_Dto dto) throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="";
		
		try{
			con=getConnection();
			pstmt=con.prepareStatement("select qp_answer from Q_pension where qp_num=?");
			pstmt.setString(1, dto.getQp_answer());
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				dto=new Q_pension_Dto();
				sql="update q_pension set qp_answer=? where=qp_num=?";
				pstmt=con.prepareStatement(sql);
				
				pstmt.setString(1, dto.getQp_answer());
				pstmt.executeUpdate();
			}//if
		}catch(Exception ex){
			System.out.println("updateAnswer 예외 : "+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){
				
			}
		}//finally end
	}//updateAnswer end
	
	public Q_pension_Dto updateGetPension (int qp_num){
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Q_pension_Dto dto=null;
		try{
			con=getConnection();
			pstmt=con.prepareStatement("select qp_num,qp_question,qp_title from q_pension where qp_num=?");
			pstmt.setInt(1, qp_num);
			pstmt.executeQuery();
			
			if(rs.next()){
				dto=new Q_pension_Dto();
				dto.setQp_num(rs.getInt("qp_num"));
				dto.setQp_question(rs.getString("qp_question"));
				dto.setQp_title(rs.getString("qp_title"));
			}
		}catch(Exception ex){
			System.out.println("updateGetPension() 예외 : "+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){
				
			}//try end
		}//finally end
		return dto;
	}//updateGetPension end
	
	public void Q_ToYouHyoo(Q_Youhyoo_Dto dto) throws Exception{
			
			Connection con=null;
			PreparedStatement pstmt=null;
			ResultSet rs=null;
			
			String sql="";
			
			try{
				con=getConnection();
				sql="insert into Q_youhyoo(qy_num,qy_title,qy_content,qy_id,qy_date,qy_state,qy_answer)"+
						"values(?,?,?,?,NOW(),?,?)";
				pstmt=con.prepareStatement(sql);
				
				pstmt.setInt(1, dto.getQy_num());
				pstmt.setString(2, dto.getQy_title());
				pstmt.setString(3, dto.getQy_content());
				pstmt.setString(4, dto.getQy_id());
				pstmt.setBoolean(5, true);
				pstmt.setString(6, dto.getQy_answer());
				pstmt.executeUpdate();
				
			}catch(Exception ex){
				System.out.println("insertQuestion() 예외 : "+ex);
			}finally{
				try{
					if(rs!=null){rs.close();}
					if(pstmt!=null){pstmt.close();}
					if(con!=null){con.close();}
				}catch(Exception exx){}
			}//finally end
		}//insertQuestion end
	
	//일대일 상담문의 리스트
	public List<Q_Youhyoo_Dto> QyhList(int start,int cnt, int qy_num) throws Exception{
		//mysql에서 int start(시작 위치),int end(갯수)
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List <Q_Youhyoo_Dto>list=new ArrayList<Q_Youhyoo_Dto>();
		try{
			//처리문
			con=getConnection();//커넥션 얻기
			pstmt=con.prepareStatement("select * from Q_youhyoo where qy_id=? order by qy_num desc limit ?,?");
			pstmt.setInt(1, qy_num);
			pstmt.setInt(2, start-1);
			pstmt.setInt(3, cnt);
			rs=pstmt.executeQuery();//쿼리 수행

			while(rs.next()){
				Q_Youhyoo_Dto dto=new Q_Youhyoo_Dto();
					
				dto.setQy_num(rs.getInt("qp_num"));
				dto.setQy_title(rs.getString("qy_title"));
				dto.setQy_content(rs.getString("qy_content"));
				dto.setQy_id(rs.getString("qy_id"));
				dto.setQy_date(rs.getDate("qy_date"));
				dto.setQy_state(rs.getBoolean("qy_state"));
				dto.setQy_answer(rs.getString("qy_answer"));
					
				list.add(dto);//list에 넣기
			}

		}catch(Exception ex){
			System.out.println("getList() 예외:"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally end
		return list;//***
	}//QyhList end
	
	public InsertDto PensionDetail(int p_num) throws Exception{
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		InsertDto dto=new InsertDto();
		
		try{
			con=getConnection();
			pstmt=con.prepareStatement("select * from pension inner join detail_Support on p_num=rs_pnum"+
			" inner join detail_Facility on p_num=rf_pnum where p_num=?");
			
			pstmt.setInt(1, p_num);
			rs=pstmt.executeQuery();
			
			if(rs.next()){
				
				dto.setP_intro(rs.getString("p_intro"));
				
				dto.setRs_market(rs.getString("rs_market"));
				dto.setRs_meal(rs.getString("rs_meal"));
				dto.setRs_party(rs.getString("rs_party"));
				dto.setRs_board(rs.getString("rs_board"));
				dto.setRs_pickup(rs.getString("rs_pickup"));
				dto.setRs_inet(rs.getString("rs_inet"));
				dto.setRs_movie(rs.getString("rs_movie"));
				dto.setRs_cafe(rs.getString("rs_cafe"));
				dto.setRs_shuttle(rs.getString("rs_shuttle"));
				
				dto.setRf_pool(rs.getString("rf_pool"));
				dto.setRf_slide(rs.getString("rf_slide"));
				dto.setRf_soccer(rs.getString("rf_soccer"));
				dto.setRf_jokgoo(rs.getString("rf_jokgoo"));
				dto.setRf_bbq(rs.getString("rf_bbq"));
				dto.setRf_campfire(rs.getString("rf_campfire"));
				dto.setRf_karaoke(rs.getString("rf_karaoke"));
				dto.setRf_basketball(rs.getString("rf_basketball"));
				dto.setRf_seminar(rs.getString("rf_seminar"));
				dto.setRf_bike(rs.getString("rf_bike"));
				dto.setRf_4wbike(rs.getString("rf_4wbike"));
				dto.setRf_servival(rs.getString("rf_servival"));
				
			}
		}catch(Exception ex){
			System.out.println("PensionDetail() 오류"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally end
		return dto;
	}//PensionInfo end
	
	//룸사진얻어오기
	public String get_pension_photo(String num){
		Connection con = null;
		Statement stmt= null;
		ResultSet rs= null;
		String p_photo="";
		try{
			con=getConnection();
			stmt=con.createStatement();
			
			rs=stmt.executeQuery("select p_photo from pension where p_num="+num);
			
			if(rs.next()){
				
				p_photo=rs.getString("p_photo");
				
			}//if
		}catch(Exception ex){
			System.out.println("get_pension_photo() 오류"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(stmt!=null){stmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){
				
			}
		}
		return p_photo;
	}//get_pension_photo()
	
	// 방사진 얻어오기
	public List get_room_photo(String num) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Room_Dto> r_list = new ArrayList<Room_Dto>();
		try {
			con = getConnection();
			stmt = con.createStatement();

			rs = stmt.executeQuery("select r_photo,r_name from room where r_pension="+ num);

			while (rs.next()) {
				Room_Dto r_dto = new Room_Dto();

				r_dto.setR_photo(rs.getString("r_photo"));
				r_dto.setR_name(rs.getString("r_name"));

				r_list.add(r_dto);
			}// while
		} catch (Exception ex) {
			System.out.println("get_room_photo() 오류" + ex);
		} finally {
			try {
				if (rs != null) {rs.close();}
				if (stmt != null) {	stmt.close();}
				if (con != null) {con.close();}
			} catch (Exception exx) {}
		}
		return r_list;
	}// get_room_photo()
	
}
