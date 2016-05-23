package youhyoo;

import java.util.*;

public class OrderRoom_Dto {
	private int o_num;
	private int o_pnum;
	private String o_pname;
	private int o_rnum;
	private int o_people;
	private String o_rname;
	private Date o_date;
	private int o_exprice;
	private int o_price;
	private Boolean o_state;
	private int o_group;
	
	public OrderRoom_Dto(){}

	public int getO_num() {
		return o_num;
	}

	public void setO_num(int o_num) {
		this.o_num = o_num;
	}

	public int getO_pnum() {
		return o_pnum;
	}

	public void setO_pnum(int o_pnum) {
		this.o_pnum = o_pnum;
	}

	public String getO_pname() {
		return o_pname;
	}

	public void setO_pname(String o_pname) {
		this.o_pname = o_pname;
	}

	public int getO_rnum() {
		return o_rnum;
	}

	public void setO_rnum(int o_rnum) {
		this.o_rnum = o_rnum;
	}

	public int getO_people() {
		return o_people;
	}

	public void setO_people(int o_people) {
		this.o_people = o_people;
	}

	public String getO_rname() {
		return o_rname;
	}

	public void setO_rname(String o_rname) {
		this.o_rname = o_rname;
	}

	public Date getO_date() {
		return o_date;
	}

	public void setO_date(Date o_date) {
		this.o_date = o_date;
	}

	public int getO_exprice() {
		return o_exprice;
	}

	public void setO_exprice(int o_exprice) {
		this.o_exprice = o_exprice;
	}

	public int getO_price() {
		return o_price;
	}

	public void setO_price(int o_price) {
		this.o_price = o_price;
	}

	public Boolean getO_state() {
		return o_state;
	}

	public void setO_state(Boolean o_state) {
		this.o_state = o_state;
	}

	public int getO_group() {
		return o_group;
	}

	public void setO_group(int o_group) {
		this.o_group = o_group;
	}
}
