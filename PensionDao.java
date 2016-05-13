
import java.sql.*;

import javax.sql.*;//DataSource
import javax.naming.*;//lookup

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
	
	public void insertQuestion(Q_pention_Dto dto) throws Exception{
		
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
			pstmt.setInt(5, 0);
			pstmt.executeUpdate();
			
		}catch(Exception ex){
			System.out.println("insertQuestion() ���� : "+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally end
	}//insertQuestion end
	
	public int getArticleCount() throws Exception{
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		int x=0;
		
		try{
			con=getConnection();
			pstmt=con.prepareStatement("select count(*) from Q_pension");
			rs=pstmt.executeQuery();//��������
			
			if(rs.next()){
				x=rs.getInt(1);
			}//if
		}catch(Exception ex1){
			System.out.println("getArticleCount() ����:"+ex1);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally end
		return x;
	}//getArticleCount end
	
	public List<Q_pention_Dto> getList(int start,int cnt) throws Exception{
		//mysql���� int start(���� ��ġ),int end(����)
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		List <Q_pention_Dto>list=null;// Vector ��� List,ArrayList ���
		
		try{
			//ó����
			con=getConnection();//Ŀ�ؼ� ���
			pstmt=con.prepareStatement("select * from Q_pension");
			rs=pstmt.executeQuery();//���� ����
			
			if(rs.next()){
				list=new ArrayList<Q_pention_Dto>();
				do{
					Q_pention_Dto dto=new Q_pention_Dto();
					dto.setQp_num(rs.getInt("qp_num"));
					dto.setQp_state(rs.getBoolean("qp_state"));
					dto.setQp_title(rs.getString("qp_title"));
					dto.setQp_question(rs.getString("qp_question"));
					dto.setQp_id(rs.getString("qp_id"));
					dto.setQp_date(rs.getDate("qp_date"));
					dto.setQp_view(rs.getInt("qp_view"));
					dto.setQp_answer(rs.getString("qp_answer"));
					dto.setQp_pension(rs.getInt("qp_pension"));
					list.add(dto);//list�� �ֱ�
				}while(rs.next());
			}//if
		}catch(Exception ex){
			System.out.println("getList() ����:"+ex);
		}finally{
			try{
				if(rs!=null){rs.close();}
				if(pstmt!=null){pstmt.close();}
				if(con!=null){con.close();}
			}catch(Exception exx){}
		}//finally end
		return list;//***
	}//getList end
}