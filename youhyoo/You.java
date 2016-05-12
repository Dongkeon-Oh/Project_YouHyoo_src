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
	
	
	Connection con=null;
	Statement stmt=null;
	ResultSet rs=null;
	//������ : �ʱ�ȭ �۾�
	public You(){
		try{
			Class.forName(DRIVER); //����̹� �ε�
			System.out.println("����̹� �ε� ����");
		}catch(ClassNotFoundException ex1){
			System.out.println("����̹� �ε� ����:"+ex1);
		}
	}//������ end
	
	public List get_P_List(String sql){
		
		List <Pension_Dto>p_num=new ArrayList<Pension_Dto>();
		
		try{
			con=DriverManager.getConnection(URL, USER, PWD);
			stmt=con.createStatement();
			System.out.println("select p_num,p_name,p_addr1,p_addr2 from pension where p_num=any(select distinct ra_pnum from detail_Around inner join detail_Facility on ra_pnum=rf_pnum inner join detail_Support on ra_pnum=rs_pnum inner join detail_Structure on ra_pnum=rr_pnum where "+sql);

			rs=stmt.executeQuery("select p_num,p_name,p_addr1,p_addr2 from pension where p_num=any(select distinct ra_pnum from detail_Around inner join detail_Facility on ra_pnum=rf_pnum inner join detail_Support on ra_pnum=rs_pnum inner join detail_Structure on ra_pnum=rr_pnum where "+sql);
			while(rs.next()){
				Pension_Dto p_dto=new Pension_Dto();
				
				//����,  
				//addr1,addr2 
				p_dto.setP_num(rs.getInt("p_num"));
				p_dto.setP_name(rs.getString("p_name"));
				p_dto.setP_addr1(rs.getString("p_addr1"));
				p_dto.setP_addr1(rs.getString("p_addr2"));
				
				p_num.add(p_dto);
			}//while
		}catch(Exception ex){
			System.out.println("get_P_List() ����"+ex);
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
			con=DriverManager.getConnection(URL, USER, PWD);
			stmt=con.createStatement();
			rs=stmt.executeQuery("select * from room inner join pension on p_num=r_pension where r_pension="+p_num);

			while(rs.next()){
				Room_Dto r_dto=new Room_Dto();
				
			    //�ο�,�߰��ݾ�,���
				r_dto.setR_name(rs.getString("r_name")); //�󰴽�
				r_dto.setR_size(rs.getInt("r_size")); //����
				r_dto.setR_mincapa(rs.getInt("r_mincapa"));
				r_dto.setR_maxcapa(rs.getInt("r_maxcapa"));
				
				//�߰��ݾ�?????????????????

				//���
				r_dto.setR_max_wd(rs.getInt("r_max_wd")); //������ ���߰�
				r_dto.setR_min_wd(rs.getInt("r_min_wd")); //�񼺼��� ���߰�
				r_dto.setR_max_we(rs.getInt("r_max_we")); //������ �ָ���
				r_dto.setR_min_we(rs.getInt("r_min_we")); //�񼺼��� �ָ���
				
				r_list.add(r_dto);
			}//while
		}catch(Exception ex){
			System.out.println("get_r_List() ����"+ex);
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

